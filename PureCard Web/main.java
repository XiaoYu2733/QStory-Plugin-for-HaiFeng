import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.*;
import android.webkit.*;
import java.io.*;
import java.util.*;
import java.net.URLEncoder;
import java.net.URLDecoder;

private static final String LOCAL_HOME_PATH = "/storage/emulated/0/Android/data/com.tencent.mobileqq/QStory/Plugin/PureCard Web/html/Xie0v0.html";

private static final int CORNER_RADIUS = 12;
private static final int TOOLBAR_CORNER_RADIUS = 8;
private static final int DIALOG_WIDTH_RATIO = 94;
private static final int DIALOG_HEIGHT_RATIO = 90;
private static final int COMPACT_DIALOG_WIDTH_RATIO = 88;
private static final int COMPACT_DIALOG_HEIGHT_RATIO = 65;

private static final String COLOR_PRIMARY = "#4A90E2";
private static final String COLOR_SUCCESS = "#34C759";
private static final String COLOR_WARNING = "#FF9500";
private static final String COLOR_ERROR = "#FF6B6B";
private static final String COLOR_SECONDARY = "#5856D6";
private static final String COLOR_DOWNLOAD = "#27AE60";
private static final String COLOR_CUSTOM = "#9B59B6";
private static final String COLOR_BG_LIGHT = "#F0F4F8";
private static final String COLOR_BG_LIGHTER = "#F8FAFF";
private static final String COLOR_TEXT_PRIMARY = "#2C3E50";
private static final String COLOR_TEXT_SECONDARY = "#A0AEC0";
private static final String COLOR_BORDER = "#E1E8ED";
private static final String COLOR_EDGE_TRIGGER = "#664A90E2";

private static final String DESKTOP_UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";
private static final String BING_SEARCH_URL = "https://cn.bing.com/search?q=";

private static final int EDGE_TRIGGER_WIDTH = 14;
private static final int EDGE_TRIGGER_HEIGHT = 120;

private static final Set<String> temporaryBypassUrls = new HashSet<>();

private static Dialog browserDialog = null;

private WindowManager windowManager;
private View edgeTrigger;
private boolean isEdgeTriggerEnabled = false;

addItem("🌐 浏览器悬浮条", "toggleEdgeTrigger");
addItem("🪟 浏览器视图", "openBrowser");

void onCreateMenu(Object msg) {
    if (msg != null) {
        addMenuItem("打开", "handleLongPressBrowser");
    }
}

private void createEdgeTrigger() {
    Activity act = getActivity();
    if (act == null) {
        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {
                createEdgeTrigger();
            }
        }, 1000);
        return;
    }
    
    act.runOnUiThread(new Runnable() {
        public void run() {
            try {
                if (edgeTrigger != null) {
                    removeEdgeTrigger();
                }
                
                windowManager = (WindowManager) act.getSystemService(Context.WINDOW_SERVICE);
                
                WindowManager.LayoutParams edgeParams = new WindowManager.LayoutParams(
                    dpToPx(act, EDGE_TRIGGER_WIDTH),
                    dpToPx(act, EDGE_TRIGGER_HEIGHT),
                    WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    PixelFormat.TRANSLUCENT
                );
                
                edgeParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
                edgeParams.x = 0;
                edgeParams.y = 0;
                
                edgeTrigger = new View(act);
                GradientDrawable edgeBg = new GradientDrawable();
                edgeBg.setColor(Color.parseColor(COLOR_EDGE_TRIGGER));
                edgeBg.setCornerRadii(new float[]{
                    dpToPx(act, 16), dpToPx(act, 16),
                    dpToPx(act, 16), dpToPx(act, 16),
                    dpToPx(act, 16), dpToPx(act, 16),
                    dpToPx(act, 16), dpToPx(act, 16)
                });
                edgeTrigger.setBackground(edgeBg);
                
                edgeTrigger.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        openBrowserWithUrlAndFlag(null, false);
                    }
                });
                
                edgeTrigger.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        removeEdgeTrigger();
                        return true;
                    }
                });
                
                windowManager.addView(edgeTrigger, edgeParams);
                isEdgeTriggerEnabled = true;
                
            } catch (Exception e) {
            }
        }
    });
}

private void removeEdgeTrigger() {
    if (edgeTrigger != null && windowManager != null) {
        try {
            windowManager.removeView(edgeTrigger);
        } catch (Exception e) {
        }
        edgeTrigger = null;
        isEdgeTriggerEnabled = false;
    }
}

public void toggleEdgeTrigger(String groupUin, String userUin, int chatType) {
    Activity act = getActivity();
    if (act != null) {
        act.runOnUiThread(new Runnable() {
            public void run() {
                if (isEdgeTriggerEnabled) {
                    removeEdgeTrigger();
                } else {
                    createEdgeTrigger();
                }
            }
        });
    }
}

public void openBrowser(String groupUin, String userUin, int chatType) {
    openBrowserWithUrlAndFlag(null, false);
}

public void handleLongPressBrowser(Object msg) {
    String content = msg.MessageContent;
    String urlToOpen = extractUrlFromMessage(content);
    
    if (urlToOpen != null) {
        openBrowserWithUrlAndFlag(urlToOpen, true);
    } else {
        String searchUrl = BING_SEARCH_URL + safeEncode(content);
        openBrowserWithUrlAndFlag(searchUrl, false);
    }
}

private void openBrowserWithUrlAndFlag(String url, boolean isFromLongPressBilibili) {
    Activity act = getActivity();
    if (act != null) {
        act.runOnUiThread(new Runnable() {
            public void run() {
                showBrowserDialogWithFlag(act, url, isFromLongPressBilibili);
            }
        });
    }
}

private void temporarilyBypassDownloadCheck(String url) {
    if (url != null && !url.isEmpty()) {
        temporaryBypassUrls.add(url);
        
        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {
                temporaryBypassUrls.remove(url);
            }
        }, 5000);
    }
}

private boolean shouldBypassDownloadCheck(String url) {
    return url != null && temporaryBypassUrls.contains(url);
}

private void showBrowserDialogWithFlag(Activity act, String initialUrl, boolean isFromLongPressBilibili) {
    if (browserDialog != null && browserDialog.isShowing()) {
        return;
    }

    final Dialog dialog = createBrowserDialog(act);
    final WebView webView = createWebView(act);
    final EditText urlInput = createUrlInput(act, initialUrl);
    final ProgressBar progressBar = createProgressBar(act);
    final boolean[] isDesktopMode = {false};
    final String[] originalUrl = {null};
    
    LinearLayout mainLayout = buildBrowserLayout(act, dialog, webView, urlInput, progressBar, 
                                               isDesktopMode, originalUrl, initialUrl, isFromLongPressBilibili);
    
    setupDialogWindow(dialog, act, mainLayout);
    configureWebViewClients(act, webView, progressBar, urlInput, isDesktopMode, originalUrl);
    setupBrowserInteractions(act, dialog, webView, urlInput, progressBar, isDesktopMode, originalUrl);
    
    dialog.show();
    loadInitialPage(webView, urlInput, initialUrl, isFromLongPressBilibili, originalUrl, isDesktopMode[0], act);
}

private Dialog createBrowserDialog(Activity act) {
    Dialog dialog = new Dialog(act);
    browserDialog = dialog;
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(true);
    dialog.setCanceledOnTouchOutside(true);
    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
        public void onDismiss(DialogInterface d) { browserDialog = null; }
    });
    return dialog;
}

private LinearLayout buildBrowserLayout(Activity act, Dialog dialog, WebView webView, EditText urlInput,
                                      ProgressBar progressBar, boolean[] isDesktopMode, String[] originalUrl,
                                      String initialUrl, boolean isFromLongPressBilibili) {
    LinearLayout mainLayout = createMainLayout(act);
    
    mainLayout.addView(createTitleBar(act, dialog));
    mainLayout.addView(createUrlArea(act, urlInput, progressBar, webView));
    mainLayout.addView(webView);
    mainLayout.addView(createBottomBar(act, webView, urlInput, isDesktopMode, originalUrl));
    
    return mainLayout;
}

private void setupDialogWindow(Dialog dialog, Activity act, View contentView) {
    dialog.setContentView(contentView);
    Window window = dialog.getWindow();
    WindowManager.LayoutParams params = window.getAttributes();
    
    DisplayMetrics metrics = new DisplayMetrics();
    act.getWindowManager().getDefaultDisplay().getMetrics(metrics);
    
    params.width = (int) (metrics.widthPixels * DIALOG_WIDTH_RATIO / 100.0);
    params.height = (int) (metrics.heightPixels * DIALOG_HEIGHT_RATIO / 100.0);
    params.gravity = Gravity.CENTER;
    window.setBackgroundDrawableResource(android.R.color.transparent);
    window.setAttributes(params);
    window.setWindowAnimations(android.R.style.Animation_Dialog);
}

private LinearLayout createMainLayout(Activity act) {
    LinearLayout layout = new LinearLayout(act);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setPadding(0, 0, 0, 0);
    
    GradientDrawable bg = new GradientDrawable();
    bg.setColor(Color.WHITE);
    bg.setCornerRadius(dpToPx(act, CORNER_RADIUS));
    layout.setBackground(bg);
    
    return layout;
}

private LinearLayout createTitleBar(Activity act, Dialog dialog) {
    LinearLayout titleBar = new LinearLayout(act);
    titleBar.setOrientation(LinearLayout.HORIZONTAL);
    titleBar.setGravity(Gravity.CENTER_VERTICAL);
    titleBar.setPadding(dpToPx(act, 12), dpToPx(act, 8), dpToPx(act, 8), dpToPx(act, 8));
    
    GradientDrawable titleBg = new GradientDrawable();
    titleBg.setColor(Color.parseColor(COLOR_BG_LIGHT));
    titleBg.setCornerRadii(new float[]{
        dpToPx(act, CORNER_RADIUS), dpToPx(act, CORNER_RADIUS),
        dpToPx(act, CORNER_RADIUS), dpToPx(act, CORNER_RADIUS),
        0, 0, 0, 0
    });
    titleBar.setBackground(titleBg);
    
    LinearLayout titleContent = createTitleContent(act);
    Button closeBtn = createCompactIconButton(act, "✕", COLOR_ERROR, 10);
    closeBtn.setOnClickListener(new View.OnClickListener() { 
        public void onClick(View v) { dialog.dismiss(); } 
    });
    
    titleBar.addView(titleContent);
    titleBar.addView(closeBtn);
    
    return titleBar;
}

private LinearLayout createTitleContent(Activity act) {
    LinearLayout titleContent = new LinearLayout(act);
    titleContent.setOrientation(LinearLayout.HORIZONTAL);
    titleContent.setGravity(Gravity.CENTER_VERTICAL);
    
    TextView browserIcon = new TextView(act);
    browserIcon.setText("🌐");
    browserIcon.setTextSize(14);
    
    TextView titleText = new TextView(act);
    titleText.setText("PureCard 浏览器");
    titleText.setTextSize(13);
    titleText.setTextColor(Color.parseColor(COLOR_TEXT_PRIMARY));
    titleText.setTypeface(null, android.graphics.Typeface.BOLD);
    titleText.setPadding(dpToPx(act, 6), 0, 0, 0);
    
    titleContent.addView(browserIcon);
    titleContent.addView(titleText);
    
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, -2, 1);
    titleContent.setLayoutParams(params);
    
    return titleContent;
}

private LinearLayout createUrlArea(Activity act, EditText urlInput, ProgressBar progressBar, WebView webView) {
    LinearLayout urlArea = new LinearLayout(act);
    urlArea.setOrientation(LinearLayout.VERTICAL);
    urlArea.setPadding(dpToPx(act, 12), dpToPx(act, 8), dpToPx(act, 12), dpToPx(act, 10));
    urlArea.setBackgroundColor(Color.parseColor(COLOR_BG_LIGHTER));
    
    progressBar.setLayoutParams(createProgressBarLayoutParams(act));
    urlArea.addView(progressBar);
    urlArea.addView(createUrlInputLayout(act, urlInput, webView));
    
    return urlArea;
}

private LinearLayout createUrlInputLayout(Activity act, final EditText urlInput, final WebView webView) {
    LinearLayout urlLayout = new LinearLayout(act);
    urlLayout.setOrientation(LinearLayout.HORIZONTAL);
    urlLayout.setGravity(Gravity.CENTER_VERTICAL);
    urlLayout.setPadding(dpToPx(act, 10), dpToPx(act, 8), dpToPx(act, 10), dpToPx(act, 8));
    
    GradientDrawable urlBg = new GradientDrawable();
    urlBg.setColor(Color.WHITE);
    urlBg.setCornerRadius(dpToPx(act, 10));
    urlBg.setStroke(dpToPx(act, 1), Color.parseColor(COLOR_BORDER));
    urlLayout.setBackground(urlBg);
    
    LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(0, -2, 1);
    inputParams.setMargins(0, 0, dpToPx(act, 6), 0);
    urlInput.setLayoutParams(inputParams);
    
    Button editBtn = createSmallActionButton(act, "✏️", COLOR_TEXT_SECONDARY);
    editBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            showUrlEditDialog(getActivity(), urlInput.getText().toString(), webView, urlInput);
        }
    });
    
    final Button goBtn = createSmallActionButton(act, "➡️", COLOR_PRIMARY);
    goBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            String url = urlInput.getText().toString().trim();
            if (isBilibiliMobileUrl(url) && !isDesktopMode[0]) {
                showBilibiliMobileWarning(getActivity(), url);
            } else {
                loadUrlDirectly(url, webView, urlInput);
            }
        }
    });
    
    urlLayout.addView(urlInput);
    urlLayout.addView(editBtn);
    urlLayout.addView(goBtn);
    
    return urlLayout;
}

private EditText createUrlInput(Activity act, String initialUrl) {
    EditText urlInput = new EditText(act);
    urlInput.setHint("输入网址或搜索内容...");
    urlInput.setText(initialUrl == null ? "" : initialUrl);
    urlInput.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
    urlInput.setSingleLine(true);
    urlInput.setEllipsize(android.text.TextUtils.TruncateAt.END);
    urlInput.setBackground(null);
    urlInput.setPadding(dpToPx(act, 6), dpToPx(act, 8), dpToPx(act, 6), dpToPx(act, 8));
    urlInput.setTextSize(13);
    urlInput.setTextColor(Color.parseColor(COLOR_TEXT_PRIMARY));
    urlInput.setHintTextColor(Color.parseColor(COLOR_TEXT_SECONDARY));
    
    return urlInput;
}

private ProgressBar createProgressBar(Activity act) {
    ProgressBar progressBar = new ProgressBar(act, null, android.R.attr.progressBarStyleHorizontal);
    progressBar.setVisibility(View.GONE);
    return progressBar;
}

private LinearLayout.LayoutParams createProgressBarLayoutParams(Activity act) {
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, dpToPx(act, 2));
    params.setMargins(0, 0, 0, dpToPx(act, 8));
    return params;
}

private LinearLayout createBottomBar(Activity act, final WebView webView, final EditText urlInput, 
                                   final boolean[] isDesktopMode, final String[] originalUrl) {
    LinearLayout bottomBar = new LinearLayout(act);
    bottomBar.setOrientation(LinearLayout.HORIZONTAL);
    bottomBar.setGravity(Gravity.CENTER);
    bottomBar.setPadding(dpToPx(act, 8), dpToPx(act, 10), dpToPx(act, 10), dpToPx(act, 10));
    
    GradientDrawable bottomBg = new GradientDrawable();
    bottomBg.setColor(Color.parseColor(COLOR_BG_LIGHTER));
    bottomBg.setCornerRadii(new float[]{0, 0, 0, 0, dpToPx(act, CORNER_RADIUS), dpToPx(act, CORNER_RADIUS), 
                                       dpToPx(act, CORNER_RADIUS), dpToPx(act, CORNER_RADIUS)});
    bottomBg.setStroke(dpToPx(act, 1), Color.parseColor(COLOR_BORDER));
    bottomBar.setBackground(bottomBg);
    
    Button backBtn = createNavButton(act, "⬅️", COLOR_PRIMARY);
    Button forwardBtn = createNavButton(act, "➡️", COLOR_PRIMARY);
    Button refreshBtn = createNavButton(act, "🔄", COLOR_SUCCESS);
    Button homeBtn = createNavButton(act, "🏠", COLOR_WARNING);
    final Button uaSwitchBtn = createNavButton(act, "📱", COLOR_SECONDARY);
    Button downloadBtn = createNavButton(act, "📥", COLOR_DOWNLOAD);
    
    setupBottomBarActions(act, backBtn, forwardBtn, refreshBtn, homeBtn, uaSwitchBtn, downloadBtn, 
                         webView, urlInput, isDesktopMode, originalUrl);
    
    bottomBar.addView(backBtn);
    bottomBar.addView(forwardBtn);
    bottomBar.addView(refreshBtn);
    bottomBar.addView(homeBtn);
    bottomBar.addView(uaSwitchBtn);
    bottomBar.addView(downloadBtn);
    
    return bottomBar;
}

private void setupBottomBarActions(Activity act, Button backBtn, Button forwardBtn, Button refreshBtn,
                                 Button homeBtn, final Button uaSwitchBtn, Button downloadBtn,
                                 final WebView webView, final EditText urlInput, 
                                 final boolean[] isDesktopMode, final String[] originalUrl) {
    backBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            if (webView.canGoBack()) {
                webView.goBack();
                originalUrl[0] = null;
            }
        }
    });
    
    forwardBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            if (webView.canGoForward()) {
                webView.goForward();
                originalUrl[0] = null;
            }
        }
    });
    
    refreshBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) { 
            webView.reload();
        }
    });
    
    homeBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) { 
            loadLocalHome(webView, urlInput);
            originalUrl[0] = null;
        }
    });
    
    uaSwitchBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            isDesktopMode[0] = !isDesktopMode[0];
            
            if (isDesktopMode[0]) {
                switchToDesktopMode(webView.getSettings(), isDesktopMode, uaSwitchBtn);
            } else {
                switchToMobileMode(webView.getSettings(), isDesktopMode, uaSwitchBtn);
            }
            
            handleUASwitchReload(webView, originalUrl[0], isDesktopMode[0], act);
        }
    });
    
    downloadBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            showDownloadManagerDialog(act);
        }
    });
}

private WebView createWebView(Activity act) {
    WebView webView = new WebView(act);
    webView.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1));
    configureWebViewSettings(act, webView);
    return webView;
}

private void configureWebViewSettings(Activity act, WebView webView) {
    WebSettings settings = webView.getSettings();
    
    settings.setJavaScriptEnabled(true);
    settings.setDomStorageEnabled(true);
    settings.setDatabaseEnabled(true);
    settings.setAppCacheEnabled(true);
    settings.setLoadsImagesAutomatically(true);
    
    settings.setUseWideViewPort(true);
    settings.setLoadWithOverviewMode(true);
    settings.setBuiltInZoomControls(true);
    settings.setDisplayZoomControls(false);
    settings.setSupportZoom(true);
    
    settings.setAllowFileAccess(true);
    settings.setAllowContentAccess(true);
    enableUniversalAccess(settings);
}

private void enableUniversalAccess(WebSettings settings) {
    try {
        settings.getClass().getMethod("setAllowFileAccessFromFileURLs", boolean.class).invoke(settings, true);
        settings.getClass().getMethod("setAllowUniversalAccessFromFileURLs", boolean.class).invoke(settings, true);
    } catch (Throwable t) {
    }
}

private void configureWebViewClients(Activity act, final WebView webView, final ProgressBar progressBar,
                                   final EditText urlInput, final boolean[] isDesktopMode, final String[] originalUrl) {
    webView.setWebViewClient(new WebViewClient() {
        public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(10);
            urlInput.setText(url);
            
            if (shouldBypassDownloadCheck(url)) {
                return;
            }
            
            if (isDownloadUrl(url)) {
                act.runOnUiThread(new Runnable() {
                    public void run() {
                        showDownloadConfirmationDialog(act, url, webView);
                    }
                });
                view.stopLoading();
                return;
            }
        }

        public void onPageFinished(WebView view, String url) {
            progressBar.setProgress(100);
            progressBar.postDelayed(new Runnable() {
                public void run() { progressBar.setVisibility(View.GONE); }
            }, 300);
            if (isDesktopMode[0]) webView.setInitialScale(30);
            else webView.setInitialScale(100);
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            progressBar.setVisibility(View.GONE);
            showFriendlyErrorDialog(act, errorCode, description, failingUrl);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            
            if (shouldBypassDownloadCheck(url)) {
                return false;
            }
            
            if (isDownloadUrl(url)) {
                act.runOnUiThread(new Runnable() {
                    public void run() {
                        showDownloadConfirmationDialog(act, url, webView);
                    }
                });
                return true;
            }
            
            if (isBilibiliMobileUrl(url) && !isDesktopMode[0]) {
                showBilibiliMobileWarning(act, url);
                originalUrl[0] = null;
                return true;
            }
            return false;
        }
    });

    webView.setWebChromeClient(new WebChromeClient() {
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);
        }
    });
}

private void setupBrowserInteractions(Activity act, Dialog dialog, final WebView webView, 
                                    final EditText urlInput, final ProgressBar progressBar,
                                    final boolean[] isDesktopMode, final String[] originalUrl) {
    urlInput.setOnKeyListener(new View.OnKeyListener() {
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                String url = urlInput.getText().toString().trim();
                
                if (shouldBypassDownloadCheck(url)) {
                    loadUrlDirectly(url, webView, urlInput);
                    originalUrl[0] = null;
                    return true;
                }
                
                if (isBilibiliMobileUrl(url) && !isDesktopMode[0]) {
                    showBilibiliMobileWarning(act, url);
                    originalUrl[0] = null;
                } else {
                    loadUrlDirectly(url, webView, urlInput);
                    originalUrl[0] = null;
                }
                return true;
            }
            return false;
        }
    });
}

private void loadInitialPage(WebView webView, EditText urlInput, String initialUrl, 
                           boolean isFromLongPressBilibili, String[] originalUrl, boolean isDesktopMode, Activity act) {
    if (initialUrl != null && initialUrl.length() > 0) {
        if (isBilibiliMobileUrl(initialUrl) && !isDesktopMode) {
            showBilibiliMobileWarning(act, initialUrl);
            if (isFromLongPressBilibili) {
                originalUrl[0] = initialUrl;
            } else {
                originalUrl[0] = null;
            }
        } else {
            webView.loadUrl(initialUrl);
            urlInput.setText(initialUrl);
            originalUrl[0] = null;
        }
    } else {
        loadLocalHome(webView, urlInput);
        originalUrl[0] = null;
    }
}

private void loadLocalHome(WebView webView, EditText urlInput) {
    java.io.File f = new java.io.File(LOCAL_HOME_PATH);
    if (f.exists()) {
        String localUrl = "file://" + LOCAL_HOME_PATH;
        webView.loadUrl(localUrl);
        if (urlInput != null) urlInput.setText(localUrl);
    } else {
        showHomeMissingDialog(getActivity());
        String fallback = "https://cn.bing.com";
        webView.loadUrl(fallback);
        if (urlInput != null) urlInput.setText(fallback);
    }
}

private void handleUASwitchReload(WebView webView, String originalUrl, boolean isDesktopMode, Activity act) {
    if (originalUrl != null && !originalUrl.isEmpty()) {
        if (!isBilibiliMobileUrl(originalUrl) || 
            (isBilibiliMobileUrl(originalUrl) && isDesktopMode)) {
            forceReloadWithNewUA(webView, originalUrl);
        } else {
            showBilibiliMobileWarning(act, originalUrl);
        }
    } else {
        String currentUrl = webView.getUrl();
        if (currentUrl != null && !currentUrl.isEmpty()) {
            forceReloadWithNewUA(webView, currentUrl);
        } else {
            loadLocalHome(webView, null);
        }
    }
}

private void forceReloadWithNewUA(WebView webView, String currentUrl) {
    if (currentUrl == null || currentUrl.isEmpty()) return;
    
    webView.clearCache(true);
    webView.clearHistory();
    
    String reloadUrl;
    if (currentUrl.contains("?")) {
        reloadUrl = currentUrl + "&_t=" + System.currentTimeMillis();
    } else {
        reloadUrl = currentUrl + "?_t=" + System.currentTimeMillis();
    }
    
    webView.loadUrl(reloadUrl);
}

private Button createSmallActionButton(Activity act, String icon, String color) {
    Button btn = new Button(act);
    btn.setText(icon);
    btn.setTextSize(11);
    btn.setPadding(dpToPx(act, 8), dpToPx(act, 4), dpToPx(act, 8), dpToPx(act, 4));
    btn.setMinWidth(dpToPx(act, 0));
    btn.setMinHeight(dpToPx(act, 0));
    
    GradientDrawable bg = new GradientDrawable();
    bg.setColor(Color.parseColor(color));
    bg.setCornerRadius(dpToPx(act, TOOLBAR_CORNER_RADIUS));
    btn.setBackground(bg);
    btn.setTextColor(Color.WHITE);
    
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
    lp.setMargins(dpToPx(act, 3), 0, 0, 0);
    btn.setLayoutParams(lp);
    
    return btn;
}

private Button createCompactIconButton(Activity act, String icon, String color, float textSize) {
    Button btn = new Button(act);
    btn.setText(icon);
    btn.setTextSize(textSize);
    btn.setPadding(dpToPx(act, 8), dpToPx(act, 4), dpToPx(act, 8), dpToPx(act, 4));
    btn.setMinWidth(dpToPx(act, 0));
    btn.setMinHeight(dpToPx(act, 0));
    
    GradientDrawable bg = new GradientDrawable();
    bg.setColor(Color.parseColor(color));
    bg.setCornerRadius(dpToPx(act, TOOLBAR_CORNER_RADIUS));
    btn.setBackground(bg);
    btn.setTextColor(Color.WHITE);
    
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
    lp.setMargins(dpToPx(act, 4), 0, 0, 0);
    btn.setLayoutParams(lp);
    
    return btn;
}

private Button createNavButton(Activity act, String icon, String color) {
    Button btn = new Button(act);
    btn.setText(icon);
    btn.setTextSize(14);
    btn.setPadding(0, dpToPx(act, 10), 0, dpToPx(act, 10));
    
    GradientDrawable bg = new GradientDrawable();
    bg.setColor(Color.parseColor(color));
    bg.setCornerRadius(dpToPx(act, TOOLBAR_CORNER_RADIUS));
    btn.setBackground(bg);
    btn.setTextColor(Color.WHITE);
    
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, -2, 1);
    lp.setMargins(dpToPx(act, 4), 0, dpToPx(act, 4), 0);
    btn.setLayoutParams(lp);
    
    return btn;
}

private GradientDrawable createRoundRectDrawable(int color, float radius) {
    GradientDrawable drawable = new GradientDrawable();
    drawable.setColor(color);
    drawable.setCornerRadius(radius);
    return drawable;
}

private void switchToDesktopMode(WebSettings settings, boolean[] isDesktopMode, Button uaSwitchBtn) {
    settings.setUserAgentString(DESKTOP_UA);
    settings.setUseWideViewPort(true);
    settings.setLoadWithOverviewMode(true);
    isDesktopMode[0] = true;
    uaSwitchBtn.setText("💻");
}

private void switchToMobileMode(WebSettings settings, boolean[] isDesktopMode, Button uaSwitchBtn) {
    String mobileUA = settings.getUserAgentString();
    settings.setUserAgentString(mobileUA);
    settings.setUseWideViewPort(false);
    settings.setLoadWithOverviewMode(false);
    isDesktopMode[0] = false;
    uaSwitchBtn.setText("📱");
}

private boolean isDownloadUrl(String url) {
    if (url == null || url.isEmpty()) return false;
    
    if (shouldBypassDownloadCheck(url)) {
        return false;
    }
    
    String lowerUrl = url.toLowerCase();
    
    String[] downloadExtensions = {
        ".zip", ".rar", ".7z", ".tar", ".gz", ".bz2", ".xz",
        ".apk", ".exe", ".msi", ".dmg", ".pkg", ".deb", ".rpm",
        ".doc", ".docx", ".pdf", ".ppt", ".pptx", ".xls", ".xlsx",
        ".mp3", ".wav", ".flac", ".aac", ".mp4", ".avi", ".mkv", ".mov",
        ".iso", ".img", ".dmg", ".torrent"
    };
    
    String[] downloadKeywords = {
        "/download/", "download", "attachment", "?download=",
        "&download=", "force-download", "save-as"
    };
    
    for (String ext : downloadExtensions) {
        if (lowerUrl.contains(ext)) {
            return true;
        }
    }
    
    for (String keyword : downloadKeywords) {
        if (lowerUrl.contains(keyword)) {
            return true;
        }
    }
    
    if (lowerUrl.contains("content-disposition=attachment") || 
        lowerUrl.contains("response-content-disposition=attachment")) {
        return true;
    }
    
    return false;
}

private void showDownloadConfirmationDialog(final Activity act, final String url, final WebView webView) {
    Dialog downloadDialog = new Dialog(act);
    downloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    downloadDialog.setCancelable(true);
    downloadDialog.setCanceledOnTouchOutside(true);

    LinearLayout layout = new LinearLayout(act);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setPadding(dpToPx(act, 20), dpToPx(act, 20), dpToPx(act, 20), dpToPx(act, 15));
    
    GradientDrawable bg = new GradientDrawable();
    bg.setColor(Color.WHITE);
    bg.setCornerRadius(dpToPx(act, CORNER_RADIUS));
    bg.setStroke(dpToPx(act, 1), Color.parseColor(COLOR_BORDER));
    layout.setBackground(bg);

    DisplayMetrics metrics = new DisplayMetrics();
    act.getWindowManager().getDefaultDisplay().getMetrics(metrics);

    TextView title = new TextView(act);
    title.setText("下载确认");
    title.setTextSize(16);
    title.setTextColor(Color.parseColor(COLOR_DOWNLOAD));
    title.setTypeface(null, android.graphics.Typeface.BOLD);
    title.setGravity(Gravity.CENTER);
    LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(-1, -2);
    titleParams.setMargins(0, 0, 0, dpToPx(act, 12));
    title.setLayoutParams(titleParams);

    TextView downloadIcon = new TextView(act);
    downloadIcon.setText("⏬");
    downloadIcon.setTextSize(24);
    downloadIcon.setGravity(Gravity.CENTER);
    LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(-1, -2);
    iconParams.setMargins(0, 0, 0, dpToPx(act, 10));
    downloadIcon.setLayoutParams(iconParams);

    TextView message = new TextView(act);
    message.setText("检测到下载链接\n是否要下载此文件？");
    message.setTextSize(14);
    message.setTextColor(Color.parseColor(COLOR_TEXT_PRIMARY));
    message.setGravity(Gravity.CENTER);
    message.setLineSpacing(dpToPx(act, 4), 1.0f);
    LinearLayout.LayoutParams messageParams = new LinearLayout.LayoutParams(-1, -2);
    messageParams.setMargins(0, 0, 0, dpToPx(act, 15));
    message.setLayoutParams(messageParams);

    LinearLayout urlLayout = createInfoLayout(act, "🔗", 
        url.length() > 50 ? url.substring(0, 50) + "..." : url, 
        12, "#6C757D");
    LinearLayout.LayoutParams urlParams = new LinearLayout.LayoutParams(-1, -2);
    urlParams.setMargins(0, 0, 0, dpToPx(act, 15));
    urlLayout.setLayoutParams(urlParams);

    String fileName = extractFileNameFromUrl(url);
    LinearLayout fileLayout = createInfoLayout(act, "📄", 
        "文件名: " + fileName, 12, "#495057");

    View separator = createDialogSeparator(act);

    LinearLayout buttonLayout = new LinearLayout(act);
    buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
    buttonLayout.setGravity(Gravity.CENTER);
    LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(-1, -2);
    buttonLayout.setLayoutParams(buttonLayoutParams);

    Button cancelBtn = createEditDialogButton(act, "❌ 取消", "#95A5A6", new View.OnClickListener() {
        public void onClick(View v) {
            downloadDialog.dismiss();
        }
    });

    Button confirmBtn = createEditDialogButton(act, "✅ 下载", COLOR_DOWNLOAD, new View.OnClickListener() {
        public void onClick(View v) {
            downloadDialog.dismiss();
            String fileName = extractFileNameFromUrl(url);
            downloadFileWithNewAPI(act, url, fileName);
        }
    });

    Button browseBtn = createEditDialogButton(act, "🌐 浏览", COLOR_PRIMARY, new View.OnClickListener() {
        public void onClick(View v) {
            downloadDialog.dismiss();
            temporarilyBypassDownloadCheck(url);
            webView.loadUrl(url);
        }
    });

    LinearLayout.LayoutParams cancelParams = new LinearLayout.LayoutParams(0, -2, 1);
    cancelParams.setMargins(0, 0, dpToPx(act, 5), 0);
    cancelBtn.setLayoutParams(cancelParams);

    LinearLayout.LayoutParams confirmParams = new LinearLayout.LayoutParams(0, -2, 1);
    confirmParams.setMargins(dpToPx(act, 5), 0, dpToPx(act, 5), 0);
    confirmBtn.setLayoutParams(confirmParams);

    LinearLayout.LayoutParams browseParams = new LinearLayout.LayoutParams(0, -2, 1);
    browseParams.setMargins(dpToPx(act, 5), 0, 0, 0);
    browseBtn.setLayoutParams(browseParams);

    buttonLayout.addView(cancelBtn);
    buttonLayout.addView(confirmBtn);
    buttonLayout.addView(browseBtn);

    layout.addView(title);
    layout.addView(downloadIcon);
    layout.addView(message);
    layout.addView(urlLayout);
    layout.addView(fileLayout);
    layout.addView(separator);
    layout.addView(buttonLayout);

    downloadDialog.setContentView(layout);
    
    Window window = downloadDialog.getWindow();
    WindowManager.LayoutParams params = window.getAttributes();
    params.width = (int) (metrics.widthPixels * COMPACT_DIALOG_WIDTH_RATIO / 100.0);
    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    params.gravity = Gravity.CENTER;
    window.setBackgroundDrawableResource(android.R.color.transparent);
    window.setAttributes(params);
    
    downloadDialog.show();
}

private void showHomeMissingDialog(final Activity act) {
    Dialog dlg = new Dialog(act);
    dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

    LinearLayout layout = createDialogLayout(act);
    
    TextView icon = createDialogIcon(act, "📁", 32);
    TextView title = createDialogTitle(act, "主页文件缺失", COLOR_WARNING);
    TextView reason = createDialogText(act, "未找到本地主页 HTML 文件", 15, COLOR_TEXT_PRIMARY);
    LinearLayout pathLayout = createInfoLayout(act, "📂", LOCAL_HOME_PATH, 11, "#6C757D");
    LinearLayout tipsLayout = createTipsLayout(act, "💡", "请检查文件路径是否正确，或重新放置主页文件", 13, "#2C5282");
    View separator = createDialogSeparator(act);
    Button cancelBtn = createDialogButton(act, "❌ 取消", "#95A5A6", new View.OnClickListener() {
        public void onClick(View v) { dlg.dismiss(); }
    });

    layout.addView(icon);
    layout.addView(title);
    layout.addView(reason);
    layout.addView(pathLayout);
    layout.addView(tipsLayout);
    layout.addView(separator);
    layout.addView(cancelBtn);

    setupDialogDisplay(dlg, act, layout);
}

private void showBilibiliMobileWarning(final Activity act, String url) {
    Dialog dlg = new Dialog(act);
    dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

    LinearLayout layout = createDialogLayout(act);
    
    TextView icon = createDialogIcon(act, "📱", 32);
    TextView title = createDialogTitle(act, "B站移动端链接", "#FF6B35");
    LinearLayout urlLayout = createInfoLayout(act, "🔗", url, 12, "#6C757D");
    LinearLayout reasonLayout = createTextLayout(act, "💡", 
        "该链接为B站移动端页面，在当前模式下可能无法正常播放视频", 14, "#495057");
    LinearLayout tipsLayout = createTipsLayout(act, "🔧", 
        "点击底部工具栏的 📱/💻 按钮切换到合适的UA模式后再尝试访问", 13, "#2C5282");
    View separator = createDialogSeparator(act);
    Button cancelBtn = createDialogButton(act, "❌ 取消", "#95A5A6", new View.OnClickListener() { 
        public void onClick(View v) { dlg.dismiss(); } 
    });

    layout.addView(icon);
    layout.addView(title);
    layout.addView(urlLayout);
    layout.addView(reasonLayout);
    layout.addView(tipsLayout);
    layout.addView(separator);
    layout.addView(cancelBtn);

    setupDialogDisplay(dlg, act, layout);
}

private void showFriendlyErrorDialog(final Activity act, int errorCode, String description, String failingUrl) {
    String[] info = getFriendlyErrorInfo(errorCode, description, failingUrl);
    String titleText = info[0], reasonText = info[1], tipsText = info[2];

    Dialog dlg = new Dialog(act);
    dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

    LinearLayout layout = createDialogLayout(act);
    
    TextView icon = createDialogIcon(act, "🚫", 32);
    TextView title = createDialogTitle(act, titleText, "#E74C3C");
    
    LinearLayout urlLayout = null;
    if (failingUrl != null && !failingUrl.isEmpty()) {
        urlLayout = createInfoLayout(act, "🔗", failingUrl, 12, "#6C757D");
    }
    
    LinearLayout reasonLayout = createTextLayout(act, "💡", reasonText, 14, "#495057");
    LinearLayout tipsLayout = createTipsLayout(act, "🔧", tipsText, 13, "#2C5282");
    View separator = createDialogSeparator(act);
    Button cancelBtn = createDialogButton(act, "❌ 取消", "#95A5A6", new View.OnClickListener() { 
        public void onClick(View v) { dlg.dismiss(); } 
    });

    layout.addView(icon);
    layout.addView(title);
    
    if (urlLayout != null) {
        layout.addView(urlLayout);
    }
    
    layout.addView(reasonLayout);
    layout.addView(tipsLayout);
    layout.addView(separator);
    layout.addView(cancelBtn);

    setupDialogDisplay(dlg, act, layout);
}

private String[] getFriendlyErrorInfo(int code, String description, String url) {
    String lower = description == null ? "" : description.toLowerCase();
    String title = "网页无法打开";
    String reason = "页面加载过程中出现了问题，无法正常访问目标网站。";
    String tips = "建议您：\n• 检查网络连接状态\n• 刷新页面重试\n• 切换浏览器UA模式\n• 稍后再尝试访问";

    if (lower.contains("internet_disconnected") || lower.contains("disconnected") || code == -2) {
        title = "网络连接已断开";
        reason = "您的设备当前没有连接到互联网，或者网络信号不稳定导致连接中断。";
        tips = "请检查：\n• Wi-Fi或移动数据是否开启\n• 飞行模式是否关闭\n• 网络信号强度是否足够";
    } else if (lower.contains("dns") || lower.contains("name not resolved") || code == -105) {
        title = "DNS解析失败";
        reason = "无法找到目标网站对应的服务器地址，可能是域名错误或DNS服务异常。";
        tips = "可以尝试：\n• 检查网址拼写是否正确\n• 切换网络环境后重试\n• 等待DNS服务恢复";
    } else if (lower.contains("timeout") || lower.contains("timed out") || code == -8) {
        title = "连接超时";
        reason = "网站服务器响应时间过长，可能是网络拥堵或服务器繁忙。";
        tips = "建议您：\n• 检查网络连接质量\n• 稍等片刻后刷新页面\n• 尝试访问其他网站测试";
    } else if (lower.contains("ssl") || lower.contains("certificate") || code == -111) {
        title = "安全连接异常";
        reason = "与网站的安全证书验证失败，可能存在安全风险或证书配置问题。";
        tips = "请确认：\n• 系统日期时间是否正确\n• 网络环境是否安全可信\n• 是否为网站临时维护";
    } else if (lower.contains("forbidden") || lower.contains("blocked") || code == -102) {
        title = "访问被拒绝";
        reason = "网站服务器拒绝了您的访问请求，可能是IP被限制或需要特殊权限。";
        tips = "可以尝试：\n• 切换UA模式（底部📱/💻按钮）\n• 清除浏览器缓存\n• 使用其他网络访问";
    } else if (url != null && isBilibiliMobileUrl(url)) {
        title = "B站移动端链接限制";
        reason = "此链接为B站移动端专用页面，在当前浏览模式下可能无法正常播放视频内容。";
        tips = "解决方法：\n• 点击底部工具栏的📱/💻按钮\n• 切换到合适的UA模式\n• 重新加载页面";
    } else if (description != null && description.trim().length() > 0) {
        reason = "错误详情：" + description;
    }

    return new String[]{title, reason, tips};
}

private void downloadFileWithNewAPI(final Activity act, final String url, final String fileName) {
    if (!checkDownloadPathPermission()) {
        return;
    }
    
    new Thread(new Runnable() {
        public void run() {
            try {
                String downloadPath = appPath + "/Download/" + fileName;
                httpDownload(url, downloadPath);
                
                act.runOnUiThread(new Runnable() {
                    public void run() {
                    }
                });
                
            } catch (final Exception e) {
                act.runOnUiThread(new Runnable() {
                    public void run() {
                        enhancedErrorLog("Download", e);
                    }
                });
            }
        }
    }).start();
}

private boolean checkDownloadPathPermission() {
    try {
        String downloadDirPath = appPath + "/Download";
        File downloadDir = new File(downloadDirPath);
        
        if (!downloadDir.exists()) {
            if (!downloadDir.mkdirs()) {
                return false;
            }
        }
        
        File testFile = new File(downloadDir, "test_permission.tmp");
        if (testFile.createNewFile()) {
            testFile.delete();
            return true;
        }
        
        return false;
    } catch (Exception e) {
        enhancedErrorLog("PathPermission", e);
        return false;
    }
}

private void showCustomUrlDownloadDialog(final Activity act) {
    Dialog customDialog = new Dialog(act);
    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    customDialog.setCancelable(true);
    customDialog.setCanceledOnTouchOutside(true);

    LinearLayout mainLayout = new LinearLayout(act);
    mainLayout.setOrientation(LinearLayout.VERTICAL);
    mainLayout.setPadding(0, 0, 0, 0);
    
    GradientDrawable mainBg = new GradientDrawable();
    mainBg.setColor(Color.WHITE);
    mainBg.setCornerRadius(dpToPx(act, CORNER_RADIUS));
    mainLayout.setBackground(mainBg);

    DisplayMetrics metrics = new DisplayMetrics();
    act.getWindowManager().getDefaultDisplay().getMetrics(metrics);

    LinearLayout titleBar = new LinearLayout(act);
    titleBar.setOrientation(LinearLayout.HORIZONTAL);
    titleBar.setGravity(Gravity.CENTER_VERTICAL);
    titleBar.setPadding(dpToPx(act, 20), dpToPx(act, 15), dpToPx(act, 15), dpToPx(act, 15));
    GradientDrawable titleBg = new GradientDrawable();
    titleBg.setColor(Color.parseColor("#F0F8F0"));
    titleBg.setCornerRadii(new float[]{dpToPx(act, CORNER_RADIUS), dpToPx(act, CORNER_RADIUS), dpToPx(act, CORNER_RADIUS), dpToPx(act, CORNER_RADIUS), 0, 0, 0, 0});
    titleBar.setBackground(titleBg);
    
    LinearLayout titleContent = new LinearLayout(act);
    titleContent.setOrientation(LinearLayout.HORIZONTAL);
    titleContent.setGravity(Gravity.CENTER_VERTICAL);
    
    TextView downloadIcon = new TextView(act);
    downloadIcon.setText("🌐");
    downloadIcon.setTextSize(16);
    
    TextView titleText = new TextView(act);
    titleText.setText("自定义URL下载");
    titleText.setTextSize(16);
    titleText.setTextColor(Color.parseColor(COLOR_DOWNLOAD));
    titleText.setTypeface(null, android.graphics.Typeface.BOLD);
    titleText.setPadding(dpToPx(act, 8), 0, 0, 0);
    
    titleContent.addView(downloadIcon);
    titleContent.addView(titleText);
    LinearLayout.LayoutParams titleContentParams = new LinearLayout.LayoutParams(0, -2, 1);
    titleContent.setLayoutParams(titleContentParams);
    
    Button closeBtn = createCompactIconButton(act, "✕", COLOR_DOWNLOAD, 12);
    closeBtn.setOnClickListener(new View.OnClickListener() { 
        public void onClick(View v) { 
            customDialog.dismiss(); 
        } 
    });
    
    titleBar.addView(titleContent);
    titleBar.addView(closeBtn);

    LinearLayout contentLayout = createCustomUrlContentLayout(act);

    LinearLayout bottomToolbar = createCustomUrlBottomToolbar(act, customDialog);

    mainLayout.addView(titleBar);
    mainLayout.addView(contentLayout);
    mainLayout.addView(bottomToolbar);
    customDialog.setContentView(mainLayout);

    Window window = customDialog.getWindow();
    WindowManager.LayoutParams params = window.getAttributes();
    params.width = (int) (metrics.widthPixels * COMPACT_DIALOG_WIDTH_RATIO / 100.0);
    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    params.gravity = Gravity.CENTER;
    window.setBackgroundDrawableResource(android.R.color.transparent);
    window.setAttributes(params);
    
    customDialog.show();
}

private LinearLayout createCustomUrlContentLayout(final Activity act) {
    LinearLayout contentLayout = new LinearLayout(act);
    contentLayout.setOrientation(LinearLayout.VERTICAL);
    contentLayout.setPadding(dpToPx(act, 20), dpToPx(act, 20), dpToPx(act, 20), dpToPx(act, 15));
    LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(-1, -2);
    contentLayout.setLayoutParams(contentParams);

    final EditText urlInput = new EditText(act);
    urlInput.setHint("输入要下载的文件URL...");
    urlInput.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
    urlInput.setSingleLine(true);
    urlInput.setBackground(createRoundRectDrawable(Color.parseColor(COLOR_BG_LIGHTER), dpToPx(act, 8)));
    urlInput.setPadding(dpToPx(act, 12), dpToPx(act, 12), dpToPx(act, 12), dpToPx(act, 12));
    urlInput.setTextSize(14);
    urlInput.setTextColor(Color.parseColor(COLOR_TEXT_PRIMARY));
    urlInput.setHintTextColor(Color.parseColor(COLOR_TEXT_SECONDARY));
    
    LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(-1, -2);
    inputParams.setMargins(0, 0, 0, dpToPx(act, 10));
    urlInput.setLayoutParams(inputParams);

    final EditText fileNameInput = new EditText(act);
    fileNameInput.setHint("输入保存的文件名（可选）");
    fileNameInput.setInputType(InputType.TYPE_CLASS_TEXT);
    fileNameInput.setSingleLine(true);
    fileNameInput.setBackground(createRoundRectDrawable(Color.parseColor(COLOR_BG_LIGHTER), dpToPx(act, 8)));
    fileNameInput.setPadding(dpToPx(act, 12), dpToPx(act, 12), dpToPx(act, 12), dpToPx(act, 12));
    fileNameInput.setTextSize(14);
    fileNameInput.setTextColor(Color.parseColor(COLOR_TEXT_PRIMARY));
    fileNameInput.setHintTextColor(Color.parseColor(COLOR_TEXT_SECONDARY));
    
    LinearLayout.LayoutParams fileNameParams = new LinearLayout.LayoutParams(-1, -2);
    fileNameParams.setMargins(0, 0, 0, 0);
    fileNameInput.setLayoutParams(fileNameParams);

    contentLayout.addView(urlInput);
    contentLayout.addView(fileNameInput);
    
    return contentLayout;
}

private LinearLayout createCustomUrlBottomToolbar(final Activity act, final Dialog dialog) {
    LinearLayout bottomToolbar = new LinearLayout(act);
    bottomToolbar.setOrientation(LinearLayout.HORIZONTAL);
    bottomToolbar.setGravity(Gravity.CENTER);
    bottomToolbar.setPadding(dpToPx(act, 15), dpToPx(act, 12), dpToPx(act, 15), dpToPx(act, 12));
    
    GradientDrawable toolbarBg = new GradientDrawable();
    toolbarBg.setColor(Color.parseColor(COLOR_BG_LIGHTER));
    toolbarBg.setCornerRadii(new float[]{0, 0, 0, 0, dpToPx(act, CORNER_RADIUS), dpToPx(act, CORNER_RADIUS), 
                                       dpToPx(act, CORNER_RADIUS), dpToPx(act, CORNER_RADIUS)});
    toolbarBg.setStroke(dpToPx(act, 1), Color.parseColor(COLOR_BORDER));
    bottomToolbar.setBackground(toolbarBg);

    Button cancelBtn = new Button(act);
    cancelBtn.setText("❌ 取消");
    cancelBtn.setTextSize(14);
    cancelBtn.setPadding(dpToPx(act, 25), dpToPx(act, 12), dpToPx(act, 25), dpToPx(act, 12));
    cancelBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            dialog.dismiss();
        }
    });
    
    GradientDrawable cancelBtnBg = new GradientDrawable();
    cancelBtnBg.setColor(Color.parseColor("#95A5A6"));
    cancelBtnBg.setCornerRadius(dpToPx(act, TOOLBAR_CORNER_RADIUS));
    cancelBtn.setBackground(cancelBtnBg);
    cancelBtn.setTextColor(Color.WHITE);

    Button confirmBtn = new Button(act);
    confirmBtn.setText("✅ 开始下载");
    confirmBtn.setTextSize(14);
    confirmBtn.setPadding(dpToPx(act, 25), dpToPx(act, 12), dpToPx(act, 25), dpToPx(act, 12));
    confirmBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            EditText urlInput = (EditText) ((LinearLayout) dialog.findViewById(android.R.id.content))
                .getChildAt(1)
                .getChildAt(0);
            
            EditText fileNameInput = (EditText) ((LinearLayout) dialog.findViewById(android.R.id.content))
                .getChildAt(1)
                .getChildAt(1);
            
            String url = urlInput.getText().toString().trim();
            String fileName = fileNameInput.getText().toString().trim();
            
            if (url.isEmpty()) {
                return;
            }
            
            if (fileName.isEmpty()) {
                fileName = extractFileNameFromUrl(url);
            }
            
            dialog.dismiss();
            downloadFileWithNewAPI(act, url, fileName);
        }
    });
    
    GradientDrawable confirmBtnBg = new GradientDrawable();
    confirmBtnBg.setColor(Color.parseColor(COLOR_DOWNLOAD));
    confirmBtnBg.setCornerRadius(dpToPx(act, TOOLBAR_CORNER_RADIUS));
    confirmBtn.setBackground(confirmBtnBg);
    confirmBtn.setTextColor(Color.WHITE);
    
    LinearLayout.LayoutParams cancelParams = new LinearLayout.LayoutParams(0, -2, 1);
    cancelParams.setMargins(0, 0, dpToPx(act, 8), 0);
    cancelBtn.setLayoutParams(cancelParams);

    LinearLayout.LayoutParams confirmParams = new LinearLayout.LayoutParams(0, -2, 1);
    confirmParams.setMargins(dpToPx(act, 8), 0, 0, 0);
    confirmBtn.setLayoutParams(confirmParams);

    bottomToolbar.addView(cancelBtn);
    bottomToolbar.addView(confirmBtn);
    
    return bottomToolbar;
}

private String extractFileNameFromUrl(String url) {
    try {
        String cleanUrl = url.split("\\?")[0].split("#")[0];
        String[] parts = cleanUrl.split("/");
        String fileName = parts[parts.length - 1];
        
        if (fileName.isEmpty() || fileName.length() > 100 || !fileName.contains(".")) {
            String suggestedName = extractFileNameFromParams(url);
            if (!suggestedName.isEmpty()) {
                return suggestedName;
            }
            return "download_" + System.currentTimeMillis();
        }
        
        fileName = fileName.replaceAll("[^a-zA-Z0-9.-]", "_");
        
        return fileName;
    } catch (Exception e) {
        return "download_" + System.currentTimeMillis();
    }
}

private String extractFileNameFromParams(String url) {
    try {
        if (url.contains("filename=")) {
            String[] params = url.split("[?&]");
            for (String param : params) {
                if (param.startsWith("filename=")) {
                    String filename = param.substring(9);
                    filename = URLDecoder.decode(filename, "UTF-8");
                    return filename;
                }
            }
        }
        
        String[] nameParams = {"name=", "file=", "download="};
        for (String paramName : nameParams) {
            if (url.contains(paramName)) {
                String[] params = url.split("[?&]");
                for (String param : params) {
                    if (param.startsWith(paramName)) {
                        String filename = param.substring(paramName.length());
                        filename = URLDecoder.decode(filename, "UTF-8");
                        if (filename.contains(".")) {
                            return filename;
                        }
                    }
                }
            }
        }
    } catch (Exception e) {
    }
    return "";
}

private void showDownloadManagerDialog(final Activity act) {
    Dialog downloadDialog = new Dialog(act);
    downloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    downloadDialog.setCancelable(true);
    downloadDialog.setCanceledOnTouchOutside(true);

    LinearLayout mainLayout = new LinearLayout(act);
    mainLayout.setOrientation(LinearLayout.VERTICAL);
    mainLayout.setPadding(0, 0, 0, 0);
    GradientDrawable mainBg = new GradientDrawable();
    mainBg.setColor(Color.WHITE);
    mainBg.setCornerRadius(dpToPx(act, CORNER_RADIUS));
    mainLayout.setBackground(mainBg);

    LinearLayout titleBar = new LinearLayout(act);
    titleBar.setOrientation(LinearLayout.HORIZONTAL);
    titleBar.setGravity(Gravity.CENTER_VERTICAL);
    titleBar.setPadding(dpToPx(act, 20), dpToPx(act, 15), dpToPx(act, 15), dpToPx(act, 15));
    GradientDrawable titleBg = new GradientDrawable();
    titleBg.setColor(Color.parseColor("#F0F8F0"));
    titleBg.setCornerRadii(new float[]{dpToPx(act, CORNER_RADIUS), dpToPx(act, CORNER_RADIUS), dpToPx(act, CORNER_RADIUS), dpToPx(act, CORNER_RADIUS), 0, 0, 0, 0});
    titleBar.setBackground(titleBg);
    
    LinearLayout titleContent = new LinearLayout(act);
    titleContent.setOrientation(LinearLayout.HORIZONTAL);
    titleContent.setGravity(Gravity.CENTER_VERTICAL);
    
    TextView downloadIcon = new TextView(act);
    downloadIcon.setText("📥");
    downloadIcon.setTextSize(16);
    
    TextView titleText = new TextView(act);
    titleText.setText("下载管理");
    titleText.setTextSize(16);
    titleText.setTextColor(Color.parseColor(COLOR_DOWNLOAD));
    titleText.setTypeface(null, android.graphics.Typeface.BOLD);
    titleText.setPadding(dpToPx(act, 8), 0, 0, 0);
    
    titleContent.addView(downloadIcon);
    titleContent.addView(titleText);
    LinearLayout.LayoutParams titleContentParams = new LinearLayout.LayoutParams(0, -2, 1);
    titleContent.setLayoutParams(titleContentParams);
    
    Button closeBtn = createCompactIconButton(act, "✕", COLOR_DOWNLOAD, 12);
    closeBtn.setOnClickListener(new View.OnClickListener() { 
        public void onClick(View v) { 
            downloadDialog.dismiss(); 
        } 
    });
    
    titleBar.addView(titleContent);
    titleBar.addView(closeBtn);

    LinearLayout contentLayout = createDownloadContentLayout(act);

    LinearLayout bottomToolbar = createDownloadBottomToolbar(act, downloadDialog);

    mainLayout.addView(titleBar);
    mainLayout.addView(contentLayout);
    mainLayout.addView(bottomToolbar);
    downloadDialog.setContentView(mainLayout);

    Window window = downloadDialog.getWindow();
    WindowManager.LayoutParams params = window.getAttributes();
    DisplayMetrics metrics = new DisplayMetrics();
    act.getWindowManager().getDefaultDisplay().getMetrics(metrics);
    params.width = (int) (metrics.widthPixels * COMPACT_DIALOG_WIDTH_RATIO / 100.0);
    params.height = (int) (metrics.heightPixels * COMPACT_DIALOG_HEIGHT_RATIO / 100.0);
    params.gravity = Gravity.CENTER;
    window.setBackgroundDrawableResource(android.R.color.transparent);
    window.setAttributes(params);
    window.setWindowAnimations(android.R.style.Animation_Dialog);

    downloadDialog.show();
}

private LinearLayout createDownloadContentLayout(final Activity act) {
    LinearLayout contentLayout = new LinearLayout(act);
    contentLayout.setOrientation(LinearLayout.VERTICAL);
    contentLayout.setGravity(Gravity.CENTER);
    contentLayout.setPadding(dpToPx(act, 30), dpToPx(act, 40), dpToPx(act, 30), dpToPx(act, 30));
    LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(-1, 0, 1);
    contentLayout.setLayoutParams(contentParams);

    TextView downloadIcon = new TextView(act);
    downloadIcon.setText("⏬");
    downloadIcon.setTextSize(48);
    downloadIcon.setGravity(Gravity.CENTER);
    LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(-1, -2);
    iconParams.setMargins(0, 0, 0, dpToPx(act, 15));
    downloadIcon.setLayoutParams(iconParams);

    TextView title = new TextView(act);
    title.setText("下载管理");
    title.setTextSize(18);
    title.setTextColor(Color.parseColor(COLOR_DOWNLOAD));
    title.setTypeface(null, android.graphics.Typeface.BOLD);
    title.setGravity(Gravity.CENTER);
    LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(-1, -2);
    titleParams.setMargins(0, 0, 0, dpToPx(act, 8));
    title.setLayoutParams(titleParams);

    TextView desc = new TextView(act);
    desc.setText("支持自定义URL下载\n目前下载路径为\n［/storage/emulated/0/Android/data/com.tencent.mobileqq/QStory/Plugin/PureCard Web/Download/］\n可通过文件管理访问\n暂不支持脚本访问");
    desc.setTextSize(14);
    desc.setTextColor(Color.parseColor("#7D7D7D"));
    desc.setGravity(Gravity.CENTER);
    desc.setLineSpacing(dpToPx(act, 4), 1.0f);
    LinearLayout.LayoutParams descParams = new LinearLayout.LayoutParams(-1, -2);
    descParams.setMargins(0, 0, 0, dpToPx(act, 0));
    desc.setLayoutParams(descParams);

    contentLayout.addView(downloadIcon);
    contentLayout.addView(title);
    contentLayout.addView(desc);
    
    return contentLayout;
}

private LinearLayout createDownloadBottomToolbar(final Activity act, final Dialog dialog) {
    LinearLayout bottomToolbar = new LinearLayout(act);
    bottomToolbar.setOrientation(LinearLayout.HORIZONTAL);
    bottomToolbar.setGravity(Gravity.CENTER);
    bottomToolbar.setPadding(dpToPx(act, 15), dpToPx(act, 12), dpToPx(act, 15), dpToPx(act, 12));
    
    GradientDrawable toolbarBg = new GradientDrawable();
    toolbarBg.setColor(Color.parseColor(COLOR_BG_LIGHTER));
    toolbarBg.setCornerRadii(new float[]{0, 0, 0, 0, dpToPx(act, CORNER_RADIUS), dpToPx(act, CORNER_RADIUS), 
                                       dpToPx(act, CORNER_RADIUS), dpToPx(act, CORNER_RADIUS)});
    toolbarBg.setStroke(dpToPx(act, 1), Color.parseColor(COLOR_BORDER));
    bottomToolbar.setBackground(toolbarBg);

    Button customDownloadBtn = new Button(act);
    customDownloadBtn.setText("🌐 自定义URL下载");
    customDownloadBtn.setTextSize(14);
    customDownloadBtn.setPadding(dpToPx(act, 25), dpToPx(act, 12), dpToPx(act, 25), dpToPx(act, 12));
    customDownloadBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            dialog.dismiss();
            showCustomUrlDownloadDialog(act);
        }
    });
    
    GradientDrawable customBtnBg = new GradientDrawable();
    customBtnBg.setColor(Color.parseColor(COLOR_DOWNLOAD));
    customBtnBg.setCornerRadius(dpToPx(act, TOOLBAR_CORNER_RADIUS));
    customDownloadBtn.setBackground(customBtnBg);
    customDownloadBtn.setTextColor(Color.WHITE);
    
    LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(-1, -2);
    btnParams.setMargins(dpToPx(act, 10), 0, dpToPx(act, 10), 0);
    customDownloadBtn.setLayoutParams(btnParams);

    bottomToolbar.addView(customDownloadBtn);
    
    return bottomToolbar;
}

private LinearLayout createDialogLayout(Activity act) {
    LinearLayout layout = new LinearLayout(act);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setPadding(dpToPx(act, 25), dpToPx(act, 25), dpToPx(act, 25), dpToPx(act, 20));
    
    GradientDrawable bg = new GradientDrawable();
    bg.setColor(Color.WHITE);
    bg.setCornerRadius(dpToPx(act, CORNER_RADIUS));
    bg.setStroke(dpToPx(act, 1), Color.parseColor("#E8F4FD"));
    layout.setBackground(bg);
    
    return layout;
}

private TextView createDialogIcon(Activity act, String icon, int size) {
    TextView iconView = new TextView(act);
    iconView.setText(icon);
    iconView.setTextSize(size);
    iconView.setGravity(Gravity.CENTER);
    LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(-1, -2);
    iconParams.setMargins(0, 0, 0, dpToPx(act, 12));
    iconView.setLayoutParams(iconParams);
    return iconView;
}

private TextView createDialogTitle(Activity act, String text, String color) {
    TextView title = new TextView(act);
    title.setText(text);
    title.setTextSize(18);
    title.setTextColor(Color.parseColor(color));
    title.setTypeface(null, android.graphics.Typeface.BOLD);
    title.setGravity(Gravity.CENTER);
    LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(-1, -2);
    titleParams.setMargins(0, 0, 0, dpToPx(act, 10));
    title.setLayoutParams(titleParams);
    return title;
}

private TextView createDialogText(Activity act, String text, int size, String color) {
    TextView textView = new TextView(act);
    textView.setText(text);
    textView.setTextSize(size);
    textView.setTextColor(Color.parseColor(color));
    textView.setGravity(Gravity.CENTER);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
    params.setMargins(0, 0, 0, dpToPx(act, 15));
    textView.setLayoutParams(params);
    return textView;
}

private LinearLayout createInfoLayout(Activity act, String icon, String text, int textSize, String textColor) {
    LinearLayout layout = new LinearLayout(act);
    layout.setOrientation(LinearLayout.HORIZONTAL);
    layout.setGravity(Gravity.CENTER_VERTICAL);
    layout.setPadding(dpToPx(act, 15), dpToPx(act, 12), dpToPx(act, 15), dpToPx(act, 12));
    layout.setBackground(createRoundRectDrawable(Color.parseColor("#F8F9FA"), dpToPx(act, 10)));
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
    params.setMargins(0, 0, 0, dpToPx(act, 12));
    layout.setLayoutParams(params);
    
    TextView iconView = new TextView(act);
    iconView.setText(icon);
    iconView.setTextSize(14);
    iconView.setPadding(0, 0, dpToPx(act, 8), 0);
    
    TextView textView = new TextView(act);
    textView.setText(text);
    textView.setTextSize(textSize);
    textView.setTextColor(Color.parseColor(textColor));
    textView.setSingleLine(true);
    textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
    textView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1));
    
    layout.addView(iconView);
    layout.addView(textView);
    
    return layout;
}

private LinearLayout createTextLayout(Activity act, String icon, String text, int textSize, String textColor) {
    LinearLayout layout = new LinearLayout(act);
    layout.setOrientation(LinearLayout.HORIZONTAL);
    layout.setGravity(Gravity.TOP);
    layout.setPadding(0, 0, 0, dpToPx(act, 12));
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
    params.setMargins(0, 0, 0, dpToPx(act, 12));
    layout.setLayoutParams(params);
    
    TextView iconView = new TextView(act);
    iconView.setText(icon);
    iconView.setTextSize(14);
    iconView.setPadding(0, dpToPx(act, 2), dpToPx(act, 10), 0);
    
    TextView textView = new TextView(act);
    textView.setText(text);
    textView.setTextSize(textSize);
    textView.setTextColor(Color.parseColor(textColor));
    textView.setLineSpacing(dpToPx(act, 3), 1.0f);
    textView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1));
    
    layout.addView(iconView);
    layout.addView(textView);
    
    return layout;
}

private LinearLayout createTipsLayout(Activity act, String icon, String text, int textSize, String textColor) {
    LinearLayout layout = new LinearLayout(act);
    layout.setOrientation(LinearLayout.HORIZONTAL);
    layout.setGravity(Gravity.TOP);
    layout.setBackground(createRoundRectDrawable(Color.parseColor("#E8F4FD"), dpToPx(act, 10)));
    layout.setPadding(dpToPx(act, 15), dpToPx(act, 12), dpToPx(act, 15), dpToPx(act, 12));
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
    params.setMargins(0, 0, 0, dpToPx(act, 20));
    layout.setLayoutParams(params);
    
    TextView iconView = new TextView(act);
    iconView.setText(icon);
    iconView.setTextSize(14);
    iconView.setPadding(0, dpToPx(act, 2), dpToPx(act, 10), 0);
    
    TextView textView = new TextView(act);
    textView.setText(text);
    textView.setTextSize(textSize);
    textView.setTextColor(Color.parseColor(textColor));
    textView.setLineSpacing(dpToPx(act, 3), 1.0f);
    textView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1));
    
    layout.addView(iconView);
    layout.addView(textView);
    
    return layout;
}

private View createDialogSeparator(Activity act) {
    View separator = new View(act);
    separator.setLayoutParams(new LinearLayout.LayoutParams(-1, dpToPx(act, 1)));
    separator.setBackgroundColor(Color.parseColor("#E8F4FD"));
    LinearLayout.LayoutParams separatorParams = new LinearLayout.LayoutParams(-1, dpToPx(act, 1));
    separatorParams.setMargins(0, 0, 0, dpToPx(act, 15));
    separator.setLayoutParams(separatorParams);
    return separator;
}

private Button createDialogButton(Activity act, String text, String color, View.OnClickListener listener) {
    Button btn = new Button(act);
    btn.setText(text);
    btn.setTextSize(14);
    btn.setPadding(dpToPx(act, 35), dpToPx(act, 12), dpToPx(act, 35), dpToPx(act, 12));
    btn.setMinWidth(dpToPx(act, 120));
    
    GradientDrawable bg = new GradientDrawable();
    bg.setColor(Color.parseColor(color));
    bg.setCornerRadius(dpToPx(act, 20));
    btn.setBackground(bg);
    btn.setTextColor(Color.WHITE);
    btn.setOnClickListener(listener);
    
    return btn;
}

private Button createEditDialogButton(Activity act, String text, String color, View.OnClickListener listener) {
    Button btn = new Button(act);
    btn.setText(text);
    btn.setTextSize(14);
    btn.setPadding(dpToPx(act, 20), dpToPx(act, 10), dpToPx(act, 20), dpToPx(act, 10));
    btn.setMinWidth(dpToPx(act, 0));
    
    GradientDrawable bg = new GradientDrawable();
    bg.setColor(Color.parseColor(color));
    bg.setCornerRadius(dpToPx(act, 20));
    btn.setBackground(bg);
    btn.setTextColor(Color.WHITE);
    btn.setOnClickListener(listener);
    
    return btn;
}

private void setupDialogDisplay(Dialog dlg, Activity act, LinearLayout layout) {
    dlg.setContentView(layout);
    Window window = dlg.getWindow();
    WindowManager.LayoutParams params = window.getAttributes();
    DisplayMetrics metrics = new DisplayMetrics();
    act.getWindowManager().getDefaultDisplay().getMetrics(metrics);
    
    params.width = (int) (metrics.widthPixels * 0.84);
    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    params.gravity = Gravity.CENTER;
    window.setBackgroundDrawableResource(android.R.color.transparent);
    window.setElevation(dpToPx(act, 8));
    window.setAttributes(params);
    
    try { 
        dlg.show(); 
    } catch (Exception e) { 
    }
}

private void loadUrlDirectly(String url, WebView webView, EditText urlInput) {
    if (url.isEmpty()) {
        return;
    }
    if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("file://")) {
        if (url.contains(".") && !url.contains(" ")) {
            url = "https://" + url;
        } else {
            url = BING_SEARCH_URL + safeEncode(url);
        }
    }
    webView.loadUrl(url);
    if (urlInput != null) urlInput.setText(url);
}

private boolean isBilibiliMobileUrl(String url) {
    if (url == null || url.isEmpty()) return false;
    String lowerUrl = url.toLowerCase();
    boolean isBilibiliMobile =
        lowerUrl.startsWith("https://m.bilibili.com/") ||
        lowerUrl.startsWith("http://m.bilibili.com/") ||
        lowerUrl.startsWith("bilibili://") ||
        (lowerUrl.contains("bilibili.com") && !lowerUrl.startsWith("https://www.bilibili.com")) ||
        lowerUrl.contains("b23.tv") ||
        lowerUrl.contains("bili22.cn") ||
        lowerUrl.contains("bili33.cn") ||
        lowerUrl.contains("bilibili.com/video/") ||
        lowerUrl.contains("bilibili.com/page/");
    return isBilibiliMobile;
}

private void showUrlEditDialog(final Activity act, String currentUrl, final WebView webView, final EditText urlInput) {
    Dialog editDialog = new Dialog(act);
    editDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    editDialog.setCancelable(true);
    editDialog.setCanceledOnTouchOutside(true);

    LinearLayout layout = new LinearLayout(act);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setPadding(dpToPx(act, 20), dpToPx(act, 20), dpToPx(act, 20), dpToPx(act, 15));
    
    GradientDrawable bg = new GradientDrawable();
    bg.setColor(Color.WHITE);
    bg.setCornerRadius(dpToPx(act, CORNER_RADIUS));
    bg.setStroke(dpToPx(act, 1), Color.parseColor(COLOR_BORDER));
    layout.setBackground(bg);

    DisplayMetrics metrics = new DisplayMetrics();
    act.getWindowManager().getDefaultDisplay().getMetrics(metrics);

    TextView title = new TextView(act);
    title.setText("编辑URL");
    title.setTextSize(16);
    title.setTextColor(Color.parseColor(COLOR_PRIMARY));
    title.setTypeface(null, android.graphics.Typeface.BOLD);
    title.setGravity(Gravity.CENTER);
    LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(-1, -2);
    titleParams.setMargins(0, 0, 0, dpToPx(act, 12));
    title.setLayoutParams(titleParams);

    final EditText editInput = new EditText(act);
    editInput.setText(currentUrl);
    editInput.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
    editInput.setSingleLine(true);
    editInput.setBackground(createRoundRectDrawable(Color.parseColor(COLOR_BG_LIGHTER), dpToPx(act, 8)));
    editInput.setPadding(dpToPx(act, 12), dpToPx(act, 12), dpToPx(act, 12), dpToPx(act, 12));
    editInput.setTextSize(14);
    editInput.setTextColor(Color.parseColor(COLOR_TEXT_PRIMARY));
    LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(-1, -2);
    inputParams.setMargins(0, 0, 0, dpToPx(act, 15));
    editInput.setLayoutParams(inputParams);

    View separator = createDialogSeparator(act);

    LinearLayout buttonLayout = new LinearLayout(act);
    buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
    buttonLayout.setGravity(Gravity.CENTER);
    LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(-1, -2);
    buttonLayout.setLayoutParams(buttonLayoutParams);

    Button cancelBtn = createEditDialogButton(act, "❌ 取消", "#95A5A6", new View.OnClickListener() {
        public void onClick(View v) {
            editDialog.dismiss();
        }
    });

    Button confirmBtn = createEditDialogButton(act, "✅ 确认", COLOR_PRIMARY, new View.OnClickListener() {
        public void onClick(View v) {
            String newUrl = editInput.getText().toString().trim();
            editDialog.dismiss();
            if (urlInput != null) {
                urlInput.setText(newUrl);
            }
            loadUrlDirectly(newUrl, webView, urlInput);
        }
    });

    LinearLayout.LayoutParams cancelParams = new LinearLayout.LayoutParams(0, -2, 1);
    cancelParams.setMargins(0, 0, dpToPx(act, 8), 0);
    cancelBtn.setLayoutParams(cancelParams);

    LinearLayout.LayoutParams confirmParams = new LinearLayout.LayoutParams(0, -2, 1);
    confirmParams.setMargins(dpToPx(act, 8), 0, 0, 0);
    confirmBtn.setLayoutParams(confirmParams);

    buttonLayout.addView(cancelBtn);
    buttonLayout.addView(confirmBtn);

    layout.addView(title);
    layout.addView(editInput);
    layout.addView(separator);
    layout.addView(buttonLayout);

    editDialog.setContentView(layout);
    
    Window window = editDialog.getWindow();
    WindowManager.LayoutParams params = window.getAttributes();
    params.width = (int) (metrics.widthPixels * COMPACT_DIALOG_WIDTH_RATIO / 100.0);
    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    params.gravity = Gravity.CENTER;
    window.setBackgroundDrawableResource(android.R.color.transparent);
    window.setAttributes(params);
    
    editDialog.show();
}

private int dpToPx(Context context, float dp) {
    float density = context.getResources().getDisplayMetrics().density;
    return Math.round(dp * density);
}

private String safeEncode(String s) {
    try { 
        return URLEncoder.encode(s == null ? "" : s, "UTF-8"); 
    } catch (Exception e) { 
        return ""; 
    }
}

private String extractUrlFromMessage(String content) {
    if (content == null || content.isEmpty()) return null;

    String urlPattern = "(https?://[^\\s]+)";
    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(urlPattern);
    java.util.regex.Matcher matcher = pattern.matcher(content);
    if (matcher.find()) return matcher.group(1);

    String domainPattern = "(www\\.[^\\s]+\\.[a-z]{2,})|([^\\s]+\\.[a-z]{2,}/[^\\s]*)";
    pattern = java.util.regex.Pattern.compile(domainPattern);
    matcher = pattern.matcher(content);
    if (matcher.find()) {
        String domain = matcher.group();
        if (!domain.startsWith("http")) return "https://" + domain;
    }
    
    return null;
}

private void enhancedErrorLog(String tag, Exception e) {
    error(e);
}

public void cleanup() {
    removeEdgeTrigger();
    if (browserDialog != null && browserDialog.isShowing()) {
        browserDialog.dismiss();
    }
}

void init() {
}

init();
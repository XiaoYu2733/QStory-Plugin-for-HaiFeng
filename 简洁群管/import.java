
// 一些用不到的 仅做保存
import android.view.*;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.regex.*;
import java.lang.reflect.*;
import android.os.Bundle;
import android.os.Environment;
import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.view.WindowManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.graphics.PixelFormat;
import android.widget.SeekBar;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.renderscript.*;
import android.text.TextWatcher;
import android.text.Editable;
import android.telephony.TelephonyManager;
import android.net.wifi.WifiManager;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.app.ActivityManager;
import android.text.format.Formatter;
import android.webkit.WebView;
import androidx.fragment.app.Fragment;

import mqq.app.AppService;
import mqq.manager.TicketManager;
import oicq.wlogin_sdk.request.WtloginHelper;

import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.util.zip.*;
import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;
import dalvik.system.DexClassLoader;

import oicq.wlogin_sdk.request.*;
import android.app.Application;
import android.app.ProgressDialog;
import android.app.ProgressBar;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.content.res.Resources;
import android.os.Build;
import android.view.Window;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.text.method.ScrollingMovementMethod;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.renderscript.RenderScript;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.ScriptIntrinsicBlur;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.URI;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.util.zip.Inflater;
import java.util.zip.Deflater;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.text.DateFormat;
import java.text.ParseException;
import java.nio.charset.StandardCharsets;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import androidx.annotation.Keep;

private Dialog dlg;
private String cGroup;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Environment;

import android.app.Application;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.webkit.WebView;
import android.app.ActivityManager;


import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TimePicker;
import android.widget.VideoView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.content.DialogInterface.OnMultiChoiceClickListener;

import android.util.Log;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import android.view.View;
import android.view.ViewGroup;
import android.view.Gravity;

import android.graphics.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ClassLoader;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.CharSequence;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import java.io.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.net.Socket;
import android.net.ConnectivityManager;

import dalvik.system.DexClassLoader;

import com.tencent.mobileqq.imcore.IMCore;

import java.io.InputStream; 
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.*;

import java.util.Calendar;

import com.tencent.mobileqq.emoticon.QQEmojiUtil;
import com.tencent.mobileqq.emoticon.QQSysFaceUtil;

import java.lang.*;
import java.net.*;

import java.io.File;
import java.util.*;
import android.content.*;
import android.net.wifi.Wifi;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Environment;
import android.os.StatFs;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import java.util.concurrent.TimeUnit;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.text.format.Formatter;
import android.app.Activity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.DecimalFormat;
import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.*;

import com.tencent.mobileqq.app.FrameHelperActivity;
import com.tencent.mobileqq.app.BaseActivity;
import com.tencent.mobileqq.activity.home.Conversation;
import com.tencent.mobileqq.utils.DialogUtil;
import android.app.Dialog;
import com.tencent.common.app.BaseApplicationImpl;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.graphics.Color;
import android.content.DialogInterface;
import android.app.Activity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.DialogFragment;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;
import org.json.*;
import android.widget.Toast;
import java.io.*; 
import java.net.*;
import java.lang.*;
import android.view.Window;
import android.app.Activity;
import android.widget.LinearLayout;
import android.app.Dialog;
import android.webkit.*;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import org.json.JSONArray;
import org.json.JSONObject;
HashMap FileMemCache = new HashMap();
import android.app.*;
import android.widget.*;
import android.content.*;
import android.text.*;
import android.os.Environment;
import com.tencent.mobileqq.app.BusinessHandlerFactory;
import com.tencent.common.app.BaseApplicationImpl;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
//----------------------------卑微萌新
import android.app.Activity;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.graphics.*;
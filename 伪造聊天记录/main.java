load(appPath + "/库/import.java");

addItem("构造聊天记录", "tiaoshi");
addItem("说明", "ceshikuang");

int type;
String qq = "";
String groupuin = "";
JSONArray datamasge = new JSONArray();

public void ceshikuang(String groupUin, String uin, int chatType) {
    showNativeDialog("说明","注意：填写聊天对象构建消息体在写完“QQ”，“内容”，“类型”之后，需要点击添加对象，添加到“消息体显示框”里面展示出才算有效计入内容\n\n构造聊天记录\n\n构造卡片：\n\n聊天记录卡片的外显，标题，副标题（中间部分），最下面小字，（中间部分只支持添加显示一行，多行以及特殊符号会发生未知错误。）\n\n聊天记录内容：QQ号，消息，消息类型（“wz”：文字，“ark”：卡片，“md”：Markdown）\n\n“消息体显示”为输入框，可粘贴结构体进行快速构建\n\n构造成功发送的卡片自己不可见，其他人正常打开。\n\n如有疑问可进入qs官方群\n\n临江QQ群：634941583");
}

public void tiaoshi(String groupUin, String uin, int chatType) {
    type = chatType;
    qq = uin;
    groupuin = groupUin;
    showCustomDialog();
}

public void clearDatamasge() {
    datamasge = new JSONArray();
}

public void showCustomDialog() {
    final Activity activity = getActivity();
    if (activity == null) return;

    activity.runOnUiThread(new Runnable() {
        public void run() {
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(30, 20, 30, 20);
            
            LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
            );
            contentParams.setMargins(16, 16, 16, 16); 
            
            LinearLayout.LayoutParams ctextParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
            );
            ctextParams.setMargins(16, 0, 16, 0); 

            // 外显
            TextView tishi = new TextView(activity);
            tishi.setText("聊天记录卡片外显");
            tishi.setTextColor(Color.GRAY);
            tishi.setLayoutParams(ctextParams);
            layout.addView(tishi);
            
            final EditText extField = new EditText(activity);
            extField.setText("聊天记录");
            extField.setPadding(10, 16, 10, 16);
            extField.setLayoutParams(contentParams);
            layout.addView(extField);

            // 标题
            TextView bt = new TextView(activity);
            bt.setText("聊天记录卡片标题");
            bt.setLayoutParams(ctextParams);
            bt.setTextColor(Color.GRAY);
            layout.addView(bt);
            
            final EditText titleField = new EditText(activity);
            titleField.setText("群聊的聊天记录");
            titleField.setPadding(10, 16, 10, 16);
            titleField.setLayoutParams(contentParams);
            layout.addView(titleField);

            // 小字
            TextView xz = new TextView(activity);
            xz.setText("聊天记录卡片下面的小字");
            xz.setLayoutParams(ctextParams);
            xz.setTextColor(Color.GRAY);
            layout.addView(xz);
            
            final EditText descField = new EditText(activity);
            descField.setText("聊天记录");
            descField.setPadding(10, 16, 10, 16);
            descField.setLayoutParams(contentParams);
            layout.addView(descField);

            // 中间显示
            TextView zj = new TextView(activity);
            zj.setText("聊天记录卡片中间显示");
            zj.setLayoutParams(ctextParams);
            zj.setTextColor(Color.GRAY);
            layout.addView(zj);
            
            final EditText contentField = new EditText(activity);
            contentField.setText("...");
            contentField.setPadding(10, 16, 10, 16);
            contentField.setLayoutParams(contentParams);

            layout.addView(contentField);
            LinearLayout.LayoutParams neirong = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
             150, 1f);
            neirong.setMargins(16, 16, 16, 16);
            
            
            final EditText messageContent = new EditText(activity);
            messageContent.setHint("消息体显示");
            messageContent.setLayoutParams(neirong);    
            messageContent.setBackgroundColor(Color.LTGRAY);
            messageContent.setTextColor(Color.BLACK);
            messageContent.setTextSize(12);
            messageContent.setSingleLine(false);
            messageContent.setLines(5);
            layout.addView(messageContent);

            // 消息体输入
            final EditText qqField = new EditText(activity);
            qqField.setHint("QQ号");
            qqField.setPadding(10, 16, 10, 16);
            qqField.setLayoutParams(contentParams);
            layout.addView(qqField);
            
            final EditText nrField = new EditText(activity);
            nrField.setPadding(10, 16, 10, 0);
            nrField.setHint("内容");
            
            nrField.setLayoutParams(neirong);    
            layout.addView(nrField);
            
            TextView contentLabel = new TextView(activity);
            contentLabel.setText("消息内容，消息类型可填入'wz'（文字）'ark'（卡片）'md'Markdown");
            contentLabel.setTextColor(Color.GRAY);
            contentLabel.setLayoutParams(contentParams);
            layout.addView(contentLabel);

                                
            final EditText typeField = new EditText(activity);
            typeField.setHint("消息类型");
            typeField.setPadding(10, 0, 10, 16);
            typeField.setLayoutParams(contentParams);
            typeField.setText("wz");
            layout.addView(typeField);

            

            Button addBtn = new Button(activity);
            LinearLayout.LayoutParams addParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            addParams.setMargins(10, 16, 10, 16);
            addBtn.setLayoutParams(addParams);
            addBtn.setText("添加对象");
            layout.addView(addBtn);

            Button cancelBtn = new Button(activity);
            cancelBtn.setText("取消");
            LinearLayout.LayoutParams cancelParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            cancelParams.weight = 1;
            cancelParams.setMargins(16, 16, 8, 16);
            cancelBtn.setLayoutParams(cancelParams);
            
            Button confirmBtn = new Button(activity);
            confirmBtn.setText("确定");
            LinearLayout.LayoutParams okParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            okParams.weight = 1;
            okParams.setMargins(16, 16, 8, 16);
            confirmBtn.setLayoutParams(okParams);

            LinearLayout buttonLayout = new LinearLayout(activity);
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
            buttonLayout.addView(cancelBtn);
            buttonLayout.addView(confirmBtn);
            layout.addView(buttonLayout);

            final AlertDialog dialog = new AlertDialog.Builder(activity,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                    .setTitle("自定义聊天消息")
                    .setView(layout)
                    .create();

            final int[] jishi = {1};
            addBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String qqStr = qqField.getText().toString().trim();
                    String nrStr = nrField.getText().toString().trim();
                    String typeStr = typeField.getText().toString().trim();

                    if (qqStr.isEmpty() || nrStr.isEmpty() || typeStr.isEmpty()) {
                        Toast.makeText(activity, "请填写完整消息信息", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    
                    String liex = "";
                    if ("wz".equals(typeStr)) liex = "文本";
                    else if ("ark".equals(typeStr)) liex = "卡片";
                    else if ("md".equals(typeStr)) liex = "Markdown";
                    
                    String newItem = jishi[0] + ".QQ号: " + qqStr + "，内容： " + nrStr + "，类型： " + liex + "\n";

                    String current = messageContent.getText().toString();
                    try {
                        JSONObject set = new JSONObject();
                        set.put("qq", qqStr);
                        set.put("nr", nrStr);
                        set.put("type", typeStr);
                        datamasge.put(set);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    messageContent.setText(current + newItem);
                    qqField.setText("");
                    nrField.setText("");
                    jishi[0] += 1;
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    clearDatamasge();
                    jishi[0] = 1;
                    dialog.dismiss();
                }
            });

            confirmBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    jishi[0] = 1;
                    dialog.dismiss();
                    if (datamasge != null && datamasge.length() > 0) {
                                        new Thread(new Runnable() {
                        public void run() {
                            try {
                                String jsonString = generateJson(
                                    extField.getText().toString(),
                                    titleField.getText().toString(),
                                    descField.getText().toString(),
                                    contentField.getText().toString(),
                                    datamasge
                                );

                                String url = "https://xiaodi.jujukai.cn/Api/ltwz.php";
                                HashMap headers = new HashMap();
                                headers.put("Content-Type", "application/json;charset=UTF-8");
                                
                                String response = httpRequest(url, jsonString, headers, "POST");
                                sendCard(groupuin, qq, response);
                                clearDatamasge();
                                
                            } catch (Exception e) {
                                log("发送异常: " + e.getMessage());
                            }
                        }
                    }).start();
                    } else {
                    Toast.makeText(activity, "消息内容为空，请填写完整消息信息", Toast.LENGTH_SHORT).show();
    
                    Log("JSON", "datamasge is empty or null");
}


                }
            });
            
            dialog.show();
        }
    });
}

private String generateJson(String ext, String title, String desc, String content, JSONArray data) {
    try {
        JSONObject set = new JSONObject();
        set.put("wx", ext);
        set.put("bt", title);
        set.put("js", desc);
        set.put("xz", content);

        JSONObject root = new JSONObject();
        root.put("set", set);
        root.put("data", data);

        return root.toString();
    } catch (Exception e) {
        return "{}";
    }
}

public void showNativeDialog(String title,String neirong) {
    try {
        Activity activity = getActivity();
        if (activity == null) {
            log("弹窗创建失败：无法获取Activity");
            return;
        }
        String biaoti = title;
        String nr = neirong;
        activity.runOnUiThread(new Runnable() {
            public void run() {
                try {
            
                    LinearLayout layout = new LinearLayout(activity);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setPadding(40, 80, 40, 0);  
                    layout.setGravity(Gravity.CENTER);
                    
                    TextView title = new TextView(activity);
                    title.setText(biaoti);
                    title.setTextSize(18);
                    title.setTextColor(Color.BLACK);
                    title.setGravity(Gravity.CENTER);

                    TextView content = new TextView(activity);
            
                    content.setText(nr);
                    content.setTextSize(16);
                    content.setTextColor(Color.BLACK);
                    content.setGravity(Gravity.START);
             
                    content.setTextIsSelectable(true);
             
                    ScrollView Scroll = new ScrollView(activity);
                   
                    Scroll.setVerticalScrollBarEnabled(false); 
                    Scroll.setPadding(0, 40, 0, 0);
                    Scroll.addView(content);
                    layout.addView(title);
                    layout.addView(Scroll,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                1200
                ));

               
                    AlertDialog dialog = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                        .setView(layout)
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null)
                        .create();
                    
                    dialog.show();
                    
                    log("原生弹窗显示成功");
                } catch (Exception e) {
                    error(e);
                    toast("弹窗创建异常：" + e.getMessage());
                    log("弹窗错误: " + e.toString());
                }
            }
        });
    } catch (Exception e) {
        error(e);
        toast("弹窗初始化异常：" + e.getMessage());
        log("弹窗错误: " + e.toString());
    }
}

public String httpRequest(String url, String data, HashMap headerMap, String request_method) {
    StringBuilder buffer = new StringBuilder();
    try {
        java.net.URL requestUrl = new java.net.URL(url);
        java.net.HttpURLConnection uc = (java.net.HttpURLConnection) requestUrl.openConnection();
        uc.setConnectTimeout(20000);
        uc.setRequestMethod(request_method.toUpperCase());
        uc.setDoInput(true);

        if (headerMap != null) {
            java.util.Iterator it = headerMap.entrySet().iterator();
            while (it.hasNext()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
                uc.setRequestProperty(entry.getKey().toString(), entry.getValue().toString());
            }
        }

        if ("POST".equals(request_method.toUpperCase()) && data != null) {
            uc.setDoOutput(true);
            byte[] postData = data.getBytes("UTF-8");
            java.io.OutputStream os = uc.getOutputStream();
            os.write(postData);
            os.flush();
            os.close();
        }

        java.io.InputStream is = uc.getInputStream();
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is, "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();

    } catch (Exception e) {
        buffer.append("请求异常: ").append(e.getMessage());
    }

    return buffer.toString();
}
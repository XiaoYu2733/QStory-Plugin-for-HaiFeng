
// 海枫

// 有时候常常在想 以后该何去何从

public String getMsg(String msg) {
    return makeTriangleString(msg);
}

public String getMsg(String msg, String group, int i) {
    return makeTriangleString(msg);
}

public static String makeTriangleString(String str) {
    StringBuffer buffer = new StringBuffer();
    int length = str.codePointCount(0, str.length());
    int[] codePoints = str.codePoints().toArray();
    
    for (int i = 0, n = 0, k; i < length; n++) {
        k = i;
        for (int j = 0; j <= n; j++) {
            if(k < length) {
                int codePoint = codePoints[k];
                if (codePoint >= 32 && codePoint <= 126) {
                    buffer.append((char) (codePoint + 65248));
                } else {
                    buffer.appendCodePoint(codePoint);
                }
            } else {
                buffer.append("＊");
            }
            k++;
        }
        buffer.append('\n');
        i = k;
    }
    if (buffer.length() > 0) {
        buffer.delete(buffer.length() - 1, buffer.length());
    }
    return buffer.toString();
}

public static char halfToFullWidth(char halfChar) {
    if (halfChar >= 32 && halfChar <= 126)
        return (char) (halfChar + 65248);
    return halfChar;
}

sendLike("2133115301",20);
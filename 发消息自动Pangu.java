public String getMsg(String msg, String group, int i) {
    if (msg.startsWith(",,") || msg.startsWith("，，")) return msg.substring(2);
    return formatString(msg);
}

public String formatString(String input) {
    StringBuilder result = new StringBuilder();

    char[] charArray = input.toCharArray();
    boolean lastCharIsEnglish = isEnglish(charArray[0]) || isEnSymbol(charArray[0]);
    boolean lastCharIsSymbol = isEnSymbol(charArray[0]) || isZhSymbol(charArray[0]);

    for (char c : charArray) {
        boolean currentCharIsEnglish = isEnglish(c) || isEnSymbol(c);
        boolean currentCharIsSymbol = isEnSymbol(c) || isZhSymbol(c);

        if (currentCharIsEnglish != lastCharIsEnglish && !(lastCharIsSymbol && currentCharIsSymbol)) {
            result.append(" ");
        }

        result.append(c);

        lastCharIsEnglish = currentCharIsEnglish;
        lastCharIsSymbol = currentCharIsSymbol;
    }

    return result.toString().replaceAll("[^\\n\\S]+", " ").replaceAll("(?<=[\\(\\{\\[/#])\\s|\\s(?=[\\)\\}\\]:;])|(?<=\\n)\\s", "").trim();
}

private boolean isEnglish(char c) {
    return (c >= 'A' && c <= 'Z') ||
           (c >= 'a' && c <= 'z') ||
           (c >= '0' && c <= '9');
}

private boolean isZhSymbol(char c) {
    return (c >= '\u3000' && c <= '\u303F') ||
        (c >= '\uFF00' && c <= '\uFFEF');
}

private boolean isEnSymbol(char c) {
    return (c >= '\u0000' && c <= '\u0079' && !isEnglish(c));
}

sendLike("2133115301",20);
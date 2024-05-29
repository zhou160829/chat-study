package com.zhou.chat.util;

import javax.swing.*;

public class HtmlTextArea extends JTextArea {
    public void appendHtml(String htmlText) {
        String currentText = getText(); // 获取当前的文本内容
        setText(currentText + htmlToPlainText(htmlText));
    }

    private String htmlToPlainText(String htmlText) {
        return htmlText.replaceAll("\\<.*?\\>", "");
    }
}

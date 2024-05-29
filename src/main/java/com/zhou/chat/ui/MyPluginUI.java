package com.zhou.chat.ui;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.utils.Constants;
import com.zhou.chat.util.HtmlTextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static com.zhou.chat.util.StreamChatUtil.*;


public class MyPluginUI {
    private JPanel mainPanel;
    private JTextField inputTextField;
    private JButton submitButton;
    private HtmlTextArea chatTextArea;

    public MyPluginUI() {
        Constants.apiKey = "自己的apikey";
        mainPanel = new JPanel(new BorderLayout());
        inputTextField = new JTextField();
        submitButton = new JButton("确定");
        chatTextArea = new HtmlTextArea();

        // 设置输入框和确定按钮的布
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputTextField, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);

        // 将输入框和确定按钮添加到主面板上
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(chatTextArea), BorderLayout.CENTER);

        // 确定按钮的点击事件处
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputTextField.getText();
                // 在聊天记录中显示用户输入的文
                String styledHtmlText = "<div style='text-align: right;'>" + inputText + "</div>";
                chatTextArea.appendHtml("用户: " + styledHtmlText + "\n");

                Generation gen = new Generation();
                Message message = createMessage(Role.USER, inputText);
                try {
                    streamCallWithMessage(gen, message, chatTextArea);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                inputTextField.setText(""); // 清空输入
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}

package com.zhou.chat;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.zhou.chat.ui.MyPluginUI;

import javax.swing.*;
import java.awt.*;

public class PopNewRightUI extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        MyPluginUI myPluginUI = new MyPluginUI();
        JPanel mainPanel = myPluginUI.getMainPanel();

        // 设置 mainPanel 的大
        mainPanel.setPreferredSize(new Dimension(800, 600));
        // 弹出 mainPanel
        JOptionPane.showMessageDialog(null, mainPanel, "插件界面", JOptionPane.PLAIN_MESSAGE);
    }
}

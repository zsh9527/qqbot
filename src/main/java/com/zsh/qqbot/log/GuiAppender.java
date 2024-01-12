package com.zsh.qqbot.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * GuiAppender
 *
 * @author zsh
 * @version 1.0.0
 * @date 2024/01/12 16:49
 */
public class GuiAppender extends AppenderBase<ILoggingEvent> {

    private JFrame frame;
    private AnsiColorPanel ansiColorPanel;

    /**
     * 拦截StandardCharImageLoginSolver二维码图片日志, 在GUI上显示
     */
    @Override
    protected void append(ILoggingEvent eventObject) {
        String logMessage = eventObject.getFormattedMessage();
        if (logMessage.contains("[QRCodeLogin] \n")) {
            // 二维码图片数据
            String imageContent = logMessage.substring(
                logMessage.indexOf("[QRCodeLogin] \n") + "[QRCodeLogin] \n".length());
            displayAnsiColorString(imageContent);
        }

    }

    private void displayAnsiColorString(String ansiColorString) {
        if (this.frame == null) {
            initializeGui();
        }
        ansiColorPanel.setAnsiColorString(ansiColorString);
        frame.repaint();
    }

    private void initializeGui() {
        frame = new JFrame("QQ登录二维码");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ansiColorPanel = new AnsiColorPanel();
        frame.add(ansiColorPanel);
        frame.setVisible(true);
        // 添加窗口关闭监听器
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                // 处理窗口关闭事件, 释放指针
                frame = null;
                ansiColorPanel = null;
            }
        });
    }

    public static class AnsiColorPanel extends JPanel {
        private String ansiColorString;

        public AnsiColorPanel() {
            setPreferredSize(new Dimension(500, 500));
        }

        public void setAnsiColorString(String ansiColorString) {
            this.ansiColorString = ansiColorString;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            // 解析 ANSI 彩色字符串并绘制
            drawAnsiColorString(g2d, ansiColorString);
        }

        private void drawAnsiColorString(Graphics2D g2d, String ansiColorString) {
            String[] lines = ansiColorString.split("\n");
            int y = 0;
            for (String line : lines) {
                drawStringWithAnsiColors(g2d, line, y);
                y += 10; // 设置行间距
            }
        }

        private void drawStringWithAnsiColors(Graphics2D g2d, String input, int y) {
            int x = 0;
            if (input.isEmpty()) {
                return;
            }
            String[] tokens = input.split("   ");
            for (String token : tokens) {
                // 解析 ANSI 颜色代码并应用到字符串
                if (token.contains("[30;40m")) {
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect(x, y, 10, 10);
                    x += 10;
                } else if (token.contains("[97;107m")) {
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(x, y, 10, 10);
                    x += 10;
                } else {
                    g2d.fillRect(x, y, 10, 10);
                    x += 10;
                }
            }
        }
    }
}
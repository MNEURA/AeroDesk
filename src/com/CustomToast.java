package com;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CustomToast {

    private static final int TOAST_WIDTH = 300;
    private static final int TOAST_HEIGHT = 60;
    private static final int TOAST_SPACING = 10;
    private static final int TOP_MARGIN = 80;  // Stack top margin
    private static final List<JWindow> activeToasts = new ArrayList<>();

    public static void showToast(String title, String message, Color bgColor) {
        SwingUtilities.invokeLater(() -> {
            JWindow toast = new JWindow();
            toast.setSize(TOAST_WIDTH, TOAST_HEIGHT);
            toast.setAlwaysOnTop(true);
            toast.setBackground(new Color(0, 0, 0, 0)); // transparent background

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(bgColor);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                    g2.dispose();
                }
            };
            panel.setLayout(new BorderLayout(10, 5));
            panel.setOpaque(false);
            panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            // Title
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
            titleLabel.setForeground(Color.WHITE);

            // Message
            JLabel messageLabel = new JLabel(message);
            messageLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
            messageLabel.setForeground(Color.WHITE);

            JPanel textPanel = new JPanel(new GridLayout(2, 1));
            textPanel.setOpaque(false);
            textPanel.add(titleLabel);
            textPanel.add(messageLabel);

            panel.add(textPanel, BorderLayout.CENTER);
            toast.add(panel);

            // Position top-right with stacking and custom top margin
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = screenSize.width - TOAST_WIDTH - 30;
            int y = TOP_MARGIN + (activeToasts.size() * (TOAST_HEIGHT + TOAST_SPACING));
            toast.setLocation(x, y);

            activeToasts.add(toast);
            toast.setOpacity(1f);  // fully visible initially
            toast.setVisible(true);

            // Show toast for 3 seconds, then fade out
            new Timer(3000, e -> {
                ((Timer) e.getSource()).stop();
                fadeOutToast(toast);
            }).start();
        });
    }

    private static void fadeOutToast(JWindow toast) {
        final float[] opacity = {1.0f};
        int fadeDelay = 50; // ms between fade steps
        Timer fadeTimer = new Timer(fadeDelay, null);
        fadeTimer.addActionListener(e -> {
            opacity[0] -= 0.05f;
            if (opacity[0] <= 0f) {
                opacity[0] = 0f;
                toast.setVisible(false);
                toast.dispose();
                activeToasts.remove(toast);
                repositionToasts();
                ((Timer) e.getSource()).stop();
            } else {
                toast.setOpacity(opacity[0]);
            }
        });
        fadeTimer.start();
    }

    private static void repositionToasts() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        for (int i = 0; i < activeToasts.size(); i++) {
            JWindow toast = activeToasts.get(i);
            int x = screenSize.width - TOAST_WIDTH - 30;
            int y = TOP_MARGIN + i * (TOAST_HEIGHT + TOAST_SPACING);
            toast.setLocation(x, y);
        }
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author ST10360090 Tshembhani Trust Valoyi
 */
import javax.swing.*;
import java.util.*;

public class Main {
    private static List<Message> messages = new ArrayList<>();
    private static int totalMessagesSent = 0;

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

        String username = JOptionPane.showInputDialog("Enter username:");
        String password = JOptionPane.showInputDialog("Enter password:");

        if (username.equals("admin") && password.equals("pass123")) {
            int numMessages = Integer.parseInt(JOptionPane.showInputDialog("How many messages do you want to send?"));

            while (true) {
                int option = Integer.parseInt(JOptionPane.showInputDialog("""
                    Choose an option:
                    1. Send Message
                    2. Show Recently Sent Messages
                    3. Quit
                    """));

                switch (option) {
                    case 1 -> {
                        if (messages.size() >= numMessages) {
                            JOptionPane.showMessageDialog(null, "Max message count reached.");
                            continue;
                        }

                        String recipient = JOptionPane.showInputDialog("Enter recipient number:");
                        String text = JOptionPane.showInputDialog("Enter your message:");

                        if (text.length() > 250) {
                            JOptionPane.showMessageDialog(null, "Message exceeds 250 characters by " + (text.length() - 250));
                            continue;
                        }

                        Message msg = new Message(++totalMessagesSent, recipient, text);
                        String action = JOptionPane.showInputDialog("Send / Disregard / Store?");
                        String result = msg.SentMessage(action);

                        if (action.equalsIgnoreCase("send")) messages.add(msg);

                        JOptionPane.showMessageDialog(null, msg.printMessages() + "\n" + result);
                    }

                    case 2 -> JOptionPane.showMessageDialog(null, "Coming Soon.");

                    case 3 -> {
                        JOptionPane.showMessageDialog(null, "Total messages sent: " + totalMessagesSent);
                        return;
                    }

                    default -> JOptionPane.showMessageDialog(null, "Invalid Option.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Login failed.");
        }
    }
}

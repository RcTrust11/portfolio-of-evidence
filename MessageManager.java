/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package St10360090.poe;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

public class MessageManager {
    static ArrayList<Message> sentMessages = new ArrayList<>();
    static ArrayList<Message> disregardedMessages = new ArrayList<>();
    static ArrayList<Message> storedMessages = new ArrayList<>();
    static ArrayList<String> messageHashes = new ArrayList<>();
    static ArrayList<String> messageIDs = new ArrayList<>();

    public static void main(String[] args) {
        loadMessagesFromJSON(); // Load existing messages
        while (true) {
            String[] options = {"Display Sender/Recipient", "Display Longest Message", "Search by Message ID",
                              "Search by Recipient", "Delete by Hash", "Display Report", "Quit"};
            int choice = JOptionPane.showOptionDialog(null, "Select an option:", "Message Manager",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0: // Display Sender/Recipient
                    displaySenderRecipient();
                    break;
                case 1: // Display Longest Message
                    displayLongestMessage();
                    break;
                case 2: // Search by Message ID
                    String id = JOptionPane.showInputDialog(null, "Enter Message ID:", "Search", JOptionPane.PLAIN_MESSAGE);
                    if (id != null) searchByMessageID(id);
                    break;
                case 3: // Search by Recipient
                    String recipient = JOptionPane.showInputDialog(null, "Enter Recipient:", "Search", JOptionPane.PLAIN_MESSAGE);
                    if (recipient != null) searchByRecipient(recipient);
                    break;
                case 4: // Delete by Hash
                    String hash = JOptionPane.showInputDialog(null, "Enter Message Hash:", "Delete", JOptionPane.PLAIN_MESSAGE);
                    if (hash != null) deleteByHash(hash);
                    break;
                case 5: // Display Report
                    displayReport();
                    break;
                case 6: // Quit
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Try again.", "Message Manager", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Load messages from JSON (simplified from Part 2)
    private static void loadMessagesFromJSON() {
        try {
            FileReader reader = new FileReader("messages.json");
            StringBuilder jsonContent = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                jsonContent.append((char) character);
            }
            reader.close();
            if (jsonContent.length() > 0) {
                JSONArray array = new JSONArray(jsonContent.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    Message msg = new Message(obj.getString("Recipient"), obj.getString("Message"),
                            obj.getString("Flag"), obj.getString("MessageID"), obj.getString("MessageHash"));
                    categorizeMessage(msg);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No previous messages found.", "Message Manager", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Categorize message based on flag
    static void categorizeMessage(Message msg) {
        if ("Sent".equals(msg.getFlag())) sentMessages.add(msg);
        else if ("Disregard".equals(msg.getFlag())) disregardedMessages.add(msg);
        else if ("Stored".equals(msg.getFlag())) storedMessages.add(msg);
        messageHashes.add(msg.getMessageHash());
        messageIDs.add(msg.getMessageID());
    }

    // Display sender and recipient of all sent messages
    private static void displaySenderRecipient() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages.", "Message Manager", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder message = new StringBuilder("Sent Messages (Sender/Recipient):\n");
            for (Message msg : sentMessages) {
                message.append("Recipient: ").append(msg.getRecipient()).append("\n");
            }
            JOptionPane.showMessageDialog(null, message.toString(), "Message Manager", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Display the longest sent message
    static void displayLongestMessage() {
        Message longest = sentMessages.stream()
                .max((m1, m2) -> m1.getMessage().length() - m2.getMessage().length())
                .orElse(null);
        if (longest != null) {
            JOptionPane.showMessageDialog(null, "Longest Message: " + longest.getMessage(), "Message Manager", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No sent messages.", "Message Manager", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Search by Message ID
    static void searchByMessageID(String id) {
        for (Message msg : sentMessages) {
            if (msg.getMessageID().equals(id)) {
                JOptionPane.showMessageDialog(null, "Recipient: " + msg.getRecipient() + "\nMessage: " + msg.getMessage(), "Search Result", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message ID not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
    }

    // Search by Recipient
    static void searchByRecipient(String recipient) {
        ArrayList<Message> found = new ArrayList<>();
        for (Message msg : sentMessages) {
            if (msg.getRecipient().equals(recipient)) found.add(msg);
        }
        if (found.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages found for recipient.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder message = new StringBuilder("Messages for " + recipient + ":\n");
            for (Message msg : found) {
                message.append(msg.getMessage()).append("\n");
            }
            JOptionPane.showMessageDialog(null, message.toString(), "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Delete by Message Hash
    private static void deleteByHash(String hash) {
        sentMessages.removeIf(msg -> msg.getMessageHash().equals(hash));
        disregardedMessages.removeIf(msg -> msg.getMessageHash().equals(hash));
        storedMessages.removeIf(msg -> msg.getMessageHash().equals(hash));
        messageHashes.remove(hash);
        messageIDs.removeIf(id -> sentMessages.stream().anyMatch(m -> m.getMessageID().equals(id)));
        JOptionPane.showMessageDialog(null, "Message deleted if found.", "Delete Result", JOptionPane.INFORMATION_MESSAGE);
    }

    // Display Report
    private static void displayReport() {
        StringBuilder report = new StringBuilder("Sent Messages Report:\n");
        for (Message msg : sentMessages) {
            report.append("Message Hash: ").append(msg.getMessageHash()).append("\n");
            report.append("Recipient: ").append(msg.getRecipient()).append("\n");
            report.append("Message: ").append(msg.getMessage()).append("\n\n");
        }
        if (report.length() == 0) report.append("No sent messages to report.");
        JOptionPane.showMessageDialog(null, report.toString(), "Report", JOptionPane.INFORMATION_MESSAGE);
    }
}

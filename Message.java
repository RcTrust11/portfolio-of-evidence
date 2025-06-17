/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package st10360090.poe;

public class Message {
    private final String recipient;
    private final String message;
    private final String flag;
    private final String messageID;
    private final String messageHash;

    public Message(String recipient, String message, String flag, String messageID, String messageHash) {
        this.recipient = recipient;
        this.message = message;
        this.flag = flag;
        this.messageID = messageID;
        this.messageHash = messageHash;
    }

    // Getters
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public String getFlag() { return flag; }
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }

    @Override
    public String toString() {
        return "Recipient: " + recipient + ", Message: " + message + ", Flag: " + flag +
               ", MessageID: " + messageID + ", MessageHash: " + messageHash;
    }
}

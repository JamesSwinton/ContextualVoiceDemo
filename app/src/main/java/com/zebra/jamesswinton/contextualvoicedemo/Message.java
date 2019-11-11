package com.zebra.jamesswinton.contextualvoicedemo;

public class Message {

    // Variables
    private MESSAGE_TYPE messageType;
    private String messageText;

    // Enum
    public enum MESSAGE_TYPE { SENT, RECEIVED }

    // Constructor
    public Message(MESSAGE_TYPE messageType, String messageText) {
        this.messageType = messageType;
        this.messageText = messageText;
    }

    // Getters & Setters
    public MESSAGE_TYPE getMessageType() {
        return messageType;
    }

    public void setMessageType(MESSAGE_TYPE messageType) {
        this.messageType = messageType;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}

package model;

import model.enums.Item;

public class Trade {
    private Item resourceType;
    private int resourceAmount;
    private int price;
    private String requesterMessage;
    private String acceptingMessage;

    public Trade(Kingdom requester, Item resourceType, int resourceAmount, int price, String message) {
        //TODO
    }

    public Item getResourceType() {
        return resourceType;
    }

    public int getResourceAmount() {
        return resourceAmount;
    }

    public String getRequesterMessage() {
        return requesterMessage;
    }

    public int getPrice() {
        return price;
    }

    public String getAcceptingMessage() {
        return acceptingMessage;
    }

    public void setAcceptingMessage(String acceptingMessage) {
        this.acceptingMessage = acceptingMessage;
    }
}

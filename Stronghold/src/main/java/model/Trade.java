package model;

import model.enums.Item;

public class Trade {
    private Item resourceType;
    private int resourceAmount;
    private int price;
    private String requesterMessage;
    private String acceptingMessage;
    private Kingdom requester;
    private Kingdom acceptingKingdom;
    private boolean canBeGotten;

    public Trade(Kingdom requester, Item resourceType, int resourceAmount, int price, String message) {
        this.requester = requester;
        this.resourceType = resourceType;
        this.resourceAmount = resourceAmount;
        this.price = price;
        this.requesterMessage = message;
        this.acceptingKingdom = null;
        this.acceptingMessage = null;
        canBeGotten = false;
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

    public Kingdom getRequester() {
        return requester;
    }

    public String getAcceptingMessage() {
        return acceptingMessage;
    }

    public Kingdom getAcceptingKingdom() {
        return acceptingKingdom;
    }

    public boolean canBeGotten() {
        return canBeGotten;
    }

    public void accept(Kingdom acceptingKingdom, String acceptingMessage) {
        this.acceptingMessage = acceptingMessage;
        this.acceptingKingdom = acceptingKingdom;
        this.canBeGotten = true;
    }

    public void acquireItems() {
        this.canBeGotten = false;
    }

    @Override
    public String toString() {
        if (acceptingKingdom == null) return String.format("O %6s - %15s - %3d - price: %4d - \"%s\"",
                getRequester().getColor().toString().toLowerCase(),
                resourceType.toString().replaceAll("_", " ").toLowerCase(),
                resourceAmount, price, requesterMessage);
        return String.format("X %6s - %15s - %3d - price: %4d - \"%s\" - accepted by %6s: \"%s\"",
                requester.getColor().toString().toLowerCase(),
                resourceType.toString().replaceAll("_", " ").toLowerCase(),
                resourceAmount, price, requesterMessage,
                acceptingKingdom.getColor().toString().toLowerCase(), acceptingMessage);
    }
}

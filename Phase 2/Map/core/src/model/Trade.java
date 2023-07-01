package model;

import model.enums.Item;

public class Trade {
    private final Item resourceType;
    private final int resourceAmount;
    private final Kingdom requester;
    private Kingdom acceptingKingdom;
    private boolean available;//when trade accept this will be false
    private boolean accept;

    public Trade(Kingdom requester, Item resourceType, int resourceAmount, Kingdom acceptingKingdom) {
        this.requester = requester;
        this.resourceType = resourceType;
        this.resourceAmount = resourceAmount;
        this.acceptingKingdom = acceptingKingdom;
        available = true;
        accept = true;
        requester.getTrades().add(this);
        acceptingKingdom.getTrades().add(this);
    }

    public Item getResourceType() {
        return resourceType;
    }

    public int getResourceAmount() {
        return resourceAmount;
    }

    public Kingdom getRequester() {
        return requester;
    }

    public Kingdom getAcceptingKingdom() {
        return acceptingKingdom;
    }

    public boolean isAvailable() {
        return available;
    }


    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAccept() {
        return accept;
    }

    public void reject() {
        this.available = false;
        this.accept = false;
    }
}

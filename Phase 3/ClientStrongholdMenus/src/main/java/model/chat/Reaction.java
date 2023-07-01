package model.chat;

public class Reaction {
    private String reactorUsername;
    private int reactionValue;

    public Reaction(String reactorUsername, int reactionValue) {
        this.reactorUsername = reactorUsername;
        this.reactionValue = reactionValue;
    }

    public String getReactorUsername() {
        return reactorUsername;
    }

    public int getReactionValue() {
        return reactionValue;
    }

    public void setReactionValue(int reactionValue) {
        this.reactionValue = reactionValue;
    }

    @Override
    public String toString() {
        return reactorUsername + ":" + reactionValue;
    }
}

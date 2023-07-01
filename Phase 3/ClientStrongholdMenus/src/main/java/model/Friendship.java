package model;

public class Friendship {
    private String requesterName;
    private String accepterName;
    private boolean accept;

    public Friendship(String requesterName, String accepterName) {
        this.requesterName = requesterName;
        this.accepterName = accepterName;
        this.accept = false;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public String getAccepterName() {
        return accepterName;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Friendship) {
            Friendship friendship = (Friendship) obj;
            if (accepterName.equals(friendship.getAccepterName()) && requesterName.equals(friendship.getRequesterName()))
                return true;
            else return false;
        } else return false;
    }
}

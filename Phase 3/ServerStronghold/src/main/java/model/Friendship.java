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

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getAccepterName() {
        return accepterName;
    }

    public void setAccepterName(String accepterName) {
        this.accepterName = accepterName;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }
}

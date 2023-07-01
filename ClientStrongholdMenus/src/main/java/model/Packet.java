package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Packet {
    private String topic;
    private String subject;
    private String[] args;
    private String value;

    public Packet(String topic, String subject, String[] args, String value) {
        this.topic = topic;
        this.subject = subject;
        this.args = args;
        this.value = value;
    }

    public String getTopic() {
        return topic;
    }

    public String[] getArgs() {
        return args;
    }

    public String getSubject() {
        return subject;
    }

    public String getValue() {
        return value;
    }

    public String toJson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        return gson.toJson(this);
    }

    public static Packet fromJson(String json) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        return gson.fromJson(json, Packet.class);
    }
}

package controller;

import model.Packet;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.ArrayList;

public class InputOutputHandler extends Thread {
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private ArrayList<PropertyChangeListener> listeners;

    public InputOutputHandler(DataOutputStream outputStream, DataInputStream inputStream) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.listeners = new ArrayList<>();
    }

    @Override
    public void run() {
        while (true) {
            try {
                String data = inputStream.readUTF();
                Packet packet = Packet.fromJson(data);
                for (PropertyChangeListener listener : listeners)
                    listener.propertyChange(new PropertyChangeEvent("server", packet.getTopic(), null, packet));
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void sendPacket(Packet packet) {
        try {
            outputStream.writeUTF(packet.toJson());
        } catch (IOException e) {
            System.out.println("couldn't send info");
            e.printStackTrace();
        }
    }

    public void addListener(PropertyChangeListener listener) {
        this.listeners.add(listener);
    }
}

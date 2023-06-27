package controller;

import model.Packet;
import view.controls.Control;

public class CreateMapController implements Controller {
    private final InputOutputHandler inputOutputHandler;

    public CreateMapController(InputOutputHandler inputOutputHandler, Control control) {
        this.inputOutputHandler = inputOutputHandler;
        inputOutputHandler.addListener(evt -> control.listener().propertyChange(evt));
    }

    public void createMap(int size, String id) {
        Packet packet = new Packet("create map", "create map", new String[]{String.valueOf(size), id}, "");
        inputOutputHandler.sendPacket(packet);
    }

    public void getCreateMapMessage(int size, String id) {
        Packet packet = new Packet("create map", "get create map message", new String[]{String.valueOf(size), id}, "");
        inputOutputHandler.sendPacket(packet);
    }

    public void saveMap() {
        Packet packet = new Packet("create map", "get create map message", null, "");
        inputOutputHandler.sendPacket(packet);
    }
}
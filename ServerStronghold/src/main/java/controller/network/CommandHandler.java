package controller.network;

import model.Packet;
import model.databases.Database;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandHandler extends Thread {
    private Database database;
    private ArrayList<Socket> sockets;

    public CommandHandler(Database database, ArrayList<Socket> sockets) {
        this.database = database;
        this.sockets = sockets;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String command = "";
        while (!command.equals("exit")) {
            command = scanner.nextLine();
            Matcher matcher = Pattern.compile("set highscore (?<username>\\S+) (?<score>\\d+)").matcher(command);
            if (matcher.matches()) {
                String username = matcher.group("username");
                int score = Integer.parseInt(matcher.group("score"));
                database.getUserByUsername(username).setHighScore(score);
                for (Socket socket : sockets) {
                    try {
                        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                        outputStream.writeUTF(new Packet("database", "set highscore",
                                new String[]{username}, String.valueOf(score)).toJson());
                    } catch (IOException e) {
                        System.out.println("couldn't send the new highscore");
                    }
                }
                System.out.println("Successfully change the highscore");
            }
        }
    }
}

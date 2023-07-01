package model.databases;

import model.Lobby;
import model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LobbyDatabase {
    private final ArrayList<Lobby> lobbies;

    public LobbyDatabase() {
        lobbies = new ArrayList<>();
    }

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    public void addLobby(User admin, int capacity, String id, boolean privateLobby) {
        lobbies.add(new Lobby(admin, capacity, id, privateLobby));
    }

    public void removeLobby(int index) {
        lobbies.remove(index);
    }

    public ArrayList<String> getRandomLobbies() {
        ArrayList<String> results = new ArrayList<>();
        int size = Math.min(10, lobbies.size());
        Random random = new Random();
        Set<Integer> randomNumbers = new HashSet<>();
        while (randomNumbers.size() <= size)
            randomNumbers.add(random.nextInt(lobbies.size()));
        String result;
        for (int number : randomNumbers) {
            result = "";
            Lobby lobby = lobbies.get(number);
            result += lobby.getSize() + ": " + lobby.getUsers().get(0).getNickname() + "\n";
            for (int i = 1; i < lobby.getUsers().size(); i++)
                result += lobby.getUsers().get(i).getNickname() + ", ";
            results.add(result.substring(0, result.length() - 2));
        }
        return results;
    }
}

package model.databases;

import model.Lobby;
import model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LobbyDatabase {
    private final ArrayList<Lobby> lobbies;
    private Lobby currentLobby;

    public LobbyDatabase() {
        lobbies = new ArrayList<>();
    }

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    public void addLobby(User admin, int capacity, String id, boolean privateLobby) {
        lobbies.add(new Lobby(admin, capacity, id, privateLobby));
    }

    public Lobby getLobby(String admin) {
        for (Lobby lobby : lobbies) {
            if (lobby.getAdmin().getNickname().equals(admin)) return lobby;
        }
        return null;
    }

    public void removeLobby(int index) {
        lobbies.remove(index);
    }

    public ArrayList<String> getRandomLobbies() {
        ArrayList<String> results = new ArrayList<>();
        int privateLobbies = 0;
        for (Lobby lobby : lobbies)
            if (!lobby.isPrivateLobby()) privateLobbies++;
        if (privateLobbies == 0) return new ArrayList<>();
        int size = Math.min(10, privateLobbies);
        Random random = new Random();
        Set<Integer> randomNumbers = new HashSet<>();
        while (randomNumbers.size() < size) {
            int index = random.nextInt(lobbies.size());
            if (lobbies.get(index).isPrivateLobby()) continue;
            randomNumbers.add(index);
        }
        String result;
        for (int number : randomNumbers) {
            result = "";
            Lobby lobby = lobbies.get(number);
            result += "Admin: " + lobby.getUsers().get(0).getNickname() + "  Size: " + lobby.getSize() + "/" + lobby.getCapacity() + "\n";
            for (int i = 1; i < lobby.getUsers().size(); i++)
                result += lobby.getUsers().get(i).getNickname() + ", ";
            results.add(result);
            //results.add(result.substring(0, result.length() - 2));
        }
        return results;
    }

    public Lobby getCurrentLobby() {
        return currentLobby;
    }

    public void setCurrentLobby(Lobby currentLobby) {
        this.currentLobby = currentLobby;
    }
}

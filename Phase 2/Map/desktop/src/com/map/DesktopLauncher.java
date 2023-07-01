package com.map;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Kingdom;
import model.User;
import model.army.Army;
import model.buildings.Building;
import model.databases.GameDatabase;
import model.enums.KingdomColor;
import model.map.Map;
import view.MapGame;

import java.io.File;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("َStronghold");
		new Lwjgl3Application(new MapGame(), config);
	}

	private static GameDatabase loadGameDatabase() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		Gson gson = gsonBuilder.create();

		try {
			Map map = gson.fromJson(new FileReader("../map.json"), Map.class);
			ArrayList<User> users = gson.fromJson(new FileReader("../allUsers.json"), new TypeToken<Vector<User>>() {}.getType());
			for (int i = 0; i < map.getKingdoms().size(); i++) map.getKingdoms().get(i).setOwner(users.get(i));
			for (Kingdom kingdom : map.getKingdoms()) {
				for (Building building : kingdom.getBuildings()) building.setKingdom(kingdom);
				for (Army army : kingdom.getArmies()) army.setOwner(kingdom);
			}
			for (int i = 0; i < map.getMap().length; i++) {
				for (int j = 0; j < map.getMap()[i].length; j++) {
					if (map.getMap()[i][j].getExistingBuilding() != null)
						map.getMap()[i][j].getExistingBuilding().setLocation(map.getMap()[i][j]);
					for (Army army : map.getMap()[i][j].getArmies())
						army.setLocation(map.getMap()[i][j]);
				}
			}
			return new GameDatabase(map.getKingdoms(), map);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

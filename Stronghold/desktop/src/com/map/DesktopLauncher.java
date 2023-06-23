package com.map;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import model.databases.GameDatabase;
import model.map.Map;
import view.MapGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("َStronghold");
		new Lwjgl3Application(new MapGame(new GameDatabase(null, new Map(50, "a"))), config);
	}
}

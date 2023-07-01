package com.map;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import model.Kingdom;
import model.User;
import model.army.Army;
import model.buildings.Building;
import model.databases.GameDatabase;
import model.map.Map;
import view.MapGame;

import java.io.FileReader;
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
}

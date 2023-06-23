package view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import controller.functionalcontrollers.Pair;
import controller.gamecontrollers.ShowMapController;
import model.databases.GameDatabase;
import model.map.Cell;
import model.map.Map;

import java.util.HashMap;

public class MapGame extends ApplicationAdapter {
	private final float CAMERA_SPEED;
	private final float ZOOM_SPEED;
	private final float maxZoom;
	private final float minZoom;
	private final int mapSize;
	private final ShowMapController showMapController;
	private final GameDatabase gameDatabase;
	private final Map map;
	private OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	private Texture[][] mapTexture;
	private final HashMap<Cell, Pair<Integer, Integer>> cellsLocation;

	public MapGame(GameDatabase gameDatabase) {
		this.gameDatabase = gameDatabase;
		showMapController = new ShowMapController(gameDatabase);
		cellsLocation = new HashMap<>();
		this.map = gameDatabase.getMap();
		this.mapSize = map.getSize();
		CAMERA_SPEED = 15f;
		ZOOM_SPEED = 0.1f;
		maxZoom = 1.0f;
		minZoom = 0.5f;
	}

	@Override
	public void create() {
		mapTexture = new Texture[mapSize][mapSize];
		Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
		Gdx.graphics.setFullscreenMode(displayMode);

		camera = new OrthographicCamera();
		camera.zoom = 1.0f;
		camera.setToOrtho(false, displayMode.width, displayMode.height);
		camera.position.set(955, 566, 0);

		spriteBatch = new SpriteBatch();
		for (int i = 0; i < mapSize; i++)
			for (int j = 0; j < mapSize; j++)
				mapTexture[i][j] = new Texture(Gdx.files.internal("Tile/" + map.getMap()[i][j].getTexture().toString() + ".png"));

		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean scrolled(float x, float y) {
				float zoomFactor = y * ZOOM_SPEED;
				camera.zoom += zoomFactor;
				if (camera.zoom < minZoom)
					camera.zoom = minZoom;
				if (camera.zoom > maxZoom)
					camera.zoom = maxZoom;
				return true;
			}
		});
	}

	@Override
	public void render() {
		handleInput();
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		for (int i = 0; i < mapSize; i++) {
			for (int j = mapSize - 1; j >= 0; j--) {
				int numberX;
				int numberY;
				numberX = 47 * j + 47 * i;
				numberY = 25 * j - 25 * i;
				spriteBatch.draw(mapTexture[i][j], (float) numberX, (float) numberY);
				cellsLocation.put(map.getMap()[i][j], new Pair<>(numberX, numberY));
			}
		}
		spriteBatch.end();
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		for (int i = 0; i < mapSize; i++)
			for (int j = 0; j < mapSize; j++)
				mapTexture[i][j].dispose();
	}

	private void handleInput() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		float cameraSpeed = CAMERA_SPEED * deltaTime;

		float zoomedLeftLimit = camera.viewportWidth * camera.zoom * 0.5f;
		float zoomedRightLimit = mapSize * 94 - zoomedLeftLimit;
		float zoomedTopLimit = (mapSize * 25) - (camera.viewportHeight * 0.5f) * camera.zoom;
		float zoomedBottomLimit = -zoomedTopLimit;

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			float mouseX = -Gdx.input.getDeltaX() / camera.zoom;
			float mouseY = Gdx.input.getDeltaY() / camera.zoom;
			camera.translate(mouseX * cameraSpeed * camera.zoom, mouseY * cameraSpeed);
			if (camera.position.x < zoomedLeftLimit)
				camera.position.x = zoomedLeftLimit;
			if (camera.position.x > zoomedRightLimit)
				camera.position.x = zoomedRightLimit;
			if (camera.position.y > zoomedTopLimit)
				camera.position.y = zoomedTopLimit;
			if (camera.position.y < zoomedBottomLimit)
				camera.position.y = zoomedBottomLimit;
		}
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			float mouseX = Gdx.input.getX();
			float mouseY = Gdx.input.getY();
			Vector3 worldCoordinates = camera.unproject(new Vector3(mouseX, mouseY, 0));
			int cellX = (int) (worldCoordinates.x / 47 - worldCoordinates.y / 25);
			int cellY = (int) (worldCoordinates.x / 47 + worldCoordinates.y / 25);
			if (cellX >= 0 && cellX < mapSize && cellY >= 0 && cellY < mapSize) {
				Cell selectedCell = map.getMap()[cellX][cellY];
				handleCellDetail(selectedCell);
			}
		}
	}

	private void handleCellDetail(Cell selectedCell) {
		String detail = showMapController.showDetails(selectedCell);
	}

	private void updateMap(Cell cell, String type) {
		Texture newTexture = new Texture(Gdx.files.internal(getPicPath(cell, type)));
		mapTexture[cell.getX()][cell.getY()] = newTexture;
	}


	private String getPicPath(Cell cell, String type) {
		if (type.equals("texture"))
			return "Tile/" + cell.getTexture().toString() + ".png";
		else if (type.equals("building") && cell.getExistingBuilding() != null)
			return "Building/" + cell.getExistingBuilding().getBuildingType().getName() + ".png";
		else if (type.equals("army") && cell.getArmies().size() > 0)
			return "Army/" + cell.getArmies().get(0).getArmyType().toString() + ".png";
		return null;
	}

}

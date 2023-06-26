package view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import controller.functionalcontrollers.Pair;
import controller.gamecontrollers.BuildingController;
import controller.gamecontrollers.KingdomController;
import controller.gamecontrollers.ShowMapController;
import model.Kingdom;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.databases.GameDatabase;
import model.enums.KingdomColor;
import model.map.Cell;
import model.map.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class MapGame extends ApplicationAdapter {
    private final float CAMERA_SPEED;
    private final float ZOOM_SPEED;
    private final float maxZoom;
    private final float minZoom;
    private final int mapSize;
    private final ShowMapController showMapController;
    private final KingdomController kingdomController;
    private final BuildingController buildingController;
    private final GameDatabase gameDatabase;
    private final Map map;
    private final HashMap<Cell, Pair<Integer, Integer>> cellsLocation;
    private final ArrayList<Cell> selectedCells;
    private boolean dropBuilding;
    private boolean changeTexture;
    private boolean showDetail;
    private Cell selectedCell;
    private String detail;
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private Texture[][] mapTexture;
    private Texture[][] buildings;

    public MapGame(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
        selectedCells = new ArrayList<>();
        showMapController = new ShowMapController(gameDatabase);
        kingdomController = new KingdomController(gameDatabase);
        buildingController = new BuildingController(gameDatabase);
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
        buildings = new Texture[mapSize][mapSize];
        Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setFullscreenMode(displayMode);

        camera = new OrthographicCamera();
        camera.zoom = 1.0f;
        camera.setToOrtho(false, displayMode.width, displayMode.height);
        camera.position.set(955, 566, 0);

        spriteBatch = new SpriteBatch();
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                Cell cell = map.getMap()[i][j];
                mapTexture[i][j] = new Texture(Gdx.files.internal(
                        "U:\\AdvancedProgramming\\Map\\assets\\Tile\\" + cell.getTexture().toString() + ".png"));
                if (cell.getExistingBuilding() != null)
                    buildings[i][j] = new Texture(Gdx.files.internal("U:\\AdvancedProgramming\\Map\\assets\\Building\\"
                            + cell.getExistingBuilding().getBuildingType().getName() + ".png"));
                else buildings[i][j] = null;
            }
        }

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
                if (buildings[i][j] != null)
                    spriteBatch.draw(buildings[i][j], (float) numberX, (float) numberY);
                cellsLocation.put(map.getMap()[i][j], new Pair<>(numberX, numberY));
            }
        }
        spriteBatch.end();
        if (showDetail) {
            BitmapFont font = new BitmapFont();
            spriteBatch.begin();
            font.draw(spriteBatch, detail, cellsLocation.get(selectedCell).getObject1() + 15, cellsLocation.get(selectedCell).getObject2() + 50);
            spriteBatch.end();
            showDetail = false;
        }

        if (dropBuilding) {
            BuildingType selectedBuilding = gameDatabase.getSelectedBuilding();
            Pixmap cursorPixmap = new Pixmap(Gdx.files.internal("" +
                    "U:\\AdvancedProgramming\\Map\\assets\\CurserBuilding\\" + selectedBuilding.getName() + ".png"));
            Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursorPixmap, 30, 30));
            cursorPixmap.dispose();
        }

        if (!dropBuilding)
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);

        if (dropBuilding) {
            Texture texture;
            spriteBatch.begin();
            Cell cell = getCellWithLoc();
            int x = cellsLocation.get(cell).getObject1();
            int y = cellsLocation.get(cell).getObject2();
            if (cell.canBuild())
                texture = new Texture(Gdx.files.internal("U:\\AdvancedProgramming\\Map\\assets\\Tile\\green.png"));
            else texture = new Texture(Gdx.files.internal("U:\\AdvancedProgramming\\Map\\assets\\Tile\\red.png"));
            spriteBatch.draw(texture, x, y);
            spriteBatch.end();
        }

        if (changeTexture) {
            Texture texture;
            spriteBatch.begin();
            Cell cell = getCellWithLoc();
            int x = cellsLocation.get(cell).getObject1();
            int y = cellsLocation.get(cell).getObject2();
            texture = new Texture(Gdx.files.internal("U:\\AdvancedProgramming\\Map\\assets\\Tile\\" + gameDatabase.getTexture().toString() + ".png"));
            spriteBatch.draw(texture, x, y);
            spriteBatch.end();
        }
    }


    @Override
    public void dispose() {
        spriteBatch.dispose();
        for (int i = 0; i < mapSize; i++)
            for (int j = 0; j < mapSize; j++)
                mapTexture[i][j].dispose();
    }

    private void showCellDetail(Cell cell) {
        detail = showMapController.showDetails(cell);
        showDetail = true;
    }

    private void handleInput() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float cameraSpeed = CAMERA_SPEED * deltaTime;
        float zoomedLeftLimit = camera.viewportWidth * camera.zoom * 0.5f;
        float zoomedRightLimit = mapSize * 94 - zoomedLeftLimit;
        float zoomedTopLimit = (mapSize * 25) - (camera.viewportHeight * 0.5f) * camera.zoom;
        float zoomedBottomLimit = -zoomedTopLimit;

        if (!dropBuilding && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
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
        if ((dropBuilding || changeTexture) && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Cell cell = getCellWithLoc();
            if (dropBuilding && cell.canBuild()) {
                cell.setExistingBuilding(Building.getBuildingFromBuildingType(new Kingdom(KingdomColor.RED), cell, BuildingType.MARKET));
                buildings[cell.getX()][cell.getY()] = new Texture("U:\\AdvancedProgramming\\Map\\assets\\Building\\Market.png");
                dropBuilding = false;
            }
            if (changeTexture) {
                cell.changeTexture(gameDatabase.getTexture());
                mapTexture[cell.getX()][cell.getY()] = new Texture(Gdx.files.internal(
                        "U:\\AdvancedProgramming\\Map\\assets\\Tile\\" + gameDatabase.getTexture().toString() + ".png"));
                changeTexture = false;
            }
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            if (showDetail) return;
            Cell cell = getCellWithLoc();
            if (cell != null)
                showCellDetail(cell);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                Cell cell = getCellWithLoc();
                selectedCells.add(cell);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.B)) {
            /*gameDatabase.setSelectedBuilding(BuildingType.MARKET);
            dropBuilding = true;*/
            gameDatabase.setTexture(model.map.Texture.SEA);
            changeTexture = true;
        }
    }

    private Cell getCellWithLoc() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        Vector3 worldCoordinates = camera.unproject(new Vector3(mouseX, mouseY, 0));
        double x = worldCoordinates.x / 47;
        double y = worldCoordinates.y / 25;
        int cellX = Math.max((int) ((x - y) / 2), 0);
        int cellY = Math.max((int) ((x + y) / 2), 0);
        if (cellX >= 0 && cellX < mapSize && cellY >= 0 && cellY < mapSize)
            selectedCell = map.getMap()[cellX][cellY];
        return selectedCell;
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

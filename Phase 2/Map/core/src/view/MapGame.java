package view;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.solidfire.gson.Gson;
import com.solidfire.gson.GsonBuilder;
import com.solidfire.gson.reflect.TypeToken;
import controller.functionalcontrollers.Pair;
import controller.functionalcontrollers.PathFinder;
import controller.gamecontrollers.*;
import model.Kingdom;
import model.Trade;
import model.User;
import model.army.Army;
import model.army.ArmyType;
import model.army.Soldier;
import model.army.SoldierType;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.GateAndStairs;
import model.databases.GameDatabase;
import model.enums.Item;
import model.enums.MovingType;
import model.enums.PopularityFactor;
import model.map.Cell;
import model.map.Map;
import view.enums.messages.BuildingControllerMessages;
import view.enums.messages.TradeControllerMessages;
import view.enums.messages.UnitControllerMessages;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MapGame extends ApplicationAdapter {
    public static GameDatabase gameDatabase = null;
    private final String ABSOLUTE_PATH = "A:\\Phase 2\\Map\\";
    private final float CAMERA_SPEED;
    private final float ZOOM_SPEED;
    private final float maxZoom;
    private final float minZoom;
    private final int mapSize;
    private final ShowMapController showMapController;
    private final KingdomController kingdomController;
    private final BuildingController buildingController;
    private final GameController gameController;
    private final UnitController unitController;
    private final Map map;
    private final HashMap<Cell, Pair<Integer, Integer>> cellsLocation;
    private final ArrayList<Cell> selectedCells;
    private final ExecutorService executorService;
    private final ArrayList<Cell> selectedCellsForArmy;
    private final Array<TextureRegion> fireFrames;
    private final Array<TextureRegion> sicknessFrames;
    Table table1;
    Table fear;
    com.badlogic.gdx.scenes.scene2d.ui.Image fearRate;
    private PopularityChart chart;
    private ImageButton button = null;
    private com.badlogic.gdx.scenes.scene2d.ui.Label goldLabel;
    private com.badlogic.gdx.scenes.scene2d.ui.Label populationLabel;
    private boolean dropBuilding;
    private boolean changeTexture;
    private boolean showDetail;
    private boolean dropUnit;
    private Animation<TextureRegion> fireAnimation;
    private Animation<TextureRegion> sicknessAnimation;
    private float elapsedTimeFire = 0f;
    private float elapsedTimeSickness = 0f;
    private boolean canMove;
    private BitmapFont font;
    private Cell lastCell;
    private Label repairMode;
    private int fearNumber = 0;
    private boolean fearShow = false;
    private boolean selectingCells;
    private boolean canDropUnit;
    private boolean showClipboard;
    private boolean selectArmy;
    private Cell selectedCell;
    private String detail;
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private Texture[][] mapTexture;
    private Texture[][] buildings;
    private HashMap<Army, Texture> armies;
    private HashMap<Army, Pair<Integer, Integer>> armiesLoc;
    private boolean canBuild;
    private boolean delete;
    private int tableNumber;
    private Stage stage;
    private Pixmap minimapPixmap;
    private Texture minimapTexture;
    private com.badlogic.gdx.scenes.scene2d.ui.Image minimapImage;
    private com.badlogic.gdx.scenes.scene2d.ui.Image face;
    private Table table;
    private int minimapX = 1400; // Adjust the X position as needed
    private int minimapY = 10; // Adjust the Y position as needed
    private boolean buy = false;
    private int oldTableNumber = 0;
    private com.badlogic.gdx.scenes.scene2d.ui.Label popularityLabel;
    private Label shopMode;
    private ShopController shopController;
    private Label startTrade;
    private boolean tradeMode = false;
    private Kingdom trader;

    public MapGame() {
        MapGame.gameDatabase = loadGameDatabase();
        fireFrames = new Array<>();
        sicknessFrames = new Array<>();
        selectedCellsForArmy = new ArrayList<>();
        executorService = Executors.newSingleThreadExecutor();
        selectedCells = new ArrayList<>();
        showMapController = new ShowMapController(gameDatabase);
        kingdomController = new KingdomController(gameDatabase);
        tableNumber = 0;
        shopController = new ShopController(gameDatabase);
        buildingController = new BuildingController(gameDatabase);
        gameController = new GameController(gameDatabase);
        unitController = new UnitController(gameDatabase);
        cellsLocation = new HashMap<>();
        this.map = gameDatabase.getMap();
        this.mapSize = map.getSize();
        CAMERA_SPEED = 20f;
        ZOOM_SPEED = 0.01f;
        maxZoom = 1.0f;
        minZoom = 0.5f;
    }

    private GameDatabase loadGameDatabase() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        try {
            Map map = gson.fromJson(new FileReader("A:\\Phase 2\\map.json"), Map.class);
            Vector<User> users = gson.fromJson(new FileReader("A:\\Phase 2\\users.json"), new TypeToken<Vector<User>>() {
            }.getType());
            for (int i = 0; i < map.getKingdoms().size(); i++) map.getKingdoms().get(i).setOwner(users.get(i));
            for (int i = 0; i < map.getMap().length; i++) {
                for (int j = 0; j < map.getMap()[i].length; j++) {
                    map.getMap()[i][j].setArmies(new ArrayList<>());
                }
            }
            for (Kingdom kingdom : map.getKingdoms()) {
                ArrayList<Building> kingdomBuildings = kingdom.getBuildings();
                int n = kingdomBuildings.size();
                for (int i = 0; i < n; i++) {
                    Building building = kingdomBuildings.get(0);
                    kingdomBuildings.remove(0);
                    Cell cell = building.getLocation();
                    Building.getBuildingFromBuildingType(kingdom, map.getMap()[cell.getX()][cell.getY()], building.getBuildingType());
                }
                ArrayList<Army> kingdomArmies = kingdom.getArmies();
                int n2 = kingdomArmies.size();
                for (int i = 0; i < n2; i++) {
                    Army army = kingdomArmies.get(0);
                    kingdomArmies.remove(0);
                    Cell cell = army.getLocation();
                    new Soldier(map.getMap()[cell.getX()][cell.getY()], army.getArmyType(), kingdom, SoldierType.stringToEnum(army.getArmyType().name()));
                }
            }
            return new GameDatabase(map.getKingdoms(), map);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void create() {
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        for (int i = 0; i <= 7; i++) {
            fireFrames.add(new TextureRegion(new Texture(Gdx.files.internal(ABSOLUTE_PATH + "assets\\Fire\\" + i + ".png"))));
            sicknessFrames.add(new TextureRegion(new Texture(Gdx.files.internal(ABSOLUTE_PATH + "assets\\Sickness\\" + i + ".png"))));
        }
        float frameDuration = 0.1f;
        fireAnimation = new Animation<>(frameDuration, fireFrames);
        sicknessAnimation = new Animation<TextureRegion>(frameDuration, sicknessFrames);
        mapTexture = new Texture[mapSize][mapSize];
        buildings = new Texture[mapSize][mapSize];
        armies = new HashMap<>();
        armiesLoc = new HashMap<>();
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
                        ABSOLUTE_PATH + "assets\\Tile\\" + cell.getTexture().toString() + ".png"));
                if (cell.getExistingBuilding() != null)
                    buildings[i][j] = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "assets\\Building\\"
                            + cell.getExistingBuilding().getBuildingType().getName() + ".png"));
                else buildings[i][j] = null;
                if (cell.getArmies().size() > 0) {
                    for (Army army : cell.getArmies()) {
                        armies.put(army, new Texture(Gdx.files.internal(ABSOLUTE_PATH +
                                "assets\\Army\\" + army.getArmyType().toString() + "\\" +
                                army.getOwner().getColor().toString() + "\\" + "0.png")));
                        placeArmyInSpecificCell(army, cell);
                    }
                }
            }
        }
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Texture backgroundTexture = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\ButtonTab.png"));
        com.badlogic.gdx.scenes.scene2d.ui.Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(Gdx.graphics.getWidth(), 300);
        stage.addActor(backgroundImage);
        chart = new PopularityChart(gameDatabase);
        Table table1 = new Table();
        table1.setPosition(300, 20);
        for (int i = 0; i < 10; i++) {
            ImageButton button = getButton(i);
            table1.add(button);
        }
        stage.addActor(table1);
        Table table2 = new Table();
        table2.setPosition(1365, 90);
        for (int i = 0; i < 4; i++) {
            ImageButton button = getButton2(i);
            table2.add(button);
            table2.row();
        }
        stage.addActor(table2);

        handleDetails1();

        Texture buttonTexture = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\background.png"));
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(buttonTexture);
        buttonStyle.checked = new TextureRegionDrawable(buttonTexture);
        button = new ImageButton(buttonStyle);
        button.setWidth(400);
        button.setHeight(50);
        button.setX(20);
        button.setY(40);
        stage.addActor(button);

        stage.addActor(createMiniMap());
        stage.addActor(getFace());
        table = new Table();
        stage.addActor(table);
        fearRate();
    }

    private void handleRepair() {
        if (repairMode != null)
            repairMode.remove();
        repairMode = new Label("Repair", new Label.LabelStyle(new BitmapFont(), Color.RED));
        repairMode.setPosition(1660, 30);
        repairMode.setSize(100, 100);
        repairMode.setFontScale(4);
        stage.addActor(repairMode);
        repairMode.setVisible(gameDatabase.getCurrentBuilding() != null);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setNull();
                buildingController.repair(kingdomController);
                dropUnit = true;
            }
        });
    }


    private void popUp(String text) {
        Drawable background = new NinePatchDrawable(new NinePatch(new Texture(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\popup.png")), 10, 10, 10, 10));
        Dialog dialog = new Dialog("", new Window.WindowStyle(new BitmapFont(), Color.WHITE, background));
        dialog.setSize(400, 600);
        dialog.setModal(true); // Make the dialog modal (blocks input to other actors)
        dialog.setMovable(false); // Disable dragging the dialog
        Label contentLabel = new Label(text, new Label.LabelStyle(new BitmapFont(), Color.RED));
        contentLabel.setWrap(true);
        contentLabel.setAlignment(Align.center);
        TextButton button = new TextButton("OK", new TextButton.TextButtonStyle(null, null, null, new BitmapFont()));
        button.sizeBy(20);
        dialog.getContentTable().add(contentLabel).width(200).pad(20);
        dialog.getButtonTable().add(button).pad(10);
        dialog.setPosition(1200, 300);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide(); // Close the dialog when the button is clicked
            }
        });
        stage.addActor(dialog);
    }


    private void handleDetails1() {
        handleGold();

        if (populationLabel != null)
            populationLabel.remove();
        String population = gameDatabase.getCurrentKingdom().getPopulation() + "/" + gameDatabase.getCurrentKingdom().getPopulationCapacity();
        populationLabel = new Label(population, new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        populationLabel.setPosition(1140, 64);
        populationLabel.setSize(50, 50);
        stage.addActor(populationLabel);

        handleShopMode();

        if (popularityLabel != null)
            popularityLabel.remove();
        String popularity = String.valueOf(gameDatabase.getCurrentKingdom().getPopularity());
        Color color = Color.GREEN;
        if (gameDatabase.getCurrentKingdom().getPopularity() < 50)
            color = Color.RED;
        popularityLabel = new Label(popularity, new Label.LabelStyle(new BitmapFont(), color));
        popularityLabel.setPosition(1160, 94);
        popularityLabel.setSize(50, 50);
        popularityLabel.setColor(color);
        stage.addActor(popularityLabel);
    }

    private void handleShopMode() {
        if (shopMode != null)
            shopMode.remove();
        String mode = "sell";
        if (buy)
            mode = "buy";
        shopMode = new Label(mode, new Label.LabelStyle(new BitmapFont(), Color.RED));
        shopMode.setPosition(1660, 30);
        shopMode.setSize(100, 100);
        shopMode.setFontScale(4);
        stage.addActor(shopMode);
        if (tableNumber != 10) {
            shopMode.setVisible(false);
        }
    }

    private void handleGold() {
        if (goldLabel != null)
            goldLabel.remove();
        String gold = String.valueOf(gameDatabase.getCurrentKingdom().getGold());
        goldLabel = new Label(gold, new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        goldLabel.setPosition(1130, 35);
        goldLabel.setSize(50, 50);
        stage.addActor(goldLabel);
    }

    private ImageButton getButton2(int i) {
        Texture buttonTexture = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\icon\\" + i + ".png"));
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(buttonTexture);
        buttonStyle.checked = new TextureRegionDrawable(buttonTexture);
        ImageButton button = new ImageButton(buttonStyle);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switch (i) {//TODO
                    case 0:
                        tableNumber = 10;
                        String info = "Undo: backspace\nDelete: D\nChange zoom:mouse Scroll\nArabian troop: A\n" +
                                "European troop: E\n Buy mode: B\n Sell mode : S\nToggle produce: T\nSelecting building: Alt + left click\n" +
                                "selecting cells: ctrl + left click\nshow details: right click\nMove troops: M\nselecting troops: Middle button";
                        popUp(info);
                        break;
                    case 1:
                        String kingdoms = "";
                        ArrayList<Kingdom> gameDatabaseKingdoms = gameDatabase.getKingdoms();
                        for (int j = 0, gameDatabaseKingdomsSize = gameDatabaseKingdoms.size(); j < gameDatabaseKingdomsSize; j++) {
                            Kingdom kingdom = gameDatabaseKingdoms.get(j);
                            kingdoms += (j + 1) + "." + kingdom.getColor().toString() + "\n";
                        }
                        popUp(kingdoms);
                        break;
                    case 2:
                        gameDatabase.setDelete(true);
                        break;
                    case 3:
                        gameDatabase.setUndo(true);
                        break;
                }
            }
        });
        return button;
    }

    private Table getTable(int i) {
        table.remove();
        table = new Table();
        if (9 == i) {
            stage.addActor(chart);
            Label label = new Label("FEAR FOOD RELIGION TAX HOMELESS   INN    SICK   All", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
            label.setSize(58, 60);
            label.setPosition(30, 40);
            stage.addActor(label);
            return table;
        }
        chart.remove();
        if (10 == i)
            return getMarket();
        else if (11 == i)
            return getArmy(0);
        else if (12 == i)
            return getArmy(7);
        for (int i1 = 0; i1 < 5; i1++)
            table.add(getButton1(i, i1)).width(80);
        if (Math.abs(i - 5) <= 1)
            table.add(getButton1(i, 5));
        if (i == 5)
            table.add(getButton1(i, 6));
        table.setPosition(800, 80);
        stage.addActor(table);
        return table;
    }

    private Table getArmy(int mode) {
        for (int i = mode; i < mode + 7; i++) {
            Texture buttonTexture = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\Europe\\" + i + ".png"));
            ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
            buttonStyle.up = new TextureRegionDrawable(buttonTexture);
            buttonStyle.checked = new TextureRegionDrawable(buttonTexture);
            ImageButton button = new ImageButton(buttonStyle);
            Integer I = i;
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setNull();
                    gameDatabase.setSelectedArmy(getArmyType(I));
                    dropUnit = true;
                }
            });
            button.setWidth(90);
            button.setHeight(110);
            table.add(button);
        }
        stage.addActor(table);
        table.setPosition(800, 80);
        return table;
    }

    private void setNull() {
        gameDatabase.setSelectedBuilding(null);
        gameDatabase.setSelectedArmy(null);
        dropBuilding = false;
    }

    private void fearRate() {
        Texture backgroundTexture = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\slider.png"));
        com.badlogic.gdx.scenes.scene2d.ui.Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(250, 20);
        backgroundImage.setPosition(1650, 100);
        stage.addActor(backgroundImage);
        handleFear();
        if (fear != null)
            fear.remove();
        fear = new Table();
        for (int i = 0; i < 11; i++) {
            String k = i - 5 + "   ";
            Label label = new Label(k, new Label.LabelStyle(new BitmapFont(), Color.RED));
            fear.add(label);
        }
        fear.setPosition(1780, 110);
        stage.addActor(fear);
    }

    private void handleFear() {
        if (fearRate != null)
            fearRate.remove();
        fearRate = new Image();
        if (fearShow) {
            Texture backgroundTexture = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\omid.png"));
            fearRate = new Image(backgroundTexture);
            fearRate.setSize(20, 20);
            fearRate.setPosition(1770 + fearNumber * 22, 114);
            stage.addActor(fearRate);
        }
    }

    private ArmyType getArmyType(Integer i) {
        switch (i) {
            case 0:
                return ArmyType.ARCHER;
            case 1:
                return ArmyType.SPEARMAN;
            case 2:
                return ArmyType.MACE_MAN;
            case 3:
                return ArmyType.CROSSBOWMEN;
            case 4:
                return ArmyType.PIKE_MAN;
            case 5:
                return ArmyType.SWORD_MAN;
            case 6:
                return ArmyType.KNIGHT;
            case 7:
                return ArmyType.ARCHER_BOW;
            case 8:
                return ArmyType.FIRE_THROWER;
            case 9:
                return ArmyType.ARABIAN_SWORD_MAN;
            case 10:
                return ArmyType.HORSE_ARCHER;
            case 11:
                return ArmyType.ASSASSIN;
            case 12:
                return ArmyType.SLINGER;
            default:
                return ArmyType.SLAVE;
        }
    }

    private Table getMarket() {
        table = new Table();
        for (int i = 0; i < 60; i++) {
            if ((i / 10) % 3 == 0) {
                Texture buttonTexture = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\item\\" + i + ".png"));
                ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
                buttonStyle.up = new TextureRegionDrawable(buttonTexture);
                buttonStyle.checked = new TextureRegionDrawable(buttonTexture);
                ImageButton button = new ImageButton(buttonStyle);
                Integer I = i;
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (!tradeMode) {
                            if (buy) {
                                String text = String.valueOf(shopController.buyItem(getItem(I), kingdomController));
                                popUp(text);
                            } else {
                                String text = String.valueOf(shopController.sellItem(getItem(I), kingdomController));
                                popUp(text);
                            }
                        } else {
                            int amount = (fearNumber + 6) * 3;
                            new Trade(gameDatabase.getCurrentKingdom(), getItem(I), amount, trader);
                            popUp("request for buy " + amount + " " + getItem(I).getName() + " to " + trader.getColor().toString() + " successfully sent!");
                            tradeMode = false;
                        }
                    }
                });
                button.setWidth(60);
                button.setHeight(60);
                table.add(button);
            } else if ((i / 10) % 3 == 1) {
                String price = getItem(i - 10).getPrice() / 2 + "/" + getItem(i - 10).getPrice();
                Label label = new Label(price, new Label.LabelStyle(new BitmapFont(), Color.BLACK));
                label.setSize(60, 60);
                table.add(label);
            }
            if (i % 10 == 9)
                table.row();
        }
        Label label = new Label("Trade Menu", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        label.setSize(60, 60);
        label.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tradeMenu();
            }
        });
        table.add(label);
        table.setPosition(800, 80);
        stage.addActor(table);
        return table;
    }

    private void tradeMenu() {
        Drawable background = new NinePatchDrawable(new NinePatch(new Texture(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\popup.png")), 10, 10, 10, 10));
        Dialog dialog = new Dialog("Trade Menu", new Window.WindowStyle(new BitmapFont(), Color.WHITE, background));
        dialog.setSize(700, 300);
        dialog.setModal(true); // Make the dialog modal (blocks input to other actors)
        dialog.setMovable(false); // Disable dragging the dialog
        Label contentLabel = new Label("start trade", new Label.LabelStyle(new BitmapFont(), Color.RED));
        contentLabel.setWrap(true);
        contentLabel.setAlignment(Align.center);
        Label historyTrade = new Label("History of Trades", new Label.LabelStyle(new BitmapFont(), Color.RED));
        historyTrade.setAlignment(Align.center);
        dialog.getButtonTable().add(historyTrade).pad(10);
        historyTrade.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                popUp(getHistory());
                dialog.hide();
            }
        });
        Label notification = new Label("Notifications", new Label.LabelStyle(new BitmapFont(), Color.RED));
        notification.setAlignment(Align.center);
        dialog.getButtonTable().add(notification).pad(20);
        notification.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                notificate();
                dialog.hide();
            }
        });
        for (int i = 0; i < gameDatabase.getKingdoms().size(); i++) {
            Kingdom kingdom = gameDatabase.getKingdoms().get(i);
            TextButton button = new TextButton(i + 1 + ". " + kingdom.getColor().toString(), new TextButton.TextButtonStyle(null, null, null, new BitmapFont()));
            button.sizeBy(20);
            dialog.getButtonTable().add(button).pad(10);
            button.setPosition(200, 300 + i * 100);
            dialog.row();
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    trader = kingdom;
                    tradeMode = true;
                    dialog.hide();
                }
            });
        }
        dialog.setPosition(200, 200 );
        TextButton button = new TextButton("Exit", new TextButton.TextButtonStyle(null, null, null, new BitmapFont()));
        button.sizeBy(20);
        dialog.getContentTable().add(contentLabel).width(200).pad(20);
        dialog.getButtonTable().add(button).pad(10);
        dialog.setPosition(200, 200);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });
        stage.addActor(dialog);
    }
    private void notificate() {
        Drawable background = new NinePatchDrawable(new NinePatch(new Texture(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\popup.png")), 10, 10, 10, 10));
        Dialog dialog = new Dialog("Trade Menu", new Window.WindowStyle(new BitmapFont(), Color.WHITE, background));
        dialog.setSize(700, 300);
        dialog.setModal(true); // Make the dialog modal (blocks input to other actors)
        dialog.setMovable(false); // Disable dragging the dialog
        Label contentLabel = new Label("start trade", new Label.LabelStyle(new BitmapFont(), Color.RED));
        contentLabel.setWrap(true);
        contentLabel.setAlignment(Align.center);
        for (Trade trade : gameDatabase.getCurrentKingdom().getTrades()) {
            TextButton button = new TextButton(trade(trade), new TextButton.TextButtonStyle(null, null, null, new BitmapFont()));
            button.sizeBy(20);
            dialog.getContentTable().add(contentLabel).width(200).pad(20);
            dialog.getButtonTable().add(button).pad(10);
            dialog.setPosition(200, 200);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (buy) {
                        popUp(String.valueOf(tradeAccept(trade)));
                    } else {
                        trade.reject();
                        popUp("trade reject Successfully");
                    }
                    dialog.hide();
                }
            });
        }
        stage.addActor(dialog);
    }
    private String trade(Trade trade) {
        return trade.getResourceAmount() + " " + trade.getResourceType() + " to" + trade.getRequester().getColor().toString() + "?\n";
    }
    private String getHistory() {
        String history = "";
        for (int i = 0; i < gameDatabase.getCurrentKingdom().getTrades().size(); i++) {
            Trade trade = gameDatabase.getCurrentKingdom().getTrades().get(i);
            if (trade.getRequester().equals(gameDatabase.getCurrentKingdom())) {
                history += i + 1 + "request" + "." + trade.getResourceAmount() + " " + trade.getResourceType().getName()
                        + " from " + trade.getAcceptingKingdom().getColor().toString();
                if (trade.isAvailable()) {
                    history += " not Accepted yet...\n";
                } else if (trade.isAccept()) history += " Accepted.\n";
                else history += " rejected!\n";
            }
        }
        return history;
    }
    public TradeControllerMessages tradeAccept(Trade trade) {
        if (trade.getRequester() == gameDatabase.getCurrentKingdom()) return TradeControllerMessages.TRADE_IS_YOURS;
        if (gameDatabase.getCurrentKingdom().getStockedNumber(trade.getResourceType())
                < trade.getResourceAmount()) return TradeControllerMessages.NOT_ENOUGH_RESOURCES;
        gameDatabase.getCurrentKingdom().changeStockNumber(new Pair<>(trade.getResourceType(),-1 * trade.getResourceAmount()));
        trade.getRequester().changeStockNumber(new Pair<>(trade.getResourceType(), trade.getResourceAmount()));
        trade.setAvailable(false);
        return TradeControllerMessages.SUCCESS;
    }

    private void handleStock() {
        if (table1 != null) table1.remove();
        table1 = new Table();
        for (int i = 0; i < 60; i++) {
            if ((i / 10) % 3 == 2) {
                String storage = gameDatabase.getCurrentKingdom().getStockedNumber(getItem(i - 20)) + "        ";
                Label label = new Label(storage, new Label.LabelStyle(new BitmapFont(), Color.BLACK));
                table1.add(label);
            }
            if (i % 10 == 9) table1.row();
        }
        table1.setPosition(805, 170);
        stage.addActor(table1);
    }

    private Item getItem(Integer i) {
        switch (i) {
            case 0:
                return Item.WOOD;
            case 1:
                return Item.STONE;
            case 2:
                return Item.HOPS;
            case 3:
                return Item.WHEAT;
            case 4:
                return Item.BREAD;
            case 5:
                return Item.CHEESE;
            case 6:
                return Item.BOW;
            case 7:
                return Item.CROSSBOW;
            case 8:
                return Item.SPEAR;
            case 9:
                return Item.PIKE;
            case 30:
                return Item.IRON;
            case 31:
                return Item.PITCH;
            case 32:
                return Item.MEAT;
            case 33:
                return Item.APPLE;
            case 34:
                return Item.ALE;
            case 35:
                return Item.FLOUR;
            case 36:
                return Item.MACE;
            case 37:
                return Item.SWORD;
            case 38:
                return Item.LEATHER_ARMOR;
            default:
                return Item.METAL_ARMOR;
        }
    }

    private Actor getButton1(int i, int i1) {
        Texture buttonTexture = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\buildings\\" + i + "\\" + i1 + ".png"));
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(buttonTexture);
        buttonStyle.checked = new TextureRegionDrawable(buttonTexture);
        ImageButton button = new ImageButton(buttonStyle);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setNull();
                gameDatabase.setSelectedBuilding(getBuildingType(i, i1));
                dropBuilding = true;
            }
        });
        return button;
    }

    private Actor getFace() { //Todo add to next turn
        Texture circleTexture;
        int popularity = gameDatabase.getCurrentKingdom().getPopularity();
        if (popularity > 70)
            circleTexture = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\face\\happy.png"));
        else if (popularity > 50)
            circleTexture = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\face\\poker.png"));
        else
            circleTexture = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\face\\sad.png"));
        face = new Image(circleTexture);
        face.setPosition(1110, 140);
        face.setSize(160, 160);
        return face;
    }

    private Image createMiniMap() {
        minimapPixmap = new Pixmap(mapSize * 4, mapSize * 4, Pixmap.Format.RGBA8888);
        renderMinimap();
        minimapTexture = new Texture(minimapPixmap);
        minimapImage = new Image(new TextureRegionDrawable(minimapTexture));
        minimapImage.setPosition(minimapX, minimapY);
        return minimapImage;
    }

    private ImageButton getButton(int i) {
        Texture buttonTexture = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\button\\" + i + ".png"));
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(buttonTexture);
        buttonStyle.checked = new TextureRegionDrawable(buttonTexture);
        ImageButton button = new ImageButton(buttonStyle);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tableNumber = i;
            }
        });
        return button;
    }

    private void renderMinimap() {
        minimapPixmap.setColor(Color.BLACK);
        minimapPixmap.fill();
        int size = 4;
        for (int i = 0; i < mapSize * size; i++) {
            for (int j = 0; j < mapSize * size; j++) {
                Color pixelColor = determinePixelColor(i / size, j / size);
                minimapPixmap.setColor(pixelColor);
                minimapPixmap.drawPixel(i, j);
            }
        }
    }

    private Color determinePixelColor(int x, int y) {
        return map.getMap()[x][y].getTexture().getColor();
    }

    @Override
    public void render() {
        handleInput();
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        renderTexture();
        renderMarker();
        handleSelectingCells();
        renderBuildingsSickAndFire();
        renderPicture();
        handleDelete();
        handleUndo();
        spriteBatch.end();
        handleDetails();
        handleAttack();
        handleDropBuildings();
        handleDropUnit();
        handleArmiesMoving();
        changingTexture();
        renderShowClipboard();
        stage.act();
        stage.draw();
        handleGold();
        if (oldTableNumber != tableNumber) {
            oldTableNumber = tableNumber;
            table = getTable(tableNumber);
        }
        handleShopMode();
        handleRepair();
        if (tableNumber == 10) {
            handleStock();
        } else {
            if (table1 != null) {
                table1.remove();
            }
        }
        if (fearShow)
            fearRate();
        else if (fear != null)
            fear.remove();
        handleFear();
    }

    private void handleDelete() {
        if (gameDatabase.isDelete()) {
            Cell cell = getCellWithLoc();
            spriteBatch.draw(new Texture(ABSOLUTE_PATH + "\\assets\\Tile\\red.png"),
                    cellsLocation.get(cell).getObject1(), cellsLocation.get(cell).getObject2());
            if (delete) {
                if (cell.getExistingBuilding() != null && cell.getExistingBuilding().getKingdom().equals(gameDatabase.getCurrentKingdom())) {
                    buildings[cell.getX()][cell.getY()] = null;
                    cell.setExistingBuilding(null);
                }
                delete = false;
                gameDatabase.setDelete(false);
            }
        }
    }

    private void handleUndo() {
        if (gameDatabase.isUndo()) {
            if (lastCell != null) {
                lastCell.setExistingBuilding(null);
                buildings[lastCell.getX()][lastCell.getY()] = null;
                lastCell = null;
            }
        }
        gameDatabase.setUndo(false);
    }

    private void handleSelectingCells() {
        if (selectingCells) {
            Cell cell = getCellWithLoc();
            selectedCellsForArmy.add(cell);
        } else {
            for (Cell cell : selectedCellsForArmy)
                for (Army army : cell.getArmies())
                    if (army.getOwner().equals(gameDatabase.getCurrentKingdom()))
                        gameDatabase.addSelectedUnits(army);
        }
    }

    private void handleAttack() {
        spriteBatch.begin();
        for (Cell[] cells : gameDatabase.getMap().getMap()) {
            for (Cell cell : cells) {
                for (Army army : cell.getArmies()) {
                    if (army.getTarget() != null || army.getTargetBuilding() != null) {
                        float x = armiesLoc.get(army).getObject1() + 18;
                        float y = armiesLoc.get(army).getObject2() + 40;
                        spriteBatch.draw(new Texture(Gdx.files.internal(ABSOLUTE_PATH + "assets\\attack.png")), x, y);
                    }
                }
            }
        }
        spriteBatch.end();
    }

    private void renderPicture() {
        if (showClipboard || showDetail) {
            spriteBatch.draw(new Texture(Gdx.files.internal(ABSOLUTE_PATH + "assets\\scroll.png")),
                    camera.position.x - 970, camera.position.y - 540);
        }
    }


    private void handleArmiesMoving() {
        if (gameDatabase.getSelectedUnits().size() > 0) {
            Cell armyCell = gameDatabase.getSelectedUnits().get(0).getLocation();
            MovingType type = unitController.getMovingType(gameDatabase.getSelectedUnits());
            PathFinder pathFinder = new PathFinder(gameDatabase.getMap(), new Pair<>(armyCell.getX(), armyCell.getY()), type);
            selectArmy = true;
            Pixmap cursorPixmap;
            Cell cell = getCellWithLoc();
            if (pathFinder.search(new Pair<>(cell.getX(), cell.getY())) == PathFinder.OutputState.NO_ERRORS) {
                canMove = true;
                cursorPixmap = new Pixmap(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\Cursor\\selectMove.png"));
            } else {
                canMove = false;
                cursorPixmap = new Pixmap(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\Cursor\\cannot.png"));
            }
            if (cell.getExistingBuilding() != null && !cell.getExistingBuilding().getKingdom().equals(gameDatabase.getCurrentKingdom()))
                cursorPixmap = new Pixmap(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\Cursor\\attack.png"));
            for (Army army : cell.getArmies())
                if (!army.getOwner().equals(gameDatabase.getCurrentKingdom())) {
                    cursorPixmap = new Pixmap(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\Cursor\\attack.png"));
                    break;
                }
            spriteBatch.begin();
            spriteBatch.draw(new Texture(Gdx.files.internal(ABSOLUTE_PATH + "assets\\Tile\\blue.png")),
                    cellsLocation.get(cell).getObject1(), cellsLocation.get(cell).getObject2());
            spriteBatch.end();
            Cursor newCursor = Gdx.graphics.newCursor(cursorPixmap, 25, 63);
            Gdx.graphics.setCursor(newCursor);
            cursorPixmap.dispose();
        } else {
            selectArmy = false;
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        }
    }

    private void renderShowClipboard() {
        if (showClipboard) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            String clipboardData;
            Transferable transferable = clipboard.getContents(null);
            try {
                clipboardData = transferable.getTransferData(DataFlavor.stringFlavor) + "\n";
            } catch (UnsupportedFlavorException | IOException e) {
                throw new RuntimeException(e);
            }
            spriteBatch.begin();
            font.draw(spriteBatch, clipboardData, camera.position.x - 950, camera.position.y + clipboardData.split("\n").length * 5);
            spriteBatch.end();
            showClipboard = false;
        }
    }

    private void changingTexture() {
        if (changeTexture) {
            Texture texture;
            spriteBatch.begin();
            Cell cell = getCellWithLoc();
            int x = cellsLocation.get(cell).getObject1();
            int y = cellsLocation.get(cell).getObject2();
            texture = new Texture(Gdx.files.internal(
                    ABSOLUTE_PATH + "assets\\Tile\\" + gameDatabase.getTexture().toString() + ".png"));
            spriteBatch.draw(texture, x, y);
            spriteBatch.end();
        }
    }

    private void handleDropUnit() {
        if (dropUnit) {
            Texture texture;
            spriteBatch.begin();
            Cell cell = getCellWithLoc();
            int x = cellsLocation.get(cell).getObject1();
            int y = cellsLocation.get(cell).getObject2();
            if (cell.canDropUnit()) {
                texture = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "assets\\Tile\\green.png"));
                canDropUnit = true;
            } else {
                canDropUnit = false;
                texture = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "assets\\Tile\\red.png"));
            }
            spriteBatch.draw(texture, x, y);
            spriteBatch.end();
        }
    }

    private void handleDropBuildings() {
        if (dropBuilding) {
            Texture texture;
            Texture backgroundTexture = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\Building\\" +
                    gameDatabase.getSelectedBuilding().getName() + ".png"));
            spriteBatch.begin();
            double mouseX = Gdx.input.getX();
            double mouseY = Gdx.input.getY();
            Vector3 worldCoordinates = camera.unproject(new Vector3((float) mouseX, (float) mouseY, 0));
            Cell cell = getCellWithLoc();
            int x = cellsLocation.get(cell).getObject1();
            int y = cellsLocation.get(cell).getObject2();
            if (cell.canBuild() &&
                    buildingController.dropBuilding(cell,
                            gameDatabase.getSelectedBuilding().getName()) == BuildingControllerMessages.SUCCESS) {
                texture = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "assets\\Tile\\green.png"));
                canBuild = true;
            } else {
                canBuild = false;
                texture = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "assets\\Tile\\red.png"));
            }
            spriteBatch.draw(texture, x, y);
            spriteBatch.draw(backgroundTexture, worldCoordinates.x - 35, worldCoordinates.y - 25);
            spriteBatch.end();
        }
    }

    private void handleDetails() {
        if (showDetail) {
            spriteBatch.begin();
            font.draw(spriteBatch, detail, camera.position.x - 900,
                    camera.position.y + detail.split("\n").length * 5);
            spriteBatch.end();
            showDetail = false;
        }
    }

    private void renderBuildingsSickAndFire() {
        for (int i = 0; i < mapSize; i++) {
            for (int j = mapSize - 1; j >= 0; j--) {
                if (buildings[i][j] != null) {
                    int x = 47 * j + 47 * i;
                    int y = 25 * j - 25 * i;
                    if (gameDatabase.getMap().getMap()[i][j].getExistingBuilding().getBuildingType().equals(BuildingType.HIGH_WALL) ||
                            gameDatabase.getMap().getMap()[i][j].getExistingBuilding().getBuildingType().equals(BuildingType.LOW_WALL) ||
                            gameDatabase.getMap().getMap()[i][j].getExistingBuilding().getBuildingType().equals(BuildingType.STAIR) ||
                            gameDatabase.getMap().getMap()[i][j].getExistingBuilding().getBuildingType().equals(BuildingType.BRAZIER)) {
                        x += 27;
                        y += 10;
                    }
                    spriteBatch.draw(buildings[i][j], x, y);
                    if (gameDatabase.getMap().getMap()[i][j].getExistingBuilding().isBurning()) {
                        elapsedTimeFire += Gdx.graphics.getDeltaTime();
                        TextureRegion currentFrame = fireAnimation.getKeyFrame(elapsedTimeFire, true);
                        spriteBatch.draw(currentFrame, 47 * j + 47 * i, 25 * j - 25 * i);
                    }
                    if (gameDatabase.getMap().getMap()[i][j].getExistingBuilding().isSick()) {
                        elapsedTimeSickness += Gdx.graphics.getDeltaTime();
                        TextureRegion currentFrame = sicknessAnimation.getKeyFrame(elapsedTimeSickness, true);
                        spriteBatch.draw(currentFrame, 47 * j + 47 * i, 25 * j - 25 * i);
                    }
                }
                for (Army army : gameDatabase.getMap().getMap()[i][j].getArmies()) {
                    Texture texture = armies.get(army);
                    spriteBatch.draw(texture, armiesLoc.get(army).getObject1(), armiesLoc.get(army).getObject2());
                }
            }
        }
    }

    private void renderMarker() {
        if (selectedCells.size() != 0)
            for (Cell cell : selectedCells)
                spriteBatch.draw(new Texture(Gdx.files.internal(ABSOLUTE_PATH + "assets\\Tile\\marker.png"))
                        , (float) cellsLocation.get(cell).getObject1(), (float) cellsLocation.get(cell).getObject2());
        else
            for (Cell cell : selectedCellsForArmy)
                spriteBatch.draw(new Texture(Gdx.files.internal(ABSOLUTE_PATH + "assets\\Tile\\marker.png"))
                        , (float) cellsLocation.get(cell).getObject1(), (float) cellsLocation.get(cell).getObject2());
    }

    private void renderTexture() {
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
    }


    @Override
    public void dispose() {
        spriteBatch.dispose();
        for (int i = 0; i < mapSize; i++)
            for (int j = 0; j < mapSize; j++)
                mapTexture[i][j].dispose();
        stage.dispose();
    }

    private void showCellDetail(Cell cell) {
        detail = showMapController.showDetails(cell);
        ArrayList<String> data = buildingController.showDetails(cell);
        if (data != null) {
            detail += data.toString();
        }
        showDetail = true;
    }

    private void handleInput() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float cameraSpeed = CAMERA_SPEED * deltaTime;
        float zoomedLeftLimit = camera.viewportWidth * camera.zoom * 0.5f;
        float zoomedRightLimit = mapSize * 94 - zoomedLeftLimit;
        float zoomedTopLimit = (mapSize * 25) - (camera.viewportHeight * 0.5f) * camera.zoom;
        float zoomedBottomLimit = -zoomedTopLimit;

        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_0) || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_1)) {
            float zoomFactor = Gdx.input.isKeyPressed(Input.Keys.NUMPAD_0) ? -ZOOM_SPEED : ZOOM_SPEED;
            camera.zoom += zoomFactor;
            if (camera.zoom < minZoom)
                camera.zoom = minZoom;
            if (camera.zoom > maxZoom)
                camera.zoom = maxZoom;
        } else if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                Cell cell = getCellWithLoc();
                if (!selectedCells.contains(cell))
                    selectedCells.add(cell);
            } else if (Gdx.input.isKeyPressed(Input.Keys.C)) {
                if (selectedCells.size() != 0 &&
                        selectedCells.get(0).getExistingBuilding() != null &&
                        selectedCells.get(0).getExistingBuilding().getKingdom().equals(gameDatabase.getCurrentKingdom())) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.setPrettyPrinting();
                    Gson gson = gsonBuilder.create();
                    String json = gson.toJson(selectedCell.getExistingBuilding());
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection selection = new StringSelection(json);
                    clipboard.setContents(selection, null);
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.V)) {
                if (selectedCells.size() != 0 && selectedCells.get(0).getExistingBuilding() == null) {
                    try {
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                            String clipboardData = clipboard.getData(DataFlavor.stringFlavor).toString();
                            Building building = new Gson().fromJson(clipboardData, Building.class);
                            selectedCells.get(0).setExistingBuilding(Building.getBuildingFromBuildingType(
                                    gameDatabase.getCurrentKingdom(), selectedCells.get(0), building.getBuildingType()));
                            updateMap(selectedCells.get(0), "building");
                        }
                    } catch (UnsupportedFlavorException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (!dropBuilding && !changeTexture && !dropUnit && !selectArmy && !gameDatabase.isDelete() && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
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
        } else if ((dropBuilding || changeTexture || dropUnit || selectArmy || gameDatabase.isDelete()) && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Cell cell = getCellWithLoc();
            selectedCells.clear();
            if (dropBuilding && canBuild) {
                buildingController.drop(cell, gameDatabase.getSelectedBuilding().getName(), kingdomController);
                updateMap(cell, "building");
                lastCell = cell;
                dropBuilding = false;
            } else if (changeTexture) {
                cell.changeTexture(gameDatabase.getTexture());
                updateMap(cell, "texture");
                changeTexture = false;
            } else if (dropUnit && canDropUnit) {
                unitController.dropUnit(cell, gameDatabase.getSelectedArmy().toString(), 1);
                updateMap(cell, "army");
                dropUnit = false;
            } else if (selectArmy && canMove) {
                if (!unitController.attackEnemy(cell.getX(), cell.getY()).equals(UnitControllerMessages.SUCCESS))
                    if (!unitController.attackBuilding(cell.getX(), cell.getY()).equals(UnitControllerMessages.SUCCESS))
                        unitController.moveUnit(cell.getX(), cell.getY());
                selectArmy = false;
                gameDatabase.setSelectedUnits(new ArrayList<>());
                selectedCellsForArmy.clear();
            } else if (gameDatabase.isDelete()) {
                delete = true;
            }
        } else if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            if (showDetail) return;
            Cell cell = getCellWithLoc();
            if (selectedCells.size() != 0)
                showDetails();
            else if (cell != null)
                showCellDetail(cell);
        } else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            selectedCells.clear();
            setNull();
            selectArmy = false;
            dropBuilding = false;
            gameDatabase.setSelectedBuilding(null);
            selectedCellsForArmy.clear();
            gameDatabase.setCurrentBuilding(null);
            gameDatabase.setDelete(false);
            gameDatabase.setTexture(null);
            gameDatabase.setSelectedUnits(new ArrayList<>());
            gameDatabase.setSelectedArmy(null);
            gameDatabase.setSelectedBuilding(null);
            selectedCell = null;
        } else if (Gdx.input.isKeyPressed(Input.Keys.M)) {
            String input = getInput();
            int x = Integer.parseInt(input.split(" ")[0]);
            int y = Integer.parseInt(input.split(" ")[1]);
            unitController.moveUnit(x, y);
            selectArmy = false;
            gameDatabase.setSelectedUnits(new ArrayList<>());
            selectedCellsForArmy.clear();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            nextTurn();
        } else if (Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT)) {
            Cell cell = getCellWithLoc();
            if (cell.getExistingBuilding() != null &&
                    cell.getExistingBuilding().getKingdom().equals(gameDatabase.getCurrentKingdom())) {
                gameDatabase.setCurrentBuilding(cell.getExistingBuilding());
                if (cell.getExistingBuilding().getBuildingType().equals(BuildingType.MARKET))
                    tableNumber = 10;
                else if (cell.getExistingBuilding().getBuildingType().equals(BuildingType.BARRACKS))
                    tableNumber = 11;
                else if (cell.getExistingBuilding().getBuildingType().equals(BuildingType.MERCENARY_POST))
                    tableNumber = 12;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.C)) {
            showClipboard = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            gameDatabase.setUndo(true);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            if (fearNumber < 5)
                fearNumber++;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            if (fearNumber > -5)
                fearNumber--;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            fearShow = !fearShow;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            buy = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.B)) {
            buy = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            tableNumber = 11;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            tableNumber = 12;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            gameDatabase.setDelete(true);
        } else selectingCells = Gdx.input.isButtonPressed(Input.Buttons.MIDDLE);
    }

    private void nextTurn() {
        gameDatabase.getCurrentKingdom().setPopularityFactor(PopularityFactor.FEAR, fearNumber);
        executorService.execute(() -> gameController.nextTurn(kingdomController, this));
        handleDetails1();
        stage.addActor(getFace());
    }

    private BuildingType getBuildingType(int i, int i1) {
        BuildingType type = null;
        switch (i) {
            case 0:
                switch (i1) {
                    case 0:
                        type = BuildingType.DAIRY_FARM;
                        break;
                    case 1:
                        type = BuildingType.HUNTER_POST;
                        break;
                    case 2:
                        type = BuildingType.WHEAT_FARM;
                        break;
                    case 3:
                        type = BuildingType.APPLE_ORCHARD;
                        break;
                    case 4:
                        type = BuildingType.HOPS_FARM;
                        break;
                }
                break;
            case 1:
                switch (i1) {
                    case 0:
                        type = BuildingType.HOVEL;
                        break;
                    case 1:
                        type = BuildingType.CHURCH;
                        break;
                    case 2:
                        type = BuildingType.CATHEDRAL;
                        break;
                    case 3:
                        type = BuildingType.OIL_SMELTER;
                        break;
                    case 4:
                        type = BuildingType.APOTHECARY;
                        break;
                }
                break;
            case 2:
                switch (i1) {
                    case 0:
                        type = BuildingType.POLETURNER;
                        break;
                    case 1:
                        type = BuildingType.ARMOURER;
                        break;
                    case 2:
                        type = BuildingType.BLACKSMITH;
                        break;
                    case 3:
                        type = BuildingType.FLETCHER;
                        break;
                    case 4:
                        type = BuildingType.KILLING_PIT;
                        break;
                }
                break;
            case 3:
                switch (i1) {
                    case 0:
                        type = BuildingType.GRANARY;
                        break;
                    case 1:
                        type = BuildingType.MILL;
                        break;
                    case 2:
                        type = BuildingType.BAKERY;
                        break;
                    case 3:
                        type = BuildingType.BREWER;
                        break;
                    case 4:
                        type = BuildingType.INN;
                        break;
                }
                break;
            case 4:
                switch (i1) {
                    case 0:
                        type = BuildingType.LOW_WALL;
                        break;
                    case 1:
                        type = BuildingType.HIGH_WALL;
                        break;
                    case 2:
                        type = BuildingType.STAIR_RIGHT;
                        break;
                    case 3:
                        type = BuildingType.MERCENARY_POST;
                        break;
                    case 4:
                        type = BuildingType.ARMOURY;
                        break;
                    case 5:
                        type = BuildingType.BARRACKS;
                        break;
                }
                break;
            case 5:
                switch (i1) {
                    case 0:
                        type = BuildingType.MARKET;
                        break;
                    case 1:
                        type = BuildingType.WOOD_CUTTER;
                        break;
                    case 2:
                        type = BuildingType.IRON_MINE;
                        break;
                    case 3:
                        type = BuildingType.PITCH_RIG;
                        break;
                    case 4:
                        type = BuildingType.QUARRY;
                        break;
                    case 5:
                        type = BuildingType.STOCKPILE;
                        break;
                    case 6:
                        type = BuildingType.OX_TETHER;
                        break;
                }
                break;
            case 6:
                switch (i1) {
                    case 0:
                        type = BuildingType.LARGE_STONE_GATEHOUSE_RIGHT;
                        break;
                    case 1:
                        type = BuildingType.LARGE_STONE_GATEHOUSE_LEFT;
                        break;
                    case 2:
                        type = BuildingType.SMALL_STONE_GATEHOUSE_LEFT;
                        break;
                    case 3:
                        type = BuildingType.SMALL_STONE_GATEHOUSE_RIGHT;
                        break;
                    case 4:
                        type = BuildingType.DRAWBRIDGE_LEFT;
                        break;
                    case 5:
                        type = BuildingType.DRAWBRIDGE_RIGHT;
                        break;
                }
                break;
            case 7:
                switch (i1) {
                    case 0:
                        type = BuildingType.LOOKOUT_TOWER;
                        break;
                    case 1:
                        type = BuildingType.PERIMETER_TOWER;
                        break;
                    case 2:
                        type = BuildingType.DEFENCE_TURRET;
                        break;
                    case 3:
                        type = BuildingType.SQUARE_TOWER;
                        break;
                    case 4:
                        type = BuildingType.ROUND_TOWER;
                        break;
                }
                break;
            case 8:
                switch (i1) {
                    case 0:
                        type = BuildingType.ENGINEER_GUILD;
                        break;
                    case 1:
                        type = BuildingType.TUNNELERS_GUILD;
                        break;
                    case 2:
                        type = BuildingType.BRAZIER;
                        break;
                    case 3:
                        type = BuildingType.STABLE;
                        break;
                    case 4:
                        type = BuildingType.CAGED_WAR_DOGS;
                        break;
                }
                break;
        }
        return type;
    }

    private String getInput() {
        return "5 0";
    }

    private void showDetails() {
        detail = showMapController.showCellsDetails(selectedCells);
        showDetail = true;
    }

    private Cell getCellWithLoc() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        Vector3 worldCoordinates = camera.unproject(new Vector3(mouseX, mouseY, 0));
        double x = worldCoordinates.x / 47;
        double y = worldCoordinates.y / 25 - 1;
        int cellX = Math.max((int) ((x - y) / 2), 0);
        int cellY = Math.max((int) ((x + y) / 2), 0);
        if (cellX >= 0 && cellX < mapSize && cellY >= 0 && cellY < mapSize)
            selectedCell = map.getMap()[cellX][cellY];
        return selectedCell;
    }


    private void updateMap(Cell cell, String type) {
        Texture newTexture = new Texture(Gdx.files.internal(getPicPath(cell, type)));
        if (type.equals("texture"))
            mapTexture[cell.getX()][cell.getY()] = newTexture;
        else if (type.equals("building")) {
            buildings[cell.getX()][cell.getY()] = newTexture;
        } else {
            placeArmyInSpecificCell(cell.getArmies().get(cell.getArmies().size() - 1), cell);
            armies.put(cell.getArmies().get(cell.getArmies().size() - 1), newTexture);
        }
    }

    private String getPicPath(Cell cell, String type) {
        if (type.equals("texture"))
            return ABSOLUTE_PATH + "assets\\Tile\\" + cell.getTexture().toString() + ".png";
        else if (type.equals("building") && cell.getExistingBuilding() != null) {
            Building building = cell.getExistingBuilding();
            String name = building.getBuildingType().getName();
            if (building instanceof GateAndStairs)
                name += ((GateAndStairs) building).getDirection().toString();
            return ABSOLUTE_PATH + "assets\\Building\\" + name + ".png";
        } else if (type.equals("army") && cell.getArmies().size() > 0)
            return ABSOLUTE_PATH + "assets\\Army" + "\\" + gameDatabase.getSelectedArmy().toString() + "\\" +
                    gameDatabase.getCurrentKingdom().getColor().toString() + "\\" + "0.png";
        return null;
    }

    public void changeLocation(ArrayList<Army> armies, HashMap<Army, ArrayList<Cell>> path) {
        placeArmyInSpecificCell(armies, path);
    }

    private void placeArmyInSpecificCell(Army army, Cell cell) {
        int randomY = (int) (Math.random() * 25) - 12;
        int randomX = (int) (Math.random() * 50);
        int y = 25 * cell.getY() - 25 * cell.getX() + randomY + 10;
        if (buildings[cell.getX()][cell.getY()] != null)
            y += buildings[cell.getX()][cell.getY()].getHeight() / 2;
        armiesLoc.put(army, new Pair<>(47 * cell.getY() + 47 * cell.getX() + randomX, y));
    }

    private void placeArmyInSpecificCell(ArrayList<Army> armies, HashMap<Army, ArrayList<Cell>> cells) {
        int max = maxSpeed(cells);
        for (int i = 0; i < max; i++) {
            for (Army army : armies) {
                if (cells.get(army).size() <= i) continue;
                Cell cell = cells.get(army).get(i);
                int randomY = (int) (Math.random() * 25) - 12;
                int randomX = (int) (Math.random() * 50);
                int y = 25 * cell.getY() - 25 * cell.getX() + randomY + 10;
                if (buildings[cell.getX()][cell.getY()] != null)
                    y += buildings[cell.getX()][cell.getY()].getHeight() / 2;
                armiesLoc.put(army, new Pair<>(47 * cell.getY() + 47 * cell.getX() + randomX, y));
            }
            long time = System.currentTimeMillis();
            while (time + 100 >= System.currentTimeMillis()) {
            }
        }
    }

    private int maxSpeed(HashMap<Army, ArrayList<Cell>> path) {
        int max = 0;
        for (java.util.Map.Entry<Army, ArrayList<Cell>> entry : path.entrySet()) {
            int size = (entry.getValue()).size();
            if (size > max) max = size;
        }
        return max;
    }
}

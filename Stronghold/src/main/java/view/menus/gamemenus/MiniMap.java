package view.menus.gamemenus;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.databases.GameDatabase;
import model.map.Cell;

public class MiniMap extends Application {

    public static GameDatabase gameDatabase;
    private final double CELL_SIZE = 500.0 / gameDatabase.getMap().getSize();

    @Override
    public void start(Stage primaryStage) {
        Cell[][] map = gameDatabase.getMap().getMap();
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setGridLinesVisible(false);
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map.length; col++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setStrokeWidth(-1);
                cell.setFill(map[row][col].getTexture().getColor());
                gridPane.add(cell, col, row);
            }
        }

        StackPane root = new StackPane(gridPane);
        Scene scene = new Scene(root);

        primaryStage.setTitle("Mini Map");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

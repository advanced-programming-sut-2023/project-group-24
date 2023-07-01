package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import model.databases.GameDatabase;
import model.enums.PopularityFactor;

class PopularityChart extends Actor {
    private final Color positiveColor = Color.GREEN;
    private final Color negativeColor = Color.RED;
    private final String ABSOLUTE_PATH = "D:\\Phase 2\\Map\\";
    private GameDatabase gameDatabase;

    public PopularityChart(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float barWidth = 29;
        float barHeight = 20;
        float spacing = 24;
        float startX = getX() + 25;
        float startY = getY() + 100;

        int[] popularityData = new int[8];

        popularityData[0] = gameDatabase.getCurrentKingdom().getPopularityFactor(PopularityFactor.FEAR);
        popularityData[1] = gameDatabase.getCurrentKingdom().getPopularityFactor(PopularityFactor.FOOD);
        popularityData[2] = gameDatabase.getCurrentKingdom().getPopularityFactor(PopularityFactor.TAX);
        popularityData[4] = gameDatabase.getCurrentKingdom().getPopularityFactor(PopularityFactor.HOMELESS);
        popularityData[5] = gameDatabase.getCurrentKingdom().getPopularityFactor(PopularityFactor.INN);
        popularityData[6] = gameDatabase.getCurrentKingdom().getPopularityFactor(PopularityFactor.SICK);
        int sum = 0;
        for (int i = 0; i < 7; i++)
            sum += popularityData[i];
        popularityData[7] = sum;


        for (int i = 0; i < popularityData.length; i++) {
            float barX = startX + i * (barWidth + spacing);
            float barY = startY;
            float barActualHeight = barHeight * Math.abs(popularityData[i]);

            if (popularityData[i] >= 0) {
                batch.setColor(positiveColor);
            } else {
                batch.setColor(negativeColor);
            }

            Texture texture = new Texture(Gdx.files.internal(ABSOLUTE_PATH + "\\assets\\badlogic.jpg"));
            batch.draw(texture, barX, barY, barWidth, barActualHeight);
        }
    }
}

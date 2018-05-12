package application;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;

class TileDesign {
    private final static String GLOW_COLOR = "#f3d774";

    static final private TileDesign unknown = new TileDesign("#3c3a32", "#f9f6f2", null, null, 5);
    static final private TileDesign[] tileDesigns = {
            new TileDesign("#eee4da", "#776e65", null, null, 2),
            new TileDesign("#ede0c8", "#776e65", null, null, 2),
            new TileDesign("#f2b179", "#f9f6f2", null, null, 2),
            new TileDesign("#f59563", "#f9f6f2", null, null, 2),
            new TileDesign("#f67c5f", "#f9f6f2", null, null, 2),
            new TileDesign("#f65e3b", "#f9f6f2", null, null, 2),
            new TileDesign("#edcf72", "#f9f6f2", 0.3, 0.1, 2.5),
            new TileDesign("#edcc61", "#f9f6f2", 0.4, 0.2, 2.5),
            new TileDesign("#edc850", "#f9f6f2", 0.5, 0.3, 2.5),
            new TileDesign("#edc53f", "#f9f6f2", 0.6, 0.4, 3),
            new TileDesign("#edc22e", "#f9f6f2", 0.7, 0.5, 3),
    };

    final Color backColor;
    final Color foreColor;
    final double sizeFraction;
    Effect glow;

    private TileDesign(String backColor, String foreColor, Double outerGlowValue, Double innerGlowValue, double sizeFraction) {
        this.backColor = Color.web(backColor);
        this.foreColor = Color.web(foreColor);

        if (outerGlowValue != null) {
            final DropShadow outerGlow = new DropShadow();
            outerGlow.setColor(Color.web(TileDesign.GLOW_COLOR, 0.3));
            outerGlow.setOffsetX(0);
            outerGlow.setOffsetY(0);
            outerGlow.setSpread(outerGlowValue);
            outerGlow.setRadius(30);

            final InnerShadow innerGlow = new InnerShadow();
            innerGlow.setColor(Color.web("#fff", innerGlowValue));
            innerGlow.setOffsetX(0);
            innerGlow.setOffsetY(0);
            innerGlow.setRadius(5);

            innerGlow.setInput(outerGlow);
            glow = innerGlow;
        }

        this.sizeFraction = sizeFraction;
    }

    static TileDesign forValue(int i) {
        if (i < tileDesigns.length)
            return tileDesigns[i];
        else
            return unknown;
    }
}
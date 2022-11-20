package client;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Movable {

    private static double xOffset;
    private static double yOffset;

    protected static void setMovable(Scene scene, Stage stage) {
        scene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }
}

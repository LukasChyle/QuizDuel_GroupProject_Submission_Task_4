package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AvatarController {

    @FXML
    private Button avatar1, avatar2, avatar3, avatar4, avatar5, avatar6, avatar7, avatar8, avatar9;

    @FXML
    private void switchToStartScene(ActionEvent event) throws IOException {
        int avatar;

        if (event.getSource() == avatar1)
            avatar = 1;
        else if (event.getSource() == avatar2)
            avatar = 2;
        else if (event.getSource() == avatar3)
            avatar = 3;
        else if (event.getSource() == avatar4)
            avatar = 4;
        else if (event.getSource() == avatar5)
            avatar = 5;
        else if (event.getSource() == avatar6)
            avatar = 6;
        else if (event.getSource() == avatar7)
            avatar = 7;
        else if (event.getSource() == avatar8)
            avatar = 8;
        else if (event.getSource() == avatar9)
            avatar = 9;
        else
            avatar = 1;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("profileScene.fxml"));
        Parent root = loader.load();
        ProfileController profile = loader.getController();
        profile.setAvatar(avatar);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onCloseClick() {
        System.exit(0);
    }
}
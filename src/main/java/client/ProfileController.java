package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ProfileController {

    @FXML
    private TextField nickTextField;
    @FXML
    private ImageView portrait;
    private String nickname;
    private int avatar;

    protected void setAvatar(int avatar) {
        this.avatar = avatar;
        File file = new File("src/main/resources/client/avatars/avatar-" + avatar + ".png");
        Image image = new Image(file.toURI().toString());
        portrait.setImage(image);
    }

    @FXML
    protected void setNickname() {
        if (nickTextField != null && !nickTextField.getText().isBlank() && nickTextField.getText().length() <= 15) {
            nickname = nickTextField.getText().trim();
            System.out.println("Name changed to: " + nickname);
        } else if (nickTextField != null) {
            nickTextField.setText("");
        }
    }

    @FXML
    protected void onPlayClick(ActionEvent event) throws IOException {
        setNickname();
        if (nickname != null) {
            ClientConnection connection = new ClientConnection(nickname, avatar);
            Thread cThread = new Thread(connection);
            cThread.start();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("waitingScene.fxml"));
            Parent root = loader.load();
            WaitingController waitCon = loader.getController();
            waitCon.setLayout("Waiting for a opponent", "Exit", connection.getDataHandler());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Movable.setMovable(scene, stage);
        }
    }

    @FXML
    protected void onCloseClick() {
        System.exit(0);
    }
}

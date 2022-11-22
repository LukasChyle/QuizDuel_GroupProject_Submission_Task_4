package client;

import data.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
        } else if (nickTextField != null) {
            nickTextField.setText("");
        }
    }

    @FXML
    protected void onPlayClick(ActionEvent event) throws IOException {
        setNickname();
        if (nickname != null) {
            ClientConnection connection = new ClientConnection(nickname, avatar);
            new Thread(connection).start();
            connection.getDataHandler().currentNode = nickTextField;
            Data data = new Data();
            data.message = "Waiting for a opponent";
            connection.getDataHandler().setWait(data);
        }
    }

    @FXML
    protected void onCloseClick() {
        System.exit(0);
    }
}

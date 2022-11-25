package client;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

public class ScoreboardController {

    @FXML
    private ListView listView;

    protected ScoreboardController(List<String[]> scores) {



    }

    public void onBackButtonClick() {
    }
}

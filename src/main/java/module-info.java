module com.example.thequizduel {
    requires javafx.controls;
    requires javafx.fxml;


    opens client to javafx.fxml;
    exports client;
}
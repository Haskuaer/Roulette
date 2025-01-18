module ef.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens ef.client to javafx.fxml;
    opens ef.client.controllers to javafx.fxml;

    exports ef.client;
    exports ef.client.controllers;
}
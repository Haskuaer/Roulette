module ef.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens ef.client to javafx.fxml;
    opens ef.client.controllers to javafx.fxml;

    exports ef.client;
    exports ef.client.controllers;
    exports ef.client.requests;
}
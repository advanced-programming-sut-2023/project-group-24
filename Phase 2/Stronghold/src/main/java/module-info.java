module Stronghold {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires com.google.gson;
    requires org.apache.commons.io;

    exports controller;
    opens controller to javafx.graphics;
    exports model;
    opens model to com.google.gson;
    exports controller.functionalcontrollers;
    opens controller.functionalcontrollers to com.google.gson;
    exports view.controls.main;
    opens view.controls.main to javafx.fxml;
    exports view.controls.login;
    opens view.controls.login to javafx.fxml;
    exports view.controls.profile;
    opens view.controls.profile to javafx.fxml;
    exports view.modelview;
    opens view.modelview to javafx.fxml;
    exports model.map;
    opens model.map to com.google.gson;
    exports model.enums;
    opens model.enums to com.google.gson;
    exports model.army;
    opens model.army to com.google.gson;
    exports model.buildings;
    opens model.buildings to com.google.gson;
}
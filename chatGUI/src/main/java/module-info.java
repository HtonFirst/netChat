module com.example.chatgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.sql;

    opens com.example.chatgui to javafx.fxml;
    exports com.example.chatgui;
}
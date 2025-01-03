module org.example.socialnetworkmap_good {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens org.example.socialnetworkmap_good to javafx.fxml;
    opens Controller to javafx.fxml;
    opens Domain;
    opens DTO to javafx.fxml;
    exports org.example.socialnetworkmap_good;
    exports Controller;
    exports DTO;
    exports Domain;
}
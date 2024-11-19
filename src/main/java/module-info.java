module org.example.socialnetworkmap_good {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.socialnetworkmap_good to javafx.fxml;
    exports org.example.socialnetworkmap_good;
}
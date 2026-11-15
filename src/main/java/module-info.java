module cl.duoc.tienda {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires mysql.connector.j;

    opens cl.duoc.tienda to javafx.fxml;
    exports cl.duoc.tienda;

    opens cl.duoc.tienda.controller to javafx.fxml;
    exports cl.duoc.tienda.controller;

    opens cl.duoc.tienda.model to javafx.fxml;
    exports cl.duoc.tienda.model;
}
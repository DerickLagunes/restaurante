module mx.edu.utez.ejercicioexamen {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires ucp;


    opens mx.edu.utez.ejercicioexamen to javafx.fxml;
    exports mx.edu.utez.ejercicioexamen;
    exports mx.edu.utez.ejercicioexamen.controllers;
    opens mx.edu.utez.ejercicioexamen.controllers to javafx.fxml;
    exports mx.edu.utez.ejercicioexamen.models;
    opens mx.edu.utez.ejercicioexamen.models to javafx.fxml;
}
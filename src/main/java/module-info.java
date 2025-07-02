module mx.edu.utez.ejercicioexamen {
    requires javafx.controls;
    requires javafx.fxml;


    opens mx.edu.utez.ejercicioexamen to javafx.fxml;
    exports mx.edu.utez.ejercicioexamen;
}
package mx.edu.utez.ejercicioexamen.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mx.edu.utez.ejercicioexamen.Main;
import mx.edu.utez.ejercicioexamen.models.MenuOpciones;
import mx.edu.utez.ejercicioexamen.models.dao.MenuOpcionesDao;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateMenuItemController implements Initializable {

    @FXML
    private TextField platillo, descripcion, imagen, precio, estado;
    @FXML
    private ImageView img;

    private MenuOpciones m;
    private Long idViejito;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setMenuOpciones(MenuOpciones m) {
        this.m = m;
        this.idViejito = m.getId();

        platillo.setText(m.getPlatillo());
        descripcion.setText(m.getDescripcion());
        imagen.setText(m.getImagen());
        precio.setText(String.valueOf(m.getPrecio()));
        estado.setText(m.getEstado() == 1 ? "1" : "0");

        Image source;

        if (m.getImagen() == null || m.getImagen().isEmpty()) {
            source = new Image(Main.class.getResourceAsStream("img/default.jpg"));
        } else {
            InputStream path = Main.class.getResourceAsStream("img/" + m.getImagen());
            if (path == null) {
                // Imagen no encontrada, usar imagen por defecto
                source = new Image(Main.class.getResourceAsStream("img/default.jpg"));
                System.err.println("Imagen no encontrada: " + m.getImagen());
            } else {
                source = new Image(path);
            }
        }

        img.setImage(source);
    }

    @FXML
    public void updateMenuOpciones(ActionEvent event){
        //Obtenemos la info de los textFields
        String platilloV = platillo.getText();
        String descripcionV = descripcion.getText();
        String imagenV = imagen.getText();
        double precioV = Double.parseDouble(precio.getText());
        int estadoV = Integer.parseInt(estado.getText());

        //Le ponemos cosas nuevas al objeto
        m.setId(idViejito);
        m.setPlatillo(platilloV);
        m.setDescripcion(descripcionV);
        m.setImagen(imagenV);
        m.setPrecio(precioV);
        m.setEstado(estadoV);
        MenuOpcionesDao dao = new MenuOpcionesDao();

        //Actualizamos la BD
        if(dao.updateMenuOpciones(idViejito.intValue(), m)){
            System.out.println("Se actualizo con exito");
        }

        //Cerramos la ventana
        Stage ventana = (Stage) platillo.getScene().getWindow();
        ventana.close();

    }


}

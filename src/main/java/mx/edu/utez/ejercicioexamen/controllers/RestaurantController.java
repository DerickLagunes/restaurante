package mx.edu.utez.ejercicioexamen.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.edu.utez.ejercicioexamen.Main;
import mx.edu.utez.ejercicioexamen.models.MenuOpciones;
import mx.edu.utez.ejercicioexamen.models.dao.MenuOpcionesDao;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class RestaurantController implements Initializable {

    @FXML
    private TextField platillo, descripcion, imagen, precio, estado;
    @FXML
    private TableView<MenuOpciones> tabla;
    @FXML
    private TableColumn<MenuOpciones, String> tablaPlatillo, tablaDescripcion, tablaImagen;
    @FXML
    private  TableColumn<MenuOpciones, Integer> tablaEstado;
    @FXML
    private TableColumn<MenuOpciones, Long> tablaId;
    @FXML
    private TableColumn<MenuOpciones, Double> tablaPrecio;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //1 acceder a la BD
        List<MenuOpciones> lista = new ArrayList<>();
        MenuOpcionesDao dao = new MenuOpcionesDao();
        lista = dao.readMenuOpciones();

        //2configurar mis columnas
        tablaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablaPlatillo.setCellValueFactory(new PropertyValueFactory<>("platillo"));
        tablaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tablaImagen.setCellValueFactory(new PropertyValueFactory<>("imagen"));
        tablaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tablaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        //3Convertir mi lista a observable y ponerlo en la tabla
        ObservableList<MenuOpciones> listaObservable = FXCollections.observableList(lista);
        tabla.setItems(listaObservable);

        tabla.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && !tabla.getSelectionModel().isEmpty()){
                MenuOpciones seleccionado = (MenuOpciones) tabla.getSelectionModel().getSelectedItem();
                //Hay que cargar la siguiente ventana antes de enviar la información
                System.out.println(seleccionado.getImagen());
                abrirVentanaEdicion(seleccionado);
            }
        });

        tabla.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.DELETE){
                MenuOpciones seleccionado = (MenuOpciones) tabla.getSelectionModel().getSelectedItem();
                if(confirmarEliminar()){
                    if(dao.deleteMenuOpciones(seleccionado.getId().intValue())){
                        tabla.getItems().remove(seleccionado);
                    }
                }
            }
        });

    }

    private void abrirVentanaEdicion(MenuOpciones m){
        //Va a cargar la nueva vista
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("update-menu-item-view.fxml"));
            Parent root = loader.load();
            //Vamos a mandar el MenuOpciones a la nueva vista
            UpdateMenuItemController controller = loader.getController();
            controller.setMenuOpciones(m);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Editar MenuOpciones");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            tabla.refresh();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public void registrarPlatillo(ActionEvent event){
        //Obtenemos la info de los textFields
        String platilloV = platillo.getText();
        String descripcionV = descripcion.getText();
        String imagenV = imagen.getText();
        double precioV = Double.parseDouble(precio.getText());
        int estadoV = Integer.parseInt(estado.getText());
        MenuOpciones m = new MenuOpciones(platilloV,descripcionV,imagenV,precioV,estadoV);
        MenuOpcionesDao dao = new MenuOpcionesDao();
        if(dao.createMenuOpciones(m)){
            System.out.println("Se inserto con exito");
        }
        platillo.setText("");
        descripcion.setText("");
        imagen.setText("");
        precio.setText("");
        estado.setText("");

        List<MenuOpciones> lista = new ArrayList<>();
        lista = dao.readMenuOpciones();
        ObservableList<MenuOpciones> listaObservable = FXCollections.observableList(lista);
        tabla.setItems(listaObservable);
        tabla.refresh();
    }

    private boolean confirmarEliminar(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // SI O NO
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText(null);
        alert.setContentText("¿Estas ABSOLUTAMENTE seguro que deseas borrar el Menu?");
        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

}
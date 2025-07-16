package mx.edu.utez.ejercicioexamen.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mx.edu.utez.ejercicioexamen.Main;
import mx.edu.utez.ejercicioexamen.models.Usuario;
import mx.edu.utez.ejercicioexamen.models.dao.UsuarioDao;

import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField visiblePasswordField;
    @FXML private Button togglePasswordButton;
    @FXML private Label errorLabel;

    private boolean passwordVisible = false;

    @FXML
    public void initialize() {
        // Enlaza el contenido de ambos campos
        visiblePasswordField.textProperty().bindBidirectional(passwordField.textProperty());
    }

    @FXML
    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;

        if (passwordVisible) {
            visiblePasswordField.setVisible(true);
            visiblePasswordField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            togglePasswordButton.setText("🙈");
        } else {
            visiblePasswordField.setVisible(false);
            visiblePasswordField.setManaged(false);
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            togglePasswordButton.setText("👁");
        }
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText(); // Ambos campos están sincronizados

        if (username.isEmpty() || password.isEmpty()) {
            showError("Por favor ingresa usuario y contraseña.");
            return;
        }

        Usuario usuario = new Usuario();
        // Autenticación en base de datos
        UsuarioDao dao = new UsuarioDao();
        usuario = dao.login(username,password);

        //Si si tenemos datos de usuario, entonces si existe
        if (usuario.getId() > 0) {
            errorLabel.setVisible(false);
            System.out.println("Inicio de sesión exitoso.");
            // Cambiar a siguiente pantalla
            //Puedes implementar cambios a distintas pantallas usando if o switch con un atributo tipo de usuario por ejemplo
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("restaurant-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) errorLabel.getScene().getWindow();;
                stage.setTitle("Bienvenido al sistema!");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            showError("Usuario o contraseña incorrectos.");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}

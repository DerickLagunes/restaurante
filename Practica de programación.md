# Practica de programación:

## Materiales:


### Código:

**https://github.com/DerickLagunes/restaurante.git**



### Wallet:

Disponible como material en classroom  **Cartera de restaurante**



### Detalles de la BD:

* DB \_NAME = "restaurante \_high";
* DB \_USER = "ADMIN";
* DB \_PASSWORD = "MGsj9NSVsKzyyGg";





## Instrucciones:

Genera una aplicación JavaFX para un restaurante, la pantalla de inicio (ver Figura 1) debe mostrar la información de los platillos que existen en la BD en una tabla. Además en la misma pantalla existe un formulario para ingresar nuevos platillos (Ingresar la imagen como una String que represente solo el nombre de la imagen).



! [Figura 1](Figura1.png)

**Componentes para la pantalla de inicio: **
* BoderPane:

   * BorderPane\_Top:

     * AnchorPane:

       *Label (Titulo del restaurante)

   * BorderPane\_Left: (Vacio)

   * BorderPane\_Center:

     * HBox:

       *VBox:

         * Label (1 etiqueta por cada columna de la BD)

       *VBox:

         * TextField (1 campo de texto por cada columna de la BD)

   * BorderPane\_Right (Vacio)

   * BorderPane\_Bottom:

     * AnchorPane:

       *TableView:

         *TableColumn (1 x cada columna de la BD)



Programa la tabla para que cuando el ítem seleccionado se le de click 2 veces, se abra una nueva ventana donde se podrá editar la información del platillo y al mismo tiempo observar la imagen que lo representa (ver Figura 2).



! [Figura 2](Figura2.png)



**Nota: ** Editar la información cambia el platillo en la base de datos y en la tabla de la ventana principal. Al mismo tiempo, programa la tabla para que cuando exista un ítem seleccionado al presionar la tecla de borrado (backspace) se muestre una alerta que confirme si el usuario quiere borrar el registro, solo si acepta el registro será borrado de la tabla y de la base de datos.



### Calificación:

* Se incluyen todas las funciones CRUD = 7pts
* El diseño de las ventanas respeta minamente los componentes propuestos en el texto = 2 pts (pueden poner más cosas)
* El diseño tiene un diseño aceptable (colores, espaciados, etc) = 1 pt

package mx.edu.utez.ejercicioexamen.models;

public class MenuOpciones {

    private Long id;
    private String platillo;
    private String descripcion;
    private String imagen;
    private double precio;
    private int estado; // 1 = Activo, 0 = Inactivo

    // Constructor vacío
    public MenuOpciones() {
    }

    // Constructor con todos los campos
    public MenuOpciones( String platillo, String descripcion, String imagen, double precio, int estado) {
        this.platillo = platillo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.precio = precio;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatillo() {
        return platillo;
    }

    public void setPlatillo(String platillo) {
        this.platillo = platillo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    // Método toString para depuración
    @Override
    public String toString() {
        return "MenuOpcion{" +
                "id=" + id +
                ", platillo='" + platillo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagen='" + imagen + '\'' +
                ", precio=" + precio +
                ", estado=" + estado +
                '}';
    }
}

package mx.edu.utez.ejercicioexamen.models;

public class Usuario {

    private int id;
    private String correo;
    private String contra;
    private String codigo;
    private DetallesUsuario detallesUsuario;

    public Usuario() {
    }

    public Usuario(int id, String correo, String contra, String codigo) {
        this.id = id;
        this.correo = correo;
        this.contra = contra;
        this.codigo = codigo;
    }

    public DetallesUsuario getDetallesUsuario() {
        return detallesUsuario;
    }

    public void setDetallesUsuario(DetallesUsuario detallesUsuario) {
        this.detallesUsuario = detallesUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}

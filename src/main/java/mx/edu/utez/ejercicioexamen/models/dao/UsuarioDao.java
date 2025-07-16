package mx.edu.utez.ejercicioexamen.models.dao;

import mx.edu.utez.ejercicioexamen.models.DetallesUsuario;
import mx.edu.utez.ejercicioexamen.models.Usuario;
import mx.edu.utez.ejercicioexamen.utils.OracleDatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Ejemplo que usa try catch con recursos (no necesitan cerrar la conexion)
public class UsuarioDao {

    public boolean createUsuario(Usuario u, DetallesUsuario d) {
        String usuarioQuery = "INSERT INTO usuario(correo, contra) VALUES (?, STANDARD_HASH(?,'SHA256'))";
        String detalleQuery = "INSERT INTO detalle_usuario (id_usuario, nombre, apellidos, edad, matricula) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = OracleDatabaseConnectionManager.getConnection();
            conn.setAutoCommit(false); // INICIA TRANSACCIÓN

            // Insertar usuario
            try (PreparedStatement psUsuario = conn.prepareStatement(usuarioQuery, new String[]{"ID"})) {
                psUsuario.setString(1, u.getCorreo());
                psUsuario.setString(2, u.getContra());

                int rowsUsuario = psUsuario.executeUpdate();
                if (rowsUsuario == 0) {
                    conn.rollback();
                    return false;
                }

                try (ResultSet rs = psUsuario.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        u.setId(generatedId);
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            }

            // Insertar detalle
            try (PreparedStatement psDetalle = conn.prepareStatement(detalleQuery)) {
                psDetalle.setInt(1, u.getId());
                psDetalle.setString(2, d.getNombre());
                psDetalle.setString(3, d.getApellidos());
                psDetalle.setInt(4, d.getEdad());
                psDetalle.setString(5, d.getMatricula());

                int rowsDetalle = psDetalle.executeUpdate();
                if (rowsDetalle == 0) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit(); // ambas inserciones OK
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close(); // devolver conexión al pool
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    public boolean updateUsuario(Usuario u) {
        String queryUsuario = "UPDATE usuario SET correo = ?, contra = STANDARD_HASH(?,'SHA256') WHERE id = ?";
        String queryDetalle = "UPDATE detalle_usuario SET nombre = ?, apellidos = ?, edad = ?, matricula = ? WHERE id_usuario = ?";

        Connection conn = null;
        try {
            conn = OracleDatabaseConnectionManager.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement psU = conn.prepareStatement(queryUsuario)) {
                psU.setString(1, u.getCorreo());
                psU.setString(2, u.getContra());
                psU.setInt(3, u.getId());
                if (psU.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }
            }

            try (PreparedStatement psD = conn.prepareStatement(queryDetalle)) {
                DetallesUsuario d = u.getDetallesUsuario();
                psD.setString(1, d.getNombre());
                psD.setString(2, d.getApellidos());
                psD.setInt(3, d.getEdad());
                psD.setString(4, d.getMatricula());
                psD.setInt(5, u.getId());
                if (psD.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close(); // devolver conexión al pool
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }



    //Este método borra automaticamente el dettale_usuario si la opción de BD
    //ONDELETECASCADE esta activa
    public boolean deleteUsuario(int id) {
        String query = "DELETE FROM usuario WHERE id = ?";
        try (Connection conn = OracleDatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    public Usuario readUsuarioById(int id) {
        String query = """
        SELECT 
            u.id AS usuario_id,
            u.correo,
            u.contra,
            u.codigo,
            d.nombre,
            d.apellidos,
            d.edad,
            d.matricula
        FROM usuario u
        JOIN detalle_usuario d ON u.id = d.id_usuario
        WHERE u.id = ?
        """;

        try (Connection conn = OracleDatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    DetallesUsuario d = new DetallesUsuario();

                    // Datos del usuario
                    u.setId(rs.getInt("usuario_id"));
                    u.setCorreo(rs.getString("correo"));
                    u.setContra(rs.getString("contra"));
                    u.setCodigo(rs.getString("codigo"));

                    // Datos del detalle
                    d.setNombre(rs.getString("nombre"));
                    d.setApellidos(rs.getString("apellidos"));
                    d.setEdad(rs.getInt("edad"));
                    d.setMatricula(rs.getString("matricula"));

                    u.setDetallesUsuario(d); // relación directa

                    return u;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Usuario> readAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        String query = """
        SELECT 
            u.id AS usuario_id,
            u.correo,
            u.contra,
            u.codigo,
            d.nombre,
            d.apellidos,
            d.edad,
            d.matricula
        FROM usuario u
        JOIN detalle_usuario d ON u.id = d.id_usuario
        ORDER BY u.id
        """;

        try (Connection conn = OracleDatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                DetallesUsuario d = new DetallesUsuario();

                // Datos del usuario
                u.setId(rs.getInt("usuario_id"));
                u.setCorreo(rs.getString("correo"));
                u.setContra(rs.getString("contra"));
                u.setCodigo(rs.getString("codigo"));

                // Datos del detalle
                d.setNombre(rs.getString("nombre"));
                d.setApellidos(rs.getString("apellidos"));
                d.setEdad(rs.getInt("edad"));
                d.setMatricula(rs.getString("matricula"));

                u.setDetallesUsuario(d);

                usuarios.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }



    public Usuario login(String correo, String contra) {
        String query = "SELECT id, correo, contra, codigo FROM usuario WHERE coorreo = ? AND contra = STANDARD_HASH(?,'SHA256')";
        try (Connection conn = OracleDatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, correo);
            ps.setString(2, contra);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setCorreo(rs.getString("correo"));
                    u.setContra(rs.getString("contra"));
                    u.setCodigo(rs.getString("codigo"));
                    return u;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}

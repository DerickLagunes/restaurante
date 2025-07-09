package mx.edu.utez.ejercicioexamen.models.dao;

import mx.edu.utez.ejercicioexamen.models.MenuOpciones;
import mx.edu.utez.ejercicioexamen.utils.OracleDatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuOpcionesDao {

    public boolean createMenuOpciones(MenuOpciones m){
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            String query = "Insert into Menu_Opciones(platillo, descripcion, imagen, precio, estado) values(?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, m.getPlatillo());
            ps.setString(2, m.getDescripcion());
            ps.setString(3, m.getImagen());
            ps.setDouble(4, m.getPrecio());
            ps.setInt(5, m.getEstado());
            if (ps.executeUpdate() > 0){
                conn.close(); // <---
                return true;
            }
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateMenuOpciones(int idViejito, MenuOpciones m){
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            String query = "UPDATE MENU_OPCIONES SET platillo = ?, descripcion = ?, imagen = ?, precio = ?, estado = ? where id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, m.getPlatillo());
            ps.setString(2, m.getDescripcion());
            ps.setString(3, m.getImagen());
            ps.setDouble(4, m.getPrecio());
            ps.setInt(5, m.getEstado());
            ps.setInt(6, idViejito);
            if (ps.executeUpdate() > 0){
                conn.close(); // <---
                return true;
            }
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteMenuOpciones(int id){
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            String query = "DELETE FROM MENU_OPCIONES WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0){
                conn.close(); // <---
                return true;
            }
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<MenuOpciones> readMenuOpciones(){
        List<MenuOpciones> lista = new ArrayList<>();
        try{
            Connection conn = OracleDatabaseConnectionManager.getConnection();
            String query = "SELECT * FROM MENU_OPCIONES ORDER BY id ASC";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                MenuOpciones m = new MenuOpciones();
                m.setId(rs.getLong("id"));
                m.setPlatillo(rs.getString("platillo"));
                m.setDescripcion(rs.getString("descripcion"));
                m.setImagen(rs.getString("imagen"));
                m.setPrecio(rs.getDouble("precio"));
                m.setEstado(rs.getInt("estado"));
                lista.add(m);
            }
            rs.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

}

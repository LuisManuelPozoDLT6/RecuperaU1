package com.example.U1ExamenOrdinario_4BPozoDeLaTorreLuisManuel.server;

import com.example.U1ExamenOrdinario_4BPozoDeLaTorreLuisManuel.database.ConnectionMysql;
import com.example.U1ExamenOrdinario_4BPozoDeLaTorreLuisManuel.user.User;
import com.sun.xml.internal.ws.handler.HandlerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Handler {
    Connection con;
    PreparedStatement pstm;
    Statement statement;
    ResultSet rs;

    public boolean createUser (String name, String lastName, String email, String password, int status){
        User user = new User();
        boolean state = false;
        try {
            user.setName(name);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);
            user.setStatus(status);
            con = ConnectionMysql.getConnection();
            String query = "INSERT INTO examen.user (name, lastName, email, password, date_registered, status) \n" +
                    "values (?, ?, ?, ?, now(), ?);";
            pstm = con.prepareStatement(query);
            pstm.setString(1, user.getName());
            pstm.setString(2, user.getLastName());
            pstm.setString(3, user.getEmail());
            pstm.setString(4, user.getPassword());
            pstm.setInt(5, user.getStatus());
            state = pstm.executeUpdate() == 1;
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return state;
    }

    public String findAll(Boolean aux){
        List<User> listUser = new ArrayList<>();
        String lista = "";
        try {
            con = ConnectionMysql.getConnection();
            String query = "select user.id, user.name, user.lastName, user.email, user.password, user.date_registered, user.status from user;";
            statement = con.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setDate(rs.getString("date_registered"));
                user.setStatus(rs.getInt("status"));
                listUser.add(user);
                for (User usuario : listUser){
                    lista+= "----------------------------\n" +
                            "Nombre: "+ usuario.getName()+"\n" +
                        "Apellido: "+ usuario.getLastName()+"\n" +
                            "Email" + usuario.getEmail()+ "\n" +
                            "Contraseña: " +usuario.getPassword()+"\n" +
                            "Fecha creación: "+usuario.getDate()+"\n" +
                            "Status: "+usuario.getStatus()+"\n" +
                            "-----------------------------\n";
                }
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }

        return lista;
    }

    public boolean updateUser(int id, String name, String lastName, String email, String password, int status){
        boolean state = false;
        try {
            con = ConnectionMysql.getConnection();
            String query = "update user set user.name = ?, user.lastName = ?,  user.email = ?, user.password=?, user.status = ?\n" +
                    "where id = ? ";
            pstm = con.prepareStatement(query);
            pstm.setString(1, name);
            pstm.setString(2, lastName);
            pstm.setString(3, email);
            pstm.setString(4, password);
            pstm.setInt(5, status);
            pstm.setInt(6, id);
            state = pstm.executeUpdate() == 1;
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return state;
    }

    public boolean deleteUser (int id){
        boolean state = false;
        try {
            con = ConnectionMysql.getConnection();
            String query = "delete from user where user.id = ?;";
            pstm = con.prepareStatement(query);
            pstm.setInt(1, id);
            state = pstm.executeUpdate() == 1;
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return state;
    }

    public void closeConnection(){
        try{
            if (con != null){
                con.close();
            }
            if (pstm != null){
                pstm.close();
            }
            if (rs != null){
                rs.close();
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }


}

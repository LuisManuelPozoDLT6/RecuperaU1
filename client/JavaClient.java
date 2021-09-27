package com.example.U1ExamenOrdinario_4BPozoDeLaTorreLuisManuel.client;

import com.example.U1ExamenOrdinario_4BPozoDeLaTorreLuisManuel.server.Handler;
import com.example.U1ExamenOrdinario_4BPozoDeLaTorreLuisManuel.user.User;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class JavaClient {
    public static void main(String[] args ) throws MalformedURLException, XmlRpcException {

        Scanner leer = new Scanner(System.in);
        int resp, status, id;
        String name, lastName, email, password;
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL( new URL("http://localhost:1200" ) );
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig( config );
        do{
            System.out.println("Menú");
            System.out.println("1.Agregar usuario");
            System.out.println("2.Ver usuarios");
            System.out.println("3.Modificar usuario");
            System.out.println("4.Eliminar usuario");
            System.out.println("5.Salir");
            resp = leer.nextInt();
            switch (resp){
                case 1:
                    System.out.println("Ingresa nombre del usuario");
                    name = leer.next();
                    System.out.println("Ingresa apellido del usuario");
                    lastName = leer.next();
                    System.out.println("Ingresa email del usuario");
                    email = leer.next();
                    System.out.println("Ingresa contraseña del usuario");
                    password = leer.next();
                    System.out.println("Ingresa status del usuario");
                    status = leer.nextInt();
                    Object[] params1 = {name, lastName, email, password, status};
                    Boolean result = (Boolean) client.execute( "Handler.createUser",  params1);
                    if (result){
                        System.out.println("Usuario registrado!");
                    }else{
                        System.out.println("No se registro al usuario");
                    }
                    break;
                case 2:
                    Object[] params2 = {true};
                    String result2 = (String) client.execute( "Handler.findAll",params2);
                    System.out.println(result2);
                    break;
                case 3:
                    System.out.println("Ingresa el id del usuario que deseas modificar");
                    id = leer.nextInt();
                    System.out.println("Ingresa nombre del usuario");
                    name = leer.next();
                    System.out.println("Ingresa apellido del usuario");
                    lastName = leer.next();
                    System.out.println("Ingresa email del usuario");
                    email = leer.next();
                    System.out.println("Ingresa contraseña del usuario");
                    password = leer.next();
                    System.out.println("Ingresa status del usuario");
                    status = leer.nextInt();
                    Object[] params3 = {id, name, lastName, email, password, status};
                    Boolean result3 = (Boolean) client.execute( "Handler.updateUser",  params3);
                    if (result3){
                        System.out.println("Usuario modificado!");
                    }else{
                        System.out.println("No se pudo modificar al usuario");
                    }
                    break;
                case 4:
                    System.out.println("Ingresa el id del usuario que deseas eliminar");
                    id = leer.nextInt();
                    Object[] params4 = {id};
                    Boolean result4 = (Boolean) client.execute( "Handler.deleteUser",  params4);
                    if (result4){
                        System.out.println("Se ha eliminado al usuario");
                    }else {
                        System.out.println("No se pudo eliminar al usuario");
                    }
                    break;
                case 5:
                    System.out.println("Saliendo");
                    break;
                default:
                    System.out.println("Opción invalida");
            }
        }while (resp !=5);


    }
}

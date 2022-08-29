package com.auxProyect.auxProyect.controllers;

import com.auxProyect.auxProyect.Modelos.Usuario;
import com.auxProyect.auxProyect.dao.UsuarioDao;
import com.auxProyect.auxProyect.utils.JWTutil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioControl {

    //Inyección de dependecia
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTutil jwTutil;

    //Request que solo devuelve un user
    @RequestMapping(value = "api/usuario/{id}", method = RequestMethod.GET) //http://localhost:8080/usuario/1234
    public Usuario getUsuario(@PathVariable Long id){
        System.out.println("entro");
        Usuario usuario = new Usuario(id,"Jaime","Higueras","jaime@gail","58584883","12324");
        return usuario;
    }


    @RequestMapping(value = "api/prueba")
    public String prueba(){
        return "prueba";
    }



     @RequestMapping(value = "api/usuarios")
    public List<Usuario> getUsuarios(@RequestHeader(value = "Authorization")String token){
        //Comprobamos que el token de sesión es correcto

         try{
             String usuarioID = jwTutil.getKey(token);
             if(usuarioID == null){ //Si el navegador no tiene ningun token

             }
             return usuarioDao.getUsuarios();
         }catch (Exception e){
             System.out.println("NO EXISTE NINGUN TOKEN DE LOGEO");
             return null;
         }

    }


    //HTTP -> DELETE
    @RequestMapping(value = "api/usuario/{id}", method = RequestMethod.DELETE)
    public void eliminarUsuario(@PathVariable Long id){
        usuarioDao.eliminar(id);
    }


    @RequestMapping(value = "api/usuario", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario){

        //Encriptación de la password (Cuidado con el tamaño disponible en la bbdd)
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);
        String hash = argon2.hash(1,1024,1,usuario.getPassword());
        usuario.setPassword(hash);

        System.out.println(hash);
        System.out.println("entro en registar usuario");
        usuarioDao.registrar(usuario);
    }




}

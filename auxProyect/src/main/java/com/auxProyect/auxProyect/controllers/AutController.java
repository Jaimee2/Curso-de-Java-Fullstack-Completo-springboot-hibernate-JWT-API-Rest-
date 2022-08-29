package com.auxProyect.auxProyect.controllers;

import com.auxProyect.auxProyect.Modelos.Usuario;
import com.auxProyect.auxProyect.dao.UsuarioDao;
import com.auxProyect.auxProyect.utils.JWTutil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AutController {

    //Inyección de dependecia
    @Autowired
    private UsuarioDao usuarioDao;

    //Inyeccion de dependencia
    @Autowired
    private JWTutil jwTutil;


    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario usuario){
        System.out.println("entro en login usuario");

        Usuario userLog = usuarioDao.verificarSesion(usuario);

        if(userLog != null){
            //Gestión de la sesión
            String token = jwTutil.create(String.valueOf(userLog.getId()),userLog.getEmail());
            return token;

        }else{
            return "Fail";
        }
    }
}

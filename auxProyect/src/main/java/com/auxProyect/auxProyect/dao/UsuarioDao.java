package com.auxProyect.auxProyect.dao;

import com.auxProyect.auxProyect.Modelos.Usuario;

import java.util.List;

public interface UsuarioDao {

    List<Usuario> getUsuarios();

    void eliminar(Long id);

    void registrar(Usuario usuario);

    Usuario verificarSesion(Usuario usuario);
}

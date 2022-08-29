package com.auxProyect.auxProyect.dao;


import com.auxProyect.auxProyect.Modelos.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository //Es como un alias de @component añadiendo un valor semántioco
@Transactional
public class UsuarioDaoImp implements UsuarioDao{

    //Nos sierve para hacer la conexión con la BBDD
    @PersistenceContext
    public EntityManager entityManager;

    //Metodo que obtiene todos los usuarios que hay registrados en la BBDD
    @Override
    public List<Usuario> getUsuarios() {
        String query = "FROM Usuario"; //Cuidado no es SQL en from se indica el nombre del modelo Usuario
        //Obtenemos un listado de usuarios de la consulta
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class,id); //Obtenemos columna indicando clase e id
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }
    /*
    //Función que realiza una verificación
    //Si nos devuelve la consulta a la bbdd alguna fila -- >true
    @Override
    public boolean verificarSesion(Usuario usuario) {
        String query = "FROM Usuario WHERE email = :email AND password = :password "; //Cuidado no es SQL en from se indica el nombre del modelo Usuario
        //Obtenemos un listado de usuarios de la consulta
        List <Usuario> lista = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .setParameter("password",usuario.getPassword()).getResultList();

        if(lista.isEmpty()){
            return false;
        }else {
            return true;
        }
    }
*/
    @Override
   /* public boolean verificarSesion(Usuario usuario) {
        String query = "FROM Usuario WHERE email = :email"; //Cuidado no es SQL en from se indica el nombre del modelo Usuario
        //Obtenemos un listado con los user que machean con ese email
        List <Usuario> lista = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();

        if(lista.isEmpty()){
            return false;
        }else {
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);
            //Hash obtenido de la bbdd
            String passwordHash = lista.get(0).getPassword();
            //Paswword que nos pasan desde el loging --> usuario.getPassword()
            return argon2.verify(passwordHash,usuario.getPassword()); //True si es la misma password
        }


    }*/


    public Usuario verificarSesion(Usuario usuario) {
        String query = "FROM Usuario WHERE email = :email"; //Cuidado no es SQL en from se indica el nombre del modelo Usuario
        //Obtenemos un listado con los user que machean con ese email
        List <Usuario> lista = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();

        if(lista.isEmpty()){
            return null;
        }else { //Comprobamos password
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);
            //Hash obtenido de la bbdd
            String passwordHash = lista.get(0).getPassword();
            //Paswword que nos pasan desde el loging --> usuario.getPassword()
             if(argon2.verify(passwordHash,usuario.getPassword())){
                //Devovlemos el primer user de la lista
                 return lista.get(0);
            }else{
                 return null;
             }
        }
    }


}


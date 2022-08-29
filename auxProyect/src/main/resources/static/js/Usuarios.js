// Call the dataTables jQuery plugin
//Darle funcionalidad a la tabla
$(document).ready(function() {

  $('#Usuarios').DataTable();
  getUsuarios();
  actualizarEmailUser();
});

function   actualizarEmailUser(){
    document.getElementById('txt-email').textContent = localStorage.email;
}


function getHeaders() {
    return {
     'Accept': 'application/json',
     'Content-Type': 'application/json',
     'Authorization': localStorage.token
   };
}


async function getUsuario(){
                  //await espera el resultado
  const request = await fetch('http://localhost:8080/usuario/1234', {
    method: 'GET',
    headers: getHeaders()
  });
  const usuario = await request.json();

  console.log(usuario);

}

async function getUsuarios(){
                  //await espera el resultado
  const request = await fetch('http://localhost:8080/api/usuarios', {
    method: 'GET',
    headers: getHeaders()
  });
  const usuarios = await request.json();

  console.log(usuarios);

    let usuarioHTML = '';
    let telefonoText = '';
    for(let usuario of usuarios){
        telefonoText = usuario.telefono == null ? "Sin tlf" : usuario.telefono;
        console.log("entro en el forecha");
        console.log("telefono :    "+ telefonoText);
         usuarioHTML +=  '<tr>' +
                                 '<td>'+usuario.id+'</td>' +
                                 '<td>'+usuario.nombre +' '+usuario.apellido+'</td>' +
                                 '<td>'+usuario.email+'</td>' +
                                 '<td>'+telefonoText+'</td>' +
                                 '<td><a href="#" onclick = "eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle btn-sm">' +
                                     '<i class="fas fa-trash"></i>' +
                                 '</a></td>' +
                          '</tr>';
    }

     document.querySelector('#Usuarios tbody').outerHTML = usuarioHTML;
}

async function eliminarUsuario(id){

    if(confirm('¿Desea eliminar este usuario?')){
        const request = await fetch('http://localhost:8080/api/usuario/' + id, {
            method: 'DELETE',
            headers: getHeaders()
        });
    }
    //No es lo ideal pero para ir más rapido recargamos la página
    location.reload();
}

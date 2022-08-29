// Call the dataTables jQuery plugin
//Darle funcionalidad a la tabla
$(document).ready(function() {
    //On ready
});
//Cuado se inicie la página llamamos a la funcion para cagar los usuarios



async function login(){

    let  datos = {};
    datos.email = document.getElementById('txtEmail').value;
    datos.password = document.getElementById('txtPassword').value;

                  //await espera el resultado
  const request = await fetch('http://localhost:8080/api/login', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
     body: JSON.stringify(datos) //Combierte un objeto js a json
  });

  const respond = await request.text();

    if(respond != 'Fail'){
        localStorage.token = respond;
        localStorage.email = datos.email;

        window.location.href = 'Usuarios.html'
    }else{
    console.log("entro");
        alert("Las credenciales no correctas, intentelo de nuevo");
    }

}
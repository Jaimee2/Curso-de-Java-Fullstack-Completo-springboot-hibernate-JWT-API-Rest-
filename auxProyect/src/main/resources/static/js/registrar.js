
async function registrarUsuarios(){
 console.log("no entiendo que pasa aqui");
    let  datos = {};
    datos.nombre = document.getElementById('txtNombre').value;
    datos.apellido = document.getElementById('txtApellido').value;
    datos.email = document.getElementById('txtEmail').value;
    datos.password = document.getElementById('txtPassword').value;

                  //await espera el resultado
  const request = await fetch('http://localhost:8080/api/usuario', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
     body: JSON.stringify(datos) //Combierte un objeto js a json
  });
  console.log('response.status: ', request.status); // 👉️ 200
  if(request.status == 200){
    alert('La cuenta fue creada con éxito!');
    window.location.href = 'login.html'
  }

}



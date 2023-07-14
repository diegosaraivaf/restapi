

function salvar(){
	
	var url = "http://localhost:8080/usuarios/"
	axios.post(url,
			{
				email:document.getElementById('usuario').value ,
				senha:document.getElementById('senha').value
			}
		)
		.then(response => { 
			console.log(response)
		})
		.catch(error => {
			new Notify ({
			    title: 'Sucesso',
			    text: error.response.data,
			    autoclose: true,
			    autotimeout: 3000,
			    status: 'error'/*‘success’, ‘error’, or ‘warning’*/
			})
		});

}

document.getElementById('btSalvar').addEventListener('click',e =>salvar())
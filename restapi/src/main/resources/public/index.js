


function logar(){
	var url = "usuarios/autenticar"
	axios.post(url,
			{
				email:document.getElementById('usuario').value ,
				senha:document.getElementById('senha').value
			}
		)
		.then(response => { 
			console.log(response)		
			localStorage.setItem('Authorization', 'Bearer '+response.data.token);
			window.location.href = "home.html"
			
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


document.getElementById('btLogin').addEventListener('click',e =>logar())
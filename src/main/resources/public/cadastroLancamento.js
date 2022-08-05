app()

function app(){
	manipularFormulario()
}

function manipularFormulario() {

	const form_lancamento = document.getElementById('form-lancamento')
	
	form_lancamento.onsubmit = async (event) => {
	}
}

async function salvar(){
	event.preventDefault()

	const inputTipo = document.getElementById('tipo')
	const inputValor = document.getElementById('valor')
	
	await axios.post('http://localhost:8080/lancamentos',{
		tipo : inputTipo.value,
		valor : inputValor.value
	})
	
	window.location.href = "http://localhost:8080/consultaLancamento.html";
}


document.getElementById('salvar').addEventListener('click',salvar)


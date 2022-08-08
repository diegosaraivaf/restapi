axios.defaults.baseURL = 'http://localhost:8080'
	
app()

var documento = null
var tipo = null
var valor = null
var contribuinteId = null

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

	
	
	await axios.post('lancamentos',{
		tipoLancamento : tipo,
		valor : valor,
		contribuinte : {
			id : contribuinteId
		},
		parcelas : [
			{valor:100}
		]
	})
	
	window.location.href = "consultaLancamento.html";
}

aoMudarDocumento = (event) => {
	
	axios.get(`contribuintes/documento/${event.target.value}`).then(response => {
		document.getElementById('nome').value = response.data.nome
		contribuinteId = response.data.id
	})
}

document.getElementById('salvar').addEventListener('click',salvar)
document.getElementById('documento').addEventListener('change',e => aoMudarDocumento(e))
document.getElementById('tipo').addEventListener('change',e => {tipo = e.target.value })
document.getElementById('valor').addEventListener('change',e => {valor = e.target.value })


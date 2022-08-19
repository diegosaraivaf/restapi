import {getValoresTBody} from './util.js'

axios.defaults.baseURL = 'http://localhost:8080'
var inputDocumento = document.getElementById('documento')
var tipo = null
var inputValor = document.getElementById('valor')
var contribuinteId = null

window.onload = function () {
	var params = window.location.search.substring(1).split('&');
	
	if(params.length > 0 && params[0] != ""){
		carregarInformacoes(params[0])
	}
 }

const carregarInformacoes = (id) =>{
	
	axios.get(`lancamentos/${id}`)
		.then(response => {
			var lancamento = response.data
			inputDocumento.value = response.data.contribuinte.documento
			inputValor.value = response.data.valor
			
			/*contribuinte = response.data*/
		}).catch(error => {
			console.log(error)
		})
	
}

/*
  app()
  function app(){
	manipularFormulario()
}

function manipularFormulario() {
	const form_lancamento = document.getElementById('form-lancamento')
	
	form_lancamento.onsubmit = async (event) => {
	}
}*/

const salvar = async () => {
	event.preventDefault()
	var tabela = getValoresTBody('tableData')
	var parcelas = []
	
	for(let tab of tabela){
		parcelas.push({
			valor: tab[1]
		})
	}
	
	await axios.post('lancamentos',{
		tipoLancamento : tipo,
		valor : valor,
		contribuinte : {
			id : contribuinteId
		},
		parcelas : parcelas
	})
	
	window.location.href = "consultaLancamento.html";
}

const aoMudarDocumento = (event) => {
	axios.get(`contribuintes/documento/${event.target.value}`).then(response => {
		document.getElementById('nome').value = response.data.nome
		contribuinteId = response.data.id
	})
}

const aoMudarQtdParcelas = (e) =>{
	var qtdParcelas  = e.target.value
	const tableBody  = document.getElementById('tableData')
	var valorParcela = valor / qtdParcelas
	
	tableBody.innerHTML = ''	
	for(var i = 0; i < qtdParcelas; i++ ){
		tableBody.innerHTML = tableBody.innerHTML += 
			`<tr>
				<th>${i+1}</th>
				<th>${valorParcela}</th>
			</tr>`;
	}
	/*document.querySelectorAll(".removerLinha").forEach( e => e.addEventListener("click", (e) => {removerLinha(e.target)}))*/
}


document.getElementById('voltar').addEventListener('click',e =>{ window.location.href = "http://localhost:8080/consultaLancamento.html" })
document.getElementById('salvar').addEventListener('click',salvar)
document.getElementById('documento').addEventListener('change',e => aoMudarDocumento(e))
document.getElementById('tipo').addEventListener('change',e => {tipo = e.target.value })
document.getElementById('valor').addEventListener('change',e => {valor = e.target.value })
document.getElementById('qtdParcela').addEventListener('keyup',e => aoMudarQtdParcelas(e))

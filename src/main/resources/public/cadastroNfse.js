import {getValoresTBody,preecherTabela} from './util.js'

axios.defaults.baseURL = 'http://localhost:8080'
var inputPrestador = document.getElementById('prestador')
var inputTomador = document.getElementById('tomador')
var inputLocalPrestacao = document.getElementById('localPrestacao')
var inputValor = document.getElementById('valor')
var inputItens = document.getElementById('itens')
var prestador = null
var tomador = null

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
			lancamentoId = lancamento.id
			inputDocumento.value = lancamento.contribuinte.documento
			inputNome.value = lancamento.contribuinte.nome
			contribuinteId = lancamento.contribuinte.id
			inputValor.value = lancamento.valor
			inputQtdParcela.value = lancamento.parcelas.length
			selectTipo.value = lancamento.tipoLancamento
			
			var parcelas = []
			lancamento.parcelas.forEach(e => {
				parcelas.push([e.id,e.valor])
			})
			preecherTabela('tableData',parcelas)
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

	var nfse = {
		prestador,
		tomador,
		localPrestacao : inputLocalPrestacao.value,
		valor : inputValor.value,
		listaItens : inputItens.value
	}
	
	/*if(lancamentoId != null){
		await axios.put('lancamentos/'+lancamentoId,lancamento)
	}
	else{
		await axios.post('lancamentos',nfse)
	}*/
	await axios.post('nfses',nfse)
	
	window.location.href = "consultaNfse.html";
}

const aoMudarPrestador = (event) => {
	axios.get(`contribuintes/documento/${event.target.value}`).then(response => {
		document.getElementById('prestadorNome').value = response.data.nome
		prestador = response.data
	})
}

const aoMudarTomador = (event) => {
	axios.get(`contribuintes/documento/${event.target.value}`).then(response => {
		document.getElementById('tomadorNome').value = response.data.nome
		tomador = response.data
	})
}

const aoMudarQtdParcelas = (e) =>{
	var qtdParcelas  = e.target.value
	var valorParcela = inputValor.value / qtdParcelas
	var dados = []
	
	for(var i = 0; i < qtdParcelas; i++ ){
		dados.push([i+1,valorParcela])
	}
	
	preecherTabela('tableData',dados)
	
	
//	var qtdParcelas  = e.target.value
//	const tableBody  = document.getElementById('tableData')
//	var valorParcela = valor / qtdParcelas
//	
//	tableBody.innerHTML = ''	
//	for(var i = 0; i < qtdParcelas; i++ ){
//		tableBody.innerHTML = tableBody.innerHTML += 
//			`<tr>
//				<th>${i+1}</th>
//				<th>${valorParcela}</th>
//			</tr>`;
//	}
	/*document.querySelectorAll(".removerLinha").forEach( e => e.addEventListener("click", (e) => {removerLinha(e.target)}))*/
}




document.getElementById('voltar').addEventListener('click',e =>{ window.location.href = "http://localhost:8080/consultaLancamento.html" })
document.getElementById('salvar').addEventListener('click',salvar)
document.getElementById('prestadorDocumento').addEventListener('change',e => aoMudarPrestador(e))
document.getElementById('tomadorDocumento').addEventListener('change',e => aoMudarTomador(e))

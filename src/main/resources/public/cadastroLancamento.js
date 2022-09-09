import {getValoresTBody,preecherTabela} from './util.js'

axios.defaults.baseURL = 'http://localhost:8080'
var inputDocumento = document.getElementById('documento')
var inputNome = document.getElementById('nome')
var inputQtdParcela = document.getElementById('qtdParcela')
var selectTipo = document.getElementById('tipo')
var inputValor = document.getElementById('valor')
var contribuinteId = null
var lancamentoId = null   

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
	var tabela = getValoresTBody('tableData')
	var parcelas = []
	
	for(let tab of tabela){
		parcelas.push({
			valor: tab[1]
		})
	}
	
	var lancamento = {
		tipoLancamento : selectTipo.value,
		valor : inputValor.value,
		contribuinte : {
			id : contribuinteId
		},
		parcelas : parcelas
	}
	
	if(lancamentoId != null){
		await axios.put('lancamentos/'+lancamentoId,lancamento)
	}
	else{
		await axios.post('lancamentos',lancamento)
	}
	
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
document.getElementById('documento').addEventListener('change',e => aoMudarDocumento(e))
document.getElementById('tipo').addEventListener('change',e => {tipo = e.target.value })
document.getElementById('valor').addEventListener('change',e => {inputValor.value = e.target.value })
document.getElementById('qtdParcela').addEventListener('keyup',e => aoMudarQtdParcelas(e))

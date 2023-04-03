import {getValoresTBody,preecherTabela} from './util.js'

axios.defaults.baseURL = 'http://localhost:8080'
var inputPrestador = document.getElementById('prestador')
var inputTomador = document.getElementById('tomador')
var inputLocalPrestacao = document.getElementById('localPrestacao')
var inputValor = document.getElementById('valor')
var inputItens = document.getElementById('itens')
var prestador = null
var tomador = null
var itensNota = []

window.onload = function () {
	var params = window.location.search.substring(1).split('&');
	
	if(params.length > 0 && params[0] != ""){
		carregarInformacoes(params[0])
	}
 }

const carregarInformacoes = (id) =>{
	axios.get(`nfses/${id}`)
		.then(response => {
			var nfse = response.data
			inputPrestador.value = nfse.prestador.documento
			inputTomador.value = nfse.tomaodr.documento
			
			var itemTable = []
			nfse.itensNfse.forEach(i => {
				itemTable.push([i.id,i.descricao])
			})
			preecherTabela('tableData',itemTable)
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
	var itensNfse = []
	
	for(let tab of tabela){
		itensNfse.push({
			descricao: tab[1],
			valor: tab[2].replaceAll('.', '').replaceAll(',', '.'),
			quantidade: tab[3]
		})
	}

	var nfse = {
		prestador,
		tomador,
		localPrestacao : inputLocalPrestacao.value,
		valor : inputValor.value,
		itensNfse : itensNfse
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

const adicionarItem = (e) =>{
	event.preventDefault()
	
	itensNota.push([
		itensNota.length +1,
		document.getElementById('itemDescricao').value,
		document.getElementById('itemValor').value,
		document.getElementById('itemQuantidade').value
	])
	
	preecherTabela('tableData',itensNota)
	
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
document.getElementById('adicionar').addEventListener('click',e => adicionarItem(e))




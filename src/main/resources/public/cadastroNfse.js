import {getValoresTBody,preecherTabela} from './util.js'

//eu poderia colocar a mudanca de estado do objeto no onchage dos inputs filds ,fazer isso depois,acho que necesse senario aqui e mais dificil gerencia o estado do objeto, melhor apenas construi o objeto no fim 

axios.defaults.baseURL = 'http://localhost:8080'
var inputPrestadorDocumento = document.getElementById('prestadorDocumento')
var inputTomadorDocumento = document.getElementById('tomadorDocumento')
var inputPrestadorNome = document.getElementById('prestadorNome')
var inputTomadorNome = document.getElementById('tomadorNome')
var inputLocalPrestacao = document.getElementById('localPrestacao')
var inputValor = document.getElementById('valor')
var inputItens = document.getElementById('itens')
var prestador = {}
var tomador = {}
var itensNota = []
var idNfse = null

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
			idNfse = nfse.id
			prestador = nfse.prestador
			inputPrestadorDocumento.value = nfse.prestador.documento
			inputPrestadorNome.value = nfse.prestador.nome
			tomador = nfse.tomador
			inputTomadorDocumento.value = nfse.tomador.documento
			inputTomadorNome.value = nfse.tomador.nome
			inputLocalPrestacao.value = nfse.localPrestacao
			inputValor.value = nfse.valorServico
			
			nfse.itensNfse.forEach(i => {
				itensNota.push([i.id,i.descricao,i.valor,i.quantidade])
			})
			preecherTabela('tableData',itensNota)
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




function validar(event) {
	// Fetch all the forms to apply custom Bootstrap validation styles
	var forms = document.querySelectorAll('.needs-validation');

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms).forEach(function(form) {
      //  event.stopPropagation();
        form.classList.add('was-validated');
    });
    
		     // Stop execution if there are invalid forms
    var invalidForms = document.querySelectorAll('.was-validated:invalid');
    if (invalidForms.length > 0) {
        return true;
    }
}

const salvar = async (event) => {
	event.preventDefault()
	if(validar(event)){return}

	
	var tabela = getValoresTBody('tableData')
	var itensNfse = []
	
	for(let tab of tabela){
		itensNfse.push({
			id: tab[0],
			descricao: tab[1],
			valor: tab[2].replaceAll('.', '').replaceAll(',', '.'),
			quantidade: tab[3]
		})
	}
	
	if(prestador.id == null){
		prestador.documento = inputPrestadorDocumento.value
		prestador.nome = inputPrestadorNome.value
		
		const response = await axios.post('contribuintes/',prestador,{headers : { 'Authorization': localStorage.getItem('Authorization')}})
		prestador = response.data
	}
	if(tomador.id == null){
		tomador.documento = inputTomadorDocumento.value
		tomador.nome = inputTomadorNome.value
		
		const response = await axios.post('contribuintes/',tomador,{headers : { 'Authorization': localStorage.getItem('Authorization')}})
		tomador = response.data
	}

	var nfse = {
		prestadorId : prestador.id,
		tomadorId :tomador.id ,
		localPrestacao : inputLocalPrestacao.value,
		valorServico : inputValor.value,
		itensNfse : itensNfse
	}
	
	if(idNfse != null){
		await axios.put('nfses/'+idNfse,nfse,{headers : { 'Authorization': localStorage.getItem('Authorization')}})
	}
	else{
		await axios.post('nfses',nfse,{headers : { 'Authorization': localStorage.getItem('Authorization')}})
	}
	
	window.location.href = "consultaNfse.html";
}

const aoMudarPrestador = (event) => {
	axios.get(`contribuintes/documento/${event.target.value}`).then(response => {
		if(response.data.id != null){
			document.getElementById('prestadorNome').value = response.data.nome
			prestador = response.data
			inputPrestadorNome.readOnly = true
		}else{
			inputPrestadorNome.readOnly = false
			inputPrestadorNome.value = ''
			prestador = {}
		}
	})

}

const aoMudarTomador = (event) => {
	axios.get(`contribuintes/documento/${event.target.value}`).then(response => {
		if(response.data.id != null){
			document.getElementById('tomadorNome').value = response.data.nome
			tomador = response.data
			inputTomadorNome.readOnly = true
		}else{
			inputTomadorNome.readOnly = false
			inputTomadorNome.value = ''
			tomador = {}
		}
	})
}

const adicionarItem = (e) =>{
	event.preventDefault()
	
	itensNota.push([
		'',
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





document.getElementById('voltar').addEventListener('click',e =>{ window.location.href = "http://localhost:8080/consultaNfse.html" })
document.getElementById('salvar').addEventListener('click',salvar)
document.getElementById('prestadorDocumento').addEventListener('change',e => aoMudarPrestador(e))
document.getElementById('tomadorDocumento').addEventListener('change',e => aoMudarTomador(e))
document.getElementById('adicionar').addEventListener('click',e => adicionarItem(e))




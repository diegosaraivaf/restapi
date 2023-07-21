
import {teste} from './teste.js';
import {getValoresTBody,preecherTabela} from './util.js'
//import {axios} from './axios.min.js';


function app(){
	manipularFormulario()
}

function manipularFormulario() {
	const form_lancamento = document.getElementById('form-nfse')
	
	form_lancamento.onsubmit = async (event) => {
		event.preventDefault()
		
		pesquisar()
	}
}

async function pesquisar(){
	const inputId = document.getElementById('id').value
	const inputTipo = document.getElementById('tipo').value
	const inputValor = document.getElementById('valor').value
	const selectSituacaoNfse = document.getElementById('situacaoNfse').value
	var page  = Number(document.getElementById('lbPaginaAtual').innerText) === 0 ? 0 : Number(document.getElementById('lbPaginaAtual').innerText) -1
	
	var url = `http://localhost:8080/nfses?sort=prestador.nome,asc&size=5&page=${page}`

	if(inputId.length > 0){
		url = url+'&id='+ inputId;
	}
	if(inputTipo.length > 0){
		url = url+'&tipo='+ inputTipo;
	}
	if(inputValor.length > 0){
		url = url+'&valor='+ inputValor;
	}
	if(selectSituacaoNfse.length > 0){
		url = url+'&situacaoNfse='+ selectSituacaoNfse;
	}
	
	var response
	try{
		response = await axios.get(url,{headers : { 'Authorization': localStorage.getItem('Authorization')}})
	}catch(error){
		new Notify ({
		    title: 'Erro',
		    text: error.response.data,
		    autoclose: true,
		    autotimeout: 3000,
		    status: 'error'/*‘success’, ‘error’, or ‘warning’*/
		})
	}
	
	const nfses  = response.data.content
	document.getElementById('lbPaginaAtual').innerText = response.data.pageable.pageNumber +1
	document.getElementById('lbTotalPaginas').innerText = Number(response.data.totalPages)  
	
	var notas = []
	nfses.forEach(e => {
		 //tive divicudade ao colocar nome do metodo no botao,ele nao encontrava por alguma motivo, pode ter relacao com o module 
		notas.push([
			e.id,
			e.dataEmissao,
			e.prestador?e.prestador.documento +'-'+e.prestador.nome :'' ,
			e.tomador?e.tomador.documento+'-'+e.tomador.nome:'' ,
			e.valorServico,
			e.situacaoNfse,
			`<input type="button" class="btn btn-secondary" onclick="editar(${e.id})"  value="Editar" >`+
			`<input type="button" class="btn btn-danger" onclick="excluir(${e.id})"  value="Cancelar" >`
		])
	})
	preecherTabela('tableData',notas)
	
}
	
async function proximo(){
	const inputId = document.getElementById('id').value
	const inputTipo = document.getElementById('tipo').value
	const inputValor = document.getElementById('valor').value
	const selectSituacaoNfse = document.getElementById('situacaoNfse').value
	var url = `http://localhost:8080/nfses?sort=id&size=5&page=${Number(document.getElementById('lbPaginaAtual').innerText)}`

	if(inputId.length > 0){
		url = url+'&id='+ inputId;
	}
	if(inputTipo.length > 0){
		url = url+'&tipo='+ inputTipo;
	}
	if(inputValor.length > 0){
		url = url+'&valor='+ inputValor;
	}
	if(selectSituacaoNfse.length > 0){
		url = url+'&situacaoNfse='+ selectSituacaoNfse;
	}
	
	var response = await axios.get(url,{headers : { 'Authorization': localStorage.getItem('Authorization')}})
	document.getElementById('lbPaginaAtual').innerText = response.data.pageable.pageNumber +1
	document.getElementById('lbTotalPaginas').innerText = Number(response.data.totalPages)  
	console.log(response)
	
	const nfses  = response.data.content
	
	var notas = []
	nfses.forEach(e => {
		 //tive divicudade ao colocar nome do metodo no botao,ele nao encontrava por alguma motivo, pode ter relacao com o module 
		notas.push([
			e.id,
			e.dataEmissao,
			e.prestador?e.prestador.documento +'-'+e.prestador.nome :'' ,
			e.tomador?e.tomador.documento+'-'+e.tomador.nome:'' ,
			e.valorServico,
			e.situacaoNfse,
			`<input type="button" class="btn btn-secondary" onclick="editar(${e.id})"  value="Editar" >`+
			`<input type="button" class="btn btn-danger" onclick="excluir(${e.id})"  value="Cancelar" >`
		])
	})
	preecherTabela('tableData',notas)
}

function voltar(){
	const inputId = document.getElementById('id').value
	const inputTipo = document.getElementById('tipo').value
	const inputValor = document.getElementById('valor').value
	const selectSituacaoNfse = document.getElementById('situacaoNfse').value
	var url = `http://localhost:8080/nfses?sort=id&size=5&page=${Number(document.getElementById('lbPaginaAtual').innerText -2)}`

	if(inputId.length > 0){
		url = url+'&id='+ inputId;
	}
	if(inputTipo.length > 0){
		url = url+'&tipo='+ inputTipo;
	}
	if(inputValor.length > 0){
		url = url+'&valor='+ inputValor;
	}
	if(selectSituacaoNfse.length > 0){
		url = url+'&situacaoNfse='+ selectSituacaoNfse;
	}
	
	axios.get(url,{headers : { 'Authorization': localStorage.getItem('Authorization')}})
		.then(response => { 
			const nfses  = response.data.content
			document.getElementById('lbPaginaAtual').innerText = response.data.pageable.pageNumber +1
			document.getElementById('lbTotalPaginas').innerText = Number(response.data.totalPages)  
	
			var notas = []
			nfses.forEach(e => {
				 //tive divicudade ao colocar nome do metodo no botao,ele nao encontrava por alguma motivo, pode ter relacao com o module 
				notas.push([
					e.id,
					e.dataEmissao,
					e.prestador?e.prestador.documento +'-'+e.prestador.nome :'' ,
					e.tomador?e.tomador.documento+'-'+e.tomador.nome:'' ,
					e.valorServico,
					e.situacaoNfse,
					`<input type="button" class="btn btn-secondary" onclick="editar(${e.id})"  value="Editar" >`+
					`<input type="button" class="btn btn-danger" onclick="excluir(${e.id})"  value="Cancelar" >`
				])
			})
			preecherTabela('tableData',notas)
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

function editar(id) {
	window.location.href = `http://localhost:8080/cadastroNfse.html?${id}`;
}
window.editar = editar;

function excluir(id) {
	console.log('excluir id=' + id);
	axios.delete(`http://localhost:8080/nfses/${id}`,{headers : { 'Authorization': localStorage.getItem('Authorization')}}).then(response =>{
		pesquisar()
	}).catch(error =>{
		console.log(error)
	})
}
window.excluir = excluir;


async function gerarPdf(){
	const inputId = document.getElementById('id').value
	const inputTipo = document.getElementById('tipo').value
	const inputValor = document.getElementById('valor').value
	const selectSituacaoNfse = document.getElementById('situacaoNfse').value
	var page  = Number(document.getElementById('lbPaginaAtual').innerText) === 0 ? 0 : Number(document.getElementById('lbPaginaAtual').innerText) -1
	
	var url = `http://localhost:8080/nfses/pdf?sort=id`

	if(inputId.length > 0){
		url = url+'&id='+ inputId;
	}
	if(inputTipo.length > 0){
		url = url+'&tipo='+ inputTipo;
	}
	if(inputValor.length > 0){
		url = url+'&valor='+ inputValor;
	}
	if(selectSituacaoNfse.length > 0){
		url = url+'&situacaoNfse='+ selectSituacaoNfse;
	}
	 
	fetch(url,{headers : { 'Authorization': localStorage.getItem('Authorization')}})
	.then(response => response.blob())
	.then(blob => {
	   // Create an object URL from the response blob
	   const objectUrl = URL.createObjectURL(blob);
	    
	   // Open the URL in a new window or tab
	   window.open(objectUrl, '_blank');
	})
	.catch(error => {
	   console.error('Error:', error);
	});
}



app()
document.getElementById('pesquisar').addEventListener('click', pesquisar)
document.getElementById('btnProximo').addEventListener('click', proximo)
document.getElementById('btnVoltar').addEventListener('click', voltar)
document.getElementById('btnPdf').addEventListener('click', gerarPdf)
document.getElementById('nome').addEventListener('change',(e) => {nome = e.target.value })




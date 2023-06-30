
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
	
	var url = 'http://localhost:8080/nfses?sort=id&size=5&page=0'

	if(inputId.length > 0){
		url = url+'&id='+ inputId;
	}
	if(inputTipo.length > 0){
		url = url+'&tipo='+ inputTipo;
	}
	if(inputValor.length > 0){
		url = url+'&valor='+ inputValor;
	}
	
	var response
	try{
		response = await axios.get(url)
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
			`<input type="button" class="btn btn-secondary" onclick="window.location.href = 'http://localhost:8080/cadastroNfse.html?${e.id}'"  value="Editar" >`
		])
	})
	preecherTabela('tableData',notas)
	
}

//function editar(id) {
//	console.log('teste')
	//window.location.href = `http://localhost:8080/cadastroNfse.html?${id}`;
//}

const editar = (id) => {
	console.log('teste')
}



var paginaAtual  
var totalPaginas
	
async function proximo(){
	const inputId = document.getElementById('id').value
	const inputTipo = document.getElementById('tipo').value
	const inputValor = document.getElementById('valor').value
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
	
	var response = await axios.get(url)
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
			`<input type="button" class="btn btn-secondary" onclick="window.location.href = 'http://localhost:8080/cadastroNfse.html?${e.id}'"  value="Editar" >`
		])
	})
	preecherTabela('tableData',notas)
}

function voltar(){
	const inputId = document.getElementById('id').value
	const inputTipo = document.getElementById('tipo').value
	const inputValor = document.getElementById('valor').value
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
	
	axios.get(url)
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
					`<input type="button" class="btn btn-secondary" onclick="window.location.href = 'http://localhost:8080/cadastroNfse.html?${e.id}'"  value="Editar" >`
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

app()
document.getElementById('pesquisar').addEventListener('click', pesquisar)
document.getElementById('btnProximo').addEventListener('click', proximo)
document.getElementById('btnVoltar').addEventListener('click', voltar)
document.getElementById('nome').addEventListener('change',(e) => {nome = e.target.value })





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
	
	var url = 'http://localhost:8080/nfses?size=5&page=0'

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
	const nfses  = response.data.content
	
	var notas = []
	nfses.forEach(e => {
		 //tive divicudade ao colocar nome do metodo no botao,ele nao encontrava por alguma motivo, pode ter relacao com o module 
		notas.push([
			e.id,
			e.prestador?e.prestador.documento:'' ,
			e.tomador?e.tomador.documento:'' ,
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


var page = 0
async function proximo(){
	page = page +1
	var response = await axios.get(`http://localhost:8080/nfses?size=5&page=${page}`)
	console.log(response)
	
	const nfses  = response.data.content
	
	var notas = []
	nfses.forEach(e => {
		 //tive divicudade ao colocar nome do metodo no botao,ele nao encontrava por alguma motivo, pode ter relacao com o module 
		notas.push([
			e.id,
			e.prestador?e.prestador.documento:'' ,
			e.tomador?e.tomador.documento:'' ,
			e.valorServico,
			`<input type="button" class="btn btn-secondary" onclick="window.location.href = 'http://localhost:8080/cadastroNfse.html?${e.id}'"  value="Editar" >`
		])
	})
	preecherTabela('tableData',notas)
}

app()
document.getElementById('pesquisar').addEventListener('click', pesquisar)
document.getElementById('btnProximo').addEventListener('click', proximo)
document.getElementById('nome').addEventListener('change',(e) => {nome = e.target.value })




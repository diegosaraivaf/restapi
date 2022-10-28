import {teste} from './teste.js';
import {getValoresTBody,preecherTabela} from './util.js'
/*import {axios} from './axios.min.js';*/

function app(){
	manipularFormulario()
}

function manipularFormulario() {

	
	const form_lancamento = document.getElementById('form-lancamento')
	
	form_lancamento.onsubmit = async (event) => {
		event.preventDefault()
		
		pesquisarLancamento()
	}
}


async function pesquisar(){
	const inputId = document.getElementById('id').value
	const inputTipo = document.getElementById('tipo').value
	const inputValor = document.getElementById('valor').value
	
	var url = 'http://localhost:8080/nfses?'
	
	if(inputId != null){
		url = url+'&id='+ inputId;
	}
	if(inputTipo != null){
		url = url+'&tipo='+ inputTipo;
	}
	if(inputValor != null){
		url = url+'&valor='+ inputValor;
	}
	
	const response = await axios.get(url)
	const nfses  = response.data
	
	console.log(nfses)
	
	var notas = []
	nfses.forEach(e => {
		 
		notas.push([
			e.id,
			e.prestador?e.prestador.documento:'' ,
			e.tomador?e.tomador.documento:'' ,
			e.valorServico])
	})
	preecherTabela('tableData',notas)
	
}

app()
document.getElementById('pesquisar').addEventListener('click',pesquisar)
document.getElementById('nome').addEventListener('change',(e) => {nome = e.target.value })

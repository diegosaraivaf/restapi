import {teste} from './teste.js';
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
	const form_lancamento = document.getElementById('form-lancamento')
	const inputId = document.getElementById('id').value
	const inputTipo = document.getElementById('tipo').value
	const inputValor = document.getElementById('valor').value
	
	var url = 'http://localhost:8080/lancamentos?'
	
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
	const lancamentos  = response.data
	
	
	
	const tableLancamento = document.getElementById('tableLancamento')
	tableLancamento.innerHTML = ''
	lancamentos.forEach(l => {
		var tr = document.createElement("tr"); // cria o elemento tr
		
		var td = document.createElement("td"); // cria o element td
		var lancamentoId = document.createTextNode(l.id); 
		td.appendChild(lancamentoId); // adiciona o texto na td criada
		tr.appendChild(td); // adiciona a td na tr
		
		var td = document.createElement("td"); // cria o element td
		var textnode = document.createTextNode(l.tipo); 
		td.appendChild(textnode); // adiciona o texto na td criada
		tr.appendChild(td); // adiciona a td na tr
		
		var td = document.createElement("td"); // cria o element td
		var textnode = document.createTextNode(l.valor); 
		td.appendChild(textnode); // adiciona o texto na td criada
		tr.appendChild(td); // adiciona a td na tr
		
		var td = document.createElement("td"); // cria o element td
		var textnode =  document.createElement('BUTTON');
		//textnode.nodeValue('teste')
		td.appendChild(textnode); // adiciona o texto na td criada
		textnode.innerHTML = 'Deletar'
		tr.appendChild(td); // adiciona a td na tr
		
		textnode.onclick = async function()
	     {
	        
			await axios.delete("http://localhost:8080/lancamentos/"+lancamentoId.textContent,{})
			alert('item removido ' +lancamentoId.textContent)
			
			pesquisarLancamento()
	     }
		tableLancamento.appendChild(tr)
		
		var td = document.createElement("td"); // cria o element td
		var btDetalhe =  document.createElement('BUTTON');
		td.appendChild(btDetalhe); // adiciona o texto na td criada
		btDetalhe.innerHTML = 'Detalhar'
		tr.appendChild(td); // adiciona a td na tr
		
		btDetalhe.onclick = async function()
	     {
	        console.log('teste detalhe')
			axios.get("http://localhost:8080/lancamentos/"+lancamentoId.textContent,{}).then(
				response => {
					console.log(response.data)
					window.location.href = "http://localhost:8080/cadastroLancamento.html";
					var inputTipo = document.getElementById('tipo')
					
					inputTipo.nodeValue('teste')
					
				}
			
			)
			

	     }
		tableLancamento.appendChild(tr)
		

	});
	
	/*axios.get("http://localhost:8080/lancamentos").then(response => console.log(response.data))*/
}

app()
document.getElementById('pesquisar').addEventListener('click',pesquisar)

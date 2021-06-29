app()

function app(){
	listarLancamento()
	manipularFormulario()
}

function manipularFormulario() {

	const form_lancamento = document.getElementById('form-lancamento')
	const inputTipo = document.getElementById('tipo')
	const inputValor = document.getElementById('valor')
	
	
	form_lancamento.onsubmit = async (event) => {
		event.preventDefault()
		

		await axios.post('http://localhost:8080/lancamentos',{
			tipo : inputTipo.value,
			valor : inputValor.value
		})
		
		listarLancamento()
	}
}


async function listarLancamento(){
	const response = await axios.get("http://localhost:8080/lancamentos")
	const lancamentos  = response.data
	
	
/*	
	const listUl = document.getElementById('listaLancamento')
	listUl.innerHTML = ''
	
	lancamentos.forEach(l => {
		const item = document.createElement('li')
		item.innerText = 'TIPO - ' + l.tipo +' VALOR -'+ l.valor
		listUl.appendChild(item)
	});*/
	
	
	
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
		tr.appendChild(td); // adiciona a td na tr
		
		textnode.onclick = async function()
	     {
	        
			await axios.delete("http://localhost:8080/lancamentos/"+lancamentoId.textContent,{})
			alert('item removido ' +lancamentoId.textContent)
			
			listarLancamento()
	     }
		
		tableLancamento.appendChild(tr)

	});
	
	/*axios.get("http://localhost:8080/lancamentos").then(response => console.log(response.data))*/
}

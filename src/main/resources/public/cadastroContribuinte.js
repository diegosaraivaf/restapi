
axios.defaults.baseURL = 'http://localhost:8080'
var contribuinte = {}
var enderecosContribuinte = []
const inputNome = document.getElementById('nome')
const inputDocumento = document.getElementById('documento')
const inputRua1 = document.getElementById('rua1')
const inputBairro1 = document.getElementById('bairro1')
const inputRua2 = document.getElementById('rua2')
const inputBairro3 = document.getElementById('bairro2')
	
function init(){
	var params = window.location.search.substring(1).split('&');
	
	if(params.length > 0 && params[0] != ""){
		loadContribuinte(params[0])
	}
}

const loadContribuinte = (id) =>{
	
	axios.get(`contribuintes/${id}`)
		.then(response => {
			inputNome.value = response.data.nome
			inputDocumento.value = response.data.documento
			
			contribuinte = response.data
		}).catch(error => {
			console.log(error)
		})
	
	axios.get(`contribuintes/${id}/enderecos`)
		.then(response =>{
			enderecosContribuinte = response.data
			loadEnderecoTableData(response.data)
		}).catch(error => {
			console.log(error)
		})
}

function loadEnderecoTableData(enderecos){
	const tableBody  = document.getElementById('tableData')
	let dataHtml = ''
		
	for(let endereco of enderecos){
		console.log(endereco)
		dataHtml += `
			<tr>
				<td>${endereco.id}</td>
				<td><input value="${endereco.rua}" type="text" class="form-control"  ></td>
				<td><input value="${endereco.bairro}" type="text" class="form-control" ></td>
				</th><th><button class="removerLinha"   class="btn btn-primary"  >remover</button></th>
			</tr>`
	}
	tableBody.innerHTML = dataHtml
	
	document.querySelectorAll(".removerLinha").forEach( e => e.addEventListener("click", (e) => {removerLinha(e.target)}))
}
	
const put = async () => {
	try {
		const cont = await axios.put(`contribuintes/${contribuinte.id}`,contribuinte)
			
		transformaTabelaEmObjeto()
		console.log(enderecosContribuinte)
		const endereco = await axios.post(`contribuintes/${cont.data.id}/enderecos`,enderecosContribuinte)
		
		new Notify ({
				    title: 'Sucesso',
				    text: 'Contribuinte atualizado com sucesso',
				    autoclose: true,
				    autotimeout: 3000,
				    status: 'success'/*‘success’, ‘error’, or ‘warning’*/
				})
	}
	catch(error){
		new Notify ({
		    title: 'Erro',
		    text: error.response.data,
		    autoclose: true,
		    autotimeout: 3000,
		    status: 'error'/*‘success’, ‘error’, or ‘warning’*/
		})
	}
}

const salvarAtualizar = () => {
	if(contribuinte.id != null){
		put()
	} else {
		post()
	}
}

const transformaTabelaEmObjeto = () =>{
	const tableBody  = document.getElementById('tableData')
	console.log(tableBody.rows)
	enderecosContribuinte = []
	
	for(var i = 0;i < tableBody.rows.length;i++){
		enderecosContribuinte.push({
			rua : tableBody.rows[i].cells[1].children[0].value,
			bairro :tableBody.rows[i].cells[2].children[0].value
		})
		
	}
	
}

const post = async  () => {
	transformaTabelaEmObjeto()
		
	try {
		const cont = await axios.post('contribuintes', contribuinte)
		
		/*enderecosContribuinte.forEach(async e => { 
			console.log({...e, contribuinte : cont.data})
			const endereco = await axios.post(`contribuintes/${cont.data.id}/enderecos`,{...e, contribuinte : cont.data})
		})*/

		console.log(enderecosContribuinte)
		const endereco = await axios.post(`contribuintes/${cont.data.id}/enderecos`,enderecosContribuinte)
	
		
		/*window.location.href = "http://localhost:8080/consultaContribuinte.html";*/
		
		/*https://www.cssscript.com/toast-simple-notify/
		 * ESTA MENSAGE DE ERRO APARECE ANTES MESMO DOS SALVAMENTOS SEREM CONCLUIDOS
		*/	new Notify ({
			    title: 'Notify Title',
			    text: 'Notify Message',
			    autoclose: true,
			    autotimeout: 3000,
			    status: 'success'/*‘success’, ‘error’, or ‘warning’*/
			})
	} 
	catch(error){
		new Notify ({
		    title: 'Erro',
		    text: error.response.data,
		    autoclose: true,
		    autotimeout: 3000,
		    status: 'error'/*‘success’, ‘error’, or ‘warning’*/
		})
	}
}

const adicionarLinha = () =>{
	const tableBody  = document.getElementById('tableData')
	tableBody.innerHTML = tableBody.innerHTML += 
		`<tr>
			<th></th>
			<th><input type="text" class="form-control" ></th>
			<th><input type="text" class="form-control" ></th>
			</th><th><button class="removerLinha"   class="btn btn-primary"  >remover</button></th>
		</tr>`;
		
	document.querySelectorAll(".removerLinha").forEach( e => e.addEventListener("click", (e) => {removerLinha(e.target)}))
}

const removerLinha = (linha) =>{
	linha.parentElement.parentElement.remove()
}

const voltar = () =>{
	new Notify ({
	    title: 'Notify Title',
	    text: 'Notify Message',
	    autoclose: true,
	    autotimeout: 3000,
	    status: 'success'/*‘success’, ‘error’, or ‘warning’*/
	})
	console.log('isso ai ')
	window.location.href = "http://localhost:8080/consultaContribuinte.html";
}

document.getElementById('salvar').addEventListener('click',salvarAtualizar)
document.getElementById('voltar').addEventListener('click',voltar)
document.getElementById('add').addEventListener('click',adicionarLinha)
document.getElementById('nome').addEventListener('change',(e) => {contribuinte.nome = e.target.value })
document.getElementById('documento').addEventListener('change',(e) => {contribuinte.documento = e.target.value })


init()






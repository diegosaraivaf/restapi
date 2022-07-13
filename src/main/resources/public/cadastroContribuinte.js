
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
			
		})
	
	axios.get(`contribuintes/${id}/enderecos`)
		.then(response =>{
			enderecosContribuinte = response.date
			loadEnderecoTableData(response.data)
		}).catch(error => {
			
		})
}

function loadEnderecoTableData(data){
	const tableBody  = document.getElementById('tableData')
	let dataHtml = ''
		
	for(let endereco of data){
		dataHtml += `
			<tr>
				<td>${endereco.id}</td>
				<td><input value=${endereco.rua} type="text" class="form-control" required="" ></td>
				<td><input value=${endereco.bairro} type="text" class="form-control" required="" ></td>
			</tr>`
	}
	tableBody.innerHTML = dataHtml
}
	
const put = () => {
	/*contribuinte.nome = inputNome.value*/
	/*contribuinte.documento = inputDocumento.value*/
	
	axios.put(`contribuintes/${contribuinte.id}`,contribuinte)
		.then(response => {
			new Notify ({
			    title: 'Sucesso',
			    text: 'Contribuinte atualizado com sucesso',
			    autoclose: true,
			    autotimeout: 3000,
			    status: 'success'/*‘success’, ‘error’, or ‘warning’*/
			})
		}).catch(error => {
			console.log('erro ao atualizar contribuinte')
		})
	
/*	const endereco = {
		rua : 'teste', 
		bairro : 'teste',
		numero : 'teste',
		cep : 'teste'
	}
	axios.put('enderecos/1',endereco)
		.then(response => {
			console.log('endereco atualizado')
		}).catch(error => {
			console.log('erro ao atualizar endereco')
		})*/
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
	for(var i = 0;i < tableBody.rows.length;i++){
		enderecosContribuinte.push({
			rua : tableBody.rows[i].cells[1].children[0].value,
			bairro :tableBody.rows[i].cells[2].children[0].value
		})
		
	}
	console.log(enderecosContribuinte)
}

const post = async  () => {
	transformaTabelaEmObjeto()
		
	try {
		const cont = await axios.post('contribuintes', contribuinte)
		console.log('contribuinte cadastrado')
		
		enderecosContribuinte.forEach(async e => { 
			console.log({...e, contribuinte : cont.data})
			const endereco = await axios.post(`contribuintes/${cont.data.id}/enderecos`,{...e, contribuinte : cont.data})
		})
				
		
		console.log('endereco cadastrado')
		
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
	console.log('passo aqui')
		const tableBody  = document.getElementById('tableData')
		tableBody.innerHTML = tableBody.innerHTML += '<tr><th></th><th><input type="text" class="form-control" ></th><th><input type="text" class="form-control" ></th></tr>';
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






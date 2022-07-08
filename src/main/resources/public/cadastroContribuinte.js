
axios.defaults.baseURL = 'http://localhost:8080'
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
			console.log(response.data)
			inputNome.value = response.data.nome
			inputDocumento.value = response.data.documento
		}).catch(error => {
			
		})
}
	
const put = (id) => {
//		const contribuinte = {
//			nome : 'teste',
//			documento :'teste',
//		}
//	const contribuinte = await axios.get(`contribuintes/${id}`)
		/*.then(response => {
			console.log(response.data)
		})*/
	console.log(response.data)
	inputNome.value = contribuinte.nome
	
	axios.put('contribuintes/1',contribuinte)
		.then(response => {
			console.log('consitruinte atualizado com sucesso')
		}).catch(error => {
			console.log('erro ao atualizar contribuinte')
		})
	
	const endereco = {
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
		})
}


const post = async  () => {
	const form_lancamento = document.getElementById('form')
	
//	form_lancamento.onsubmit = async (event) => {
//		event.preventDefault()

	const inputNome = document.getElementById('nome')
	const inputDocumento = document.getElementById('documento')
	const inputRua1 = document.getElementById('rua1')
	const inputBairro1 = document.getElementById('bairro1')
	const inputRua2 = document.getElementById('rua2')
	const inputBairro3 = document.getElementById('bairro2')
		
	
//		await axios.post('http://localhost:8080/contribuintes', data)
	try {
		const data  = {
			nome : inputNome.value,
			documento : inputDocumento.value
		}
		const contribuinte = await axios.post('contribuintes', data)
		console.log('contribuinte cadastrado')
		
		const endereco = await axios.post('enderecos',{
			rua : inputRua1.value,
			bairro : inputBairro1.value,
//			contribuinte : contribuinte.data
			contribuinte : contribuinte.data
		})
		console.log('endereco cadastrado')
		
		window.location.href = "http://localhost:8080/consultaContribuinte.html";
		
		/*https://www.cssscript.com/toast-simple-notify/
		*/	new Notify ({
			    title: 'Notify Title',
			    text: 'Notify Message',
			    autoclose: true,
			    autotimeout: 3000,
			    status: 'success'/*‘success’, ‘error’, or ‘warning’*/
			})
	} 
	catch(error){
		console.log('erro ao salvar contribuinte com endereco')
	}
	
		
//	}
}

document.getElementById('salvar').addEventListener('click',post)
init()






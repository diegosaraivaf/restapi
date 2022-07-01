//app()
//
//function app(){
//	manipularFormulario()
//}

const post = () => {
	const form_lancamento = document.getElementById('form')
	
//	form_lancamento.onsubmit = async (event) => {
		event.preventDefault()

		const inputNome = document.getElementById('nome')
		const inputDocumento = document.getElementById('documento')
		const inputRua1 = document.getElementById('rua1')
		const inputBairro1 = document.getElementById('bairro1')
		const inputRua2 = document.getElementById('rua2')
		const inputBairro3 = document.getElementById('bairro2')
		
		//pegar o id do contribuinte para passar como parametro na insercao de endereco
		console.log("salvando contribuinte")
		const data  = {
			nome : inputNome.value,
			documento : inputDocumento.value
		}
//		await axios.post('http://localhost:8080/contribuintes', data)
		axios.post('http://localhost:8080/contribuintes', data)
		
		console.log("salvando endereco")
		axios.post('http://localhost:8080/enderecos',{
			rua : inputRua1.value,
			bairro : inputBairro1.value
		})
		
		//window.location.href = "http://localhost:8080/consultaLancamento.html";
//	}
}

const put = () => {
	const contribuinte = {
		nome : 'teste',
		documento :'teste',
	}
	axios.put('http://localhost:8080/contribuintes/1',contribuinte)
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
	axios.put('http://localhost:8080/enderecos/1',endereco)
		.then(response => {
			console.log('endereco atualizado')
		}).catch(error => {
			console.log('erro ao atualizar endereco')
		})
}

const get = () => {
	const config = {
		params : {
			_limit : 5
		}
	};
	
	axios.get('http://localhost:8080/contribuintes', config)
		.then(response => { 
			console.log(response.data)
		})
		.catch(error => {
		    console.log(error);
		});
	
}

const delet = () => {
	axios.delete('http://localhost:8080/contribuintes/7')
		.then(response => {
			console.log('contribuinte excluido ')
		})
		.catch(error => {
			console.log('erro ao excluir contribuinte')
		})
}

/*execulta as duas requisicoes e so retorna no 'then' caso as duas requisicaes sejam bem sucedidas*/
const multiple = () =>{
	Promise.all([
			axios.get('http://localhost:8080/contribuintes'),
			axios.get('http://localhost:8080/enderecos')
	]).then(response => {
		console.table(response.[0].data)
		console.table(response.[1].data)
	})
}

document.getElementById('pesquisar').addEventListener('click',get)
document.getElementById('cadastrar').addEventListener('click',post)
document.getElementById('atualizar').addEventListener('click',put)
document.getElementById('deletar').addEventListener('click',delet)



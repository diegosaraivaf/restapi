//app()
//
//function app(){
//	manipularFormulario()
//}

axios.defaults.baseURL = 'http://localhost:8080'

const post = () => {
	const form_lancamento = document.getElementById('form')
	
//	form_lancamento.onsubmit = async (event) => {
//		event.preventDefault()

	const inputNome = document.getElementById('nome')
	const inputDocumento = document.getElementById('documento')
	const inputRua1 = document.getElementById('rua1')
	const inputBairro1 = document.getElementById('bairro1')
	const inputRua2 = document.getElementById('rua2')
	const inputBairro3 = document.getElementById('bairro2')
		
	//pegar o id do contribuinte para passar como parametro na insercao de endereco
	const data  = {
		nome : inputNome.value,
		documento : inputDocumento.value
	}
//		await axios.post('http://localhost:8080/contribuintes', data)
	axios.post('contribuintes', data)
		.then(response => {
			console.log('Contribuinte cadastrado')
		}).catch(error =>{ 
			console.log('Erro ao cadastrar contribuinte')
		})
	
	axios.post('enderecos',{
		rua : inputRua1.value,
		bairro : inputBairro1.value
	})
		.then(response => {
			console.log('Endereco cadastrado')
		}).catch(error =>{ 
			console.log('Erro ao cadastrar endereco')
		})
	
		//window.location.href = "http://localhost:8080/consultaLancamento.html";
//	}
}

const put = () => {
	const contribuinte = {
		nome : 'teste',
		documento :'teste',
	}
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

const get = () => {
	const config = {
		params : {
			_limit : 5
		}
	};
	
	axios.get('contribuintes', config)
		.then(response => { 
			console.log(response.data)
			
			loadTableData(response.data)
		})
		.catch(error => {
		    console.log(error);
		});
	
}

const del = (id) => {
	axios.delete(`contribuintes/${id}`)
		.then(response => {
			console.log('contribuinte excluido ')
		})
		.catch(error => {
			console.log('erro ao excluir contribuinte')
		})
}

/*execulta as duas requisicoes e so retorna no 'then' caso as duas requisicaes sejam bem sucedidas*/
const multiple = () => {
	Promise.all([
		axios.get('contribuintes'),
		axios.get('enderecos')
	]).then(response => {
		console.table(response[0].data)
		console.table(response[1].data)
	})
}


function loadTableData(data){
	const tableBody  = document.getElementById('tableData')
	let dataHtml = ''
		
	for(let person of data){
		
		dataHtml += `<tr>
				<td> <input type="button" onClick={del(${person.id})}  value="Deletar" ></td>
				<td>${person.nome}</td>
				<td>${person.documento}</td>
			</tr>`
	}
	
	tableBody.innerHTML = dataHtml
}

const handlerDeletar = (id) =>
{
   
	alert('item removido ' + id)
}

document.getElementById('pesquisar').addEventListener('click',get)
document.getElementById('cadastrar').addEventListener('click',post)
document.getElementById('atualizar').addEventListener('click',put)
document.getElementById('deletar').addEventListener('click',del)




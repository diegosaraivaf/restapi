//app()
//
//function app(){
//	manipularFormulario()
//}

axios.defaults.baseURL = 'http://localhost:8080'

const get = () => {
	const config = {
		params : {
			_limit : 5
		}
	};

	axios.get('contribuintes', config)
		.then(response => { 
			loadTableData(response.data)
		})
		.catch(error => {
		    console.log(error);
		});
}

const del = (id) => {
	axios.delete(`contribuintes/${id}`)
		.then(response => {
			new Notify ({
			    title: 'Sucesso',
			    text: 'Contribuinte removido com sucesso',
			    autoclose: true,
			    autotimeout: 3000,
			    status: 'success'/*‘success’, ‘error’, or ‘warning’*/
			})
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
		
		dataHtml += `
			<tr>
				<td>${person.nome}</td>
				<td>${person.documento}</td>
				<td> <input type="button" onClick={del(${person.id})}  value="Deletar" ></td>
				<td> <input type="button" onClick={editar(${person.id})}  value="Editar" ></td>
			</tr>`
	}
	
	tableBody.innerHTML = dataHtml
}

const handlerDeletar = (id) =>{
	alert('item removido ' + id)
}

const editar = (id) => {
	console.log('teste')
	window.location.href = `http://localhost:8080/cadastroContribuinte.html?${id}`;
}

document.getElementById('pesquisar').addEventListener('click',get)

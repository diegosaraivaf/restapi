//app()
//
//function app(){
//	manipularFormulario()
//}

axios.defaults.baseURL = 'http://localhost:8080'
var nome = null
var documento = null
var rua = null

const get = () => {
/*	const config = {
		params : {
			_limit : 5
		}
	};*/
	
	var url = 'contribuintes?0=0'
	if(documento != null){
		url = url + '&documento='+ documento 
	}
	if(nome != null){
		url = url + '&nome='+ nome 
	}
	if(rua != null){
		url = url + '&rua='+ rua 
	}
	console.log(url)
	axios.get(url)
		.then(response => { 
			loadTableData(response.data)
		})
		.catch(error => {
			new Notify ({
			    title: 'Sucesso',
			    text: error.response.data,
			    autoclose: true,
			    autotimeout: 3000,
			    status: 'error'/*‘success’, ‘error’, or ‘warning’*/
			})
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
			console.log(error)
			new Notify ({
			    title: 'Sucesso',
			    text: error.response.data,
			    autoclose: true,
			    autotimeout: 3000,
			    status: 'error'/*‘success’, ‘error’, or ‘warning’*/
			})
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
document.getElementById('documento').addEventListener('change',(e) => {documento = e.target.value })
document.getElementById('nome').addEventListener('change',(e) => {nome = e.target.value })
document.getElementById('rua').addEventListener('change',(e) => {rua = e.target.value })

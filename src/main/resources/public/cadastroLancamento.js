

axios.defaults.baseURL = 'http://localhost:8080'
var documento = null
var tipo = null
var valor = null
var contribuinteId = null

/*
  app()
  function app(){
	manipularFormulario()
}

function manipularFormulario() {
	const form_lancamento = document.getElementById('form-lancamento')
	
	form_lancamento.onsubmit = async (event) => {
	}
}*/

async function salvar(){
	event.preventDefault()
	
	var tabela = getValoresTBody('tableData')
	console.log(tabela)
	
	await axios.post('lancamentos',{
		tipoLancamento : tipo,
		valor : valor,
		contribuinte : {
			id : contribuinteId
		},
		parcelas : [
			{valor:100}
		]
	})
	
	window.location.href = "consultaLancamento.html";
}

aoMudarDocumento = (event) => {
	axios.get(`contribuintes/documento/${event.target.value}`).then(response => {
		document.getElementById('nome').value = response.data.nome
		contribuinteId = response.data.id
	})
}

aoMudarQtdParcelas = (e) =>{
	var qtdParcelas  = e.target.value
	const tableBody  = document.getElementById('parcelas')
	var valorParcela = valor / qtdParcelas
	
	tableBody.innerHTML = ''	
	for(var i = 0; i < qtdParcelas; i++ ){
		tableBody.innerHTML = tableBody.innerHTML += 
			`<tr>
				<th>${i+1}</th>
				<th>${valorParcela}</th>
			</tr>`;
	}
		
	/*document.querySelectorAll(".removerLinha").forEach( e => e.addEventListener("click", (e) => {removerLinha(e.target)}))*/
}

const getValoresTBody = (id) ={
	const tableBody  = document.getElementById(id)
	var tabela = [] 
	
	for(var i = 0;i < tableBody.rows.length;i++){
		var linha = []
		 for(var i2 = 0;i2 < tableBody.rows[i].cells.length;i2++){
			 linha.push(tableBody.rows[i].cells[i2].children[0].value)
		 }
		tabela.push(
			linha
		)
	}
	return tabela
}

document.getElementById('salvar').addEventListener('click',salvar)
document.getElementById('documento').addEventListener('change',e => aoMudarDocumento(e))
document.getElementById('tipo').addEventListener('change',e => {tipo = e.target.value })
document.getElementById('valor').addEventListener('change',e => {valor = e.target.value })
document.getElementById('qtdParcela').addEventListener('keyup',e => aoMudarQtdParcelas(e))

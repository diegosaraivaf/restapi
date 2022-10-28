export const getValoresTBody = (id) => {
	const tableBody  = document.getElementById('tableData')
	var tabela = [] 
	console.log(tableBody)
	for(var i = 0;i < tableBody.rows.length;i++){
		var linha = []
		 for(var i2 = 0;i2 < tableBody.rows[i].cells.length;i2++){
			 console.log(tableBody.rows[i])
			 linha.push(tableBody.rows[i].cells[i2].childNodes[0].nodeValue)
		 }
		tabela.push(
			linha
		)
	}
	return tabela
}

/* Melhorar este metodo colocado o nome das colunas que vao ser exibidas */
export const preecherTabela = (idTabela, dados) =>{
	const tableBody  = document.getElementById(idTabela)
	var linha = ''
	console.log(dados)
	for(var i = 0; i < dados.length; i++ ){
		linha += '<tr>'
		for(var i2 = 0; i2 < dados[i].length; i2++ ){
			linha += `<th>${dados[i][i2]}</th>`
		}
		linha += '</tr>';
	}
	tableBody.innerHTML = ''	
	tableBody.innerHTML = linha
}


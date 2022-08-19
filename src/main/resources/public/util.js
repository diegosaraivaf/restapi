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


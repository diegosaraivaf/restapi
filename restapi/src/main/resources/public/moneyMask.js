/**
autor :diego
 */
function currency (e) {
	var str = e.target.value
    if (typeof str === 'string') {
      str = str.replace(/\D/g, '') // remove virgulas e pontos
      str = (str / 100).toFixed(2) + '' // transforma em ponto flutuante (empurrando os numeros para a direita)
      str = str.substring(0, 14)
      str = str.replace('.', ',') // remove os pontos
      str = str.replace(/(\d)(\d{3})(\d{3})(\d{3}),/g, '$1.$2.$3.$4,') // se passar de 11 caracteres, adiciona . (casa dos bilhões)
      str = str.replace(/(\d)(\d{3})(\d{3}),/g, '$1.$2.$3,') // se passar de 8 caracteres, adiciona . (casa dos milhões)
      str = str.replace(/(\d)(\d{3}),/g, '$1.$2,') // se passar de 5 caracteres, adiciona . (casa dos milhares)
	
	  e.target.value = str
     // return str
    }
    //return ''
}

document.querySelectorAll('.money').forEach(function(e) {e.addEventListener('keyup',e => currency(e))})

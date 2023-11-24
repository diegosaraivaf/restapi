import { TextField } from "@mui/material";
import React from "react";
import { IMaskInput } from "react-imask";
import { NumericFormat } from "react-number-format";


//poruqe react.forwardref e ref aqui ??
const NumericFormatCustom = React.forwardRef(function NumericFormatCustom(props, ref) {
    const { onChange, ...other } = props;
    return (
        <NumericFormat
          {...other}
          getInputRef={ref}
          thousandSeparator="."
          decimalSeparator=","
          valueIsNumericString
          prefix="R$"
          fixedDecimalScale={true}
          onValueChange={(values) => {
            onChange({
              target: {
                name: props.name,
                value: values.value,
              }
            });
          }}
      />
    );
  });


export function TextFieldCurrency(props) {
    const { onChange, ...other } = props;
    return(<>
        <TextField
          {...props}
          InputProps={{
              inputComponent: NumericFormatCustom,
          }}
        />
    </>)
}

// function toMoney (val) {
//     if (!val) return val
//     val = val.replace(/\D/g, '') // remove virgulas e pontos
//     val = (val / 100).toFixed(2) + '' // transforma em ponto flutuante (empurrando os numeros para a direita)
//     val = val.substring(0, 14) // limita de 14 caracteres
//     val = val.replace('.', ',') // remove os pontos
//     val = val.replace(/(\d)(\d{3})(\d{3})(\d{3}),/g, '$1.$2.$3.$4,') // se passar de 11 caracteres, adiciona . (casa dos bilhões)
//     val = val.replace(/(\d)(\d{3})(\d{3}),/g, '$1.$2.$3,') // se passar de 8 caracteres, adiciona . (casa dos milhões)
//     val = val.replace(/(\d)(\d{3}),/g, '$1.$2,') // se passar de 5 caracteres, adiciona . (casa dos milhares)
//     return val
//   }

//   export function TextFieldCurrency(props) {
//     return(<>
//         <TextField
//         {...props}
//             onChange={e=> {
//             props.onChange(e)
//             console.log('text field ')
//             toMoney(e.target.value)
            
//             }}
//         />
//     </>)
//     }





const TextMaskCustom =(props)=>{
    return (
      <IMaskInput
        {...props}
        mask="(#00) 000-0000"
        definitions={{
          '#': /[1-9]/,
        }}
        overwrite
        unmask={false}
      />
    );
  };


  export function TextFieldMask(props){
    return(<>
        <TextField
        {...props}
        name="textmask"
        id="formatted-text-mask-input"
        InputProps={{
            inputComponent: TextMaskCustom,
        }}
        
        
        />
    </>)
  }

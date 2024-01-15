import { Button} from "@mui/material";
import React, { useState } from "react";
import { SnackbarContext } from "../componente/SnackbarContext";
import Api from "../componente/Api";

export function Home({titulo,descricao}) {

  const [message, setMessage] = useState('You server message here.')

  const testar =()=>{
    Api.patch("lancamentos/teste/1",{
      id:1,
       valor:'400',
      // dataEmissao:'2023-12-23',
      //parcelas:[{id:'1',valor:'100',situacao:'EMITIDA',dataVencimento:'2023-12-22'}]
      // contribuinte:{id:'1',documento:'2'}
      // ,situacaoLancamento:'EMITIDO'

    })

  }

  const enviarEmail =()=>{
    Api.post("/email/enviar",{})

  }


  return (
    <>
      <Button variant="contained" onClick={testar} >Testar Path</Button>
      <Button variant="contained" onClick={enviarEmail} >Enviar email</Button>
    </>
  );
}

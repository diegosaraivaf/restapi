import { Button, Snackbar } from "@mui/material";
import { Head } from "./head";
import { useContext, useState } from "react";
import { SnackbarContext } from "../componente/SnackbarContext";
import Api from "../componente/Api";

export function Home({titulo,descricao}) {
  const {message} = useContext(SnackbarContext)
 

  const testar =()=>{
    Api.patch("lancamentos/teste/1",{
      valor:'100',
      dataEmissao:'2023-12-22',
      // situacaoLancamento:'EMITIDO',
      // parcelas:[{id:'1',valor:'100',situacao:'EMITIDA',dataVencimento:'2023-12-22'}],
      contribuinte:{id:'1',nome:'teste',documento:'teste'}

    })

  }




  return (
    <>
      <Button variant="contained" onClick={testar} >Testar Path</Button>


    </>
  );
}

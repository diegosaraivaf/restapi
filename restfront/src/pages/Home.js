import { Button, Snackbar } from "@mui/material";
import { Head } from "./head";
import { useContext, useState } from "react";
import { SnackbarContext } from "../componente/SnackbarContext";

export function Home({titulo,descricao}) {
  const {message} = useContext(SnackbarContext)
 

  const enviar =()=>{
    message('teste','warning')

  }

  // const mostrarMensagem =(mensagem)=>{
  //   setMessage({open:true, message:'teste'})
  // }



  return (
    <>
      <Button onClick={enviar} >show </Button>


    </>
  );
}

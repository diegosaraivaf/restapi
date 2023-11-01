import { Box, Button, Container, Paper, Snackbar } from "@mui/material";
import { Head } from "./head";
import { useContext, useState } from "react";
import { SnackbarContext } from "../componente/SnackbarContext";

export function Login() {
  const {message} = useContext(SnackbarContext)
 

  const enviar =()=>{
    message('teste','warning')

  }

  return (
    <>
       <Paper elevation={3}>
        teste
        </Paper>

      <Button onClick={enviar} >show </Button>


    </>
  );
}

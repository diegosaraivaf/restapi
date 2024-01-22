import { Button, Link, Paper, TextField, Typography } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { SnackbarContext } from "../componente/SnackbarContext";
import Api from "../componente/Api";
import { useNavigate, useSearchParams } from "react-router-dom";

export function RecuperarSenha() {
  const {message} = useContext(SnackbarContext)
  const navigate = useNavigate();
  const [email,setEmail] = useState('');
  const [searchParams] = useSearchParams();
  const codigoRecuperacaoSenha = searchParams.get("codigoRecuperacaoSenha")

  useEffect( () => {
    setEmail(searchParams.get("email"))
  }, []);

  const enviar = async ()=>{
    try{
      debugger
      const response = await Api.post('/usuarios/gerarCodigoRecuperacaoSenha',email)

      message('Verifique a caixa de entrada do seu email para fazer a redefinicao da senha ','success')
    }catch(e){
      console.log(e)
      message(e.response.data,'error')
    }

  }


  return (
    !codigoRecuperacaoSenha 
    ?
      <div className="login-background">
        <Paper  
        className="login-container"
        elevatio={10}>
          <Typography variant="h6">Digite o seu email</Typography> 
          <TextField label="Email" className="mt10" onChange={e => setEmail( e.target.value)}/>
          <div className="flex justify-space-between">
            <Button variant="contained" onClick={e=>{navigate('/')}} >Voltar </Button>
            <Button variant="contained" className="mla" onClick={enviar} >Enviar </Button>
          </div>
        </Paper>
      </div>
  : 
    <div className="login-background">
          <Paper  
          className="login-container"
          elevatio={10}>
            <Typography variant="h6">Defina a nova senha </Typography> 
            <TextField label="Codigo recuperacao" value={codigoRecuperacaoSenha}  className="mt10" disabled />
            <TextField label="Email" value={email} className="mt10" disabled />
            <TextField label="Nova senha" />
            <TextField label="Repita Nova senha"/>
            <div className="flex justify-space-between">
              <Button variant="contained" onClick={e=>{navigate('/')}} >Voltar </Button>
              <Button variant="contained" className="mla" onClick={enviar} >Enviar </Button>
            </div>
          </Paper>
      </div>
  );
}

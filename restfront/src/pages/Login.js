import { Button, Paper, TextField } from "@mui/material";
import { useContext, useState } from "react";
import { SnackbarContext } from "../componente/SnackbarContext";
import Api from "../componente/Api";
import { useNavigate } from "react-router-dom";

export function Login() {
  const {message} = useContext(SnackbarContext)
  const navigate = useNavigate();
  const [usuario,setUsuario] = useState({
    email:'',
    senha:''
  });

 

  const enviar = async ()=>{
    
    
    try{
      const response = await Api.post('/usuarios/autenticar',usuario)
   
      localStorage.setItem('email', usuario.email)
      localStorage.setItem('token', response.data.token)
      navigate('/Home')
      message('entrar','success')
    }catch(e){
      console.log(e)
      if(e.code == "ERR_NETWORK"){
        message("API fora dor ar",'error')  
        //e.message : "Network Error"
        // e.code : "ERR_NETWORK"   
      }else{
        message(e.response.data,'error')
      }
     
    }

  }

  return (
    <div className="login-background">
      <Paper  
      className="login-container"
      elevatio={10}>
        <TextField label="Email" onChange={e => setUsuario({...usuario,email: e.target.value})}/>
        <TextField label="Senha" onChange={e => setUsuario({...usuario,senha: e.target.value})} />
        <Button  onClick={enviar} >Logar </Button>
      </Paper>

      


    </div>
  );
}

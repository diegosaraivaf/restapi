import { Button, Card, CardContent, CardHeader, Grid, Paper, Snackbar, Stack, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import KeyboardReturnIcon from '@mui/icons-material/KeyboardReturn';
import { Head } from "./head";
import { useForm ,useFieldArray} from "react-hook-form";
import { Menu } from "./Menu";
import { useContext, useEffect, useState } from "react";
import { SnackbarContext } from "../componente/SnackbarContext";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";
import Api from "../componente/Api";

export function CadastroOpcaoCaracteristica() {
  const {message} = useContext(SnackbarContext)
  const navigate = useNavigate();
  const location = useLocation();
  const [searchParams] = useSearchParams();
  const id = searchParams.get("id")
  const [caracteristica, setCaracteristica]  = useState({
    nome:'',
  })

  useEffect( () => {
    if(id != null){
      let caracteristica = {}

      const carregar = async() =>{
        caracteristica =  (await Api.get(`/caracteristicas/${id}`)).data
        setCaracteristica(caracteristica)
      }
      carregar()
    }
    else{
    }
  }, []);

  const cadastrar = async ()=>{
    try{
      const caracteristicaAux  = (await Api.post('/opcoesCaracteristicas',caracteristica)).data

      navigate("/ConsultaOpcaoCaracteristica");
      message('Opcão Caracteristica cadastrado com sucesso','success')
    }
    catch(error){
      console.log(error)
      message(error.response.data.message ,'error')
    }
  } 
 
  return (<>
    <Paper className="container" >
      <Grid container spacing={2}>
        <Grid item xs={12} sm={3}>
          <TextField 
            label="Nome" 
            value={caracteristica.nome} 
            onChange={(e)=>{setCaracteristica({...caracteristica, nome : e.target.value})}}  
            fullWidth    
            />
        </Grid>
      </Grid>

      <Stack direction={"row"} justifyContent="space-between" sx={{ mt: 5 }}>
          <Button onClick={e=> {navigate(`/ConsultaOpcaoCaracteristica`)}} variant="contained" color="secondary" startIcon={<KeyboardReturnIcon/>}>Voltar</Button>
          <Button type="submit" variant="contained" onClick={cadastrar} startIcon={<AddCircleOutlineIcon/>}>Cadastrar</Button>
     </Stack>
    </Paper>
  </>);
}

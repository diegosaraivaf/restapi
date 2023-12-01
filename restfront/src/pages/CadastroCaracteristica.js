import { Button, FormControl, Grid, InputLabel, MenuItem, Paper, Select, Stack, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import KeyboardReturnIcon from '@mui/icons-material/KeyboardReturn';
import { useContext, useEffect, useState } from "react";
import { SnackbarContext } from "../componente/SnackbarContext";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";
import Api from "../componente/Api";

export function CadastroCaracteristica() {
  const {message} = useContext(SnackbarContext)
  const navigate = useNavigate();
  const location = useLocation();
  const [searchParams] = useSearchParams();
  const id = searchParams.get("id")
  const [caracteristica, setCaracteristica]  = useState({
    nome:'',
    tipoCaracteristica:'',
    opcoesCaracteristica:[]
  })

  useEffect( () => {
    if(id != null){
      let caracteristica = {}

      const carregar = async() =>{
        caracteristica =  (await Api.get(`/caracteristicas/${id}`)).data
        setCaracteristica(caracteristica)
      }
      carregar()

    }else{

    }

  }, []);

  const cadastrar = async ()=>{
    try{
      debugger
      const caracteristicaAux  = (await Api.post('/caracteristicas',caracteristica)).data

      navigate("/ConsultaCaracteristica");
      message('Caracteristica cadastrado com sucesso','success')
    }
    catch(error){
      console.log(error)
      message(error.response.data.message ,'error')
    }
  } 

  const adicionarItem =()=>{
    setCaracteristica({...caracteristica, opcoesCaracteristica : caracteristica.opcoesCaracteristica.concat([{}])})
  }
 
  return (
    <>
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
        <Grid item xs={12} sm={3}>
         <TextField
            value={caracteristica.tipoCaracteristica}
            onChange={e=>{setCaracteristica({...caracteristica,tipoCaracteristica : e.target.value})}}
            select // tell TextField to render select
            label="Tipo caracteristica"
            fullWidth
          >
            <MenuItem key={1} value="TERRENO">Terreno</MenuItem>
            <MenuItem key={2} value="EDIFICACAO">Edificacao</MenuItem>
          </TextField>

        </Grid>
      </Grid>
      <TextField label="Opção Caracteristica"/>

      <Table >
        <TableHead>
          <TableRow>
            <TableCell>Id</TableCell>
            <TableCell >Nome</TableCell>
            <TableCell > <Button onClick={adicionarItem} >Adicionar</Button></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {caracteristica.opcoesCaracteristica.map((row,index) => (
            <TableRow
              key={row.id}
            >
              <TableCell >{row.id}</TableCell>
              <TableCell >
                <TextField value={row.nome} onChange={(e)=>{
                    caracteristica.opcoesCaracteristica[index].nome = e.target.value
                    setCaracteristica({...caracteristica})
                  }}/> 
                </TableCell>
              <TableCell >{row.tipoCaracteristica}</TableCell>
              <TableCell >
                <Button >Editar</Button>
                <Button >Excluir</Button>
              </TableCell>

            </TableRow>
          ))}
        </TableBody>
      </Table>

      <Stack direction={"row"} justifyContent="space-between" sx={{ mt: 5 }}>
          <Button onClick={e=> {navigate(`/ConsultaCaracteristica`)}} variant="contained" color="secondary" startIcon={<KeyboardReturnIcon/>}>Voltar</Button>
          <Button type="submit" variant="contained" onClick={cadastrar} startIcon={<AddCircleOutlineIcon/>}>Cadastrar</Button>
     </Stack>
    </Paper>
    </>
  );
}

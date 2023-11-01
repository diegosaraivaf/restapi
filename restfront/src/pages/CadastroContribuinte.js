import { Button, Card, CardContent, CardHeader, Grid, Paper, Snackbar, Stack, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import KeyboardReturnIcon from '@mui/icons-material/KeyboardReturn';
import { Head } from "./head";
import { useForm ,useFieldArray} from "react-hook-form";
import { Menu } from "./Menu";
import { useContext, useEffect, useState } from "react";
import { SnackbarContext } from "../componente/SnackbarContext";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";

export function CadastroContribuinte() {
  const {message} = useContext(SnackbarContext)
  const navigate = useNavigate();
  const location = useLocation();
  const [searchParams] = useSearchParams();
  const id = searchParams.get("id")
  const [contribuinte, setContribuinte]  = useState({
    nome:'',
    documento:'',
    enderecos:[]
  })

  useEffect( () => {
    console.log('parametro: '+ id)
    if(id != null){
      let contribuinte = {}

      const carregar = async() =>{
        await fetch(`http://localhost:8080/contribuintes/${id}`, { method: 'GET' })
        .then(json => json.json())
        .then(response => {
          contribuinte = response
        })
        .catch(err => {
          message(err,'error')
        })

        await fetch(`http://localhost:8080/contribuintes/${id}/enderecos`, { method: 'GET' })
        .then(json => json.json())
        .then(response => {
          console.log(contribuinte)
          contribuinte  = {...contribuinte,enderecos:response}
          setContribuinte(contribuinte)
          console.log(contribuinte)
        })
        .catch(err => {
          message(err,'error')
        })
      }
      carregar()

    }else{

    }

  }, []);

  const onSubmit = async ()=>{
    try{
      const response = await fetch('http://localhost:8080/contribuintes', { 
        method: 'POST', 
        body: JSON.stringify(contribuinte) ,
        headers: {"Content-type": "application/json; charset=UTF-8"}
      })
      const contribuinteJson = await response.json();

      const response2 = await fetch(`http://localhost:8080/contribuintes/${contribuinteJson.id}/enderecos`, { 
        method: 'POST', 
        body: JSON.stringify(contribuinte.enderecos) ,
        headers: {"Content-type": "application/json; charset=UTF-8"}
      })
      navigate("/ConsultaContribuinte");
      message('Contribuinte cadastrado com sucesso','success')
    }
    catch{
      message('Erro ao cadastrar','warning')
    }
  } 

  const onAdicionarItem = ()=>{
    setContribuinte({...contribuinte, enderecos:contribuinte.enderecos.concat( [{rua:'',bairro:''}]) }) 
  }

  const removerItem=(row)=>{
    contribuinte.enderecos.splice(row,1)
    setContribuinte({...contribuinte, enderecos:contribuinte.enderecos})
  }
 
  return (
    <>

    <Paper className="container" >
      <Grid container spacing={2}>
        <Grid item xs={12} sm={3}>
          <TextField 
            label="Nome" 
            value={contribuinte.nome} 
            onChange={(e)=>{setContribuinte({...contribuinte, nome : e.target.value})}}  
            fullWidth    
            InputLabelProps={{ shrink: contribuinte.nome?.length > 0 }}/>
        </Grid>
        <Grid item xs={12} sm={5}>
          <TextField 
            label="Documento" 
            value={contribuinte.documento} 
            onChange={(e)=>{setContribuinte({...contribuinte, documento : e.target.value})}} 
            fullWidth
            InputLabelProps={{ shrink: contribuinte.documento?.length > 0 }}/>
        </Grid>
      </Grid>
      <Stack direction={"row"}  sx={{ mt:5 }}>
      <Button variant="contained" onClick={onAdicionarItem} startIcon={<AddCircleOutlineIcon/>} sx={{ ml: 'auto' }}>Adicionar Endereco</Button>
      </Stack>
      <Table >
        <TableHead>
          <TableRow>
            <TableCell>Id</TableCell>
            <TableCell>Rua</TableCell>
            <TableCell>Bairro</TableCell>
            <TableCell>Ações</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {contribuinte.enderecos.map((row,index) => (
            <TableRow
              key={index}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell >{index}</TableCell>
              <TableCell >
                <TextField value={contribuinte.enderecos[index].rua} 
                onChange={(e)=>{
                  contribuinte.enderecos[index].rua = e.target.value; 
                  setContribuinte({...contribuinte})
                }}/>
              </TableCell>
              <TableCell >
                <TextField 
                value={contribuinte.enderecos[index].bairro} 
                onChange={(e)=>{
                  contribuinte.enderecos[index].bairro = e.target.value; 
                  setContribuinte({...contribuinte})
                }}/>
              </TableCell>
              <TableCell >
                <Button onClick={()=>removerItem(index)}>Remover</Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>


      <Stack direction={"row"} justifyContent="space-between" sx={{ mt: 5 }}>
          <Button onClick={e=> {window.location.href = `/ConsultaContribuinte`}} variant="contained" color="secondary" startIcon={<KeyboardReturnIcon/>}>Voltar</Button>
          <Button type="submit" variant="contained" onClick={onSubmit} startIcon={<AddCircleOutlineIcon/>}>Cadastrar</Button>
     </Stack>
    </Paper>
    </>
  );
}

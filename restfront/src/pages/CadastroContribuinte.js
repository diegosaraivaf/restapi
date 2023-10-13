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
  const [contribuinte, setContribuinte]  = useState({enderecos:[]})

  const [searchParams, setSearchParams] = useSearchParams();


  useEffect(() => {
    const id = searchParams.get("id")
    if(id != null){
      fetch(`http://localhost:8080/contribuintes/${id}`, { method: 'GET' })
      .then(json => json.json())
      .then(response => {
        console.log(response)
        response.enderecos = []
        setContribuinte(response)

      })
      .catch(err => {
        console.log(err.message)
      })
    }else{

    }

}, []);

  
  const onSubmit = async ()=>{
    // console.log(contribuinte);
    try{
      const response = await fetch('http://localhost:8080/contribuintes', { 
        method: 'POST', body: JSON.stringify(contribuinte) ,
        headers: {"Content-type": "application/json; charset=UTF-8"}
      })
      const contribuinteJson = await response.json();
      console.log(`http://localhost:8080/contribuintes/${contribuinteJson.id}/enderecos`);

      const response2 = await fetch(`http://localhost:8080/contribuintes/${contribuinteJson.id}/enderecos`, { 
        method: 'POST', body: JSON.stringify(contribuinte.enderecos) ,
        headers: {"Content-type": "application/json; charset=UTF-8"}
      })
      navigate("/ConsultaContribuinte");
      message('Contribuinte cadastrado com sucesso','success')
    }catch{
      message('Erro ao cadastrar','warning')
    }
  } 

  const onAdicionarItem = ()=>{
    setContribuinte({...contribuinte, enderecos:contribuinte.enderecos.concat( [{}]) }) 
  }
 
  return (
    <>

    <Paper className="container" >
      <Grid container spacing={2}>
        <Grid item xs={12} sm={3}>
          <TextField label="Nome" value={contribuinte.nome} onChange={(e)=>{setContribuinte({...contribuinte, nome : e.target.value})}}  fullWidth/>
        </Grid>
        <Grid item xs={12} sm={5}>
          <TextField label="Documento" value={contribuinte.documento} onChange={(e)=>{setContribuinte({...contribuinte, documento : e.target.value})}} fullWidth/>
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
          </TableRow>
        </TableHead>
        <TableBody>
          {contribuinte.enderecos.map((row,index) => (
            <TableRow
              key={index}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
      {/*  onChange={(e)=>{}} setContribuinte(contribuinte.enderecos[index].rua = 'teste') */}
              <TableCell >{index}</TableCell>
              <TableCell >
                <TextField onChange={(e)=>{
                contribuinte.enderecos[index].rua = e.target.value; 
                setContribuinte({...contribuinte})
                }}/>
              </TableCell>
              <TableCell >
                <TextField onChange={(e)=>{
                contribuinte.enderecos[index].bairro = e.target.value; 
                setContribuinte({...contribuinte})
                }}/>
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

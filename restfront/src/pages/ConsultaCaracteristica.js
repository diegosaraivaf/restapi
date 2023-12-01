import { Button, Grid, Paper, Stack, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";
import { useContext, useState } from "react";
import { createSearchParams, useNavigate,} from "react-router-dom";
import { SnackbarContext } from "../componente/SnackbarContext";
import {  ConfirmDialogContext } from "../componente/ConfirmDialogContext";
import Api from "../componente/Api";

export function ConsultaCaracteristica() {
  const {message} = useContext(SnackbarContext)
  const {confirmDialog} = useContext(ConfirmDialogContext)
  const navigate = useNavigate();
  const [filtro, setFiltro] = useState({
    id:'',
    nome:'',
    documento:'',
    rua:'',
    bairro:''
  });
  const [caracteristicas, setCaracteristicas] = useState([]);

  const pesquisar =data=>{
    Api.get('/caracteristicas',{
      params: {
        id: filtro.id.length > 0 ?  filtro.id : null ,
        nome: filtro.nome.length > 0 ?  filtro.nome : null ,
        documento: filtro.documento.length > 0 ?  filtro.documento : null ,
        rua: filtro.rua.length > 0 ?  filtro.rua : null ,
        bairro: filtro.bairro.length > 0 ?  filtro.bairro : null 
      }
    }).then(response =>{
      console.log(response)
      setCaracteristicas(response.data)    
    })
    .catch(error =>{
      console.log(error)
      message(error.message,'error')
    })
  }

  const editar = (id)=>{
    navigate({
      pathname: "/CadastroCaracteristica",
      search: createSearchParams({id}).toString()
    });
  }

  const excluir =  (id)=>{
      confirmDialog('Voçê realmente desaja excluir este registro?',()=>{
        Api.delete(`/caracteristicas/${id}`).then(response =>{
          pesquisar()
          message('registro excluido ')
        }).catch(error =>{
          console.log(error)
          message(String(error),'error')
        })
    })
  }

  return (
    <>
    <form >
    <Paper className="container" >
      <Grid container spacing={2}>
        <Grid item xs={12} sm={3}>
          <TextField label="Id" onChange={(e)=>{setFiltro({...filtro, id : e.target.value})}}  fullWidth/>
        </Grid>
        <Grid item xs={12} sm={9}>
          <TextField label="Nome" onChange={(e)=>{setFiltro({...filtro, nome : e.target.value})}} fullWidth/>
        </Grid>
      </Grid>

      <Stack direction={"row"} justifyContent="space-between" sx={{ mt: 2 }}>
          <Button onClick={e=> {navigate(`/CadastroCaracteristica`)}} variant="contained" color="secondary" >Cadastro</Button>
          <Button variant="contained" onClick={pesquisar} >Pesquisar</Button>
     </Stack>
    </Paper>

    <Paper className="container">
      <Table >
        <TableHead>
          <TableRow>
            <TableCell>Id</TableCell>
            <TableCell >Nome</TableCell>
            <TableCell >Ações</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {caracteristicas.map((row) => (
            <TableRow
              key={row.id}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell >{row.id}</TableCell>
              <TableCell >{row.nome}</TableCell>
              <TableCell >{row.tipoCaracteristica}</TableCell>
              <TableCell >
                <Button onClick={() =>{editar(row.id)}}>Editar</Button>
                <Button onClick={()=>{excluir(row.id)}}>Excluir</Button>
              </TableCell>

            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Paper>
    </form>
    </>
  );
}

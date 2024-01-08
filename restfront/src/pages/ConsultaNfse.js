
import { Button, Grid, Pagination, Paper, Stack, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";
import { useContext, useState } from "react";
import Api from "../componente/Api";
import {createSearchParams, useNavigate } from "react-router-dom";
import { SnackbarContext } from "../componente/SnackbarContext";
import { ConfirmDialogContext } from "../componente/ConfirmDialogContext";

export function ConsultaNfse() {
  //const { register,  handleSubmit} = useForm();
  const [notas, setNotas] = useState([]);
  const [filtroNota, setFiltroNota] = useState({id:'',tipo:'',valorServico:'',documentoPrestador:'',nomePrestador:'',situacaoNfse:''});
  const navigate = useNavigate();
  const {message} = useContext(SnackbarContext)
  const {confirmDialog} = useContext(ConfirmDialogContext)
  let [page, setPage] = useState(1);
  let [count, setCount] = useState(1);
  
  const pesquisar = data => {
    console.log(data);
    Api.get('/nfses',{
      params: {
        size: 10,
        documentoPrestador: filtroNota.documentoPrestador.length > 0 ?  filtroNota.documentoPrestador : null ,
        nomePrestador: filtroNota.nomePrestador.length > 0 ?  filtroNota.nomePrestador : null ,
      }
    }).then(response =>{
      setNotas(response.data.content)
      setCount(response.data.totalPages)

    }).catch(error =>{
      console.log(error)
    })
  }

  const editar = (id)=>{
    navigate({
      pathname: "/CadastroNfse",
      search: createSearchParams({id}).toString()
    });
  }

  const excluir = (id)=>{
    confirmDialog('Voçê realmente desaja cancelar esta nota?',()=>{
      Api.delete(`/nfses/${id}`).then(response =>{
        message('Nfse cancelada com sucesso ')
      }).catch(error =>{
        console.log(error)
        message(error,'error')
      })
    })
  }

  const mudarPagina =(e, p)=>{
    setPage(p)
    console.log(p)

    Api.get('/nfses',{
      params: {
        page: p-1,
        size: 10
      }
    }).then(response =>{
      setNotas(response.data.content)
      setCount(response.data.totalPages)
    }).catch(error =>{
      console.log(error)
    })
  }

  return (
    <>
    <form>
    <Paper className="container" >
      <Grid container spacing={2}>
        <Grid item xs={12} sm={4}>
          <TextField label="Id" fullWidth/>
        </Grid>
        <Grid item xs={12} sm={4}>
          <TextField label="Tipo"  fullWidth/>
        </Grid>
        <Grid item xs={12} sm={4}>
          <TextField label="Valor" onChange={(e)=>{setFiltroNota({...filtroNota, valorServico : e.target.value})}} fullWidth/>
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField label="Documento Prestador"  onChange={(e)=>{setFiltroNota({...filtroNota, documentoPrestador : e.target.value})}} fullWidth/>
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField label="Nome Prestador" onChange={(e)=>{setFiltroNota({...filtroNota, nomePrestador : e.target.value})}} fullWidth/>
        </Grid>
        <Grid item xs={12} sm={4}>
          <TextField label="Situacao" onChange={(e)=>{setFiltroNota({...filtroNota, situacaoNfse : e.target.value})}} fullWidth/>
        </Grid>
        <Grid item xs={12} sm={4}>
          <TextField label="Data Emissao" fullWidth/>
        </Grid>
      </Grid>

      <Stack direction={"row"} justifyContent="space-between" sx={{ mt: 2 }}>
          <Button onClick={e=> navigate("/CadastroNfse")} variant="contained" color="secondary" >Cadastro</Button>
          <Button onClick={pesquisar} variant="contained"  >Pesquisar</Button>
     </Stack>
    </Paper>

    <Paper className="container">
      <Table >
        <TableHead>
          <TableRow>
            <TableCell>Id</TableCell>
            <TableCell align="right">Data Emissao</TableCell>
            <TableCell align="right">Prestador</TableCell>
            <TableCell align="right">Tomador</TableCell>
            <TableCell align="right">Valor</TableCell>
            <TableCell align="right">Situacao</TableCell>
            <TableCell align="right">Acoes</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {notas.map((row) => (
            <TableRow
              key={row.id}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell component="th" scope="row">{row.id}</TableCell>
              <TableCell>{row.dataEmissao}</TableCell>
              <TableCell>{row.prestador.documento}</TableCell>
              <TableCell>{row.tomador.nome}</TableCell>
              <TableCell>{row.valorServico}</TableCell>
              <TableCell>{row.situacaoNfse}</TableCell>
              <TableCell>
                <Button onClick={e =>{ editar(row.id)}}>Editar</Button> 
                <Button onClick={e =>{ excluir(row.id)}}>Cancelar</Button> 
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
      <Pagination  count={count} page={page} onChange={mudarPagina} color="primary"/>
    </Paper>
    </form>
    </>
  );
}


import { Button, Container, Grid, Paper, Stack, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";
import { Input } from "../componente/Input";
import { Head } from "./head";
import { useForm } from "react-hook-form";
import { useState } from "react";
import Api from "../componente/Api";
import {createSearchParams, useNavigate } from "react-router-dom";

export function ConsultaNfse() {
  const { register,  handleSubmit} = useForm();
  const [notas, setNotas] = useState([]);
  const navigate = useNavigate();
  
  const onSubmit = data => {
    console.log(data);
    Api.get('/nfses').then(response =>{
      setNotas(response.data.content)
    }).catch(error =>{
      console.log(error)
    })

    // fetch('http://localhost:8080/nfses', { method: 'GET' })
    // .then(json => json.json())
    // .then(response => {
    //   console.log(response.content)
    //   setNotas(response.content)
    // })
    // .catch(err => {
    //   console.log(err.message)
    // })
  }

  const editar = (id)=>{
    navigate({
      pathname: "/CadastroNfse",
      search: createSearchParams({id}).toString()
    });
  }

  return (
    <>
    <form onSubmit={handleSubmit(onSubmit)}>
    <Paper className="container" >
      <Grid container spacing={2}>
        <Grid item xs={12} sm={4}>
          <TextField label="Id" {...register("id")} fullWidth/>
        </Grid>
        <Grid item xs={12} sm={4}>
          <TextField label="Tipo" {...register("tipo")} fullWidth/>
        </Grid>
        <Grid item xs={12} sm={4}>
          <TextField label="Valor" {...register("valor")} fullWidth/>
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField label="Documento" {...register("documento")} fullWidth/>
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField label="Nome" {...register("nome")}fullWidth/>
        </Grid>
        <Grid item xs={12} sm={4}>
          <TextField label="Situacao" {...register("situacao")} fullWidth/>
        </Grid>
        <Grid item xs={12} sm={4}>
          <TextField label="Data Emissao" {...register("dataEmissao")} fullWidth/>
        </Grid>
      </Grid>


      <Stack direction={"row"} justifyContent="space-between" sx={{ mt: 2 }}>
          <Button onClick={e=> {window.location.href = `/CadastroNfse`}} variant="contained" color="secondary" >Cadastro</Button>
          <Button type="submit" variant="contained"  >Pesquisar</Button>
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
              <TableCell><Button onClick={e =>{ editar(row.id)}}>Editar</Button> </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Paper>
    </form>
    </>
  );
}

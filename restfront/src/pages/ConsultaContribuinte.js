import { Button, Grid, Paper, Stack, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";
import { useState } from "react";
import { createSearchParams, useNavigate } from "react-router-dom";

export function ConsultaContribuinte() {

  const [filtro, setFiltro] = useState({});
  const navigate = useNavigate();
  const [contribuintes, setContribuintes] = useState([]);


  const onSubmit = (e) => {
    e.preventDefault()

    fetch('http://localhost:8080/contribuintes', { method: 'GET' })
    .then(json => json.json())
    .then(response => {
      console.log(response)
      setContribuintes(response)
    })
    .catch(err => {
      console.log(err.message)
    })
  }

  const onEdit = (id)=>{

    navigate({
      pathname: "/CadastroContribuinte",
      search: createSearchParams({
          id
      }).toString()
  });

    // navigate(`/CadastroContribuinte/${id}`);
  }

  return (
    <>
    <form >
    <Paper className="container" >
      <Grid container spacing={2}>
        <Grid item xs={12} sm={3}>
          <TextField label="Id" onChange={(e)=>{setFiltro({...filtro, id : e.target.value})}}  fullWidth/>
        </Grid>
        <Grid item xs={12} sm={5}>
          <TextField label="Nome" onChange={(e)=>{setFiltro({...filtro, nome : e.target.value})}} fullWidth/>
        </Grid>
        <Grid item xs={12} sm={4}>
          <TextField label="Documento" onChange={(e)=>{setFiltro({...filtro, documento : e.target.value})}} fullWidth/>
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField label="Rua" onChange={(e)=>{setFiltro({...filtro, rua : e.target.value})}} fullWidth/>
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField label="Bairro" onChange={(e)=>{setFiltro({...filtro, bairro : e.target.value})}} fullWidth/>
        </Grid>
        <Grid item xs={12} sm={4}>
          <TextField label="CEP" onChange={(e)=>{setFiltro({...filtro, cep : e.target.value})}} fullWidth/>
        </Grid>
      </Grid>


      <Stack direction={"row"} justifyContent="space-between" sx={{ mt: 2 }}>
          <Button onClick={e=> {window.location.href = `/CadastroContribuinte`}} variant="contained" color="secondary" >Cadastro</Button>
          <Button type="submit" variant="contained" onClick={onSubmit} >Pesquisar</Button>
     </Stack>
    </Paper>

    <Paper className="container">
      <Table >
        <TableHead>
          <TableRow>
            <TableCell>Id</TableCell>
            <TableCell >Documento</TableCell>
            <TableCell >Nome</TableCell>
            <TableCell >Ações</TableCell>

          </TableRow>
        </TableHead>
        <TableBody>
          {contribuintes.map((row) => (
            <TableRow
              key={row.id}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell >{row.id}</TableCell>
              <TableCell >{row.documento}</TableCell>
              <TableCell >{row.nome}</TableCell>
              <TableCell ><Button onClick={() =>{onEdit(row.id)}}>Editar</Button></TableCell>

            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Paper>
    </form>
    </>
  );
}

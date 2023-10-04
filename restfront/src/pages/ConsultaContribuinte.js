import { Button, Grid, Paper, Stack, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";
import { useState } from "react";
import { useForm } from "react-hook-form";


export function ConsultaContribuinte() {
  const { register,  handleSubmit} = useForm();
  const [contribuintes, setContribuintes] = useState([]);


  const onSubmit = data => {
    console.log(data);

    fetch('http://localhost:8080/contribuintes', { method: 'GET' })
    .then(json => json.json())
    .then(response => {
      console.log(response.content)
      setContribuintes(response.content)
    })
    .catch(err => {
      console.log(err.message)
    })
  }

  return (
    <>
    <form onSubmit={handleSubmit(onSubmit)}>
    <Paper className="container" >
      <Grid container spacing={2}>
        <Grid item xs={12} sm={3}>
          <TextField label="Id" {...register("id")} fullWidth/>
        </Grid>
        <Grid item xs={12} sm={5}>
          <TextField label="Nome" {...register("nome")} fullWidth/>
        </Grid>
        <Grid item xs={12} sm={4}>
          <TextField label="Documento" {...register("documento")} fullWidth/>
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField label="Rua" {...register("rua")} fullWidth/>
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField label="Bairro" {...register("bairro")}fullWidth/>
        </Grid>
        <Grid item xs={12} sm={4}>
          <TextField label="CEP" {...register("cep")} fullWidth/>
        </Grid>
      </Grid>


      <Stack direction={"row"} justifyContent="space-between" sx={{ mt: 2 }}>
          <Button onClick={e=> {window.location.href = `/CadastroContribuinte`}} variant="contained" color="secondary" >Cadastro</Button>
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
          {contribuintes.map((row) => (
            <TableRow
              key={row.id}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell component="th" scope="row">{row.id}</TableCell>
              <TableCell align="right">{row.dataEmissao}</TableCell>
              <TableCell align="right">{row.prestador.documento}</TableCell>
              <TableCell align="right">{row.tomador.nome}</TableCell>
              <TableCell align="right">{row.valorServico}</TableCell>
              <TableCell align="right">{row.situacaoNfse}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Paper>
    </form>
    </>
  );
}

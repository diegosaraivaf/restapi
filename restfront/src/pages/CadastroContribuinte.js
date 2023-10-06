import { Button, Card, CardContent, CardHeader, Grid, Paper, Stack, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";
import { Head } from "./head";
import { useForm ,useFieldArray} from "react-hook-form";
import { Menu } from "./Menu";
import { useState } from "react";

export function CadastroContribuinte() {
  const [contribuinte, setContribuinte]  = useState({enderecos:[]})
  
  const onSubmit = async ()=>{
    console.log(contribuinte);

    const response = await fetch('http://localhost:8080/contribuintes', { 
      method: 'POST', body: JSON.stringify(contribuinte) ,
      headers: {"Content-type": "application/json; charset=UTF-8"}
    })
    const contribuinteJson = await response.json();
    console.log(contribuinteJson);
    // .then(json => json.json())
    // .then(response => {
    //   console.log(response.content)
    // })
    // .catch(err => {
    //   console.log(err)
    // })

  } 

  const onAdicionarItem = ()=>{
    setContribuinte({...contribuinte, enderecos:contribuinte.enderecos.concat( [{rua:'teste'}]) }) 
  }
 
  return (
    <>
    <Paper className="container" >
      <Grid container spacing={2}>
        <Grid item xs={12} sm={3}>
          <TextField label="Nome" onChange={(e)=>{setContribuinte({...contribuinte, nome : e.target.value})}}  fullWidth/>
        </Grid>
        <Grid item xs={12} sm={5}>
          <TextField label="Documento" onChange={(e)=>{setContribuinte({...contribuinte, documento : e.target.value})}} fullWidth/>
        </Grid>
      </Grid>
      <Button variant="contained" onClick={onAdicionarItem} startIcon={<DeleteIcon />} >Adicionar Item</Button>
      <Table >
        <TableHead>
          <TableRow>
            <TableCell align="right">Id</TableCell>
            <TableCell align="right">Rua</TableCell>
            <TableCell align="right">Bairro</TableCell>
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


      <Stack direction={"row"} justifyContent="space-between" sx={{ mt: 2 }}>
          <Button onClick={e=> {window.location.href = `/CadastroContribuinte`}} variant="contained" color="secondary" >Voltar</Button>
          <Button type="submit" variant="contained" onClick={onSubmit} >Cadastrar</Button>
     </Stack>
    </Paper>
    </>
  );
}

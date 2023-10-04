import { Button, Card, CardContent, CardHeader, Grid, Paper, Stack, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";
import { Head } from "./head";
import { useForm ,useFieldArray} from "react-hook-form";
import { Menu } from "./Menu";

export function CadastroNfse() {
  const { register,  handleSubmit,control} = useForm();
  const {fields, append, remove} = useFieldArray({name:"itens", control});

  const onSubmit = data => {
    console.log(data);

    fetch('http://localhost:8080/nfses', { 
      method: 'POST', body: JSON.stringify(data) ,
      headers: {"Content-type": "application/json; charset=UTF-8"}
    })
    .then(json => json.json())
    .then(response => {
      console.log(response.content)
    })
    .catch(err => {
      console.log(err)
    })
  }

  const onAdicionarItem = ()=>{
    append({descricao:''})
    console.log('adiciona item');
  }

  return (
    <>
    <form onSubmit={handleSubmit(onSubmit)}>
      <Paper className="container" >
        Prestador
        <Grid container spacing={2}>
          <Grid item>
            <TextField label="Documento" {...register("prestador.documento")} fullWidth/>
          </Grid>
          <Grid item>
          <TextField label="Nome" {...register("prestador.nome")} fullWidth/>
          </Grid>
        </Grid>
      </Paper>

      <Paper className="container" >
        Tomador
        <Grid container spacing={2}>
          <Grid item>
            <TextField label="Documento" {...register("documentoTomador")} fullWidth/>
          </Grid>
          <Grid item>
          <TextField label="Nome" {...register("nomeTomador")} fullWidth/>
          </Grid>
        </Grid>
      </Paper>

      <Paper className="container" >
        <Grid container spacing={2}>
          <Grid item>
            <TextField label="Local Prestacao" {...register("localPrestacao")} fullWidth/>
          </Grid>
          <Grid item>
          <TextField label="Valor" {...register("valorServico")} fullWidth/>
          </Grid>
        </Grid>

        {/* <Grid container spacing={2}>
          <Grid item xs={12} sm={4}>
            <TextField label="Descricao" {...register("documento")} fullWidth/>
          </Grid>
          <Grid item xs={12} sm={3}>
            <TextField label="Valor" {...register("nome")} fullWidth/>
          </Grid>
          <Grid item xs={12} sm={3}>
            <TextField label="Quantidade" {...register("nome")} fullWidth/>
          </Grid>
          <Grid item xs={12} sm={2}>
            <Button onClick={onAdicionarItem} >Adicionar</Button> 
          </Grid>
        </Grid> */}

        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Id</TableCell>
              <TableCell >Descricao</TableCell>
              <TableCell >Valor</TableCell>
              <TableCell >Quantidade</TableCell>
              <TableCell > <Button onClick={onAdicionarItem} >Adicionar</Button></TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {fields.map((field,index) =>{
              return (
                <TableRow>
                  <TableCell>${index}</TableCell>
                  <TableCell><TextField {...register(`itens.${index}.descricao`)}/></TableCell>
                  <TableCell ><TextField {...register(`itens.${index}.valor`)}/></TableCell>
                  <TableCell ><TextField {...register(`itens.${index}.quantidade`)}/></TableCell>
                  <TableCell><Button onClick={()=> {remove(index)}}>Excluir</Button></TableCell>
                </TableRow>
              );
            })}
            </TableBody>
        </Table>
      </Paper>

      <Stack direction={"row"} justifyContent="space-between" sx={{ mt: 2 }}>
          <Button onClick={e=> {window.location.href = `/ConsultaNfse`}} variant="contained" color="secondary" >Voltar</Button>
          <Button type="submit" variant="contained"  >Cadastrar</Button>
     </Stack>

    </form>
    </>
  );
}

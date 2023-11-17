import { Alert, Button, DialogTitle, Grid, Input, Paper, Stack, Table, TableBody, TableCell, TableHead, TableRow, TextField, Typography } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import Api from "../componente/Api";
import { SnackbarContext } from "../componente/SnackbarContext";

export function CadastroNfse() {
//const { register,  handleSubmit,control,setValue} = useForm();
 //const {fields, append, remove} = useFieldArray({name:"itens", control});
  const [nfse,setNfse] = useState({
    id:'',
    prestador:{
      id:'',
      nome:'',
      documento:''
    },
    tomador:{
      id:'',
      nome:'',
      documento:''
    },
    itensNfse : []
  })
  const navigate = useNavigate();
  const {message} = useContext(SnackbarContext)
  const [searchParams] = useSearchParams();
  const id = searchParams.get("id")


  useEffect( () => {
    if(id != null){
      const carregar = async() =>{
        debugger
        const nfse =  (await Api.get(`/nfses/${id}`)).data
        setNfse(nfse)
      }
      
      carregar()

    }else{

    }
  }, []);

  const onSubmit = data => {
    console.log(data);

    Api.post('/nfses',nfse).then(response =>{

    }).catch(error =>{

    })

    // fetch('http://localhost:8080/nfses', { 
    //   method: 'POST', body: JSON.stringify(data) ,
    //   headers: {"Content-type": "application/json; charset=UTF-8"}
    // })
    // .then(json => json.json())
    // .then(response => {
    //   console.log(response.content)
    // })
    // .catch(err => {
    //   console.log(err)
    // })
  }

  const adicionarItem = ()=>{
    // append({descricao:''})
    setNfse({...nfse, itensNfse:nfse.itensNfse.concat([{}])})
    console.log('adiciona item');
  }

  function deleteItem(row){
    nfse.itensNfse.splice(row,1)
    setNfse({...nfse})
  }

  //melhorar essa logica depois 
  const carregarPreatadorPorDocumento = async (documento)=>{
    let contribuinte = null;

    if(!!documento && !!documento.trim()){
      contribuinte = (await Api.get(`/contribuintes/documento/${documento}`)).data
    }
      
    if(!!contribuinte){
      setNfse({...nfse,prestador: contribuinte})
    }else{
      nfse.prestador.nome = ''
      setNfse({...nfse})
    }
  }
  
  //melhorar essa logica depois 
  const carregarTomadorPorDocumento = async (documento)=>{
    let contribuinte = null;

    if(!!documento && !!documento.trim()){
      contribuinte = (await Api.get(`/contribuintes/documento/${documento}`)).data
    }
      
    if(!!contribuinte){
      setNfse({...nfse,tomador: contribuinte})
    }else{
      nfse.tomador.nome = ''
      setNfse({...nfse})
    }
   }

   const cadastrar = ()=>{
    console.log("teste")
    Api.post(`/nfses`,{
      ...nfse,
      prestadorId: nfse.prestador.id,
      tomadorId: nfse.tomador.id,
    })
    .then(response =>{
      console.log('entro aqui')
      navigate('/ConsultaNfse')
      message('Nfse cadastrada com sucesso')
    }).catch(error=>{
      console.log(error.data)
      message(error.response.data,'error')
    })
   }

  return (
    <>
    <form >

      <Paper className="container" >
        <div className="title">
          Prestador
        </div>
        <Grid container spacing={2}>
          <Grid item>
            <TextField 
              label="Documento"
              value={nfse.prestador.documento} 
              onChange={(e)=>{
                nfse.prestador.documento = e.target.value
                setNfse({...nfse})
                carregarPreatadorPorDocumento(nfse.prestador.documento)
              }} 
              fullWidth
            />

          
          </Grid>
          <Grid item>
          <TextField 
            label="Nome" 
            value={nfse.prestador.nome} 
            onChange={(e)=>{
              nfse.prestador.nome = e.target.value
              setNfse({...nfse})
            }}  
            fullWidth
          />
          </Grid>
        </Grid>
      </Paper>

      <Paper className="container" >
        <div className="title">
          Tomador
        </div>
        <Grid container spacing={2}>
          <Grid item>
            <TextField label="Documento" 
              value={nfse.tomador.documento} 
              onChange={(e)=>{
                nfse.tomador.documento = e.target.value
                setNfse({...nfse})
                carregarTomadorPorDocumento(nfse.tomador.documento)
              }}  
              fullWidth/>
          </Grid>
          <Grid item>
          <TextField label="Nome" 
            value={nfse.tomador.nome} 
            onChange={(e)=>{
              nfse.tomador.nome = e.target.value
              setNfse({...nfse})
            }}  
            fullWidth/>
          </Grid>
        </Grid>
      </Paper>

      <Paper className="container" >
      <div className="title">
          Informações da nota
        </div>
        <Grid container spacing={2}>
          <Grid item>
            <TextField label="Local Prestacao"  fullWidth/>
          </Grid>
          <Grid item>
          <TextField label="Valor"  fullWidth/>
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
              <TableCell > <Button onClick={adicionarItem} >Adicionar</Button></TableCell>
            </TableRow>
          </TableHead>
           <TableBody>
            {nfse.itensNfse?.map((field,index) =>{
              return (
                <TableRow key={index}>
                  <TableCell>${index}</TableCell>
                   <TableCell><TextField value={nfse.itensNfse[index].descricao}/></TableCell>
                  <TableCell ><TextField value={nfse.itensNfse[index].value}/></TableCell>
                  <TableCell ><TextField value={nfse.itensNfse[index].quantidade}/></TableCell>
                  <TableCell><Button onClick={()=> {deleteItem(index)}}>Excluir</Button></TableCell> 
                </TableRow>
              );
            })}
            </TableBody> 
        </Table>
      </Paper>

      <Stack direction={"row"} justifyContent="space-between" sx={{ mt: 2 }}>
          <Button onClick={e=> navigate(`/ConsultaNfse`)} variant="contained" color="secondary" >Voltar</Button>
          <Button onClick={cadastrar} variant="contained"  >Cadastrar</Button>
     </Stack>

    </form>
    </>
  );
}

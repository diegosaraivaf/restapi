import {Button, Grid, Paper, Stack, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";
import React, { useContext, useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import Api from "../componente/Api";
import { SnackbarContext } from "../componente/SnackbarContext";
import { TextFieldCurrency} from "../componente/TextFieldUtil";

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
    localPrestacao:'',
    valorServico:'',
    itensNfse : [{descricao:'',valor:'',quantidade:''}]
  })
  const navigate = useNavigate();
  const {message} = useContext(SnackbarContext)
  const [searchParams] = useSearchParams();
  const id = searchParams.get("id")

  


  useEffect( () => {
    if(!!id){
      const carregar = async() =>{
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
  }

  const adicionarItem = ()=>{
    // append({descricao:''})
    setNfse({...nfse, itensNfse:nfse.itensNfse.concat([{descricao:'',valor:'',quantidade:''}])})
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
    
    if(!!id) {
      Api.put(`/nfses/${id}`,{
        ...nfse,
        prestadorId: nfse.prestador.id,
        tomadorId: nfse.tomador.id,
      })
      .then(response =>{
        navigate('/ConsultaNfse')
        message('Nfse editada com sucesso')
      }).catch(error=>{
        console.log(String(error.data))
        message(error.response.data,'error')
      })
    }
    else{
      Api.post(`/nfses`,{
        ...nfse,
        prestadorId: nfse.prestador.id,
        tomadorId: nfse.tomador.id,
      })
      .then(response =>{
        navigate('/ConsultaNfse')
        message('Nfse cadastrada com sucesso')
      }).catch(error=>{
        console.log(String(error.data))
        message(error.response.data,'error')
      })
    }
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
            disabled
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
            disabled
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
            <TextField 
              label="Local Prestacao"
              value={nfse.localPrestacao} 
              onChange={(e)=>{
                setNfse({...nfse, localPrestacao :e.target.value})
              }}   
              fullWidth/>
          </Grid>
          <Grid item>
          <TextFieldCurrency
            label="Valor"
            value={nfse.valorServico}
            onChange={(e)=>{
              setNfse({...nfse, valorServico :e.target.value})
            }}   
            fullWidth/>
          </Grid>
        </Grid>

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
                  <TableCell>
                    <TextField 
                      value={nfse.itensNfse[index].descricao}
                      onChange={(e)=>{
                        nfse.itensNfse[index].descricao = e.target.value
                        setNfse({...nfse})
                      }} /></TableCell>
                  <TableCell >
                    <TextField 
                    value={nfse.itensNfse[index].valor}
                    onChange={(e)=>{
                      nfse.itensNfse[index].valor = e.target.value
                      setNfse({...nfse})
                    }}/></TableCell>
                  <TableCell >
                    <TextField 
                    value={nfse.itensNfse[index].quantidade}
                    onChange={(e)=>{
                      nfse.itensNfse[index].quantidade = e.target.value
                      setNfse({...nfse})
                    }}/></TableCell>
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

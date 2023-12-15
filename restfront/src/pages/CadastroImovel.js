import { Button, Grid,  ImageList,  ImageListItem,  Paper, Stack, TextField } from "@mui/material";
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import KeyboardReturnIcon from '@mui/icons-material/KeyboardReturn';
import { useContext, useEffect, useState } from "react";
import { SnackbarContext } from "../componente/SnackbarContext";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";
import Api from "../componente/Api";

export function CadastroImovel() {
  const {message} = useContext(SnackbarContext)
  const navigate = useNavigate();
  const location = useLocation();
  const [searchParams] = useSearchParams();
  const id = searchParams.get("id")
  const [imovel, setImovel]  = useState({
    proprietario: {id:'', nome:'', documento:''},
    imagens:[]
  })
  const [imagens,setImagens] = useState([])


  useEffect( () => {
    if(id != null){
      let imovel = {}

      const carregar = async() =>{
        debugger
        imovel =  (await Api.get(`/imoveis/${id}`)).data
        setImovel(imovel)

        let imagens =  (await Api.get(`/imoveis/${id}/imagens`)).data
        setImagens(imagens)
      }
      carregar()
    }
    else{
    }
  }, []);

  const cadastrar = async ()=>{
    try{
      debugger
      const imovelAux  = (await Api.post('/imoveis',imovel)).data

      navigate("/ConsultaImovel");
      message('Imovel cadastrado com sucesso','success')
    }
    catch(error){
      console.log(error)
      message(error.response.data.message ,'error')
    }
  } 

  const uploadFile = (event) =>{
    debugger
    console.log(event)

    const formData = new FormData();
    formData.append('file', event.target.files[0]);
    const config={
      headers:{'content-type':'multipart/form-data'}
    }

    Api.post(`/imoveis/${imovel.id}/imagens`,formData, config).then(response =>{
      setImagens([...imagens,response.data])  
      
    }).catch(error =>{
      debugger
      console.log(error)
    })
  }

  const onChangeDocumento = (evento) =>{
    debugger
    imovel.proprietario.documento = evento.target.value

    Api.get('contribuintes/documento/'+evento.target.value)
    .then(response => {
      console.log(response);
      imovel.proprietario = response.data 
      setImovel({...imovel})
    })
    .catch(error =>{
      console.log(error)
    })
  }
 
  return (
    <>
    <Paper className="container" >
      <Grid container spacing={2}>
        <Grid item xs={12} sm={3}>
          <TextField 
            label="Documento" 
            value={imovel.proprietario.documento} 
            onChange={(e)=>{
              onChangeDocumento(e)
            }}  
            fullWidth    
            />
        </Grid>
        <Grid item xs={12} sm={3}>
         <TextField
            value={imovel.proprietario.nome}
            onChange={e=>{
              imovel.proprietario.nome = e.target.value
              setImovel({...imovel})
            }}
            label="Nome"
            fullWidth
          />
        </Grid>
      </Grid>

      <Stack direction={"row"} justifyContent="space-between" sx={{ mt: 5 }}>
          <Button onClick={e=> {navigate(`/ConsultaCaracteristica`)}} variant="contained" color="secondary" startIcon={<KeyboardReturnIcon/>}>Voltar</Button>
          <Button type="submit" variant="contained" onClick={cadastrar} startIcon={<AddCircleOutlineIcon/>}>Cadastrar</Button>
     </Stack>
    </Paper>

    <Paper className="container" >
      Imagens
      <Button
        variant="contained"
        component="label"
      >
        Upload File
        <input
          type="file"
          hidden
          onChange={uploadFile}
        />
      </Button>

      <ImageList sx={{ width: 500, height: 450 }} cols={3} rowHeight={164}>
        {imagens.map((item) => (
          <ImageListItem key={item.id}>
            <img
              srcSet={`${item.arquivo}?w=164&h=164&fit=crop&auto=format&dpr=2 2x`}
              src={`data:image/jpeg;base64,${item.arquivo}`} 
              alt={item.nomeArquivo}
              loading="lazy"
            />
          </ImageListItem>
        ))}
      </ImageList> 
    </Paper>
    </>
  );
}

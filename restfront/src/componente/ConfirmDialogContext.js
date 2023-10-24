import { Close } from "@mui/icons-material";
import { Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, IconButton, Typography, } from "@mui/material";
import { createContext, useState,useEffect } from "react";

//criacao do contexto
export const ConfirmDialogContext = createContext();

//criacao do provider
//childen se refere ao componente filhos, os que estao 'envolvido' por este componente
export const ConfirmDialogProvider = ({children}) =>{
    const [open,setOpen] = useState(false)
    const [messages,setMessages] = useState([])
    const [onConfirm,setOnConfirm] = useState([])

    //acao disponibilizada globalmente
    const confirmDialog =(message,confirm)=>{
        setMessages(message)
        setOnConfirm(() => { return confirm });
        setOpen(true)
    }

    const handleClose = (id) => {
        // setOpen(false);
    };

    return (
    //propriedate 'value' define quais metodos vao ficao visiveis 
    <ConfirmDialogContext.Provider value={{confirmDialog}}>
      {children}
      <Dialog open={open} maxWidth="sm" fullWidth>
        <DialogTitle>{messages}</DialogTitle>
        <Box position="absolute" top={0} right={0}>
          <IconButton onClick={()=> setOpen(false)}>
            <Close />
          </IconButton>
        </Box>
        <DialogContent>
          <Typography>some message here</Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={()=> setOpen(false)} color="primary" variant="contained">
            Cancel
          </Button>
          <Button onClick={()=>{onConfirm();setOpen(false);}} color="secondary" variant="contained">
            Confirm
          </Button>
        </DialogActions>
      </Dialog>
      </ConfirmDialogContext.Provider>
    )
}
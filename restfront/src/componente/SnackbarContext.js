import { Alert, Snackbar } from "@mui/material";
import { createContext, useState } from "react";

//criacao do contexto
export const SnackbarContext = createContext();

//criacao do provider
//childen se refere ao componente filhos, os que estao 'envolvido' por este componente
export const SnackBarProvider = ({children}) =>{
    const [open,setOpen] = useState(false)
    const [messages,setMessages] = useState([])
    const [severity,setSeverity] = useState('info')

    //acao disponibilizada globalmente
    const message =(message,severity)=>{

        const newSnackbar = {
            id: new Date().getTime(),
            message,
            severity,
          };

        setMessages(messages.concat([newSnackbar]))
        // "error","info","success","warning"
        setSeverity(severity)
        setOpen(true)
    }

    const handleClose = (id) => {
        setMessages((prev) => prev.filter((item) => item.id !== id));
        // setOpen(false);
    };
    

    return (
        //propriedate 'value' define quais metodos vao ficao visiveis 
        <SnackbarContext.Provider value={{message}}>
            {children}
            {
                messages.map((item) => (
                <Snackbar
                    key={item.id}
                    open={true}
                    anchorOrigin={{ vertical:'top', horizontal:'center'  }}
                    autoHideDuration={5000}
                    onClose={()=>handleClose(item.id)}
                >
                    <Alert severity={item.severity}>
                        {item.message}
                    </Alert>
                </Snackbar>
                ))
            }
        </SnackbarContext.Provider>
    )
}
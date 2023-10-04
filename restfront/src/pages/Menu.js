import { List, ListItemButton, ListItemIcon, ListItemText } from "@mui/material";

export function Menu(){
    return (<>
         <List sx={{ maxWidth: 360, bgcolor: 'background.paper' }}>
            <ListItemButton onClick={e=> {window.location.href = `/`}}>
                <ListItemIcon>
                    {/* <SendIcon /> */}
                </ListItemIcon>
                <ListItemText primary="Inicio" />
            </ListItemButton>
            <ListItemButton onClick={e=> {window.location.href = `/ConsultaNfse`}}>
                <ListItemIcon>
                {/* <DraftsIcon /> */}
                </ListItemIcon>
                <ListItemText primary="Nfse" />
            </ListItemButton>
            <ListItemButton onClick={e=> {window.location.href = `/ConsultaContribuinte`}}>
                <ListItemIcon>
                {/* <DraftsIcon /> */}
                </ListItemIcon>
                <ListItemText primary="Contribuinte" />
            </ListItemButton>

         </List>
    </>);
}



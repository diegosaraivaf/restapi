import { Collapse, Drawer, List, ListItem, ListItemIcon, ListItemText } from "@mui/material";

import React, { useState } from "react";

import ExpandLessIcon from '@mui/icons-material/ExpandLess';
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import HomeOutlinedIcon from "@mui/icons-material/HomeOutlined";
import LocalLibraryOutlinedIcon from "@mui/icons-material/LocalLibraryOutlined";
import TrendingUpOutlinedIcon from "@mui/icons-material/TrendingUpOutlined";
import { useNavigate } from "react-router-dom";
// import DescriptionOutlinedIcon from "@material-ui/icons/DescriptionOutlined";

const menu = [
    {
      icon: <HomeOutlinedIcon />,
      title: "Home",
      items: [],
      to: "/Home"
    },
    {
      icon: <LocalLibraryOutlinedIcon />,
      title: "Notas",
      items: [
        {
          title: "NFSe",
          to: '/ConsultaNfse'
        },
        {
          title: "Fundamental Analysis",
          items: [
            {
              title: "The Dow Theory",
              to: "/thedowtheory"
            },
            {
              title: "Charts & Chart Patterns",
              to: "/chart"
            },
            {
              title: "Trend & Trend Lines",
              to: "/trendlines"
            },
            {
              title: "Support & Resistance",
              to: "/sandr"
            }
          ]
        },
        {
          title: "Elliot Wave Analysis",
          items: [
            {
              title: "The Dow Theory",
              to: "/thedowtheory"
            },
            {
              title: "Charts & Chart Patterns",
              to: "/chart"
            },
            {
              title: "Trend & Trend Lines",
              to: "/trendlines"
            },
            {
              title: "Support & Resistance",
              to: "/sandr"
            }
          ]
        }
      ]
    },
    {
      icon: <TrendingUpOutlinedIcon />,
      title: "Contribuinte",
      to: "/ConsultaContribuinte"
    },
    {
      icon: <TrendingUpOutlinedIcon />,
      title: "Imovel",
      to: "/ConsultaImovel"
    },
    {
      icon: <HomeOutlinedIcon />,
      title: "Configurações",
      items: [
        {
          title: "Caracteristicas",
          to: "/ConsultaCaracteristica"
        },
        {
          title: "Opções caracteristicas",
          to: "/ConsultaOpcaoCaracteristica"
        },
      ],
    },
    {
      //   icon: <DescriptionOutlinedIcon />,
        title: "Chat room",
        to:'/ChatRoom'
    },
    {
    //   icon: <DescriptionOutlinedIcon />,
      title: "Api Documentacao",
      to:'http://localhost:8080/swagger-ui/index.html'
    }
  ];

export function Menu(){
    return (
        <aside id="aside-layout">
          <Drawer
              sx={{
                  flexShrink: 0
              }}
              variant="permanent"
              anchor="left"
          >
            <List> 
                <MontarMenu/>
            </List> 
          </Drawer>
        </aside>
        );
}

function MontarMenu() {
    return menu.map((item, key) => <MenuItem key={key} item={item} />);
}



const MenuItem = ({ item }) => {
  
  const Component = hasChildren(item) ? MultiLevel : SingleLevel;
  return <Component item={item} />;
};




const SingleLevel = ({ item }) => {
  const navigate = useNavigate();
  return (
      <ListItem onClick={e=> {navigate(item.to)}} >
      <ListItemIcon>{item.icon}</ListItemIcon>
      <ListItemText primary={item.title}  />
      </ListItem>
  );
};

const MultiLevel = ({ item }) => {
    const { items: children } = item;
    const [open, setOpen] = useState(false);

    const handleClick = () => {
        setOpen((prev) => !prev);
    };

    return (
        <>
        <ListItem button onClick={handleClick} className={open?'menu-list-selected':''}>
            <ListItemIcon>{item.icon}</ListItemIcon>
            <ListItemText primary={item.title} />
            {open ? <ExpandLessIcon /> : <ExpandMoreIcon />} 
        </ListItem>
        <Collapse in={open} timeout="auto" unmountOnExit>
            <List component="div" disablePadding>
            {children.map((child, key) => (
                <MenuItem key={key} item={child} />
            ))}
            </List>
        </Collapse>
        </>
    );
};

function hasChildren(item) {
    const { items: children } = item;

    if (children === undefined) {
        return false;
    }

    if (children.constructor !== Array) {
        return false;
    }

    if (children.length === 0) {
        return false;
    }

    return true;
}



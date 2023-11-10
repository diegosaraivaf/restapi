import { Collapse, Divider, Drawer, List, ListItem, ListItemButton, ListItemIcon, ListItemText } from "@mui/material";
import InboxIcon from '@mui/icons-material/MoveToInbox';
import MailIcon from '@mui/icons-material/Mail';
import React, { useState } from "react";

import ExpandLessIcon from '@mui/icons-material/ExpandLess';
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import HomeOutlinedIcon from "@mui/icons-material/HomeOutlined";
import LocalLibraryOutlinedIcon from "@mui/icons-material/LocalLibraryOutlined";
import TrendingUpOutlinedIcon from "@mui/icons-material/TrendingUpOutlined";
// import DescriptionOutlinedIcon from "@material-ui/icons/DescriptionOutlined";

const menu = [
    {
      icon: <HomeOutlinedIcon />,
      title: "Home",
      items: []
    },
    {
      icon: <LocalLibraryOutlinedIcon />,
      title: "Education",
      items: [
        {
          title: "Technical Analysis",
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
      title: "Options"
    },
    {
    //   icon: <DescriptionOutlinedIcon />,
      title: "Blog"
    }
  ];

export function Menu(){
    return (<>
        <aside id="aside-layout">
        <Drawer
            sx={{
                width: 240,
                flexShrink: 0,
                '& .MuiDrawer-paper': {
                width: 240,
                boxSizing: 'border-box',
                },
            }}
            variant="permanent"
            anchor="left"
        >
            <List> 
                <MontarMenu/>
                {/* <ListItem disablePadding>
                    <ListItemButton onClick={e=> {window.location.href = `/`}}>
                        <ListItemIcon>
                            <InboxIcon /> 
                        </ListItemIcon>
                        <ListItemText primary="Inicio" />
                    </ListItemButton>
                </ListItem>
           
                <ListItem disablePadding>
                    <ListItemButton onClick={e=> {window.location.href = `/ConsultaNfse`}}>
                        <ListItemIcon>
                            <MailIcon /> 
                        </ListItemIcon>
                        <ListItemText primary="Noto Fiscal" />
                    </ListItemButton>
                </ListItem>
          
                <ListItem disablePadding>
                    <ListItemButton onClick={e=> {window.location.href = `/ConsultaContribuinte`}} >
                        <ListItemIcon>
                            <InboxIcon /> 
                        </ListItemIcon>
                        <ListItemText primary="Contribuinte" />
                    </ListItemButton>
                </ListItem>

                <Collapse in={true} timeout="auto" unmountOnExit>
                    <Divider />
                    <List component="div" disablePadding>
                    <ListItem >
                        <ListItemText inset primary="Nested Page 1" />
                    </ListItem>
                    <ListItem>
                        <ListItemText inset primary="Nested Page 2" />
                    </ListItem>
                    </List>
                </Collapse> */}
            </List> 
  
        </Drawer>
        </aside>
        </>);
}

function MontarMenu() {
    return menu.map((item, key) => <MenuItem key={key} item={item} />);
}

const MenuItem = ({ item }) => {
    const Component = hasChildren(item) ? MultiLevel : SingleLevel;
    return <Component item={item} />;
};

const SingleLevel = ({ item }) => {
    return (
        <ListItem >
        <ListItemIcon>{item.icon}</ListItemIcon>
        <ListItemText primary={item.title} />
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
        <ListItem button onClick={handleClick}>
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



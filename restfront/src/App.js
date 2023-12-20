import './App.css';
import { BrowserRouter, Outlet, Route, Routes,Redirect, Navigate } from "react-router-dom";
import { Home } from './pages/Home';
import { About } from './pages/About';
import { CadastroNfse } from './pages/CadastroNfse';
import { ConsultaNfse } from './pages/ConsultaNfse';
import "./index.css";
import { ConsultaContribuinte } from './pages/ConsultaContribuinte';
import Layout from './pages/Layout';
import { CadastroContribuinte } from './pages/CadastroContribuinte';
import { SnackBarProvider } from './componente/SnackbarContext';
import { ConfirmDialogProvider } from './componente/ConfirmDialogContext';
import { Login } from './pages/Login';
import { ConsultaCaracteristica } from './pages/ConsultaCaracteristica';
import { CadastroCaracteristica } from './pages/CadastroCaracteristica';
import { ConsultaOpcaoCaracteristica } from './pages/ConsultaOpcaoCaracteritica';
import { CadastroOpcaoCaracteristica } from './pages/CadastroOpcaoCaracteristica';
import { ConsultaImovel } from './pages/ConsultaImovel';
import { CadastroImovel } from './pages/CadastroImovel';

function App() {
  return (
    <BrowserRouter>
      <ConfirmDialogProvider>
      <SnackBarProvider>
    
          <Routes>
            
            <Route  element={<Layout> <Outlet /> </Layout>}>
              <Route  element={<PrivateRoute/>}>
                <Route path="/Home" exact element={<Home />} />
                <Route path="/About" element={<About />} />
                <Route path="/ConsultaNfse" element={<ConsultaNfse />} />
                <Route path="/CadastroNfse" element={<CadastroNfse />} />
                <Route path="/ConsultaContribuinte" element={<CadastroNfse/>} />
                <Route path="/CadastroContribuinte/:id?" element={<CadastroContribuinte />} />
                <Route path="/ConsultaCaracteristica" element={<ConsultaCaracteristica />} />
                <Route path="/CadastroCaracteristica/:id?" element={<CadastroCaracteristica />} />
                <Route path="/ConsultaOpcaoCaracteristica" element={<ConsultaOpcaoCaracteristica />} />
                <Route path="/CadastroOpcaoCaracteristica/:id?" element={<CadastroOpcaoCaracteristica />} />
                <Route path="/ConsultaImovel" element={<ConsultaImovel/>} />
                <Route path="/CadastroImovel" element={<CadastroImovel/>}  />
              </Route>
            </Route>
            <Route path="/" element={<Login />} />
          </Routes>
   
        
      </SnackBarProvider>
      </ConfirmDialogProvider>
    </BrowserRouter>
  );
}

const PrivateRoute = () => {
  const auth = localStorage.getItem('token'); // determine if authorized, from context or however you're doing it

  // If authorized, return an outlet that will render child elements
  // If not, return element that will navigate to login page
  return auth ? <Outlet /> : <Navigate to="/" />;
}

export default App;

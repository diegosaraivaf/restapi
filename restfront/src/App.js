import logo from './logo.svg';
import './App.css';
import { Card } from './componente/Card';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Home } from './pages/Home';
import { About } from './pages/About';
import { CadastroNfse } from './pages/CadastroNfse';
import { ConsultaNfse } from './pages/ConsultaNfse';
import "./index.css";
import { ConsultaContribuinte } from './pages/ConsultaContribuinte';
import Layout from './pages/Layout';
import { CadastroContribuinte } from './pages/CadastroContribuinte';

function App() {
  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/about" element={<About />} />
          <Route path="/ConsultaNfse" element={<ConsultaNfse />} />
          <Route path="/CadastroNfse" element={<CadastroNfse />} />
          <Route path="/ConsultaContribuinte" element={<ConsultaContribuinte />} />
          <Route path="/CadastroContribuinte" element={<CadastroContribuinte />} />
        </Routes>
      </Layout>
    </BrowserRouter>
  );
}

export default App;

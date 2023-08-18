import logo from './logo.svg';
import './App.css';
import { Card } from './componente/Card';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Home } from './pages/Home';
import { About } from './pages/About';
import { CadastroNfse } from './pages/CadastroNfse';
import { ConsultaNfse } from './pages/ConsultaNfse';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/ConsultaNfse" element={<ConsultaNfse />} />
        <Route path="/CadastroNfse" element={<CadastroNfse />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;

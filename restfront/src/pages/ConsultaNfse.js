import { Button } from "../componente/Button";
import { Input } from "../componente/Input";
import { Head } from "./head";

export function ConsultaNfse() {
  return (
    <>
    <Head/>
    
    {/* <a href="#" className="block rounded-md bg-indigo-600  h-52 font-medium text-white  hover:bg-indigo-700">Checkout</a> */}

    <h1>Consulta NFSe</h1>

    <Button label="Cadastro" onClick={e=> {window.location.href = `/CadastroNfse`}}/>
    <Button label="Pesquisa" />
    


    id 
    <Input />
    Tipo
    Valor
    Documento
    Nome 
    Situacao

    pdf

    table
    Id
    Data Emissao
    Prestador
    Tomador
    Valor
    Situação
    Acções

    
    </>
  );
}

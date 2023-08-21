import { Button } from "../componente/Button";
import { Input } from "../componente/Input";
import { Head } from "./head";

export function ConsultaNfse() {
  return (
    <>
    <div className="container md mx-auto">
      <Head/>
      
      <h1 className="mx-auto">Consulta NFSe</h1>

      <Button label="Cadastro" onClick={e=> {window.location.href = `/CadastroNfse`}}/>
      <Button label="Pesquisa" />
      <button className="btn btn-secondary">teste</button>

      <div className="grid grid-cols-2 gap-4">
        <div className="relative mb-6" data-te-input-wrapper-init>
          id  
          <Input />
        </div>
        <div className="relative mb-6" data-te-input-wrapper-init>
          Tipo
          <Input />
        </div>
      </div>
      Valor
      Documento
      Nome 
      Situacao
    </div>
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

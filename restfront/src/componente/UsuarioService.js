import { jwtDecode } from "jwt-decode";

export function getUsuarioLogado() {
    //pega o usuario do token
    const token = localStorage.getItem('token');
    const decoded = jwtDecode(token);
    return decoded
  }
  
  
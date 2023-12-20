import axios from 'axios'
import { useNavigate } from 'react-router-dom';

const Api = axios.create({
    baseURL:'http://localhost:8080'
})



//seta o token no head de todas a requisicoes 
Api.defaults.headers.common['Authorization'] = 
    localStorage.getItem('token') != null ? 'Bearer '+ localStorage.getItem('token') : null

//interceptador de responses
Api.interceptors.response.use(
    response => response,
    error =>  {
        debugger
        if(error.code == "ERR_NETWORK"){
            alert("API fora dor ar")  
        // }else if(error.response.status == 401){
        }else if(error.response.status == 403){
            debugger
            localStorage.removeItem("token");
            window.location.href="/";
        }

        return Promise.reject(error) 
    }
);

export default Api;
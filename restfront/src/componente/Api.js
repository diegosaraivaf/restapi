import axios from 'axios'
import { useNavigate } from 'react-router-dom';

export const URL = 'http://192.168.1.5:8080' 

const Api = axios.create({
    baseURL: URL
})


//seta o token no head de todas a requisicoes 
// Api.defaults.headers.common['Authorization'] = 
//     localStorage.getItem('token') != null ? 'Bearer '+ localStorage.getItem('token') : null

//interceptador de responses
Api.interceptors.response.use(
    response => response,
    error =>  {
        debugger
        if(error.code == "ERR_NETWORK"){
            alert("API fora dor ar")  
        // }else if(error.response.status == 401){
        }else if(error.response.status == 403){
            localStorage.removeItem("token");
            window.location.href="/";
        }

        return Promise.reject(error) 
    }
);

Api.interceptors.request.use(
    (request) => {
        const token = localStorage.getItem('token');
        if (token) {
            request.headers['Authorization'] = `Bearer ${token}`;
        }
        return request;
    }, 
    (error) => {
        return Promise.reject(error);
    }
);

export default Api;
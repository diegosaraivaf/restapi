import axios from 'axios'

const Api = axios.create({
    baseURL:'http://localhost:8080'
})

//seta o token no head de todas a requisicoes 
Api.defaults.headers.common['Authorization'] = 
    localStorage.getItem('token') != null ? 'Bearer '+ localStorage.getItem('token') : null

export default Api;
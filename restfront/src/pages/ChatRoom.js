import {over} from 'stompjs'
import SockJS from 'sockjs-client'
import { useEffect, useState } from 'react'
import {getUsuarioLogado} from '../componente/UsuarioService';
import { URL } from '../componente/Api';

//porque que se eu colocar essa variavel dentro de function ChatRoom ela fica zerando ??
//variavel brobal shared between all the ChatRomm
var stompClient = null

export function ChatRoom() {
    const [mensagens,setMensagens] = useState([])
    const [mensagem,setMensagem] = useState('')
    const nomeUsuario = getUsuarioLogado().nome

    useEffect( () => {
        resgisterUser()
    }, []);

    //se conecta ao socket
    const resgisterUser = () =>{
        //stomp endpoint
        if(!stompClient){
            let Sock = new SockJS(URL+'/ws');
            stompClient = over(Sock)
            stompClient.connect({},onConnected,onError)
        }
    }

    //quando a conexao com o socket form bem sucedida essa function e execultada
    //faz a inscricao no topicos do socket do servidor
    const onConnected =()=>{
        //subscribe to the endpoint public
        stompClient.subscribe("/chatroom/public",onPublicMessageReceived)
        
        //subscribe for the private chat
        // stompClient.subscribe('/user/'+userName+'/private',onPrivateMessageReceived)
    }

    //execultado quando ocorre um erro ao consectar o stompClient
    const onError =()=>{
        alert('erro no socket')
    }

    //metodo referencia ao vincular um topico ao stompClinet,
    //execulta uma regra quado o topico no servidor recebe alguma atualizacao 
    const onPublicMessageReceived = (payload) =>{
        //payload is the stomp message and the payload is inside the body
        let payloadData = JSON.parse(payload.body)
        setMensagens(prevMensagens => [...prevMensagens, payloadData.senderName +': '+ payloadData.message]);
    }

    // const onPrivateMessageReceived = (payload) =>{
    //     //payload is the stomp message and the payload is inside the bory
    //     let payloadData = JSON.parse(payload.body)
    // }

    //envia uma mensagem para o 'endpoint' disponibilizado no caso " @MessageMapping("/message")"
    const sendPublicMessage=()=>{
        if(stompClient){
            let message = {
                senderName:nomeUsuario,
                message:mensagem,
                status:'MESSAGE'
            }
            
            stompClient.send('/app/message',{},JSON.stringify(message))
            setMensagem('')
        }
    }

    return (<>
        <input 
            value={mensagem} 
            //onKeyUp por algum motivo quebrou o onChage
            onKeyPress={e => {
                if (e.code === "Enter"){
                    sendPublicMessage()
                }
            }}
            onChange={e=> {
                setMensagem(e.target.value)
            }}  
        />
        <button onClick={sendPublicMessage}  >Enviar</button>

        <div>
            <ul>
            {mensagens.map((mensagem,index)=>( 
                <li key={index}>{mensagem}</li>
            ))}
            </ul>
        </div>
       
    </>)
}
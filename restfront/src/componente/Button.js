export function Button({label,onClick}){
    return(<>
        <button onClick={onClick} class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">{label}</button>
    </>)
}
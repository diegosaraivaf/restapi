export function Button({label,onClick}){
    return(<>
        <button onClick={onClick} className="btn btn-primary">
            <i class="bi-person-plus-fill me-1"></i>
            {label}
        </button>
    </>)
}

export function Card({titulo,descricao}) {
  return (
    <>
    <div style={{backgroundColor: 'blue'}}>
      {titulo}
    </div>
    <div style={{backgroundColor: 'gray'}}>
      {descricao}
    </div>
    </>
  );
}


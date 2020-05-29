// Manejo de mensajes SweetAlert para eliminar cliente

const eliminar = document.querySelector('.listado-clientes');

if(eliminar){
    eliminar.addEventListener('click', e => {        
        if(e.target.classList.contains('btn-outline-danger')){
            e.preventDefault()
            const boton = e.target;            
            const idCliente = boton.dataset.cliente;
            const url = `${location.origin}/cliente/eliminar/${idCliente}`
 
            Swal.fire({
                title: 'Eliminar cliente',
                text: "Un vez eliminado no es posible recuperarlo",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si, eliminar!'
              }).then((result) => {
                if (result.value) {                                                       
                    window.location= `${url}` 
                }
              })              
        }
    });
}

// Manejo de mensajes SweetAlert actualizacion y registro de cliente
const mensaje = document.querySelector('.mensaje-success');

if(mensaje){
    const impresionMensaje = mensaje.dataset.mensaje;
    
    Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: `${impresionMensaje}`,
        showConfirmButton: false,
        timer: 2500
    })
}

// Manejo de mensajes SweetAlert para manejo de errores

const mensajeError = document.querySelector('.mensaje-error');

if(mensajeError){
    const impresionMensaje = mensajeError.dataset.mensaje;    
    Swal.fire({
        position: 'top-end',
        icon: 'error',
        title: `${impresionMensaje}`,
        showConfirmButton: false,
        timer: 2500
    })
}

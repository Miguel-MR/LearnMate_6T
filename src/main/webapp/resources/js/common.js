function handleGuardarCambios(button) {
    if (!confirm('¿Está seguro de que desea guardar los cambios?')) {
        return false;
    }

    // El postback (envío de JSF) empieza inmediatamente después de que retornamos 'true'.
    // Usamos setTimeout para que la deshabilitación se ejecute un instante *después*
    // de que el navegador haya procesado el clic, pero antes de que el usuario pueda volver a hacer clic.
    setTimeout(function() {
        button.disabled = true;
    }, 50); // 50 milisegundos son suficientes.

    // Devolvemos true para que el postback de JSF se ejecute.
    return true;
}
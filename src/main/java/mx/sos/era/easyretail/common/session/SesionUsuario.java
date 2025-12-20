package mx.sos.era.easyretail.common.session;

public class SesionUsuario {
    private String usuarioActivo;

    public SesionUsuario(String usuarioActivo) {
        this.usuarioActivo = usuarioActivo;
    }

    public String getUsuarioActivo() {
        return usuarioActivo;
    }

    public void setUsuarioActivo(String usuarioActivo) {
        this.usuarioActivo = usuarioActivo;
    }
}
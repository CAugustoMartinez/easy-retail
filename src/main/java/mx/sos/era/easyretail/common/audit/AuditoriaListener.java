package mx.sos.era.easyretail.common.audit;

import jakarta.persistence.PreUpdate;
import mx.sos.era.easyretail.common.session.SesionUsuarioContext;

import java.time.LocalDateTime;

public class AuditoriaListener {
    @PreUpdate
    public void preUpdate(Object entity){
        if (entity instanceof Auditable auditable){
            String usuarioActivo = SesionUsuarioContext.getSesionUsuario().getUsuarioActivo();
            auditable.setModificadoPor(usuarioActivo != null ? usuarioActivo : "SISTEMA");
            auditable.setFechaModificacion(LocalDateTime.now());
        }
    }
}
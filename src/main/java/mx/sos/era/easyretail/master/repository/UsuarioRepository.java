package mx.sos.era.easyretail.master.repository;

import mx.sos.era.easyretail.master.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsuario(String usuario);
}
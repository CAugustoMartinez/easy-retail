package mx.sos.era.easyretail.master.repository;

import mx.sos.era.easyretail.master.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    Optional<Empresa> findByRfc(String rfc);

    Optional<Empresa> findByNombre(String nombre);
}
package mx.sos.era.easyretail.master.service;

import javafx.collections.FXCollections;
import mx.sos.era.easyretail.master.entity.Empresa;
import mx.sos.era.easyretail.master.repository.EmpresaRepository;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
    private final EmpresaRepository repo;

    public EmpresaService(EmpresaRepository repo){
        this.repo = repo;
    }

    public Empresa crear(Empresa empresa){
        return repo.save(empresa);
    }

    public List<Empresa> listar(){
        return repo.findAll();
    }

    public Optional<Empresa> buscarPorId(Integer idEmpresa){
        return repo.findById(idEmpresa);
    }

    public Optional<Empresa> buscarPorNombre(String nombre){
        return repo.findByNombre(nombre);
    }

    public Optional<Empresa> buscarPorRfc(String rfc){
        return repo.findByRfc(rfc);
    }

    public Empresa actualizar(Empresa empresa){
        return repo.save(empresa);
    }

    public void eliminar(Integer idEmpresa){
        repo.deleteById(idEmpresa);
    }
}
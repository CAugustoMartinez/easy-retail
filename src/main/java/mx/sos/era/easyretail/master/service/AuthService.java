package mx.sos.era.easyretail.master.service;

import mx.sos.era.easyretail.master.entity.Usuario;
import mx.sos.era.easyretail.master.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public Usuario login(String usuario, String password) {
        Usuario u = repo.findByUsuario(usuario);
        if (u == null) return null;

        if (encoder.matches(password, u.getPassword())) {
            return u;
        }
        return null;
    }

    public Usuario crearUsuario(String usuario, String password, String nombre, String rol) {
        String hash = encoder.encode(password);
        Usuario u = new Usuario(usuario, hash, nombre, rol);
        return repo.save(u);
    }

    public void resetPassword(Long idUsuario, String nuevaPassword) {
        Usuario u = repo.findById(idUsuario).orElse(null);
        if (u == null) return;

        u.setPassword(encoder.encode(nuevaPassword));
        repo.save(u);
    }
}
package mx.sos.era.easyretail.ui.listeners;

import mx.sos.era.easyretail.master.entity.Usuario;

public interface LoginListener {
    void onLoginSuccess(Usuario usuario);
}
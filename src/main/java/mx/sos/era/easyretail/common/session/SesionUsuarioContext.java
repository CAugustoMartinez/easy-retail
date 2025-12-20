package mx.sos.era.easyretail.common.session;

public class SesionUsuarioContext {
    private static final ThreadLocal<SesionUsuario> contexto = new ThreadLocal<>();

    public static void setSesionUsuario(SesionUsuario sesion){
        contexto.set(sesion);
    }

    public static SesionUsuario getSesionUsuario(){
        return contexto.get();
    }

    public static void clearSesion(){
        contexto.remove();
    }
}
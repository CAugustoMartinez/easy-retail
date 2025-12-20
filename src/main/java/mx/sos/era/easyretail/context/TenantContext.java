package mx.sos.era.easyretail.context;

public class TenantContext {
    private static final ThreadLocal<String> CURRENT = new ThreadLocal<>();
    public static void setCurrentTenant(String tenantId){ CURRENT.set(tenantId); }
    public static String getCurrentTenant(){ return CURRENT.get(); }
    public static void clear(){ CURRENT.remove(); }
}
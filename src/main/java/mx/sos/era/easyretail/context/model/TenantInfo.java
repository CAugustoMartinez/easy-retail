package mx.sos.era.easyretail.context.model;

public class TenantInfo {
    private String tenantId;   // Ej: "empresa_123"
    private String host;       // Ej: "localhost" o IP del servidor
    private int port;          // Ej: 3306
    private String database;   // Ej: "empresa_123"
    private String user;       // Usuario de conexión
    private String password;   // Contraseña (idealmente cifrada)

    public TenantInfo() {}

    public TenantInfo(String tenantId, String host, int port, String database, String user, String password) {
        this.tenantId = tenantId;
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    // Getters y setters
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }

    public String getDatabase() { return database; }
    public void setDatabase(String database) { this.database = database; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
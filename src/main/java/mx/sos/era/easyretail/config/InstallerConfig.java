package mx.sos.era.easyretail.config;

import mx.sos.era.easyretail.util.CryptoHelper;

/**
 * Configuración inicial del sistema para la base maestra (dbempresas).
 * Se llena en tiempo de instalación, no en desarrollo.
 */
public class InstallerConfig {

    private String host;
    private int port;
    private String database;
    private String user;
    private String encryptedPassword;
    private String appPath;
    private String branch;
    private String cashRegister;


    public InstallerConfig() {}

    public InstallerConfig(String host, int port, String database, String user, String password, String appPath, String branch, String cashRegister) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        setPassword(password);
        this.appPath = appPath;
        this.branch = branch;
        this.cashRegister = cashRegister;
    }

    // Getters y setters
    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }

    public String getDatabase() { return database; }
    public void setDatabase(String database) { this.database = database; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getPassword() { return CryptoHelper.decrypt(encryptedPassword); }
    public void setPassword(String password) { this.encryptedPassword = CryptoHelper.encrypt(password); }

    public String getEncryptedPassword(){ return encryptedPassword; }

    public String getAppPath() { return appPath; }
    public void setAppPath(String appPath) { this.appPath = appPath; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public String getCashRegister() { return cashRegister; }
    public void setCashRegister(String cashRegister) { this.cashRegister = cashRegister; }
}
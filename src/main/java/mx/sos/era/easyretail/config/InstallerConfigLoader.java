package mx.sos.era.easyretail.config;

import mx.sos.era.easyretail.util.CryptoHelper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.nio.file.*;
import java.util.Properties;

/**
 * Loader de configuración inicial del sistema.
 * - Si existe installer.properties, lo carga.
 * - Si no existe, lanza wizard y guarda archivo.
 */
public class InstallerConfigLoader {

    private static final String CONFIG_FILE = "config/installer.properties";

    public static InstallerConfig loadFromFile() throws IOException {
        Properties props = new Properties();
        Path path = Paths.get(CONFIG_FILE);

        if (!Files.exists(path)) {
            throw new IllegalStateException("No existe el archivo installer.properties. Ejecute InstallerWizard primero.");
        }

        // 🚀 Si ya existe, cargarlo
        try (InputStream in = Files.newInputStream(path)) {
            props.load(in);
        }

        InstallerConfig cfg = new InstallerConfig();
        cfg.setHost(props.getProperty("host"));
        cfg.setPort(Integer.parseInt(props.getProperty("port")));
        cfg.setDatabase(props.getProperty("database"));
        cfg.setUser(props.getProperty("user"));
        cfg.setPassword(CryptoHelper.decrypt(props.getProperty("password"))); // 🔐 desencripta al asignar
        cfg.setAppPath(props.getProperty("appPath"));
        cfg.setBranch(props.getProperty("branch"));
        cfg.setCashRegister(props.getProperty("cashRegister"));
        return cfg;
    }

    public static void saveToFile(InstallerConfig cfg)throws IOException{
        Properties props = new Properties();
        // Guardar en archivo
        props.setProperty("host", cfg.getHost());
        props.setProperty("port", String.valueOf(cfg.getPort()));
        props.setProperty("database", cfg.getDatabase().toLowerCase());
        props.setProperty("user", cfg.getUser());
        props.setProperty("password", cfg.getEncryptedPassword()); // 🔐 se guarda encriptada
        props.setProperty("appPath", cfg.getAppPath());
        props.setProperty("branch", cfg.getBranch());
        props.setProperty("cashRegister", cfg.getCashRegister());

        Path path = Paths.get(CONFIG_FILE);
        Files.createDirectories(path.getParent());
        try (OutputStream out = Files.newOutputStream(path)) {
            props.store(out, "Installer configuration");
        }
    }
}
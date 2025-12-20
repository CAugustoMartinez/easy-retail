package mx.sos.era.easyretail.util;

import com.zaxxer.hikari.HikariDataSource;
import mx.sos.era.easyretail.context.model.TenantInfo;

import javax.sql.DataSource;

public class MysqlDataSourceFactory {
    public static DataSource create(TenantInfo info) {
        // Validaciones básicas
        if (info == null) {
            throw new IllegalArgumentException("TenantInfo no puede ser null");
        }
        if (info.getHost() == null || info.getDatabase() == null || info.getUser() == null) {
            throw new IllegalArgumentException("TenantInfo incompleto: " + info);
        }

        // Construcción del JDBC URL
        String jdbcUrl = "jdbc:mysql://" + info.getHost() + ":" + info.getPort() + "/" + info.getDatabase()
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC\n";

        System.out.println("[MysqlDataSourceFactory] Creando DataSource para tenant=" + info.getTenantId());
        System.out.println(" → URL: " + jdbcUrl);
        System.out.println(" → Usuario: " + info.getUser());

        try {
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl(jdbcUrl);
            ds.setUsername(info.getUser());
            ds.setPassword(info.getPassword());
            ds.setMaximumPoolSize(50);
            ds.setPoolName("tenant-" + info.getTenantId());
            return ds;
        } catch (Exception e) {
            System.err.println("[MysqlDataSourceFactory] ERROR creando DataSource para tenant=" + info.getTenantId());
            e.printStackTrace();
            throw new RuntimeException("No se pudo crear DataSource para tenant=" + info.getTenantId(), e);
        }
    }


}
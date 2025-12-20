package mx.sos.era.easyretail.context;

import mx.sos.era.easyretail.context.model.TenantInfo;
import mx.sos.era.easyretail.util.MysqlDataSourceFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component()
public class DataSourceManager {
    private final Map<String, DataSource> cache = new ConcurrentHashMap<>();

    public DataSource getOrCreate(TenantInfo info){
        return cache.computeIfAbsent(info.getTenantId(), id -> MysqlDataSourceFactory.create(info));
    }

}
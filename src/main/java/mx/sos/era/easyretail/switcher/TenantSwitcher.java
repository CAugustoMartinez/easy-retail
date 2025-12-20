package mx.sos.era.easyretail.switcher;

import mx.sos.era.easyretail.context.DataSourceManager;
import mx.sos.era.easyretail.context.TenantContext;
import mx.sos.era.easyretail.context.TenantKeys;
import mx.sos.era.easyretail.context.TenantRoutingDataSource;
import mx.sos.era.easyretail.context.model.TenantInfo;
import mx.sos.era.easyretail.master.service.EmpresaCatalogoService;

import javax.sql.DataSource;

public class TenantSwitcher {
    private final EmpresaCatalogoService catalogo;
    private final DataSourceManager manager;
    private final TenantRoutingDataSource routing;

     public TenantSwitcher(EmpresaCatalogoService catalogo, DataSourceManager manager, DataSource routing){
        this.catalogo = catalogo;
        this.manager = manager;
        this.routing = (TenantRoutingDataSource) routing;
     }

     public void useMaster(){
         TenantContext.setCurrentTenant(TenantKeys.MASTER);
     }

     public void switchTo(String empresaId){
         TenantInfo info = catalogo.getInfo(empresaId);
         DataSource ds = manager.getOrCreate(info);
         TenantContext.setCurrentTenant(info.getTenantId());
     }
}
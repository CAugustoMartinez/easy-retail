package mx.sos.era.easyretail.master.entity;

import jakarta.persistence.*;
import mx.sos.era.easyretail.common.audit.Auditable;
import java.time.LocalDateTime;

@Entity
@Table(name = "empresas")
public class Empresa extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empresa_id")
    private Integer empresaId;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "razon_social", length = 150)
    private String razonSocial;

    @Column(name = "rfc", length = 20)
    private String rfc;

    @Column(name = "regimen_fiscal", length = 50)
    private String regimenFiscal;

    @Column(name = "host", length = 100, nullable = false)
    private String host;

    @Column(name = "port", nullable = false)
    private Integer port;

    @Column(name = "database_name", length = 100, nullable = false)
    private String databaseName;

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "email_contacto", length = 100)
    private String emailContacto;

    @Column(name = "telefono_contacto", length = 20)
    private String telefonoContacto;

    @Column(name = "pagina_web", length = 150)
    private String paginaWeb;

    @Column(name = "logo_path", length = 200)
    private String logoPath;

    @Column(name = "certificado_path", length = 200)
    private String certificadoPath;

    @Column(name = "sello_digital_path", length = 200)
    private String selloDigitalPath;

    @Lob
    @Column(name = "parametros_json")
    private String parametrosJson;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "creado_por", length = 50)
    private String creadoPor;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    // --- Getters y Setters ---
    public Integer getEmpresaId() { return empresaId; }
    public void setEmpresaId(Integer empresaId) { this.empresaId = empresaId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }

    public String getRegimenFiscal() { return regimenFiscal; }
    public void setRegimenFiscal(String regimenFiscal) { this.regimenFiscal = regimenFiscal; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public Integer getPort() { return port; }
    public void setPort(Integer port) { this.port = port; }

    public String getDatabaseName() { return databaseName; }
    public void setDatabaseName(String databaseName) { this.databaseName = databaseName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmailContacto() { return emailContacto; }
    public void setEmailContacto(String emailContacto) { this.emailContacto = emailContacto; }

    public String getTelefonoContacto() { return telefonoContacto; }
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }

    public String getPaginaWeb() { return paginaWeb; }
    public void setPaginaWeb(String paginaWeb) { this.paginaWeb = paginaWeb; }

    public String getLogoPath() { return logoPath; }
    public void setLogoPath(String logoPath) { this.logoPath = logoPath; }

    public String getCertificadoPath() { return certificadoPath; }
    public void setCertificadoPath(String certificadoPath) { this.certificadoPath = certificadoPath; }

    public String getSelloDigitalPath() { return selloDigitalPath; }
    public void setSelloDigitalPath(String selloDigitalPath) { this.selloDigitalPath = selloDigitalPath; }

    public String getParametrosJson() { return parametrosJson; }
    public void setParametrosJson(String parametrosJson) { this.parametrosJson = parametrosJson; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public String getCreadoPor() { return creadoPor; }
    public void setCreadoPor(String creadoPor) { this.creadoPor = creadoPor; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
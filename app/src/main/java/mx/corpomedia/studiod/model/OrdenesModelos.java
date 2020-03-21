package mx.corpomedia.studiod.model;

public class OrdenesModelos {
    private String id, orden_id, usuarios_id, paciente, tipo_entrega, registro_mordida, antagonista, total, status, fecha;

    public OrdenesModelos() {
    }

    public OrdenesModelos(String id, String orden_id, String usuarios_id, String paciente, String tipo_entrega, String registro_mordida, String antagonista, String total, String status, String fecha) {
        this.id = id;
        this.orden_id = orden_id;
        this.usuarios_id = usuarios_id;
        this.paciente = paciente;
        this.tipo_entrega = tipo_entrega;
        this.registro_mordida = registro_mordida;
        this.antagonista = antagonista;
        this.total = total;
        this.status = status;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrden_id() {
        return orden_id;
    }

    public void setOrden_id(String orden_id) {
        this.orden_id = orden_id;
    }

    public String getUsuarios_id() {
        return usuarios_id;
    }

    public void setUsuarios_id(String usuarios_id) {
        this.usuarios_id = usuarios_id;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getTipo_entrega() {
        return tipo_entrega;
    }

    public void setTipo_entrega(String tipo_entrega) {
        this.tipo_entrega = tipo_entrega;
    }

    public String getRegistro_mordida() {
        return registro_mordida;
    }

    public void setRegistro_mordida(String registro_mordida) {
        this.registro_mordida = registro_mordida;
    }

    public String getAntagonista() {
        return antagonista;
    }

    public void setAntagonista(String antagonista) {
        this.antagonista = antagonista;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

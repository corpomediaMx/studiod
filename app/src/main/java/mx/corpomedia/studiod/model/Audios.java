package mx.corpomedia.studiod.model;

public class Audios {

    private String id, dientes_id, dientes, nombre, archivo, estado, fecha;

    public Audios() {
    }

    public Audios(String id, String dientes_id, String dientes, String nombre, String archivo, String estado, String fecha) {
        this.id = id;
        this.dientes_id = dientes_id;
        this.dientes = dientes;
        this.nombre = nombre;
        this.archivo = archivo;
        this.estado = estado;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDientes_id() {
        return dientes_id;
    }

    public void setDientes_id(String dientes_id) {
        this.dientes_id = dientes_id;
    }

    public String getDientes() {
        return dientes;
    }

    public void setDientes(String dientes) {
        this.dientes = dientes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

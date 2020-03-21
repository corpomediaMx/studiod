package mx.corpomedia.studiod.model;

public class tiposIndicacionesModel {

    private String id, indicaciones_id, nombre, descripcion, strimagen;

    public tiposIndicacionesModel() {
    }

    public tiposIndicacionesModel(String id, String indicaciones_id, String nombre, String descripcion, String strimagen) {
        this.id = id;
        this.indicaciones_id = indicaciones_id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.strimagen = strimagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndicaciones_id() {
        return indicaciones_id;
    }

    public void setIndicaciones_id(String indicaciones_id) {
        this.indicaciones_id = indicaciones_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getStrimagen() {
        return strimagen;
    }

    public void setStrimagen(String strimagen) {
        this.strimagen = strimagen;
    }
}

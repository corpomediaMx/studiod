package mx.corpomedia.studiod.model;

public class IndicacionesModel {

    private String id, nombre, descripcion, strimagen;

    public IndicacionesModel() {
    }

    public IndicacionesModel(String id, String nombre, String descripcion, String strimagen) {
        this.id = id;
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

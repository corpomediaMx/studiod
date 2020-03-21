package mx.corpomedia.studiod.model;

public class DisenosModel {

    private  String id, materiales_id, nombre;

    public DisenosModel() {
    }

    public DisenosModel(String id, String materiales_id, String nombre) {
        this.id = id;
        this.materiales_id = materiales_id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMateriales_id() {
        return materiales_id;
    }

    public void setMateriales_id(String materiales_id) {
        this.materiales_id = materiales_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

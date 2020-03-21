package mx.corpomedia.studiod.model;

public class MaterialesModel {

    private  String id, tipos_indicaciones_id, nombre;
    private Double precio;

    public MaterialesModel() {
    }

    public MaterialesModel(String id, String tipos_indicaciones_id, String nombre, Double precio) {
        this.id = id;
        this.tipos_indicaciones_id = tipos_indicaciones_id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipos_indicaciones_id() {
        return tipos_indicaciones_id;
    }

    public void setTipos_indicaciones_id(String tipos_indicaciones_id) {
        this.tipos_indicaciones_id = tipos_indicaciones_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}

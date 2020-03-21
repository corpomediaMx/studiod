package mx.corpomedia.studiod.model;

public class Dientes {

    private String id, orden_id, tipos_indicaciones_id, dientes, materiales_id, material_nombre, disenos_id, diseno_nombre, color, prueba, observaciones, user_id, precio;

    public Dientes() {
    }

    public Dientes(String id, String orden_id, String tipos_indicaciones_id, String dientes, String materiales_id, String material_nombre, String disenos_id, String diseno_nombre, String color, String prueba, String observaciones, String user_id, String precio) {
        this.id = id;
        this.orden_id = orden_id;
        this.tipos_indicaciones_id = tipos_indicaciones_id;
        this.dientes = dientes;
        this.materiales_id = materiales_id;
        this.material_nombre = material_nombre;
        this.disenos_id = disenos_id;
        this.diseno_nombre = diseno_nombre;
        this.color = color;
        this.prueba = prueba;
        this.observaciones = observaciones;
        this.user_id = user_id;
        this.precio = precio;
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

    public String getTipos_indicaciones_id() {
        return tipos_indicaciones_id;
    }

    public void setTipos_indicaciones_id(String tipos_indicaciones_id) {
        this.tipos_indicaciones_id = tipos_indicaciones_id;
    }

    public String getDientes() {
        return dientes;
    }

    public void setDientes(String dientes) {
        this.dientes = dientes;
    }

    public String getMateriales_id() {
        return materiales_id;
    }

    public void setMateriales_id(String materiales_id) {
        this.materiales_id = materiales_id;
    }

    public String getMaterial_nombre() {
        return material_nombre;
    }

    public void setMaterial_nombre(String material_nombre) {
        this.material_nombre = material_nombre;
    }

    public String getDisenos_id() {
        return disenos_id;
    }

    public void setDisenos_id(String disenos_id) {
        this.disenos_id = disenos_id;
    }

    public String getDiseno_nombre() {
        return diseno_nombre;
    }

    public void setDiseno_nombre(String diseno_nombre) {
        this.diseno_nombre = diseno_nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrueba() {
        return prueba;
    }

    public void setPrueba(String prueba) {
        this.prueba = prueba;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}

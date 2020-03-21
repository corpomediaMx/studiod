package mx.corpomedia.studiod.model;

public class DientesModelos {

    private String diente, tipo;

    public DientesModelos() {
    }

    public DientesModelos(String diente, String tipo) {
        this.diente = diente;
        this.tipo = tipo;
    }

    public String getDiente() {
        return diente;
    }

    public void setDiente(String diente) {
        this.diente = diente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

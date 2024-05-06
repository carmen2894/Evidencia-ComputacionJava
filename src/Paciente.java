import java.io.Serializable;

class Paciente implements Serializable {
    private int id;
    private String nombre;

    public Paciente(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
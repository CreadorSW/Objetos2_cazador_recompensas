package ar.edu.unahur.obj2;

import ar.edu.unahur.obj2.profugos.IProfugo;
import java.util.HashSet;
import java.util.Set;

// EL enunciado no especifica tipos de Zonas (como para que hagan match con el tipo de cazador), se sobreentiende que un Cazador Rural va a cazar en una zona rural.
public class Zona {

    protected String nombre;
    protected Set<IProfugo> profugos = new HashSet<>();

    public Zona(String nombre) {
        this.nombre = nombre;
    }

    public Set<IProfugo> getProfugos() {
        return profugos;
    }
}

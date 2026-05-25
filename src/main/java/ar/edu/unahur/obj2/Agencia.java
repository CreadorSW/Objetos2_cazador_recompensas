package ar.edu.unahur.obj2;

import ar.edu.unahur.obj2.cazadores.Cazador;
import ar.edu.unahur.obj2.profugos.IProfugo;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Agencia {

    // Atributo estático privado: guarda la única instancia de la clase.
    // static: el campo existe sin necesidad de una instancia previa. Sin static, haría falta new Agencia() para que exista, pero el constructor privado lo impide.
    // final: la referencia no se puede reasignar. Sin final, alguien podría pisar INSTANCIA con otra instancia y romper el singleton.

    private static final Agencia INSTANCIA = new Agencia();
    private Set<Cazador> cazadores = new HashSet<>();

    // Constructor privado: evita que externos creen instancias con new
    private Agencia() {}

    // Método estático público: punto de acceso global a la única instancia.
    // static: al pertenecer a la clase y no a una instancia, puede llamarse con Agencia.getInstancia() desde cualquier parte del código.
    public static Agencia getInstancia() {
        return INSTANCIA;
    }

    public void registrarCazador(Cazador cazador) {
        cazadores.add(cazador);
    }

    public void desregistrarCazadores() {
        cazadores.clear();
    }

    public Set<IProfugo> getProfugos() {
        return cazadores
            .stream()
            .flatMap(c -> c.getProfugosCapturados().stream())
            .collect(Collectors.toSet());
    }

    public IProfugo getProfugoMasHabil() {
        return getProfugos()
            .stream()
            .max(Comparator.comparing(IProfugo::getHabilidad))
            .orElse(null);
    }

    public Cazador getCazadorConMasCapturas() {
        return cazadores
            .stream()
            .max(Comparator.comparing(Cazador::cantidadDeCapturas))
            .orElse(null);
    }
}

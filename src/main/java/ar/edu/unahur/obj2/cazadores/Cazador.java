package ar.edu.unahur.obj2.cazadores;

import ar.edu.unahur.obj2.Zona;
import ar.edu.unahur.obj2.profugos.IProfugo;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Cazador {

    protected Set<IProfugo> profugosCapturados = new HashSet<>();
    protected Integer experiencia;

    public Cazador(Integer experiencia) {
        this.experiencia = experiencia;
    }

    // El cazador va a una zona a cazar

    public void comenzarProcesoDeCaptura(Zona zona) {
        //filter(this::cazar)` devuelve un **Stream<IProfugo>** que contiene solo los prófugos para los cuales `cazar()` devolvió `true`. Después `.collect(Collectors.toSet())` toma ese stream, itera y los junta en un `Set<IProfugo>`.
        Set<IProfugo> capturados = zona
            .getProfugos()
            .stream()
            .filter(this::cazar)
            .collect(Collectors.toSet());
        // Seleccionamos los intimidados (profugos no capturados).
        Set<IProfugo> intimidados = new HashSet<>(zona.getProfugos());
        intimidados.removeAll(capturados);

        zona.getProfugos().removeAll(capturados);

        //.min()` devuelve un `OptionalInt` porque si el stream está vacío (no hay intimidados), no hay valor mínimo. `.orElse(0)` le dice "si no hay mínimo, usá 0".

        //Sin `.orElse(0)` no compila, porque `OptionalInt` no se asigna automáticamente a `Integer`. Si se quiere evitar el `orElse`, habría que hacer un `ifPresent` o chequear `isPresent()`, pero `orElse(0)` es la forma más limpia.
        Integer minimoHabilidad = intimidados
            .stream()
            .mapToInt(IProfugo::getHabilidad)
            .min()
            .orElse(0);

        this.experiencia += minimoHabilidad + 2 * capturados.size();
    }

    // Template Method
    // Obs: le paso un tipo IProfugo, así en el futuro puedo pasarle un prófugo decorado.
    public Boolean cazar(IProfugo profugo) {
        if (puedeCazar(profugo)) {
            this.profugosCapturados.add(profugo);
            return true;
        } else {
            intimidar(profugo);
            return false;
        }
    }

    private Boolean puedeCazar(IProfugo profugo) {
        return (
            this.experiencia > profugo.getInocencia() && doPuedeCazar(profugo)
        );
    }

    protected abstract Boolean doPuedeCazar(IProfugo profugo);

    // Template Method
    public void intimidar(IProfugo profugo) {
        profugo.disminuirInocencia(); // parte común
        doIntimidar(profugo); // parte específica
    }

    protected abstract void doIntimidar(IProfugo profugo);
}

package ar.edu.unahur.obj2.cazadores;

import ar.edu.unahur.obj2.profugos.IProfugo;
import java.util.ArrayList;
import java.util.List;

public abstract class Cazador {

    protected List<IProfugo> profugos = new ArrayList<>();
    protected Integer experiencia;

    public Cazador(Integer experiencia) {
        this.experiencia = experiencia;
    }

    // Template Method

    public void cazar(IProfugo profugo) {
        if (puedeCazar(profugo)) {
            this.profugos.add(profugo);
        } else {
            intimidar(profugo);
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

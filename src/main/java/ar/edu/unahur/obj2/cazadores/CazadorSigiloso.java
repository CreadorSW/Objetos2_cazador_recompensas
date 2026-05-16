package ar.edu.unahur.obj2.cazadores;

import ar.edu.unahur.obj2.profugos.IProfugo;

public class CazadorSigiloso extends Cazador {

    public CazadorSigiloso(Integer experiencia) {
        super(experiencia);
    }

    @Override
    protected Boolean doPuedeCazar(IProfugo profugo) {
        return (profugo.getHabilidad() < 50);
    }

    @Override
    protected void doIntimidar(IProfugo profugo) {
        profugo.reducirHabilidad();
    }
}

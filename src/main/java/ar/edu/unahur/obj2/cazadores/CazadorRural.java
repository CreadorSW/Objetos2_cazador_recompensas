package ar.edu.unahur.obj2.cazadores;

import ar.edu.unahur.obj2.profugos.IProfugo;

public class CazadorRural extends Cazador {

    public CazadorRural(Integer experiencia) {
        super(experiencia);
    }

    @Override
    protected Boolean doPuedeCazar(IProfugo profugo) {
        return (profugo.esNervioso());
    }

    @Override
    protected void doIntimidar(IProfugo profugo) {
        profugo.volverseNervioso();
    }
}

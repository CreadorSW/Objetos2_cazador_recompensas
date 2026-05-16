package ar.edu.unahur.obj2.cazadores;

import ar.edu.unahur.obj2.profugos.IProfugo;
import ar.edu.unahur.obj2.profugos.Profugo;

public class CazadorUrbano extends Cazador {

    public CazadorUrbano(Integer experiencia) {
        super(experiencia);
    }

    @Override
    protected void doIntimidar(IProfugo profugo) {
        this.inocencia -= 2;
        profugo.dejarDeEstarNervioso();
    }

    @Override
    protected Boolean doPuedeCazar(IProfugo profugo) {
        return (!profugo.esNervioso());
    }
}

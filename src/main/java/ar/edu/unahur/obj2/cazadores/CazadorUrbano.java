package ar.edu.unahur.obj2.cazadores;

import ar.edu.unahur.obj2.profugos.IProfugo;
import ar.edu.unahur.obj2.profugos.Profugo;

public class CazadorUrbano extends Cazador {

    public CazadorUrbano(Integer experiencia) {
        super(experiencia);
    }

    @Override
    protected void doIntimidar(IProfugo profugo) {
        profugo.disminuirInocencia();
        profugo.dejarDeEstarNervioso();
    }

    @Override
    protected Boolean doPuedeCazar(IProfugo profugo) {
        return (!profugo.esNervioso());
    }
}

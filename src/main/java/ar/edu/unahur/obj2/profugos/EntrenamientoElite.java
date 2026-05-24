package ar.edu.unahur.obj2.profugos;

public class EntrenamientoElite extends ProfugoDecorator {

    public EntrenamientoElite(IProfugo wrappee) {
        super(wrappee);
    }

    @Override
    public void volverseNervioso() {}
}

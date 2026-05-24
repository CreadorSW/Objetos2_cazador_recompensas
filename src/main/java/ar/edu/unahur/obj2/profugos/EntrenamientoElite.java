package ar.edu.unahur.obj2.profugos;

public class EntrenamientoElite extends ProfugoDecorator {

    public EntrenamientoElite(IProfugo wrappee) {
        super(wrappee);
    }

    // si el profugo nace nervioso, el entrenamiento de Elite lo cura

    public Boolean esNervioso() {
        return false;
    }

    @Override
    public void volverseNervioso() {}
}

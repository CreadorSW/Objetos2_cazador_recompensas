package ar.edu.unahur.obj2.profugos;

public class ProteccionLegal extends ProfugoDecorator {

    public ProteccionLegal(IProfugo wrappee) {
        super(wrappee);
    }

    @Override
    public Integer getInocencia() {
        return Math.max(wrappee.getInocencia(), 40);
    }

    // Esto queda raro para el caso en
    @Override
    public void disminuirInocencia() {
        if (wrappee.getInocencia() > 40) {
            wrappee.disminuirInocencia();
        }
    }
}

package ar.edu.unahur.obj2.profugos;

public class ArtesMarciales extends ProfugoDecorator {

    public ArtesMarciales(IProfugo wrappee) {
        super(wrappee);
    }

    // no necesito sobreescribir otros métodos, ya que los heredo de ProfugoDecorator.
    @Override
    public Integer getHabilidad() {
        return Math.min(wrappee.getHabilidad() * 2, 100);
    }
}

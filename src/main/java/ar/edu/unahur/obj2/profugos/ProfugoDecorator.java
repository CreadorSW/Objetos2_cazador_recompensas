package ar.edu.unahur.obj2.profugos;

public abstract class ProfugoDecorator implements IProfugo {

    protected IProfugo wrappee;

    public ProfugoDecorator(IProfugo wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public Integer getInocencia() {
        return wrappee.getInocencia();
    }

    @Override
    public Integer getHabilidad() {
        return wrappee.getHabilidad();
    }

    @Override
    public Boolean esNervioso() {
        return wrappee.esNervioso();
    }

    @Override
    public void volverseNervioso() {
        wrappee.volverseNervioso();
    }

    @Override
    public void dejarDeEstarNervioso() {
        wrappee.dejarDeEstarNervioso();
    }

    @Override
    public void reducirHabilidad() {
        wrappee.reducirHabilidad();
    }

    @Override
    public void disminuirInocencia() {
        wrappee.disminuirInocencia();
    }
}

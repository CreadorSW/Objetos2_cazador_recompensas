package ar.edu.unahur.obj2.profugos;

public class Profugo implements IProfugo {

    // Obviamente tiene que hacer un override a todos los métodos de IProfugo.
    protected Integer nivelDeInocencia;
    protected Integer nivelDeHabilidad;
    protected Boolean esNervioso;

    public Profugo(
        Integer nivelDeInocencia,
        Integer nivelDeHabilidad,
        Boolean esNervioso
    ) {
        this.nivelDeInocencia = nivelDeInocencia;
        this.nivelDeHabilidad = nivelDeHabilidad;
        this.esNervioso = esNervioso;
    }

    @Override
    public Integer getInocencia() {
        return this.nivelDeInocencia;
    }

    @Override
    public Integer getHabilidad() {
        return this.nivelDeHabilidad;
    }

    @Override
    public Boolean esNervioso() {
        return esNervioso;
    }

    @Override
    public void volverseNervioso() {
        this.esNervioso = true;
    }

    @Override
    public void dejarDeEstarNervioso() {
        this.esNervioso = false;
    }

    @Override
    public void reducirHabilidad() {
        this.nivelDeHabilidad = Math.max(0, this.nivelDeHabilidad - 5);
    }

    @Override
    public void disminuirInocencia() {
        this.nivelDeHabilidad = Math.max(0, this.nivelDeInocencia - 2);
    }
}

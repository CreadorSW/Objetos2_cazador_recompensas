package ar.edu.unahur.obj2.profugos;

public class Profugo implements IProfugo {

    protected Integer nivelDeHabilidad;
    protected Boolean esNervioso;

    public Profugo(Integer nivelDeHabilidad, Boolean esNervioso) {
        this.nivelDeHabilidad = nivelDeHabilidad;
        this.esNervioso = esNervioso;
    }

    @Override
    public Integer getHabilidad() {
        return nivelDeHabilidad;
    }

    @Override
    public Boolean esNervioso() {
        return esNervioso;
    }
}

package ar.edu.unahur.obj2.profugos;

public abstract class ProfugoDecorator implements IProfugo {

    // Algo esencial para entender decorator: cuando una clase implementa una interfaz, establece una relación de tipo "es un" con la interfaz. ProfugoDecorator "es un IProfugo", y también lo es Profugo. Entonces el decorador base y el componente concreto, ambos son IProfugo. Esto permite que  el decorador pueda envolver a cualquier IProfugo, o dicho de otra manera, a cualquier clase que implemente IProfugo (incluso otro decorador). Al apilar siempre tengo un IProfugo, y eso me permite decorar cualquier IProfugo con cualquier cantidad de decoradores.
    protected IProfugo wrappee;

    // El wrappee es el objeto que se está decorando
    public ProfugoDecorator(IProfugo wrappee) {
        this.wrappee = wrappee;
    }

    // Debe sobreescribir TODOS los métodos de la interface ya que luego delega cada uno al wrappee. Notar en cada override  como se le pasa el mensaje al wrappee.
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

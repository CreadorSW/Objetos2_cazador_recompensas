package ar.edu.unahur.obj2;

import ar.edu.unahur.obj2.profugos.*;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    protected Profugo profugoInocente;
    protected Profugo profugoCulpableNervioso;
    protected Profugo profugoNervioso;
    protected Profugo profugoHabil;
    protected IProfugo profugoConProteccionLegal;
    protected IProfugo profugoConArtesMarciales;
    protected IProfugo profugoConEntrenamientoElite;
    protected IProfugo profugoFullDecorado;

    @BeforeEach
    void setUp() {
        profugoInocente = new Profugo(80, 70, false);
        profugoCulpableNervioso = new Profugo(20, 30, true);
        profugoNervioso = new Profugo(60, 40, true);
        profugoHabil = new Profugo(30, 80, false);

        profugoConProteccionLegal = new ProteccionLegal(
            new Profugo(20, 50, false)
        );
        profugoConArtesMarciales = new ArtesMarciales(
            new Profugo(50, 30, false)
        );
        profugoConEntrenamientoElite = new EntrenamientoElite(
            new Profugo(50, 50, true)
        );
        profugoFullDecorado = new ProteccionLegal(
            new ArtesMarciales(
                new EntrenamientoElite(new Profugo(30, 20, true))
            )
        );
    }
}

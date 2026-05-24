package ar.edu.unahur.obj2.cazadores;

import ar.edu.unahur.obj2.BaseTest;
import ar.edu.unahur.obj2.Zona;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;

public abstract class CazadorBaseTest extends BaseTest {

    protected CazadorUrbano urbano;
    protected CazadorRural rural;
    protected CazadorSigiloso sigiloso;
    protected Zona zonaMixta;
    protected Zona zonaSoloInocentes;
    protected Zona zonaSoloNerviosos;

    @BeforeEach
    void setUpCazadores() {
        urbano = new CazadorUrbano(50);
        rural = new CazadorRural(30);
        sigiloso = new CazadorSigiloso(70);

        zonaMixta = new Zona("mixta");
        zonaMixta
            .getProfugos()
            .addAll(
                List.of(
                    profugoInocente,
                    profugoCulpableNervioso,
                    profugoNervioso,
                    profugoHabil
                )
            );

        zonaSoloInocentes = new Zona("solo inocentes");
        zonaSoloInocentes
            .getProfugos()
            .addAll(List.of(profugoInocente, profugoHabil));

        zonaSoloNerviosos = new Zona("solo nerviosos");
        zonaSoloNerviosos
            .getProfugos()
            .addAll(List.of(profugoCulpableNervioso, profugoNervioso));
    }
}

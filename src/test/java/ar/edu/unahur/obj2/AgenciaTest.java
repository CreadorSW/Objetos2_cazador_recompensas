package ar.edu.unahur.obj2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.unahur.obj2.cazadores.CazadorBaseTest;
import ar.edu.unahur.obj2.profugos.IProfugo;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class AgenciaTest extends CazadorBaseTest {

    @Test
    void getProfugos_retornaprofugosCapturadosLosCapturados() {
        Agencia.getInstancia().desregistrarCazadores();
        urbano.comenzarProcesoDeCaptura(zonaSoloInocentes);
        sigiloso.comenzarProcesoDeCaptura(zonaSoloNerviosos);

        Agencia.getInstancia().registrarCazador(urbano);
        Agencia.getInstancia().registrarCazador(sigiloso);

        Set<IProfugo> profugosCapturados = Agencia.getInstancia().getProfugos();

        assertEquals(3, profugosCapturados.size());
        assertTrue(profugosCapturados.contains(profugoHabil));
        assertTrue(profugosCapturados.contains(profugoCulpableNervioso));
        assertTrue(profugosCapturados.contains(profugoNervioso));
    }

    @Test
    void getProfugoMasHabil_retornaElDeMayorHabilidad() {
        Agencia.getInstancia().desregistrarCazadores();
        sigiloso.comenzarProcesoDeCaptura(zonaSoloNerviosos);

        Agencia.getInstancia().registrarCazador(sigiloso);

        assertEquals(
            profugoNervioso,
            Agencia.getInstancia().getProfugoMasHabil()
        );
    }

    @Test
    void getCazadorConMasCapturas_retornaElQueMasCapturo() {
        Agencia.getInstancia().desregistrarCazadores();
        sigiloso.comenzarProcesoDeCaptura(zonaSoloNerviosos);
        urbano.comenzarProcesoDeCaptura(zonaSoloInocentes);

        Agencia.getInstancia().registrarCazador(sigiloso);
        Agencia.getInstancia().registrarCazador(urbano);

        assertEquals(
            sigiloso,
            Agencia.getInstancia().getCazadorConMasCapturas()
        );
    }
}

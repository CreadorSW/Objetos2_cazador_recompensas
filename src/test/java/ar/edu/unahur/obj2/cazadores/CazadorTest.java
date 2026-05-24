package ar.edu.unahur.obj2.cazadores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CazadorTest extends CazadorBaseTest {

    @Test
    void zonaMixta_tieneLosProfugosEsperados() {
        assertEquals(4, zonaMixta.getProfugos().size());
        assertTrue(zonaMixta.getProfugos().contains(profugoInocente));
        assertTrue(zonaMixta.getProfugos().contains(profugoCulpableNervioso));
        assertTrue(zonaMixta.getProfugos().contains(profugoNervioso));
        assertTrue(zonaMixta.getProfugos().contains(profugoHabil));
    }

    @Test
    void comenzarProcesoDeCaptura_sumaExperienciaCorrecta() {
        sigiloso.comenzarProcesoDeCaptura(zonaMixta);
        assertEquals(139, sigiloso.experiencia);
    }

    @Test
    void comenzarProcesoDeCaptura_intimidaCorrectamente() {
        sigiloso.comenzarProcesoDeCaptura(zonaMixta);
        // intimidados
        assertEquals(78, profugoInocente.getInocencia());
        assertEquals(65, profugoInocente.getHabilidad());
        assertEquals(28, profugoHabil.getInocencia());
        assertEquals(75, profugoHabil.getHabilidad());
        // capturados (sin cambios en atributos)
        assertEquals(20, profugoCulpableNervioso.getInocencia());
        assertEquals(60, profugoNervioso.getInocencia());
    }

    @Test
    void comenzarProcesoDeCaptura_remueveCapturadosDeZona() {
        sigiloso.comenzarProcesoDeCaptura(zonaMixta);
        assertEquals(2, zonaMixta.getProfugos().size());
        assertTrue(zonaMixta.getProfugos().contains(profugoInocente));
        assertTrue(zonaMixta.getProfugos().contains(profugoHabil));
        assertFalse(zonaMixta.getProfugos().contains(profugoCulpableNervioso));
        assertFalse(zonaMixta.getProfugos().contains(profugoNervioso));
    }
}

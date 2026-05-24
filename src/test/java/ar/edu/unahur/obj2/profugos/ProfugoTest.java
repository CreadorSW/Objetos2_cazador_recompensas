package ar.edu.unahur.obj2.profugos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.unahur.obj2.BaseTest;
import org.junit.jupiter.api.Test;

public class ProfugoTest extends BaseTest {

    @Test
    void dadoProfugoBase_losMetodosDevuelvenLosValoresEsperados() {
        assertEquals(80, profugoInocente.getInocencia());
        profugoInocente.disminuirInocencia();
        assertEquals(78, profugoInocente.getInocencia());

        assertEquals(70, profugoInocente.getHabilidad());
        profugoInocente.reducirHabilidad();
        assertEquals(65, profugoInocente.getHabilidad());

        assertFalse(profugoInocente.esNervioso());
        profugoInocente.dejarDeEstarNervioso();
        assertFalse(profugoInocente.esNervioso());

        profugoInocente.volverseNervioso();
        assertTrue(profugoInocente.esNervioso());
        profugoInocente.dejarDeEstarNervioso();
        assertFalse(profugoInocente.esNervioso());
    }

    @Test
    void dadoProfugoFullDecorado_seComportaComoEsperado() {
        // ProteccionLegal maquilla 30→40
        assertEquals(40, profugoFullDecorado.getInocencia());

        // ArtesMarciales duplica 20→40
        assertEquals(40, profugoFullDecorado.getHabilidad());

        // EntrenamientoElite pisa nerviosismo (wrappee es true)
        assertFalse(profugoFullDecorado.esNervioso());
        profugoFullDecorado.volverseNervioso();
        assertFalse(profugoFullDecorado.esNervioso());

        // ProteccionLegal no baja de 40
        profugoFullDecorado.disminuirInocencia();
        assertEquals(40, profugoFullDecorado.getInocencia());
    }
}

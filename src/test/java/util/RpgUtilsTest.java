package util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RpgUtilsTest {
    
    public RpgUtilsTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void testCalcularModificador() {
        System.out.println("calcularModificador");
        assertEquals(0, RpgUtils.calcularModificador(10), "Atributo 10 deve dar mod 0");
        assertEquals(1, RpgUtils.calcularModificador(12), "Atributo 12 deve dar mod 1");
        assertEquals(-1, RpgUtils.calcularModificador(8), "Atributo 8 deve dar mod -1");
        assertEquals(5, RpgUtils.calcularModificador(20), "Atributo 20 deve dar mod 5");
        assertEquals(-5, RpgUtils.calcularModificador(1), "Atributo 1 deve dar mod -5");
    }
}

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class ProcessaBoletoTest {

    @Test
    public void testProcessaBoletoValorIgual() {
        ProcessaBoleto processar = new ProcessaBoleto();

        Fatura fatura = new Fatura("2023-08-15", "Cliente A", 1500.00);
        List<Boleto> boletos = Arrays.asList(
                new Boleto("001", "2023-08-01", 500.00),
                new Boleto("002", "2023-08-02", 400.00),
                new Boleto("003", "2023-08-03", 600.00)
        );

        processar.processarBoleto(fatura, boletos);

        assertTrue(fatura.isPaga());
    }
}
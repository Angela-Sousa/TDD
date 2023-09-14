import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProcessaBoletoFunctionalTest {
    @Test
    public void testProcessaBoletoComValorNegativo() {
        ProcessaBoleto processar = new ProcessaBoleto();
        Fatura fatura = new Fatura(LocalDate.of(2023, 8, 15), "Cliente K", 100.00);
        List<Boleto> boletos = Arrays.asList(
                new Boleto("001", LocalDate.of(2023, 8, 1), -50.00)
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            processar.processarBoleto(fatura, boletos);
        });

        assertEquals("O valor do boleto deve ser positivo", exception.getMessage());

    }
}

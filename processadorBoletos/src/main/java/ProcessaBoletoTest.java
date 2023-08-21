import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessaBoletoTest {

    @Test
    public void testProcessaBoletoFaturaPagaValorIgual() {
        ProcessaBoleto processar = new ProcessaBoleto();

        Fatura fatura = new Fatura(LocalDate.of(2023, 8, 15), "Cliente A", 1500.00);
        List<Boleto> boletos = Arrays.asList(
                new Boleto("001", LocalDate.of(2023, 8, 1), 500.00),
                new Boleto("002", LocalDate.of(2023, 8, 2), 400.00),
                new Boleto("003", LocalDate.of(2023, 8, 3), 600.00)
        );

        processar.processarBoleto(fatura, boletos);

        assertTrue(fatura.isPago());
        assertEquals(3, fatura.getPagamentos().size());
    }

    @Test
    public void testProcessaBoletoFaturaNaoPagaValorMenor() {
        ProcessaBoleto processar = new ProcessaBoleto();

        Fatura fatura = new Fatura(LocalDate.of(2023, 8, 15), "Cliente B", 1500.00);
        List<Boleto> boletos = Arrays.asList(
                new Boleto("001", LocalDate.of(2023, 8, 10), 400.00),
                new Boleto("002", LocalDate.of(2023, 8, 11), 400.00),
                new Boleto("003", LocalDate.of(2023, 8, 12), 600.00)
        );

        processar.processarBoleto(fatura, boletos);

        assertFalse(fatura.isPago());
        assertEquals(3, fatura.getPagamentos().size());
    }

    @Test
    public void testProcessaBoletoFaturaPagaValorMaior() {
        ProcessaBoleto processar = new ProcessaBoleto();

        Fatura fatura = new Fatura(LocalDate.of(2023, 8, 15), "Cliente C", 1500.00);
        List<Boleto> boletos = Arrays.asList(
                new Boleto("001", LocalDate.of(2023, 8, 10), 1800.00)
        );

        processar.processarBoleto(fatura, boletos);

        assertTrue(fatura.isPago());
        assertEquals(1, fatura.getPagamentos().size());
    }

    @Test
    public void testProcessaBoletoFaturaVazia() {
        ProcessaBoleto processar = new ProcessaBoleto();

        Fatura fatura = new Fatura(LocalDate.of(2023, 8, 15), "Cliente D", 1500.00);
        List<Boleto> boletos = new ArrayList<>();

        processar.processarBoleto(fatura, boletos);

        assertFalse(fatura.isPago());
        assertTrue(fatura.getPagamentos().isEmpty());
    }

    @Test
    public void testProcessaBoletoFaturaNaoPagaValorNegativo() {
        ProcessaBoleto processar = new ProcessaBoleto();

        Fatura fatura = new Fatura(LocalDate.of(2023, 8, 15), "Cliente G", -500.00);
        List<Boleto> boletos = Arrays.asList(
                new Boleto("001", LocalDate.of(2023, 8, 10), 300.00),
                new Boleto("002", LocalDate.of(2023, 8, 11), 300.00),
                new Boleto("003", LocalDate.of(2023, 8, 12), 300.00)
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            processar.processarBoleto(fatura, boletos);
        });

        assertEquals("O valor da fatura deve ser positivo", exception.getMessage());
    }
}
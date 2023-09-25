import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@DisplayName("Testes para ProcessaBoleto")
class ProcessaBoletoTestJunit {

    private ProcessaBoleto processar;

    @BeforeEach
    void setUp() {
        processar = new ProcessaBoleto();
    }

    @Test
    @DisplayName("Fatura paga com valor igual")
    void testProcessaBoletoFaturaPagaValorIgual() {

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
    @DisplayName("Fatura paga com valor menor")
    void testProcessaBoletoFaturaNaoPagaValorMenor() {

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
    @DisplayName("Fatura paga com valor maior")
    void testProcessaBoletoFaturaPagaValorMaior() {

        Fatura fatura = new Fatura(LocalDate.of(2023, 8, 15), "Cliente C", 1500.00);
        List<Boleto> boletos = Arrays.asList(
                new Boleto("001", LocalDate.of(2023, 8, 10), 1800.00)
        );

        processar.processarBoleto(fatura, boletos);

        assertTrue(fatura.isPago());
        assertEquals(1, fatura.getPagamentos().size());
    }

    @Test
    @DisplayName("Fatura vazia")
    void testProcessaBoletoFaturaVazia() {

        Fatura fatura = new Fatura(LocalDate.of(2023, 8, 15), "Cliente D", 1500.00);
        List<Boleto> boletos = new ArrayList<>();

        processar.processarBoleto(fatura, boletos);

        assertFalse(fatura.isPago());
        assertTrue(fatura.getPagamentos().isEmpty());
    }

    @Test
    @DisplayName("Fatura não paga com valor negativo")
    void testProcessaBoletoFaturaNaoPagaValorNegativo() {

        Fatura fatura = new Fatura(LocalDate.of(2023, 8, 15), "Cliente G", -500.00);
        List<Boleto> boletos = Arrays.asList(
                new Boleto("001", LocalDate.of(2023, 8, 10), 300.00),
                new Boleto("002", LocalDate.of(2023, 8, 11), 300.00),
                new Boleto("003", LocalDate.of(2023, 8, 12), 300.00)
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            processar.processarBoleto(fatura, boletos);
        });

        assertAll("Validando exceção e mensagem",
                () -> assertEquals("O valor da fatura deve ser positivo", exception.getMessage()),
                () -> assertFalse(fatura.isPago())
        );
    }

    @Test
    @DisplayName("Processamento de boleto com valor negativo")
    public void testProcessaBoletoComValorNegativo() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 8, 15), "Cliente K", 100.00);
        List<Boleto> boletos = Arrays.asList(
                new Boleto("001", LocalDate.of(2023, 8, 1), -50.00)
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            processar.processarBoleto(fatura, boletos);
        });

        assertAll("Verificações",
                () -> assertEquals("O valor do boleto deve ser positivo", exception.getMessage()),
                () -> assertFalse(fatura.isPago(), "A fatura não deve estar paga")
        );
    }
}
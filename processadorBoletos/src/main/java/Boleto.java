import java.time.LocalDate;

public class Boleto {
    private String codigo;
    private LocalDate data;
    private double valorPago;
    private static final String TIPO_BOLETO = "BOLETO";

    public Boleto(String code, LocalDate data, double valorPago) {
        this.codigo = code;
        this.data = data;
        this.valorPago = valorPago;
    }

    public double getValorPago() {
        return valorPago;
    }

    public String getCode() {
        return codigo;
    }

    public LocalDate getDate() {
        return data;
    }

    public String getTipo() {
        return TIPO_BOLETO;
    }
}

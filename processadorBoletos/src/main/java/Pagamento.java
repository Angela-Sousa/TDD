import java.time.LocalDate;

public class Pagamento {
    private String tipo;
    private double valor;
    private LocalDate data;

    public Pagamento(String tipo, double valor, LocalDate data) {
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
    }
}

import java.util.ArrayList;
import java.util.List;

public class Fatura {
    private String date;
    private String nomeCliente;
    private double somaTotal;
    private boolean pagamento;
    private List<Pagamento> pagamentos;

    public Fatura(String date, String nomeCliente, double somaTotal) {
        this.date = date;
        this.nomeCliente = nomeCliente;
        this.somaTotal = somaTotal;
        this.pagamentos = new ArrayList<>();
    }
    public boolean isPago() {
        return pagamento;
    }
    public void marcarComoPago() {
        this.pagamento = true;
    }

    public double getTotal() {
        return this.somaTotal;
    }

    public void addPagamento(Pagamento pagamento) {
        pagamentos.add(pagamento);
    }
}

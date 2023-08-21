import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Fatura {
    private String date;
    private String nomeCliente;
    private double somaTotal;
    private boolean pago;
    private List<Pagamento> pagamentos;

    public Fatura(String date, String nomeCliente, double somaTotal) {
        this.date = date;
        this.nomeCliente = nomeCliente;
        this.somaTotal = somaTotal;
        this.pago = false;
        this.pagamentos = new ArrayList<>();
    }
    public boolean isPago() {
        return pago;
    }
    public void marcarComoPago() {
        this.pago = true;
    }

    public double getTotal() {
        return this.somaTotal;
    }

    public void addPagamento(Pagamento pagamento) {
        pagamentos.add(pagamento);
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }
}

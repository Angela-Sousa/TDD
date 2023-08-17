import java.util.Arrays;
import java.util.List;
public class ProcessaBoleto {
    public void processarBoleto(Fatura fatura, List<Boleto> boletos) {
        if (fatura.getTotal() < 0) {
            throw new IllegalArgumentException("O valor da fatura deve ser positivo");
        }

        double totalBoletos = boletos.stream().mapToDouble(Boleto::getValorPago).sum();

        if (totalBoletos >= fatura.getTotal()) {
            fatura.marcarComoPago();
        }

        for (Boleto boleto : boletos) {
            Pagamento pagamento = new Pagamento(boleto.getTipo(), boleto.getValorPago(), boleto.getDate());
            fatura.addPagamento(pagamento);
        }
    }
}

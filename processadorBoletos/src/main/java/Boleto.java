public class Boleto {
    private String code;
    private String date;
    private double valorPago;
    private static final String TIPO_BOLETO = "BOLETO";

    public Boleto(String code, String date, double valorPago) {
        this.code = code;
        this.date = date;
        this.valorPago = valorPago;
    }

    public double getValorPago() {
        return valorPago;
    }

    public String getCode() {
        return code;
    }

    public String getDate() {
        return date;
    }

    public String getTipo() {
        return TIPO_BOLETO;
    }
}
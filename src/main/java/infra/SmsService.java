package infra;

public class SmsService {

    public void enviarSms(String telefone, String mensagem) {
        System.out.println("Enviando SMS para " + telefone + ": " + mensagem);
    }
}

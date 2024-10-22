package br.com.valueprojects.mock_spring.model;

import infra.JogoDao;
import infra.SmsService;
import java.sql.SQLException;

public class EnviarSMS {

    private Juiz juiz;
    private JogoDao jogoDao;
    private SmsService smsService;

    // Injetando dependências via construtor
    public EnviarSMS(Juiz juiz, JogoDao jogoDao, SmsService smsService) {
        this.juiz = juiz;
        this.jogoDao = jogoDao;
        this.smsService = smsService;
    }

    // Método para validar o primeiro colocado
    public void validaPrimeiroColocado(Jogo jogo, double vencedorJogo) {
        // Avalia o resultado do jogo com o juiz
        juiz.julga(jogo);

        // Compara se o resultado é o mesmo do vencedor
        if (vencedorJogo == juiz.getPrimeiroColocado()) {
            Participante vencedor = juiz.getPrimeiroColocadoParticipante();

            try {
                // Salva o vencedor no banco de dados
                jogoDao.salvaPrimeiroColocado(vencedor);

                // Envia o SMS para o vencedor
                String mensagem = "Parabéns, " + vencedor.getNome() + "! Você é o vencedor do jogo!";
                String telefoneVencedor = vencedor.getTelefone();
                smsService.enviarSms(telefoneVencedor, mensagem);
            } catch (SQLException e) {
                System.err.println("Erro ao salvar o vencedor no banco de dados: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}

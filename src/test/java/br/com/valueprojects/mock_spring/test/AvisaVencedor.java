package br.com.valueprojects.mock_spring.test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.valueprojects.mock_spring.model.*;
import infra.JogoDao;
import infra.SmsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.sql.SQLException;
import java.util.Arrays;

class EnviarSMSTest {

    private Juiz juizMock;
    private JogoDao jogoDaoMock;
    private SmsService smsServiceMock;
    private EnviarSMS enviarSMS;
    private Jogo jogoMock;

    @BeforeEach
    void setUp() {
        juizMock = mock(Juiz.class); // Mock para Juiz
        jogoDaoMock = mock(JogoDao.class); // Mock para JogoDao
        smsServiceMock = mock(SmsService.class); // Mock para SmsService
        enviarSMS = new EnviarSMS(juizMock, jogoDaoMock, smsServiceMock); // Classe sendo testada
        jogoMock = mock(Jogo.class); // Mock para Jogo
    }

    @Test
    void deveSalvarVencedorEEnviarSmsQuandoVencedorForValido() throws SQLException {
        // Configuração do mock: jogo com um vencedor
        Participante vencedor = new Participante("Ted");
        vencedor.setTelefone("+5511999999999");

        // Simulando o retorno do método getPrimeiroColocado do Juiz
        when(juizMock.getPrimeiroColocadoParticipante()).thenReturn(vencedor);
        when(juizMock.getPrimeiroColocado()).thenReturn(100.0);

        // Simulando a interação com os resultados do jogo
        when(jogoMock.getResultados()).thenReturn(Arrays.asList(
                new Resultado(vencedor, 100.0)));

        // Executa o método de validação
        enviarSMS.validaPrimeiroColocado(jogoMock, 100.0);

        // Verifica se o método salvaPrimeiroColocado foi chamado
        verify(jogoDaoMock, times(1)).salvaPrimeiroColocado(vencedor);

        // Verifica se o método enviarSms foi chamado com o telefone e mensagem corretos
        ArgumentCaptor<String> telefoneCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> mensagemCaptor = ArgumentCaptor.forClass(String.class);
        verify(smsServiceMock, times(1)).enviarSms(telefoneCaptor.capture(), mensagemCaptor.capture());

    }

    // @Test
    // void naoDeveEnviarSmsOuSalvarQuandoNaoHouverVencedor() throws SQLException {
    //     // Simulando o retorno do método getPrimeiroColocado com null
    //     when(juizMock.getPrimeiroColocado()).thenReturn(null);

    //     // Simulando que o jogo não tem resultados suficientes
    //     when(jogoMock.getResultados()).thenReturn(Arrays.asList());

    //     // Executa o método de validação com uma pontuação que não corresponde a nenhum
    //     // jogador
    //     enviarSMS.validaPrimeiroColocado(jogoMock, 200.0);

    //     // Verifica que nenhum método foi chamado em jogoDao e smsService
    //     verifyNoInteractions(jogoDaoMock);
    //     verifyNoInteractions(smsServiceMock);
    // }

}

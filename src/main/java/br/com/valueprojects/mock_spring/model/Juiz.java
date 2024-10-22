package br.com.valueprojects.mock_spring.model;

import java.util.List;

public class Juiz {

	private double maisPontos = Double.NEGATIVE_INFINITY;
	private double menosPontos = Double.POSITIVE_INFINITY;

	private Participante primeiroColocado; // Participante com mais pontos
	private Participante ultimoColocado; // Participante com menos pontos

	// Método para julgar os resultados do jogo
	public void julga(Jogo jogo) {
		List<Resultado> resultados = jogo.getResultados();

		if (resultados.size() == 0) {
			throw new RuntimeException("Sem resultados não há julgamento!");
		}

		for (Resultado resultado : resultados) {
			double metrica = resultado.getMetrica();
			Participante participante = resultado.getParticipante(); // Supondo que Resultado tenha um Participante

			// Verifica o participante com a maior pontuação
			if (metrica > maisPontos) {
				maisPontos = metrica;
				primeiroColocado = participante; // Atribui o participante com mais pontos
			}

			// Verifica o participante com a menor pontuação
			if (metrica < menosPontos) {
				menosPontos = metrica;
				ultimoColocado = participante; // Atribui o participante com menos pontos
			}
		}
	}

	// Retorna a pontuação mais alta
	public double getPrimeiroColocado() {
		return maisPontos;
	}

	// Retorna o participante com a maior pontuação
	public Participante getPrimeiroColocadoParticipante() {
		return primeiroColocado;
	}

	// Retorna a pontuação mais baixa
	public double getUltimoColocado() {
		return menosPontos;
	}

	// Retorna o participante com a menor pontuação
	public Participante getUltimoColocadoParticipante() {
		return ultimoColocado;
	}
}

package br.com.persistenciafa7.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import br.com.persistenciafa7.dao.TimeDao;
import br.com.persistenciafa7.model.Jogador;
import br.com.persistenciafa7.model.Partida;
import br.com.persistenciafa7.model.Pessoa;
import br.com.persistenciafa7.model.Time;
import br.com.persistenciafa7.util.HibernateUtil;

public class ExecucaoDeJogos {
	
	/**
	 * Roteiro de teste passo #2
	 * @param args
	 */
	public static void main(String[] args) {
		
		Session s = HibernateUtil.getHibernateSession();
		TimeDao timeDao = new TimeDao();
		s.getTransaction().begin();
		Time fortaleza = timeDao.findByName("Fortaleza");
		Time ceara = timeDao.findByName("Ceará");
		
		// Jogo de Ida
		Partida partidaIda  = executaPartida(fortaleza, ceara);
		timeDao.saveOrUpdate(partidaIda);
		
		//Jogo de Volta
		Partida partidaVolta  = executaPartida(ceara, fortaleza);
		timeDao.saveOrUpdate(partidaVolta);
		
		s.beginTransaction().commit();
	}

	private static Partida executaPartida(Time mandante, Time visitante) {
		
		Partida partida = new Partida();
		partida.setMandante(mandante);
		partida.setVisitante(visitante);
		
		Collection<Jogador> jogadoresEscalados = getEscalacao(mandante, visitante); 
		partida.setJogadores(jogadoresEscalados);
		
		partida.setGolsMandante((int)Math.ceil(Math.random() * 5));
		partida.setGolsVisitante((int)Math.ceil(Math.random() * 5));
		
		return partida;

	}

	private static Collection<Jogador> getEscalacao(Time... times) {
		Collection<Jogador> jogadoresEscalados = new ArrayList<>();

		for (Time time : times) {
			List<Pessoa> pessoas = new ArrayList<>(time.getPessoas());
			Collections.shuffle(pessoas);

			Iterator<Pessoa> ite = time.getPessoas().iterator();

			while (ite.hasNext()) {
				Pessoa p = ite.next();
				if(p instanceof Jogador) {
					jogadoresEscalados.add((Jogador)p);
					if(!jogadoresEscalados.isEmpty() && jogadoresEscalados.size() % 11 == 0) {
						break;
					}
				} 
				
				
				
			}
		}
		return jogadoresEscalados;
	}
}
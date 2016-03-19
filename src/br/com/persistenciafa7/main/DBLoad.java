package br.com.persistenciafa7.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

import br.com.persistenciafa7.model.Campeonato;
import br.com.persistenciafa7.model.Cidade;
import br.com.persistenciafa7.model.Estadio;
import br.com.persistenciafa7.model.Estado;
import br.com.persistenciafa7.model.Jogador;
import br.com.persistenciafa7.model.Pessoa;
import br.com.persistenciafa7.model.Tecnico;
import br.com.persistenciafa7.model.Time;
import br.com.persistenciafa7.util.HibernateUtil;

public class DBLoad {
	
	private static Session session;
	

	/**
	 * Insere os campeonatos 
	 * 
	 * @author erinaldo.souza
	 * @since 2016-02-18
	 * 
	 * @param nomes
	 */
	private static void insertCampeonatos(String... nomes) {
		for (String nome: nomes) {
			double premiacao = Math.floor(Math.random() * 1000000d);
			Campeonato c = new Campeonato(nome, premiacao);
			session.save(c);
		}
	}
	
	/**
	 * Insere um estado, cidade, estadios e times
	 * 
	 * @author erinaldo.souza
	 * @since 2016-03-18
	 * 
	 * @param estado
	 * @param cidade
	 */
	private static void insereEstadosCidadesEstadiosTimesCascade(String strEstado, String nomeCidade) {
		String[] estadoArr = strEstado.split("\\W");
		
		Estado estado = new Estado(estadoArr[0], estadoArr[1]);
		Cidade  cidade = new Cidade(nomeCidade, estado);
		
		Estadio castelcao = new Estadio("Castel�o", 30000, cidade);
		Estadio pici = new Estadio("Pici", 29000, cidade);

		cidade.addEstadio(pici);
		cidade.addEstadio(castelcao);
		
		estado.addCidade(cidade);
		
		Time f =  new Time("Fortaleza", cidade, castelcao);
		Time c =  new Time("Cear�", cidade, pici);
		
		cidade.setTimes(Arrays.asList(new Time[]{f,c}));

		session.save(estado);
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
//		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");
//		entityManagerFactory.createEntityManager();
		
		session = HibernateUtil.getHibernateSession();
		
		session.beginTransaction();
		insertCampeonatos("Brasileiro", "Copa do Brasil", "Libertadores");
		
		insereEstadosCidadesEstadiosTimesCascade("Cear�-CE", "Fortaleza");
		
		Collection<Time> times = session.createCriteria(Time.class).list();
		
		inserePessoasNosTimes(times);
		session.getTransaction().commit();
		
	}

	/**
	 * Insere os jogadores, tecnicos e pessoas comuns associadas a um time
	 * 
	 * @author erinaldo.souza
	 * @since 2016-03-18
	 * 
	 * @param times
	 */
	private static void inserePessoasNosTimes(Collection<Time> times) {
		List<Pessoa> pessoas = new ArrayList<>(11);
		Pessoa p = null;
		for (Time time : times) {
			for (int i = 0; i < 11; i++) {
				p = new Jogador("jogador_" + i + "_" + time.getNome(), "Posicao" + i , time);
				session.save(p);
				pessoas.add(p);
			}
				
			p = new Tecnico("Tecnico do" + time.getNome(), "Time de 3� divis�o", time);
			session.save(p);
			pessoas.add(p);
			
			p = new Pessoa("George do " + time.getNome(), time);
			session.save(p);
			
			time.setPessoas(pessoas);
			session.saveOrUpdate(time);
			pessoas.clear();
		}
	}
}
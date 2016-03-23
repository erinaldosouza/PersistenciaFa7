package br.com.persistenciafa7.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.persistenciafa7.model.Time;

public class TimeDao extends GenericDao {

	/**
	 * Busca um time pelo nome
	 * 
	 * @author erinaldo.souza
	 * @since 016-03-22
	 * 
	 * @param nome
	 * @return time com o nome especifico
	 */
	public Time findByName(String nome) {
		Criteria c = session.createCriteria(Time.class)
		.add(Restrictions.eq("nome", nome));
		return (Time) c.uniqueResult();
	}
}
package br.com.persistencia.fa7.interceptors;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class ModelInterceptor extends EmptyInterceptor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8069890939815759832L;
	
	
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,	String[] propertyNames, Type[] types) {
		boolean hasChange = false;
		
		for (int i = 0; i < propertyNames.length; i++) {
			if("data".equalsIgnoreCase(propertyNames[i])){
				state[i] = new Date();
				hasChange = true;
			};
			
		}
		return hasChange;
	}
}

package com.guiaindicado.dominio.geral;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioHibernate {

	@Autowired private SessionFactory sessionFactory;

	protected Session getSessao() {
		return sessionFactory.getCurrentSession();
	}
}

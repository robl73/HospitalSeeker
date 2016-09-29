package com.hospitalsearch.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospitalsearch.dao.AdminTokenConfigDAO;
import com.hospitalsearch.entity.AdminTokenConfig;
import com.hospitalsearch.entity.Token;



@Repository
public class AdminTokenConfigDAOImpl extends GenericDAOImpl<AdminTokenConfig, Long> implements AdminTokenConfigDAO {

	@Autowired(required = true)
	public AdminTokenConfigDAOImpl(SessionFactory factory) {
		this.setSessionFactory(factory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdminTokenConfig> getAll() {
		String sql = "SELECT * FROM AdminTokenConfig";
		SQLQuery query = this.currentSession().createSQLQuery(sql);
		query.addEntity(AdminTokenConfig.class);
		return query.list();
	}

	@Override
	public void update(AdminTokenConfig instance) {
		try{
		org.hibernate.Query query = this.currentSession()
				.createQuery("update AdminTokenConfig set value = :value where token = :token");
		query.setParameter("value", instance.getValue());
		query.setParameter("token", instance.getToken());
		query.executeUpdate();
		}catch(Exception e){
			System.out.println("Exception in update(AdminTokenConfig instance). Enter valid data, please.");
		}
	}

	@Override
	public void save(AdminTokenConfig instance) {
	/*	if (isExists(instance) == true) {
			update(instance);
		} else {
			org.hibernate.Query query = this.currentSession()
					.createQuery("insert into AdminTokenConfig (token, value) values (:token, :value)");
			query.setParameter("token", Token.REMEMBER_ME_TOKEN_EXPIRATION);// instance.getToken());
			query.setParameter("value", "45");//instance.getValue());
			query.executeUpdate();
		}*/
	}

	private boolean isExists(AdminTokenConfig instance) {
		boolean exists = false;
		for (AdminTokenConfig adminTokenConfig : getAll()) {
			if (instance.getToken().equals(adminTokenConfig.getToken())) {
				exists = true;
			}
		}
		return exists;
	}

	@Override
	public void delete(AdminTokenConfig instance) {
		// TODO Auto-generated method stub

	}

	@Override
	public AdminTokenConfig getByToken(Token token) {
		String sql = "SELECT * FROM AdminTokenConfig where token = '" + token + "'";
		SQLQuery query = this.currentSession().createSQLQuery(sql);
		query.addEntity(AdminTokenConfig.class);
		return (AdminTokenConfig) query.uniqueResult();
	}

}

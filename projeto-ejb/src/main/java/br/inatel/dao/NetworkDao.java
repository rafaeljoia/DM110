package br.inatel.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.inatel.Host;

@Stateless
public class NetworkDao {
	
	@PersistenceContext(unitName = "network")
	private EntityManager em;
	
	
	public void insertHost(Host host) {
		em.persist(host);
	}
	
	public List<Host> getHost() {
		return em.createQuery("from Host e", Host.class).getResultList();
	}
	
	public boolean getHostStatus(String ip) {
		boolean result = em.createQuery("from Host e where ip = :ip ", Host.class)
				.setParameter("ip", ip)
				.getResultList()
				.get(0)
				.getStatus();
		return result;
	}

}

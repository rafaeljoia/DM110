package br.inatel.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

import br.inatel.api.Host;
import br.inatel.api.NetworkService;
import br.inatel.interfaces.NetworkRemote;

@RequestScoped
public class NetworkServiceImpl implements NetworkService {
	
	@EJB(lookup = "java:app/projeto-ejb-0.1-SNAPSHOT/NetworkBean!br.inatel.interfaces.NetworkRemote")
	private NetworkRemote networkRemote;

	@Override
	public void start(String ip, int cidr) {
		
		List<String> networkIps = NetworkIPFinder.getIPList(ip, cidr);
		List<String> ipToBeSent = new ArrayList<String>();
		
		for (String ipString : networkIps) {
			ipToBeSent.add(ipString);
			
			if (ipToBeSent.size() == 10) {
				networkRemote.insertHost(ipToBeSent);
				ipToBeSent = new ArrayList<>();
			}
			
		}
		
		if (ipToBeSent.size() > 0) {
			networkRemote.insertHost(ipToBeSent);
		}
		

	}

	@Override
	public Host getIpStatus(String ip) {
		return networkRemote.getHostStatus(ip);
	}

}

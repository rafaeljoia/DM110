package br.inatel.interfaces;

import java.util.List;

import br.inatel.api.Host;

public interface Network {
		
	Host getHostStatus(String ip);
		
	void insertHost(List<String> networkIps);
	
}

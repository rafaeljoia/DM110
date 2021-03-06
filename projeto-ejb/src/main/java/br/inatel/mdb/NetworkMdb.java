package br.inatel.mdb;

import java.net.NetworkInterface;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import br.inatel.dao.NetworkDao;
import br.inatel.entities.Host;
import br.inatel.interfaces.NetworkLocal;
import br.inatel.network.ListNetwork;
import br.inatel.network.PingIP;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
		@ActivationConfigProperty(propertyName="destination", propertyValue="java:/jms/queue/dm110queue"),
		@ActivationConfigProperty(propertyName="maxSession", propertyValue="10")
})
public class NetworkMdb implements MessageListener {
	
	@EJB
	NetworkDao networkDao;

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		
		try {
			if (message instanceof ObjectMessage) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				Object object = objectMessage.getObject();
				
				if (object instanceof ListNetwork) {
					ListNetwork listNetwork = (ListNetwork) object;
					
					for (String ip : listNetwork.getNetworkIps()) {
						Host host = new Host();
						host.setIp(ip);
						host.setStatus(PingIP.execPing(ip));
						networkDao.insertHost(host);
					}
					
				}		
	
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

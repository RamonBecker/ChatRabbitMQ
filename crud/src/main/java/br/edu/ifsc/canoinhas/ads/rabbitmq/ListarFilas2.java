package br.edu.ifsc.canoinhas.ads.rabbitmq;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import com.rabbitmq.http.client.Client;
import com.rabbitmq.http.client.ClientParameters;
import com.rabbitmq.http.client.domain.ExchangeInfo;
import com.rabbitmq.http.client.domain.QueueInfo;

public class ListarFilas2 {

	public static void main(String args[]) throws MalformedURLException, URISyntaxException {

		Client c = new Client(
				new ClientParameters().url("http://127.0.0.1:15672/api/").username("admin").password("ads2020"));

		List<QueueInfo> queues = c.getQueues();

		for (QueueInfo queuesInfo : queues) {
			System.out.println(queuesInfo.getName());
		}

		List<ExchangeInfo> exchanges = c.getExchanges();

		for (ExchangeInfo exchangeInfo : exchanges) {
			System.out.println(exchangeInfo.getName());
		}
	}
}

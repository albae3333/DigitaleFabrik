package de.fh.albsig.digitalfactory.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.fh.albsig.digitalfactory.connector.TaskConfirmer;
import de.fh.albsig.digitalfactory.connector.TaskSchedule;
import de.fh.albsig.digitalfactory.connector.model.Confirmation;
import de.fh.albsig.digitalfactory.connector.model.Task;

public class MqttClientMain
{
	public static void main(final String[] args)
		throws MqttException
	{
		final IMqttClient mqttClient = new MqttClient(
				String.format("tcp://%s:%s", MqttConstants.BROKER, MqttConstants.BROKER_PORT), "SAP");
		
		final MqttConnectOptions options = new MqttConnectOptions();
		options.setAutomaticReconnect(true);
		options.setCleanSession(true);
		options.setConnectionTimeout(60);
		mqttClient.connect(options);

		String lastTransactionKey = "0220";
		String amountOfWater = "23";
		
		mqttClient.subscribe(MqttConstants.TOPIC_GETSTEPS, 1, (topic, message) -> {
			try {
				
				String[] message_split = new String(message.getPayload()).split(";");
				String orderNumber = message_split[0];
				String transactionKey = message_split[1];
				
				final TaskSchedule schedule = new TaskSchedule();
				String json_current_task = "0";
				String json_next_task = "0";
				//von SAP den aktuellen Schritt holen
				if(Integer.parseInt(transactionKey)<=210)
				{	
					Confirmation confirmation = new Confirmation(orderNumber, transactionKey, true);
					TaskConfirmer confirmer = new TaskConfirmer();
					confirmer.confirm(confirmation);
					transactionKey = String.valueOf(Integer.parseInt(transactionKey)+10);
					while(transactionKey.length() < 4)
					{
						transactionKey = "0" + transactionKey;
					}
					final List<Task> tasks = schedule.nextTask(orderNumber, transactionKey);
					json_current_task = new ObjectMapper().writeValueAsString(tasks.get(0));
					if(!(transactionKey.equals((lastTransactionKey))))
					{
						json_next_task = new ObjectMapper().writeValueAsString(tasks.get(1));

					}
				}
				
				final String combined_json = "{ \"currentTask\": "+json_current_task +", \"nextTask\": "+ json_next_task+ ",\"water\": " + amountOfWater +"}";
				System.out.println("MQTT message send:\n" + combined_json);
				//beide Schritte werden als JSON gesendet
				mqttClient.publish(MqttConstants.TOPIC_STEPS, combined_json.getBytes(), 1, false);

			} catch (final Throwable t) {

				t.printStackTrace(System.err);

			}
		});

		mqttClient.subscribe(MqttConstants.TOPIC_GETLIST, 1, (topic, message) -> {
			try {
				OrderListScheduler scheduler = new OrderListScheduler();
				List<Order> orders = new ArrayList<Order>();
				orders = scheduler.orderList();	
				
				int i = 0;
				String combined_json = "{";
				String json_temp_order = null;
				
				while(i < orders.size())
				{
					if(i!=0){
						combined_json = combined_json + ",";
					}
					json_temp_order = new ObjectMapper().writeValueAsString(orders.get(i));
					combined_json = combined_json + " \"Auftrag" + String.valueOf(i) + "\": "+ json_temp_order;
					i++;
				}
				combined_json = combined_json + "}";
				
				System.out.println("MQTT message send:\n" + combined_json);
				mqttClient.publish(MqttConstants.TOPIC_LIST, combined_json.getBytes(), 1, false);
				
			} catch (final Throwable t) {

				t.printStackTrace(System.err);

			}
		});
		
		
		System.out.println("Press enter to exit!");

		final Scanner s = new Scanner(System.in);
		s.nextLine();
		s.close();

		System.exit(0);
	}

}

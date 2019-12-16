package de.fh.albsig.digitalfactory.client;

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
		options.setCleanSession(false);
		options.setConnectionTimeout(10);
		mqttClient.connect(options);

		mqttClient.subscribe(MqttConstants.TOPIC_GETLIST, 1, (topic, message) -> {
			try {
				final TaskSchedule schedule = new TaskSchedule();
				final Task task = schedule.nextTask(orderlists);
				System.out.println(task);

				final String json = new ObjectMapper().writeValueAsString(task);

				mqttClient.publish(MqttConstants.TOPIC_LIST, json.getBytes(), 1, false);

			} catch (final Throwable t) {

				t.printStackTrace(System.err);

			}
		});

		mqttClient.subscribe(MqttConstants.TOPIC_GETSTEPS, 1, (topic, message) -> {
			try {

				final String payload = new String(message.getPayload());

				final Confirmation confirmation = new ObjectMapper().readValue(payload, Confirmation.class);
				System.out.println(confirmation);

				final TaskConfirmer confirmer = new TaskConfirmer();
				final String confirmationMessage = confirmer.confirm(confirmation);
				System.out.println(confirmationMessage);

				final TaskSchedule schedule = new TaskSchedule();
				final Task task = schedule.nextTask(confirmation.getOrderNumber());
				System.out.println(task);

				final String json = new ObjectMapper().writeValueAsString(task);

				mqttClient.publish(MqttConstants.TOPIC_STEPS, json.getBytes(), 1, false);

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

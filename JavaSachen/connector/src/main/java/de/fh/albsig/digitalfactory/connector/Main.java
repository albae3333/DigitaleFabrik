package de.fh.albsig.digitalfactory.connector;

import com.sap.conn.jco.JCoException;

import de.fh.albsig.digitalfactory.connector.model.Confirmation;
import de.fh.albsig.digitalfactory.connector.model.Task;


public class Main
{

	public static void main(final String[] args) throws JCoException
	{
		final TaskSchedule schedule = new TaskSchedule();
		final Task task = schedule.nextTask("000001000185");
		System.out.println(task.getTransactionKey());

		final Confirmation confirmation = new Confirmation("000001000185", "0020", true);

		final TaskConfirmer confirmer = new TaskConfirmer();
		final String message = confirmer.confirm(confirmation);
		System.out.println(message);
	}

}

package de.fh.albsig.digitalfactory.connector;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRepository;

import de.fh.albsig.digitalfactory.connector.model.Task;


public final class TaskSchedule
{

	private final JCoDestination destination;
	private final JCoFunction function;


	public TaskSchedule()
		throws JCoException
	{
		this.destination = JCoDestinationManager.getDestination(JCoConstants.JCO_DESTINATION_PATH);
		this.destination.ping();

		final JCoRepository repository = this.destination.getRepository();

		this.function = repository.getFunction(JCoConstants.NEXT_TASK_BY_ORDERNUMBER);
	}


	public Task nextTask(final String orderNumber)
		throws JCoException
	{
		final JCoParameterList input = this.function.getImportParameterList();
		input.setValue(JCoConstants.I_ORDERNUMBER, orderNumber);

		this.function.execute(this.destination);

		final JCoParameterList output = this.function.getExportParameterList();

		return this.parseTask(output);
	}


	private Task parseTask(final JCoParameterList values)
	{
		final Task task = new Task(
			values.getString(JCoConstants.E_TRANSACTIONKEY),
			values.getString(JCoConstants.E_DESCRIPTION),
			values.getDouble(JCoConstants.E_TEMPERATURE),
			values.getLong(JCoConstants.E_DURATION),
			Boolean.valueOf(values.getString(JCoConstants.E_STIRRER_ENABLED)),
			Boolean.valueOf(values.getString(JCoConstants.E_HEATER_ENABLED)),
			values.getString(JCoConstants.E_OPERATION)
		);
		return task;
	}

}

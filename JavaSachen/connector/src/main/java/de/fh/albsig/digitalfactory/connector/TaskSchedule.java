package de.fh.albsig.digitalfactory.connector;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoTable;

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


	public List<Task> nextTask(final String orderNumber, final String transactionKey)
		throws JCoException, FileNotFoundException, UnsupportedEncodingException
	{
		final JCoParameterList input = this.function.getImportParameterList();
		input.setValue(JCoConstants.I_ORDERNUMBER, orderNumber);
		input.setValue(JCoConstants.I_TRANSACTIONKEY, transactionKey);

		this.function.execute(this.destination);
	
		final JCoTable table = this.function.getTableParameterList().getTable("T_OPERATION");
		List<Task> tasks = new ArrayList<Task>();

		for (int i = 0; i < table.getNumRows(); i++)

		{
			table.setRow(i);
			
			// the for-each loop works here, however better performance is provided
			// by accessing fields directly from the table e.g. with getString(i)
			int j = 0;
			String[] StringField = new String[table.getNumColumns()];
			for (JCoField field : table) {
				StringField[j] = field.getString();
				j++;
			};
			long l = (long)Double.parseDouble(StringField[3]);
			
			Task task = new Task(StringField[1], StringField[6], 
					Double.parseDouble(StringField[2]),  l, 
					Boolean.valueOf(StringField[4]), Boolean.valueOf(StringField[5]), 
					StringField[7]);
	
			tasks.add(task);
		};
		return tasks;
	}


/*
	private Task parseTask(final JCoParameterList values)
	{
		//final Task task = new Task("0010", "", 0.0, 0, true, true, "");
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
*/
}
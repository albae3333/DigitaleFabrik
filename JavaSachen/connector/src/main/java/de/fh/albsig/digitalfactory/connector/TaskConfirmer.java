package de.fh.albsig.digitalfactory.connector;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRepository;

import de.fh.albsig.digitalfactory.connector.model.Confirmation;


public final class TaskConfirmer
{

	private final JCoDestination destination;
	private final JCoFunction function;


	public TaskConfirmer()
		throws JCoException
	{
		this.destination = JCoDestinationManager.getDestination(JCoConstants.JCO_DESTINATION_PATH);
		this.destination.ping();

		final JCoRepository repository = this.destination.getRepository();

		this.function = repository.getFunction(JCoConstants.CONFIRM_TASK);
	}


	public String confirm(final Confirmation confirmation)
		throws JCoException
	{
		final JCoParameterList input = this.function.getImportParameterList();
		input.setValue(JCoConstants.I_ORDERNUMBER, confirmation.getOrderNumber());
		input.setValue(JCoConstants.I_TRANSACTIONKEY, confirmation.getTransactionKey());

		this.function.execute(this.destination);

		final JCoParameterList output = this.function.getExportParameterList();

		return output.getString(JCoConstants.E_CONFIRMATION_MESSAGE);
	}

}

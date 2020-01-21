package de.fh.albsig.digitalfactory.client;

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

import de.fh.albsig.digitalfactory.connector.JCoConstants;

public class OrderListScheduler {
	
	
	
	private JCoDestination destination;
	private JCoFunction function;

	public OrderListScheduler() throws JCoException
	{
		this.destination = JCoDestinationManager.getDestination(JCoConstants.JCO_DESTINATION_PATH);
		this.destination.ping();

		final JCoRepository repository = this.destination.getRepository();

		this.function = repository.getFunction(JCoConstants.GETORDERLIST);
	}
	
	public List<Order> orderList()
			throws JCoException, FileNotFoundException, UnsupportedEncodingException
		{
			final JCoParameterList input = this.function.getImportParameterList();
			input.setValue(JCoConstants.I_FERTIGUNGSSTEUERER, "013");
			this.function.execute(this.destination);
		
			final JCoTable table = this.function.getExportParameterList().getTable("E_LISTE");
			List<Order> orders = new ArrayList<Order>();
			
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
				
				Order order = new Order(StringField[0], StringField[1], 
						StringField[2], StringField[3],
						StringField[4]);
				orders.add(order);
			};
			return orders;
		}

	
	

}

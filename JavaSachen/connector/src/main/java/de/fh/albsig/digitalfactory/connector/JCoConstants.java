package de.fh.albsig.digitalfactory.connector;


public final class JCoConstants
{

	public static final String JCO_DESTINATION_PATH = "src/main/resources/ConProperties";


	public static final String NEXT_TASK_BY_ORDERNUMBER = "Z_NEXTOP_BYORDER1";
	public static final String CONFIRM_TASK = "Z_MS_CONFIRMOP";


	public static final String I_ORDERNUMBER = "I_AUFNR";
	public static final String I_TRANSACTIONKEY = "I_VORNR";

	public static final String E_OPERATION = "E_OPCODE";
	public static final String E_TRANSACTIONKEY = "E_VORNR";
	public static final String E_DESCRIPTION = "E_DESCRIPTION";
	public static final String E_TEMPERATURE = "E_TEMP";
	public static final String E_DURATION = "E_DAUER";
	public static final String E_STIRRER_ENABLED = "E_SWITCH1";
	public static final String E_HEATER_ENABLED = "E_SWITCH2";
	public static final String E_CONFIRMATION_MESSAGE = "E_RESULT";


	private JCoConstants() {}
}

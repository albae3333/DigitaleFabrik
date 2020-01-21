package de.fh.albsig.digitalfactory.connector;


public final class JCoConstants
{

	public static final String JCO_DESTINATION_PATH = "src/main/resources/ConProperties";


	public static final String NEXT_TASK_BY_ORDERNUMBER = "ZE271_NAECHSTESCHRITTEHOLEN";
	public static final String CONFIRM_TASK = "ZE271_RUECKMELDUNG";
	public static final String GETORDERLIST = "ZE271_AUFTRAGSLISTE";

	public static final String I_FERTIGUNGSSTEUERER = "I_FERTIGUNGSSTEUERER";
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

package ihm;

import com.sun.jna.*;
import com.sun.jna.platform.win32. WinDef.*;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public class Couleur
{
	public static final String RED_UNDERLINED = "\033[4;31m";

	public static final String RESET   = "\u001B[0m";
	public static final String BLACK   = "\u001B[30m";
	public static final String RED     = "\u001B[31m";
	public static final String MAGENTA = "\u001B[35m";
	public static final String GREEN   = "\u001B[32m";
	public static final String YELLOW  = "\u001B[33m";
	public static final String BLUE    = "\u001B[34m";
	public static final String PURPLE  = "\u001B[35m";
	public static final String CYAN    = "\u001B[36m";
	public static final String WHITE   = "\u001B[37m";

	public static final String BLACK_BACKGROUND  = "\u001B[40m";
	public static final String RED_BACKGROUND    = "\u001B[41m";
	public static final String GREEN_BACKGROUND  = "\u001B[42m";
	public static final String YELLOW_BACKGROUND = "\u001B[43m";
	public static final String BLUE_BACKGROUND   = "\u001B[44m";
	public static final String PURPLE_BACKGROUND = "\u001B[45m";
	public static final String CYAN_BACKGROUND   = "\u001B[46m";
	public static final String WHITE_BACKGROUND  = "\u001B[47m";

	private String nom;
	private String stylo;
	private String gras;

	public Couleur(String nom, String stylo, String gras)
	{
		this.nom   = nom;
		this.stylo = stylo;
		this.gras  = gras;
	}

	public String ecrire(char stylo)
	{
		switch(stylo)
		{
			case 'R' : return RED;
			case 'G' : return GREEN;
			case 'B' : return BLUE;
			case 'Y' : return YELLOW;
			case 'P' : return PURPLE;
			case 'C' : return CYAN;
			case 'M' : return MAGENTA;
			default  : return RESET;
		}
	}

	public String surligner(char feutre)
	{
		switch(feutre)
		{
			case 'R' : return RED_BACKGROUND;
			case 'G' : return GREEN_BACKGROUND;
			case 'B' : return BLUE_BACKGROUND;
			case 'Y' : return YELLOW_BACKGROUND;
			case 'P' : return PURPLE_BACKGROUND;
			case 'C' : return CYAN_BACKGROUND;
			default  : return BLACK_BACKGROUND;
		}
	}

	public void start()
	{
		if(System.getProperty("os.name").startsWith("Windows"))
		{
			// Set output mode to handle virtual terminal sequences
			Function GetStdHandleFunc = Function.getFunction("kernel32", "GetStdHandle");
			DWORD STD_OUTPUT_HANDLE = new DWORD(-11);
			HANDLE hOut = (HANDLE)GetStdHandleFunc.invoke(HANDLE.class, new Object[]{STD_OUTPUT_HANDLE});

			DWORDByReference p_dwMode = new DWORDByReference(new DWORD(0));
			Function GetConsoleModeFunc = Function.getFunction("kernel32", "GetConsoleMode");
			GetConsoleModeFunc.invoke(BOOL.class, new Object[]{hOut, p_dwMode});

			int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 4;
			DWORD dwMode = p_dwMode.getValue();
			dwMode.setValue(dwMode.intValue() | ENABLE_VIRTUAL_TERMINAL_PROCESSING);
			Function SetConsoleModeFunc = Function.getFunction("kernel32", "SetConsoleMode");
			SetConsoleModeFunc.invoke(BOOL.class, new Object[]{hOut, dwMode});
		}
	}
}
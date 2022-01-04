import metier.*;
import ihm.*;
import java.util.Scanner;


public class Controleur
{
	public static void main(String[] args)
	{
		boolean auto = true;
		Menu    m;

		int i = 1;
		while (i==1)
		{
			menu1();
			Scanner sc = new Scanner(System.in);
			switch (sc.nextLine())
			{
				case "A":
					m = new Menu(auto);
					i = 2;
					break;
				case "P":
					auto = false;
					m = new Menu(auto);
					i = 2;
					break;
				case "Q":
					return;
			}
		}
	}

	public static void menu1()
    {
        System.out.print("+----------------------------------------------+\n" + 
                         "|                                              |\n" +
                         "| Quel mode souhaitez vous ?                   |\n" +
                         "|                                              |\n" + 
                         "| [A]utomatique ?                              |\n" +
                         "| [P]as à pas ?                                |\n" + 
                         "| [Q]uitter ?                                  |\n" +
                         "+----------------------------------------------+\n");
    }
}
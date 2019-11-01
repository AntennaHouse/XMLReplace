import java.io.*;
import java.util.*;

public class XMLReplace {

	public static void main(String args[]){

		char[] caps = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		char[] lower = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		char[] numeric = {'1','2','3','4','5','6','7','8','9','0'};
		Random rdm = new Random();
		boolean mask = false;
		String inputFileName = null;
		String outputFileName = null;

		for (int i = 0;  i < args.length;  i++) {
		    if (args[i].equals("--mask")) {
			mask = true;
			continue;
		    }

		    if (inputFileName == null) {
			inputFileName = args[i];
			continue;
		    } else if (outputFileName == null) {
			outputFileName = args[i];
			continue;
		    } else {
			System.out.println("unknown option: " + args[i]);
			System.exit(1);
		    }
		}
		if(inputFileName == null  ||  outputFileName == null)
		{
			System.out.println("Usage: [--mask] FOFile OutputFile");
			System.exit(1);
		}

		File fis;
		File fos;
		boolean replaceFlag = false;
		try{
			fis = new File(inputFileName);
			fos = new File(outputFileName);
			char[] v = new char[1];
			FileReader sr = new FileReader(fis);
			FileWriter sw = new FileWriter(fos);
			int z;
			z=sr.read(v);
			boolean ampFlag = false;

			char temp;
			while(z != -1)
			{
				char m = v[0];
				if(m == '<')
					replaceFlag = false;
				else if(m == '>')
					replaceFlag = true;
				else if(m == ' ' || Character.isSpaceChar(m) || m == 194)
					m=m;
				else if(m == '&')
					ampFlag = true;
				else if (m == ';' && ampFlag == true)
				    ampFlag = false;
				else if(m == '\r' || m == '\n' || m == '\f' || m == '\"' || m == '\\' || m == '\'' || m == '\b' || m == '\t' || !Character.isLetterOrDigit(m))
					m=m;
				else if(replaceFlag == true && ampFlag == false){
				    if (mask) {
					m = 'T';
				    } else {
					if(Character.isDigit(m))
						m = numeric[rdm.nextInt(10)];
					else if(Character.isUpperCase(m))
					{
						System.out.println(m);
						m = caps[rdm.nextInt(26)];
					}
					else if(Character.isLowerCase(m))
						m = lower[rdm.nextInt(26)];
					else
						m=m;
				    }
				}

				sw.write(m);
				z=sr.read(v);
			}
			sr.close();
			sw.close();
		}catch(Exception e){
			System.out.println("Error " + e);
		}
	}

}

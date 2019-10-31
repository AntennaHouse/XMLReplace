import java.io.*;
import java.util.*;

public class XMLReplace {


	
	public static void main(String args[]){
		
		char[] caps = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		char[] lower = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		char[] numeric = {'1','2','3','4','5','6','7','8','9','0'};
		Random rdm = new Random();
		if(args.length < 2)
		{
			System.out.println("Usage: FOFile OutputFile");
			return;
		}
		
		File fis;
		File fos;
		boolean replaceFlag = false;
		try{
			fis = new File(args[0]);
			fos = new File(args[1]);
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
				else if (m == ';' && replaceFlag == true && ampFlag == true)
				    ampFlag = false;
				else if(m == '\r' || m == '\n' || m == '\f' || m == '\"' || m == '\\' || m == '\'' || m == '\b' || m == '\t' || !Character.isLetterOrDigit(m))
					m=m;
				else if(replaceFlag == true && ampFlag == false){
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
					//m = 'T';
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

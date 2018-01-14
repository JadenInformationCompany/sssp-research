package com.szhis.frsoft.common.utils.chinesechar;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*五笔码生成*/
public class IGenerateIMECodeWB implements IGenerateIMECode {
	static String[] IME ;
	private static IGenerateIMECodeWB instance = null;
	/*初始化编码数组*/
	static{		
			
		try {	
			BufferedReader in = new BufferedReader(new InputStreamReader(IGenerateIMECode.class.getResourceAsStream("/IMECodeWB.txt")));
			String line;
			line = in.readLine();
			if(line!=null){
				IME = new String[Integer.parseInt(line)];
				int i=0;
				while ((line = in.readLine()) != null){
					//System.out.println(line);
					IME[i] = line;
					i ++;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}		
	}
	
	@Override
	public String generateFullCode(String text) {
		 StringBuilder out = new StringBuilder ("");
		 String tmp;
		 for (char c : text.toCharArray()){
			  int asciiCode = (int)c;
			  if((asciiCode>=19968) && (asciiCode<=40869)){
				  tmp = IME[asciiCode-19968];
				  if(tmp.isEmpty()){
					  out.append(c);
				  }
				  else{
					  out.append(tmp);
				  }
			  }
			  else{
				  out.append(c);
			  }
		  }
		  return out.toString();
	}

	@Override
	public String generateShortCode(String text) {
		 StringBuilder out = new StringBuilder ("");
		 String tmp;
		 for (char c : text.toCharArray()){
			  int asciiCode = (int)c;
			  if((asciiCode>=19968) && (asciiCode<=40869)){
				  tmp = IME[asciiCode-19968];
				  if(tmp.isEmpty()){
					  out.append(c);
				  }
				  else{
					  out.append(tmp.charAt(0));
				  }
			  }
			  else{
				  out.append(c);
			  }
		  }
		  return out.toString();	
	} 
	
	public static IGenerateIMECodeWB getInstance() {
		if( instance == null ) {
			try{
				instance = new IGenerateIMECodeWB();
			} catch (Exception e) {
				
			}		
        }
        return instance;	
	}
}
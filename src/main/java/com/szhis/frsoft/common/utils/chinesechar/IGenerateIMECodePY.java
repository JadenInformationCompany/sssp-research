package com.szhis.frsoft.common.utils.chinesechar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



/*拼音码生成*/
public class IGenerateIMECodePY implements IGenerateIMECode {
	static String[] IME ;
	private static IGenerateIMECodePY instance = null;

	/*初始化编码数组*/
	static{		
		
			
		try {	
			BufferedReader in = new BufferedReader(new InputStreamReader(IGenerateIMECode.class.getResourceAsStream("/IMECodePY.txt")));
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
		 int index;
		 for (char c : text.toCharArray()){
			  int asciiCode = (int)c;
			  if((asciiCode>=19968) && (asciiCode<=40869)){
				  tmp = IME[asciiCode-19968];
				  if(tmp.isEmpty()){
					  out.append(c);
				  }
				  else{
					  index = tmp.indexOf(' ');
					  if(index>=0){
						  tmp = tmp.substring(0,  index-1);
					  }
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
	
	public static IGenerateIMECodePY getInstance() {
		if( instance == null ) {
            instance = new IGenerateIMECodePY();
        }
        return instance;	
	}
}

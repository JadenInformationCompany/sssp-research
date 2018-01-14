package com.szhis.frsoft.common.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szhis.frsoft.common.utils.chinesechar.IGenerateIMECode;
import com.szhis.frsoft.common.utils.chinesechar.IGenerateIMECodePY;
import com.szhis.frsoft.common.utils.chinesechar.IGenerateIMECodeWB;
import com.szhis.frsoft.common.utils.chinesechar.IMECode;


@Controller
@RequestMapping(value = "/common/chinesechar")
public class IMECodeController {
	//private static Logger logger = LoggerFactory.getLogger(IMECodeController.class);
	
	@RequestMapping(method = RequestMethod.POST)
	
	public ResponseEntity<IMECode> convert(@RequestBody IMECode imecode){
		return ImeConvert(imecode);
	}
	
	public ResponseEntity<IMECode> ImeConvert(IMECode imecode) {		
		if(!imecode.getText().isEmpty()){		
			IGenerateIMECode ime = null;
			try{
				if((imecode.getFlag()&2)==2){ //五笔
					ime = IGenerateIMECodeWB.getInstance();
				}
				else{
					ime = IGenerateIMECodePY.getInstance();
				}
			}catch(Exception e){
				ime = null;
			}
			
			if(ime==null){
				imecode.setCode(imecode.getText());
			}else{
				if((imecode.getFlag()&4)==4){ //完整编码
					imecode.setCode(ime.generateFullCode(imecode.getText()));
				}
				else{
					imecode.setCode(ime.generateShortCode(imecode.getText()));
				}
			}
		}
		return new ResponseEntity<IMECode>(imecode, HttpStatus.OK);		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<IMECode> ViewPage() {
		IMECode imecode = new IMECode();
		return new ResponseEntity<IMECode>(imecode, HttpStatus.OK);
	}
	
	@RequestMapping(value="/wb", method=RequestMethod.GET)
	@ResponseBody  
	public ResponseEntity<IMECode> wb(){
		IMECode imecode = new IMECode();
		return new ResponseEntity<IMECode>(imecode, HttpStatus.OK);
	}
	
	@RequestMapping(value="/wb/{text}", method=RequestMethod.GET)
	@ResponseBody  
	public ResponseEntity<IMECode> wb1(@PathVariable String text){
		IMECode imecode = new IMECode();
		imecode.setFlag(2);
		imecode.setText(text);		
		return ImeConvert(imecode);
	}
	
	@RequestMapping(value="/wb/{text}/{param}", method=RequestMethod.GET)
	@ResponseBody         
	public ResponseEntity<IMECode> wb2(@PathVariable String text, @PathVariable String param) { 
		IMECode imecode = new IMECode();
		if(param.equals("Full")){
			imecode.setFlag(2|4);
		}
		else{
			imecode.setFlag(2);
		}	
		imecode.setText(text);
		return ImeConvert(imecode);
	}	
	
	@RequestMapping(value="/py", method=RequestMethod.GET)
	@ResponseBody  
	public ResponseEntity<IMECode> py(){
		IMECode imecode = new IMECode();
		return new ResponseEntity<IMECode>(imecode, HttpStatus.OK);
	}
	
	@RequestMapping(value="/py/{text}", method=RequestMethod.GET)
	@ResponseBody  
	public ResponseEntity<IMECode> py1(@PathVariable String text){
		IMECode imecode = new IMECode();
		imecode.setFlag(1);
		imecode.setText(text);
		return ImeConvert(imecode);
	}
	
	@RequestMapping(value="/py/{text}/{param}", method=RequestMethod.GET)
	@ResponseBody         
	public ResponseEntity<IMECode> py2(@PathVariable String text, @PathVariable String param) {
		IMECode imecode = new IMECode();		
		if(param.equals("Full")){
			imecode.setFlag(1|4);
		}
		else{
			imecode.setFlag(1);
		}	
		imecode.setText(text);
		return ImeConvert(imecode);
	}
}

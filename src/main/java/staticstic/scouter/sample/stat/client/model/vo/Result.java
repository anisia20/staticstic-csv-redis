package staticstic.scouter.sample.stat.client.model.vo;

import java.util.HashMap;
import java.util.Map;

import staticstic.scouter.sample.stat.client.code.ResultCode;

public class Result extends HashMap<String, Object> {
	public void onSuccess() {
		ResponseVo vo = new ResponseVo();
		vo.setRslt(ResultCode.R_1000.rslt);
		vo.setRsltDesc(ResultCode.R_1000.rsltDesc);
		setResult(vo);
	}
	
	public void onFail() {
		ResponseVo vo = new ResponseVo();
		vo.setRslt(ResultCode.R_2900.rslt);
		vo.setRsltDesc(ResultCode.R_2900.rsltDesc);
		setResult(vo);
	}
	
	public void onFail(ResultCode code) {
		ResponseVo vo = new ResponseVo();
		vo.setRslt(code.rslt);
		vo.setRsltDesc(code.rsltDesc);
		setResult(vo);
	}
	
	public void onFail(ResultCode code, Object data) {
		ResponseVo vo = new ResponseVo();
		vo.setRslt(code.rslt);
		vo.setRsltDesc(code.rsltDesc);
		setResult(vo);
		setData(data);
	}
	
	public void onSuccess(Object data) {
		onSuccess();
		setData(data);
	}
	
	public void onFail(Object data) {
		onFail();
		setData(data);
	}
	
	public void setResult(ResponseVo vo) {
		this.put("rslt",vo.getRslt());
		this.put("rsltDesc",vo.getRsltDesc());
	}
	
	public void setData(Object data) {
		this.put("data",data);
	}
	
	public void setData(String key, Object data) {
		this.put(key,data);
	}
	
	public void setResultAndData(Map data) {
		this.putAll(data);
	}
}

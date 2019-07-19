package com.dawn.maple.common;

import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

/**
 * @author Dawn
 * 2019-04-02
 */
@Data
public class FinalResult {

    public static final  int SUCCESS_CODE = 200;
    public static final  int FAIL_CODE = 500;
    public static final  int FAIL_PARAM_CODE = 400;
    public static final  int FAIL_BUSS_CODE = 401;
    public static final  int FAIL_DB_CODE = 402;
    
    private int status;
    private String message;
    private Object data;

    private FinalResult(){

        this.status = SUCCESS_CODE;

        this.message = "";
    }

    public static FinalResult create(String message,Object data,int status){
        FinalResult result = new FinalResult();
        result.setMessage(message);
        result.setData(data);
        result.setStatus(status);
        return result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setFail(){
        this.status = FinalResult.FAIL_CODE;
    }

    public void setParamFail(){
        this.status = FinalResult.FAIL_PARAM_CODE;
    }

    public void setBussinessFail(){
        this.status = FinalResult.FAIL_BUSS_CODE;
    }

    public void setDbFail(){
        this.status = FinalResult.FAIL_DB_CODE;
    }

    public void setSuccess(){
        this.status = FinalResult.SUCCESS_CODE;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return JsonData.toJSONString(this, new SerializerFeature[]{SerializerFeature.WriteDateUseDateFormat});
	}
    
}

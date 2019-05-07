package com.jt.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class SysResult {
	private Integer status;
	private String msg;
	private Object data;
	
	public SysResult() {
		super();
	}

	public SysResult(Integer status, String msg, Object data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}
	
	//定义成功的静态方法,
	public static SysResult ok(String msg,Object data) {
		return new SysResult(200,msg,data);
	}
	
	public static SysResult ok() {
		return new SysResult(200,null,null);
	}
	
	public static SysResult ok(Object data) {
		return new SysResult(200,null,data);
	}
	
	//定义失败的静态方法
	public static SysResult fail(String msg,Object data) {
		return new SysResult(201, msg, data);
	}
	
	public static SysResult fail() {
		return new SysResult(201, null, null);
	}

	
}

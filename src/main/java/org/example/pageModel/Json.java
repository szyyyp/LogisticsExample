package org.example.pageModel;

/**
 * 
 * JSON模型
 * 用户后台向前台返回的JSON对象
 * 作者： szy
 * 日期： 2012-4-24
 */
public class Json implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8949941320355528929L;

	private boolean success = false;

	private String msg = "";

	private Integer id;

	private Object obj = null;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}

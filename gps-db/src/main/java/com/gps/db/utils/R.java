
package com.gps.db.utils;

import com.gps.db.dbutils.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
@ToString
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class R<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private int code;
	private String msg;
	private T data;

	public R() {
		code = CommonConstants.SUCCESS;
		msg = "success";
	}

	public R(T data) {
		this();
		this.data = data;
	}

	public R(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public R(Throwable e) {
		super();
		this.msg = e.getMessage();
		this.code = CommonConstants.FAIL;
	}
	
	public static <T> R<T> error(int code, String msg) {
		return new R<>(code, msg);
	}

//	public static R ok(Map<String, Object> map) {
//		R r = new R();
//		r.putAll(map);
//		return r;
//	}

	public static <T> R<T> error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}

	public static <T> R<T> error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}
	public static <T> R<T> ok(T map) {
		return new R<>(map);
	}

	public static <T> R<T> ok() {
		return new R<>();
	}

	public static R<Map<String, Object>> mapOk() {
		return new R<>();
	}

	public R<T> put(String key, Object value) {
		Map<String, Object> map;
		if (data != null && data instanceof Map) {
			map = (Map) data;
		} else {
			map = new HashMap<>();
			setData((T) map);
		}
		map.put(key, value);
		return this;
	}

}

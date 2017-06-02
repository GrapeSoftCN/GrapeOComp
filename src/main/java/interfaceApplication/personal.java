package interfaceApplication;

import java.io.FileInputStream;
import java.util.Properties;

import org.json.simple.JSONObject;

import apps.appsProxy;
import esayhelper.JSONHelper;
import esayhelper.jGrapeFW_Message;
import nlogger.nlogger;

public class personal {
	// 新增人员信息
	public String PersonAdd(String Info) {
		int code = 99;
		JSONObject object = JSONHelper.string2json(Info);
		if (object != null) {
			try {
				String info = appsProxy
						.proxyCall(callhost(), appsProxy.appid() + "/16/user/AddLeader/" + Info, null, "").toString();
				long codes = (long) JSONHelper.string2json(info).get("errorcode");
				code = Integer.parseInt(String.valueOf(codes));
			} catch (Exception e) {
				nlogger.logout(e);
				code = 99;
			}
		}
		return resultmessage(code, "人员信息新增成功");
	}

	// 删除人员信息
	public String PersonDelete(String _id) {
		int code = 99;
		try {
			String info = appsProxy.proxyCall(callhost(), appsProxy.appid() + "/16/user/UserDelect/" + _id, null, "")
					.toString();
			long codes = (long) JSONHelper.string2json(info).get("errorcode");
			code = Integer.parseInt(String.valueOf(codes));
		} catch (Exception e) {
			nlogger.logout(e);
			code = 99;
		}
		return resultmessage(code, "人员信息删除成功");
	}

	// 批量删除人员信息
	public String PersonBatchDelete(String ids) {
		int code = 99;
		try {
			String info = appsProxy
					.proxyCall(callhost(), appsProxy.appid() + "/16/user/UserBatchDelect/" + ids, null, "").toString();
			long codes = (long) JSONHelper.string2json(info).get("errorcode");
			code = Integer.parseInt(String.valueOf(codes));
		} catch (Exception e) {
			nlogger.logout(e);
			code = 99;
		}
		return resultmessage(code, "人员信息批量删除成功");
	}

	// 分页
	public String PersonPage(int ids, int pageSize) {
		String info = null;
		try {
			info = appsProxy.proxyCall(callhost(),
					appsProxy.appid() + "/16/user/UserPage/int:" + ids + "/int:" + pageSize, null, "").toString();
		} catch (Exception e) {
			nlogger.logout(e);
			info = null;
		}
		return info != null ? info : resultmessage(0, "");
	}

	// 按条件分页（条件格式 [k:v]）
	public String PersonPageBy(int ids, int pageSize, String json) {
		String info = null;
		if (JSONHelper.string2json(json) != null) {
			try {
				info = appsProxy.proxyCall(callhost(),
						appsProxy.appid() + "/16/user/UserPageBy/int:" + ids + "/int:" + pageSize + "/s:" + json, null,
						"").toString();
			} catch (Exception e) {
				nlogger.logout(e);
				info = null;
			}
		}
		return info != null ? info : resultmessage(0, "");
	}

	// 修改人员信息
	public String PersonUpdate(String id, String info) {
		int code = 99;
		if (JSONHelper.string2json(info) != null) {
			try {
				String msg = appsProxy
						.proxyCall("123.57.214.226:801",
								String.valueOf(appsProxy.appid()) + "/16/user/UserEdit/s:" + id + "/s:" + info, null, "")
						.toString();
				long codes = (long) JSONHelper.string2json(msg).get("errorcode");
				code = Integer.parseInt(String.valueOf(codes));
			} catch (Exception e) {
				nlogger.logout(e);
				code = 99;
			}
		}
		return resultmessage(code, "人员信息修改成功");
	}

	private String callhost() {
		return getAppIp("host").split("/")[0];
	}

	private String getAppIp(String key) {
		String value = "";
		try {
			Properties pro = new Properties();
			pro.load(new FileInputStream("URLConfig.properties"));
			value = pro.getProperty(key);
		} catch (Exception e) {
			value = "";
		}
		return value;
	}

	private String resultmessage(int num, String message) {
		String msg = "";
		switch (num) {
		case 0:
			msg = message;
			break;
		default:
			msg = "其它异常";
			break;
		}
		return jGrapeFW_Message.netMSG(num, msg);
	}
}

package interfaceApplication;

import java.io.FileInputStream;
import java.util.Properties;

import apps.appsProxy;
import esayhelper.JSONHelper;
import esayhelper.jGrapeFW_Message;
import nlogger.nlogger;

public class Ognization {
	// 新增组织机构信息,返回新增的组织结构信息
	public String OrganAdd(String Info) {
		String info = appsProxy.proxyCall(callhost(), appsProxy.appid() + "/16/roles/RoleInsert/" + Info, null, "")
				.toString();
		long code = (long) JSONHelper.string2json(info).get("errorcode");
		return resultmessage(Integer.parseInt(String.valueOf(code)), "新增组织机构成功");
	}

	// 删除组织机构信息
	public String OrganDelete(String _id) {
		String info = appsProxy.proxyCall(callhost(), appsProxy.appid() + "/16/roles/RoleDelete/" + _id, null, "")
				.toString();
		long code = (long) JSONHelper.string2json(info).get("errorcode");
		return resultmessage(Integer.parseInt(String.valueOf(code)), "组织机构删除成功");
	}

	// 批量删除组织机构信息
	public String OrganBatchDelete(String ids) {
		String info = appsProxy.proxyCall(callhost(), appsProxy.appid() + "/16/roles/RoleBatchDelete/" + ids, null, "")
				.toString();
		long code = (long) JSONHelper.string2json(info).get("errorcode");
		return resultmessage(Integer.parseInt(String.valueOf(code)), "组织机构批量删除成功");
	}

	// 分页
	public String OrganPage(int ids, int pageSize) {
		String info = appsProxy.proxyCall(callhost(),
				appsProxy.appid() + "/16/roles/RolePage/int:" + ids + "/int:" + pageSize, null, "").toString();
		return info;
	}

	// 按条件分页（条件格式 [k:v]）
	public String OrganPageBy(int ids, int pageSize, String json) {
		String info = null;
		if (JSONHelper.string2json(json) != null) {
			try {
				info = appsProxy.proxyCall(callhost(),
						appsProxy.appid() + "/16/roles/RolePageBy/int:" + ids + "/int:" + pageSize + "/s:" + json, null,
						"").toString();
			} catch (Exception e) {
				nlogger.logout(e);
				info = null;
			}
		}
		return info != null ? info : resultmessage(0, info);
	}

	// 修改组织机构信息
	public String OrganUpdate(String id, String info) {
		int code = 99;
		if (JSONHelper.string2json(info) != null) {
			try {
				String msg = appsProxy.proxyCall(callhost(),
						appsProxy.appid() + "/16/roles/RoleUpdate/s:" + id + "/s:" + info, null, "").toString();
				long codes = (long) JSONHelper.string2json(msg).get("errorcode");
				code = Integer.parseInt(String.valueOf(codes));
			} catch (Exception e) {
				nlogger.logout(e);
				code = 99;
			}
		}
		return resultmessage(code, "组织机构修改成功");
	}

	// 设置上级机构
	public String OgSetFatherid(String id, String fatherid) {
		int code = 99;
		try {
			String msg = appsProxy.proxyCall(callhost(),
					appsProxy.appid() + "/16/roles/RoleSetFatherId/s:" + id + "/s:" + fatherid, null, "").toString();
			long codes = (long) JSONHelper.string2json(msg).get("errorcode");
			code = Integer.parseInt(String.valueOf(codes));
		} catch (Exception e) {
			nlogger.logout(e);
			code = 99;
		}
		return resultmessage(code, "上级组织机构设置成功");
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

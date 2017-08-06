package interfaceApplication;

import org.json.simple.JSONObject;

import JGrapeSystem.jGrapeFW_Message;
import apps.appsProxy;
import json.JSONHelper;
import nlogger.nlogger;

public class personal {
	// 新增人员信息
	public String PersonAdd(String Info) {
		int code = 99;
		JSONObject object = JSONHelper.string2json(Info);
		if (object != null) {
			try {
				String info = appsProxy
						.proxyCall("/GrapeUser/user/AddLeader/" + Info, null, "").toString();
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
			String info = appsProxy.proxyCall("/GrapeUser/user/UserDelect/" + _id)
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
					.proxyCall("/GrapeUser/user/UserBatchDelect/" + ids, null, "").toString();
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
			info = appsProxy.proxyCall("/GrapeUser/user/UserPage/int:" + ids + "/int:" + pageSize).toString();
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
				info = appsProxy.proxyCall("/GrapeUser/user/UserPageBy/int:" + ids + "/int:" + pageSize + "/s:" + json).toString();
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
						.proxyCall("/GrapeUser/user/UserEdit/s:" + id + "/s:" + info, null, "")
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

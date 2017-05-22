package interfaceApplication;

import org.json.simple.JSONObject;

import apps.appsProxy;
import esayhelper.JSONHelper;
import esayhelper.jGrapeFW_Message;

public class personal {
	// 新增人员信息
	public String PersonAdd(String Info) {
		JSONObject object = JSONHelper.string2json(Info);
		if (!object.containsKey("ownid")) {
			return resultmessage(1, "");
		}
		String info = appsProxy
				.proxyCall("123.57.214.226:801",
						String.valueOf(appsProxy.appid())
								+ "/16/user/AddLeader/" + Info,
						null, "")
				.toString();
		long code = (long) JSONHelper.string2json(info).get("errorcode");
		return resultmessage(Integer.parseInt(String.valueOf(code)),
				"人员信息新增成功");
	}

	// 删除人员信息
	public String PersonDelete(String _id) {
		String info = appsProxy
				.proxyCall("123.57.214.226:801",
						String.valueOf(appsProxy.appid())
								+ "/16/user/UserDelect/" + _id,
						null, "")
				.toString();
		long code = (long) JSONHelper.string2json(info).get("errorcode");
		return resultmessage(Integer.parseInt(String.valueOf(code)),
				"人员信息删除成功");
	}

	// 批量删除人员信息
	public String PersonBatchDelete(String ids) {
		String info = appsProxy
				.proxyCall("123.57.214.226:801",
						String.valueOf(appsProxy.appid())
								+ "/16/user/UserBatchDelect/" + ids,
						null, "")
				.toString();
		long code = (long) JSONHelper.string2json(info).get("errorcode");
		return resultmessage(Integer.parseInt(String.valueOf(code)),
				"人员信息批量删除成功");
	}

	// 分页
	public String PersonPage(int ids, int pageSize) {
		String info = appsProxy.proxyCall(
				"123.57.214.226:801", String.valueOf(appsProxy.appid())
						+ "/16/user/UserPage/int:" + ids + "/int:" + pageSize,
				null, "").toString();
		return info;
	}

	// 按条件分页（条件格式 [k:v]）
	public String PersonPageBy(int ids, int pageSize, String json) {
		String info = appsProxy.proxyCall("123.57.214.226:801",
				String.valueOf(appsProxy.appid()) + "/16/user/UserPageBy/int:"
						+ ids + "/int:" + pageSize + "/s:" + json,
				null, "").toString();
		return info;
	}

	// 修改人员信息
	public String PersonUpdate(String id, String info) {
		String msg = appsProxy
				.proxyCall("123.57.214.226:801",
						String.valueOf(appsProxy.appid())
								+ "/16/user/UserEdit/s:" + id + "/s:" + info,
						null, "")
				.toString();
		long code = (long) JSONHelper.string2json(msg).get("errorcode");
		return resultmessage(Integer.parseInt(String.valueOf(code)),
				"人员信息修改成功");
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

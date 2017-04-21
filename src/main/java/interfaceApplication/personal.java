package interfaceApplication;

import org.json.simple.JSONObject;

import esayhelper.JSONHelper;
import esayhelper.jGrapeFW_Message;
import rpc.execRequest;

public class personal {
	// 新增人员信息
	public String PersonAdd(String Info) {
		JSONObject object = JSONHelper.string2json(Info);
		if (!object.containsKey("ownid")) {
			return resultmessage(1, "");
		}
		String info = execRequest._run("GrapeUser/user/UserRegister/" + Info, null).toString();
		int code = (int) JSONHelper.string2json(info).get("errorcode");
		return resultmessage(code, "人员信息新增成功");
	}

	// 删除人员信息
	public String OrganDelete(String _id) {
		String info = execRequest._run("GrapeUser/user/userDelete/" + _id, null).toString();
		int code = (int) JSONHelper.string2json(info).get("errorcode");
		return resultmessage(code, "人员信息删除成功");
	}

	// 批量删除人员信息
	public String OrganBatchDelete(String ids) {
		String info = execRequest._run("GrapeUser/user/userBatchDelete/" + ids, null).toString();
		int code = (int) JSONHelper.string2json(info).get("errorcode");
		return resultmessage(code, "人员信息批量删除成功");
	}

	// 分页(需绑定ownid)
	public String OrganPage(int ids, int pageSize, String ownid) {
		String info = execRequest._run("GrapeUser/roles/Page/int:" + ids + "/int:" + pageSize + "/s:" + ownid, null)
				.toString();
		return info;
	}

	// 按条件分页（条件格式 [k:v]）
	public String OrganPageBy(int ids, int pageSize, String json, String ownid) {
		String info = execRequest
				._run("GrapeUser/roles/PageBy/int:" + ids + "/int:" + pageSize + "/s:" + json + "/s:" + ownid, null)
				.toString();
		return info;
	}

	// 修改人员信息
	public String OCompUpdate(String id, String info) {
		String msg = execRequest._run("", null).toString();
		int code = (int) JSONHelper.string2json(msg).get("errorcode");
		return resultmessage(code, "人员信息修改成功");
	}

	private String resultmessage(int num, String message) {
		String msg = "";
		switch (num) {
		case 0:
			msg = message;
			break;
		case 1:
			msg = "新增失败，未设置ownid";
			break;

		default:
			msg = "其它异常";
			break;
		}
		return jGrapeFW_Message.netMSG(num, msg);
	}
}

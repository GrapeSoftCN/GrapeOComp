package interfaceApplication;

import esayhelper.JSONHelper;
import esayhelper.jGrapeFW_Message;
import rpc.execRequest;

public class Ognization {
	// 新增组织机构信息
	public String OrganAdd(String Info) {
		String info = execRequest._run("GrapeUser/roles/RoleInsert/" + Info, null).toString();
		long code = (long) JSONHelper.string2json(info).get("errorcode");
		return resultmessage(Integer.parseInt(String.valueOf(code)), "组织机构新增成功");
	}

	// 删除组织机构信息
	public String OrganDelete(String _id) {
		String info = execRequest._run("GrapeUser/roles/RoleDelete/" + _id, null).toString();
		long code = (long) JSONHelper.string2json(info).get("errorcode");
		return resultmessage(Integer.parseInt(String.valueOf(code)), "组织机构删除成功");
	}

	// 批量删除组织机构信息
	public String OrganBatchDelete(String ids) {
		String info = execRequest._run("GrapeUser/roles/RoleBatchDelete/" + ids, null).toString();
		int code = (int) JSONHelper.string2json(info).get("errorcode");
		return resultmessage(code, "组织机构批量删除成功");
	}

	// 分页
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

	// 修改组织机构信息
	public String OrganUpdate(String id, String info) {
		String msg = execRequest._run("", null).toString();
		int code = (int) JSONHelper.string2json(msg).get("errorcode");
		return resultmessage(code, "组织机构修改成功");
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

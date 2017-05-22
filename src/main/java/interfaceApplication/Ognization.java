package interfaceApplication;

import apps.appsProxy;
import esayhelper.JSONHelper;
import esayhelper.jGrapeFW_Message;

public class Ognization {
	// 新增组织机构信息,返回新增的组织结构信息
	public String OrganAdd(String Info) {
		String info = appsProxy
				.proxyCall("123.57.214.226:801",
						String.valueOf(appsProxy.appid())
								+ "/16/roles/RoleInsert/" + Info,
						null, "")
				.toString();
		long code = (long) JSONHelper.string2json(info).get("errorcode");
		return resultmessage(Integer.parseInt(String.valueOf(code)),
				"新增组织机构成功");
	}

	// 删除组织机构信息
	public String OrganDelete(String _id) {
		String info = appsProxy
				.proxyCall("123.57.214.226:801",
						String.valueOf(appsProxy.appid())
								+ "/16/roles/RoleDelete/" + _id,
						null, "")
				.toString();
		long code = (long) JSONHelper.string2json(info).get("errorcode");
		return resultmessage(Integer.parseInt(String.valueOf(code)),
				"组织机构删除成功");
	}

	// 批量删除组织机构信息
	public String OrganBatchDelete(String ids) {
		String info = appsProxy
				.proxyCall("123.57.214.226:801",
						String.valueOf(appsProxy.appid())
								+ "/16/roles/RoleBatchDelete/" + ids,
						null, "")
				.toString();
		long code = (long) JSONHelper.string2json(info).get("errorcode");
		return resultmessage(Integer.parseInt(String.valueOf(code)),
				"组织机构批量删除成功");
	}

	// 分页
	public String OrganPage(int ids, int pageSize) {
		String info = appsProxy.proxyCall(
				"123.57.214.226:801", String.valueOf(appsProxy.appid())
						+ "/16/roles/RolePage/int:" + ids + "/int:" + pageSize,
				null, "").toString();
		return info;
	}

	// 按条件分页（条件格式 [k:v]）
	public String OrganPageBy(int ids, int pageSize, String json) {
		String info = appsProxy.proxyCall("123.57.214.226:801",
				String.valueOf(appsProxy.appid()) + "/16/roles/RolePageBy/int:"
						+ ids + "/int:" + pageSize + "/s:" + json,
				null, "").toString();
		return info;
	}

	// 修改组织机构信息
	public String OrganUpdate(String id, String info) {
		String msg = appsProxy.proxyCall(
				"123.57.214.226:801", String.valueOf(appsProxy.appid())
						+ "/16/roles/RoleUpdate/s:" + id + "/s:" + info,
				null, "").toString();
		long code = (long) JSONHelper.string2json(msg).get("errorcode");

		return resultmessage(Integer.parseInt(String.valueOf(code)),
				"组织机构修改成功");
	}

	// 设置上级机构
	public String OgSetFatherid(String id, String fatherid) {
		String msg = appsProxy.proxyCall("123.57.214.226:801",
				String.valueOf(appsProxy.appid())
						+ "/16/roles/RoleSetFatherId/s:" + id + "/s:"
						+ fatherid,
				null, "").toString();
		long code = (long) JSONHelper.string2json(msg).get("errorcode");
		return resultmessage(Integer.parseInt(String.valueOf(code)),
				"上级组织机构设置成功");
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

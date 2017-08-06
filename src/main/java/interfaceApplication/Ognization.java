package interfaceApplication;

import java.io.FileInputStream;
import java.util.Properties;

import JGrapeSystem.jGrapeFW_Message;
import apps.appsProxy;
import json.JSONHelper;
import nlogger.nlogger;

public class Ognization {
	// 新增组织机构信息,返回新增的组织结构信息
	public String OrganAdd(String Info) {
		String info = appsProxy.proxyCall("/GrapeUser/roles/RoleInsert/" + Info)
				.toString();
		long code = (long) JSONHelper.string2json(info).get("errorcode");
		return resultmessage(Integer.parseInt(String.valueOf(code)), "新增组织机构成功");
	}

	// 删除组织机构信息
	public String OrganDelete(String _id) {
		String info = appsProxy.proxyCall("/GrapeUser/roles/RoleDelete/" + _id)
				.toString();
		long code = (long) JSONHelper.string2json(info).get("errorcode");
		return resultmessage(Integer.parseInt(String.valueOf(code)), "组织机构删除成功");
	}

	// 批量删除组织机构信息
	public String OrganBatchDelete(String ids) {
		String info = appsProxy.proxyCall("/GrapeUser/roles/RoleBatchDelete/" + ids)
				.toString();
		long code = (long) JSONHelper.string2json(info).get("errorcode");
		return resultmessage(Integer.parseInt(String.valueOf(code)), "组织机构批量删除成功");
	}

	// 分页
	public String OrganPage(int ids, int pageSize) {
		String info = appsProxy.proxyCall("/GrapeUser/16/roles/RolePage/int:" + ids + "/int:" + pageSize).toString();
		return info;
	}

	// 按条件分页（条件格式 [k:v]）
	public String OrganPageBy(int ids, int pageSize, String json) {
		String info = null;
		if (JSONHelper.string2json(json) != null) {
			try {
				info = appsProxy.proxyCall("/GrapeUser/16/roles/RolePageBy/int:" + ids + "/int:" + pageSize + "/s:" + json).toString();
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
				String msg = appsProxy.proxyCall("/GrapeUser/16/roles/RoleUpdate/s:" + id + "/s:" + info).toString();
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
			String msg = appsProxy.proxyCall("/GrapeUser/roles/RoleSetFatherId/s:" + id + "/s:" + fatherid).toString();
			long codes = (long) JSONHelper.string2json(msg).get("errorcode");
			code = Integer.parseInt(String.valueOf(codes));
		} catch (Exception e) {
			nlogger.logout(e);
			code = 99;
		}
		return resultmessage(code, "上级组织机构设置成功");
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

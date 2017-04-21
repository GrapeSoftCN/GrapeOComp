package model;

import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import esayhelper.DBHelper;
import esayhelper.JSONHelper;
import esayhelper.formHelper;
import esayhelper.jGrapeFW_Message;
import esayhelper.formHelper.formdef;
import rpc.execRequest;

public class OCompanyModel {
	private static DBHelper comp;
	private static formHelper form;

	static {
		comp = new DBHelper("mongodb", "OperateComp");
		form = comp.getChecker();
	}

	public OCompanyModel() {
		form.putRule("companyName", formdef.notNull);
	}

	// public String addOC(JSONObject object) {
	// if (!form.checkRule(object)) {
	// return resultMessage(1, "");
	// }
	// String info = comp.data(object).insertOnce().toString();
	// return find(info).toString();
	// }
	/**
	 * 修改运行单位信息
	 * 
	 * @param OCID
	 * @param object
	 * @return
	 */
	public int update(String OCID, JSONObject object) {
		int code = 99;
		// 修改基本信息
		if (object.containsKey("ownid")) {
			object.remove("ownid");
		}
		code = comp.eq("_id", new ObjectId(OCID)).data(object).update() != null ? 0 : 99;
		return code;
	}

	@SuppressWarnings("unchecked")
	public JSONObject page(int idx, int pageSize) {
		JSONArray array = comp.page(idx, pageSize);
		JSONObject object = new JSONObject();
//		for (int i = 0; i < array.size(); i++) {
//			JSONObject objects = (JSONObject) array.get(i);
//			// 获取组织机构信息
//			JSONArray organ = getOgnization(objects);
//			if (organ == null) {
//				continue;
//			}
//			// 获取人员信息
//			JSONArray person = getPerson(objects);
//			objects.put("personnel", person);
//			if (person == null) {
//				continue;
//			}
//			objects.put("organization", organ);
//			arrays.add(objects);
//		}
		object.put("totalSize", (int) Math.ceil((double) comp.count() / pageSize));
		object.put("currentPage", idx);
		object.put("pageSize", pageSize);
		object.put("data", array);
		return object;
	}

	@SuppressWarnings("unchecked")
	public JSONObject page(int idx, int pageSize, JSONObject object) {
		for (Object object2 : object.keySet()) {
			if (object.containsKey("_id")) {
				comp.eq("_id", new ObjectId(object.get("_id").toString()));
			}
			comp.eq(object2.toString(), object.get(object2.toString()));
		}
		JSONArray array = comp.page(idx, pageSize);
		JSONObject _obj = new JSONObject();
		_obj.put("totalSize", (int) Math.ceil((double) comp.count() / pageSize));
		_obj.put("currentPage", idx);
		_obj.put("pageSize", pageSize);
		_obj.put("data", array);
		return _obj;
	}

	/**
	 * 获取人员信息
	 * 
	 * @param objects
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getPerson(JSONObject objects) {
		JSONArray msg = null;
		JSONObject oJsonObject = new JSONObject();
		oJsonObject.put("ownid", objects.get("ownid").toString());
		String orString = execRequest._run("GrapeUser/user/UserSearch/s:" + oJsonObject.toString(), null).toString();
		if (orString != null) {
			String message = JSONHelper.string2json(orString).get("message").toString();
			String records = JSONHelper.string2json(message).get("records").toString();
			msg = JSONHelper.string2array(records);
		}
		return msg;
	}

	public JSONObject find(String vid) {
		return comp.eq("_id", new ObjectId(vid)).find();
	}

	public String resultMessage(int num, String message) {
		String msg = "";
		switch (num) {
		case 0:
			msg = message;
			break;
		case 1:
			msg = "必填项没有填";
			break;
		default:
			msg = "其它异常";
			break;
		}
		return jGrapeFW_Message.netMSG(num, msg);
	}
}

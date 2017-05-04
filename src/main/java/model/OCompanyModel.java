package model;

import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import esayhelper.DBHelper;
import esayhelper.formHelper;
import esayhelper.jGrapeFW_Message;
import esayhelper.formHelper.formdef;

public class OCompanyModel {
	private static DBHelper comp;
	private static formHelper form;
	private JSONObject _obj = new JSONObject();

	static {
		comp = new DBHelper("mongodb", "OperateComp");
		form = comp.getChecker();
	}

	public OCompanyModel() {
		form.putRule("companyName", formdef.notNull);
	}

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
		code = comp.eq("_id", new ObjectId(OCID)).data(object).update() != null
				? 0 : 99;
		return code;
	}

	@SuppressWarnings("unchecked")
	public JSONObject page(int idx, int pageSize) {
		JSONArray array = comp.page(idx, pageSize);
		JSONObject object = new JSONObject();
		object.put("totalSize",
				(int) Math.ceil((double) comp.count() / pageSize));
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
		_obj.put("totalSize",
				(int) Math.ceil((double) comp.count() / pageSize));
		_obj.put("currentPage", idx);
		_obj.put("pageSize", pageSize);
		_obj.put("data", array);
		return _obj;
	}

	public JSONObject find(String vid) {
		return comp.eq("_id", new ObjectId(vid)).find();
	}

	@SuppressWarnings("unchecked")
	public String resultMessage(JSONObject object) {
		_obj.put("records", object);
		return resultMessage(0, _obj.toString());
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
		case 2:
			msg = "没有修改数据权限，请联系管理员进行权限调整";
			break;
		default:
			msg = "其它异常";
			break;
		}
		return jGrapeFW_Message.netMSG(num, msg);
	}
}

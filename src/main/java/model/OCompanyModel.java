package model;

import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import apps.appsProxy;
import database.db;
import esayhelper.DBHelper;
import esayhelper.formHelper;
import esayhelper.jGrapeFW_Message;
import esayhelper.formHelper.formdef;
import nlogger.nlogger;

public class OCompanyModel {
	private static DBHelper comp;
	private static formHelper form;
	private JSONObject _obj = new JSONObject();

	static {
		comp = new DBHelper(appsProxy.configValue().get("db").toString(), "OperateComp");
		form = comp.getChecker();
	}

	public OCompanyModel() {
		form.putRule("companyName", formdef.notNull);
	}

	private db bind() {
		return comp.bind(String.valueOf(appsProxy.appid()));
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
		if (object != null) {
			// 修改基本信息
			if (object.containsKey("ownid")) {
				object.remove("ownid");
			}
			code = bind().eq("_id", new ObjectId(OCID)).data(object).update() != null ? 0 : 99;
		}
		return code;
	}

	@SuppressWarnings("unchecked")
	public String page(int idx, int pageSize) {
		JSONObject object = null;
		try {
			object = new JSONObject();
			JSONArray array = bind().page(idx, pageSize);
			object.put("totalSize", (int) Math.ceil((double) bind().count() / pageSize));
			object.put("currentPage", idx);
			object.put("pageSize", pageSize);
			object.put("data", array);
		} catch (Exception e) {
			nlogger.logout(e);
			object = null;
		}
		return resultMessage(object);
	}

	@SuppressWarnings("unchecked")
	public String page(int idx, int pageSize, JSONObject object) {
		JSONObject _obj = null;
		if (object != null) {
			try {
				for (Object object2 : object.keySet()) {
					if (object.containsKey("_id")) {
						bind().eq("_id", new ObjectId(object.get("_id").toString()));
					}
					bind().eq(object2.toString(), object.get(object2.toString()));
				}
				JSONArray array = bind().dirty().page(idx, pageSize);
				_obj = new JSONObject();
				_obj.put("totalSize", (int) Math.ceil((double) bind().count() / pageSize));
				_obj.put("currentPage", idx);
				_obj.put("pageSize", pageSize);
				_obj.put("data", array);
			} catch (Exception e) {
				nlogger.logout(e);
				_obj = null;
			}
		}
		return resultMessage(_obj);
	}

	public JSONObject find(String vid) {
		return bind().eq("_id", new ObjectId(vid)).find();
	}

	@SuppressWarnings("unchecked")
	private String resultMessage(JSONObject object) {
		if (object == null) {
			object = new JSONObject();
		}
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

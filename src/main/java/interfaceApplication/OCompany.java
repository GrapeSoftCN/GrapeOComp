package interfaceApplication;

import json.JSONHelper;
import model.OCompanyModel;

public class OCompany {
	private OCompanyModel model = new OCompanyModel();

	// 分页
	public String OCompPage(int ids, int pageSize) {
		return model.page(ids, pageSize);
	}

	// 按条件分页（条件格式 [k:v]）
	public String OCompPageBy(int ids, int pageSize, String json) {
		return model.page(ids, pageSize, JSONHelper.string2json(json));
	}

	// 修改运行单位信息
	public String OCompUpdate(String id, String info) {
		return model.resultMessage(model.update(id, JSONHelper.string2json(info)), "修改成功");
	}
}

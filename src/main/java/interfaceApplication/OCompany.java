package interfaceApplication;

import esayhelper.JSONHelper;
import model.OCompanyModel;
import rpc.execRequest;

public class OCompany {
	private OCompanyModel model = new OCompanyModel();
	private String userid;

	public OCompany() {
		userid = execRequest.getChannelValue("Userid").toString();
	}

	// 分页
	public String OCompPage(int ids, int pageSize) {
		return model.resultMessage(model.page(ids, pageSize));
	}

	// 按条件分页（条件格式 [k:v]）
	public String OCompPageBy(int ids, int pageSize, String json) {
		return model.resultMessage(
				model.page(ids, pageSize, JSONHelper.string2json(json)));
	}

	// 修改运行单位信息
	public String OCompUpdate(String id, String info) {
		String uPLV = model.find(id).get("uplv").toString();
		String tip = execRequest
				._run("GrapeAuth/Auth/UpdatePLV/s:" + uPLV + "/s:" + userid,
						null)
				.toString();
		if (!"0".equals(tip)) {
			return model.resultMessage(4, "没有编辑权限");
		}
		return model.resultMessage(
				model.update(id, JSONHelper.string2json(info)), "修改成功");
	}
}

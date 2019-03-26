package service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoTable;

import common.util.SapJco3Conn;
import dao.IMaterielDao;
import pojo.Materiel;
import pojo.Operation;
import service.materiel.IMaterielService;

@Service("taskJob")
public class TaskJob {

	protected static Logger logger = Logger.getLogger(TaskJob.class);

	private static final String[] materiels = { "Z001", "Z004", "Z005", "Z006" };

	@Autowired
	IMaterielDao materielDao;

	@Autowired
	IMaterielService materielService;

	// 文件根目录
	@Value("#{config.RootFilePath}")
	private String RootFilePath;

	// 物料接口名称
	@Value("#{config.MATERIAL_API_NAME}")
	private String materielSap;

	private static final String[] ss = { "研发文件", "质量文件", "采购文件", "安全性文件", "市场及技术文件", "规格文件" };

	private static final DateFormat nameDf = new SimpleDateFormat("yyyyMMdd");

	public void job() {
		logger.debug(">>>>>>>>>>start TaskJob.job");
		// 请求sap日期参数先指定
		Date current = new Date();

		// 4种材料目录集
		Map<String, List<String>> mtrDirs = new HashMap<String, List<String>>();

		// 组装目录
		getDirs(mtrDirs);

		// SAP接口
		JCoDestination dest;
		try {
			dest = SapJco3Conn.getDestination();

			// 获取连接
			JCoRepository repository = dest.getRepository();
			JCoFunction fm = repository.getFunction(materielSap);
			if (fm == null) {
				throw new RuntimeException("Function does not exists in SAP system.");
			}

			/** 1、普通传参 **/

			// 传参
			fm.getImportParameterList().setValue("I_DATE", nameDf.format(current)); // 定时器获取当天

			// 执行
			fm.execute(dest);
			/** 1、获取返回的结果 **/
			JCoParameterList exportParam = fm.getExportParameterList();
			String eType = exportParam.getString("E_TYPE");
			String eMsg = exportParam.getString("E_MSG");
			logger.info("返回结果：" + eType + " msg:" + eMsg);
			/**
			 * 2、获取以Table形式返回的结果
			 */
			JCoTable etTable = fm.getTableParameterList().getTable("ET_MARA");
			for (int i = 0; i < etTable.getNumRows(); i++) {
				etTable.setRow(i);
				// 编码
				String mATNR = etTable.getString("MATNR").substring(etTable.getString("MATNR").length() - 8);
				logger.debug("code mATNR: " + mATNR);
				// 类型
				String MTART = etTable.getString("MTART");
				Materiel m = new Materiel();
				m.setCode(mATNR);
				m.setType(MTART);
				m.setDscp(etTable.getString("MAKTX"));
				m.setGrp(etTable.getString("MATKL"));
				m.setGrpdesc(etTable.getString("WGBEZ"));
				m.setMark(etTable.getString("LVORM"));
				
				materielDao.delMaterielSync(mATNR);
				materielService.synchronizeMateriels(m);

				// 建目录
				// 一级目录
				new File(RootFilePath, mATNR).mkdirs();
				List<String> dirs = mtrDirs.get("Z00" + mATNR.substring(0, 1));
				if (null != dirs) {
					for (String s : dirs) {
						new File(RootFilePath + "/" + mATNR + "/" + s).mkdirs();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug(">>>>>>>>>>end TaskJob.job");
	}

	// 获取每种类型对应的目录结构
	private void getDirs(Map<String, List<String>> dirs) {
		for (String s : materiels) {
			List<String> ss = new ArrayList<String>();
			// 找到一级目录的权限id
			List<Operation> secDirs = materielDao.findSonDir(0, s);

			for (Operation op : secDirs) { // 二级目录
				logger.debug("secDirs:" + secDirs + ",optitle:" + op.getTitle());
				ss.add("/" + op.getTitle());

				List<Operation> thrDirs = materielDao.findSonDir(op.getId(), null);
				for (Operation op2 : thrDirs) { // 三级目录
					ss.add("/" + op.getTitle() + "/" + op2.getTitle());
				}
			}
			dirs.put(s, ss);
		}
	}
}

package common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;

public class SapJco3Conn implements DestinationDataProvider {
	private File dir;// 配置文件
	private String fileName = "ECC.jcoDestination"; // 自定义的fileName,不变

	public void setDestinationFile(File dir) {
		this.dir = dir;
	}

	private Properties loadProperties(File dir) throws IOException {
		Properties props = null;

		// create a file with name: fullName in destDirectory
		File destFile = new File(dir, this.fileName);

		if (destFile.exists()) {
			FileInputStream fInputStream = new FileInputStream(destFile);
			props = new Properties();
			props.load(fInputStream);
			fInputStream.close();
		} else {
			throw new RuntimeException("Destination file does not exist.");
		}

		return props;
	}

	@Override
	public Properties getDestinationProperties(String destName) {
		Properties props = null;

		try {
			props = this.loadProperties(this.dir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return props;
	}

	@Override
	public void setDestinationDataEventListener(DestinationDataEventListener listener) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean supportsEvents() {
		return false;
	}

	public static JCoDestination getDestination() throws JCoException {
		// File directory = new File("./"); // current directory;
		File directory = new File(SapJco3Conn.class.getResource("").getPath() + "../../../");

		SapJco3Conn destDataProvider = new SapJco3Conn();
		destDataProvider.setDestinationFile(directory);
		if (!Environment.isDestinationDataProviderRegistered()) {
			Environment.registerDestinationDataProvider(destDataProvider);
		}
		JCoDestination dest = JCoDestinationManager.getDestination("ECC");

		return dest;
	}
}

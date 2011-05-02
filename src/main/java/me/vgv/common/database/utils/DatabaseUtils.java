package me.vgv.common.database.utils;

import com.google.common.io.CharStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class DatabaseUtils {

	private static final Logger log = LoggerFactory.getLogger(DatabaseUtils.class);

	private DatabaseUtils() {
	}

	public static void rollbackConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				log.error("can't rollback transaction", e);
			}
		}
	}

	public static List<String> extractSqlStatementsFromStream(InputStream inputStream) throws IOException {
		List<String> result = new ArrayList<String>();

		StringBuilder sb = new StringBuilder(10240);
		for (String line : CharStreams.readLines(new InputStreamReader(inputStream, "UTF-8"))) {
			line = line.trim();
			if (!line.isEmpty() && !line.startsWith("/*")) {
				sb.append(line);
				if (line.endsWith(";")) {
					result.add(sb.toString());
					sb = new StringBuilder(10240);
				}
			}
		}

		if (sb.length() > 0) {
			result.add(sb.toString());
		}

		return result;
	}

}

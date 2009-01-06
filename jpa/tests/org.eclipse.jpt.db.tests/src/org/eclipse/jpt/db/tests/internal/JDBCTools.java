/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.tests.internal;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jpt.utility.internal.iterators.ResultSetIterator;

@SuppressWarnings("nls")
public class JDBCTools {

	public static void dump(Connection connection, String sql) throws SQLException {
		dump(execute(connection, sql));
	}

	public static void dump(ResultSet resultSet) throws SQLException {
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		// synchronize the console so everything is contiguous
		synchronized (System.out) {
			dumpOn(resultSet, pw);
		}
		pw.flush();
	}

	public static void dumpOn(ResultSet resultSet, PrintWriter pw) throws SQLException {
		ArrayList<HashMap<String, Object>> maps = convertToMaps(resultSet);
		for (Iterator<HashMap<String, Object>> mapStream = maps.iterator(); mapStream.hasNext(); ) {
			for (Iterator<Map.Entry<String, Object>> entryStream = mapStream.next().entrySet().iterator(); entryStream.hasNext(); ) {
				Map.Entry<String, Object> entry = entryStream.next();
				pw.print(entry.getKey());
				pw.print(" = ");
				pw.print(entry.getValue());
				pw.println();
			}
			if (mapStream.hasNext()) {
				pw.println();
			}
		}
		pw.println("total rows: " + maps.size());
	}

	public static ArrayList<HashMap<String, Object>> convertToMaps(Connection connection, String sql) throws SQLException {
		return convertToMaps(execute(connection, sql));
	}

	public static ResultSet execute(Connection connection, String sql) throws SQLException {
		Statement statement = connection.createStatement();
		statement.execute(sql);
		ResultSet resultSet = statement.getResultSet();
		statement.close();
		return resultSet;
	}

	public static ArrayList<HashMap<String, Object>> convertToMaps(ResultSet resultSet) throws SQLException {
		ArrayList<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
		for (Iterator<HashMap<String, Object>> stream = buildMapIterator(resultSet); stream.hasNext(); ) {
			rows.add(stream.next());
		}
		return rows;
	}

	public static Iterator<HashMap<String, Object>> buildMapIterator(ResultSet resultSet) throws SQLException {
		return new ResultSetIterator<HashMap<String, Object>>(resultSet, new MapResultSetIteratorAdapter(buildColumnNames(resultSet)));
	}

	public static String[] buildColumnNames(ResultSet resultSet) throws SQLException {
		String[] names = new String[resultSet.getMetaData().getColumnCount()];
		for (int i = 0; i < names.length; i++) {
			names[i] = resultSet.getMetaData().getColumnName(i + 1);  // NB: ResultSet index/subscript is 1-based
		}
		return names;
	}

	public static class MapResultSetIteratorAdapter implements ResultSetIterator.Adapter<HashMap<String, Object>> {
		private final String[] columnNames;
		public MapResultSetIteratorAdapter(String[] columnNames) {
			super();
			this.columnNames = columnNames;
		}
		public HashMap<String, Object> buildNext(ResultSet rs) throws SQLException {
			HashMap<String, Object> map = new HashMap<String, Object>(this.columnNames.length);
			for (int i = 0; i < this.columnNames.length; i++) {
				map.put(this.columnNames[i], rs.getObject(i + 1));  // NB: ResultSet index/subscript is 1-based
			}
			return map;
		}
	}

}

/*******************************************************************************
 * Copyright (c) 2011, 2014 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.jdbc;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@link ResultSet} utility methods.
 */
public class ResultSetTools {
	/**
	 * Return an iterator that returns the first object in each row of the
	 * specified result set. The first object in each row must be of type
	 * {@code <E>}. The iterator will close the result set once it has returned
	 * all its elements.
	 * @see ResultSetIterator
	 */
	@SuppressWarnings("unchecked")
	public static <E> ResultSetIterator<E> iterator(ResultSet resultSet) throws SQLException {
		return (ResultSetIterator<E>) iterator(resultSet, defaultRowTransformer());
	}

	/**
	 * Return a row transformer the returns the first object in a result set's
	 * current row.
	 */
	@SuppressWarnings("unchecked")
	public static <E> ResultSetRowTransformer<E> defaultRowTransformer() {
		return DEFAULT_ROW_TRANSFORMER;
	}

	/**
	 * A row transformer the returns the first object in a result set's
	 * current row.
	 */
	@SuppressWarnings("rawtypes")
	public static final ResultSetRowTransformer DEFAULT_ROW_TRANSFORMER = new DefaultRowTransformer();

	/**
	 * A row transformer the returns the first object in a result set's
	 * current row.
	 */
	public static class DefaultRowTransformer<E>
		extends ResultSetRowTransformerAdapter<E>
	{
		@Override
		@SuppressWarnings("unchecked")
		public E transform(ResultSet rs) throws SQLException {
			// result set columns are indexed starting with 1
			return (E) rs.getObject(1);
		}
	}

	/**
	 * Return an iterator that the returns the objects produced by the specified row
	 * transformer. The transformer will be used to convert each row in the
	 * result set into an object. The iterator will close the result set once
	 * it has returned all its elements.
	 * @see ResultSetIterator
	 */
	public static <E> ResultSetIterator<E> iterator(ResultSet resultSet, ResultSetRowTransformer<? extends E> rowTransformer) throws SQLException {
		return new ResultSetIterator<E>(resultSet, rowTransformer);
	}

	/**
	 * Convert the specified result set into a list of maps, each map
	 * corresponding a result set row and mapping column names to their
	 * corresponding row values. The result set will be closed upon return
	 * from this method.
	 */
	public static ArrayList<Map<String, Object>> convertToMaps(ResultSet resultSet) throws SQLException {
		return convertToList(resultSet, mapRowTransformer(resultSet));
	}

	/**
	 * Convert the specified result set into a list of maps, each map
	 * corresponding a result set row and mapping column names to their
	 * corresponding row values. The result set will be closed upon return
	 * from this method.
	 */
	public static <E> ArrayList<E> convertToList(ResultSet resultSet, ResultSetRowTransformer<? extends E> rowTransformer) throws SQLException {
		ArrayList<E> rows = new ArrayList<E>();
		for (ResultSetIterator<E> stream = iterator(resultSet, rowTransformer); stream.hasNext(); ) {
			rows.add(stream.next());
		}
		return rows;
	}

	/**
	 * Convert the specified result set into an iterator of maps, each map
	 * corresponding a result set row and mapping column names to their
	 * corresponding row values. The iterator will close the result set once
	 * it has returned all its elements.
	 */
	public static ResultSetIterator<Map<String, Object>> mapIterator(ResultSet resultSet) throws SQLException {
		return iterator(resultSet, mapRowTransformer(resultSet));
	}

	/**
	 * Return a result set row transformer that will transform the specified
	 * result set's current row into a map of the column names to their
	 * corresponding row values.
	 */
	public static MapRowTransformer mapRowTransformer(ResultSet resultSet) throws SQLException {
		return new MapRowTransformer(resultSet);
	}

	/**
	 * A row transformer the converts a result set's current row into a map of
	 * the row's column names to their corresponding values in the row.
	 */
	public static class MapRowTransformer
		extends ResultSetRowTransformerAdapter<Map<String, Object>>
	{
		private final int columnCount;
		private final String[] columnNames;

		/**
		 * The specified array of column names must be 1-based
		 * (i.e. the value at index 0 is ignored).
		 * @see ResultSet#getObject(int)
		 */
		public MapRowTransformer(ResultSet resultSet) throws SQLException {
			super();
			ResultSetMetaData rsMetaData = resultSet.getMetaData();
			this.columnCount = rsMetaData.getColumnCount() + 1;
			this.columnNames = this.buildColumnNames(rsMetaData);
		}

		/**
		 * Return the names of the specified result set's columns in an array where
		 * the index of each name matches its column index in the result set.
		 * <p>
		 * <strong>NB:</strong> A result set's index is 1-based
		 * (i.e. it is a "subscript). As a result the returned array will contain a
		 * <code>null</code> at index 0.
		 * 
		 * @see ResultSet#getObject(int)
		 */
		private String[] buildColumnNames(ResultSetMetaData rsMetaData) throws SQLException {
			String[] names = new String[this.columnCount];
			for (int i = 1; i < names.length; i++) {  // NB: start with 1
				names[i] = rsMetaData.getColumnName(i);
			}
			return names;
		}

		@Override
		public LinkedHashMap<String, Object> transform(ResultSet resultSet) throws SQLException {
			// use a linked hash map so the column order is preserved
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>(this.columnCount);
			for (int i = 1; i < this.columnCount; i++) {  // NB: start with 1 (ResultSet is 1-based)
				map.put(this.columnNames[i], resultSet.getObject(i));
			}
			return map;
		}
	}

	/**
	 * Dump contents of the specified result set to the
	 * {@link System#out system console}. The result set will be closed upon return
	 * from this method.
	 */
	public static void dump(ResultSet resultSet) throws SQLException {
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		// synchronize the console so everything is contiguous
		synchronized (System.out) {
			dumpOn(resultSet, pw);
		}
		pw.flush();
	}

	/**
	 * Dump contents of the specified result set to the specified writer.
	 * The result set will be closed upon return from this method.
	 */
	public static void dumpOn(ResultSet resultSet, PrintWriter pw) throws SQLException {
		ArrayList<Map<String, Object>> maps = convertToMaps(resultSet);
		for (Iterator<Map<String, Object>> mapStream = maps.iterator(); mapStream.hasNext(); ) {
			for (Map.Entry<String, Object> entry : mapStream.next().entrySet()) {
				pw.print(entry.getKey());
				pw.print(" = "); //$NON-NLS-1$
				pw.print(entry.getValue());
				pw.println();
			}
			if (mapStream.hasNext()) {
				pw.println();
			}
		}
		pw.println("total rows: " + maps.size()); //$NON-NLS-1$
	}
}

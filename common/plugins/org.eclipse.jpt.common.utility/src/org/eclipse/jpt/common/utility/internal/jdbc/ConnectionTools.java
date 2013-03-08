/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * {@link Connection} utility methods.
 */
public class ConnectionTools {

	/**
	 * Execute the specified SQL statement and convert its output to a list of
	 * maps that map the result set's column names to their corresponding row
	 * values.
	 * @see ResultSetTools#convertToMaps(ResultSet)
	 */
	public static List<Map<String, Object>> execute(Connection connection, String sql) throws SQLException {
		Statement statement = connection.createStatement();
		try {
			statement.execute(sql);
			return ResultSetTools.convertToMaps(statement.getResultSet());
		} finally {
			statement.close();
		}
	}

	/**
	 * Execute the specified SQL statement and pass the resulting result set to
	 * the specified command.
	 */
	public static void execute(Connection connection, String sql, ResultSetCommand command) throws SQLException {
		Statement statement = connection.createStatement();
		try {
			statement.execute(sql);
			command.execute(statement.getResultSet());
		} finally {
			statement.close();
		}
	}

	/**
	 * Execute the specified SQL statement, pass the resulting result set's
	 * rows to the specified transformer, and return a list of the transformed
	 * rows.
	 */
	public static <E> List<E> execute(Connection connection, String sql, ResultSetRowTransformer<? extends E> transformer) throws SQLException {
		Statement statement = connection.createStatement();
		try {
			statement.execute(sql);
			return ResultSetTools.convertToList(statement.getResultSet(), transformer);
		} finally {
			statement.close();
		}
	}

	/**
	 * Execute the specified SQL statement and dump its output to
	 * {@link System#out system console}.
	 */
	public static void dump(Connection connection, String sql) throws SQLException {
		execute(connection, sql, CONSOLE_DUMP_COMMAND);
	}

	public static final ResultSetCommand CONSOLE_DUMP_COMMAND = new ConsoleDumpCommand();
	public static class ConsoleDumpCommand
		extends ResultSetCommandAdapter
	{
		@Override
		public void execute(ResultSet resultSet) throws SQLException {
			ResultSetTools.dump(resultSet);
		}
	}

	/**
	 * Execute the specified SQL statement and dump its output to
	 * specified writer.
	 */
	public static void dumpOn(Connection connection, String sql, PrintWriter pw) throws SQLException {
		execute(connection, sql, new DumpCommand(pw));
	}

	public static class DumpCommand
		extends ResultSetCommandAdapter
	{
		private final PrintWriter printWriter;
		public DumpCommand(PrintWriter printWriter) {
			super();
			this.printWriter = printWriter;
		}
		@Override
		public void execute(ResultSet resultSet) throws SQLException {
			ResultSetTools.dumpOn(resultSet, this.printWriter);
		}
	}
}

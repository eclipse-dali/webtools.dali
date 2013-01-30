/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link ResultSet} utility methods.
 */
public class ResultSetTools {
	/**
	 * Return an iterator the returns the first object in each row of the
	 * specified result set. The first object in each must be of type
	 * {@code <E>}.
	 * @see ResultSetIterator
	 */
	@SuppressWarnings("unchecked")
	public static <E> ResultSetIterator<E> iterator(ResultSet resultSet) {
		return (ResultSetIterator<E>) iterator(resultSet, defaultResultSetTransformer());
	}

	/**
	 * Return a transformer the returns the first object in a result set's
	 * current row.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Transformer<ResultSet, E> defaultResultSetTransformer() {
		return DEFAULT_RESULT_SET_TRANSFORMER;
	}

	/**
	 * A transformer the returns the first object in a result set's current row.
	 */
	@SuppressWarnings("rawtypes")
	public static final Transformer DEFAULT_RESULT_SET_TRANSFORMER = new DefaultResultSetTransformer();

	/**
	 * A transformer the returns the first object in a result set's current row.
	 */
	public static class DefaultResultSetTransformer<E>
		extends TransformerAdapter<ResultSet, E>
	{
		@Override
		public E transform(ResultSet rs) {
			try {
				return this.transform_(rs);
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		}
		@SuppressWarnings("unchecked")
		private E transform_(ResultSet rs) throws SQLException {
			// result set columns are indexed starting with 1
			return (E) rs.getObject(1);
		}
	}

	/**
	 * Return an iterator the returns the objects produced by the specified transformer.
	 * @see ResultSetIterator
	 */
	public static <E> ResultSetIterator<E> iterator(ResultSet resultSet, Transformer<? super ResultSet, ? extends E> transformer) {
		return new ResultSetIterator<E>(resultSet, transformer);
	}
}

/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.iterators;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * A <code>ResultSetIterator</code> wraps an SQL {@link ResultSet}
 * and transforms its rows for client consumption. Subclasses can override
 * {@link #buildNext(ResultSet)} to build the expected object from
 * the current row of the result set.
 * <p>
 * To use, supply:<ul>
 * <li> a {@link ResultSet}
 * <li> an {@link Adapter} that converts a row in the {@link ResultSet}
 * into the desired object
 * (alternatively, subclass <code>ResultSetIterator</code>
 * and override the {@link #buildNext(ResultSet)} method)
 * </ul>
 * 
 * @param <E> the type of elements returned by the iterator
 * 
 * @see java.sql.ResultSet
 */
public class ResultSetIterator<E>
	implements Iterator<E>
{
	private final ResultSet resultSet;
	private final Adapter<E> adapter;
	private E next;
	private boolean done;


	/**
	 * Construct an iterator on the specified result set that returns
	 * the objects produced by the specified adapter.
	 */
	public ResultSetIterator(ResultSet resultSet, Adapter<E> adapter) {
		super();
		this.resultSet = resultSet;
		this.adapter = adapter;
		this.done = false;
		this.next = this.buildNext();
	}

	/**
	 * Construct an iterator on the specified result set that returns
	 * the first object in each row of the result set.
	 */
	public ResultSetIterator(ResultSet resultSet) {
		this(resultSet, Adapter.Default.<E>instance());
	}

	/**
	 * Build the next object for the iterator to return.
	 * Close the result set when we reach the end.
	 */
	private E buildNext() {
		try {
			if (this.resultSet.next()) {
				return this.buildNext(this.resultSet);
			}
			this.resultSet.close();
			this.done = true;
			return null;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * By default, return the first object in the current row
	 * of the result set. Any {@link SQLException}s will
	 * be caught and wrapped in a {@link RuntimeException}.
	 */
	protected E buildNext(ResultSet rs) throws SQLException {
		return this.adapter.buildNext(rs);
	}

	public boolean hasNext() {
		return ! this.done;
	}

	public E next() {
		if (this.done) {
			throw new NoSuchElementException();
		}
		E temp = this.next;
		this.next = this.buildNext();
		return temp;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.resultSet);
	}


	// ********** interface **********

	/**
	 * Used by {@link ResultSetIterator} to convert a
	 * {@link ResultSet}'s current row into the next object
	 * to be returned by the {@link Iterator}.
	 */
	public interface Adapter<T> {

		/**
		 * Return an object corresponding to the result set's
		 * "current" row. Any {@link SQLException}s will
		 * be caught and wrapped in a {@link RuntimeException}.
		 * @see java.sql.ResultSet
		 */
		T buildNext(ResultSet rs) throws SQLException;


		final class Default<S> implements Adapter<S> {
			@SuppressWarnings("rawtypes")
			public static final Adapter INSTANCE = new Default();
			@SuppressWarnings("unchecked")
			public static <R> Adapter<R> instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Default() {
				super();
			}
			// return the first object in the current row of the result set
			@SuppressWarnings("unchecked")
			public S buildNext(ResultSet rs) throws SQLException {
				// result set columns are indexed starting with 1
				return (S) rs.getObject(1);
			}
			@Override
			public String toString() {
				return "ResultSetIterator.Adapter.Default"; //$NON-NLS-1$
			}
			private static final long serialVersionUID = 1L;
			private Object readResolve() {
				// replace this object with the singleton
				return INSTANCE;
			}
		}

	}

}

/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
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
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * A <code>ResultSetIterator</code> wraps an SQL {@link ResultSet}
 * and transforms its rows for client consumption.
 * <p>
 * To use, supply:<ul>
 * <li> a {@link ResultSet}
 * <li> a {@link Transformer} that converts a row in the {@link ResultSet}
 * into the desired object
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
	private final Transformer<? super ResultSet, ? extends E> transformer;
	private E next;
	private boolean done;


	/**
	 * Construct an iterator on the specified result set that returns
	 * the objects produced by the specified transformer.
	 */
	public ResultSetIterator(ResultSet resultSet, Transformer<? super ResultSet, ? extends E> transformer) {
		super();
		if ((resultSet == null) || (transformer == null)) {
			throw new NullPointerException();
		}
		this.resultSet = resultSet;
		this.transformer = transformer;
		this.done = false;
		this.next = this.buildNext();
	}

	/**
	 * Build the next object for the iterator to return.
	 * Close the result set when we reach the end.
	 */
	private E buildNext() {
		try {
			return this.buildNext_();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	private E buildNext_() throws SQLException {
		if (this.resultSet.next()) {
			return this.transformer.transform(this.resultSet);
		}
		this.resultSet.close();
		this.done = true;
		return null;
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
		return ObjectTools.toString(this, this.resultSet);
	}
}

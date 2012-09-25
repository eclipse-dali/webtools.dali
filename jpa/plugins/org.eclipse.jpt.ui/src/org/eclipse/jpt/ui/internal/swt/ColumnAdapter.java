/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.swt;

import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;

/**
 * This adapter is used by the table model adapter to convert a model object
 * into the models used for each of the cells for the object's corresponding row
 * in the table.
 *
 * @version 2.0
 * @since 2.0
 */
public interface ColumnAdapter<V> {

	/**
	 * Return the cell models for the specified subject
	 * that corresponds to a single row in the table.
	 */
	WritablePropertyValueModel<?>[] cellModels(V subject);

	/**
	 * Returns the number of columns in the table. Typically this is static.
	 *
	 * @return The number of columns
	 */
	int columnCount();

	/**
	 * Returns the name of the column at the specified index.
	 *
	 * @param columnIndex The index of the column to retrieve its display text
	 * @return The display text of the column
	 */
	String columnName(int columnIndex);

	/**
	 * Returns whether the specified column is editable. Typically this is the
	 * same for every row.
	 *
	 * @param columnIndex The index of the column for which we determine if
	 * the content can be modified
	 * @return <code>true</code> to allow editing of the cell at the given
	 * column index; <code>false</code> to keep it not editable
	 */
//	boolean columnIsEditable(int columnIndex);
}

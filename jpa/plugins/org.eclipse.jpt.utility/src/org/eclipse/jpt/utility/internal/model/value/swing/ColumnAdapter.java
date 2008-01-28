/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value.swing;

import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;


/**
 * This adapter is used by the table model adapter to
 * convert a model object into the models used for each of
 * the cells for the object's corresponding row in the table.
 */
public interface ColumnAdapter {
	/**
	 * Return the number of columns in the table.
	 * Typically this is static.
	 */
	int columnCount();

	/**
	 * Return the name of the specified column.
	 */
	String columnName(int index);

	/**
	 * Return the class of the specified column.
	 */
	Class<?> columnClass(int index);

	/**
	 * Return whether the specified column is editable.
	 * Typically this is the same for every row.
	 */
	boolean columnIsEditable(int index);

	/**
	 * Return the cell models for the specified subject
	 * that corresponds to a single row in the table.
	 */
	WritablePropertyValueModel<Object>[] cellModels(Object subject);

}
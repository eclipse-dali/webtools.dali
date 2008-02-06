/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import java.util.ListIterator;

public interface IAssociationOverride extends IOverride
{
	<T extends IJoinColumn> ListIterator<T> joinColumns();
	<T extends IJoinColumn> ListIterator<T> specifiedJoinColumns();
	<T extends IJoinColumn> ListIterator<T> defaultJoinColumns();
	int joinColumnsSize();
	int specifiedJoinColumnsSize();
	int defaultJoinColumnsSize();
	IJoinColumn addSpecifiedJoinColumn(int index);
	void removeSpecifiedJoinColumn(int index);
	void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex);
		String SPECIFIED_JOIN_COLUMNS_LIST = "specifiedJoinColumnsList";
		String DEFAULT_JOIN_COLUMNS_LIST = "defaultJoinColumnsList";
		
	boolean containsSpecifiedJoinColumns();

}
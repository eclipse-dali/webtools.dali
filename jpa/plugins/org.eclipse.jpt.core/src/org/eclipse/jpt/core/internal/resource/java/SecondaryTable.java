/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.ListIterator;


public interface SecondaryTable extends Table
{
	String ANNOTATION_NAME = JPA.SECONDARY_TABLE;

	ListIterator<PrimaryKeyJoinColumn> pkJoinColumns();
	
	PrimaryKeyJoinColumn pkJoinColumnAt(int index);
	
	int indexOfPkJoinColumn(PrimaryKeyJoinColumn pkJoinColumn);
	
	int pkJoinColumnsSize();

	PrimaryKeyJoinColumn addPkJoinColumn(int index);
	
	void removePkJoinColumn(int index);
	
	void movePkJoinColumn(int oldIndex, int newIndex);

	String PK_JOIN_COLUMNS_LIST = "pkJoinColumnsList";
}

/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.resource.java.SecondaryTableAnnotation;


public interface JavaSecondaryTable extends SecondaryTable, JavaJpaContextNode
{
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns();
	
	JavaPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn();
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns();
	
	JavaPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index);
	
	void initializeFromResource(SecondaryTableAnnotation secondaryTable);
	
	void update(SecondaryTableAnnotation secondaryTable);

}
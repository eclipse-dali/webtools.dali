/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ListIterator;
import org.eclipse.jpt.core.internal.context.base.ISecondaryTable;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTable;


public interface IJavaSecondaryTable extends ISecondaryTable, IJavaJpaContextNode
{
	
	@SuppressWarnings("unchecked")
	ListIterator<IJavaPrimaryKeyJoinColumn> primaryKeyJoinColumns();
	@SuppressWarnings("unchecked")
	ListIterator<IJavaPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns();
	@SuppressWarnings("unchecked")
	ListIterator<IJavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns();
	IJavaPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index);
	
	void initializeFromResource(SecondaryTable secondaryTable);
	
	void update(SecondaryTable secondaryTable);

}
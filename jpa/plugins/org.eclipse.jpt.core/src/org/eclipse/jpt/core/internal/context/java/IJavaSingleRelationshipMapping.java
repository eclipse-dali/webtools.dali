/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ListIterator;
import org.eclipse.jpt.core.internal.context.base.ISingleRelationshipMapping;

public interface IJavaSingleRelationshipMapping extends IJavaRelationshipMapping, ISingleRelationshipMapping
{
	@SuppressWarnings("unchecked")
	ListIterator<IJavaJoinColumn> joinColumns();
	
	IJavaJoinColumn getDefaultJoinColumn();
	
	@SuppressWarnings("unchecked")
	ListIterator<IJavaJoinColumn> specifiedJoinColumns();
	
	IJavaJoinColumn addSpecifiedJoinColumn(int index);
}

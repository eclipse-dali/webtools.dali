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

public interface Query extends JavaResource
{
	String getName();
	
	void setName(String name);
	
	String getQuery();
	
	void setQuery(String query);
	
	ListIterator<QueryHint> hints();
	
	QueryHint hintAt(int index);
	
	int indexOfHint(QueryHint hint);
	
	int hintsSize();

	QueryHint addHint(int index);
	
	void removeHint(int index);
	
	void moveHint(int oldIndex, int newIndex);

}

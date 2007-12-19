/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import java.util.ListIterator;

public interface IQuery extends IJpaContextNode
{

	String getName();
	void setName(String value);
		String NAME_PROPERTY = "nameProperty";

	String getQuery();
	void setQuery(String value);
		String QUERY_PROPERTY = "queryProperty";

	<T extends IQueryHint> ListIterator<T> hints();
	int hintsSize();
	IQueryHint addHint(int index);
	void removeHint(int index);
	void moveHint(int targetIndex, int sourceIndex);
		String HINTS_LIST = "hintsList";

}

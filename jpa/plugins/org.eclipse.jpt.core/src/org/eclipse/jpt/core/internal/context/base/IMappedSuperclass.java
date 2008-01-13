/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import java.util.ListIterator;


public interface IMappedSuperclass extends ITypeMapping
{
	String getIdClass();
	void setIdClass(String value);
		String ID_CLASS_PROPERTY = "isClassProperty";

	<T extends INamedQuery> ListIterator<T> namedQueries();
	int namedQueriesSize();
	INamedQuery addNamedQuery(int index);
	void removeNamedQuery(int index);
	void moveNamedQuery(int targetIndex, int sourceIndex);
		String NAMED_QUERIES_LIST = "namedQueriesList";

	<T extends INamedNativeQuery> ListIterator<T> namedNativeQueries();
	int namedNativeQueriesSize();
	INamedNativeQuery addNamedNativeQuery(int index);
	void removeNamedNativeQuery(int index);
	void moveNamedNativeQuery(int targetIndex, int sourceIndex);
		String NAMED_NATIVE_QUERIES_LIST = "namedNativeQueriesList";

}

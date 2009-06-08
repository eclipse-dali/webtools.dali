/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.QueryContainer;

public interface JavaQueryContainer extends QueryContainer
{
	@SuppressWarnings("unchecked")
	ListIterator<JavaNamedQuery> namedQueries();

	JavaNamedQuery addNamedQuery(int index);

	@SuppressWarnings("unchecked")
	ListIterator<JavaNamedNativeQuery> namedNativeQueries();

	JavaNamedNativeQuery addNamedNativeQuery(int index);
}
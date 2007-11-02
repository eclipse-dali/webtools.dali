/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.utility.internal.Filter;

public interface IJavaTypeMapping extends ITypeMapping
{
	void initialize(JavaPersistentTypeResource persistentTypeResource);
	
	void update(JavaPersistentTypeResource persistentTypeResource);

	/**
	 * Return the candidate code-completion values for the specified position
	 * in the source code.
	 */
	Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot);
	
	String annotationName();
}
/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Annotation extends JavaResourceNode
{	
	/**
	 * Return the fully qualified annotation name.
	 * @see JPA
	 */
	String getAnnotationName();
	
	org.eclipse.jdt.core.dom.Annotation getJdtAnnotation(CompilationUnit astRoot);
	
	/**
	 * Removing the underyling Java annotation
	 */
	void removeAnnotation();

	/**
	 * Create and add Java annotation
	 */
	void newAnnotation();
	
}

/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;

public interface Annotation extends JavaResourceNode
{	
	/**
	 * Return the fully qualified annotation name.
	 * @see JPA
	 */
	String getAnnotationName();
	
	org.eclipse.jdt.core.dom.Annotation jdtAnnotation(CompilationUnit astRoot);
	
	/**
	 * Removing the underyling Java annotation
	 */
	void removeAnnotation();

	/**
	 * Create and add Java annotation
	 */
	void newAnnotation();
	
}

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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;

public interface Annotation extends JavaResource
{	
	/**
	 * Return the fully qualified annotation name.
	 * @see JPA
	 */
	String getAnnotationName();
	
	org.eclipse.jdt.core.dom.Annotation jdtAnnotation(CompilationUnit astRoot);

	DeclarationAnnotationAdapter getDeclarationAnnotationAdapter();
	
	/**
	 * Removing the underyling Java annotation
	 */
	void removeAnnotation();

	/**
	 * Create and add Java annotation
	 */
	void newAnnotation();
}

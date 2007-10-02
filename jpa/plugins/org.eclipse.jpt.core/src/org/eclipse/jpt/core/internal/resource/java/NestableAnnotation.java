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


public interface NestableAnnotation extends Annotation
{
	//currently using this when the NestableAnnotation is moved from
	//standalone to nested or from nested to standalone.
	//not the greatest since you have to make sure to call all setter methods
	void initializeFrom(NestableAnnotation oldAnnotation);
	
	org.eclipse.jdt.core.dom.Annotation annotation(CompilationUnit astRoot);
	
	/**
	 * Should only be called when the NestableAnnotation is actually nested
	 * @param newIndex
	 */
	void moveAnnotation(int newIndex);
	
	void removeAnnotation();
}

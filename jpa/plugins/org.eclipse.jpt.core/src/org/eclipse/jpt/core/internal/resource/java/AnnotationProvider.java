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

import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;

public interface AnnotationProvider
{
	/**
	 * Return the fully qualified annotation name
	 */
	String getAnnotationName();
		
	DeclarationAnnotationAdapter getDeclarationAnnotationAdapter();
	
	//TODO refactor to support Attribute level annotations.  pass in Member or make this generic
	Annotation buildAnnotation(Type type, JpaPlatform jpaPlatform);
}

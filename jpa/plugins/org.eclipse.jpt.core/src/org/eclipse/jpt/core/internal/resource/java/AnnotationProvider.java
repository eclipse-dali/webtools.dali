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

import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;

public interface AnnotationProvider
{
	/**
	 * Return the fully qualified annotation name
	 */
	String getAnnotationName();
	
	//TODO I don't really like that this is api.  If we used a visitor for discovering annotations, we could remove this
	//     see AbstractJavaPersistentResource.updateAnnotations
	DeclarationAnnotationAdapter getDeclarationAnnotationAdapter();
	
	Annotation buildAnnotation(Member member, IJpaPlatform jpaPlatform);
}

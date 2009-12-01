/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.resource.java.source;

import org.eclipse.jpt.core.internal.resource.java.source.SourceNamedQueryAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Type;

/**
 *  SourceNamedQuery1_0Annotation
 */
public final class SourceNamedQuery1_0Annotation
	extends SourceNamedQueryAnnotation
{

	// ********** constructors **********
	public SourceNamedQuery1_0Annotation(JavaResourceNode parent, Type type) {
		super(parent, type);
	}
	
	public SourceNamedQuery1_0Annotation(JavaResourceNode parent, Type type, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, type, daa, annotationAdapter);
	}	
}

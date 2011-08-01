/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;

/**
 * Used for annotations that have no behavior, just a name
 */
public final class SourceNamedAnnotation
	extends SourceAnnotation
{
	private final String annotationName;

	public SourceNamedAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, String annotationName) {
		super(parent, annotatedElement, new SimpleDeclarationAnnotationAdapter(annotationName));
		this.annotationName = annotationName;
	}

	public String getAnnotationName() {
		return this.annotationName;
	}

	public void initialize(CompilationUnit astRoot) {
		//nothing
	}
	
	public void synchronizeWith(CompilationUnit astRoot) {
		//nothing
	}
}

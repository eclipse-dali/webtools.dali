/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.source;

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

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.annotationName);
	}
}

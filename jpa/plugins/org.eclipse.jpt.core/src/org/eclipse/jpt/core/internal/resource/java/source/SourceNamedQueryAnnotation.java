/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import org.eclipse.jpt.core.internal.jpa1.resource.java.source.SourceNamedQuery1_0Annotation;
import org.eclipse.jpt.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableNamedQueryAnnotation;
import org.eclipse.jpt.core.resource.java.NestableQueryHintAnnotation;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Type;

/**
 * <code>javax.persistence.NamedQuery</code>
 */
public abstract class SourceNamedQueryAnnotation
	extends SourceQueryAnnotation
	implements NestableNamedQueryAnnotation
{
	public static final SimpleDeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);


	public SourceNamedQueryAnnotation(JavaResourceNode parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER, new ElementAnnotationAdapter(type, DECLARATION_ANNOTATION_ADAPTER));
	}

	public SourceNamedQueryAnnotation(JavaResourceNode parent, Type type, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, type, daa, annotationAdapter);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}


	// ********** AbstractBaseNamedQueryAnnotation implementation **********

	@Override
	String getNameElementName() {
		return JPA.NAMED_QUERY__NAME;
	}

	@Override
	String getQueryElementName() {
		return JPA.NAMED_QUERY__QUERY;
	}

	@Override
	String getHintsElementName() {
		return JPA.NAMED_QUERY__HINTS;
	}

	@Override
	NestableQueryHintAnnotation buildHint(int index) {
		return SourceQueryHintAnnotation.createNamedQueryQueryHint(this, this.annotatedElement, this.daa, index);
	}


	// ********** static methods **********

	static SourceNamedQueryAnnotation createNestedNamedQuery(JavaResourceNode parent, Type type, int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, attributeOverridesAdapter, ANNOTATION_NAME);
		IndexedAnnotationAdapter annotationAdapter = new ElementIndexedAnnotationAdapter(type, idaa);

		return new SourceNamedQuery1_0Annotation(parent, type, idaa, annotationAdapter);
	}
}

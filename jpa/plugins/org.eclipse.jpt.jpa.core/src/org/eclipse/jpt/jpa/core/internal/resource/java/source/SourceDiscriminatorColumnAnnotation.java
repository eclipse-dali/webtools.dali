/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * <code>javax.persistence.DiscriminatorColumn</code>
 */
public final class SourceDiscriminatorColumnAnnotation
	extends SourceBaseDiscriminatorColumnAnnotation
	implements DiscriminatorColumnAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);


	public SourceDiscriminatorColumnAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	// ********** SourceNamedColumnAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA.DISCRIMINATOR_COLUMN__NAME;
	}

	@Override
	protected String getColumnDefinitionElementName() {
		return JPA.DISCRIMINATOR_COLUMN__COLUMN_DEFINITION;
	}

	// ********** SourceBaseDiscriminatorColumnAnnotation implementation **********

	@Override
	protected String getDiscriminatorTypeElementName() {
		return JPA.DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE;
	}

	@Override
	protected String getLengthElementName() {
		return JPA.DISCRIMINATOR_COLUMN__LENGTH;
	}
}

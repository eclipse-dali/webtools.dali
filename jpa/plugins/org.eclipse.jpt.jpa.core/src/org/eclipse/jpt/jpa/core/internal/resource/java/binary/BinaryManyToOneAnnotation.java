/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.ManyToOneAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * <code>javax.persistence.ManyToOne</code>
 */
public final class BinaryManyToOneAnnotation
	extends BinaryRelationshipMappingAnnotation
	implements ManyToOneAnnotation2_0
{
	private Boolean optional;


	public BinaryManyToOneAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.optional = this.buildOptional();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setOptional_(this.buildOptional());
	}


	// ********** BinaryRelationshipMappingAnnotation implementation **********

	@Override
	String getTargetEntityElementName() {
		return JPA.MANY_TO_ONE__TARGET_ENTITY;
	}

	@Override
	String getFetchElementName() {
		return JPA.MANY_TO_ONE__FETCH;
	}

	@Override
	String getCascadeElementName() {
		return JPA.MANY_TO_ONE__CASCADE;
	}


	// ********** ManyToOneMappingAnnotation implementation **********

	// ***** optional
	public Boolean getOptional() {
		return this.optional;
	}

	public void setOptional(Boolean optional) {
		throw new UnsupportedOperationException();
	}

	private void setOptional_(Boolean optional) {
		Boolean old = this.optional;
		this.optional = optional;
		this.firePropertyChanged(OPTIONAL_PROPERTY, old, optional);
	}

	private Boolean buildOptional() {
		return (Boolean) this.getJdtMemberValue(JPA.MANY_TO_ONE__OPTIONAL);
	}

	public TextRange getOptionalTextRange() {
		throw new UnsupportedOperationException();
	}
}

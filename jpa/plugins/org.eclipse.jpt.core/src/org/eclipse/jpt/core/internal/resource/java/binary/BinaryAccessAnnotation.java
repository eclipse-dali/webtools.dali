/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.AccessAnnotation;
import org.eclipse.jpt.core.resource.java.AccessType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * javax.persistence.Access
 */
public final class BinaryAccessAnnotation
	extends BinaryAnnotation
	implements AccessAnnotation
{
	private AccessType value;


	public BinaryAccessAnnotation(JavaResourcePersistentMember parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.value = this.buildValue();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setValue_(this.buildValue());
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}


	// ********** AccessAnnotation implementation **********

	// ***** value
	public AccessType getValue() {
		return this.value;
	}

	public void setValue(AccessType value) {
		throw new UnsupportedOperationException();
	}

	private void setValue_(AccessType value) {
		AccessType old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	private AccessType buildValue() {
		return AccessType.fromJavaAnnotationValue(this.getJdtMemberValue(JPA.ACCESS__VALUE));
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}

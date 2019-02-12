/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.AccessAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.AccessType;

/**
 * <code>javax.persistence.Access</code>
 */
public final class BinaryAccessAnnotation2_0
	extends BinaryAnnotation
	implements AccessAnnotation2_0
{
	private AccessType value;


	public BinaryAccessAnnotation2_0(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
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
		return AccessType.fromJavaAnnotationValue(this.getJdtMemberValue(JPA2_0.ACCESS__VALUE));
	}

	public TextRange getValueTextRange() {
		throw new UnsupportedOperationException();
	}
}

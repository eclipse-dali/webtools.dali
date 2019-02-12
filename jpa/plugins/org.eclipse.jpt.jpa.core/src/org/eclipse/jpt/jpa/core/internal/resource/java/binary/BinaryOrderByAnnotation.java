/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.OrderByAnnotation;

/**
 * <code>javax.persistence.OrderBy</code>
 */
public final class BinaryOrderByAnnotation
	extends BinaryAnnotation
	implements OrderByAnnotation
{
	private String value;


	public BinaryOrderByAnnotation(JavaResourceAnnotatedElement parent, IAnnotation annotation) {
		super(parent, annotation);
		this.value = this.buildValue();
	}
	
	@Override
	public void update() {
		super.update();
		this.setValue_(this.buildValue());
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}


	// ********** OrderByAnnotation implementation **********

	// ***** value
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		throw new UnsupportedOperationException();
	}

	private void setValue_(String value) {
		String old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	private String buildValue() {
		return (String) this.getJdtMemberValue(JPA.ORDER_BY__VALUE);
	}

	public TextRange getValueTextRange() {
		throw new UnsupportedOperationException();
	}
}

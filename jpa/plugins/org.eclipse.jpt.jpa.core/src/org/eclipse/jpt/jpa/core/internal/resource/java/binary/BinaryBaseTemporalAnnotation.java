/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.BaseTemporalAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TemporalType;

/**
 * <code><ul>
 * <li>javax.persistence.Temporal
 * <li>javax.persistence.MapKeyTemporal
 * </ul></code>
 */
public abstract class BinaryBaseTemporalAnnotation
	extends BinaryAnnotation
	implements BaseTemporalAnnotation
{
	private TemporalType value;


	protected BinaryBaseTemporalAnnotation(JavaResourceModel parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.value = this.buildValue();
	}

	@Override
	public void update() {
		super.update();
		this.setValue_(this.buildValue());
	}


	// ********** BaseTemporalAnnotation implementation **********

	// ***** value
	public TemporalType getValue() {
		return this.value;
	}

	public void setValue(TemporalType value) {
		throw new UnsupportedOperationException();
	}

	private void setValue_(TemporalType value) {
		TemporalType old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	private TemporalType buildValue() {
		return TemporalType.fromJavaAnnotationValue(this.getJdtMemberValue(getValueElementName()));
	}

	public TextRange getValueTextRange() {
		throw new UnsupportedOperationException();
	}

	protected abstract String getValueElementName();
}

/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.BaseConvertAnnotation;

/**
 * <code><ul>
 * <li>org.eclipse.persistence.annotations.Convert
 * <li>org.eclipse.persistence.annotations.MapKeyConvert
 * </ul></code>
 */
public abstract class EclipseLinkBinaryBaseConvertAnnotation
	extends BinaryAnnotation
	implements BaseConvertAnnotation
{
	private String value;

	protected EclipseLinkBinaryBaseConvertAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.value = this.buildValue();
	}

	@Override
	public void update() {
		super.update();
		this.setValue_(this.buildValue());
	}


	// ********** ConvertAnnotation implementation **********

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
		return (String) this.getJdtMemberValue(this.getValueElementName());
	}

	protected abstract String getValueElementName();

	public TextRange getValueTextRange() {
		throw new UnsupportedOperationException();
	}

	public boolean valueTouches(int pos) {
		throw new UnsupportedOperationException();
	}
}

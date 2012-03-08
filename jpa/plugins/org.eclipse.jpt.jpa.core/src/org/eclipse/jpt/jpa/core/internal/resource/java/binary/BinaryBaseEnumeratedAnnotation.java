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
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.BaseEnumeratedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EnumType;

/**
 * <code><ul>
 * <li>javax.persistence.Enumerated
 * <li>javax.persistence.MapKeyEnumerated
 * </ul></code>
 */
public abstract class BinaryBaseEnumeratedAnnotation
	extends BinaryAnnotation
	implements BaseEnumeratedAnnotation
{
	private EnumType value;
	

	protected BinaryBaseEnumeratedAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.value = this.buildValue();
	}

	@Override
	public void update() {
		super.update();
		this.setValue_(this.buildValue());
	}


	// ********** BaseEnumeratedAnnotation implementation **********

	// ***** value
	public EnumType getValue() {
		return this.value;
	}
	
	public void setValue(EnumType value) {
		throw new UnsupportedOperationException();
	}
	
	private void setValue_(EnumType value) {
		EnumType old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}
	
	private EnumType buildValue() {
		return EnumType.fromJavaAnnotationValue(this.getJdtMemberValue(this.getValueElementName()));
	}

	public TextRange getValueTextRange() {
		throw new UnsupportedOperationException();
	}
	
	protected abstract String getValueElementName();
}

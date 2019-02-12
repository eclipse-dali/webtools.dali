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
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.MutableAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.Mutable</code>
 */
public final class EclipseLinkBinaryMutableAnnotation
	extends BinaryAnnotation
	implements MutableAnnotation
{
	private Boolean value;


	public EclipseLinkBinaryMutableAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
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


	// ********** MutableAnnotation implementation **********

	// ***** value
	public Boolean getValue() {
		return this.value;
	}

	public void setValue(Boolean value) {
		throw new UnsupportedOperationException();
	}

	private void setValue_(Boolean value) {
		Boolean old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	private Boolean buildValue() {
		return (Boolean) this.getJdtMemberValue(EclipseLink.MUTABLE__VALUE);
	}

	public TextRange getValueTextRange() {
		throw new UnsupportedOperationException();
	}
}

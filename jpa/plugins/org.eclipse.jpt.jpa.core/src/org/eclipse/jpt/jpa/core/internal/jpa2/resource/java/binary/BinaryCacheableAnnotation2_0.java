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
import org.eclipse.jpt.jpa.core.jpa2.resource.java.CacheableAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;

/**
 * <code>javax.persistence.Cacheable</code>
 */
public final class BinaryCacheableAnnotation2_0
	extends BinaryAnnotation
	implements CacheableAnnotation2_0
{
	private Boolean value;


	public BinaryCacheableAnnotation2_0(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
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


	// ********** Cacheable2_0Annotation implementation **********

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
		return (Boolean) this.getJdtMemberValue(JPA2_0.CACHEABLE__VALUE);
	}

	public TextRange getValueTextRange() {
		throw new UnsupportedOperationException();
	}
}

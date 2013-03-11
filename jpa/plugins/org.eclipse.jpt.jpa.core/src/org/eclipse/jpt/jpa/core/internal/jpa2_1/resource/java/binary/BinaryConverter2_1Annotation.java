/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ConverterAnnotation2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.JPA2_1;

/**
 * <code>javax.persistence.Converter</code>
 */
public final class BinaryConverter2_1Annotation
	extends BinaryAnnotation
	implements ConverterAnnotation2_1
{
	private Boolean autoApply;
	

	public BinaryConverter2_1Annotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.autoApply = this.buildAutoApply();
	}

	@Override
	public void update() {
		super.update();
		this.setAutoApply_(this.buildAutoApply());
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	//************* Converter2_1Annotation implementation *************
	
	// ***** autoApply
	public Boolean getAutoApply() {
		return this.autoApply;
	}

	public void setAutoApply(Boolean autoApply) {
		throw new UnsupportedOperationException();
	}

	private void setAutoApply_(Boolean autoApply) {
		Boolean old = this.autoApply;
		this.autoApply = autoApply;
		this.firePropertyChanged(AUTO_APPLY_PROPERTY, old, autoApply);
	}

	private Boolean buildAutoApply() {
		return (Boolean) this.getJdtMemberValue(getNullableElementName());
	}
	
	String getNullableElementName() {
		return JPA2_1.CONVERTER__AUTO_APPLY;
	}

	public TextRange getAutoApplyTextRange() {
		throw new UnsupportedOperationException();
	}
}

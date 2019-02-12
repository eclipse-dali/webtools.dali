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
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.JoinFetchAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.JoinFetchType;

/**
 * <code>org.eclipse.persistence.annotations.JoinFetch</code>
 */
public final class EclipseLinkBinaryJoinFetchAnnotation
	extends BinaryAnnotation
	implements JoinFetchAnnotation
{
	private JoinFetchType value;


	public EclipseLinkBinaryJoinFetchAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
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


	// ********** BinaryJoinFetchAnnotation implementation **********

	// ***** value
	public JoinFetchType getValue() {
		return this.value;
	}

	public void setValue(JoinFetchType value) {
		throw new UnsupportedOperationException();
	}

	private void setValue_(JoinFetchType value) {
		JoinFetchType old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	private JoinFetchType buildValue() {
		return JoinFetchType.fromJavaAnnotationValue(this.getJdtMemberValue(EclipseLink.JOIN_FETCH__VALUE));
	}

	public TextRange getValueTextRange() {
		throw new UnsupportedOperationException();
	}
}

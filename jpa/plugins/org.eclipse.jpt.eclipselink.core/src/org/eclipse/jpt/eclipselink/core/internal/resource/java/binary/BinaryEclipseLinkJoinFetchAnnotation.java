/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJoinFetchAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType;

/**
 * org.eclipse.persistence.annotations.JoinFetch
 */
public final class BinaryEclipseLinkJoinFetchAnnotation
	extends BinaryAnnotation
	implements EclipseLinkJoinFetchAnnotation
{
	private JoinFetchType value;


	public BinaryEclipseLinkJoinFetchAnnotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
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
		return JoinFetchType.fromJavaAnnotationValue(this.getJdtMemberValue(EclipseLinkJPA.JOIN_FETCH__VALUE));
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}

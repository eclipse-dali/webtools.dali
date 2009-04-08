/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.core.resource.java.TemporalType;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * javax.persistence.Temporal
 */
public final class BinaryTemporalAnnotation
	extends BinaryAnnotation
	implements TemporalAnnotation
{
	private TemporalType value;


	public BinaryTemporalAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
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


	// ********** TemporalAnnotation implementation **********

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
		return TemporalType.fromJavaAnnotationValue(this.getJdtMemberValue(JPA.TEMPORAL__VALUE));
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}

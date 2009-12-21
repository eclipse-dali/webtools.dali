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
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.OrderByAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * javax.persistence.OrderBy
 */
public final class BinaryOrderByAnnotation
	extends BinaryAnnotation
	implements OrderByAnnotation
{
	private String value;


	public BinaryOrderByAnnotation(JavaResourcePersistentAttribute parent, IAnnotation annotation) {
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

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}

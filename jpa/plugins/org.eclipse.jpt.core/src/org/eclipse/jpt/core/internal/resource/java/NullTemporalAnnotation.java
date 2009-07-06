/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.core.resource.java.TemporalType;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * javax.persistence.Temporal
 */
public final class NullTemporalAnnotation
	extends NullAnnotation
	implements TemporalAnnotation
{
	protected NullTemporalAnnotation(JavaResourcePersistentAttribute parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected TemporalAnnotation addSupportingAnnotation() {
		return (TemporalAnnotation) super.addSupportingAnnotation();
	}


	// ********** TemporalAnnotation implementation **********

	// ***** value
	public TemporalType getValue() {
		return null;
	}

	public void setValue(TemporalType value) {
		if (value != null) {
			this.addSupportingAnnotation().setValue(value);
		}
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return null;
	}

}

/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.NullAnnotation;
import org.eclipse.jpt.core.resource.java.AccessType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt2_0.core.resource.java.AccessAnnotation;

/**
 * javax.persistence.Access
 */
public class NullAccessAnnotation
	extends NullAnnotation
	implements AccessAnnotation
{

	protected NullAccessAnnotation(JavaResourcePersistentMember parent) {
		super(parent);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected AccessAnnotation addSupportingAnnotation() {
		return (AccessAnnotation) super.addSupportingAnnotation();
	}
	
	// ***** value
	public AccessType getValue() {
		return null;
	}
	
	public void setValue(AccessType value) {
		if (value != null) {
			this.addSupportingAnnotation().setValue(value);
		}
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return null;
	}
	
}

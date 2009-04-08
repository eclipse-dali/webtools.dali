/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.AccessAnnotation;
import org.eclipse.jpt.core.resource.java.AccessType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;

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
	protected AccessAnnotation buildSupportingAnnotation() {
		return (AccessAnnotation) super.buildSupportingAnnotation();
	}
	
	// ***** value
	public AccessType getValue() {
		return null;
	}
	
	public void setValue(AccessType value) {
		if (value != null) {
			this.buildSupportingAnnotation().setValue(value);
		}
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return null;
	}
	
}

/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.NullAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType;

/**
 * org.eclipse.persistence.annotations.JoinFetch
 */
public class NullJoinFetchAnnotation
	extends NullAnnotation
	implements JoinFetchAnnotation
{
	protected NullJoinFetchAnnotation(JavaResourcePersistentAttribute parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected JoinFetchAnnotation addSupportingAnnotation() {
		return (JoinFetchAnnotation) super.addSupportingAnnotation();
	}

	// ***** value
	public JoinFetchType getValue() {
		return null;
	}

	public void setValue(JoinFetchType value) {
		if (value != null) {
			this.addSupportingAnnotation().setValue(value);
		}
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return null;
	}

}

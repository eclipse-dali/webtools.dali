/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapsId2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentMember;

/**
 * <code>javax.persistence.MapsId</code>
 */
public final class NullMapsId2_0Annotation
	extends NullAnnotation<MapsId2_0Annotation>
	implements MapsId2_0Annotation
{
	protected NullMapsId2_0Annotation(JavaResourcePersistentMember parent) {
		super(parent);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	// ***** value
	public String getValue() {
		return null;
	}
	
	public void setValue(String newValue) {
		if (newValue != null) {
			this.addAnnotation().setValue(newValue);
		}
	}
	
	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public boolean valueTouches(int pos, CompilationUnit astRoot) {
		return false;
	}
}

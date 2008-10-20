/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;


public class NullColumn extends NullBaseColumn implements ColumnAnnotation, Annotation
{	
	public NullColumn(JavaResourcePersistentMember parent) {
		super(parent);
	}

	@Override
	public JavaResourcePersistentMember getParent() {
		return (JavaResourcePersistentMember) super.getParent();
	}
	
	public String getAnnotationName() {
		return ColumnAnnotation.ANNOTATION_NAME;
	}

	@Override
	protected ColumnAnnotation createResourceColumn() {
		return (ColumnAnnotation) getParent().addSupportingAnnotation(getAnnotationName());
	}

	public Integer getLength() {
		return null;
	}
	
	public void setLength(Integer length) {
		if (length != null) {
			createResourceColumn().setLength(length);
		}
	}
	
	public Integer getScale() {
		return null;
	}
	
	public void setScale(Integer scale) {
		if (scale != null) {
			createResourceColumn().setScale(scale);
		}
	}
	
	public Integer getPrecision() {
		return null;
	}
	
	public void setPrecision(Integer precision) {
		if (precision != null) {
			createResourceColumn().setPrecision(precision);
		}
	}
	
	public TextRange getScaleTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public TextRange getLengthTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public TextRange getPrecisionTextRange(CompilationUnit astRoot) {
		return null;
	}
}

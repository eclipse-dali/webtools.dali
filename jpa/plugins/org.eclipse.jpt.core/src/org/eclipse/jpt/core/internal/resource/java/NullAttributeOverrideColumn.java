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
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;


public class NullAttributeOverrideColumn extends NullBaseColumn implements ColumnAnnotation, Annotation
{	
	public NullAttributeOverrideColumn(AttributeOverrideAnnotation parent) {
		super(parent);
	}

	@Override
	public AttributeOverrideAnnotation getParent() {
		return (AttributeOverrideAnnotation) super.getParent();
	}
	
	public String getAnnotationName() {
		return ColumnAnnotation.ANNOTATION_NAME;
	}

	@Override
	protected ColumnAnnotation createColumnResource() {
		return getParent().addColumn();
	}

	public Integer getLength() {
		return null;
	}
	
	public void setLength(Integer length) {
		if (length != null) {
			createColumnResource().setLength(length);
		}
	}
	
	public Integer getScale() {
		return null;
	}
	
	public void setScale(Integer scale) {
		if (scale != null) {
			createColumnResource().setScale(scale);
		}
	}
	
	public Integer getPrecision() {
		return null;
	}
	
	public void setPrecision(Integer precision) {
		if (precision != null) {
			createColumnResource().setPrecision(precision);
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

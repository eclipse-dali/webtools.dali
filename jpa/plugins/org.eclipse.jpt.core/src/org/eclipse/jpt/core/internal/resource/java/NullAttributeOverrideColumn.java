/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;


public class NullAttributeOverrideColumn extends NullAbstractColumn implements Column, Annotation
{	
	public NullAttributeOverrideColumn(AttributeOverride parent) {
		super(parent);
	}

	@Override
	public AttributeOverride parent() {
		return (AttributeOverride) super.parent();
	}
	
	public String getAnnotationName() {
		return Column.ANNOTATION_NAME;
	}

	@Override
	protected Column createColumnResource() {
		return parent().addColumn();
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
	
	public ITextRange scaleTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public ITextRange lengthTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public ITextRange precisionTextRange(CompilationUnit astRoot) {
		return null;
	}
}

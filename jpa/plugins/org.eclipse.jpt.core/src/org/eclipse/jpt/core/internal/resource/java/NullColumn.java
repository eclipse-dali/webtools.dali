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


public class NullColumn extends NullAbstractColumn implements Column, Annotation
{	
	public NullColumn(JavaResource parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return Column.ANNOTATION_NAME;
	}

	@Override
	protected Column createColumnResource() {
		return (Column) super.createColumnResource();
	}


	public int getLength() {
		return Column.DEFAULT_LENGTH;
	}
	
	public void setLength(int length) {
		if (length != DEFAULT_LENGTH) {
			createColumnResource().setLength(length);
		}
	}
	
	public int getScale() {
		return Column.DEFAULT_SCALE;
	}
	
	public void setScale(int scale) {
		if (scale != DEFAULT_SCALE) {
			createColumnResource().setScale(scale);
		}
	}
	
	public int getPrecision() {
		return Column.DEFAULT_PRECISION;
	}
	
	public void setPrecision(int precision) {
		if (precision != DEFAULT_PRECISION) {
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

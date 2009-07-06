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
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * javax.persistence.Column
 */
public class NullColumnAnnotation
	extends NullBaseColumnAnnotation
	implements ColumnAnnotation
{
	public NullColumnAnnotation(JavaResourceNode parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected ColumnAnnotation addAnnotation() {
		return (ColumnAnnotation) this.addSupportingAnnotation();
	}

	// ***** length
	public Integer getLength() {
		return null;
	}

	public void setLength(Integer length) {
		if (length != null) {
			this.addAnnotation().setLength(length);
		}
	}

	public TextRange getLengthTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** scale
	public Integer getScale() {
		return null;
	}

	public void setScale(Integer scale) {
		if (scale != null) {
			this.addAnnotation().setScale(scale);
		}
	}

	public TextRange getScaleTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** precision
	public Integer getPrecision() {
		return null;
	}

	public void setPrecision(Integer precision) {
		if (precision != null) {
			this.addAnnotation().setPrecision(precision);
		}
	}

	public TextRange getPrecisionTextRange(CompilationUnit astRoot) {
		return null;
	}

}

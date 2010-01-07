/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.java.JavaBaseColumn;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaBaseColumn;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericJavaColumn extends AbstractJavaBaseColumn<ColumnAnnotation> implements JavaColumn
{

	protected Integer specifiedLength;

	protected Integer specifiedPrecision;

	protected Integer specifiedScale;
	
	public GenericJavaColumn(JavaJpaContextNode parent, JavaBaseColumn.Owner owner) {
		super(parent, owner);
	}
	
	@Override
	public void initialize(ColumnAnnotation column) {
		super.initialize(column);
		this.specifiedLength = this.getResourceLength();
		this.specifiedPrecision = this.getResourcePrecision();
		this.specifiedScale = this.getResourceScale();
	}
	
	@Override
	public void update(ColumnAnnotation column) {
		super.update(column);
		this.setSpecifiedLength_(this.getResourceLength());
		this.setSpecifiedPrecision_(this.getResourcePrecision());
		this.setSpecifiedScale_(this.getResourceScale());
	}
	
	public int getLength() {
		return (this.getSpecifiedLength() == null) ? getDefaultLength() : this.getSpecifiedLength().intValue();
	}

	public int getDefaultLength() {
		return Column.DEFAULT_LENGTH;
	}
	
	public Integer getSpecifiedLength() {
		return this.specifiedLength;
	}

	public void setSpecifiedLength(Integer newSpecifiedLength) {
		Integer oldSpecifiedLength = this.specifiedLength;
		this.specifiedLength = newSpecifiedLength;
		getResourceColumn().setLength(newSpecifiedLength);
		firePropertyChanged(SPECIFIED_LENGTH_PROPERTY, oldSpecifiedLength, newSpecifiedLength);
	}
	
	protected void setSpecifiedLength_(Integer newSpecifiedLength) {
		Integer oldSpecifiedLength = this.specifiedLength;
		this.specifiedLength = newSpecifiedLength;
		firePropertyChanged(SPECIFIED_LENGTH_PROPERTY, oldSpecifiedLength, newSpecifiedLength);
	}
	
	protected Integer getResourceLength() {
		return getResourceColumn().getLength();
	}

	public int getPrecision() {
		return (this.getSpecifiedPrecision() == null) ? getDefaultPrecision() : this.getSpecifiedPrecision().intValue();
	}

	public int getDefaultPrecision() {
		return Column.DEFAULT_PRECISION;
	}
	
	public Integer getSpecifiedPrecision() {
		return this.specifiedPrecision;
	}

	public void setSpecifiedPrecision(Integer newSpecifiedPrecision) {
		Integer oldSpecifiedPrecision = this.specifiedPrecision;
		this.specifiedPrecision = newSpecifiedPrecision;
		getResourceColumn().setPrecision(newSpecifiedPrecision);
		firePropertyChanged(SPECIFIED_PRECISION_PROPERTY, oldSpecifiedPrecision, newSpecifiedPrecision);
	}
	
	protected void setSpecifiedPrecision_(Integer newSpecifiedPrecision) {
		Integer oldSpecifiedPrecision = this.specifiedPrecision;
		this.specifiedPrecision = newSpecifiedPrecision;
		firePropertyChanged(SPECIFIED_PRECISION_PROPERTY, oldSpecifiedPrecision, newSpecifiedPrecision);
	}
	
	protected Integer getResourcePrecision() {
		return getResourceColumn().getPrecision();
	}

	public int getScale() {
		return (this.getSpecifiedScale() == null) ? getDefaultScale() : this.getSpecifiedScale().intValue();
	}

	public int getDefaultScale() {
		return Column.DEFAULT_SCALE;
	}
	
	public Integer getSpecifiedScale() {
		return this.specifiedScale;
	}

	public void setSpecifiedScale(Integer newSpecifiedScale) {
		Integer oldSpecifiedScale = this.specifiedScale;
		this.specifiedScale = newSpecifiedScale;
		getResourceColumn().setScale(newSpecifiedScale);
		firePropertyChanged(SPECIFIED_SCALE_PROPERTY, oldSpecifiedScale, newSpecifiedScale);
	}
	
	protected void setSpecifiedScale_(Integer newSpecifiedScale) {
		Integer oldSpecifiedScale = this.specifiedScale;
		this.specifiedScale = newSpecifiedScale;
		firePropertyChanged(SPECIFIED_SCALE_PROPERTY, oldSpecifiedScale, newSpecifiedScale);
	}
	
	protected Integer getResourceScale() {
		return getResourceColumn().getScale();
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = getResourceColumn().getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getOwner().getValidationTextRange(astRoot);	
	}
}

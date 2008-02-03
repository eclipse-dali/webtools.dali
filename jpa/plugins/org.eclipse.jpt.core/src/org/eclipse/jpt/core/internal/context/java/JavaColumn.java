/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IColumn;
import org.eclipse.jpt.core.internal.resource.java.Column;

public class JavaColumn extends AbstractJavaColumn<Column> implements IJavaColumn
{

	protected Integer specifiedLength;

	protected Integer specifiedPrecision;

	protected Integer specifiedScale;
	
	public JavaColumn(IJavaJpaContextNode parent, IJavaColumn.Owner owner) {
		super(parent, owner);
	}
	
	@Override
	public void initializeFromResource(Column column) {
		super.initializeFromResource(column);
		this.specifiedLength = this.specifiedLength(column);
		this.specifiedPrecision = this.specifiedPrecision(column);
		this.specifiedScale = this.specifiedScale(column);
	}
	
	@Override
	public IJavaColumn.Owner owner() {
		return (IJavaColumn.Owner) super.owner();
	}

	@Override
	protected Column columnResource() {
		return this.owner().columnResource();
	}
	
	public Integer getLength() {
		return (this.getSpecifiedLength() == null) ? getDefaultLength() : this.getSpecifiedLength();
	}

	public Integer getDefaultLength() {
		return IColumn.DEFAULT_LENGTH;
	}
	
	public Integer getSpecifiedLength() {
		return this.specifiedLength;
	}

	public void setSpecifiedLength(Integer newSpecifiedLength) {
		Integer oldSpecifiedLength = this.specifiedLength;
		this.specifiedLength = newSpecifiedLength;
		columnResource().setLength(newSpecifiedLength);
		firePropertyChanged(SPECIFIED_LENGTH_PROPERTY, oldSpecifiedLength, newSpecifiedLength);
	}

	public Integer getPrecision() {
		return (this.getSpecifiedPrecision() == null) ? getDefaultPrecision() : this.getSpecifiedPrecision();
	}

	public Integer getDefaultPrecision() {
		return IColumn.DEFAULT_PRECISION;
	}
	
	public Integer getSpecifiedPrecision() {
		return this.specifiedPrecision;
	}

	public void setSpecifiedPrecision(Integer newSpecifiedPrecision) {
		Integer oldSpecifiedPrecision = this.specifiedPrecision;
		this.specifiedPrecision = newSpecifiedPrecision;
		columnResource().setPrecision(newSpecifiedPrecision);
		firePropertyChanged(SPECIFIED_PRECISION_PROPERTY, oldSpecifiedPrecision, newSpecifiedPrecision);
	}

	public Integer getScale() {
		return (this.getSpecifiedScale() == null) ? getDefaultScale() : this.getSpecifiedScale();
	}

	public Integer getDefaultScale() {
		return IColumn.DEFAULT_SCALE;
	}
	
	public Integer getSpecifiedScale() {
		return this.specifiedScale;
	}

	public void setSpecifiedScale(Integer newSpecifiedScale) {
		Integer oldSpecifiedScale = this.specifiedScale;
		this.specifiedScale = newSpecifiedScale;
		columnResource().setScale(newSpecifiedScale);
		firePropertyChanged(SPECIFIED_SCALE_PROPERTY, oldSpecifiedScale, newSpecifiedScale);
	}

	@Override
	public boolean tableIsAllowed() {
		return true;
	}
	
	public ITextRange validationTextRange(CompilationUnit astRoot) {
		ITextRange textRange = columnResource().textRange(astRoot);
		return (textRange != null) ? textRange : this.owner().validationTextRange(astRoot);	
	}
	
	@Override
	public void update(Column column) {
		super.update(column);
		this.setSpecifiedLength(this.specifiedLength(column));
		this.setSpecifiedPrecision(this.specifiedPrecision(column));
		this.setSpecifiedScale(this.specifiedScale(column));
	}
	
	protected Integer specifiedLength(Column column) {
		return column.getLength();
	}
	
	protected Integer specifiedPrecision(Column column) {
		return column.getPrecision();
	}
	
	protected Integer specifiedScale(Column column) {
		return column.getScale();
	}
}

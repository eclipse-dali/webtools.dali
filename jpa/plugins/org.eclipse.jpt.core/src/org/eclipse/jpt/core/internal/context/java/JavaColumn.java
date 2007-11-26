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
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentResource;

public class JavaColumn extends AbstractJavaColumn<Column> implements IJavaColumn
{

	protected int specifiedLength;
	protected static final int DEFAULT_SPECIFIED_LENGTH = -1;

	protected int specifiedPrecision;
	protected static final int DEFAULT_SPECIFIED_PRECISION = -1;

	protected int specifiedScale;
	protected static final int DEFAULT_SPECIFIED_SCALE = -1;

	protected JavaPersistentResource javaPersistentResource;
	
	public JavaColumn(IJavaColumnMapping parent, IColumn.Owner owner) {
		super(parent, owner);
	}

	public void initializeFromResource(JavaPersistentResource persistentResource) {
		this.javaPersistentResource = persistentResource;
		this.initializeFromResource(this.columnResource());
	}
	
	@Override
	protected void initializeFromResource(Column column) {
		super.initializeFromResource(column);
		this.specifiedLength = this.specifiedLength(column);
		this.specifiedPrecision = this.specifiedPrecision(column);
		this.specifiedScale = this.specifiedScale(column);
	}
	
	@Override
	protected Column columnResource() {
		return (Column) this.javaPersistentResource.nonNullAnnotation(Column.ANNOTATION_NAME);
	}
	
	public int getLength() {
		return (this.getSpecifiedLength() == DEFAULT_SPECIFIED_LENGTH) ? getDefaultLength() : this.getSpecifiedLength();
	}

	public int getDefaultLength() {
		return IColumn.DEFAULT_LENGTH;
	}
	
	public int getSpecifiedLength() {
		return this.specifiedLength;
	}

	public void setSpecifiedLength(int newSpecifiedLength) {
		int oldSpecifiedLength = this.specifiedLength;
		this.specifiedLength = newSpecifiedLength;
		columnResource().setLength(newSpecifiedLength);
		firePropertyChanged(SPECIFIED_LENGTH_PROPERTY, oldSpecifiedLength, newSpecifiedLength);
	}

	public int getPrecision() {
		return (this.getSpecifiedPrecision() == DEFAULT_SPECIFIED_PRECISION) ? getDefaultPrecision() : this.getSpecifiedPrecision();
	}

	public int getDefaultPrecision() {
		return IColumn.DEFAULT_PRECISION;
	}
	
	public int getSpecifiedPrecision() {
		return this.specifiedPrecision;
	}

	public void setSpecifiedPrecision(int newSpecifiedPrecision) {
		int oldSpecifiedPrecision = this.specifiedPrecision;
		this.specifiedPrecision = newSpecifiedPrecision;
		columnResource().setPrecision(newSpecifiedPrecision);
		firePropertyChanged(SPECIFIED_PRECISION_PROPERTY, oldSpecifiedPrecision, newSpecifiedPrecision);
	}

	public int getScale() {
		return (this.getSpecifiedScale() == DEFAULT_SPECIFIED_SCALE) ? getDefaultScale() : this.getSpecifiedScale();
	}

	public int getDefaultScale() {
		return IColumn.DEFAULT_SCALE;
	}
	
	public int getSpecifiedScale() {
		return this.specifiedScale;
	}

	public void setSpecifiedScale(int newSpecifiedScale) {
		int oldSpecifiedScale = this.specifiedScale;
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
	
	public void update(JavaPersistentResource persistentResource) {
		this.javaPersistentResource = persistentResource;
		this.update(this.columnResource());
	}
	
	@Override
	protected void update(Column column) {
		super.update(column);
		this.setSpecifiedLength(this.specifiedLength(column));
		this.setSpecifiedPrecision(this.specifiedPrecision(column));
		this.setSpecifiedScale(this.specifiedScale(column));
	}
	
	protected int specifiedLength(Column column) {
		return column.getLength();
	}
	
	protected int specifiedPrecision(Column column) {
		return column.getPrecision();
	}
	
	protected int specifiedScale(Column column) {
		return column.getScale();
	}

	@Override
	protected IColumn.Owner owner() {
		return (IColumn.Owner) super.owner();
	}

	@Override
	protected String defaultName() {
		return owner().attributeName();
	}
	
	@Override
	protected String defaultTable() {
		return owner().typeMapping().getTableName();
	}
}

/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.DiscriminatorType;
import org.eclipse.jpt.core.internal.context.base.IDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.base.INamedColumn;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorColumn;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentResource;

public class JavaDiscriminatorColumn extends JavaNamedColumn<DiscriminatorColumn>
	implements IJavaDiscriminatorColumn
{

	protected DiscriminatorType specifiedDiscriminatorType;

	protected Integer specifiedLength;
	
	protected JavaPersistentResource persistenceResource;
	
	public JavaDiscriminatorColumn(IJavaEntity parent, INamedColumn.Owner owner) {
		super(parent, owner);
	}

	public void initializeFromResource(JavaPersistentResource persistentResource) {
		this.persistenceResource = persistentResource;
		this.initializeFromResource(this.columnResource());
	}
	
	@Override
	public void initializeFromResource(DiscriminatorColumn column) {
		super.initializeFromResource(column);
		this.specifiedDiscriminatorType = this.discriminatorType(column);
		this.specifiedLength = this.length(column);
	}
	
	protected IJavaEntity javaEntity() {
		return (IJavaEntity) super.parent();
	}

	@Override
	protected DiscriminatorColumn columnResource() {
		return (DiscriminatorColumn) this.persistenceResource.nonNullAnnotation(DiscriminatorColumn.ANNOTATION_NAME);
	}
	
	public DiscriminatorType getDiscriminatorType() {
		return (this.getSpecifiedDiscriminatorType() == null) ? this.getDefaultDiscriminatorType() : this.getSpecifiedDiscriminatorType();
	}

	public DiscriminatorType getDefaultDiscriminatorType() {
		return IDiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE;
	}
		
	public DiscriminatorType getSpecifiedDiscriminatorType() {
		return this.specifiedDiscriminatorType;
	}
	
	public void setSpecifiedDiscriminatorType(DiscriminatorType newSpecifiedDiscriminatorType) {
		DiscriminatorType oldDiscriminatorType = this.specifiedDiscriminatorType;
		this.specifiedDiscriminatorType = newSpecifiedDiscriminatorType;
		columnResource().setDiscriminatorType(DiscriminatorType.toJavaResourceModel(newSpecifiedDiscriminatorType));
		firePropertyChanged(IDiscriminatorColumn.SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY, oldDiscriminatorType, newSpecifiedDiscriminatorType);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedDiscriminatorType_(DiscriminatorType newSpecifiedDiscriminatorType) {
		DiscriminatorType oldDiscriminatorType = this.specifiedDiscriminatorType;
		this.specifiedDiscriminatorType = newSpecifiedDiscriminatorType;
		firePropertyChanged(IDiscriminatorColumn.SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY, oldDiscriminatorType, newSpecifiedDiscriminatorType);
	}
		
	public Integer getLength() {
		return (this.getSpecifiedLength() == null) ? this.getDefaultLength() : this.getSpecifiedLength();
	}

	public Integer getDefaultLength() {
		return IDiscriminatorColumn.DEFAULT_LENGTH;
	}

	public Integer getSpecifiedLength() {
		return this.specifiedLength;
	}

	public void setSpecifiedLength(Integer newSpecifiedLength) {
		Integer oldSpecifiedLength = this.specifiedLength;
		this.specifiedLength = newSpecifiedLength;
		columnResource().setLength(newSpecifiedLength);
		firePropertyChanged(IDiscriminatorColumn.SPECIFIED_LENGTH_PROPERTY, oldSpecifiedLength, newSpecifiedLength);
	}

	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedLength_(Integer newSpecifiedLength) {
		Integer oldSpecifiedLength = this.specifiedLength;
		this.specifiedLength = newSpecifiedLength;
		firePropertyChanged(IDiscriminatorColumn.SPECIFIED_LENGTH_PROPERTY, oldSpecifiedLength, newSpecifiedLength);
	}

	@Override
	protected String tableName() {
		return javaEntity().getTableName();
	}
	
	public ITextRange validationTextRange(CompilationUnit astRoot) {
		ITextRange textRange = columnResource().textRange(astRoot);
		return (textRange != null) ? textRange : this.owner().validationTextRange(astRoot);	
	}

	
	// ********** java annotations -> persistence model **********
	
	public void update(JavaPersistentResource persistentResource) {
		this.persistenceResource = persistentResource;
		this.update(this.columnResource());
	}
	
	@Override
	public void update(DiscriminatorColumn discriminatorColumn) {
		super.update(discriminatorColumn);
		this.setSpecifiedDiscriminatorType_(this.discriminatorType(discriminatorColumn));
		this.setSpecifiedLength_(this.length(discriminatorColumn));
	}
	
	protected DiscriminatorType discriminatorType(DiscriminatorColumn discriminatorColumn) {
		return DiscriminatorType.fromJavaResourceModel(discriminatorColumn.getDiscriminatorType());
	}
	
	protected Integer length(DiscriminatorColumn discriminatorColumn) {
		return discriminatorColumn.getLength();
	}
}

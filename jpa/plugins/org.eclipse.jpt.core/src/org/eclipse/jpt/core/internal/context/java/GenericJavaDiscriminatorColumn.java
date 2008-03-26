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
import org.eclipse.jpt.core.context.DiscriminatorColumn;
import org.eclipse.jpt.core.context.DiscriminatorType;
import org.eclipse.jpt.core.context.java.JavaDiscriminatorColumn;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaNamedColumn;
import org.eclipse.jpt.core.resource.java.DiscriminatorColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericJavaDiscriminatorColumn extends AbstractJavaNamedColumn<DiscriminatorColumnAnnotation>
	implements JavaDiscriminatorColumn
{

	protected DiscriminatorType specifiedDiscriminatorType;

	protected Integer specifiedLength;
	
	protected JavaResourcePersistentMember persistenceResource;
	
	public GenericJavaDiscriminatorColumn(JavaEntity parent, JavaNamedColumn.Owner owner) {
		super(parent, owner);
	}

	public void initializeFromResource(JavaResourcePersistentMember persistentResource) {
		this.persistenceResource = persistentResource;
		this.initializeFromResource(this.getColumnResource());
	}
	
	@Override
	public void initializeFromResource(DiscriminatorColumnAnnotation column) {
		super.initializeFromResource(column);
		this.specifiedDiscriminatorType = this.discriminatorType(column);
		this.specifiedLength = this.length(column);
	}
	
	protected JavaEntity getJavaEntity() {
		return (JavaEntity) super.getParent();
	}

	@Override
	protected DiscriminatorColumnAnnotation getColumnResource() {
		return (DiscriminatorColumnAnnotation) this.persistenceResource.getNonNullAnnotation(DiscriminatorColumnAnnotation.ANNOTATION_NAME);
	}
	
	public DiscriminatorType getDiscriminatorType() {
		return (this.getSpecifiedDiscriminatorType() == null) ? this.getDefaultDiscriminatorType() : this.getSpecifiedDiscriminatorType();
	}

	public DiscriminatorType getDefaultDiscriminatorType() {
		return DiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE;
	}
		
	public DiscriminatorType getSpecifiedDiscriminatorType() {
		return this.specifiedDiscriminatorType;
	}
	
	public void setSpecifiedDiscriminatorType(DiscriminatorType newSpecifiedDiscriminatorType) {
		DiscriminatorType oldDiscriminatorType = this.specifiedDiscriminatorType;
		this.specifiedDiscriminatorType = newSpecifiedDiscriminatorType;
		getColumnResource().setDiscriminatorType(DiscriminatorType.toJavaResourceModel(newSpecifiedDiscriminatorType));
		firePropertyChanged(DiscriminatorColumn.SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY, oldDiscriminatorType, newSpecifiedDiscriminatorType);
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
		firePropertyChanged(DiscriminatorColumn.SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY, oldDiscriminatorType, newSpecifiedDiscriminatorType);
	}
		
	public Integer getLength() {
		return (this.getSpecifiedLength() == null) ? this.getDefaultLength() : this.getSpecifiedLength();
	}

	public Integer getDefaultLength() {
		return DiscriminatorColumn.DEFAULT_LENGTH;
	}

	public Integer getSpecifiedLength() {
		return this.specifiedLength;
	}

	public void setSpecifiedLength(Integer newSpecifiedLength) {
		Integer oldSpecifiedLength = this.specifiedLength;
		this.specifiedLength = newSpecifiedLength;
		getColumnResource().setLength(newSpecifiedLength);
		firePropertyChanged(DiscriminatorColumn.SPECIFIED_LENGTH_PROPERTY, oldSpecifiedLength, newSpecifiedLength);
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
		firePropertyChanged(DiscriminatorColumn.SPECIFIED_LENGTH_PROPERTY, oldSpecifiedLength, newSpecifiedLength);
	}

	@Override
	protected String getTableName() {
		return getJavaEntity().getTableName();
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = getColumnResource().getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getOwner().getValidationTextRange(astRoot);	
	}

	
	// ********** java annotations -> persistence model **********
	
	public void update(JavaResourcePersistentMember persistentResource) {
		this.persistenceResource = persistentResource;
		this.update(this.getColumnResource());
	}
	
	@Override
	public void update(DiscriminatorColumnAnnotation discriminatorColumn) {
		super.update(discriminatorColumn);
		this.setSpecifiedDiscriminatorType_(this.discriminatorType(discriminatorColumn));
		this.setSpecifiedLength_(this.length(discriminatorColumn));
	}
	
	protected DiscriminatorType discriminatorType(DiscriminatorColumnAnnotation discriminatorColumn) {
		return DiscriminatorType.fromJavaResourceModel(discriminatorColumn.getDiscriminatorType());
	}
	
	protected Integer length(DiscriminatorColumnAnnotation discriminatorColumn) {
		return discriminatorColumn.getLength();
	}
}

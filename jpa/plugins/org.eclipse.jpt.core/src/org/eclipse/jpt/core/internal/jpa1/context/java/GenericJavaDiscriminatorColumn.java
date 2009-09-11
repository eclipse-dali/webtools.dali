/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.DiscriminatorColumn;
import org.eclipse.jpt.core.context.DiscriminatorType;
import org.eclipse.jpt.core.context.java.JavaDiscriminatorColumn;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaNamedColumn;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.DiscriminatorColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaDiscriminatorColumn extends AbstractJavaNamedColumn<DiscriminatorColumnAnnotation>
	implements JavaDiscriminatorColumn
{

	protected DiscriminatorType specifiedDiscriminatorType;

	protected DiscriminatorType defaultDiscriminatorType;
	
	protected Integer specifiedLength;
	
	protected int defaultLength;
	
	protected JavaResourcePersistentMember persistenceResource;
	
	public GenericJavaDiscriminatorColumn(JavaEntity parent, JavaDiscriminatorColumn.Owner owner) {
		super(parent, owner);
	}

	public void initialize(JavaResourcePersistentMember persistentResource) {
		this.persistenceResource = persistentResource;
		this.initialize(this.getResourceColumn());
	}
	
	@Override
	public void initialize(DiscriminatorColumnAnnotation column) {
		super.initialize(column);
		this.defaultDiscriminatorType = this.buildDefaultDiscriminatorType();
		this.defaultLength = this.buildDefaultLength();
		this.specifiedDiscriminatorType = this.getResourceDiscriminatorType(column);
		this.specifiedLength = this.getResourceLength(column);
	}
	
	@Override
	public JavaDiscriminatorColumn.Owner getOwner() {
		return (JavaDiscriminatorColumn.Owner) super.getOwner();
	}
	
	protected JavaEntity getJavaEntity() {
		return (JavaEntity) super.getParent();
	}

	@Override
	protected DiscriminatorColumnAnnotation getResourceColumn() {
		return (DiscriminatorColumnAnnotation) this.persistenceResource.
				getNonNullAnnotation(DiscriminatorColumnAnnotation.ANNOTATION_NAME);
	}
	
	public boolean isResourceSpecified() {
		return getResourceColumn().isSpecified();
	}
	
	public DiscriminatorType getDiscriminatorType() {
		return (this.getSpecifiedDiscriminatorType() == null) ? this.getDefaultDiscriminatorType() : this.getSpecifiedDiscriminatorType();
	}

	public DiscriminatorType getDefaultDiscriminatorType() {
		return this.defaultDiscriminatorType;
	}
	
	protected void setDefaultDiscriminatorType(DiscriminatorType discriminatorType) {
		DiscriminatorType old = this.defaultDiscriminatorType;
		this.defaultDiscriminatorType = discriminatorType;
		firePropertyChanged(DEFAULT_DISCRIMINATOR_TYPE_PROPERTY, old, discriminatorType);
	}
		
	public DiscriminatorType getSpecifiedDiscriminatorType() {
		return this.specifiedDiscriminatorType;
	}
	
	public void setSpecifiedDiscriminatorType(DiscriminatorType newSpecifiedDiscriminatorType) {
		DiscriminatorType oldDiscriminatorType = this.specifiedDiscriminatorType;
		this.specifiedDiscriminatorType = newSpecifiedDiscriminatorType;
		getResourceColumn().setDiscriminatorType(DiscriminatorType.toJavaResourceModel(newSpecifiedDiscriminatorType));
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
		
	public int getLength() {
		return (this.getSpecifiedLength() == null) ? this.getDefaultLength() : this.getSpecifiedLength().intValue();
	}

	public int getDefaultLength() {
		return this.defaultLength;
	}
	
	protected void setDefaultLength(int defaultLength) {
		int old = this.defaultLength;
		this.defaultLength = defaultLength;
		firePropertyChanged(DEFAULT_LENGTH_PROPERTY, old, defaultLength);
	}
	
	public Integer getSpecifiedLength() {
		return this.specifiedLength;
	}

	public void setSpecifiedLength(Integer newSpecifiedLength) {
		Integer oldSpecifiedLength = this.specifiedLength;
		this.specifiedLength = newSpecifiedLength;
		getResourceColumn().setLength(newSpecifiedLength);
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
		return getJavaEntity().getPrimaryTableName();
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = getResourceColumn().getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getOwner().getValidationTextRange(astRoot);	
	}

	
	// ********** java annotations -> persistence model **********
	
	public void update(JavaResourcePersistentMember persistentResource) {
		this.persistenceResource = persistentResource;
		this.update(this.getResourceColumn());
	}
	
	@Override
	public void update(DiscriminatorColumnAnnotation discriminatorColumn) {
		//don't call super because postUpdate() handles updating the default column name
		this.setSpecifiedName_(discriminatorColumn.getName());
		this.setColumnDefinition_(discriminatorColumn.getColumnDefinition());
		this.setSpecifiedDiscriminatorType_(this.getResourceDiscriminatorType(discriminatorColumn));
		this.setSpecifiedLength_(this.getResourceLength(discriminatorColumn));
	}
	
	@Override
	/**
	 * Using postUpdate since these defaults are dependent on the entity hierarchy
	 */
	public void postUpdate() {
		super.postUpdate();
		this.setDefaultName(this.buildDefaultName());
		this.setDefaultDiscriminatorType(this.buildDefaultDiscriminatorType());
		this.setDefaultLength(this.buildDefaultLength());
	}
	
	protected DiscriminatorType getResourceDiscriminatorType(DiscriminatorColumnAnnotation discriminatorColumn) {
		return DiscriminatorType.fromJavaResourceModel(discriminatorColumn.getDiscriminatorType());
	}
	
	protected Integer getResourceLength(DiscriminatorColumnAnnotation discriminatorColumn) {
		return discriminatorColumn.getLength();
	}
	
	@Override
	public JavaEntity getParent() {
		return (JavaEntity) super.getParent();
	}
	
	protected int buildDefaultLength() {
		return this.getOwner().getDefaultLength();
	}
	
	protected DiscriminatorType buildDefaultDiscriminatorType() {
		return this.getOwner().getDefaultDiscriminatorType();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (this.connectionProfileIsActive()) {
			if ( ! this.isResolved()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.DISCRIMINATOR_COLUMN_UNRESOLVED_NAME,
						new String[] {this.getName()}, 
						this,
						this.getNameTextRange(astRoot)
					)
				);
			}
		}
	}
}

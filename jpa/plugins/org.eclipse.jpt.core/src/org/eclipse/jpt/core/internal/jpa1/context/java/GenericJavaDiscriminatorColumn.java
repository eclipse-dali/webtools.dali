/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jpt.core.context.DiscriminatorColumn;
import org.eclipse.jpt.core.context.DiscriminatorType;
import org.eclipse.jpt.core.context.java.JavaDiscriminatorColumn;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaNamedColumn;
import org.eclipse.jpt.core.resource.java.DiscriminatorColumnAnnotation;

public class GenericJavaDiscriminatorColumn extends AbstractJavaNamedColumn<DiscriminatorColumnAnnotation>
	implements JavaDiscriminatorColumn
{

	protected DiscriminatorType specifiedDiscriminatorType;

	protected DiscriminatorType defaultDiscriminatorType;
	
	protected Integer specifiedLength;
	
	protected int defaultLength;
	
	public GenericJavaDiscriminatorColumn(JavaEntity parent, JavaDiscriminatorColumn.Owner owner) {
		super(parent, owner);
	}
	
	@Override
	public void initialize(DiscriminatorColumnAnnotation column) {
		super.initialize(column);
		this.defaultDiscriminatorType = this.buildDefaultDiscriminatorType();
		this.defaultLength = this.buildDefaultLength();
		this.specifiedDiscriminatorType = this.getResourceDiscriminatorType();
		this.specifiedLength = this.getResourceLength();
	}
	
	@Override
	public JavaDiscriminatorColumn.Owner getOwner() {
		return (JavaDiscriminatorColumn.Owner) super.getOwner();
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

	
	// ********** java annotations -> persistence model **********
	
	@Override
	public void update(DiscriminatorColumnAnnotation discriminatorColumn) {
		//don't call super because postUpdate() handles updating the default column name
		this.resourceColumn = discriminatorColumn;
		this.setSpecifiedName_(discriminatorColumn.getName());
		this.setColumnDefinition_(discriminatorColumn.getColumnDefinition());
		this.setSpecifiedDiscriminatorType_(this.getResourceDiscriminatorType());
		this.setSpecifiedLength_(this.getResourceLength());
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
	
	protected DiscriminatorType getResourceDiscriminatorType() {
		return DiscriminatorType.fromJavaResourceModel(getResourceColumn().getDiscriminatorType());
	}
	
	protected Integer getResourceLength() {
		return getResourceColumn().getLength();
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
}

/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.DiscriminatorColumn;
import org.eclipse.jpt.core.context.DiscriminatorType;
import org.eclipse.jpt.core.context.orm.OrmDiscriminatorColumn;
import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn;
import org.eclipse.jpt.core.resource.orm.XmlEntity;

public class GenericOrmDiscriminatorColumn extends AbstractOrmNamedColumn<XmlDiscriminatorColumn>
	implements OrmDiscriminatorColumn
{

	protected DiscriminatorType specifiedDiscriminatorType;
	
	protected DiscriminatorType defaultDiscriminatorType;

//	protected static final int DEFAULT_LENGTH_EDEFAULT = 31;

	protected Integer defaultLength;

	protected Integer specifiedLength;

	protected XmlEntity entity;
	
	public GenericOrmDiscriminatorColumn(OrmJpaContextNode parent, Owner owner) {
		super(parent, owner);
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
		if (oldDiscriminatorType != newSpecifiedDiscriminatorType) {
			if (this.columnResource() != null) {
				this.columnResource().setDiscriminatorType(DiscriminatorType.toOrmResourceModel(newSpecifiedDiscriminatorType));
				if (this.columnResource().isAllFeaturesUnset()) {
					removeColumnResource();
				}
			}
			else if (newSpecifiedDiscriminatorType != null) {
				addColumnResource();
				this.columnResource().setDiscriminatorType(DiscriminatorType.toOrmResourceModel(newSpecifiedDiscriminatorType));
			}
		}
		firePropertyChanged(DiscriminatorColumn.SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY, oldDiscriminatorType, newSpecifiedDiscriminatorType);
	}
	
	protected void setSpecifiedDiscriminatorType_(DiscriminatorType newSpecifiedDiscriminatorType) {
		DiscriminatorType oldDiscriminatorType = this.specifiedDiscriminatorType;
		this.specifiedDiscriminatorType = newSpecifiedDiscriminatorType;
		firePropertyChanged(DiscriminatorColumn.SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY, oldDiscriminatorType, newSpecifiedDiscriminatorType);
	}
		
	public Integer getLength() {
		return (this.getSpecifiedLength() == null) ? this.getDefaultLength() : this.getSpecifiedLength();
	}

	public Integer getDefaultLength() {
		return this.defaultLength;
	}
	
	protected void setDefaultLength(Integer newDefaultLength) {
		Integer oldDefaultLength = this.defaultLength;
		this.defaultLength = newDefaultLength;
		firePropertyChanged(DEFAULT_LENGTH_PROPERTY, oldDefaultLength, newDefaultLength);
	}

	public Integer getSpecifiedLength() {
		return this.specifiedLength;
	}

	public void setSpecifiedLength(Integer newSpecifiedLength) {
		Integer oldSpecifiedLength = this.specifiedLength;
		this.specifiedLength = newSpecifiedLength;
		if (oldSpecifiedLength != newSpecifiedLength) {
			if (this.columnResource() != null) {
				this.columnResource().setLength(newSpecifiedLength);
				if (this.columnResource().isAllFeaturesUnset()) {
					removeColumnResource();
				}
			}
			else if (newSpecifiedLength != null) {
				addColumnResource();
				columnResource().setLength(newSpecifiedLength);
			}
		}
		firePropertyChanged(SPECIFIED_LENGTH_PROPERTY, oldSpecifiedLength, newSpecifiedLength);
	}

	protected void setSpecifiedLength_(Integer newSpecifiedLength) {
		Integer oldSpecifiedLength = this.specifiedLength;
		this.specifiedLength = newSpecifiedLength;
		firePropertyChanged(SPECIFIED_LENGTH_PROPERTY, oldSpecifiedLength, newSpecifiedLength);
	}

	@Override
	protected String tableName() {
		return this.owner().typeMapping().tableName();
	}

	
	
	@Override
	protected XmlDiscriminatorColumn columnResource() {
		return this.entity.getDiscriminatorColumn();
	}
	
	@Override
	protected void addColumnResource() {
		this.entity.setDiscriminatorColumn(OrmFactory.eINSTANCE.createXmlDiscriminatorColumn());		
	}
	
	@Override
	protected void removeColumnResource() {
		this.entity.setDiscriminatorColumn(null);
	}
	
	public void initialize(XmlEntity entity) {
		this.entity = entity;
		this.initialize(this.columnResource());
	}
	
	public void update(XmlEntity entity) {
		this.entity = entity;
		this.update(this.columnResource());
	}

	
	@Override
	protected void initialize(XmlDiscriminatorColumn column) {
		super.initialize(column);
		this.specifiedLength = this.specifiedLength(column);
		this.specifiedDiscriminatorType = this.specifiedDiscriminatorType(column);
		//TODO defaultLength, discriminator type java column
	}
	
	@Override
	protected void update(XmlDiscriminatorColumn column) {
		super.update(column);
		this.setSpecifiedLength_(this.specifiedLength(column));
		this.setSpecifiedDiscriminatorType_(this.specifiedDiscriminatorType(column));
		//TODO defaultLength, scale, precision from java column
	}
	
	protected Integer specifiedLength(XmlDiscriminatorColumn column) {
		return column == null ? null : column.getLength();
	}
	
	protected DiscriminatorType specifiedDiscriminatorType(XmlDiscriminatorColumn column) {
		return column == null ? null : DiscriminatorType.fromOrmResourceModel(column.getDiscriminatorType());
	}

}

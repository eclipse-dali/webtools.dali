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

import org.eclipse.jpt.core.internal.context.base.DiscriminatorType;
import org.eclipse.jpt.core.internal.context.base.IDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.resource.orm.DiscriminatorColumn;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;

public class XmlDiscriminatorColumn extends AbstractXmlNamedColumn<DiscriminatorColumn>
	implements IDiscriminatorColumn
{

	protected DiscriminatorType specifiedDiscriminatorType;
	
	protected DiscriminatorType defaultDiscriminatorType;

//	protected static final int DEFAULT_LENGTH_EDEFAULT = 31;

	protected Integer defaultLength;

	protected Integer specifiedLength;

	protected Entity entity;
	
	protected XmlDiscriminatorColumn(IJpaContextNode parent, Owner owner) {
		super(parent, owner);
	}

//	@Override
//	protected void addInsignificantXmlFeatureIdsTo(Set<Integer> insignificantXmlFeatureIds) {
//		super.addInsignificantXmlFeatureIdsTo(insignificantXmlFeatureIds);
//		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__LENGTH);
//		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__DEFAULT_LENGTH);
//	}

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
		firePropertyChanged(IDiscriminatorColumn.SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY, oldDiscriminatorType, newSpecifiedDiscriminatorType);
	}
	
	protected void setSpecifiedDiscriminatorType_(DiscriminatorType newSpecifiedDiscriminatorType) {
		DiscriminatorType oldDiscriminatorType = this.specifiedDiscriminatorType;
		this.specifiedDiscriminatorType = newSpecifiedDiscriminatorType;
		firePropertyChanged(IDiscriminatorColumn.SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY, oldDiscriminatorType, newSpecifiedDiscriminatorType);
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
		return this.owner().typeMapping().getTableName();
	}

	
	
	@Override
	protected DiscriminatorColumn columnResource() {
		return this.entity.getDiscriminatorColumn();
	}
	
	@Override
	protected void addColumnResource() {
		this.entity.setDiscriminatorColumn(OrmFactory.eINSTANCE.createDiscriminatorColumn());		
	}
	
	@Override
	protected void removeColumnResource() {
		this.entity.setDiscriminatorColumn(null);
	}
	
	public void initialize(Entity entity) {
		this.entity = entity;
		this.initialize(this.columnResource());
	}
	
	public void update(Entity entity) {
		this.entity = entity;
		this.update(this.columnResource());
	}

	
	@Override
	protected void initialize(DiscriminatorColumn column) {
		super.initialize(column);
		this.specifiedLength = this.specifiedLength(column);
		this.specifiedDiscriminatorType = this.specifiedDiscriminatorType(column);
		//TODO defaultLength, discriminator type java column
	}
	
	@Override
	protected void update(DiscriminatorColumn column) {
		super.update(column);
		this.setSpecifiedLength_(this.specifiedLength(column));
		this.setSpecifiedDiscriminatorType_(this.specifiedDiscriminatorType(column));
		//TODO defaultLength, scale, precision from java column
	}
	
	protected Integer specifiedLength(DiscriminatorColumn column) {
		return column == null ? null : column.getLength();
	}
	
	protected DiscriminatorType specifiedDiscriminatorType(DiscriminatorColumn column) {
		return column == null ? null : DiscriminatorType.fromOrmResourceModel(column.getDiscriminatorType());
	}

}

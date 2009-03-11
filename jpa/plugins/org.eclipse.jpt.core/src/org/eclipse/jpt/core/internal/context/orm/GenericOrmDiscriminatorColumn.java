/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.core.context.DiscriminatorColumn;
import org.eclipse.jpt.core.context.DiscriminatorType;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmDiscriminatorColumn;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmDiscriminatorColumn extends AbstractOrmNamedColumn<XmlDiscriminatorColumn>
	implements OrmDiscriminatorColumn
{

	protected DiscriminatorType specifiedDiscriminatorType;
	
	protected DiscriminatorType defaultDiscriminatorType;

	protected Integer specifiedLength;
	
	protected int defaultLength;

	protected XmlEntity entity;
	
	public GenericOrmDiscriminatorColumn(XmlContextNode parent, OrmDiscriminatorColumn.Owner owner) {
		super(parent, owner);
	}
	
	@Override
	public OrmDiscriminatorColumn.Owner getOwner() {
		return (OrmDiscriminatorColumn.Owner) super.getOwner();
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
		if (oldDiscriminatorType != newSpecifiedDiscriminatorType) {
			if (this.getResourceColumn() != null) {
				this.getResourceColumn().setDiscriminatorType(DiscriminatorType.toOrmResourceModel(newSpecifiedDiscriminatorType));
				if (this.getResourceColumn().isUnset()) {
					removeResourceColumn();
				}
			}
			else if (newSpecifiedDiscriminatorType != null) {
				addResourceColumn();
				this.getResourceColumn().setDiscriminatorType(DiscriminatorType.toOrmResourceModel(newSpecifiedDiscriminatorType));
			}
		}
		firePropertyChanged(DiscriminatorColumn.SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY, oldDiscriminatorType, newSpecifiedDiscriminatorType);
	}
	
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
		if (oldSpecifiedLength != newSpecifiedLength) {
			if (this.getResourceColumn() != null) {
				this.getResourceColumn().setLength(newSpecifiedLength);
				if (this.getResourceColumn().isUnset()) {
					removeResourceColumn();
				}
			}
			else if (newSpecifiedLength != null) {
				addResourceColumn();
				getResourceColumn().setLength(newSpecifiedLength);
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
	protected String getOwningTableName() {
		return this.getOwner().getTypeMapping().getPrimaryTableName();
	}

	
	
	@Override
	public XmlDiscriminatorColumn getResourceColumn() {
		return this.entity.getDiscriminatorColumn();
	}
	
	@Override
	protected void addResourceColumn() {
		this.entity.setDiscriminatorColumn(OrmFactory.eINSTANCE.createXmlDiscriminatorColumn());		
	}
	
	@Override
	protected void removeResourceColumn() {
		this.entity.setDiscriminatorColumn(null);
	}
	
	public boolean isResourceSpecified() {
		return getResourceColumn() != null;
	}
	
	public void initialize(XmlEntity entity) {
		this.entity = entity;
		this.initialize(this.getResourceColumn());
	}
	
	public void update(XmlEntity entity) {
		this.entity = entity;
		this.update(this.getResourceColumn());
	}

	
	@Override
	protected void initialize(XmlDiscriminatorColumn column) {
		super.initialize(column);
		this.defaultDiscriminatorType = this.buildDefaultDiscriminatorType();
		this.defaultLength = this.buildDefaultLength();
		this.specifiedLength = this.getResourceLength(column);
		this.specifiedDiscriminatorType = this.getResourceDiscriminatorType(column);
	}
	
	@Override
	protected void update(XmlDiscriminatorColumn column) {
		this.setSpecifiedName_(this.getResourceColumnName(column));
		this.setColumnDefinition_(this.getResourceColumnDefinition(column));	
		this.setSpecifiedLength_(this.getResourceLength(column));
		this.setSpecifiedDiscriminatorType_(this.getResourceDiscriminatorType(column));
	}
	
	@Override
	public void postUpdate() {
		super.postUpdate();
		this.setDefaultName(this.getOwnerDefaultColumnName());
		this.setDefaultDiscriminatorType(this.buildDefaultDiscriminatorType());
		this.setDefaultLength(this.buildDefaultLength());		
	}
	
	protected Integer getResourceLength(XmlDiscriminatorColumn column) {
		return column == null ? null : column.getLength();
	}
	
	protected DiscriminatorType getResourceDiscriminatorType(XmlDiscriminatorColumn column) {
		return column == null ? null : DiscriminatorType.fromOrmResourceModel(column.getDiscriminatorType());
	}
	
	protected int buildDefaultLength() {
		return this.getOwner().getDefaultLength();
	}
	
	protected DiscriminatorType buildDefaultDiscriminatorType() {
		return this.getOwner().getDefaultDiscriminatorType();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (this.connectionProfileIsActive()) {
			if ( ! this.isResolved()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.DISCRIMINATOR_COLUMN_UNRESOLVED_NAME,
						new String[] {this.getName()}, 
						this,
						this.getNameTextRange()
					)
				);
			}
		}
	}
}

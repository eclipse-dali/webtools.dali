/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.DiscriminatorType;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmNamedColumn;
import org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn;

/**
 * <code>orm.xml</code> discriminator column
 */
public class GenericOrmDiscriminatorColumn
	extends AbstractOrmNamedColumn<XmlDiscriminatorColumn, OrmDiscriminatorColumn.Owner>
	implements OrmDiscriminatorColumn
{
	protected DiscriminatorType specifiedDiscriminatorType;
	protected DiscriminatorType defaultDiscriminatorType;

	protected Integer specifiedLength;
	protected int defaultLength;


	public GenericOrmDiscriminatorColumn(XmlContextNode parent, OrmDiscriminatorColumn.Owner owner) {
		super(parent, owner);
		this.specifiedDiscriminatorType = this.buildSpecifiedDiscriminatorType();
		this.specifiedLength = this.buildSpecifiedLength();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedDiscriminatorType_(this.buildSpecifiedDiscriminatorType());
		this.setSpecifiedLength_(this.buildSpecifiedLength());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultDiscriminatorType(this.buildDefaultDiscriminatorType());
		this.setDefaultLength(this.buildDefaultLength());
	}


	// ********** XML column **********

	@Override
	public XmlDiscriminatorColumn getXmlColumn() {
		return this.owner.getXmlColumn();
	}

	@Override
	protected XmlDiscriminatorColumn buildXmlColumn() {
		return this.owner.buildXmlColumn();
	}

	@Override
	protected void removeXmlColumn() {
		this.owner.removeXmlColumn();
	}


	// ********** discriminator type **********

	public DiscriminatorType getDiscriminatorType() {
		return (this.specifiedDiscriminatorType != null) ? this.specifiedDiscriminatorType : this.defaultDiscriminatorType;
	}

	public DiscriminatorType getSpecifiedDiscriminatorType() {
		return this.specifiedDiscriminatorType;
	}

	public void setSpecifiedDiscriminatorType(DiscriminatorType discriminatorType) {
		if (this.valuesAreDifferent(this.specifiedDiscriminatorType, discriminatorType)) {
			XmlDiscriminatorColumn xmlColumn = this.getXmlColumnForUpdate();
			this.setSpecifiedDiscriminatorType_(discriminatorType);
			xmlColumn.setDiscriminatorType(DiscriminatorType.toOrmResourceModel(discriminatorType));
			this.removeXmlColumnIfUnset();
		}
	}

	protected void setSpecifiedDiscriminatorType_(DiscriminatorType discriminatorType) {
		DiscriminatorType old = this.specifiedDiscriminatorType;
		this.specifiedDiscriminatorType = discriminatorType;
		this.firePropertyChanged(SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY, old, discriminatorType);
	}

	protected DiscriminatorType buildSpecifiedDiscriminatorType() {
		XmlDiscriminatorColumn xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : DiscriminatorType.fromOrmResourceModel(xmlColumn.getDiscriminatorType());
	}

	public DiscriminatorType getDefaultDiscriminatorType() {
		return this.defaultDiscriminatorType;
	}

	protected void setDefaultDiscriminatorType(DiscriminatorType discriminatorType) {
		DiscriminatorType old = this.defaultDiscriminatorType;
		this.defaultDiscriminatorType = discriminatorType;
		this.firePropertyChanged(DEFAULT_DISCRIMINATOR_TYPE_PROPERTY, old, discriminatorType);
	}

	protected DiscriminatorType buildDefaultDiscriminatorType() {
		return this.owner.getDefaultDiscriminatorType();
	}


	// ********** length **********

	public int getLength() {
		return (this.specifiedLength != null) ? this.specifiedLength.intValue() : this.defaultLength;
	}

	public Integer getSpecifiedLength() {
		return this.specifiedLength;
	}

	public void setSpecifiedLength(Integer length) {
		if (this.valuesAreDifferent(this.specifiedLength, length)) {
			XmlDiscriminatorColumn xmlColumn = this.getXmlColumnForUpdate();
			this.setSpecifiedLength_(length);
			xmlColumn.setLength(length);
			this.removeXmlColumnIfUnset();
		}
	}

	protected void setSpecifiedLength_(Integer length) {
		Integer old = this.specifiedLength;
		this.specifiedLength = length;
		this.firePropertyChanged(SPECIFIED_LENGTH_PROPERTY, old, length);
	}

	protected Integer buildSpecifiedLength() {
		XmlDiscriminatorColumn xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getLength();
	}

	public int getDefaultLength() {
		return this.defaultLength;
	}

	protected void setDefaultLength(int defaultLength) {
		int old = this.defaultLength;
		this.defaultLength = defaultLength;
		this.firePropertyChanged(DEFAULT_LENGTH_PROPERTY, old, defaultLength);
	}

	protected int buildDefaultLength() {
		return this.owner.getDefaultLength();
	}


	// ********** validation **********

	public boolean isResourceSpecified() {
		return this.getXmlColumn() != null;
	}
}

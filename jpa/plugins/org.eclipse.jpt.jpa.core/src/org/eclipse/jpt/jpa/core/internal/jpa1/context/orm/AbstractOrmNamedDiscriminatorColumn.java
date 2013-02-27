/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmNamedColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlBaseDiscriminatorColumn;

/**
 * <code>orm.xml</code> named discriminator column
 */
public abstract class AbstractOrmNamedDiscriminatorColumn<X extends XmlBaseDiscriminatorColumn, O extends ReadOnlyNamedDiscriminatorColumn.Owner>
	extends AbstractOrmNamedColumn<JpaContextModel, X, O>
	implements SpecifiedNamedDiscriminatorColumn
{
	protected DiscriminatorType specifiedDiscriminatorType;
	protected DiscriminatorType defaultDiscriminatorType;

	protected Integer specifiedLength;
	protected int defaultLength = DEFAULT_LENGTH;


	protected AbstractOrmNamedDiscriminatorColumn(JpaContextModel parent, O owner) {
		this(parent, owner, null);
	}

	protected AbstractOrmNamedDiscriminatorColumn(JpaContextModel parent, O owner, X xmlColumn) {
		super(parent, owner, xmlColumn);
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


	// ********** discriminator type **********

	public DiscriminatorType getDiscriminatorType() {
		return (this.specifiedDiscriminatorType != null) ? this.specifiedDiscriminatorType : this.defaultDiscriminatorType;
	}

	public DiscriminatorType getSpecifiedDiscriminatorType() {
		return this.specifiedDiscriminatorType;
	}

	public void setSpecifiedDiscriminatorType(DiscriminatorType discriminatorType) {
		if (this.valuesAreDifferent(this.specifiedDiscriminatorType, discriminatorType)) {
			X xmlColumn = this.getXmlColumnForUpdate();
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
		X xmlColumn = this.getXmlColumn();
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
			X xmlColumn = this.getXmlColumnForUpdate();
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
		X xmlColumn = this.getXmlColumn();
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
}

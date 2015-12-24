/*******************************************************************************
 * Copyright (c) 2011, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.NamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmNamedColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlBaseDiscriminatorColumn;

/**
 * <code>orm.xml</code> named discriminator column
 */
public abstract class AbstractOrmNamedDiscriminatorColumn<PA extends NamedDiscriminatorColumn.ParentAdapter, X extends XmlBaseDiscriminatorColumn>
	extends AbstractOrmNamedColumn<PA, X>
	implements SpecifiedNamedDiscriminatorColumn
{
	protected DiscriminatorType specifiedDiscriminatorType;
	protected DiscriminatorType defaultDiscriminatorType;

	protected Integer specifiedLength;
	protected int defaultLength = DEFAULT_LENGTH;


	protected AbstractOrmNamedDiscriminatorColumn(PA parentAdapter) {
		this(parentAdapter, null);
	}

	protected AbstractOrmNamedDiscriminatorColumn(PA parentAdapter, X xmlColumn) {
		super(parentAdapter, xmlColumn);
		this.specifiedDiscriminatorType = this.buildSpecifiedDiscriminatorType();
		this.specifiedLength = this.buildSpecifiedLength();
	}

	
	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedDiscriminatorType_(this.buildSpecifiedDiscriminatorType());
		this.setSpecifiedLength_(this.buildSpecifiedLength());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
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
		if (ObjectTools.notEquals(this.specifiedDiscriminatorType, discriminatorType)) {
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
		return this.parentAdapter.getDefaultDiscriminatorType();
	}


	// ********** length **********

	public int getLength() {
		return (this.specifiedLength != null) ? this.specifiedLength.intValue() : this.defaultLength;
	}

	public Integer getSpecifiedLength() {
		return this.specifiedLength;
	}

	public void setSpecifiedLength(Integer length) {
		if (ObjectTools.notEquals(this.specifiedLength, length)) {
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
		return this.parentAdapter.getDefaultLength();
	}
}

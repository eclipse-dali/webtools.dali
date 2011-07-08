/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.jpa.core.resource.orm.XmlColumn;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Specified <code>orm.xml</code> attribute override
 */
public class GenericOrmAttributeOverride
	extends AbstractOrmOverride<OrmAttributeOverrideContainer, XmlAttributeOverride>
	implements OrmAttributeOverride, OrmColumn.Owner
{
	protected final OrmColumn column;


	public GenericOrmAttributeOverride(OrmAttributeOverrideContainer parent, XmlAttributeOverride xmlOverride) {
		super(parent, xmlOverride);
		this.column = this.buildColumn();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.column.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.column.update();
	}


	// ********** specified/virtual **********

	@Override
	public OrmVirtualAttributeOverride convertToVirtual() {
		return (OrmVirtualAttributeOverride) super.convertToVirtual();
	}


	// ********** column **********

	public OrmColumn getColumn() {
		return this.column;
	}

	protected OrmColumn buildColumn() {
		return this.getContextNodeFactory().buildOrmColumn(this, this);
	}


	// ********** misc **********

	public void initializeFrom(ReadOnlyAttributeOverride oldOverride) {
		super.initializeFrom(oldOverride);
		this.column.initializeFrom(oldOverride.getColumn());
	}

	public void initializeFromVirtual(ReadOnlyAttributeOverride oldOverride) {
		super.initializeFromVirtual(oldOverride);
		this.column.initializeFromVirtual(oldOverride.getColumn());
	}


	// ********** column owner implementation **********

	public String getDefaultColumnName() {
		return this.name;
	}

	public JptValidator buildColumnValidator(ReadOnlyNamedColumn col, NamedColumnTextRangeResolver textRangeResolver) {
		return this.getContainer().buildColumnValidator(this, (ReadOnlyBaseColumn) col, this, (BaseColumnTextRangeResolver) textRangeResolver);
	}

	public XmlColumn getXmlColumn() {
		return this.getXmlOverride().getColumn();
	}

	public XmlColumn buildXmlColumn() {
		XmlColumn xmlColumn = OrmFactory.eINSTANCE.createXmlColumn();
		this.getXmlOverride().setColumn(xmlColumn);
		return xmlColumn;
	}

	public void removeXmlColumn() {
		this.getXmlOverride().setColumn(null);
	}


	// ********** mapped by relationship **********

	protected boolean attributeIsDerivedId() {
		return this.getTypeMapping().attributeIsDerivedId(this.name);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		if (this.attributeIsDerivedId()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ATTRIBUTE_OVERRIDE_DERIVED_AND_SPECIFIED,
						EMPTY_STRING_ARRAY,
						this,
						this.getValidationTextRange()
					)
				);

			// validate the column if it is specified
			if (this.xmlColumnIsSpecified()) {
				this.column.validate(messages, reporter);
			}
		} else {
			this.column.validate(messages, reporter);
		}
	}

	protected boolean xmlColumnIsSpecified() {
		return this.getXmlColumn() != null;
	}
}

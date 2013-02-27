/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.jpa.core.resource.orm.XmlColumn;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Specified <code>orm.xml</code> attribute override
 */
public class GenericOrmSpecifiedAttributeOverride
	extends AbstractOrmSpecifiedOverride<OrmAttributeOverrideContainer, XmlAttributeOverride>
	implements OrmSpecifiedAttributeOverride, OrmSpecifiedColumn.Owner
{
	protected final OrmSpecifiedColumn column;


	public GenericOrmSpecifiedAttributeOverride(OrmAttributeOverrideContainer parent, XmlAttributeOverride xmlOverride) {
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

	public OrmSpecifiedColumn getColumn() {
		return this.column;
	}

	protected OrmSpecifiedColumn buildColumn() {
		return this.getContextModelFactory().buildOrmColumn(this, this);
	}


	// ********** misc **********

	public void initializeFrom(AttributeOverride oldOverride) {
		super.initializeFrom(oldOverride);
		this.column.initializeFrom(oldOverride.getColumn());
	}

	public void initializeFromVirtual(AttributeOverride oldOverride) {
		super.initializeFromVirtual(oldOverride);
		this.column.initializeFromVirtual(oldOverride.getColumn());
	}


	// ********** column owner implementation **********

	public String getDefaultColumnName(NamedColumn col) {
		return this.name;
	}

	public JptValidator buildColumnValidator(NamedColumn col) {
		return this.getContainer().buildColumnValidator(this, (ReadOnlyBaseColumn) col, this);
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
					this.buildValidationMessage(
						this.getValidationTextRange(),
						JptJpaCoreValidationMessages.ATTRIBUTE_OVERRIDE_DERIVED_AND_SPECIFIED
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

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.column.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		return null;
	}
}

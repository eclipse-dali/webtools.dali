/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.HashBag;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConversionValue;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkConversionValue
	extends AbstractOrmXmlContextNode
	implements EclipseLinkConversionValue
{
	private final XmlConversionValue xmlConversionValue;

	private String dataValue;

	private String objectValue;


	public OrmEclipseLinkConversionValue(OrmEclipseLinkObjectTypeConverter parent, XmlConversionValue xmlConversionValue) {
		super(parent);
		this.xmlConversionValue = xmlConversionValue;
		this.dataValue = xmlConversionValue.getDataValue();
		this.objectValue = xmlConversionValue.getObjectValue();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setDataValue_(this.xmlConversionValue.getDataValue());
		this.setObjectValue_(this.xmlConversionValue.getObjectValue());
	}


	// ********** data value **********

	public String getDataValue() {
		return this.dataValue;
	}

	public void setDataValue(String value) {
		this.setDataValue_(value);
		this.xmlConversionValue.setDataValue(value);
	}

	protected void setDataValue_(String value) {
		String old = this.dataValue;
		this.dataValue = value;
		this.firePropertyChanged(DATA_VALUE_PROPERTY, old, value);
	}


	// ********** object value **********

	public String getObjectValue() {
		return this.objectValue;
	}

	public void setObjectValue(String value) {
		this.setObjectValue_(value);
		this.xmlConversionValue.setObjectValue(value);
	}

	protected void setObjectValue_(String value) {
		String old = this.objectValue;
		this.objectValue = value;
		this.firePropertyChanged(OBJECT_VALUE_PROPERTY, old, value);
	}


	// ********** misc **********

	@Override
	public OrmEclipseLinkObjectTypeConverter getParent() {
		return (OrmEclipseLinkObjectTypeConverter) super.getParent();
	}

	protected OrmEclipseLinkObjectTypeConverter getObjectTypeConverter() {
		return this.getParent();
	}

	public XmlConversionValue getXmlConversionValue() {
		return this.xmlConversionValue;
	}

	protected TextRange getDataValueTextRange() {
		return this.xmlConversionValue.getDataValueTextRange();
	}

	protected TextRange getObjectValueTextRange() {
		return this.xmlConversionValue.getObjectValueTextRange();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateDataValuesUnique(messages);
	}

	protected void validateDataValuesUnique(List<IMessage> messages) {
		HashBag<String> dataValues = CollectionTools.bag(this.getObjectTypeConverter().getDataValues(), this.getObjectTypeConverter().getDataValuesSize());
		if (dataValues.count(this.dataValue) > 1) {
			messages.add(
				DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					EclipseLinkJpaValidationMessages.MULTIPLE_OBJECT_VALUES_FOR_DATA_VALUE,
					new String[] {this.dataValue},
					this,
					this.getDataValueTextRange()
				)
			);
		}
	}

	public TextRange getValidationTextRange() {
		return this.xmlConversionValue.getValidationTextRange();
	}
}

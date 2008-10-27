/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.ConversionValue;
import org.eclipse.jpt.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkOrmConversionValue extends AbstractXmlContextNode implements ConversionValue
{	
	private XmlConversionValue resourceConversionValue;
	
	private String dataValue;
	
	private String objectValue;
	
	public EclipseLinkOrmConversionValue(EclipseLinkOrmObjectTypeConverter parent) {
		super(parent);
	}
	
	@Override
	public EclipseLinkOrmObjectTypeConverter getParent() {
		return (EclipseLinkOrmObjectTypeConverter) super.getParent();
	}

	public TextRange getValidationTextRange() {
		return this.resourceConversionValue.getValidationTextRange();
	}
	
	public String getDataValue() {
		return this.dataValue;
	}

	public void setDataValue(String newDataValue) {
		String oldDataValue = this.dataValue;
		this.dataValue = newDataValue;
		this.resourceConversionValue.setDataValue(newDataValue);
		firePropertyChanged(DATA_VALUE_PROPERTY, oldDataValue, newDataValue);
	}
	
	protected void setDataValue_(String newDataValue) {
		String oldDataValue = this.dataValue;
		this.dataValue = newDataValue;
		firePropertyChanged(DATA_VALUE_PROPERTY, oldDataValue, newDataValue);
	}
	
	public String getObjectValue() {
		return this.objectValue;
	}

	public void setObjectValue(String newObjectValue) {
		String oldObjectValue = this.objectValue;
		this.objectValue = newObjectValue;
		this.resourceConversionValue.setObjectValue(newObjectValue);
		firePropertyChanged(OBJECT_VALUE_PROPERTY, oldObjectValue, newObjectValue);
	}
	
	protected void setObjectValue_(String newObjectValue) {
		String oldObjectValue = this.objectValue;
		this.objectValue = newObjectValue;
		firePropertyChanged(OBJECT_VALUE_PROPERTY, oldObjectValue, newObjectValue);
	}

	public void initialize(XmlConversionValue resourceConversionValue) {
		this.resourceConversionValue = resourceConversionValue;
		this.dataValue = this.dataValue();
		this.objectValue = this.objectValue();
	}
	
	public void update(XmlConversionValue resourceConversionValue) {
		this.resourceConversionValue = resourceConversionValue;
		this.setDataValue_(this.dataValue());
		this.setObjectValue_(this.objectValue());
	}

	protected String dataValue() {
		return this.resourceConversionValue.getDataValue();
	}

	protected String objectValue() {
		return this.resourceConversionValue.getObjectValue();
	}

	public TextRange getDataValueTextRange() {
		return this.resourceConversionValue.getDataValueTextRange();
	}

	public TextRange getObjectValueTextRange() {
		return this.resourceConversionValue.getObjectValueTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		validateDataValuesUnique(messages);
	}
	
	protected void validateDataValuesUnique(List<IMessage> messages) {
		List<String> dataValues = CollectionTools.list(getParent().dataValues());
		dataValues.remove(this.dataValue);
		if (dataValues.contains(this.dataValue)) {
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
}

/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ConversionValueAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkJavaConversionValue
	extends AbstractJavaContextModel<EclipseLinkJavaObjectTypeConverter>
	implements EclipseLinkConversionValue
{
	private final ConversionValueAnnotation conversionValueAnnotation;

	private String dataValue;

	private String objectValue;


	public EclipseLinkJavaConversionValue(EclipseLinkJavaObjectTypeConverter parent, ConversionValueAnnotation conversionValueAnnotation) {
		super(parent);
		this.conversionValueAnnotation = conversionValueAnnotation;
		this.dataValue = conversionValueAnnotation.getDataValue();
		this.objectValue = conversionValueAnnotation.getObjectValue();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setDataValue_(this.conversionValueAnnotation.getDataValue());
		this.setObjectValue_(this.conversionValueAnnotation.getObjectValue());
	}


	// ********** data value **********

	public String getDataValue() {
		return this.dataValue;
	}

	public void setDataValue(String value) {
		this.conversionValueAnnotation.setDataValue(value);
		this.setDataValue_(value);
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
		this.conversionValueAnnotation.setObjectValue(value);
		this.setObjectValue_(value);
	}

	protected void setObjectValue_(String value) {
		String old = this.objectValue;
		this.objectValue = value;
		this.firePropertyChanged(OBJECT_VALUE_PROPERTY, old, value);
	}


	// ********** misc **********

	protected EclipseLinkJavaObjectTypeConverter getObjectTypeConverter() {
		return this.parent;
	}

	public ConversionValueAnnotation getConversionValueAnnotation() {
		return this.conversionValueAnnotation;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
	}

	public TextRange getDataValueTextRange() {
		return this.getValidationTextRange(this.conversionValueAnnotation.getDataValueTextRange());
	}

	protected TextRange getObjectValueTextRange() {
		return this.getValidationTextRange(this.conversionValueAnnotation.getObjectValueTextRange());
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.conversionValueAnnotation.getTextRange();
		return (textRange != null) ? textRange : this.getObjectTypeConverter().getValidationTextRange();
	}

	public boolean isEquivalentTo(EclipseLinkConversionValue conversionValue) {
		return ObjectTools.equals(this.dataValue, conversionValue.getDataValue()) &&
				ObjectTools.equals(this.objectValue, conversionValue.getObjectValue());
	}
}

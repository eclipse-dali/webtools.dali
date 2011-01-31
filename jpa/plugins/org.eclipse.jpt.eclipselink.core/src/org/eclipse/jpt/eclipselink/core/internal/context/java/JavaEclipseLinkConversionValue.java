/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkConversionValueAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkConversionValue
	extends AbstractJavaJpaContextNode
	implements EclipseLinkConversionValue
{
	private final EclipseLinkConversionValueAnnotation conversionValueAnnotation;

	private String dataValue;

	private String objectValue;


	public JavaEclipseLinkConversionValue(JavaEclipseLinkObjectTypeConverter parent, EclipseLinkConversionValueAnnotation conversionValueAnnotation) {
		super(parent);
		this.conversionValueAnnotation = conversionValueAnnotation;
		this.dataValue = conversionValueAnnotation.getDataValue();
		this.objectValue = conversionValueAnnotation.getObjectValue();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
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

	@Override
	public JavaEclipseLinkObjectTypeConverter getParent() {
		return (JavaEclipseLinkObjectTypeConverter) super.getParent();
	}

	protected JavaEclipseLinkObjectTypeConverter getObjectTypeConverter() {
		return this.getParent();
	}

	public EclipseLinkConversionValueAnnotation getConversionValueAnnotation() {
		return this.conversionValueAnnotation;
	}

	protected TextRange getDataValueTextRange(CompilationUnit astRoot) {
		return this.conversionValueAnnotation.getDataValueTextRange(astRoot);
	}

	protected TextRange getObjectValueTextRange(CompilationUnit astRoot) {
		return this.conversionValueAnnotation.getObjectValueTextRange(astRoot);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validateDataValuesUnique(messages, astRoot);
	}

	protected void validateDataValuesUnique(List<IMessage> messages, CompilationUnit astRoot) {
		HashBag<String> dataValues = CollectionTools.bag(this.getObjectTypeConverter().getDataValues(), this.getObjectTypeConverter().getDataValuesSize());
		if (dataValues.count(this.dataValue) > 1) {
			messages.add(
				DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					EclipseLinkJpaValidationMessages.MULTIPLE_OBJECT_VALUES_FOR_DATA_VALUE,
					new String[] {this.dataValue},
					this,
					this.getDataValueTextRange(astRoot)
				)
			);
		}
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.conversionValueAnnotation.getTextRange(astRoot);
	}
}

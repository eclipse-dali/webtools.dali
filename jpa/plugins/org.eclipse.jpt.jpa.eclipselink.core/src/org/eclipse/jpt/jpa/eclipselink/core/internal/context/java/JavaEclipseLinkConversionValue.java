/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkConversionValueAnnotation;
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


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
	}

	public TextRange getDataValueTextRange(CompilationUnit astRoot) {
		return this.getValidationTextRange(this.conversionValueAnnotation.getDataValueTextRange(astRoot), astRoot);
	}

	protected TextRange getObjectValueTextRange(CompilationUnit astRoot) {
		return this.getValidationTextRange(this.conversionValueAnnotation.getObjectValueTextRange(astRoot), astRoot);
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.conversionValueAnnotation.getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getObjectTypeConverter().getValidationTextRange(astRoot);
	}
}

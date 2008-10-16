/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.java.JavaConversionValue;
import org.eclipse.jpt.eclipselink.core.context.java.JavaObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.resource.java.ConversionValueAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkJavaConversionValue extends AbstractJavaJpaContextNode implements JavaConversionValue
{	
	private ConversionValueAnnotation resourceConversionValue;
	
	private String dataValue;
	
	private String objectValue;
	
	public EclipseLinkJavaConversionValue(JavaObjectTypeConverter parent) {
		super(parent);
	}
	
	@Override
	public JavaObjectTypeConverter getParent() {
		return (JavaObjectTypeConverter) super.getParent();
	}
	
	@Override
	protected EclipseLinkJpaFactory getJpaFactory() {
		return (EclipseLinkJpaFactory) super.getJpaFactory();
	}

	protected String getAnnotationName() {
		return ConversionValueAnnotation.ANNOTATION_NAME;
	}		

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.resourceConversionValue.getTextRange(astRoot);
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

	public void initialize(ConversionValueAnnotation resourceConversionValue) {
		this.resourceConversionValue = resourceConversionValue;
		this.dataValue = this.dataValue(resourceConversionValue);
		this.objectValue = this.objectValue(resourceConversionValue);
	}
	
	public void update(ConversionValueAnnotation resourceConversionValue) {
		this.resourceConversionValue = resourceConversionValue;
		this.setDataValue_(this.dataValue(resourceConversionValue));
		this.setObjectValue_(this.objectValue(resourceConversionValue));
	}

	protected String dataValue(ConversionValueAnnotation resourceConversionValue) {
		return resourceConversionValue.getDataValue();
	}

	protected String objectValue(ConversionValueAnnotation resourceConversionValue) {
		return resourceConversionValue.getObjectValue();
	}

	public TextRange getDataValueTextRange(CompilationUnit astRoot) {
		return this.resourceConversionValue.getDataValueTextRange(astRoot);
	}

	public TextRange getObjectValueTextRange(CompilationUnit astRoot) {
		return this.resourceConversionValue.getObjectValueTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		validateDataValuesUnique(messages, astRoot);
	}
	
	protected void validateDataValuesUnique(List<IMessage> messages, CompilationUnit astRoot) {
		List<String> dataValues = CollectionTools.list(getParent().dataValues());
		dataValues.remove(this.dataValue);
		if (dataValues.contains(this.dataValue)) {
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
}

/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkCustomizerAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkCustomizer
	extends AbstractJavaJpaContextNode
	implements EclipseLinkCustomizer
{
	private String specifiedCustomizerClass;

	private String fullyQualifiedCustomizerClass;


	public JavaEclipseLinkCustomizer(EclipseLinkJavaTypeMapping parent) {
		super(parent);
		this.specifiedCustomizerClass = this.buildSpecifiedCustomizerClass();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedCustomizerClass_(this.buildSpecifiedCustomizerClass());
	}

	@Override
	public void update() {
		super.update();
		this.setFullyQualifiedCustomizerClass(this.buildFullyQualifiedCustomizerClass());
	}


	// ********** customizer class **********

	public String getCustomizerClass() {
		return (this.specifiedCustomizerClass != null) ? this.specifiedCustomizerClass : null;
	}

	public String getSpecifiedCustomizerClass() {
		return this.specifiedCustomizerClass;
	}

	public void setSpecifiedCustomizerClass(String customizerClass) {
		if (this.valuesAreDifferent(customizerClass, this.specifiedCustomizerClass)) {
			EclipseLinkCustomizerAnnotation annotation = this.getCustomizerAnnotation();
			if (customizerClass == null) {
				if (annotation != null) {
					this.removeCustomizerAnnotation();
				}
			} else {
				if (annotation == null) {
					annotation = this.addCustomizerAnnotation();
				}
				annotation.setValue(customizerClass);
			}

			this.setSpecifiedCustomizerClass_(customizerClass);
		}
	}

	protected void setSpecifiedCustomizerClass_(String customizerClass) {
		String old = this.specifiedCustomizerClass;
		this.specifiedCustomizerClass = customizerClass;
		this.firePropertyChanged(SPECIFIED_CUSTOMIZER_CLASS_PROPERTY, old, customizerClass);
	}

	protected String buildSpecifiedCustomizerClass() {
		EclipseLinkCustomizerAnnotation annotation = this.getCustomizerAnnotation();
		return (annotation == null) ? null : annotation.getValue();
	}

	public String getDefaultCustomizerClass() {
		return null;  // no default for Java
	}


	// ********** fully-qualified customizer class **********

	public String getFullyQualifiedCustomizerClass() {
		return this.fullyQualifiedCustomizerClass;
	}

	protected void setFullyQualifiedCustomizerClass(String customizerClass) {
		String old = this.fullyQualifiedCustomizerClass;
		this.fullyQualifiedCustomizerClass = customizerClass;
		this.firePropertyChanged(FULLY_QUALIFIED_CUSTOMIZER_CLASS_PROPERTY, old, customizerClass);
	}

	protected String buildFullyQualifiedCustomizerClass() {
		EclipseLinkCustomizerAnnotation annotation = this.getCustomizerAnnotation();
		return (annotation == null) ? null : annotation.getFullyQualifiedCustomizerClassName();
	}


	// ********** customizer annotation **********

	protected EclipseLinkCustomizerAnnotation getCustomizerAnnotation() {
		return (EclipseLinkCustomizerAnnotation) this.getJavaResourceType().getAnnotation(this.getCustomizerAnnotationName());
	}

	protected EclipseLinkCustomizerAnnotation addCustomizerAnnotation() {
		return (EclipseLinkCustomizerAnnotation) this.getJavaResourceType().addAnnotation(this.getCustomizerAnnotationName());
	}

	protected void removeCustomizerAnnotation() {
		this.getJavaResourceType().removeAnnotation(this.getCustomizerAnnotationName());
	}

	protected String getCustomizerAnnotationName() {
		return EclipseLinkCustomizerAnnotation.ANNOTATION_NAME;
	}


	// ********** misc **********

	@Override
	public EclipseLinkJavaTypeMapping getParent() {
		return (EclipseLinkJavaTypeMapping) super.getParent();
	}

	protected EclipseLinkJavaTypeMapping getTypeMapping() {
		return this.getParent();
	}

	protected JavaResourceType getJavaResourceType() {
		return this.getTypeMapping().getJavaResourceType();
	}

	public char getCustomizerClassEnclosingTypeSeparator() {
		return '.';
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateCustomizerClass(messages);
	}

	protected void validateCustomizerClass(List<IMessage> messages) {
		if (this.getFullyQualifiedCustomizerClass() == null) {
			return;
		}
		//if the type cannot be resolved there is no need to perform the following validation,
		//JDT will note the error in the source
		IType customizerJdtType =  JDTTools.findType(this.getJavaProject(), this.getFullyQualifiedCustomizerClass());
		if (customizerJdtType == null) {
			return;
		}
		if (!JDTTools.typeHasPublicZeroArgConstructor(customizerJdtType)) {
			messages.add(
					this.buildErrorValidationMessage(
							this.getCustomizerClassTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.DESCRIPTOR_CUSTOMIZER_CLASS_NOT_VALID,
							this.getFullyQualifiedCustomizerClass()
					)
			);
		} 
		if (!customizerClassImplementsInterface(ECLIPSELINK_DESCRIPTOR_CUSTOMIZER_CLASS_NAME)) {
			messages.add(
					this.buildErrorValidationMessage(
							this.getCustomizerClassTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.DESCRIPTOR_CUSTOMIZER_CLASS_IMPLEMENTS_DESCRIPTOR_CUSTOMIZER,
							this.getFullyQualifiedCustomizerClass()
					)
			);
		}
	}

	protected boolean customizerClassImplementsInterface(String interfaceName) {
		return this.typeImplementsInterface(this.getFullyQualifiedCustomizerClass(), interfaceName);
	}

	/**
	 * Add <code>null</code> check.
	 */
	protected boolean typeImplementsInterface(String typeName, String interfaceName) {
		return (typeName != null) && 
				JDTTools.typeIsSubType(this.getJavaProject(), typeName, interfaceName);
	}

	protected TextRange getCustomizerClassTextRange() {
		return this.getValidationTextRange(this.getCustomizerAnnotation().getValueTextRange());
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getCustomizerAnnotation().getTextRange();
		return (textRange != null) ? textRange : this.getTypeMapping().getValidationTextRange();
	}
}

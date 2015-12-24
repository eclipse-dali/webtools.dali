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
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.TypeTools;
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.CustomizerAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkJavaCustomizer
	extends AbstractJavaContextModel<EclipseLinkJavaTypeMapping>
	implements EclipseLinkCustomizer
{
	private String specifiedCustomizerClass;

	private String fullyQualifiedCustomizerClass;


	public EclipseLinkJavaCustomizer(EclipseLinkJavaTypeMapping parent) {
		super(parent);
		this.specifiedCustomizerClass = this.buildSpecifiedCustomizerClass();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedCustomizerClass_(this.buildSpecifiedCustomizerClass());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
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
		if (ObjectTools.notEquals(customizerClass, this.specifiedCustomizerClass)) {
			CustomizerAnnotation annotation = this.getCustomizerAnnotation();
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
		CustomizerAnnotation annotation = this.getCustomizerAnnotation();
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
		CustomizerAnnotation annotation = this.getCustomizerAnnotation();
		return (annotation == null) ? null : annotation.getFullyQualifiedCustomizerClassName();
	}


	// ********** customizer annotation **********

	protected CustomizerAnnotation getCustomizerAnnotation() {
		return (CustomizerAnnotation) this.getJavaResourceType().getAnnotation(this.getCustomizerAnnotationName());
	}

	protected CustomizerAnnotation addCustomizerAnnotation() {
		return (CustomizerAnnotation) this.getJavaResourceType().addAnnotation(this.getCustomizerAnnotationName());
	}

	protected void removeCustomizerAnnotation() {
		this.getJavaResourceType().removeAnnotation(this.getCustomizerAnnotationName());
	}

	protected String getCustomizerAnnotationName() {
		return CustomizerAnnotation.ANNOTATION_NAME;
	}


	// ********** misc **********

	protected EclipseLinkJavaTypeMapping getTypeMapping() {
		return this.parent;
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
		IType customizerJdtType =  JavaProjectTools.findType(this.getJavaProject(), this.getFullyQualifiedCustomizerClass());
		if (customizerJdtType == null) {
			return;
		}
		if (!TypeTools.hasPublicZeroArgConstructor(customizerJdtType)) {
			messages.add(
					this.buildValidationMessage(
							this.getCustomizerClassTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.DESCRIPTOR_CUSTOMIZER_CLASS_NOT_VALID,
							this.getFullyQualifiedCustomizerClass()
					)
			);
		} 
		if (!customizerClassImplementsInterface(ECLIPSELINK_DESCRIPTOR_CUSTOMIZER_CLASS_NAME)) {
			messages.add(
					this.buildValidationMessage(
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
				TypeTools.isSubType(typeName, interfaceName, this.getJavaProject());
	}

	protected TextRange getCustomizerClassTextRange() {
		return this.getValidationTextRange(this.getCustomizerAnnotation().getValueTextRange());
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getCustomizerAnnotation().getTextRange();
		return (textRange != null) ? textRange : this.getTypeMapping().getValidationTextRange();
	}
}

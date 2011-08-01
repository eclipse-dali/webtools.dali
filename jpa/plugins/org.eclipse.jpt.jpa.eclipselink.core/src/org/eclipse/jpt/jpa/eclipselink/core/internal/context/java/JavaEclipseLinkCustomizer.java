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
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkCustomizerAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkCustomizer
	extends AbstractJavaJpaContextNode
	implements EclipseLinkCustomizer
{
	private String specifiedCustomizerClass;

	private String fullyQualifiedCustomizerClass;
		public static final String FULLY_QUALIFIED_CUSTOMIZER_CLASS_PROPERTY = "fullyQualifiedCustomizerClass"; //$NON-NLS-1$


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
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validateCustomizerClass(messages, astRoot);
	}

	protected void validateCustomizerClass(List<IMessage> messages,CompilationUnit astRoot) {
		IJavaProject javaProject = getPersistenceUnit().getJpaProject().getJavaProject();
		EclipseLinkCustomizerAnnotation annotation = this.getCustomizerAnnotation();
		if (annotation != null && annotation.getValue() != null && 
				//if the type cannot be resolved there is no need to perform the following validation,
				//JDT will note the error in the source
				JDTTools.findType(javaProject, annotation.getValue()) != null) {
			if (!JDTTools.classHasPublicZeroArgConstructor(javaProject, this.getFullyQualifiedCustomizerClass())) {
				messages.add(
						DefaultEclipseLinkJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								EclipseLinkJpaValidationMessages.DESCRIPTOR_CUSTOMIZER_CLASS_NOT_VALID,
								new String[] {this.getFullyQualifiedCustomizerClass()},
								this,
								this.getCustomizerClassTextRange(astRoot)
						)
				);
			} else if (!annotation.customizerClassImplementsInterface(ECLIPSELINK_DESCRIPTOR_CUSTOMIZER_CLASS_NAME, astRoot)) {
				messages.add(
						DefaultEclipseLinkJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								EclipseLinkJpaValidationMessages.DESCRIPTOR_CUSTOMIZER_CLASS_IMPLEMENTS_DESCRIPTOR_CUSTOMIZER,
								new String[] {this.getFullyQualifiedCustomizerClass()},
								this,
								this.getCustomizerClassTextRange(astRoot)
						)
				);
			} 
		}
	}

	protected TextRange getCustomizerClassTextRange(CompilationUnit astRoot) {
		return this.getValidationTextRange(this.getCustomizerAnnotation().getValueTextRange(astRoot), astRoot);
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getCustomizerAnnotation().getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getTypeMapping().getValidationTextRange(astRoot);
	}
}

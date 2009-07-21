/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.Customizer;
import org.eclipse.jpt.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkCustomizerAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkJavaCustomizer extends AbstractJavaJpaContextNode implements Customizer
{	
	private JavaResourcePersistentType resourcePersistentType;
	
	private String customizerClass;
	
	public EclipseLinkJavaCustomizer(JavaJpaContextNode parent) {
		super(parent);
	}
	
	public char getCustomizerClassEnclosingTypeSeparator() {
		return '.';
	}
	
	@Override
	protected EclipseLinkJpaFactory getJpaFactory() {
		return (EclipseLinkJpaFactory) super.getJpaFactory();
	}

	protected String getAnnotationName() {
		return EclipseLinkCustomizerAnnotation.ANNOTATION_NAME;
	}
		
	protected void addResourceCustomizer() {
		this.resourcePersistentType.addSupportingAnnotation(getAnnotationName());
	}
	
	protected void removeResourceCustomizer() {
		this.resourcePersistentType.removeSupportingAnnotation(getAnnotationName());
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getResourceCustomizer().getTextRange(astRoot);
	}

	protected EclipseLinkCustomizerAnnotation getResourceCustomizer() {
		return (EclipseLinkCustomizerAnnotation) this.resourcePersistentType.getSupportingAnnotation(getAnnotationName());
	}
	
	public String getCustomizerClass() {
		return getSpecifiedCustomizerClass();
	}
	
	public String getDefaultCustomizerClass() {
		return null;
	}
	
	public String getSpecifiedCustomizerClass() {
		return this.customizerClass;
	}

	public void setSpecifiedCustomizerClass(String newCustomizerClass) {
		if (attributeValueHasNotChanged(this.customizerClass, newCustomizerClass)) {
			return;
		}
		String oldCustomizerClass = this.customizerClass;
		this.customizerClass = newCustomizerClass;
		if (this.customizerClass != null) {
			addResourceCustomizer();
		}
		else {
			removeResourceCustomizer();
		}
		if (newCustomizerClass != null) {
			getResourceCustomizer().setValue(newCustomizerClass);
		}
		firePropertyChanged(SPECIFIED_CUSTOMIZER_CLASS_PROPERTY, oldCustomizerClass, newCustomizerClass);
	}
	
	protected void setCustomizerClass_(String newCustomizerClass) {
		String oldCustomizerClass = this.customizerClass;
		this.customizerClass = newCustomizerClass;
		firePropertyChanged(SPECIFIED_CUSTOMIZER_CLASS_PROPERTY, oldCustomizerClass, newCustomizerClass);
	}
	
	public void initialize(JavaResourcePersistentType jrpt) {
		this.resourcePersistentType = jrpt;
		EclipseLinkCustomizerAnnotation resourceCustomizer = getResourceCustomizer();
		this.customizerClass = this.customizerClass(resourceCustomizer);
	}
	
	public void update(JavaResourcePersistentType jrpt) {
		this.resourcePersistentType = jrpt;
		EclipseLinkCustomizerAnnotation resourceCustomizer = getResourceCustomizer();
		this.setCustomizerClass_(this.customizerClass(resourceCustomizer));
	}
	
	protected String customizerClass(EclipseLinkCustomizerAnnotation resourceCustomizer) {
		return resourceCustomizer == null ? null : resourceCustomizer.getValue();
	}

	public TextRange getCustomizerClassTextRange(CompilationUnit astRoot) {
		return getResourceCustomizer().getValueTextRange(astRoot);
	}
	
	//************ validation ***************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		validateConverterClass(messages, astRoot);
	}
	
	protected void validateConverterClass(List<IMessage> messages, CompilationUnit astRoot) {
		EclipseLinkCustomizerAnnotation resourceCustomizer = getResourceCustomizer();
		if (resourceCustomizer != null && !resourceCustomizer.customizerClassImplementsInterface(ECLIPSELINK_DESCRIPTOR_CUSTOMIZER_CLASS_NAME, astRoot)) {
			messages.add(
				DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					EclipseLinkJpaValidationMessages.CUSTOMIZER_CLASS_IMPLEMENTS_DESCRIPTOR_CUSTOMIZER,
					new String[] {this.customizerClass},
					this, 
					getCustomizerClassTextRange(astRoot)
				)
			);
		}
	}
}

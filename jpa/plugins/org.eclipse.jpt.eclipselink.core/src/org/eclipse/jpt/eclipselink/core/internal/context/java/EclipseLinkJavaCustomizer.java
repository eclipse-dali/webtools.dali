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
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.java.JavaCustomizer;
import org.eclipse.jpt.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.resource.java.CustomizerAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkJavaCustomizer extends AbstractJavaJpaContextNode implements JavaCustomizer
{	
	private JavaResourcePersistentType resourcePersistentType;
	
	private String customizerClass;
	
	public EclipseLinkJavaCustomizer(JavaJpaContextNode parent) {
		super(parent);
	}
	
	@Override
	protected EclipseLinkJpaFactory getJpaFactory() {
		return (EclipseLinkJpaFactory) super.getJpaFactory();
	}

	protected String getAnnotationName() {
		return CustomizerAnnotation.ANNOTATION_NAME;
	}
		
	protected void addResourceCustomizer() {
		this.resourcePersistentType.addAnnotation(getAnnotationName());
	}
	
	protected void removeResourceCustomizer() {
		this.resourcePersistentType.removeAnnotation(getAnnotationName());
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getResourceCustomizer().getTextRange(astRoot);
	}

	protected CustomizerAnnotation getResourceCustomizer() {
		return (CustomizerAnnotation) this.resourcePersistentType.getAnnotation(getAnnotationName());
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
		CustomizerAnnotation resourceCustomizer = getResourceCustomizer();
		this.customizerClass = this.customizerClass(resourceCustomizer);
	}
	
	public void update(JavaResourcePersistentType jrpt) {
		this.resourcePersistentType = jrpt;
		CustomizerAnnotation resourceCustomizer = getResourceCustomizer();
		this.setCustomizerClass_(this.customizerClass(resourceCustomizer));
	}
	
	protected String customizerClass(CustomizerAnnotation resourceCustomizer) {
		return resourceCustomizer == null ? null : resourceCustomizer.getValue();
	}

	public TextRange getCustomizerClassTextRange(CompilationUnit astRoot) {
		return getResourceCustomizer().getValueTextRange(astRoot);
	}
	
	//************ validation ***************
	
	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		validateConverterClass(messages, astRoot);
	}
	
	protected void validateConverterClass(List<IMessage> messages, CompilationUnit astRoot) {
		CustomizerAnnotation resourceCustomizer = getResourceCustomizer();
		if (resourceCustomizer != null && !resourceCustomizer.implementsDescriptorCustomizer()) {
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

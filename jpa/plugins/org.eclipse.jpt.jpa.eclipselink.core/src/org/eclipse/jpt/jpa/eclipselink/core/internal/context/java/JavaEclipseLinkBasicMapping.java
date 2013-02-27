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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.java.JavaConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratedValueMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaBasicMapping;
import org.eclipse.jpt.jpa.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkBasicMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMutable;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkConverterContainer;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkBasicMapping
	extends AbstractJavaBasicMapping
	implements
		EclipseLinkBasicMapping,
		EclipseLinkJavaConvertibleMapping,
		JavaGeneratorContainer.ParentAdapter,
		JavaGeneratedValueMapping
{
	protected final JavaEclipseLinkMutable mutable;
	
	protected final JavaEclipseLinkConverterContainer converterContainer;

	protected final JavaGeneratorContainer generatorContainer;

	protected JavaGeneratedValue generatedValue;


	public JavaEclipseLinkBasicMapping(JavaSpecifiedPersistentAttribute parent) {
		super(parent);
		this.mutable = new JavaEclipseLinkMutable(this);
		this.converterContainer = this.buildConverterContainer();
		this.generatorContainer = this.buildGeneratorContainer();
		this.generatedValue = this.buildGeneratedValue();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.mutable.synchronizeWithResourceModel();
		this.converterContainer.synchronizeWithResourceModel();
		this.generatorContainer.synchronizeWithResourceModel();
		this.syncGeneratedValue();
	}

	@Override
	public void update() {
		super.update();
		this.mutable.update();
		this.converterContainer.update();
		this.generatorContainer.update();
		if (this.generatedValue != null) {
			this.generatedValue.update();
		}
	}


	// ********** mutable **********

	public EclipseLinkMutable getMutable() {
		return this.mutable;
	}


	// ********** converters **********

	public JavaEclipseLinkConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected JavaEclipseLinkConverterContainer buildConverterContainer() {
		return new JavaEclipseLinkConverterContainerImpl(this);
	}

	// ********** converter container parent adapter **********

	public JpaContextModel getConverterContainerParent() {
		return this;  // no adapter
	}

	public JavaResourceAnnotatedElement getJavaResourceAnnotatedElement() {
		return this.getResourceAttribute();
	}

	public boolean parentSupportsConverters() {
		return ! this.getPersistentAttribute().isVirtual();
	}

	// ********** converter adapters **********

	/**
	 * put the EclipseLink convert adapter first - this is the order EclipseLink searches
	 */
	@Override
	protected Iterable<JavaConverter.Adapter> getConverterAdapters() {
		return IterableTools.insert(
				JavaEclipseLinkConvert.Adapter.instance(),
				super.getConverterAdapters()
			);
	}

	// ********** generator container **********

	public JavaGeneratorContainer getGeneratorContainer() {
		return this.generatorContainer;
	}

	protected JavaGeneratorContainer buildGeneratorContainer() {
		return this.getJpaFactory().buildJavaGeneratorContainer(this);
	}

	@Override
	public Iterable<Generator> getGenerators() {
		return this.generatorContainer.getGenerators();
	}


	// ********** generator container parent adapter **********

	public JpaContextModel getGeneratorContainerParent() {
		return this;  // no adapter
	}

	public JavaResourceMember getResourceAnnotatedElement() {
		return this.getResourceAttribute();
	}

	public boolean parentSupportsGenerators() {
		return ! this.getPersistentAttribute().isVirtual();
	}


	// ********** generated value **********

	public JavaGeneratedValue getGeneratedValue() {
		return this.generatedValue;
	}

	public JavaGeneratedValue addGeneratedValue() {
		if (this.generatedValue != null) {
			throw new IllegalStateException("generated value already exists: " + this.generatedValue); //$NON-NLS-1$
		}
		GeneratedValueAnnotation annotation = this.buildGeneratedValueAnnotation();
		JavaGeneratedValue value = this.buildGeneratedValue(annotation);
		this.setGeneratedValue(value);
		return value;
	}

	protected GeneratedValueAnnotation buildGeneratedValueAnnotation() {
		return (GeneratedValueAnnotation) this.getResourceAttribute().addAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
	}

	public void removeGeneratedValue() {
		if (this.generatedValue == null) {
			throw new IllegalStateException("generated value does not exist"); //$NON-NLS-1$
		}
		this.getResourceAttribute().removeAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
		this.setGeneratedValue(null);
	}

	protected JavaGeneratedValue buildGeneratedValue() {
		GeneratedValueAnnotation annotation = this.getGeneratedValueAnnotation();
		return (annotation == null) ? null : this.buildGeneratedValue(annotation);
	}

	protected GeneratedValueAnnotation getGeneratedValueAnnotation() {
		return (GeneratedValueAnnotation) this.getResourceAttribute().getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
	}

	protected JavaGeneratedValue buildGeneratedValue(GeneratedValueAnnotation generatedValueAnnotation) {
		return this.getJpaFactory().buildJavaGeneratedValue(this, generatedValueAnnotation);
	}

	protected void syncGeneratedValue() {
		GeneratedValueAnnotation annotation = this.getGeneratedValueAnnotation();
		if (annotation == null) {
			if (this.generatedValue != null) {
				this.setGeneratedValue(null);
			}
		}
		else {
			if ((this.generatedValue != null) && (this.generatedValue.getGeneratedValueAnnotation() == annotation)) {
				this.generatedValue.synchronizeWithResourceModel();
			} else {
				this.setGeneratedValue(this.buildGeneratedValue(annotation));
			}
		}
	}

	protected void setGeneratedValue(JavaGeneratedValue value) {
		JavaGeneratedValue old = this.generatedValue;
		this.generatedValue = value;
		this.firePropertyChanged(GENERATED_VALUE_PROPERTY, old, value);
	}

	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}

		result = this.generatorContainer.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}

		if (this.generatedValue != null) {
			result = this.generatedValue.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.mutable.validate(messages, reporter);

		this.generatorContainer.validate(messages, reporter);
		if (this.generatedValue != null) {
			this.generatedValue.validate(messages, reporter);
		}
	}
}

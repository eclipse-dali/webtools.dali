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
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaMappedSuperclass;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.CacheableReference2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCacheableReference2_0;
import org.eclipse.jpt.jpa.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkReadOnly;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkMultitenancy2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLink2_3JpaPlatformFactory;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory.EclipseLinkJpaPlatformVersion;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.EclipseLinkMappedSuperclassPrimaryKeyValidator;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.EclipseLinkMappedSuperclassValidator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * EclipseLink
 * Java mapped superclass
 */
public class JavaEclipseLinkMappedSuperclassImpl
	extends AbstractJavaMappedSuperclass
	implements 
		JavaEclipseLinkMappedSuperclass, 
		JavaCacheableReference2_0, 
		EclipseLinkJavaConverterContainer.ParentAdapter,
		JavaGeneratorContainer.Parent
{
	protected final JavaEclipseLinkCaching caching;

	protected final JavaEclipseLinkReadOnly readOnly;

	protected final EclipseLinkJavaConverterContainer converterContainer;

	protected final JavaEclipseLinkChangeTracking changeTracking;

	protected final JavaEclipseLinkCustomizer customizer;

	protected final JavaEclipseLinkMultitenancy2_3 multitenancy;

	protected final JavaGeneratorContainer generatorContainer;

	public JavaEclipseLinkMappedSuperclassImpl(JavaPersistentType parent, MappedSuperclassAnnotation mappingAnnotation) {
		super(parent, mappingAnnotation);
		this.caching = this.buildCaching();
		this.readOnly = this.buildReadOnly();
		this.converterContainer = this.buildConverterContainer();
		this.changeTracking = this.buildChangeTracking();
		this.customizer = this.buildCustomizer();
		this.multitenancy = this.buildMultitenancy();
		this.generatorContainer = this.buildGeneratorContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.caching.synchronizeWithResourceModel();
		this.readOnly.synchronizeWithResourceModel();
		this.converterContainer.synchronizeWithResourceModel();
		this.changeTracking.synchronizeWithResourceModel();
		this.customizer.synchronizeWithResourceModel();
		this.generatorContainer.synchronizeWithResourceModel();
		this.multitenancy.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.caching.update();
		this.readOnly.update();
		this.converterContainer.update();
		this.changeTracking.update();
		this.customizer.update();
		this.multitenancy.update();
		this.generatorContainer.update();
	}


	// ********** caching **********

	public JavaEclipseLinkCaching getCaching() {
		return this.caching;
	}

	protected JavaEclipseLinkCaching buildCaching() {
		return new JavaEclipseLinkCachingImpl(this);
	}


	// ********** read-only **********

	public EclipseLinkReadOnly getReadOnly() {
		return this.readOnly;
	}

	protected JavaEclipseLinkReadOnly buildReadOnly() {
		return new JavaEclipseLinkReadOnly(this);
	}


	// ********** converter container **********

	public EclipseLinkJavaConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected EclipseLinkJavaConverterContainer buildConverterContainer() {
		return new JavaEclipseLinkConverterContainerImpl(this);
	}

	@SuppressWarnings("unchecked")
	public Iterable<EclipseLinkConverter> getConverters() {
		return IterableTools.concatenate(
					this.converterContainer.getConverters(),
					this.getAttributeMappingConverters()
				);
	}

	protected Iterable<EclipseLinkConverter> getAttributeMappingConverters() {
		return IterableTools.removeNulls(this.getAttributeMappingConverters_());
	}

	protected Iterable<EclipseLinkConverter> getAttributeMappingConverters_() {
		return IterableTools.children(this.getAttributeMappings(), EclipseLinkConvertibleMapping.ATTRIBUTE_MAPPING_CONVERTERS_TRANSFORMER);
	}


	// ********** change tracking **********

	public EclipseLinkChangeTracking getChangeTracking() {
		return this.changeTracking;
	}

	protected JavaEclipseLinkChangeTracking buildChangeTracking() {
		return new JavaEclipseLinkChangeTracking(this);
	}


	// ********** customizer **********

	public EclipseLinkCustomizer getCustomizer() {
		return this.customizer;
	}

	protected JavaEclipseLinkCustomizer buildCustomizer() {
		return new JavaEclipseLinkCustomizer(this);
	}


	// ********** multitenancy **********

	public JavaEclipseLinkMultitenancy2_3 getMultitenancy() {
		return this.multitenancy;
	}


	protected JavaEclipseLinkMultitenancy2_3 buildMultitenancy() {
		return this.isEclipseLink2_3Compatible() ?
			new JavaEclipseLinkMultitenancyImpl2_3(this) :
			new NullJavaEclipseLinkMultitenancy2_3(this);
	}

	protected boolean isEclipseLink2_3Compatible() {
		return this.getJpaPlatformVersion().isCompatibleWithEclipseLinkVersion(EclipseLink2_3JpaPlatformFactory.VERSION);
	}

	@Override
	protected EclipseLinkJpaPlatformVersion getJpaPlatformVersion() {
		return (EclipseLinkJpaPlatformVersion) super.getJpaPlatformVersion();
	}

	public boolean isMultitenantMetadataAllowed() {
		return true;
	}


	// ********** generator container **********

	public JavaGeneratorContainer getGeneratorContainer() {
		return this.generatorContainer;
	}

	protected JavaGeneratorContainer buildGeneratorContainer() {
		return this.getJpaFactory().buildJavaGeneratorContainer(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<Generator> getGenerators() {
		return IterableTools.concatenate(
					super.getGenerators(),
					this.generatorContainer.getGenerators()
				);
	}

	// ********** generator container parent **********

	public boolean supportsGenerators() {
		return true;
	}


	// ********** misc **********

	public boolean usesPrimaryKeyColumns() {
		return this.getJavaResourceType().getAnnotation(EclipseLink.PRIMARY_KEY) != null;
	}

	public boolean usesPrimaryKeyTenantDiscriminatorColumns() {
		return getMultitenancy().usesPrimaryKeyTenantDiscriminatorColumns();
	}

	public Cacheable2_0 getCacheable() {
		return ((JavaCacheableReference2_0) this.getCaching()).getCacheable();
	}

	public boolean calculateDefaultCacheable() {
		return ((CacheableReference2_0) this.getCaching()).calculateDefaultCacheable();
	}

	// ********** converter container parent adapter **********

	public JpaContextModel getConverterContainerParent() {
		return this;  // no adapter
	}

	public JavaResourceAnnotatedElement getJavaResourceAnnotatedElement() {
		return this.getJavaResourceType();
	}

	public boolean parentSupportsConverters() {
		return true;
	}

	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.multitenancy.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.generatorContainer.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		return null;
	}

	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.caching.validate(messages, reporter);
		this.readOnly.validate(messages, reporter);
		this.converterContainer.validate(messages, reporter);
		this.changeTracking.validate(messages, reporter);
		this.customizer.validate(messages, reporter);
		this.multitenancy.validate(messages, reporter);
		this.generatorContainer.validate(messages, reporter);
	}

	@Override
	protected JptValidator buildPrimaryKeyValidator() {
		return new EclipseLinkMappedSuperclassPrimaryKeyValidator(this);
	}

	@Override
	protected JptValidator buildTypeMappingValidator() {
		return new EclipseLinkMappedSuperclassValidator(this);
	}
}

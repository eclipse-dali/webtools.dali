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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaEntity;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCacheableReference2_0;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkReadOnly;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaMultitenancy2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory.EclipseLinkJpaPlatformVersion;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.EclipseLinkEntityPrimaryKeyValidator;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.EclipseLinkTypeMappingValidator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ClassExtractorAnnotation2_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * EclipseLink
 * Java entity
 */
public class EclipseLinkJavaEntityImpl
	extends AbstractJavaEntity
	implements EclipseLinkJavaEntity, EclipseLinkJavaConverterContainer.Parent
{
	protected final EclipseLinkJavaCaching caching;

	protected final EclipseLinkJavaReadOnly readOnly;

	protected final EclipseLinkJavaConverterContainer converterContainer;

	protected final EclipseLinkJavaChangeTracking changeTracking;

	protected final EclipseLinkJavaCustomizer customizer;

	protected final EclipseLinkJavaMultitenancy2_3 multitenancy;

	public EclipseLinkJavaEntityImpl(JavaPersistentType parent, EntityAnnotation mappingAnnotation) {
		super(parent, mappingAnnotation);
		this.caching = this.buildCaching();
		this.readOnly = this.buildReadOnly();
		this.converterContainer = this.buildConverterContainer();
		this.changeTracking = this.buildChangeTracking();
		this.customizer = this.buildCustomizer();
		this.multitenancy = this.buildMultitenancy();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.caching.synchronizeWithResourceModel(monitor);
		this.readOnly.synchronizeWithResourceModel(monitor);
		this.converterContainer.synchronizeWithResourceModel(monitor);
		this.changeTracking.synchronizeWithResourceModel(monitor);
		this.customizer.synchronizeWithResourceModel(monitor);
		this.multitenancy.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.caching.update(monitor);
		this.readOnly.update(monitor);
		this.converterContainer.update(monitor);
		this.changeTracking.update(monitor);
		this.customizer.update(monitor);
		this.multitenancy.update(monitor);
	}


	// ********** caching **********

	public EclipseLinkJavaCaching getCaching() {
		return this.caching;
	}

	protected EclipseLinkJavaCaching buildCaching() {
		return new EclipseLinkJavaCachingImpl(this);
	}


	// ********** read-only **********

	public EclipseLinkReadOnly getReadOnly() {
		return this.readOnly;
	}

	protected EclipseLinkJavaReadOnly buildReadOnly() {
		return new EclipseLinkJavaReadOnly(this);
	}


	// ********** converter container **********

	public EclipseLinkJavaConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected EclipseLinkJavaConverterContainer buildConverterContainer() {
		return new EclipseLinkJavaConverterContainerImpl(this);
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

	protected EclipseLinkJavaChangeTracking buildChangeTracking() {
		return new EclipseLinkJavaChangeTracking(this);
	}


	// ********** customizer **********

	public EclipseLinkCustomizer getCustomizer() {
		return this.customizer;
	}

	protected EclipseLinkJavaCustomizer buildCustomizer() {
		return new EclipseLinkJavaCustomizer(this);
	}


	// ********** multitenancy **********

	public EclipseLinkJavaMultitenancy2_3 getMultitenancy() {
		return this.multitenancy;
	}

	protected EclipseLinkJavaMultitenancy2_3 buildMultitenancy() {
		return this.isEclipseLink2_3Compatible() ?
			new EclipseLinkJavaMultitenancyImpl2_3(this) :
			new EclipseLinkNullJavaMultitenancy2_3(this);
	}

	protected boolean isEclipseLink2_3Compatible() {
		return this.getJpaPlatformVersion().isCompatibleWithEclipseLinkVersion(EclipseLinkJpaPlatformFactory2_3.VERSION);
	}

	@Override
	protected EclipseLinkJpaPlatformVersion getJpaPlatformVersion() {
		return (EclipseLinkJpaPlatformVersion) super.getJpaPlatformVersion();
	}

	public boolean isMultitenantMetadataAllowed() {
		return this.isRootEntity() || this.isInheritanceStrategyTablePerClass();
	}

	protected boolean isInheritanceStrategyTablePerClass() {
		return this.getInheritanceStrategy() == InheritanceType.TABLE_PER_CLASS;
	}


	// ********** discriminator column **********

	@Override
	protected boolean buildSpecifiedDiscriminatorColumnIsAllowed() {
		return super.buildSpecifiedDiscriminatorColumnIsAllowed() && ! this.classExtractorIsSpecified();
	}

	protected boolean classExtractorIsSpecified() {
		return this.getClassExtractorAnnotation() != null;
	}

	protected ClassExtractorAnnotation2_1 getClassExtractorAnnotation() {
		return (ClassExtractorAnnotation2_1) this.getJavaResourceType().getAnnotation(ClassExtractorAnnotation2_1.ANNOTATION_NAME);
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
		return ((JavaCacheableReference2_0) this.getCaching()).calculateDefaultCacheable();
	}

	// ********** converter container parent **********

	public JavaResourceAnnotatedElement getJavaResourceAnnotatedElement() {
		return this.getJavaResourceType();
	}

	public boolean supportsConverters() {
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
	}

	@Override
	protected JpaValidator buildPrimaryKeyValidator() {
		return new EclipseLinkEntityPrimaryKeyValidator(this);
	}

	@Override
	protected JpaValidator buildTypeMappingValidator() {
		return new EclipseLinkTypeMappingValidator(this);
	}
}

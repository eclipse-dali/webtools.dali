/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaEntity;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCacheableHolder2_0;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkReadOnly;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkEntity;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v1_1.context.EclipseLinkEntityPrimaryKeyValidator;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v1_1.context.EclipseLinkTypeMappingValidator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.v2_0.resource.java.EclipseLinkClassExtractorAnnotation2_1;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * EclipseLink
 * Java entity
 */
public class JavaEclipseLinkEntityImpl
	extends AbstractJavaEntity
	implements JavaEclipseLinkEntity
{
	protected final JavaEclipseLinkCaching caching;

	protected final JavaEclipseLinkReadOnly readOnly;

	protected final JavaEclipseLinkConverterContainer converterContainer;

	protected final JavaEclipseLinkChangeTracking changeTracking;

	protected final JavaEclipseLinkCustomizer customizer;


	public JavaEclipseLinkEntityImpl(JavaPersistentType parent, EntityAnnotation mappingAnnotation) {
		super(parent, mappingAnnotation);
		this.caching = this.buildCaching();
		this.readOnly = this.buildReadOnly();
		this.converterContainer = this.buildConverterContainer();
		this.changeTracking = this.buildChangeTracking();
		this.customizer = this.buildCustomizer();
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
	}

	@Override
	public void update() {
		super.update();
		this.caching.update();
		this.readOnly.update();
		this.converterContainer.update();
		this.changeTracking.update();
		this.customizer.update();
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

	public JavaEclipseLinkConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected JavaEclipseLinkConverterContainer buildConverterContainer() {
		return new JavaEclipseLinkConverterContainerImpl(this);
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


	// ********** discriminator column **********

	@Override
	protected boolean buildSpecifiedDiscriminatorColumnIsAllowed() {
		return super.buildSpecifiedDiscriminatorColumnIsAllowed() && ! this.classExtractorIsSpecified();
	}

	protected boolean classExtractorIsSpecified() {
		return this.getClassExtractorAnnotation() != null;
	}

	protected EclipseLinkClassExtractorAnnotation2_1 getClassExtractorAnnotation() {
		return (EclipseLinkClassExtractorAnnotation2_1) this.getResourcePersistentType().getAnnotation(EclipseLinkClassExtractorAnnotation2_1.ANNOTATION_NAME);
	}


	// ********** misc **********

	public boolean usesPrimaryKeyColumns() {
		return this.getResourcePersistentType().getAnnotation(EclipseLink.PRIMARY_KEY) != null;
	}

	public JavaCacheable2_0 getCacheable() {
		return ((JavaCacheableHolder2_0) this.getCaching()).getCacheable();
	}

	public boolean calculateDefaultCacheable() {
		return ((JavaCacheableHolder2_0) this.getCaching()).calculateDefaultCacheable();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.caching.validate(messages, reporter, astRoot);
		this.readOnly.validate(messages, reporter, astRoot);
		this.converterContainer.validate(messages, reporter, astRoot);
		this.changeTracking.validate(messages, reporter, astRoot);
		this.customizer.validate(messages, reporter, astRoot);
	}

	@Override
	protected JptValidator buildPrimaryKeyValidator(CompilationUnit astRoot) {
		return new EclipseLinkEntityPrimaryKeyValidator(this, this.buildTextRangeResolver(astRoot));
	}

	@Override
	protected JptValidator buildTypeMappingValidator(CompilationUnit astRoot) {
		return new EclipseLinkTypeMappingValidator(this, this.getResourcePersistentType(), buildTextRangeResolver(astRoot));
	}
}

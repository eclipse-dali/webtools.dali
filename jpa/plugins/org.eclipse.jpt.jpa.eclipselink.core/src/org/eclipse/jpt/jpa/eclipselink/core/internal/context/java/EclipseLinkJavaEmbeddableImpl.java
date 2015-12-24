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
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaEmbeddable;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddableAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaEmbeddable;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.EclipseLinkTypeMappingValidator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * EclipseLink
 * Java embeddable type mapping
 */
public class EclipseLinkJavaEmbeddableImpl
	extends AbstractJavaEmbeddable
	implements EclipseLinkJavaEmbeddable, EclipseLinkJavaConverterContainer.Parent
{
	protected final EclipseLinkJavaConverterContainer converterContainer;

	protected final EclipseLinkJavaChangeTracking changeTracking;

	protected final EclipseLinkJavaCustomizer customizer;


	public EclipseLinkJavaEmbeddableImpl(JavaPersistentType parent, EmbeddableAnnotation mappingAnnotation) {
		super(parent, mappingAnnotation);
		this.converterContainer = this.buildConverterContainer();
		this.changeTracking = this.buildChangeTracking();
		this.customizer = this.buildCustomizer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.converterContainer.synchronizeWithResourceModel(monitor);
		this.changeTracking.synchronizeWithResourceModel(monitor);
		this.customizer.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.converterContainer.update(monitor);
		this.changeTracking.update(monitor);
		this.customizer.update(monitor);
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


	// ********** misc **********

	public boolean usesPrimaryKeyColumns() {
		return false;
	}

	public boolean usesPrimaryKeyTenantDiscriminatorColumns() {
		return false;
	}

	// ********** converter container parent **********

	public JavaResourceAnnotatedElement getJavaResourceAnnotatedElement() {
		return this.getJavaResourceType();
	}

	public boolean supportsConverters() {
		return true;
	}

	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.converterContainer.validate(messages, reporter);
		this.changeTracking.validate(messages, reporter);
		this.customizer.validate(messages, reporter);
	}

	@Override
	protected JpaValidator buildTypeMappingValidator() {
		return new EclipseLinkTypeMappingValidator(this);
	}
	
	// ********** attribute mappings ******
	
	@Override
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return ArrayTools.contains(ALLOWED_ATTRIBUTE_MAPPING_KEYS, attributeMappingKey);
	}

	public static final String[] ALLOWED_ATTRIBUTE_MAPPING_KEYS =
		new String[] {
			MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY,
			MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY,
			MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY,
			MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY,
			MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY,
			MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
			MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
			MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY
		};
}

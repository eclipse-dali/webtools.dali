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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedPersistentAttribute2_0;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.BasicCollectionAnnotation;

public class EclipseLinkJavaBasicCollectionMapping
	extends AbstractJavaAttributeMapping<BasicCollectionAnnotation>
	implements EclipseLinkBasicCollectionMapping, EclipseLinkJavaConvertibleMapping
{

	protected final EclipseLinkJavaConverterContainer converterContainer;

	public EclipseLinkJavaBasicCollectionMapping(JavaSpecifiedPersistentAttribute parent) {
		super(parent);
		this.converterContainer = this.buildConverterContainer();
	}

	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.converterContainer.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.converterContainer.update(monitor);
	}


	// ********** converters **********

	public EclipseLinkJavaConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected EclipseLinkJavaConverterContainer buildConverterContainer() {
		return new EclipseLinkJavaConverterContainerImpl(this);
	}

	// ********** converter container parent **********

	public JavaResourceAnnotatedElement getJavaResourceAnnotatedElement() {
		return this.getResourceAttribute();
	}

	public boolean supportsConverters() {
		return ! this.getPersistentAttribute().isVirtual();
	}

	// ********** metamodel **********  
	@Override
	protected String getMetamodelFieldTypeName() {
		return ((SpecifiedPersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldTypeName();
	}

	@Override
	public String getMetamodelTypeName() {
		String targetTypeName = this.getPersistentAttribute().getMultiReferenceTargetTypeName();
		return (targetTypeName != null) ? targetTypeName : MetamodelField2_0.DEFAULT_TYPE_NAME;
	}


	// ********** misc **********
	
	public String getKey() {
		return EclipseLinkMappingKeys.BASIC_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected String getAnnotationName() {
		return BasicCollectionAnnotation.ANNOTATION_NAME;
	}
}

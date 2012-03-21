/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkBasicCollectionAnnotation;

public class JavaEclipseLinkBasicCollectionMapping
	extends AbstractJavaAttributeMapping<EclipseLinkBasicCollectionAnnotation>
	implements EclipseLinkBasicCollectionMapping, EclipseLinkJavaConvertibleMapping
{

	protected final JavaEclipseLinkConverterContainer converterContainer;

	public JavaEclipseLinkBasicCollectionMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.converterContainer = this.buildConverterContainer();
	}

	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.converterContainer.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.converterContainer.update();
	}


	// ********** converters **********

	public JavaEclipseLinkConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected JavaEclipseLinkConverterContainer buildConverterContainer() {
		return new JavaEclipseLinkConverterContainerImpl(this);
	}

	// ********** converter container parent adapter **********

	public JavaJpaContextNode getConverterContainerParent() {
		return this;  // no adapter
	}

	public JavaResourceAnnotatedElement getJavaResourceAnnotatedElement() {
		return this.getResourceAttribute();
	}

	public boolean parentSupportsConverters() {
		return ! this.getPersistentAttribute().isVirtual();
	}

	// ********** metamodel **********  
	@Override
	protected String getMetamodelFieldTypeName() {
		return ((JavaPersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldTypeName();
	}

	@Override
	public String getMetamodelTypeName() {
		String targetTypeName = this.getPersistentAttribute().getMultiReferenceTargetTypeName();
		return (targetTypeName != null) ? targetTypeName : MetamodelField.DEFAULT_TYPE_NAME;
	}


	// ********** misc **********
	
	public String getKey() {
		return EclipseLinkMappingKeys.BASIC_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected String getAnnotationName() {
		return EclipseLinkBasicCollectionAnnotation.ANNOTATION_NAME;
	}
}

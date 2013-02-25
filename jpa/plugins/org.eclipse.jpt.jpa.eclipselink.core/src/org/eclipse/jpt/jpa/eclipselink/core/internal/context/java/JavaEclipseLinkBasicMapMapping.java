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

import java.util.ArrayList;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.jpa.core.jpa2.context.PersistentAttribute2_0;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkBasicMapMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkBasicMapAnnotation;

public class JavaEclipseLinkBasicMapMapping
	extends AbstractJavaAttributeMapping<EclipseLinkBasicMapAnnotation>
	implements EclipseLinkBasicMapMapping, EclipseLinkJavaConvertibleMapping
{

	protected final JavaEclipseLinkConverterContainer converterContainer;
	
	public JavaEclipseLinkBasicMapMapping(JavaPersistentAttribute parent) {
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

	public JpaContextModel getConverterContainerParent() {
		return this;  // no adapter
	}

	public JavaResourceAnnotatedElement getJavaResourceAnnotatedElement() {
		return this.getResourceAttribute();
	}

	public boolean parentSupportsConverters() {
		return ! this.getPersistentAttribute().isVirtual();
	}

	// ********** misc **********
	
	public String getKey() {
		return EclipseLinkMappingKeys.BASIC_MAP_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected String getAnnotationName() {
		return EclipseLinkBasicMapAnnotation.ANNOTATION_NAME;
	}


	// ********** metamodel **********  
	@Override
	protected String getMetamodelFieldTypeName() {
		return ((PersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldTypeName();
	}

	@Override
	public String getMetamodelTypeName() {
		String targetTypeName = this.getPersistentAttribute().getMultiReferenceTargetTypeName();
		return (targetTypeName != null) ? targetTypeName : MetamodelField.DEFAULT_TYPE_NAME;
	}

	@Override
	protected void addMetamodelFieldTypeArgumentNamesTo(ArrayList<String> typeArgumentNames) {
		this.addMetamodelFieldMapKeyTypeArgumentNameTo(typeArgumentNames);
		super.addMetamodelFieldTypeArgumentNamesTo(typeArgumentNames);
	}

	protected void addMetamodelFieldMapKeyTypeArgumentNameTo(ArrayList<String> typeArgumentNames) {
		String mapKeyTypeName = this.getPersistentAttribute().getMultiReferenceMapKeyTypeName();
		mapKeyTypeName = mapKeyTypeName != null ? mapKeyTypeName : MetamodelField.DEFAULT_TYPE_NAME;
		typeArgumentNames.add(mapKeyTypeName);
	}
}

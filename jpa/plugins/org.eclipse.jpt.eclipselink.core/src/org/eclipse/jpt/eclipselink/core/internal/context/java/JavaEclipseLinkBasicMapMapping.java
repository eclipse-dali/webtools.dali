/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import java.util.ArrayList;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkBasicMapMapping;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkBasicMapAnnotation;

public class JavaEclipseLinkBasicMapMapping
	extends AbstractJavaAttributeMapping<EclipseLinkBasicMapAnnotation>
	implements EclipseLinkBasicMapMapping
{
	
	public JavaEclipseLinkBasicMapMapping(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	public String getKey() {
		return EclipseLinkMappingKeys.BASIC_MAP_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return EclipseLinkBasicMapAnnotation.ANNOTATION_NAME;
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

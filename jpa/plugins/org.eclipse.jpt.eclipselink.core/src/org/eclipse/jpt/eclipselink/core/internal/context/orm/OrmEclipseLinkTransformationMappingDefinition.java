/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.xml.EmfTools;
import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkTransformationMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformation;

public class OrmEclipseLinkTransformationMappingDefinition
	implements OrmAttributeMappingDefinition
{
	// singleton
	private static final OrmEclipseLinkTransformationMappingDefinition INSTANCE = 
			new OrmEclipseLinkTransformationMappingDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static OrmAttributeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private OrmEclipseLinkTransformationMappingDefinition() {
		super();
	}
	
	
	public String getKey() {
		return EclipseLinkMappingKeys.TRANSFORMATION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public XmlAttributeMapping buildResourceMapping(EFactory factory) {
		return EmfTools.create(
				factory, 
				EclipseLinkOrmPackage.eINSTANCE.getXmlTransformation(), 
				XmlTransformation.class);
	}
	
	public OrmAttributeMapping buildContextMapping(
			OrmPersistentAttribute parent, 
			XmlAttributeMapping resourceMapping, 
			OrmXmlContextNodeFactory factory) {
		return ((EclipseLinkOrmXmlContextNodeFactory) factory).
				buildOrmEclipseLinkTransformationMapping(parent, (XmlTransformation) resourceMapping);
	}
	
	public XmlAttributeMapping buildVirtualResourceMapping(
			OrmTypeMapping ormTypeMapping, 
			JavaAttributeMapping javaAttributeMapping, 
			OrmXmlContextNodeFactory factory) {
		return ((EclipseLinkOrmXmlContextNodeFactory) factory).
				buildVirtualEclipseLinkXmlTransformation(ormTypeMapping, (JavaEclipseLinkTransformationMapping) javaAttributeMapping);
	}
}

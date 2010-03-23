/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.orm;

import java.util.List;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkBasicCollectionMappingUiDefinition;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkBasicMapMappingUiDefinition;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkTransformationMappingUiDefinition;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkVariableOneToOneMappingUiDefinition;
import org.eclipse.jpt.eclipselink.ui.internal.structure.EclipseLinkOrmResourceModelStructureProvider;
import org.eclipse.jpt.ui.ResourceUiDefinition;
import org.eclipse.jpt.ui.details.orm.OrmAttributeMappingUiDefinition;
import org.eclipse.jpt.ui.details.orm.OrmTypeMappingUiDefinition;
import org.eclipse.jpt.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.ui.internal.details.orm.AbstractOrmXmlResourceUiDefinition;
import org.eclipse.jpt.ui.internal.details.orm.OrmBasicMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.orm.OrmEmbeddableUiDefinition;
import org.eclipse.jpt.ui.internal.details.orm.OrmEmbeddedIdMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.orm.OrmEmbeddedMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.orm.OrmEntityUiDefinition;
import org.eclipse.jpt.ui.internal.details.orm.OrmIdMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.orm.OrmManyToManyMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.orm.OrmManyToOneMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.orm.OrmMappedSuperclassUiDefinition;
import org.eclipse.jpt.ui.internal.details.orm.OrmOneToManyMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.orm.OrmOneToOneMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.orm.OrmTransientMappingUiDefinition;
import org.eclipse.jpt.ui.internal.details.orm.OrmVersionMappingUiDefinition;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmElementCollectionMapping2_0UiDefinition;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;

public class EclipseLinkOrmXml2_0UiDefinition extends AbstractOrmXmlResourceUiDefinition
{
	// singleton
	private static final ResourceUiDefinition INSTANCE = new EclipseLinkOrmXml2_0UiDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static ResourceUiDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkOrmXml2_0UiDefinition() {
		super();
	}
	
	
	@Override
	protected OrmXmlUiFactory buildOrmXmlUiFactory() {
		return new EclipseLinkOrmXml2_0UiFactory();
	}
	
	public boolean providesUi(JpaResourceType resourceType) {
		return resourceType.equals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_2_0_RESOURCE_TYPE)
				|| resourceType.equals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_2_1_RESOURCE_TYPE);
	}
	
	public JpaStructureProvider getStructureProvider() {
		return EclipseLinkOrmResourceModelStructureProvider.instance();
	}
	
	@Override
	protected void addOrmAttributeMappingUiDefinitionsTo(List<OrmAttributeMappingUiDefinition<? extends AttributeMapping>> definitions) {
		definitions.add(OrmIdMappingUiDefinition.instance());
		definitions.add(OrmEmbeddedIdMappingUiDefinition.instance());
		definitions.add(OrmBasicMappingUiDefinition.instance());
		definitions.add(OrmVersionMappingUiDefinition.instance());
		definitions.add(OrmManyToOneMappingUiDefinition.instance());
		definitions.add(OrmOneToManyMappingUiDefinition.instance());
		definitions.add(OrmOneToOneMappingUiDefinition.instance());
		definitions.add(OrmManyToManyMappingUiDefinition.instance());
		definitions.add(OrmEmbeddedMappingUiDefinition.instance());
		definitions.add(OrmTransientMappingUiDefinition.instance());
		
		definitions.add(OrmEclipseLinkBasicCollectionMappingUiDefinition.instance());
		definitions.add(OrmEclipseLinkBasicMapMappingUiDefinition.instance());
		definitions.add(OrmEclipseLinkVariableOneToOneMappingUiDefinition.instance());
		definitions.add(OrmEclipseLinkTransformationMappingUiDefinition.instance());
		
		definitions.add(OrmElementCollectionMapping2_0UiDefinition.instance());
	}
	
	@Override
	protected void addOrmTypeMappingUiDefinitionsTo(List<OrmTypeMappingUiDefinition<? extends TypeMapping>> definitions) {
		definitions.add(OrmEntityUiDefinition.instance());
		definitions.add(OrmMappedSuperclassUiDefinition.instance());
		definitions.add(OrmEmbeddableUiDefinition.instance());
	}
}

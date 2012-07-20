/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import java.util.List;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXml2_4Definition;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.details.orm.OrmAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmBasicMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmEmbeddedIdMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmEmbeddedMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmIdMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmManyToManyMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmManyToOneMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmOneToManyMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmOneToOneMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmTransientMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmVersionMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.OrmElementCollectionMapping2_0UiDefinition;


public class EclipseLinkOrmXml2_4UiDefinition
	extends EclipseLinkOrmXml2_1UiDefinition
{
	// singleton
	private static final ResourceUiDefinition INSTANCE = new EclipseLinkOrmXml2_4UiDefinition();


	/**
	 * Return the singleton
	 */
	public static ResourceUiDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkOrmXml2_4UiDefinition() {
		super();
	}

	@Override
	protected OrmXmlUiFactory buildOrmXmlUiFactory() {
		return new EclipseLinkOrmXml2_3UiFactory();
	}

	@Override
	public boolean providesUi(JptResourceType resourceType) {
		return resourceType.equals(EclipseLinkOrmXml2_4Definition.instance().getResourceType());
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
		definitions.add(OrmEclipseLinkArrayMapping2_3UiDefinition.instance());
		definitions.add(OrmEclipseLinkStructureMapping2_3UiDefinition.instance());
	}
}
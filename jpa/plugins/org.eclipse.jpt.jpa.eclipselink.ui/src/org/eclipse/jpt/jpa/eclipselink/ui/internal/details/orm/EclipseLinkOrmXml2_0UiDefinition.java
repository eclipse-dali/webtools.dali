/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXml2_0Definition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkBasicCollectionMappingUiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkBasicMapMappingUiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkTransformationMappingUiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkVariableOneToOneMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.jpa.ui.details.JpaUiFactory;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.BasicMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.EmbeddedIdMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.EmbeddedMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.IdMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.ManyToManyMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.ManyToOneMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.OneToManyMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.OneToOneMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.TransientMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.VersionMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.orm.AbstractOrmXmlResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmPersistentAttributeDetailsProvider;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmPersistentTypeDetailsProvider;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmXmlUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.ElementCollectionMapping2_0UiDefinition;

public class EclipseLinkOrmXml2_0UiDefinition
	extends AbstractOrmXmlResourceUiDefinition
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
	protected JpaUiFactory buildUiFactory() {
		return new EclipseLinkOrmXml2_0UiFactory();
	}
	
	public boolean providesUi(JptResourceType resourceType) {
		return resourceType.equals(EclipseLinkOrmXml2_0Definition.instance().getResourceType());
	}


	// ********** details providers **********

	@Override
	protected void addDetailsProvidersTo(List<JpaDetailsProvider> providers) {
		providers.add(OrmPersistentTypeDetailsProvider.instance());
		providers.add(OrmPersistentAttributeDetailsProvider.instance());
		providers.add(EclipseLinkEntityMappings2_0DetailsProvider.instance());
	}


	// ********** structure view factory provider **********
	
	public ItemTreeStateProviderFactoryProvider getStructureViewFactoryProvider() {
		return OrmXmlUiDefinition.STRUCTURE_VIEW_FACTORY_PROVIDER;
	}
	
	@Override
	protected void addSpecifiedAttributeMappingUiDefinitionsTo(List<MappingUiDefinition> definitions) {
		definitions.add(IdMappingUiDefinition.instance());
		definitions.add(EmbeddedIdMappingUiDefinition.instance());
		definitions.add(BasicMappingUiDefinition.instance());
		definitions.add(VersionMappingUiDefinition.instance());
		definitions.add(ManyToOneMappingUiDefinition.instance());
		definitions.add(OneToManyMappingUiDefinition.instance());
		definitions.add(OneToOneMappingUiDefinition.instance());
		definitions.add(ManyToManyMappingUiDefinition.instance());
		definitions.add(EmbeddedMappingUiDefinition.instance());
		definitions.add(TransientMappingUiDefinition.instance());
		
		definitions.add(EclipseLinkBasicCollectionMappingUiDefinition.instance());
		definitions.add(EclipseLinkBasicMapMappingUiDefinition.instance());
		definitions.add(EclipseLinkVariableOneToOneMappingUiDefinition.instance());
		definitions.add(EclipseLinkTransformationMappingUiDefinition.instance());
		
		definitions.add(ElementCollectionMapping2_0UiDefinition.instance());
	}
}

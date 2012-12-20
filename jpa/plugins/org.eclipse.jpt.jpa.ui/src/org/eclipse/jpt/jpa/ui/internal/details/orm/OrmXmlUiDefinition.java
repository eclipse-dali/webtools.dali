/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import java.util.List;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.ui.internal.jface.SimpleItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmXmlDefinition;
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
import org.eclipse.jpt.jpa.ui.internal.structure.OrmStructureItemContentProviderFactory;
import org.eclipse.jpt.jpa.ui.internal.structure.OrmStructureItemLabelProviderFactory;

public class OrmXmlUiDefinition
	extends AbstractOrmXmlResourceUiDefinition
{
	// singleton
	private static final ResourceUiDefinition INSTANCE = new OrmXmlUiDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static ResourceUiDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private OrmXmlUiDefinition() {
		super();
	}
	
	
	@Override
	protected JpaUiFactory buildUiFactory() {
		return new GenericOrmXmlUiFactory();
	}
	
	public boolean providesUi(JptResourceType resourceType) {
		return resourceType.equals(GenericOrmXmlDefinition.instance().getResourceType());
	}


	// ********** details providers **********
	
	@Override
	protected void addDetailsProvidersTo(List<JpaDetailsProvider> providers) {
		providers.add(OrmPersistentTypeDetailsProvider.instance());
		providers.add(OrmPersistentAttributeDetailsProvider.instance());
		providers.add(EntityMappingsDetailsProvider.instance());
	}


	// ********** structure view factory provider **********

	public ItemTreeStateProviderFactoryProvider getStructureViewFactoryProvider() {
		return STRUCTURE_VIEW_FACTORY_PROVIDER;
	}
	
	public static final ItemTreeStateProviderFactoryProvider STRUCTURE_VIEW_FACTORY_PROVIDER =
			new SimpleItemTreeStateProviderFactoryProvider(
					OrmStructureItemContentProviderFactory.instance(),
					OrmStructureItemLabelProviderFactory.instance()
				);
	
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
	}
}

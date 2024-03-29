/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.structure;

import org.eclipse.jpt.common.ui.internal.jface.StaticItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;

/**
 * This factory builds item label providers for an <code>orm.xml</code> file
 * JPA Structure View.
 */
public class OrmStructureItemLabelProviderFactory
	extends MappingStructureItemLabelProviderFactory
{
	// singleton
	private static final ItemExtendedLabelProvider.Factory INSTANCE = new OrmStructureItemLabelProviderFactory();

	/**
	 * Return the singleton
	 */
	public static ItemExtendedLabelProvider.Factory instance() {
		return INSTANCE;
	}


	protected OrmStructureItemLabelProviderFactory() {
		super();
	}

	@Override
	public ItemExtendedLabelProvider buildProvider(Object item, ItemExtendedLabelProvider.Manager manager) {
		if (item instanceof EntityMappings) {
			return this.buildEntityMappingsProvider((EntityMappings) item, manager);
		}
		return super.buildProvider(item, manager);
	}

	protected ItemExtendedLabelProvider buildEntityMappingsProvider(EntityMappings entityMappings, ItemExtendedLabelProvider.Manager manager) {
		return new StaticItemExtendedLabelProvider(
					JptJpaUiImages.ENTITY_MAPPINGS,
					JptJpaUiMessages.ORM_ITEM_LABEL_PROVIDER_FACTORY_ENTITY_MAPPINGS_LABEL,
					this.buildEntityMappingsDescription(entityMappings),
					manager
				);
	}

	protected String buildEntityMappingsDescription(EntityMappings entityMappings) {
		StringBuilder sb = new StringBuilder();
		sb.append(JptJpaUiMessages.ORM_ITEM_LABEL_PROVIDER_FACTORY_ENTITY_MAPPINGS_LABEL);
		sb.append(" - ");  //$NON-NLS-1$
		sb.append(entityMappings.getResource().getFullPath().makeRelative());
		return sb.toString();
	}
}

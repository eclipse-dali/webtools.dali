/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.structure;

import org.eclipse.jpt.common.ui.internal.jface.StaticItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProviderFactory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.platform.generic.ClassRefItemLabelProvider;
import org.eclipse.jpt.jpa.ui.internal.platform.generic.JarFileRefItemLabelProvider;
import org.eclipse.jpt.jpa.ui.internal.platform.generic.MappingFileRefItemLabelProvider;
import org.eclipse.jpt.jpa.ui.internal.platform.generic.PersistenceUnitItemLabelProvider;

/**
 * This factory builds item label providers for a <code>persistence.xml</code> file
 * JPA Structure View.
 */
public class PersistenceStructureItemLabelProviderFactory
	implements ItemExtendedLabelProviderFactory
{
	// singleton
	private static final ItemExtendedLabelProviderFactory INSTANCE = new PersistenceStructureItemLabelProviderFactory();

	/**
	 * Return the singleton
	 */
	public static ItemExtendedLabelProviderFactory instance() {
		return INSTANCE;
	}


	protected PersistenceStructureItemLabelProviderFactory() {
		super();
	}

	public ItemExtendedLabelProvider buildProvider(Object item, ItemExtendedLabelProvider.Manager manager) {
		if (item instanceof Persistence) {
			return this.buildPersistenceProvider((Persistence) item, manager);
		}
		if (item instanceof PersistenceUnit) {
			return this.buildPersistenceUnitProvider((PersistenceUnit) item, manager);
		}
		if (item instanceof MappingFileRef) {
			return this.buildMappingFileRefProvider((MappingFileRef) item, manager);
		}
		if (item instanceof ClassRef) {
			return this.buildClassRefProvider((ClassRef) item, manager);
		}
		if (item instanceof JarFileRef) {
			return this.buildJarFileRefProvider((JarFileRef) item, manager);
		}
		return null;
	}

	protected ItemExtendedLabelProvider buildPersistenceProvider(Persistence item, ItemExtendedLabelProvider.Manager manager) {
		return new StaticItemExtendedLabelProvider(
					JptJpaUiImages.PERSISTENCE,
					JptJpaUiMessages.PersistenceItemLabelProviderFactory_persistenceLabel,
					this.buildPersistenceDescription(item),
					manager
				);
	}

	protected String buildPersistenceDescription(Persistence item) {
		StringBuilder sb = new StringBuilder();
		sb.append(JptJpaUiMessages.PersistenceItemLabelProviderFactory_persistenceLabel);
		sb.append(" - ");  //$NON-NLS-1$
		sb.append(item.getResource().getFullPath().makeRelative());
		return sb.toString();
	}

	protected ItemExtendedLabelProvider buildPersistenceUnitProvider(PersistenceUnit item, ItemExtendedLabelProvider.Manager manager) {
		return new PersistenceUnitItemLabelProvider(item, manager);
	}

	protected ItemExtendedLabelProvider buildMappingFileRefProvider(MappingFileRef item, ItemExtendedLabelProvider.Manager manager) {
		return new MappingFileRefItemLabelProvider(item, manager);
	}

	protected ItemExtendedLabelProvider buildClassRefProvider(ClassRef item, ItemExtendedLabelProvider.Manager manager) {
		return new ClassRefItemLabelProvider(item, manager);
	}

	protected ItemExtendedLabelProvider buildJarFileRefProvider(JarFileRef item, ItemExtendedLabelProvider.Manager manager) {
		return new JarFileRefItemLabelProvider(item, manager);
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}
}

/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.generic;

import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.common.ui.internal.jface.StaticItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProviderFactory;
import org.eclipse.jpt.jpa.core.JpaNode;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JarFile;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;

/**
 * This factory builds item label providers for the JPA content in the
 * Common Navigator.
 */
public class GenericNavigatorItemLabelProviderFactory
	implements ItemExtendedLabelProviderFactory
{
	// singleton
	private static final ItemExtendedLabelProviderFactory INSTANCE = new GenericNavigatorItemLabelProviderFactory();

	/**
	 * Return the singleton
	 */
	public static ItemExtendedLabelProviderFactory instance() {
		return INSTANCE;
	}


	protected GenericNavigatorItemLabelProviderFactory() {
		super();
	}

	public ItemExtendedLabelProvider buildProvider(Object item, ItemExtendedLabelProvider.Manager manager) {
		if (item instanceof PersistenceXml) {
			return this.buildPersistenceXmlProvider((PersistenceXml) item, manager);
		}
		if (item instanceof PersistenceUnit) {
			return this.buildPersistenceUnitProvider((PersistenceUnit) item, manager);
		}
		if (item instanceof OrmXml) {
			return this.buildOrmXmlProvider((OrmXml) item, manager);
		}
		if (item instanceof PersistentType) {
			return this.buildPersistentTypeProvider((PersistentType) item, manager);
		}
		if (item instanceof ReadOnlyPersistentAttribute) {
			return this.buildPersistentAttributeProvider((ReadOnlyPersistentAttribute) item, manager);
		}
		if (item instanceof JarFile) {
			return this.buildJarFileProvider((JarFile) item, manager);
		}
		return null;
	}

	protected ItemExtendedLabelProvider buildPersistenceXmlProvider(PersistenceXml item, @SuppressWarnings("unused") ItemExtendedLabelProvider.Manager manager) {
		return this.buildResourceItemLabelProvider(item);
	}

	protected ItemExtendedLabelProvider buildPersistenceUnitProvider(PersistenceUnit item, ItemExtendedLabelProvider.Manager manager) {
		return new PersistenceUnitItemLabelProvider(item, manager);
	}

	protected ItemExtendedLabelProvider buildOrmXmlProvider(OrmXml item, @SuppressWarnings("unused") ItemExtendedLabelProvider.Manager manager) {
		return this.buildResourceItemLabelProvider(item);
	}

	protected ItemExtendedLabelProvider buildPersistentTypeProvider(PersistentType item, ItemExtendedLabelProvider.Manager manager) {
		return new PersistentTypeItemLabelProvider(item, manager);
	}

	protected ItemExtendedLabelProvider buildPersistentAttributeProvider(ReadOnlyPersistentAttribute item, ItemExtendedLabelProvider.Manager manager) {
		return new PersistentAttributeItemLabelProvider(item, manager);
	}

	protected ItemExtendedLabelProvider buildJarFileProvider(JarFile item, @SuppressWarnings("unused") ItemExtendedLabelProvider.Manager manager) {
		return this.buildResourceItemLabelProvider(item, JptUiIcons.JAR_FILE);
	}

	protected ItemExtendedLabelProvider buildResourceItemLabelProvider(JpaNode node) {
		return this.buildResourceItemLabelProvider(node.getResource());
	}

	protected ItemExtendedLabelProvider buildResourceItemLabelProvider(JpaNode node, String imageKey) {
		return this.buildResourceItemLabelProvider(node.getResource(), imageKey);
	}

	protected ItemExtendedLabelProvider buildResourceItemLabelProvider(IResource resource) {
		return this.buildResourceItemLabelProvider(resource, JptUiIcons.JPA_FILE);
	}

	protected ItemExtendedLabelProvider buildResourceItemLabelProvider(IResource resource, String imageKey) {
		return new StaticItemExtendedLabelProvider(
					JptJpaUiPlugin.getImage(imageKey),
					resource.getName(),
					this.buildResourceDescription(resource)
				);
	}

	protected String buildResourceDescription(IResource resource) {
		StringBuilder sb = new StringBuilder();
		sb.append(resource.getName());
		sb.append(" - ");  //$NON-NLS-1$
		sb.append(resource.getParent().getFullPath().makeRelative());
		return sb.toString();
	}
}

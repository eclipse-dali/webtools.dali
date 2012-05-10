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

import org.eclipse.jpt.common.ui.internal.jface.StaticItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider.Manager;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProviderFactory;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;

/**
 * This factory builds item content providers for a <code>persistence.xml</code>
 * file JPA Structure View.
 */
public class PersistenceStructureItemContentProviderFactory
	implements ItemTreeContentProviderFactory
{
	// singleton
	private static final ItemTreeContentProviderFactory INSTANCE = new PersistenceStructureItemContentProviderFactory();

	/**
	 * Return the singleton
	 */
	public static ItemTreeContentProviderFactory instance() {
		return INSTANCE;
	}


	protected PersistenceStructureItemContentProviderFactory() {
		super();
	}

	public ItemTreeContentProvider buildProvider(Object item, ItemTreeContentProvider.Manager manager) {
		if (item instanceof JpaFile) {
			return this.buildJpaFileProvider((JpaFile) item, manager);
		}
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

	protected ItemTreeContentProvider buildJpaFileProvider(JpaFile item, Manager manager) {
		return new JpaFileItemContentProvider(item, manager);
	}

	protected ItemTreeContentProvider buildPersistenceProvider(Persistence item, Manager manager) {
		return new PersistenceItemContentProvider(item, manager);
	}

	protected ItemTreeContentProvider buildPersistenceUnitProvider(PersistenceUnit item, Manager manager) {
		return new PersistenceUnitItemContentProvider(item, manager);
	}

	protected ItemTreeContentProvider buildMappingFileRefProvider(MappingFileRef item, @SuppressWarnings("unused") Manager manager) {
		return new StaticItemTreeContentProvider(item.getPersistenceUnit());
	}

	protected ItemTreeContentProvider buildClassRefProvider(ClassRef item, @SuppressWarnings("unused") Manager manager) {
		return new StaticItemTreeContentProvider(item.getPersistenceUnit());
	}

	protected ItemTreeContentProvider buildJarFileRefProvider(JarFileRef item, @SuppressWarnings("unused") Manager manager) {
		return new StaticItemTreeContentProvider(item.getPersistenceUnit());
	}
}

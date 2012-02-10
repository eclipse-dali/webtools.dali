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
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProviderFactory;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider.Manager;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.ui.internal.platform.generic.OrmPersistentTypeItemContentProvider;

/**
 * This factory builds item content providers for an <code>orm.xml</code> file
 * JPA Structure View.
 */
public class OrmStructureItemContentProviderFactory
	implements ItemTreeContentProviderFactory
{
	// singleton
	private static final ItemTreeContentProviderFactory INSTANCE = new OrmStructureItemContentProviderFactory();

	/**
	 * Return the singleton
	 */
	public static ItemTreeContentProviderFactory instance() {
		return INSTANCE;
	}


	protected OrmStructureItemContentProviderFactory() {
		super();
	}

	public ItemTreeContentProvider buildProvider(Object item, ItemTreeContentProvider.Manager manager) {
		if (item instanceof JpaFile) {
			return this.buildJpaFileProvider((JpaFile) item, manager);
		}
		if (item instanceof EntityMappings) {
			return this.buildEntityMappingsProvider((EntityMappings) item, manager);
		}
		if (item instanceof OrmPersistentType) {
			return this.buildOrmPersistentTypeProvider((OrmPersistentType) item, manager);
		}
		if (item instanceof OrmPersistentAttribute) {
			return this.buildOrmPersistentAttributeProvider((OrmPersistentAttribute) item, manager);
		}
		return null;
	}

	protected ItemTreeContentProvider buildJpaFileProvider(JpaFile item, Manager manager) {
		return new JpaFileItemContentProvider(item, manager);
	}

	protected ItemTreeContentProvider buildEntityMappingsProvider(EntityMappings item, Manager manager) {
		return new EntityMappingsItemContentProvider(item, manager);
	}

	protected ItemTreeContentProvider buildOrmPersistentTypeProvider(OrmPersistentType item, Manager manager) {
		return new OrmPersistentTypeItemContentProvider(item, manager);
	}

	protected ItemTreeContentProvider buildOrmPersistentAttributeProvider(OrmPersistentAttribute item, @SuppressWarnings("unused") Manager manager) {
		return new StaticItemTreeContentProvider(item.getParent());
	}
}

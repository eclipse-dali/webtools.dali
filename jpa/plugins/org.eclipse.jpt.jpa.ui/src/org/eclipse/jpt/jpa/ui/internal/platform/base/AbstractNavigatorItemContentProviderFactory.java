/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.base;

import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider.Manager;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProviderFactory;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.ui.internal.platform.generic.OrmXmlItemContentProvider;
import org.eclipse.jpt.jpa.ui.internal.platform.generic.PersistenceUnitItemContentProvider;
import org.eclipse.jpt.jpa.ui.internal.platform.generic.PersistenceXmlItemContentProvider;
import org.eclipse.jpt.jpa.ui.internal.structure.JpaStructureNodeItemContentProvider;

/**
 * This factory builds item content providers for the JPA content in the
 * Project Explorer.
 */
public abstract class AbstractNavigatorItemContentProviderFactory
	implements ItemTreeContentProviderFactory
{
	protected AbstractNavigatorItemContentProviderFactory() {
		super();
	}

	public ItemTreeContentProvider buildProvider(Object item, ItemTreeContentProvider.Manager manager) {
		if (item instanceof PersistenceXml) {
			//this skips the PersistenceXml.Persistence child and uses PersistenceUnits as children
			return this.buildPersistenceXmlProvider((PersistenceXml) item, manager);
		}
		if (item instanceof PersistenceUnit) {
			return this.buildPersistenceUnitProvider((PersistenceUnit) item, manager);
		}
		if (item instanceof OrmXml) {
			//this skips the OrmXml.EntityMappings child and uses OrmPersistentTypes as children
			return this.buildOrmXmlProvider((OrmXml) item, manager);
		}
		if (item instanceof JpaStructureNode) {
			return this.buildJpaStructureNodeProvider((JpaStructureNode) item, manager);
		}
		return null;
	}

	protected ItemTreeContentProvider buildPersistenceXmlProvider(PersistenceXml item, Manager manager) {
		return new PersistenceXmlItemContentProvider(item, manager);
	}

	protected ItemTreeContentProvider buildPersistenceUnitProvider(PersistenceUnit item, Manager manager) {
		return new PersistenceUnitItemContentProvider(item, manager);
	}

	protected ItemTreeContentProvider buildOrmXmlProvider(OrmXml item, Manager manager) {
		return new OrmXmlItemContentProvider(item, manager);
	}

	protected ItemTreeContentProvider buildJpaStructureNodeProvider(JpaStructureNode item, Manager manager) {
		return new JpaStructureNodeItemContentProvider(item, manager);
	}
}

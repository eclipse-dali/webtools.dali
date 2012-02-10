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

import org.eclipse.jpt.common.ui.internal.jface.StaticItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider.Manager;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProviderFactory;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;

/**
 * This factory builds item content providers for the JPA content in the
 * Common Navigator.
 */
public class GenericNavigatorItemContentProviderFactory
	implements ItemTreeContentProviderFactory
{
	// singleton
	private static final ItemTreeContentProviderFactory INSTANCE = new GenericNavigatorItemContentProviderFactory();

	/**
	 * Return the singleton
	 */
	public static ItemTreeContentProviderFactory instance() {
		return INSTANCE;
	}


	protected GenericNavigatorItemContentProviderFactory() {
		super();
	}

	public ItemTreeContentProvider buildProvider(Object item, ItemTreeContentProvider.Manager manager) {
		if (item instanceof PersistenceXml) {
			return this.buildPersistenceXmlProvider((PersistenceXml) item, manager);
		}
		if (item instanceof PersistenceUnit) {
			return this.buildPersistenceUnitProvider((PersistenceUnit) item, manager);
		}
		if (item instanceof OrmXml) {
			return this.buildOrmXmlProvider((OrmXml) item, manager);
		}
		if (item instanceof OrmPersistentType) {
			return this.buildOrmPersistentTypeProvider((OrmPersistentType) item, manager);
		}
		if (item instanceof JavaPersistentType) {
			return this.buildJavaPersistentTypeProvider((JavaPersistentType) item, manager);
		}
		if (item instanceof PersistentAttribute) {
			return this.buildPersistentAttributeProvider((PersistentAttribute) item, manager);
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

	protected ItemTreeContentProvider buildOrmPersistentTypeProvider(OrmPersistentType item, Manager manager) {
		return new OrmPersistentTypeItemContentProvider(item, manager);
	}

	protected ItemTreeContentProvider buildJavaPersistentTypeProvider(JavaPersistentType item, Manager manager) {
		return new JavaPersistentTypeItemContentProvider(item, manager);
	}

	protected ItemTreeContentProvider buildPersistentAttributeProvider(PersistentAttribute item, @SuppressWarnings("unused") Manager manager) {
		return new StaticItemTreeContentProvider(item.getParent());
	}
}

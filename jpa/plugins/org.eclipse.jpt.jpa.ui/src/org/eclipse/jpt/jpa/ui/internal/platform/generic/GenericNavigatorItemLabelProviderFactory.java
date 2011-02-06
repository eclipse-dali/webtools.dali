/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.generic;

import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.jpa.core.context.JpaRootContextNode;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JarFile;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.ui.internal.jface.JarFileItemLabelProvider;

public class GenericNavigatorItemLabelProviderFactory
	implements ItemLabelProviderFactory
{
	public ItemLabelProvider buildItemLabelProvider(Object item, DelegatingContentAndLabelProvider contentAndLabelProvider) {
		if (item instanceof JpaRootContextNode) {
			return new RootContextItemLabelProvider((JpaRootContextNode) item, contentAndLabelProvider);
		}
		else if (item instanceof PersistenceXml) {
			return new PersistenceXmlItemLabelProvider((PersistenceXml) item, contentAndLabelProvider);	
		}
		else if (item instanceof PersistenceUnit) {
			return new PersistenceUnitItemLabelProvider((PersistenceUnit) item, contentAndLabelProvider);	
		}
		else if (item instanceof OrmXml) {
			return new OrmXmlItemLabelProvider((OrmXml) item, contentAndLabelProvider);	
		}
		else if (item instanceof PersistentType) {
			return new PersistentTypeItemLabelProvider((PersistentType) item, contentAndLabelProvider);	
		}
		else if (item instanceof PersistentAttribute) {
			return new PersistentAttributeItemLabelProvider((PersistentAttribute) item, contentAndLabelProvider);	
		}
		else if (item instanceof JarFile) {
			return new JarFileItemLabelProvider((JarFile) item, contentAndLabelProvider);
		}
		return null;
	}
}

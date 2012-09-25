/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.platform.generic;

import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JarFile;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.ui.internal.jface.JarFileItemLabelProvider;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.ui.jface.ItemLabelProviderFactory;

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

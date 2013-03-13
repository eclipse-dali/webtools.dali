/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.structure;

import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider.Factory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.ui.internal.platform.generic.PersistentAttributeItemLabelProvider;
import org.eclipse.jpt.jpa.ui.internal.platform.generic.PersistentTypeItemLabelProvider;

/**
 * This factory builds item label providers for the persistent types and
 * attributes in a JPA Structure View.
 */
public abstract class MappingStructureItemLabelProviderFactory
	implements ItemExtendedLabelProvider.Factory
{
	protected MappingStructureItemLabelProviderFactory() {
		super();
	}

	public ItemExtendedLabelProvider buildProvider(Object item, ItemExtendedLabelProvider.Manager manager) {
		if (item instanceof PersistentType) {
			return this.buildPersistentTypeProvider((PersistentType) item, manager);
		}
		if (item instanceof PersistentAttribute) {
			return this.buildPersistentAttributeProvider((PersistentAttribute) item, manager);
		}
		return null;
	}

	protected ItemExtendedLabelProvider buildPersistentTypeProvider(PersistentType item, ItemExtendedLabelProvider.Manager manager) {
		return new PersistentTypeItemLabelProvider(item, manager);
	}

	protected ItemExtendedLabelProvider buildPersistentAttributeProvider(PersistentAttribute item, ItemExtendedLabelProvider.Manager manager) {
		return new PersistentAttributeItemLabelProvider(item, manager);
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}
}

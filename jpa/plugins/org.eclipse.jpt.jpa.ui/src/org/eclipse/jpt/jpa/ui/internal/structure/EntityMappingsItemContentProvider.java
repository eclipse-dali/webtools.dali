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

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;

public class EntityMappingsItemContentProvider
	extends AbstractItemTreeContentProvider<EntityMappings, OrmPersistentType>
{
	public EntityMappingsItemContentProvider(EntityMappings entityMappings, Manager manager) {
		super(entityMappings, manager);
	}
	
	public Object getParent() {
		// I'd like to return the resource model here, but that involves a hefty 
		// API change - we'll see what happens with this code first
		return null;
	}

	@Override
	protected CollectionValueModel<OrmPersistentType> buildChildrenModel() {
		return new ListCollectionValueModelAdapter<OrmPersistentType>(new ChildrenModel(this.item));
	}

	protected static class ChildrenModel
		extends ListAspectAdapter<EntityMappings, OrmPersistentType>
	{
		ChildrenModel(EntityMappings entityMappings) {
			super(EntityMappings.PERSISTENT_TYPES_LIST, entityMappings);
		}

		@Override
		protected ListIterable<OrmPersistentType> getListIterable() {
			return this.subject.getPersistentTypes();
		}

		@Override
		public int size_() {
			return this.subject.getPersistentTypesSize();
		}
	}
}
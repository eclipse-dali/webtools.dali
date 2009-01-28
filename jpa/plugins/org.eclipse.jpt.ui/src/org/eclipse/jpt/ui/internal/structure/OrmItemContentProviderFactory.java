/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.structure;

import java.util.ListIterator;

import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.platform.generic.OrmPersistentTypeItemContentProvider;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.TreeItemContentProvider;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;

public class OrmItemContentProviderFactory extends GeneralJpaMappingItemContentProviderFactory
{
	@Override
	public TreeItemContentProvider buildItemContentProvider(
			Object item, DelegatingContentAndLabelProvider contentProvider) {
		DelegatingTreeContentAndLabelProvider treeContentProvider = (DelegatingTreeContentAndLabelProvider) contentProvider;
		if (item instanceof JpaFile) {
			return new ResourceModelItemContentProvider((JpaFile) item, treeContentProvider);
		}
		if (item instanceof EntityMappings) {
			return new EntityMappingsItemContentProvider((EntityMappings) item, treeContentProvider);
		}
		return super.buildItemContentProvider(item, treeContentProvider);
	}
	
	@Override
	protected TreeItemContentProvider buildPersistentTypeItemContentProvider(PersistentType persistentType, DelegatingTreeContentAndLabelProvider treeContentProvider) {
		return new OrmPersistentTypeItemContentProvider((OrmPersistentType) persistentType, treeContentProvider);
	}
	
	public static class EntityMappingsItemContentProvider extends AbstractTreeItemContentProvider<OrmPersistentType>
	{
		public EntityMappingsItemContentProvider(
				EntityMappings entityMappings, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(entityMappings, contentProvider);
		}
		
		@Override
		public EntityMappings getModel() {
			return (EntityMappings) super.getModel();
		}
		
		@Override
		public Object getParent() {
			// I'd like to return the resource model here, but that involves a hefty 
			// API change - we'll see what happens with this code first
			return null;
		}
		
		@Override
		protected CollectionValueModel<OrmPersistentType> buildChildrenModel() {
			return new ListCollectionValueModelAdapter<OrmPersistentType>(
			new ListAspectAdapter<EntityMappings, OrmPersistentType>(
					EntityMappings.PERSISTENT_TYPES_LIST, getModel()) {
				@Override
				protected ListIterator<OrmPersistentType> listIterator_() {
					return subject.persistentTypes();
				}
			});
		}
	}
}

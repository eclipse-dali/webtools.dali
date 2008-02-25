/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.structure;

import java.util.ArrayList;
import java.util.ListIterator;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.ResourceModel;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.orm.OrmResourceModel;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.TreeItemContentProvider;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;

public class OrmItemContentProviderFactory extends GeneralJpaMappingItemContentProviderFactory
{
	@Override
	public TreeItemContentProvider buildItemContentProvider(
			Object item, DelegatingContentAndLabelProvider contentProvider) {
		DelegatingTreeContentAndLabelProvider treeContentProvider = (DelegatingTreeContentAndLabelProvider) contentProvider;
		if (item instanceof OrmResourceModel) {
			return new OrmResourceModelItemContentProvider((OrmResourceModel) item, treeContentProvider);
		}
		if (item instanceof EntityMappings) {
			return new EntityMappingsItemContentProvider((EntityMappings) item, treeContentProvider);
		}
		return super.buildItemContentProvider(item, treeContentProvider);
	}
	
	
	@Override
	protected TreeItemContentProvider buildPersistentTypeItemContentProvider(PersistentType persistentType, DelegatingTreeContentAndLabelProvider treeContentProvider) {
		return new PersistentTypeItemContentProvider((OrmPersistentType) persistentType, treeContentProvider);
	}
	
	public static class PersistentTypeItemContentProvider extends AbstractTreeItemContentProvider<OrmPersistentAttribute>
	{
		public PersistentTypeItemContentProvider(
			OrmPersistentType persistentType, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(persistentType, contentProvider);
		}
		
		@Override
		public Object getParent() {
			return ((OrmPersistentType) model()).parent();
		}
		
		@Override
		protected ListValueModel<OrmPersistentAttribute> buildChildrenModel() {
			java.util.List<ListValueModel<OrmPersistentAttribute>> list = new ArrayList<ListValueModel<OrmPersistentAttribute>>();
			list.add(buildSpecifiedPersistentAttributesListHolder());
			list.add(buildVirtualPersistentAttributesListHolder());
			return new CompositeListValueModel<ListValueModel<OrmPersistentAttribute>, OrmPersistentAttribute>(list);
		}
		

		protected ListValueModel<OrmPersistentAttribute> buildSpecifiedPersistentAttributesListHolder() {
			return new ListAspectAdapter<OrmPersistentType, OrmPersistentAttribute>(new String[]{PersistentType.SPECIFIED_ATTRIBUTES_LIST}, (OrmPersistentType) model()) {
				@Override
				protected ListIterator<OrmPersistentAttribute> listIterator_() {
					return subject.specifiedAttributes();
				}
				@Override
				protected int size_() {
					return subject.specifiedAttributesSize();
				}
			};
		}
		
		protected ListValueModel<OrmPersistentAttribute> buildVirtualPersistentAttributesListHolder() {
			return new ListAspectAdapter<OrmPersistentType, OrmPersistentAttribute>(new String[]{OrmPersistentType.VIRTUAL_ATTRIBUTES_LIST}, (OrmPersistentType) model()) {
				@Override
				protected ListIterator<OrmPersistentAttribute> listIterator_() {
					return subject.virtualAttributes();
				}
				@Override
				protected int size_() {
					return subject.virtualAttributesSize();
				}
			};
		}
}
	
	
	public static class OrmResourceModelItemContentProvider extends AbstractTreeItemContentProvider<JpaStructureNode>
	{
		public OrmResourceModelItemContentProvider(
				OrmResourceModel ormResourceModel, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(ormResourceModel, contentProvider);
		}
		
		@Override
		public Object getParent() {
			return null;
		}
		
		@Override
		protected ListValueModel<JpaStructureNode> buildChildrenModel() {
			return new ListAspectAdapter<OrmResourceModel, JpaStructureNode>(
					ResourceModel.ROOT_STRUCTURE_NODES_LIST, (OrmResourceModel) model()) {
				@Override
				protected ListIterator<JpaStructureNode> listIterator_() {
					return subject.rootStructureNodes();
				}
			};
		}	
	}
	
	
	public static class EntityMappingsItemContentProvider extends AbstractTreeItemContentProvider<OrmPersistentType>
	{
		public EntityMappingsItemContentProvider(
				EntityMappings entityMappings, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(entityMappings, contentProvider);
		}
		
		
		@Override
		public Object getParent() {
			// I'd like to return the resource model here, but that involves a hefty 
			// API change - we'll see what happens with this code first
			return null;
		}
		
		@Override
		protected ListValueModel<OrmPersistentType> buildChildrenModel() {
			return new ListAspectAdapter<EntityMappings, OrmPersistentType>(
					EntityMappings.PERSISTENT_TYPES_LIST, (EntityMappings) model()) {
				@Override
				protected ListIterator<OrmPersistentType> listIterator_() {
					return subject.ormPersistentTypes();
				}
			};
		}
	}
}

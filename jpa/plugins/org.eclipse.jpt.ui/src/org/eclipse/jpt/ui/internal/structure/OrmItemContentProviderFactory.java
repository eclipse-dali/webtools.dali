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
import org.eclipse.jpt.core.internal.IJpaStructureNode;
import org.eclipse.jpt.core.internal.IResourceModel;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.OrmResourceModel;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.ITreeItemContentProvider;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;

public class OrmItemContentProviderFactory extends GeneralJpaMappingItemContentProviderFactory
{
	@Override
	public ITreeItemContentProvider buildItemContentProvider(
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
	protected ITreeItemContentProvider buildPersistentTypeItemContentProvider(IPersistentType persistentType, DelegatingTreeContentAndLabelProvider treeContentProvider) {
		return new PersistentTypeItemContentProvider((XmlPersistentType) persistentType, treeContentProvider);
	}
	
	public static class PersistentTypeItemContentProvider extends AbstractTreeItemContentProvider<XmlPersistentAttribute>
	{
		public PersistentTypeItemContentProvider(
			XmlPersistentType persistentType, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(persistentType, contentProvider);
		}
		
		@Override
		public Object getParent() {
			return ((XmlPersistentType) model()).parent();
		}
		
		@Override
		protected ListValueModel<XmlPersistentAttribute> buildChildrenModel() {
			java.util.List<ListValueModel<XmlPersistentAttribute>> list = new ArrayList<ListValueModel<XmlPersistentAttribute>>();
			list.add(buildSpecifiedPersistentAttributesListHolder());
			list.add(buildVirtualPersistentAttributesListHolder());
			return new CompositeListValueModel<ListValueModel<XmlPersistentAttribute>, XmlPersistentAttribute>(list);
		}
		

		protected ListValueModel<XmlPersistentAttribute> buildSpecifiedPersistentAttributesListHolder() {
			return new ListAspectAdapter<XmlPersistentType, XmlPersistentAttribute>(new String[]{IPersistentType.SPECIFIED_ATTRIBUTES_LIST}, (XmlPersistentType) model()) {
				@Override
				protected ListIterator<XmlPersistentAttribute> listIterator_() {
					return subject.specifiedAttributes();
				}
				@Override
				protected int size_() {
					return subject.specifiedAttributesSize();
				}
			};
		}
		
		protected ListValueModel<XmlPersistentAttribute> buildVirtualPersistentAttributesListHolder() {
			return new ListAspectAdapter<XmlPersistentType, XmlPersistentAttribute>(new String[]{XmlPersistentType.VIRTUAL_ATTRIBUTES_LIST}, (XmlPersistentType) model()) {
				@Override
				protected ListIterator<XmlPersistentAttribute> listIterator_() {
					return subject.virtualAttributes();
				}
				@Override
				protected int size_() {
					return subject.virtualAttributesSize();
				}
			};
		}
}
	
	
	public static class OrmResourceModelItemContentProvider extends AbstractTreeItemContentProvider<IJpaStructureNode>
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
		protected ListValueModel<IJpaStructureNode> buildChildrenModel() {
			return new ListAspectAdapter<OrmResourceModel, IJpaStructureNode>(
					IResourceModel.ROOT_STRUCTURE_NODES_LIST, (OrmResourceModel) model()) {
				@Override
				protected ListIterator<IJpaStructureNode> listIterator_() {
					return subject.rootStructureNodes();
				}
			};
		}	
	}
	
	
	public static class EntityMappingsItemContentProvider extends AbstractTreeItemContentProvider<XmlPersistentType>
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
		protected ListValueModel<XmlPersistentType> buildChildrenModel() {
			return new ListAspectAdapter<EntityMappings, XmlPersistentType>(
					EntityMappings.PERSISTENT_TYPES_LIST, (EntityMappings) model()) {
				@Override
				protected ListIterator<XmlPersistentType> listIterator_() {
					return subject.xmlPersistentTypes();
				}
			};
		}
	}
}

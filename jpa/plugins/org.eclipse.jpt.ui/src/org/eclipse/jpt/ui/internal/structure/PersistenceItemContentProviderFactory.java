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
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.internal.IJpaStructureNode;
import org.eclipse.jpt.core.internal.IResourceModel;
import org.eclipse.jpt.core.internal.context.persistence.IClassRef;
import org.eclipse.jpt.core.internal.context.persistence.IMappingFileRef;
import org.eclipse.jpt.core.internal.context.persistence.IPersistence;
import org.eclipse.jpt.core.internal.context.persistence.IPersistenceUnit;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModel;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.ITreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.ITreeItemContentProviderFactory;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyCompositeListIterator;
import org.eclipse.jpt.utility.internal.model.value.CollectionListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyCollectionValueModelAdapter;

public class PersistenceItemContentProviderFactory
	implements ITreeItemContentProviderFactory
{
	public ITreeItemContentProvider buildItemContentProvider(
			Object item, DelegatingContentAndLabelProvider contentAndLabelProvider) {
		DelegatingTreeContentAndLabelProvider treeContentProvider = (DelegatingTreeContentAndLabelProvider) contentAndLabelProvider;
		if (item instanceof PersistenceResourceModel) {
			return new PersistenceResourceModelItemContentProvider((PersistenceResourceModel) item, treeContentProvider);
		}
		else if (item instanceof IPersistence) {
			return new PersistenceItemContentProvider((IPersistence) item, treeContentProvider);
		}
		else if (item instanceof IPersistenceUnit) {
			return new PersistenceUnitItemContentProvider((IPersistenceUnit) item, treeContentProvider);	
		}
		else if (item instanceof IMappingFileRef) {
			return new MappingFileRefItemContentProvider((IMappingFileRef) item, treeContentProvider);	
		}
		else if (item instanceof IClassRef) {
			return new ClassRefItemContentProvider((IClassRef) item, treeContentProvider);	
		}
		return null;
	}
	
	
	public static class PersistenceResourceModelItemContentProvider extends AbstractTreeItemContentProvider<IJpaStructureNode>
	{
		public PersistenceResourceModelItemContentProvider(
				PersistenceResourceModel persistenceResourceModel, 
				DelegatingTreeContentAndLabelProvider contentProvider) {
			super(persistenceResourceModel, contentProvider);
		}
		
		@Override
		public Object getParent() {
			return null;
		}
		
		@Override
		protected ListValueModel<IJpaStructureNode> buildChildrenModel() {
			return new ListAspectAdapter<PersistenceResourceModel, IJpaStructureNode>(
					IResourceModel.ROOT_STRUCTURE_NODES_LIST, (PersistenceResourceModel) model()) {
				@Override
				protected ListIterator<IJpaStructureNode> listIterator_() {
					return subject.rootStructureNodes();
				}
			};
		}	
	}
	
	
	public static class PersistenceItemContentProvider extends AbstractTreeItemContentProvider<IPersistenceUnit>
	{
		public PersistenceItemContentProvider(
				IPersistence persistence, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(persistence, contentProvider);
		}
		
		
		@Override
		public Object getParent() {
			// I'd like to return the resource model here, but that involves a hefty 
			// API change - we'll see what happens with this code first
			return null;
		}
		
		@Override
		protected ListValueModel<IPersistenceUnit> buildChildrenModel() {
			return new ListAspectAdapter<IPersistence, IPersistenceUnit>(
					IPersistence.PERSISTENCE_UNITS_LIST, (IPersistence) model()) {
				@Override
				protected ListIterator<IPersistenceUnit> listIterator_() {
					return subject.persistenceUnits();
				}
			};
		}
	}
	
	
	public static class PersistenceUnitItemContentProvider extends AbstractTreeItemContentProvider<IJpaStructureNode>
	{
		public PersistenceUnitItemContentProvider(
				IPersistenceUnit persistenceUnit, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(persistenceUnit, contentProvider);
		}
		
		@Override
		public Object getParent() {
			return ((IPersistenceUnit) model()).persistence();
		}
		
		@Override
		protected ListValueModel<IJpaStructureNode> buildChildrenModel() {
			ListValueModel<IJpaStructureNode> specifiedMappingFileLvm = 
				new ListAspectAdapter<IPersistenceUnit, IJpaStructureNode>(
						IPersistenceUnit.SPECIFIED_MAPPING_FILE_REF_LIST,
						(IPersistenceUnit) model()) {
					@Override
					protected ListIterator<IJpaStructureNode> listIterator_() {
						return new ReadOnlyCompositeListIterator<IJpaStructureNode>(
							subject.specifiedMappingFileRefs());
					}
				};
			
			ListValueModel<IJpaStructureNode> impliedMappingFileLvm = 
				new CollectionListValueModelAdapter<IJpaStructureNode>(
					new PropertyCollectionValueModelAdapter<IJpaStructureNode>(
						new PropertyAspectAdapter<IPersistenceUnit, IJpaStructureNode>(
								IPersistenceUnit.IMPLIED_MAPPING_FILE_REF_PROPERTY,
								(IPersistenceUnit) model()) {
							 @Override
							protected IJpaStructureNode buildValue_() {
								return subject.getImpliedMappingFileRef();
							}
						}));
			ListValueModel<IJpaStructureNode> classLvm = 
				new ListAspectAdapter<IPersistenceUnit, IJpaStructureNode>(
						new String[] {IPersistenceUnit.SPECIFIED_CLASS_REF_LIST, IPersistenceUnit.IMPLIED_CLASS_REF_LIST},
						(IPersistenceUnit) model()) {
					@Override
					protected ListIterator<IJpaStructureNode> listIterator_() {
						return new ReadOnlyCompositeListIterator<IJpaStructureNode>(
							subject.classRefs());
					}
				};
			List<ListValueModel<IJpaStructureNode>> list = new ArrayList<ListValueModel<IJpaStructureNode>>();
			list.add(specifiedMappingFileLvm);
			list.add(impliedMappingFileLvm);
			list.add(classLvm);
			return new CompositeListValueModel<ListValueModel<IJpaStructureNode>, IJpaStructureNode>(list);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static class MappingFileRefItemContentProvider extends AbstractTreeItemContentProvider
	{
		public MappingFileRefItemContentProvider(
				IMappingFileRef mappingFileRef, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(mappingFileRef, contentProvider);
		}
		
		@Override
		public Object getParent() {
			return ((IMappingFileRef) model()).persistenceUnit();
		}
		
		@Override
		public boolean hasChildren() {
			return false;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static class ClassRefItemContentProvider extends AbstractTreeItemContentProvider
	{
		public ClassRefItemContentProvider(
				IClassRef classRef, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(classRef, contentProvider);
		}
		
		@Override
		public Object getParent() {
			return ((IClassRef) model()).persistenceUnit();
		}
		
		@Override
		public boolean hasChildren() {
			return false;
		}
	}
}

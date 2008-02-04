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

import java.util.ListIterator;
import org.eclipse.jpt.core.internal.IResourceModel;
import org.eclipse.jpt.core.internal.context.base.IClassRef;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.base.IMappingFileRef;
import org.eclipse.jpt.core.internal.context.base.IPersistence;
import org.eclipse.jpt.core.internal.context.base.IPersistenceUnit;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModel;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.ITreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.ITreeItemContentProviderFactory;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyCompositeListIterator;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;

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
	
	
	public static class PersistenceResourceModelItemContentProvider extends AbstractTreeItemContentProvider<IJpaContextNode>
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
		protected ListValueModel<IJpaContextNode> buildChildrenModel() {
			return new ListAspectAdapter<PersistenceResourceModel, IJpaContextNode>(
					IResourceModel.ROOT_CONTEXT_NODE_LIST, (PersistenceResourceModel) model()) {
				@Override
				protected ListIterator<IJpaContextNode> listIterator_() {
					return subject.rootContextNodes();
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
	
	
	public static class PersistenceUnitItemContentProvider extends AbstractTreeItemContentProvider<IJpaContextNode>
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
		protected ListValueModel<IJpaContextNode> buildChildrenModel() {
			return new ListAspectAdapter<IPersistenceUnit, IJpaContextNode>(
					new String[] {IPersistenceUnit.SPECIFIED_MAPPING_FILE_REF_LIST, IPersistenceUnit.SPECIFIED_CLASS_REF_LIST, IPersistenceUnit.IMPLIED_CLASS_REF_LIST},
					(IPersistenceUnit) model()) {
				@Override
				protected ListIterator<IJpaContextNode> listIterator_() {
					return new ReadOnlyCompositeListIterator<IJpaContextNode>(
						subject.mappingFileRefs(), subject.classRefs());
				}
			};
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

/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.ResourceModel;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.resource.persistence.PersistenceResourceModel;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.TreeItemContentProvider;
import org.eclipse.jpt.ui.jface.TreeItemContentProviderFactory;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;

public class PersistenceItemContentProviderFactory
	implements TreeItemContentProviderFactory
{
	public TreeItemContentProvider buildItemContentProvider(
			Object item, DelegatingContentAndLabelProvider contentAndLabelProvider) {
		DelegatingTreeContentAndLabelProvider treeContentProvider = (DelegatingTreeContentAndLabelProvider) contentAndLabelProvider;
		if (item instanceof PersistenceResourceModel) {
			return new PersistenceResourceModelItemContentProvider((PersistenceResourceModel) item, treeContentProvider);
		}
		else if (item instanceof Persistence) {
			return new PersistenceItemContentProvider((Persistence) item, treeContentProvider);
		}
		else if (item instanceof PersistenceUnit) {
			return new PersistenceUnitItemContentProvider((PersistenceUnit) item, treeContentProvider);	
		}
		else if (item instanceof MappingFileRef) {
			return new MappingFileRefItemContentProvider((MappingFileRef) item, treeContentProvider);	
		}
		else if (item instanceof ClassRef) {
			return new ClassRefItemContentProvider((ClassRef) item, treeContentProvider);	
		}
		return null;
	}
	
	
	public static class PersistenceResourceModelItemContentProvider extends AbstractTreeItemContentProvider<JpaStructureNode>
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
		protected ListValueModel<JpaStructureNode> buildChildrenModel() {
			return new ListAspectAdapter<PersistenceResourceModel, JpaStructureNode>(
					ResourceModel.ROOT_STRUCTURE_NODES_LIST, (PersistenceResourceModel) model()) {
				@Override
				protected ListIterator<JpaStructureNode> listIterator_() {
					return subject.rootStructureNodes();
				}
			};
		}	
	}
	
	
	public static class PersistenceItemContentProvider extends AbstractTreeItemContentProvider<PersistenceUnit>
	{
		public PersistenceItemContentProvider(
				Persistence persistence, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(persistence, contentProvider);
		}
			
		@Override
		public Persistence model() {
			return (Persistence) super.model();
		}
		
		@Override
		public Object getParent() {
			// I'd like to return the resource model here, but that involves a hefty 
			// API change - we'll see what happens with this code first
			return null;
		}
		
		@Override
		protected ListValueModel<PersistenceUnit> buildChildrenModel() {
			return new ListAspectAdapter<Persistence, PersistenceUnit>(
					Persistence.PERSISTENCE_UNITS_LIST, model()) {
				@Override
				protected ListIterator<PersistenceUnit> listIterator_() {
					return subject.persistenceUnits();
				}
				@Override
				protected int size_() {
					return subject.persistenceUnitsSize();
				}
			};
		}
	}
	
	
	public static class PersistenceUnitItemContentProvider extends AbstractTreeItemContentProvider<JpaStructureNode>
	{
		public PersistenceUnitItemContentProvider(
				PersistenceUnit persistenceUnit, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(persistenceUnit, contentProvider);
		}
		
		@Override
		public PersistenceUnit model() {
			return (PersistenceUnit) super.model();
		}
		
		@Override
		public Persistence getParent() {
			return model().getParent();
		}
		
		@Override
		protected ListValueModel<JpaStructureNode> buildChildrenModel() {
			ListValueModel<MappingFileRef> specifiedMappingFileLvm = 
				new ListAspectAdapter<PersistenceUnit, MappingFileRef>(
						PersistenceUnit.SPECIFIED_MAPPING_FILE_REF_LIST,
						model()) {
					@Override
					protected ListIterator<MappingFileRef> listIterator_() {
						return subject.specifiedMappingFileRefs();
					}
					@Override
					protected int size_() {
						return subject.specifiedMappingFileRefsSize();
					}
				};
			
			ListValueModel<MappingFileRef> impliedMappingFileLvm = 
				new PropertyListValueModelAdapter<MappingFileRef>(
					new PropertyAspectAdapter<PersistenceUnit, MappingFileRef>(
							PersistenceUnit.IMPLIED_MAPPING_FILE_REF_PROPERTY,
							model()) {
						@Override
						protected MappingFileRef buildValue_() {
							return subject.getImpliedMappingFileRef();
						}
					}
				);
			ListValueModel<ClassRef> specifiedClassLvm = 
				new ListAspectAdapter<PersistenceUnit, ClassRef>(
						PersistenceUnit.SPECIFIED_CLASS_REF_LIST,
						model()) {
					@Override
					protected ListIterator<ClassRef> listIterator_() {
						return subject.specifiedClassRefs();
					}
					@Override
					protected int size_() {
						return subject.specifiedClassRefsSize();
					}
				};
			ListValueModel<ClassRef> impliedClassLvm = 
				new ListAspectAdapter<PersistenceUnit, ClassRef>(
						PersistenceUnit.IMPLIED_CLASS_REF_LIST,
						model()) {
					@Override
					protected ListIterator<ClassRef> listIterator_() {
						return subject.impliedClassRefs();
					}
					@Override
					protected int size_() {
						return subject.impliedClassRefsSize();
					}
				};
			List<ListValueModel<? extends JpaStructureNode>> list = new ArrayList<ListValueModel<? extends JpaStructureNode>>(4);
			list.add(specifiedMappingFileLvm);
			list.add(impliedMappingFileLvm);
			list.add(specifiedClassLvm);
			list.add(impliedClassLvm);
			
			return new CompositeListValueModel<ListValueModel<? extends JpaStructureNode>, JpaStructureNode>(list);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static class MappingFileRefItemContentProvider extends AbstractTreeItemContentProvider
	{
		public MappingFileRefItemContentProvider(
				MappingFileRef mappingFileRef, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(mappingFileRef, contentProvider);
		}
		
		@Override
		public Object getParent() {
			return ((MappingFileRef) model()).getPersistenceUnit();
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
				ClassRef classRef, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(classRef, contentProvider);
		}
		
		@Override
		public Object getParent() {
			return ((ClassRef) model()).getPersistenceUnit();
		}
		
		@Override
		public boolean hasChildren() {
			return false;
		}
	}
}

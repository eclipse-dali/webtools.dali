/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.common.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.common.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.ui.jface.TreeItemContentProvider;
import org.eclipse.jpt.common.ui.jface.TreeItemContentProviderFactory;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;

public class PersistenceItemContentProviderFactory
	implements TreeItemContentProviderFactory
{
	public TreeItemContentProvider buildItemContentProvider(
			Object item, DelegatingContentAndLabelProvider contentAndLabelProvider) {
		DelegatingTreeContentAndLabelProvider treeContentProvider = (DelegatingTreeContentAndLabelProvider) contentAndLabelProvider;
		if (item instanceof JpaFile) {
			return new ResourceModelItemContentProvider((JpaFile) item, treeContentProvider);
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
		else if (item instanceof JarFileRef) {
			return new JarFileRefItemContentProvider((JarFileRef) item, treeContentProvider);
		}
		return null;
	}
	
	
	public static class PersistenceItemContentProvider extends AbstractTreeItemContentProvider<PersistenceUnit>
	{
		public PersistenceItemContentProvider(
				Persistence persistence, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(persistence, contentProvider);
		}
			
		@Override
		public Persistence getModel() {
			return (Persistence) super.getModel();
		}
		
		@Override
		public Object getParent() {
			// I'd like to return the resource model here, but that involves a hefty 
			// API change - we'll see what happens with this code first
			return null;
		}
		
		@Override
		protected CollectionValueModel<PersistenceUnit> buildChildrenModel() {
			return new ListCollectionValueModelAdapter<PersistenceUnit>(
			new ListAspectAdapter<Persistence, PersistenceUnit>(Persistence.PERSISTENCE_UNITS_LIST, getModel()) {
				@Override
				protected ListIterator<PersistenceUnit> listIterator_() {
					return subject.persistenceUnits();
				}
				@Override
				protected int size_() {
					return subject.persistenceUnitsSize();
				}
			});
		}
	}
	
	
	public static class PersistenceUnitItemContentProvider extends AbstractTreeItemContentProvider<JpaStructureNode>
	{
		public PersistenceUnitItemContentProvider(
				PersistenceUnit persistenceUnit, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(persistenceUnit, contentProvider);
		}
		
		@Override
		public PersistenceUnit getModel() {
			return (PersistenceUnit) super.getModel();
		}
		
		@Override
		public Persistence getParent() {
			return getModel().getParent();
		}
		
		@Override
		protected CollectionValueModel<JpaStructureNode> buildChildrenModel() {
			ListValueModel<MappingFileRef> specifiedMappingFileLvm = 
				new ListAspectAdapter<PersistenceUnit, MappingFileRef>(
						PersistenceUnit.SPECIFIED_MAPPING_FILE_REFS_LIST,
						getModel()) {
					@Override
					protected ListIterator<MappingFileRef> listIterator_() {
						return subject.specifiedMappingFileRefs();
					}
					@Override
					protected int size_() {
						return subject.specifiedMappingFileRefsSize();
					}
				};
			
			ListValueModel<MappingFileRef> impliedMappingFileCvm = 
				new PropertyListValueModelAdapter<MappingFileRef>(
					new PropertyAspectAdapter<PersistenceUnit, MappingFileRef>(
							PersistenceUnit.IMPLIED_MAPPING_FILE_REF_PROPERTY,
							getModel()) {
						@Override
						protected MappingFileRef buildValue_() {
							return subject.getImpliedMappingFileRef();
						}
					}
				);
			ListValueModel<ClassRef> specifiedClassCvm = 
				new ListAspectAdapter<PersistenceUnit, ClassRef>(
						PersistenceUnit.SPECIFIED_CLASS_REFS_LIST,
						getModel()) {
					@Override
					protected ListIterator<ClassRef> listIterator_() {
						return subject.specifiedClassRefs();
					}
					@Override
					protected int size_() {
						return subject.specifiedClassRefsSize();
					}
				};
			ListValueModel<ClassRef> impliedClassCvm = 
				new CollectionListValueModelAdapter<ClassRef>(
					new CollectionAspectAdapter<PersistenceUnit, ClassRef>(
							PersistenceUnit.IMPLIED_CLASS_REFS_COLLECTION,
							getModel()) {
						@Override
						protected Iterator<ClassRef> iterator_() {
							return subject.impliedClassRefs();
						}
						@Override
						protected int size_() {
							return subject.impliedClassRefsSize();
						}
					});
			ListValueModel<JarFileRef> jarFileCvm =
				new ListAspectAdapter<PersistenceUnit, JarFileRef>(
						PersistenceUnit.JAR_FILE_REFS_LIST,
						getModel()) {
					@Override
					protected ListIterator<JarFileRef> listIterator_() {
						return subject.jarFileRefs();
					}
					@Override
					protected int size_() {
						return subject.jarFileRefsSize();
					}
				};
			List<ListValueModel<? extends JpaStructureNode>> list = new ArrayList<ListValueModel<? extends JpaStructureNode>>(4);
			list.add(specifiedMappingFileLvm);
			list.add(impliedMappingFileCvm);
			list.add(specifiedClassCvm);
			list.add(impliedClassCvm);
			list.add(jarFileCvm);
			
			return new ListCollectionValueModelAdapter<JpaStructureNode>(
				new CompositeListValueModel
					<ListValueModel<? extends JpaStructureNode>, JpaStructureNode>
						(list));
		}
	}
	
	
	public static class MappingFileRefItemContentProvider extends AbstractTreeItemContentProvider<MappingFileRef>
	{
		public MappingFileRefItemContentProvider(
				MappingFileRef mappingFileRef, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(mappingFileRef, contentProvider);
		}
		
		@Override
		public MappingFileRef getModel() {
			return (MappingFileRef) super.getModel();
		}
		
		@Override
		public Object getParent() {
			return getModel().getPersistenceUnit();
		}
		
		@Override
		public boolean hasChildren() {
			return false;
		}
	}
	
	
	public static class ClassRefItemContentProvider extends AbstractTreeItemContentProvider<ClassRef>
	{
		public ClassRefItemContentProvider(
				ClassRef classRef, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(classRef, contentProvider);
		}
		
		@Override
		public ClassRef getModel() {
			return (ClassRef) super.getModel();
		}
		
		@Override
		public Object getParent() {
			return getModel().getPersistenceUnit();
		}
		
		@Override
		public boolean hasChildren() {
			return false;
		}
	}
	
	
	public static class JarFileRefItemContentProvider extends AbstractTreeItemContentProvider<JarFileRef>
	{
		public JarFileRefItemContentProvider(
				JarFileRef jarFileRef, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(jarFileRef, contentProvider);
		}
		
		@Override
		public JarFileRef getModel() {
			return (JarFileRef) super.getModel();
		}
		
		@Override
		public Object getParent() {
			return getModel().getPersistenceUnit();
		}
		
		@Override
		public boolean hasChildren() {
			return false;
		}
	}
}

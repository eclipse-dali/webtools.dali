/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.common.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.ui.jface.TreeItemContentProvider;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.ui.internal.structure.PersistenceItemContentProviderFactory;

public class EclipseLinkPersistenceItemContentProviderFactory
	extends PersistenceItemContentProviderFactory
{
	@Override
	public TreeItemContentProvider buildItemContentProvider(
			Object item, DelegatingContentAndLabelProvider contentAndLabelProvider) {
		DelegatingTreeContentAndLabelProvider treeContentProvider = (DelegatingTreeContentAndLabelProvider) contentAndLabelProvider;
		if (item instanceof EclipseLinkPersistenceUnit) {
			return new EclipseLinkPersistenceUnitItemContentProvider((EclipseLinkPersistenceUnit) item, treeContentProvider);	
		}
		return super.buildItemContentProvider(item, contentAndLabelProvider);
	}
	
	
	public static class EclipseLinkPersistenceUnitItemContentProvider 
		extends PersistenceUnitItemContentProvider
	{
		public EclipseLinkPersistenceUnitItemContentProvider(
				EclipseLinkPersistenceUnit persistenceUnit, DelegatingTreeContentAndLabelProvider contentProvider) {
			super(persistenceUnit, contentProvider);
		}
		
		@Override
		public EclipseLinkPersistenceUnit getModel() {
			return (EclipseLinkPersistenceUnit) super.getModel();
		}
		
		@Override
		protected CollectionValueModel<JpaStructureNode> buildChildrenModel() {
			CollectionValueModel<MappingFileRef> specifiedMappingFileCvm = 
				new ListCollectionValueModelAdapter<MappingFileRef>(
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
				});
			
			CollectionValueModel<MappingFileRef> impliedMappingFileCvm = 
				new PropertyCollectionValueModelAdapter<MappingFileRef>(
					new PropertyAspectAdapter<PersistenceUnit, MappingFileRef>(
							PersistenceUnit.IMPLIED_MAPPING_FILE_REF_PROPERTY,
							getModel()) {
						@Override
						protected MappingFileRef buildValue_() {
							return subject.getImpliedMappingFileRef();
						}
					}
				);
			CollectionValueModel<MappingFileRef> impliedEclipseLinkMappingFileCvm = 
				new PropertyCollectionValueModelAdapter<MappingFileRef>(
					new PropertyAspectAdapter<EclipseLinkPersistenceUnit, MappingFileRef>(
							EclipseLinkPersistenceUnit.IMPLIED_ECLIPSELINK_MAPPING_FILE_REF_PROPERTY,
							getModel()) {
						@Override
						protected MappingFileRef buildValue_() {
							return subject.getImpliedEclipseLinkMappingFileRef();
						}
					}
				);
			CollectionValueModel<ClassRef> specifiedClassCvm = 
				new ListCollectionValueModelAdapter<ClassRef>(
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
				});
			CollectionValueModel<ClassRef> impliedClassCvm = 
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
				};
			List<CollectionValueModel<? extends JpaStructureNode>> list = new ArrayList<CollectionValueModel<? extends JpaStructureNode>>(4);
			list.add(specifiedMappingFileCvm);
			list.add(impliedMappingFileCvm);
			list.add(impliedEclipseLinkMappingFileCvm);
			list.add(specifiedClassCvm);
			list.add(impliedClassCvm);
			
			return new CompositeCollectionValueModel<CollectionValueModel<? extends JpaStructureNode>, JpaStructureNode>(list);
		}
	}
}

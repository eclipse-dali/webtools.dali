/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.structure.PersistenceItemContentProviderFactory;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.TreeItemContentProvider;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;

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
		protected ListValueModel<JpaStructureNode> buildChildrenModel() {
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
			
			ListValueModel<MappingFileRef> impliedMappingFileLvm = 
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
			ListValueModel<MappingFileRef> impliedEclipseLinkMappingFileLvm = 
				new PropertyListValueModelAdapter<MappingFileRef>(
					new PropertyAspectAdapter<EclipseLinkPersistenceUnit, MappingFileRef>(
							EclipseLinkPersistenceUnit.IMPLIED_ECLIPSELINK_MAPPING_FILE_REF_PROPERTY,
							getModel()) {
						@Override
						protected MappingFileRef buildValue_() {
							return subject.getImpliedEclipseLinkMappingFileRef();
						}
					}
				);
			ListValueModel<ClassRef> specifiedClassLvm = 
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
			ListValueModel<ClassRef> impliedClassLvm = 
				new ListAspectAdapter<PersistenceUnit, ClassRef>(
						PersistenceUnit.IMPLIED_CLASS_REFS_LIST,
						getModel()) {
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
			list.add(impliedEclipseLinkMappingFileLvm);
			list.add(specifiedClassLvm);
			list.add(impliedClassLvm);
			
			return new CompositeListValueModel<ListValueModel<? extends JpaStructureNode>, JpaStructureNode>(list);
		}
	}
}

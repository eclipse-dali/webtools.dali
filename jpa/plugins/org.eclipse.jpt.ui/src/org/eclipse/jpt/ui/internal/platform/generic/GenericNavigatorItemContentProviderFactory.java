/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.platform.generic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.TreeItemContentProvider;
import org.eclipse.jpt.ui.jface.TreeItemContentProviderFactory;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.model.value.CollectionListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.FilteringCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationListValueModelAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;

public class GenericNavigatorItemContentProviderFactory
	implements TreeItemContentProviderFactory
{
	public TreeItemContentProvider buildItemContentProvider(Object item, DelegatingContentAndLabelProvider contentAndLabelProvider) {
		DelegatingTreeContentAndLabelProvider treeContentAndLabelProvider = (DelegatingTreeContentAndLabelProvider) contentAndLabelProvider;
		
		if (item instanceof JpaRootContextNode) {
			return new RootContextItemContentProvider((JpaRootContextNode) item, treeContentAndLabelProvider);
		}
		else if (item instanceof PersistenceXml) {
			return new PersistenceXmlItemContentProvider((PersistenceXml) item, treeContentAndLabelProvider);	
		}
		else if (item instanceof PersistenceUnit) {
			return new PersistenceUnitItemContentProvider((PersistenceUnit) item, treeContentAndLabelProvider);	
		}
		else if (item instanceof OrmXml) {
			return new OrmXmlItemContentProvider((OrmXml) item, treeContentAndLabelProvider);	
		}
		else if (item instanceof OrmPersistentType) {
			return new OrmPersistentTypeItemContentProvider((OrmPersistentType) item, treeContentAndLabelProvider);	
		}
		else if (item instanceof JavaPersistentType) {
			return new JavaPersistentTypeItemContentProvider((JavaPersistentType) item, treeContentAndLabelProvider);	
		}
		else if (item instanceof PersistentAttribute) {
			return new PersistentAttributeItemContentProvider((PersistentAttribute) item, treeContentAndLabelProvider);	
		}
		return null;
	}
	
	
	public static class PersistenceUnitItemContentProvider extends AbstractTreeItemContentProvider<JpaContextNode>
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
		public PersistenceXml getParent() {
			return getModel().getParent().getParent();
		}
	
		@Override
		protected ListValueModel<JpaContextNode> buildChildrenModel() {
			List<ListValueModel<? extends JpaContextNode>> list = new ArrayList<ListValueModel<? extends JpaContextNode>>();
			list.add(buildSpecifiedOrmXmlLvm());
			list.add(buildImpliedMappingFileLvm());
			list.add(buildPersistentTypeLvm());
			return new CompositeListValueModel<ListValueModel<? extends JpaContextNode>, JpaContextNode>(list);
		}
		
		protected ListValueModel<JpaContextNode> buildSpecifiedOrmXmlLvm() {
			return new CollectionListValueModelAdapter<JpaContextNode>(
				new FilteringCollectionValueModel<MappingFile>(
					new ListCollectionValueModelAdapter<MappingFile>(
						new TransformationListValueModelAdapter<MappingFileRef, MappingFile>(
							new ItemPropertyListValueModelAdapter<MappingFileRef>(
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
								}, MappingFileRef.MAPPING_FILE_PROPERTY)) {
							@Override
							protected MappingFile transformItem(MappingFileRef item) {
								return item.getMappingFile();
							}
						})) {
					@Override
					protected Iterator<MappingFile> filter(Iterator<? extends MappingFile> items) {
						return new FilteringIterator<MappingFile, MappingFile>(items) {
							@Override
							protected boolean accept(MappingFile o) {
								return o != null;
							}
						};
					}
				});
		}
		
		protected ListValueModel<MappingFile> buildImpliedMappingFileLvm() {
			return new PropertyListValueModelAdapter<MappingFile>(
				new PropertyAspectAdapter<MappingFileRef, MappingFile>(
						new PropertyAspectAdapter<PersistenceUnit, MappingFileRef>(
								PersistenceUnit.IMPLIED_MAPPING_FILE_REF_PROPERTY,
								getModel()) {
							@Override
							protected MappingFileRef buildValue_() {
								return subject.getImpliedMappingFileRef();
							}
						},
						MappingFileRef.MAPPING_FILE_PROPERTY) {
					@Override
					protected MappingFile buildValue_() {
						return subject.getMappingFile();
					}
				}
			);
		}
		
		protected ListValueModel<JpaContextNode> buildPersistentTypeLvm() {
			return new CollectionListValueModelAdapter<JpaContextNode>(
				new FilteringCollectionValueModel<PersistentType>(
					new ListCollectionValueModelAdapter<PersistentType>(
						new TransformationListValueModelAdapter<ClassRef, PersistentType>(
							new ItemPropertyListValueModelAdapter<ClassRef>(buildClassRefLvm(), ClassRef.JAVA_PERSISTENT_TYPE_PROPERTY)) {
							@Override
							protected PersistentType transformItem(ClassRef item) {
								return item.getJavaPersistentType();
							}
						})) {
					@Override
					protected Iterator<PersistentType> filter(Iterator<? extends PersistentType> items) {
						return new FilteringIterator<PersistentType, PersistentType>(items) {
							@Override
							protected boolean accept(PersistentType o) {
								return o != null;
							}
						};
					}
				});
		}
		
		protected ListValueModel<ClassRef> buildClassRefLvm() {
			ArrayList<ListValueModel<ClassRef>> holders = new ArrayList<ListValueModel<ClassRef>>(2);
			holders.add(buildSpecifiedClassRefLvm());
			holders.add(buildImpliedClassRefLvm());
			return new CompositeListValueModel<ListValueModel<ClassRef>, ClassRef>(holders);
		}
		
		protected ListValueModel<ClassRef> buildSpecifiedClassRefLvm() {
			return new ListAspectAdapter<PersistenceUnit, ClassRef>(
				PersistenceUnit.SPECIFIED_CLASS_REFS_LIST, getModel()) {
					@Override
					protected ListIterator<ClassRef> listIterator_() {
						return subject.specifiedClassRefs();
					}
					@Override
					protected int size_() {
						return subject.specifiedClassRefsSize();
					}
			};
		}
		
		protected ListValueModel<ClassRef> buildImpliedClassRefLvm() {
			return new ListAspectAdapter<PersistenceUnit, ClassRef>(
				PersistenceUnit.IMPLIED_CLASS_REFS_LIST, getModel()) {
					@Override
					protected ListIterator<ClassRef> listIterator_() {
						return subject.impliedClassRefs();
					}
					@Override
					protected int size_() {
						return subject.impliedClassRefsSize();
					}
			};
		}
	}
}

/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.java.JarFile;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.TreeItemContentProvider;
import org.eclipse.jpt.ui.jface.TreeItemContentProviderFactory;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.FilteringCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationListValueModelAdapter;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;

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
		protected CollectionValueModel<JpaContextNode> buildChildrenModel() {
			List<CollectionValueModel<? extends JpaContextNode>> list = new ArrayList<CollectionValueModel<? extends JpaContextNode>>();
			list.add(buildSpecifiedOrmXmlCvm());
			list.add(buildImpliedMappingFileCvm());
			list.add(buildPersistentTypeCvm());
			list.add(buildJarFileCvm());
			return new CompositeCollectionValueModel<CollectionValueModel<? extends JpaContextNode>, JpaContextNode>(list);
		}
		
		protected CollectionValueModel<JpaContextNode> buildSpecifiedOrmXmlCvm() {
			return new FilteringCollectionValueModel<JpaContextNode>(
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
					protected Iterable<JpaContextNode> filter(Iterable<? extends JpaContextNode> items) {
						return new FilteringIterable<JpaContextNode, JpaContextNode>(items) {
							@Override
							protected boolean accept(JpaContextNode o) {
								return o != null;
							}
						};
					}
				};
		}
		
		protected CollectionValueModel<MappingFile> buildImpliedMappingFileCvm() {
			return new PropertyCollectionValueModelAdapter<MappingFile>(
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
		
		protected CollectionValueModel<JpaContextNode> buildPersistentTypeCvm() {
			return new FilteringCollectionValueModel<JpaContextNode>(
					new ListCollectionValueModelAdapter<PersistentType>(
						new TransformationListValueModelAdapter<ClassRef, PersistentType>(
							new ItemPropertyListValueModelAdapter<ClassRef>(buildClassRefCvm(), ClassRef.JAVA_PERSISTENT_TYPE_PROPERTY)) {
							@Override
							protected PersistentType transformItem(ClassRef item) {
								return item.getJavaPersistentType();
							}
						})) {
					@Override
					protected Iterable<JpaContextNode> filter(Iterable<? extends JpaContextNode> items) {
						return new FilteringIterable<JpaContextNode, JpaContextNode>(items) {
							@Override
							protected boolean accept(JpaContextNode o) {
								return o != null;
							}
						};
					}
				};
		}
		
		protected CollectionValueModel<ClassRef> buildClassRefCvm() {
			ArrayList<CollectionValueModel<ClassRef>> holders = new ArrayList<CollectionValueModel<ClassRef>>(2);
			holders.add(buildSpecifiedClassRefCvm());
			holders.add(buildImpliedClassRefCvm());
			return new CompositeCollectionValueModel<CollectionValueModel<ClassRef>, ClassRef>(holders);
		}
		
		protected CollectionValueModel<ClassRef> buildSpecifiedClassRefCvm() {
			return new ListCollectionValueModelAdapter<ClassRef>(
			new ListAspectAdapter<PersistenceUnit, ClassRef>(
				PersistenceUnit.SPECIFIED_CLASS_REFS_LIST, getModel()) {
					@Override
					protected ListIterator<ClassRef> listIterator_() {
						return subject.specifiedClassRefs();
					}
					@Override
					protected int size_() {
						return subject.specifiedClassRefsSize();
					}
			});
		}
		
		protected CollectionValueModel<ClassRef> buildImpliedClassRefCvm() {
			return new CollectionAspectAdapter<PersistenceUnit, ClassRef>(
				PersistenceUnit.IMPLIED_CLASS_REFS_COLLECTION, getModel()) {
					@Override
					protected Iterator<ClassRef> iterator_() {
						return subject.impliedClassRefs();
					}
					@Override
					protected int size_() {
						return subject.impliedClassRefsSize();
					}
			};
		}
		
		protected CollectionValueModel<JpaContextNode> buildJarFileCvm() {
			return new FilteringCollectionValueModel<JpaContextNode>(
					new ListCollectionValueModelAdapter<JarFile>(
						new TransformationListValueModelAdapter<JarFileRef, JarFile>(
							new ItemPropertyListValueModelAdapter<JarFileRef>(buildJarFileRefCvm(), JarFileRef.JAR_FILE_PROPERTY)) {
							@Override
							protected JarFile transformItem(JarFileRef item) {
								return item.getJarFile();
							}
						})) {
					@Override
					protected Iterable<JpaContextNode> filter(Iterable<? extends JpaContextNode> items) {
						return new FilteringIterable<JpaContextNode, JpaContextNode>(items) {
							@Override
							protected boolean accept(JpaContextNode o) {
								return o != null;
							}
						};
					}
				};
		}
		
		protected CollectionValueModel<JarFileRef> buildJarFileRefCvm() {
			return new ListCollectionValueModelAdapter<JarFileRef>(
				new ListAspectAdapter<PersistenceUnit, JarFileRef>(
					PersistenceUnit.JAR_FILE_REFS_LIST, getModel()) {
						@Override
						protected ListIterator<JarFileRef> listIterator_() {
							return subject.jarFileRefs();
						}
						@Override
						protected int size_() {
							return subject.jarFileRefsSize();
						}
				});
		}
	}
}

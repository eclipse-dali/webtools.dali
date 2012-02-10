/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.structure;

import java.util.ArrayList;
import org.eclipse.jpt.common.ui.internal.jface.AbstractItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;

/**
 * Item content provider for structure view.
 */
public class PersistenceUnitItemContentProvider
	extends AbstractItemTreeContentProvider<PersistenceUnit, JpaStructureNode>
{
	public PersistenceUnitItemContentProvider(PersistenceUnit persistenceUnit, Manager manager) {
		super(persistenceUnit, manager);
	}

	public Persistence getParent() {
		return this.item.getParent();
	}

	@Override
	protected CollectionValueModel<JpaStructureNode> buildChildrenModel() {
		return new ListCollectionValueModelAdapter<JpaStructureNode>(this.buildChildrenModel_());
	}

	protected ListValueModel<JpaStructureNode> buildChildrenModel_() {
		ArrayList<ListValueModel<? extends JpaStructureNode>> list = new ArrayList<ListValueModel<? extends JpaStructureNode>>();
		this.addChildrenModelsTo(list);
		return new CompositeListValueModel<ListValueModel<? extends JpaStructureNode>, JpaStructureNode>(list);
	}

	protected void addChildrenModelsTo(ArrayList<ListValueModel<? extends JpaStructureNode>> list) {
		list.add(this.buildSpecifiedMappingFileRefsModel());
		list.add(this.buildImpliedMappingFileRefsModel());
		list.add(this.buildSpecifiedClassRefsModel());
		list.add(this.buildImpliedClassRefsModel());
		list.add(this.buildJarFileRefsModel());
	}

	protected ListValueModel<MappingFileRef> buildSpecifiedMappingFileRefsModel() {
		return new SpecifiedMappingFileRefsModel(this.item);
	}

	public static class SpecifiedMappingFileRefsModel
		extends ListAspectAdapter<PersistenceUnit, MappingFileRef>
	{
		public SpecifiedMappingFileRefsModel(PersistenceUnit persistenceUnit) {
			super(PersistenceUnit.SPECIFIED_MAPPING_FILE_REFS_LIST, persistenceUnit);
		}

		@Override
		protected ListIterable<MappingFileRef> getListIterable() {
			return this.subject.getSpecifiedMappingFileRefs();
		}

		@Override
		public int size_() {
			return this.subject.getSpecifiedMappingFileRefsSize();
		}
	}

	protected ListValueModel<MappingFileRef> buildImpliedMappingFileRefsModel() {
		return new PropertyListValueModelAdapter<MappingFileRef>(this.buildImpliedMappingFileRefModel());
	}

	protected PropertyValueModel<MappingFileRef> buildImpliedMappingFileRefModel() {
		return new ImpliedMappingFileRefModel(this.item);
	}

	public static class ImpliedMappingFileRefModel
		extends PropertyAspectAdapter<PersistenceUnit, MappingFileRef>
	{
		public ImpliedMappingFileRefModel(PersistenceUnit persistenceUnit) {
			super(PersistenceUnit.IMPLIED_MAPPING_FILE_REF_PROPERTY, persistenceUnit);
		}
		@Override
		protected MappingFileRef buildValue_() {
			return this.subject.getImpliedMappingFileRef();
		}
	}

	protected ListValueModel<ClassRef> buildSpecifiedClassRefsModel() {
		return new SpecifiedClassRefsModel(this.item);
	}

	public static class SpecifiedClassRefsModel
		extends ListAspectAdapter<PersistenceUnit, ClassRef>
	{
		public SpecifiedClassRefsModel(PersistenceUnit persistenceUnit) {
			super(PersistenceUnit.SPECIFIED_CLASS_REFS_LIST, persistenceUnit);
		}

		@Override
		protected ListIterable<ClassRef> getListIterable() {
			return this.subject.getSpecifiedClassRefs();
		}

		@Override
		public int size_() {
			return this.subject.getSpecifiedClassRefsSize();
		}
	}

	protected ListValueModel<ClassRef> buildImpliedClassRefsModel() {
		return new CollectionListValueModelAdapter<ClassRef>(this.buildImpliedClassRefsModel_());
	}

	protected CollectionValueModel<ClassRef> buildImpliedClassRefsModel_() {
		return new ImpliedClassRefsModel(this.item);
	}

	public static class ImpliedClassRefsModel
		extends CollectionAspectAdapter<PersistenceUnit, ClassRef>
	{
		public ImpliedClassRefsModel(PersistenceUnit persistenceUnit) {
			super(PersistenceUnit.IMPLIED_CLASS_REFS_COLLECTION, persistenceUnit);
		}

		@Override
		protected Iterable<ClassRef> getIterable() {
			return this.subject.getImpliedClassRefs();
		}

		@Override
		public int size_() {
			return this.subject.getImpliedClassRefsSize();
		}
	}

	protected ListValueModel<JarFileRef> buildJarFileRefsModel() {
		return new JarFileRefsModel(this.item);
	}

	public static class JarFileRefsModel
		extends ListAspectAdapter<PersistenceUnit, JarFileRef>
	{
		public JarFileRefsModel(PersistenceUnit persistenceUnit) {
			super(PersistenceUnit.JAR_FILE_REFS_LIST, persistenceUnit);
		}

		@Override
		protected ListIterable<JarFileRef> getListIterable() {
			return this.subject.getJarFileRefs();
		}

		@Override
		public int size_() {
			return this.subject.getJarFileRefsSize();
		}
	}
}
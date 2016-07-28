/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.structure;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.JptCommonUiImages;
import org.eclipse.jpt.common.ui.internal.jface.ModelItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.internal.jface.NullItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.internal.jface.StaticItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.model.value.ListValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;

/**
 * This factory builds item label providers for a <code>persistence.xml</code> file
 * JPA Structure View.
 */
public class PersistenceStructureItemLabelProviderFactory
	implements ItemExtendedLabelProvider.Factory
{
	// singleton
	private static final ItemExtendedLabelProvider.Factory INSTANCE = new PersistenceStructureItemLabelProviderFactory();

	/**
	 * Return the singleton
	 */
	public static ItemExtendedLabelProvider.Factory instance() {
		return INSTANCE;
	}


	protected PersistenceStructureItemLabelProviderFactory() {
		super();
	}

	public ItemExtendedLabelProvider buildProvider(Object item, ItemExtendedLabelProvider.Manager manager) {
		if (item instanceof Persistence) {
			return this.buildPersistenceProvider((Persistence) item, manager);
		}
		if (item instanceof PersistenceUnit) {
			return buildPersistenceUnitProvider((PersistenceUnit) item, manager);
		}
		if (item instanceof MappingFileRef) {
			return this.buildMappingFileRefProvider((MappingFileRef) item, manager);
		}
		if (item instanceof ClassRef) {
			return this.buildClassRefProvider((ClassRef) item, manager);
		}
		if (item instanceof JarFileRef) {
			return this.buildJarFileRefProvider((JarFileRef) item, manager);
		}
		return NullItemExtendedLabelProvider.instance();
	}


	// ********** persistence **********

	protected ItemExtendedLabelProvider buildPersistenceProvider(Persistence item, ItemExtendedLabelProvider.Manager manager) {
		return new StaticItemExtendedLabelProvider(
					JptJpaUiImages.PERSISTENCE,
					JptJpaUiMessages.PERSISTENCE_ITEM_LABEL_PROVIDER_FACTORY_PERSISTENCE_LABEL,
					this.buildPersistenceDescription(item),
					manager
				);
	}

	protected String buildPersistenceDescription(Persistence item) {
		StringBuilder sb = new StringBuilder();
		sb.append(JptJpaUiMessages.PERSISTENCE_ITEM_LABEL_PROVIDER_FACTORY_PERSISTENCE_LABEL);
		sb.append(" - ");  //$NON-NLS-1$
		sb.append(item.getResource().getFullPath().makeRelative());
		return sb.toString();
	}


	// ********** persistence unit **********

	/**
	 * shared method
	 */
	public static ItemExtendedLabelProvider buildPersistenceUnitProvider(PersistenceUnit persistenceUnit, ItemExtendedLabelProvider.Manager manager) {
		return new ModelItemExtendedLabelProvider(
				persistenceUnit,
				manager,
				buildPersistenceUnitImageDescriptorModel(persistenceUnit),
				buildPersistenceUnitTextModel(persistenceUnit),
				buildPersistenceUnitDescriptionModel(persistenceUnit)
			);
	}

	protected static PropertyValueModel<ImageDescriptor> buildPersistenceUnitImageDescriptorModel(@SuppressWarnings("unused") PersistenceUnit persistenceUnit) {
		return PropertyValueModelTools.staticModel(JptJpaUiImages.PERSISTENCE_UNIT);
	}

	protected static PropertyValueModel<String> buildPersistenceUnitTextModel(PersistenceUnit persistenceUnit) {
		return new PersistenceUnitTextModel(persistenceUnit);
	}

	public static class PersistenceUnitTextModel
		extends PropertyAspectAdapterXXXX<PersistenceUnit, String>
	{
		public PersistenceUnitTextModel(PersistenceUnit subject) {
			super(PersistenceUnit.NAME_PROPERTY, subject);
		}
		@Override
		protected String buildValue_() {
			return this.subject.getName();
		}
	}

	protected static PropertyValueModel<String> buildPersistenceUnitDescriptionModel(PersistenceUnit persistenceUnit) {
		return new PersistenceUnitDescriptionModel(persistenceUnit);
	}

	public static class PersistenceUnitDescriptionModel
		extends PersistenceUnitTextModel
	{
		public PersistenceUnitDescriptionModel(PersistenceUnit subject) {
			super(subject);
		}
		@Override
		protected String buildValue_() {
			StringBuilder sb = new StringBuilder();
			sb.append(super.buildValue_());
			sb.append(" - ");  //$NON-NLS-1$
			sb.append(this.subject.getResource().getFullPath().makeRelative());
			return sb.toString();
		}
	}


	// ********** mapping file ref **********

	protected ItemExtendedLabelProvider buildMappingFileRefProvider(MappingFileRef mappingFileRef, ItemExtendedLabelProvider.Manager manager) {
		return new ModelItemExtendedLabelProvider(
				mappingFileRef,
				manager,
				this.buildMappingFileRefImageDescriptorModel(mappingFileRef),
				this.buildMappingFileRefTextModel(mappingFileRef),
				this.buildMappingFileRefDescriptionModel(mappingFileRef)
			);
	}


	protected PropertyValueModel<ImageDescriptor> buildMappingFileRefImageDescriptorModel(MappingFileRef mappingFileRef) {
		return PropertyValueModelTools.staticModel(JptCommonUiImages.gray(JptJpaUiImages.MAPPING_FILE_REF, mappingFileRef.isDefault()));
	}

	protected PropertyValueModel<String> buildMappingFileRefTextModel(MappingFileRef mappingFileRef) {
		return new MappingFileRefTextModel(mappingFileRef);
	}

	public static class MappingFileRefTextModel
		extends PropertyAspectAdapterXXXX<MappingFileRef, String>
	{
		public MappingFileRefTextModel(MappingFileRef subject) {
			super(MappingFileRef.FILE_NAME_PROPERTY, subject);
		}
		@Override
		protected String buildValue_() {
			return this.subject.getFileName();
		}
	}

	protected PropertyValueModel<String> buildMappingFileRefDescriptionModel(MappingFileRef mappingFileRef) {
		return buildQuotedComponentDescriptionModel(
					mappingFileRef,
					this.buildMappingFileRefTextModel(mappingFileRef)
				);
	}

	// ********** class ref **********

	protected ItemExtendedLabelProvider buildClassRefProvider(ClassRef classRef, ItemExtendedLabelProvider.Manager manager) {
		return new ModelItemExtendedLabelProvider(
				classRef,
				manager,
				this.buildClassRefImageDescriptorModel(classRef),
				this.buildClassRefTextModel(classRef),
				this.buildClassRefDescriptionModel(classRef)
			);
	}

	protected PropertyValueModel<ImageDescriptor> buildClassRefImageDescriptorModel(@SuppressWarnings("unused") ClassRef classRef) {
		return PropertyValueModelTools.staticModel(JptJpaUiImages.CLASS_REF);
	}

	protected PropertyValueModel<String> buildClassRefTextModel(ClassRef classRef) {
		return new ClassRefTextModel(classRef);
	}

	public static class ClassRefTextModel
		extends PropertyAspectAdapterXXXX<ClassRef, String>
	{
		public ClassRefTextModel(ClassRef subject) {
			super(ClassRef.CLASS_NAME_PROPERTY, subject);
		}
		@Override
		protected String buildValue_() {
			return this.subject.getClassName();
		}
	}

	protected PropertyValueModel<String> buildClassRefDescriptionModel(ClassRef classRef) {
		return buildQuotedComponentDescriptionModel(
					classRef,
					this.buildClassRefTextModel(classRef)
				);
	}


	// ********** jar file ref **********

	protected ItemExtendedLabelProvider buildJarFileRefProvider(JarFileRef jarFileRef, ItemExtendedLabelProvider.Manager manager) {
		return new ModelItemExtendedLabelProvider(
				jarFileRef,
				manager,
				this.buildJarFileRefImageDescriptorModel(jarFileRef),
				this.buildJarFileRefTextModel(jarFileRef),
				this.buildJarFileRefDescriptionModel(jarFileRef)
			);
	}

	protected PropertyValueModel<ImageDescriptor> buildJarFileRefImageDescriptorModel(@SuppressWarnings("unused") JarFileRef jarFileRef) {
		return PropertyValueModelTools.staticModel(JptJpaUiImages.JAR_FILE_REF);
	}

	protected PropertyValueModel<String> buildJarFileRefTextModel(JarFileRef jarFileRef) {
		return new JarFileRefTextModel(jarFileRef);
	}

	public static class JarFileRefTextModel
		extends PropertyAspectAdapterXXXX<JarFileRef, String>
	{
		public JarFileRefTextModel(JarFileRef subject) {
			super(JarFileRef.FILE_NAME_PROPERTY, subject);
		}
		@Override
		protected String buildValue_() {
			return this.subject.getFileName();
		}
	}

	protected PropertyValueModel<String> buildJarFileRefDescriptionModel(JarFileRef jarFileRef) {
		return buildQuotedComponentDescriptionModel(
					jarFileRef,
					this.buildJarFileRefTextModel(jarFileRef)
				);
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}


	// ********** component description model **********

	public static PropertyValueModel<String> buildQuotedComponentDescriptionModel(JpaContextModel node, PropertyValueModel<String> nodeTextModel) {
		return buildComponentDescriptionModel(node, true, nodeTextModel);
	}

	@SafeVarargs
	public static PropertyValueModel<String> buildNonQuotedComponentDescriptionModel(JpaContextModel node, PropertyValueModel<String>... nodeTextModels) {
		return buildComponentDescriptionModel(node, false, nodeTextModels);
	}

	@SafeVarargs
	protected static PropertyValueModel<String> buildComponentDescriptionModel(JpaContextModel node, boolean quote, PropertyValueModel<String>... nodeTextModels) {
		IResource nodeResource = node.getResource();
		String nodePath = (nodeResource == null) ? null : nodeResource.getFullPath().makeRelative().toString();
		TransformerAdapter<Collection<String>, String> transformer = new ComponentDescriptionTransformer(nodePath, quote);

		ArrayList<PropertyValueModel<String>> models = new ArrayList<>();
		models.add(buildPersistenceUnitTextModel(node.getPersistenceUnit()));
		CollectionTools.addAll(models, nodeTextModels);

		return ListValueModelTools.compositePropertyValueModel(models, transformer);
	}


	public static class ComponentDescriptionTransformer
		extends TransformerAdapter<Collection<String>, String>
	{
		protected final String path;
		protected final boolean quote;

		ComponentDescriptionTransformer(String path, boolean quote) {
			super();
			this.path = path;
			this.quote = quote;
		}

		@Override
		public String transform(Collection<String> models) {
			StringBuilder sb = new StringBuilder();
			sb.append(IterableTools.first(models)); // persistence unit
			sb.append('/');
			if (this.quote) {
				sb.append('\"');
			}
			sb.append(IterableTools.get(models, 1)); // nodes
			for (int i = 2; i < models.size(); i++) {
				sb.append('/');
				sb.append(IterableTools.get(models, i));
			}
			if (this.quote) {
				sb.append('\"');
			}
			if (this.path != null) {
				sb.append(" - "); //$NON-NLS-1$
				sb.append(this.path);
			}
			return sb.toString();
		}
	}
}

/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.generic;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.internal.jface.ModelItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.internal.jface.NullItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.internal.jface.StaticItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaModel;
import org.eclipse.jpt.jpa.core.context.JpaContextRoot;
import org.eclipse.jpt.jpa.core.context.ManagedType;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JarFile;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.jpa2_1.context.ConverterType2_1;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.structure.MappingStructureItemLabelProviderFactory;
import org.eclipse.jpt.jpa.ui.internal.structure.PersistenceStructureItemLabelProviderFactory;

/**
 * This factory builds item label providers for the JPA content in the
 * Common Navigator.
 */
public class GenericNavigatorItemLabelProviderFactory
	implements ItemExtendedLabelProvider.Factory
{
	// singleton
	private static final ItemExtendedLabelProvider.Factory INSTANCE = new GenericNavigatorItemLabelProviderFactory();

	/**
	 * Return the singleton
	 */
	public static ItemExtendedLabelProvider.Factory instance() {
		return INSTANCE;
	}


	private GenericNavigatorItemLabelProviderFactory() {
		super();
	}

	public ItemExtendedLabelProvider buildProvider(Object item, ItemExtendedLabelProvider.Manager manager) {
		if (item instanceof JpaContextRoot) {
			return this.buildJpaContextRootProvider((JpaContextRoot) item, manager);
		}
		if (item instanceof PersistenceXml) {
			return this.buildPersistenceXmlProvider((PersistenceXml) item, manager);
		}
		if (item instanceof PersistenceUnit) {
			return this.buildPersistenceUnitProvider((PersistenceUnit) item, manager);
		}
		if (item instanceof OrmXml) {
			return this.buildOrmXmlProvider((OrmXml) item, manager);
		}
		if (item instanceof PersistentType) {
			return this.buildPersistentTypeProvider((PersistentType) item, manager);
		}
		if (item instanceof ConverterType2_1) {
			return this.buildConverterTypeProvider((ManagedType) item, manager);
		}
		if (item instanceof PersistentAttribute) {
			return this.buildPersistentAttributeProvider((PersistentAttribute) item, manager);
		}
		if (item instanceof JarFile) {
			return this.buildJarFileProvider((JarFile) item, manager);
		}
		return NullItemExtendedLabelProvider.instance();
	}

	protected ItemExtendedLabelProvider buildJpaContextRootProvider(JpaContextRoot item, ItemExtendedLabelProvider.Manager manager) {
		return new StaticItemExtendedLabelProvider(
				JptJpaUiImages.JPA_CONTENT,
				JptJpaUiMessages.JpaContent_label,
				JptJpaUiMessages.JpaContent_label + " - " + item.getResource().getFullPath().makeRelative(), //$NON-NLS-1$
				manager
			);
	}

	protected ItemExtendedLabelProvider buildPersistenceXmlProvider(PersistenceXml item, ItemExtendedLabelProvider.Manager manager) {
		return this.buildResourceItemLabelProvider(item, manager);
	}

	protected ItemExtendedLabelProvider buildPersistenceUnitProvider(PersistenceUnit item, ItemExtendedLabelProvider.Manager manager) {
		return PersistenceStructureItemLabelProviderFactory.buildPersistenceUnitProvider(item, manager);
	}

	protected ItemExtendedLabelProvider buildOrmXmlProvider(OrmXml item, ItemExtendedLabelProvider.Manager manager) {
		return this.buildResourceItemLabelProvider(item, manager);
	}

	protected ItemExtendedLabelProvider buildPersistentTypeProvider(PersistentType item, ItemExtendedLabelProvider.Manager manager) {
		return MappingStructureItemLabelProviderFactory.buildPersistentTypeProvider(item, manager);
	}

	protected ItemExtendedLabelProvider buildConverterTypeProvider(ManagedType item, ItemExtendedLabelProvider.Manager manager) {
		return new ModelItemExtendedLabelProvider(
				item,
				manager,
				this.buildConverterTypeImageDescriptorModel(item),
				this.buildConverterTypeTextModel(item),
				this.buildConverterTypeDescriptionModel(item)
			);
	}

	protected PropertyValueModel<ImageDescriptor> buildConverterTypeImageDescriptorModel(@SuppressWarnings("unused") ManagedType item) {
		return new StaticPropertyValueModel<ImageDescriptor>(JptJpaUiImages.CONVERTER);
	}

	protected PropertyValueModel<String> buildConverterTypeTextModel(ManagedType managedType) {
		return new ConverterTypeTextModel(managedType);
	}

	public static class ConverterTypeTextModel
		extends PropertyAspectAdapter<ManagedType, String>
	{
		public ConverterTypeTextModel(ManagedType subject) {
			super(ManagedType.NAME_PROPERTY, subject);
		}
		@Override
		protected String buildValue_() {
			return this.subject.getSimpleName();
		}
	}

	@SuppressWarnings("unchecked")
	protected PropertyValueModel<String> buildConverterTypeDescriptionModel(ManagedType managedType) {
		return PersistenceStructureItemLabelProviderFactory.buildNonQuotedComponentDescriptionModel(
					managedType,
					this.buildConverterTypeTextModel(managedType)
				);
	}

	protected ItemExtendedLabelProvider buildPersistentAttributeProvider(PersistentAttribute item, ItemExtendedLabelProvider.Manager manager) {
		return MappingStructureItemLabelProviderFactory.buildPersistentAttributeProvider(item, manager);
	}

	protected ItemExtendedLabelProvider buildJarFileProvider(JarFile item, ItemExtendedLabelProvider.Manager manager) {
		return this.buildResourceItemLabelProvider(item, JptJpaUiImages.JAR_FILE, manager);
	}

	protected ItemExtendedLabelProvider buildResourceItemLabelProvider(JpaModel node, ItemExtendedLabelProvider.Manager manager) {
		return this.buildResourceItemLabelProvider(node.getResource(), manager);
	}

	protected ItemExtendedLabelProvider buildResourceItemLabelProvider(JpaModel node, ImageDescriptor imageDescriptor, ItemExtendedLabelProvider.Manager manager) {
		return this.buildResourceItemLabelProvider(node.getResource(), imageDescriptor, manager);
	}

	protected ItemExtendedLabelProvider buildResourceItemLabelProvider(IResource resource, ItemExtendedLabelProvider.Manager manager) {
		return this.buildResourceItemLabelProvider(resource, JptJpaUiImages.JPA_FILE, manager);
	}

	protected ItemExtendedLabelProvider buildResourceItemLabelProvider(IResource resource, ImageDescriptor imageDescriptor, ItemExtendedLabelProvider.Manager manager) {
		return new StaticItemExtendedLabelProvider(
					imageDescriptor,
					resource.getName(),
					this.buildResourceDescription(resource),
					manager
				);
	}

	protected String buildResourceDescription(IResource resource) {
		StringBuilder sb = new StringBuilder();
		sb.append(resource.getName());
		sb.append(" - ");  //$NON-NLS-1$
		sb.append(resource.getParent().getFullPath().makeRelative());
		return sb.toString();
	}
}

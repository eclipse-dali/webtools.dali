/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal;

import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.IEmbeddable;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedIdMapping;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.IManyToManyMapping;
import org.eclipse.jpt.core.internal.context.base.IManyToOneMapping;
import org.eclipse.jpt.core.internal.context.base.IMappedSuperclass;
import org.eclipse.jpt.core.internal.context.base.IOneToManyMapping;
import org.eclipse.jpt.core.internal.context.base.IOneToOneMapping;
import org.eclipse.jpt.core.internal.context.base.ITransientMapping;
import org.eclipse.jpt.core.internal.context.base.IVersionMapping;
import org.eclipse.jpt.ui.internal.details.IJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.details.BasicMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EmbeddableComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EmbeddedIdMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EmbeddedMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EntityComposite;
import org.eclipse.jpt.ui.internal.mappings.details.IdMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.ManyToManyMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.ManyToOneMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.MappedSuperclassComposite;
import org.eclipse.jpt.ui.internal.mappings.details.OneToManyMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.OneToOneMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.TransientMappingComposite;
import org.eclipse.jpt.ui.internal.mappings.details.VersionMappingComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * The default implementation of the UI factory required to show the information
 * related to a JPA mapping (type or attribute).
 *
 * @see IJpaUiFactory
 *
 * @version 2.0
 * @since 1.0
 */
public abstract class BaseJpaUiFactory implements IJpaUiFactory
{
	/*
	 * (non-Javadoc)
	 */
	public IJpaComposite<IBasicMapping> createBasicMappingComposite(
		PropertyValueModel<IBasicMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new BasicMappingComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public IJpaComposite<IEmbeddable> createEmbeddableComposite(
		PropertyValueModel<IEmbeddable> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new EmbeddableComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public IJpaComposite<IEmbeddedIdMapping> createEmbeddedIdMappingComposite(
		PropertyValueModel<IEmbeddedIdMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new EmbeddedIdMappingComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public IJpaComposite<IEmbeddedMapping> createEmbeddedMappingComposite(
		PropertyValueModel<IEmbeddedMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new EmbeddedMappingComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public IJpaComposite<IEntity> createEntityComposite(
		PropertyValueModel<IEntity> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new EntityComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public IJpaComposite<IIdMapping> createIdMappingComposite(
		PropertyValueModel<IIdMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new IdMappingComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public IJpaComposite<IManyToManyMapping> createManyToManyMappingComposite(
		PropertyValueModel<IManyToManyMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new ManyToManyMappingComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public IJpaComposite<IManyToOneMapping> createManyToOneMappingComposite(
		PropertyValueModel<IManyToOneMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new ManyToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public IJpaComposite<IMappedSuperclass> createMappedSuperclassComposite(
		PropertyValueModel<IMappedSuperclass> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new MappedSuperclassComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public IJpaComposite<IOneToManyMapping> createOneToManyMappingComposite(
		PropertyValueModel<IOneToManyMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new OneToManyMappingComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public IJpaComposite<IOneToOneMapping> createOneToOneMappingComposite(
		PropertyValueModel<IOneToOneMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new OneToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public IJpaComposite<ITransientMapping> createTransientMappingComposite(
		PropertyValueModel<ITransientMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new TransientMappingComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public IJpaComposite<IVersionMapping> createVersionMappingComposite(
		PropertyValueModel<IVersionMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new VersionMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
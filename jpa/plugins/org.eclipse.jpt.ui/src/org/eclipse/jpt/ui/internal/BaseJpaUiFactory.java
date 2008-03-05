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

import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.MappedSuperclass;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
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
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * The default implementation of the UI factory required to show the information
 * related to a JPA mapping (type or attribute).
 *
 * @see JpaUiFactory
 *
 * @version 2.0
 * @since 1.0
 */
public abstract class BaseJpaUiFactory implements JpaUiFactory
{
	/*
	 * (non-Javadoc)
	 */
	public JpaComposite<BasicMapping> createBasicMappingComposite(
		PropertyValueModel<BasicMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new BasicMappingComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public JpaComposite<Embeddable> createEmbeddableComposite(
		PropertyValueModel<Embeddable> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new EmbeddableComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public JpaComposite<EmbeddedIdMapping> createEmbeddedIdMappingComposite(
		PropertyValueModel<EmbeddedIdMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new EmbeddedIdMappingComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public JpaComposite<EmbeddedMapping> createEmbeddedMappingComposite(
		PropertyValueModel<EmbeddedMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new EmbeddedMappingComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public JpaComposite<Entity> createEntityComposite(
		PropertyValueModel<Entity> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new EntityComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public JpaComposite<IdMapping> createIdMappingComposite(
		PropertyValueModel<IdMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new IdMappingComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public JpaComposite<ManyToManyMapping> createManyToManyMappingComposite(
		PropertyValueModel<ManyToManyMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new ManyToManyMappingComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public JpaComposite<ManyToOneMapping> createManyToOneMappingComposite(
		PropertyValueModel<ManyToOneMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new ManyToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public JpaComposite<MappedSuperclass> createMappedSuperclassComposite(
		PropertyValueModel<MappedSuperclass> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new MappedSuperclassComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public JpaComposite<OneToManyMapping> createOneToManyMappingComposite(
		PropertyValueModel<OneToManyMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new OneToManyMappingComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public JpaComposite<OneToOneMapping> createOneToOneMappingComposite(
		PropertyValueModel<OneToOneMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new OneToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public JpaComposite<TransientMapping> createTransientMappingComposite(
		PropertyValueModel<TransientMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new TransientMappingComposite(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	public JpaComposite<VersionMapping> createVersionMappingComposite(
		PropertyValueModel<VersionMapping> subjectHolder,
		Composite parent,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		return new VersionMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
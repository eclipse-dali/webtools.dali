/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.ui.internal.mappings.details.BasicComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EmbeddableComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EmbeddedComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EmbeddedIdComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EntityComposite;
import org.eclipse.jpt.ui.internal.mappings.details.IdComposite;
import org.eclipse.jpt.ui.internal.mappings.details.ManyToManyComposite;
import org.eclipse.jpt.ui.internal.mappings.details.ManyToOneComposite;
import org.eclipse.jpt.ui.internal.mappings.details.MappedSuperclassComposite;
import org.eclipse.jpt.ui.internal.mappings.details.OneToManyComposite;
import org.eclipse.jpt.ui.internal.mappings.details.OneToOneComposite;
import org.eclipse.jpt.ui.internal.mappings.details.TransientComposite;
import org.eclipse.jpt.ui.internal.mappings.details.VersionComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public abstract class BaseJpaUiFactory implements IJpaUiFactory
{
	public IJpaComposite<IBasicMapping> createBasicMappingComposite(PropertyValueModel<IBasicMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new BasicComposite(subjectHolder, parent, widgetFactory);
	}

	public IJpaComposite<IEmbeddable> createEmbeddableComposite(PropertyValueModel<IEmbeddable> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new EmbeddableComposite(subjectHolder, parent, widgetFactory);
	}

	public IJpaComposite<IEmbeddedIdMapping> createEmbeddedIdMappingComposite(PropertyValueModel<IEmbeddedIdMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new EmbeddedIdComposite(subjectHolder, parent, widgetFactory);
	}

	public IJpaComposite<IEmbeddedMapping> createEmbeddedMappingComposite(PropertyValueModel<IEmbeddedMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new EmbeddedComposite(subjectHolder, parent, widgetFactory);
	}

	public IJpaComposite<IEntity> createEntityComposite(PropertyValueModel<IEntity> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new EntityComposite(subjectHolder, parent, widgetFactory);
	}

	public IJpaComposite<IIdMapping> createIdMappingComposite(PropertyValueModel<IIdMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new IdComposite(subjectHolder, parent, widgetFactory);
	}

	public IJpaComposite<IManyToManyMapping> createManyToManyMappingComposite(PropertyValueModel<IManyToManyMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new ManyToManyComposite(subjectHolder, parent, widgetFactory);
	}

	public IJpaComposite<IManyToOneMapping> createManyToOneMappingComposite(PropertyValueModel<IManyToOneMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new ManyToOneComposite(subjectHolder, parent, widgetFactory);
	}

	public IJpaComposite<IMappedSuperclass> createMappedSuperclassComposite(PropertyValueModel<IMappedSuperclass> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new MappedSuperclassComposite(subjectHolder, parent, widgetFactory);
	}

	public IJpaComposite<IOneToManyMapping> createOneToManyMappingComposite(PropertyValueModel<IOneToManyMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new OneToManyComposite(subjectHolder, parent, widgetFactory);
	}

	public IJpaComposite<IOneToOneMapping> createOneToOneMappingComposite(PropertyValueModel<IOneToOneMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new OneToOneComposite(subjectHolder, parent, widgetFactory);
	}

	public IJpaComposite<ITransientMapping> createTransientMappingComposite(PropertyValueModel<ITransientMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new TransientComposite(subjectHolder, parent, widgetFactory);
	}

	public IJpaComposite<IVersionMapping> createVersionMappingComposite(PropertyValueModel<IVersionMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new VersionComposite(subjectHolder, parent, widgetFactory);
	}
}
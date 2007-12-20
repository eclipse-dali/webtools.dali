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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public abstract class BaseJpaUiFactory implements IJpaUiFactory
{
	public IJpaComposite<IBasicMapping> createBasicMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new BasicComposite(parent, widgetFactory);
	}

	public IJpaComposite<IEmbeddable> createEmbeddableComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new EmbeddableComposite(parent, widgetFactory);
	}

	public IJpaComposite<IEmbeddedIdMapping> createEmbeddedIdMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new EmbeddedIdComposite(parent, widgetFactory);
	}

	public IJpaComposite<IEmbeddedMapping> createEmbeddedMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new EmbeddedComposite(parent, widgetFactory);
	}

	public IJpaComposite<IEntity> createEntityComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new EntityComposite(parent, widgetFactory);
	}

	public IJpaComposite<IIdMapping> createIdMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new IdComposite(parent, widgetFactory);
	}

	public IJpaComposite<IManyToManyMapping> createManyToManyMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new ManyToManyComposite(parent, widgetFactory);
	}

	public IJpaComposite<IManyToOneMapping> createManyToOneMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new ManyToOneComposite(parent, widgetFactory);
	}

	public IJpaComposite<IMappedSuperclass> createMappedSuperclassComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new MappedSuperclassComposite(parent, widgetFactory);
	}

	public IJpaComposite<IOneToManyMapping> createOneToManyMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new OneToManyComposite(parent, widgetFactory);
	}

	public IJpaComposite<IOneToOneMapping> createOneToOneMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new OneToOneComposite(parent, widgetFactory);
	}

	public IJpaComposite<ITransientMapping> createTransientMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new TransientComposite(parent, widgetFactory);
	}

	public IJpaComposite<IVersionMapping> createVersionMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return new VersionComposite(parent, widgetFactory);
	}
}
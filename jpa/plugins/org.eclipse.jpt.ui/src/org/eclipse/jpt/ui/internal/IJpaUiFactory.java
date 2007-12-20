/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Use IJpaFactory to create any IJavaTypeMapping or IJavaAttributeMappings.  This is necessary
 * so that platforms can extend the java model with their own annotations.
 * IJavaTypeMappingProvider and IJavaAttributeMappingProvider use this factory.
 * See IJpaPlatform.javaTypeMappingProviders() and IJpaPlatform.javaAttributeMappingProviders()
 * for creating new mappings types.
 * @see BaseJpaUiFactory
 */
public interface IJpaUiFactory
{
	IJpaComposite<IBasicMapping> createBasicMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IEmbeddable> createEmbeddableComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IEmbeddedIdMapping> createEmbeddedIdMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IEmbeddedMapping> createEmbeddedMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IEntity> createEntityComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IIdMapping> createIdMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IManyToManyMapping> createManyToManyMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IManyToOneMapping> createManyToOneMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IMappedSuperclass> createMappedSuperclassComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IOneToManyMapping> createOneToManyMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IOneToOneMapping> createOneToOneMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<ITransientMapping> createTransientMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IVersionMapping> createVersionMappingComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);
}
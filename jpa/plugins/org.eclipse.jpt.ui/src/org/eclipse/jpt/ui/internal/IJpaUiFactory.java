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
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
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
	IJpaComposite<IBasicMapping> createBasicMappingComposite(PropertyValueModel<IBasicMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IEmbeddable> createEmbeddableComposite(PropertyValueModel<IEmbeddable> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IEmbeddedIdMapping> createEmbeddedIdMappingComposite(PropertyValueModel<IEmbeddedIdMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IEmbeddedMapping> createEmbeddedMappingComposite(PropertyValueModel<IEmbeddedMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IEntity> createEntityComposite(PropertyValueModel<IEntity> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IIdMapping> createIdMappingComposite(PropertyValueModel<IIdMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IManyToManyMapping> createManyToManyMappingComposite(PropertyValueModel<IManyToManyMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IManyToOneMapping> createManyToOneMappingComposite(PropertyValueModel<IManyToOneMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IMappedSuperclass> createMappedSuperclassComposite(PropertyValueModel<IMappedSuperclass> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IOneToManyMapping> createOneToManyMappingComposite(PropertyValueModel<IOneToManyMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IOneToOneMapping> createOneToOneMappingComposite(PropertyValueModel<IOneToOneMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<ITransientMapping> createTransientMappingComposite(PropertyValueModel<ITransientMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	IJpaComposite<IVersionMapping> createVersionMappingComposite(PropertyValueModel<IVersionMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);
}
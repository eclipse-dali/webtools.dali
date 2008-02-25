/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui;

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
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.platform.JpaFactory;
import org.eclipse.jpt.core.platform.JpaPlatform;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.BaseJpaUiFactory;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Use {@link JpaFactory} to create any {@link JavaTypeMapping} or {@link JavaAttributeMapping}s.  This is necessary
 * so that platforms can extend the java model with their own annotations.
 * {@link JavaTypeMappingProvider} and {@link JavaAttributeMappingProvider} use this factory.
 * See {@link JpaPlatform#javaTypeMappingProviders()} and {@link JpaPlatform#javaAttributeMappingProviders()
 * for creating new mappings types.
 * @see BaseJpaUiFactory
 */
public interface JpaUiFactory
{
	JpaComposite<BasicMapping> createBasicMappingComposite(PropertyValueModel<BasicMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	JpaComposite<Embeddable> createEmbeddableComposite(PropertyValueModel<Embeddable> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	JpaComposite<EmbeddedIdMapping> createEmbeddedIdMappingComposite(PropertyValueModel<EmbeddedIdMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	JpaComposite<EmbeddedMapping> createEmbeddedMappingComposite(PropertyValueModel<EmbeddedMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	JpaComposite<Entity> createEntityComposite(PropertyValueModel<Entity> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	JpaComposite<IdMapping> createIdMappingComposite(PropertyValueModel<IdMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	JpaComposite<ManyToManyMapping> createManyToManyMappingComposite(PropertyValueModel<ManyToManyMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	JpaComposite<ManyToOneMapping> createManyToOneMappingComposite(PropertyValueModel<ManyToOneMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	JpaComposite<MappedSuperclass> createMappedSuperclassComposite(PropertyValueModel<MappedSuperclass> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	JpaComposite<OneToManyMapping> createOneToManyMappingComposite(PropertyValueModel<OneToManyMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	JpaComposite<OneToOneMapping> createOneToOneMappingComposite(PropertyValueModel<OneToOneMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	JpaComposite<TransientMapping> createTransientMappingComposite(PropertyValueModel<TransientMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

	JpaComposite<VersionMapping> createVersionMappingComposite(PropertyValueModel<VersionMapping> subjectHolder, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);
}
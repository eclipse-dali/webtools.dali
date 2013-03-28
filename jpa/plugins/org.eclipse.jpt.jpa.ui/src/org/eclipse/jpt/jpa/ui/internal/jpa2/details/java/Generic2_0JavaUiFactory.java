/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details.java;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.EmbeddedIdMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.EmbeddedMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.IdMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ManyToManyMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ManyToOneMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToManyMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToOneMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaEntity2_0;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.internal.details.java.AbstractJavaUiFactory;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.ElementCollectionMappingComposite2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.EmbeddedIdMappingComposite2_0;
import org.eclipse.jpt.jpa.ui.jpa2.details.JpaUiFactory2_0;
import org.eclipse.swt.widgets.Composite;

public class Generic2_0JavaUiFactory
	extends AbstractJavaUiFactory
	implements JpaUiFactory2_0
{
	// ********** type mappings **********

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createMappedSuperclassComposite(
			PropertyValueModel<? extends MappedSuperclass> mappedSuperclassModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaMappedSuperclassComposite2_0((PropertyValueModel<? extends JavaMappedSuperclass>) mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createEntityComposite(
			PropertyValueModel<? extends Entity> entityModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEntity2_0Composite((PropertyValueModel<? extends JavaEntity2_0>) entityModel, parentComposite, widgetFactory, resourceManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createEmbeddableComposite(
			PropertyValueModel<? extends Embeddable> embeddableModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEmbeddable2_0Composite((PropertyValueModel<? extends JavaEmbeddable>) embeddableModel, parentComposite, widgetFactory, resourceManager);
	}


	// ********** attribute mappings **********

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createIdMappingComposite(
			PropertyValueModel<? extends IdMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaIdMapping2_0Composite((PropertyValueModel<? extends IdMapping2_0>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createEmbeddedIdMappingComposite(
			PropertyValueModel<? extends EmbeddedIdMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EmbeddedIdMappingComposite2_0((PropertyValueModel<? extends EmbeddedIdMapping2_0>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createEmbeddedMappingComposite(
			PropertyValueModel<? extends EmbeddedMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEmbeddedMapping2_0Composite((PropertyValueModel<? extends EmbeddedMapping2_0>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createManyToManyMappingComposite(
			PropertyValueModel<? extends ManyToManyMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaManyToManyMappingComposite2_0((PropertyValueModel<? extends ManyToManyMapping2_0>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createManyToOneMappingComposite(
			PropertyValueModel<? extends ManyToOneMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaManyToOneMappingComposite2_0((PropertyValueModel<? extends ManyToOneMapping2_0>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createOneToManyMappingComposite(
			PropertyValueModel<? extends OneToManyMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaOneToManyMappingComposite2_0((PropertyValueModel<? extends OneToManyMapping2_0>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createOneToOneMappingComposite(
			PropertyValueModel<? extends OneToOneMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaOneToOneMappingComposite2_0((PropertyValueModel<? extends OneToOneMapping2_0>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createElementCollectionMapping2_0Composite(
			PropertyValueModel<? extends ElementCollectionMapping2_0> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new ElementCollectionMappingComposite2_0(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}

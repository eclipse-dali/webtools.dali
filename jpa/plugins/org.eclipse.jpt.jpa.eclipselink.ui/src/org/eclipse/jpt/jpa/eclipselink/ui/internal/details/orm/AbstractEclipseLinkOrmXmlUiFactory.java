/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.internal.details.TransientMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.orm.AbstractOrmXmlUiFactory;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractEclipseLinkOrmXmlUiFactory
	extends AbstractOrmXmlUiFactory
{
	// ********** type mappings **********

	@Override
	public JpaComposite createOrmMappedSuperclassComposite(
			PropertyValueModel<OrmMappedSuperclass> mappedSuperclassModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkMappedSuperclassComposite(mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmEntityComposite(
			PropertyValueModel<OrmEntity> entityModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkEntityComposite(entityModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmEmbeddableComposite(
			PropertyValueModel<OrmEmbeddable> embeddableModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkEmbeddableComposite(embeddableModel, parentComposite, widgetFactory, resourceManager);
	}


	// ********** attribute mappings **********

	@Override
	public JpaComposite createOrmIdMappingComposite(
			PropertyValueModel<OrmIdMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkIdMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmBasicMappingComposite(
			PropertyValueModel<OrmBasicMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkBasicMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmVersionMappingComposite(
			PropertyValueModel<OrmVersionMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkVersionMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmManyToOneMappingComposite(
			PropertyValueModel<OrmManyToOneMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkManyToOneMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmOneToManyMappingComposite(
			PropertyValueModel<OrmOneToManyMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkOneToManyMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmOneToOneMappingComposite(
			PropertyValueModel<OrmOneToOneMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkOneToOneMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmManyToManyMappingComposite(
			PropertyValueModel<OrmManyToManyMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkManyToManyMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmTransientMappingComposite(
			PropertyValueModel<OrmTransientMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new TransientMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}

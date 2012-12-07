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
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkOrmXml1_1UiFactory
	extends AbstractEclipseLinkOrmXmlUiFactory
{
	// ********** attribute mappings **********

	@Override
	public JpaComposite createOrmIdMappingComposite(
			PropertyValueModel<OrmIdMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkIdMapping1_1Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmEmbeddedIdMappingComposite(
			PropertyValueModel<OrmEmbeddedIdMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkEmbeddedIdMapping1_1Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmBasicMappingComposite(
			PropertyValueModel<OrmBasicMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkBasicMapping1_1Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmVersionMappingComposite(
			PropertyValueModel<OrmVersionMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkVersionMapping1_1Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmManyToOneMappingComposite(
			PropertyValueModel<OrmManyToOneMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkManyToOneMapping1_1Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmOneToManyMappingComposite(
			PropertyValueModel<OrmOneToManyMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkOneToManyMapping1_1Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmOneToOneMappingComposite(
			PropertyValueModel<OrmOneToOneMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkOneToOneMapping1_1Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmManyToManyMappingComposite(
			PropertyValueModel<OrmManyToManyMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkManyToManyMapping1_1Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmEmbeddedMappingComposite(
			PropertyValueModel<OrmEmbeddedMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkEmbeddedMapping1_1Composite(mappingModel, enabledModel,parentComposite, widgetFactory, resourceManager);
	}
}

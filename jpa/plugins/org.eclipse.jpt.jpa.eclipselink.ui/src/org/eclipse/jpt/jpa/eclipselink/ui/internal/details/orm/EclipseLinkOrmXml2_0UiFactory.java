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
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.jpa2.details.orm.OrmXmlUiFactory2_0;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkOrmXml2_0UiFactory
	extends AbstractEclipseLinkOrmXmlUiFactory
	implements OrmXmlUiFactory2_0
{
	// ********** type mappings **********

	@Override
	public JpaComposite createOrmMappedSuperclassComposite(
			PropertyValueModel<OrmMappedSuperclass> mappedSuperclassModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkMappedSuperclass2_0Composite(mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmEntityComposite(
			PropertyValueModel<OrmEntity> entityModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkEntity2_0Composite(entityModel, parentComposite, widgetFactory, resourceManager);
	}


	// ********** attribute mappings **********

	@Override
	public JpaComposite createOrmIdMappingComposite(
			PropertyValueModel<OrmIdMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkIdMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmEmbeddedMappingComposite(
			PropertyValueModel<OrmEmbeddedMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkEmbeddedMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmEmbeddedIdMappingComposite(
			PropertyValueModel<OrmEmbeddedIdMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkEmbeddedIdMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
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
		return new OrmEclipseLinkManyToOneMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmOneToManyMappingComposite(
			PropertyValueModel<OrmOneToManyMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkOneToManyMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmOneToOneMappingComposite(
			PropertyValueModel<OrmOneToOneMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkOneToOneMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmManyToManyMappingComposite(
			PropertyValueModel<OrmManyToManyMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkManyToManyMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createOrmElementCollectionMapping2_0Composite(
			PropertyValueModel<OrmElementCollectionMapping2_0> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkElementCollectionMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}

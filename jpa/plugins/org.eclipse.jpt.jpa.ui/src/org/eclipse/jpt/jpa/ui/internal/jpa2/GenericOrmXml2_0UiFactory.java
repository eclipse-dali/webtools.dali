/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2;

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
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.internal.details.TransientMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.orm.AbstractOrmXmlUiFactory;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.OrmBasicMapping2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.OrmElementCollectionMapping2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.OrmEmbeddedIdMapping2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.OrmEmbeddedMapping2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.OrmEntity2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.OrmIdMapping2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.OrmManyToManyMapping2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.OrmManyToOneMapping2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.OrmOneToManyMapping2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.OrmOneToOneMapping2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm.OrmVersionMapping2_0Composite;
import org.eclipse.jpt.jpa.ui.jpa2.details.orm.OrmXmlUiFactory2_0;
import org.eclipse.swt.widgets.Composite;

public class GenericOrmXml2_0UiFactory
	extends AbstractOrmXmlUiFactory
	implements OrmXmlUiFactory2_0
{
	// ********** type mappings **********

	@Override
	public JpaComposite createOrmEntityComposite(
			PropertyValueModel<OrmEntity> entityModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEntity2_0Composite(entityModel, parentComposite, widgetFactory, resourceManager);
	}


	// **************** attribute mappings ***********************

	@Override
	public JpaComposite createOrmIdMappingComposite(
			PropertyValueModel<OrmIdMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmIdMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmEmbeddedIdMappingComposite(
			PropertyValueModel<OrmEmbeddedIdMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEmbeddedIdMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmBasicMappingComposite(
			PropertyValueModel<OrmBasicMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmBasicMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmVersionMappingComposite(
			PropertyValueModel<OrmVersionMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmVersionMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmManyToOneMappingComposite(
			PropertyValueModel<OrmManyToOneMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmManyToOneMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmOneToManyMappingComposite(
			PropertyValueModel<OrmOneToManyMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmOneToManyMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmOneToOneMappingComposite(
			PropertyValueModel<OrmOneToOneMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmOneToOneMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmManyToManyMappingComposite(
			PropertyValueModel<OrmManyToManyMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmManyToManyMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createOrmEmbeddedMappingComposite(
			PropertyValueModel<OrmEmbeddedMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEmbeddedMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
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

	public JpaComposite createOrmElementCollectionMapping2_0Composite(
			PropertyValueModel<OrmElementCollectionMapping2_0> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmElementCollectionMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}

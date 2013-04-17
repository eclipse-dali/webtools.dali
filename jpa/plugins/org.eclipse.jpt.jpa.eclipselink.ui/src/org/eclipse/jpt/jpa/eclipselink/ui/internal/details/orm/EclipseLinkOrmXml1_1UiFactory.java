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
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkBasicMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkIdMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkManyToManyMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkManyToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkOneToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkVersionMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkOrmXml1_1UiFactory
	extends AbstractEclipseLinkOrmXmlUiFactory
{
	// ********** attribute mappings **********

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createIdMappingComposite(
			PropertyValueModel<? extends IdMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkIdMapping1_1Composite((PropertyValueModel<? extends EclipseLinkIdMapping>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createEmbeddedIdMappingComposite(
			PropertyValueModel<? extends EmbeddedIdMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkEmbeddedIdMapping1_1Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createBasicMappingComposite(
			PropertyValueModel<? extends BasicMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkBasicMapping1_1Composite((PropertyValueModel<? extends EclipseLinkBasicMapping>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createVersionMappingComposite(
			PropertyValueModel<? extends VersionMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkOrmVersionMappingComposite1_1((PropertyValueModel<? extends EclipseLinkVersionMapping>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createManyToOneMappingComposite(
			PropertyValueModel<? extends ManyToOneMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkManyToOneMapping1_1Composite((PropertyValueModel<? extends EclipseLinkManyToOneMapping>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createOneToManyMappingComposite(
			PropertyValueModel<? extends OneToManyMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkOneToManyMapping1_1Composite((PropertyValueModel<? extends EclipseLinkOneToManyMapping>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createOneToOneMappingComposite(
			PropertyValueModel<? extends OneToOneMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkOrmOneToOneMappingComposite1_1((PropertyValueModel<? extends EclipseLinkOneToOneMapping>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createManyToManyMappingComposite(
			PropertyValueModel<? extends ManyToManyMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkManyToManyMapping1_1Composite((PropertyValueModel<? extends EclipseLinkManyToManyMapping>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createEmbeddedMappingComposite(
			PropertyValueModel<? extends EmbeddedMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEclipseLinkEmbeddedMapping1_1Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}

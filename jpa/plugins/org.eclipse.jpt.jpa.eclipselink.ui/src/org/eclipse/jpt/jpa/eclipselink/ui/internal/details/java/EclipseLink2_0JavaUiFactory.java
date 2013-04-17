/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.EmbeddedIdMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkIdMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkManyToManyMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkManyToOneMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkOneToManyMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkOneToOneMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.EmbeddedIdMappingComposite2_0;
import org.eclipse.jpt.jpa.ui.jpa2.details.JpaUiFactory2_0;
import org.eclipse.swt.widgets.Composite;

public class EclipseLink2_0JavaUiFactory
	extends EclipseLink1_2JavaUiFactory
	implements JpaUiFactory2_0
{
	// ********** type mappings **********
	
	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createEntityComposite(
			PropertyValueModel<? extends Entity> entityModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkJavaEntityComposite2_0((PropertyValueModel<? extends EclipseLinkJavaEntity>) entityModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createMappedSuperclassComposite(
			PropertyValueModel<? extends MappedSuperclass> mappedSuperclassModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkJavaMappedSuperclassComposite2_0((PropertyValueModel<? extends EclipseLinkJavaMappedSuperclass>) mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
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
		return new EclipseLinkJavaIdMappingComposite2_0((PropertyValueModel<? extends EclipseLinkIdMapping2_0>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
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
	public JpaComposite createElementCollectionMapping2_0Composite(
			PropertyValueModel<? extends ElementCollectionMapping2_0> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkJavaElementCollectionMappingComposite2_0((PropertyValueModel<? extends EclipseLinkElementCollectionMapping2_0>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createOneToManyMappingComposite(
			PropertyValueModel<? extends OneToManyMapping> mappingModel, 
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite, 
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkJavaOneToManyMappingComposite2_0((PropertyValueModel<? extends EclipseLinkOneToManyMapping2_0>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createOneToOneMappingComposite(
			PropertyValueModel<? extends OneToOneMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkJavaOneToOneMappingComposite2_0((PropertyValueModel<? extends EclipseLinkOneToOneMapping2_0>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createManyToOneMappingComposite(
			PropertyValueModel<? extends ManyToOneMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEclipseLinkManyToOneMapping2_0Composite((PropertyValueModel<? extends EclipseLinkManyToOneMapping2_0>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createManyToManyMappingComposite(
			PropertyValueModel<? extends ManyToManyMapping> mappingModel, 
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite, 
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkJavaManyToManyMappingComposite2_0((PropertyValueModel<? extends EclipseLinkManyToManyMapping2_0>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}

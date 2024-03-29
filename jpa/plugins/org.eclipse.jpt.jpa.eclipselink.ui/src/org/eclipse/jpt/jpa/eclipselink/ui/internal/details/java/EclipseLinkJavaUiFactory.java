/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
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
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaEmbeddable;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.internal.details.java.AbstractJavaUiFactory;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkJavaUiFactory
	extends AbstractJavaUiFactory
{
	// ********** type mappings **********

	@Override
	@SuppressWarnings("unchecked")
	public JpaComposite createMappedSuperclassComposite(
			PropertyValueModel<? extends MappedSuperclass> mappedSuperclassModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkJavaMappedSuperclassComposite((PropertyValueModel<? extends EclipseLinkJavaMappedSuperclass>) mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createEntityComposite(
			PropertyValueModel<? extends Entity> entityModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkJavaEntityComposite((PropertyValueModel<? extends EclipseLinkJavaEntity>) entityModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createEmbeddableComposite(
			PropertyValueModel<? extends Embeddable> embeddableModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkJavaEmbeddableComposite((PropertyValueModel<? extends EclipseLinkJavaEmbeddable>) embeddableModel, parentComposite, widgetFactory, resourceManager);
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
		return new EclipseLinkJavaIdMappingComposite((PropertyValueModel<? extends EclipseLinkIdMapping>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createBasicMappingComposite(
			PropertyValueModel<? extends BasicMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkJavaBasicMappingComposite((PropertyValueModel<? extends EclipseLinkBasicMapping>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createVersionMappingComposite(
			PropertyValueModel<? extends VersionMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkJavaVersionMappingComposite((PropertyValueModel<? extends EclipseLinkVersionMapping>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createManyToOneMappingComposite(
			PropertyValueModel<? extends ManyToOneMapping> mappingModel, 
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite, 
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkJavaManyToOneMappingComposite((PropertyValueModel<? extends EclipseLinkManyToOneMapping>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createOneToManyMappingComposite(
			PropertyValueModel<? extends OneToManyMapping> mappingModel, 
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite, 
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkJavaOneToManyMappingComposite((PropertyValueModel<? extends EclipseLinkOneToManyMapping>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createOneToOneMappingComposite(
			PropertyValueModel<? extends OneToOneMapping> mappingModel, 
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite, 
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkJavaOneToOneMappingComposite((PropertyValueModel<? extends EclipseLinkOneToOneMapping>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createManyToManyMappingComposite(
			PropertyValueModel<? extends ManyToManyMapping> mappingModel, 
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite, 
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkJavaManyToManyMappingComposite((PropertyValueModel<? extends EclipseLinkManyToManyMapping>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}

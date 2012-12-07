/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkBasicMapMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTransformationMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkBasicCollectionMappingComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkBasicMapMappingComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkManyToManyMappingComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkManyToOneMappingComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkOneToManyMappingComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkOneToOneMappingComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkTransformationMappingComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkVariableOneToOneMappingComposite;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.internal.details.java.AbstractJavaUiFactory;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkJavaUiFactory
	extends AbstractJavaUiFactory
{
	// ********** type mappings **********
	
	@Override
	public JpaComposite createJavaMappedSuperclassComposite(
			PropertyValueModel<JavaMappedSuperclass> mappedSuperclassModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEclipseLinkMappedSuperclassComposite(mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@Override
	public JpaComposite createJavaEntityComposite(
			PropertyValueModel<JavaEntity> entityModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEclipseLinkEntityComposite(entityModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@Override
	public JpaComposite createJavaEmbeddableComposite(
			PropertyValueModel<JavaEmbeddable> embeddableModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEclipseLinkEmbeddableComposite(embeddableModel, parentComposite, widgetFactory, resourceManager);
	}
	
	
	// ********** attribute mappings **********
	
	@Override
	public JpaComposite createJavaIdMappingComposite(
			PropertyValueModel<JavaIdMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEclipseLinkIdMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@Override
	public JpaComposite createJavaBasicMappingComposite(
			PropertyValueModel<JavaBasicMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEclipseLinkBasicMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@Override
	public JpaComposite createJavaVersionMappingComposite(
			PropertyValueModel<JavaVersionMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEclipseLinkVersionMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@Override
	public JpaComposite createJavaManyToOneMappingComposite(
			PropertyValueModel<JavaManyToOneMapping> mappingModel, 
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite, 
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkManyToOneMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@Override
	public JpaComposite createJavaOneToManyMappingComposite(
			PropertyValueModel<JavaOneToManyMapping> mappingModel, 
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite, 
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkOneToManyMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@Override
	public JpaComposite createJavaOneToOneMappingComposite(
			PropertyValueModel<JavaOneToOneMapping> mappingModel, 
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite, 
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkOneToOneMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@Override
	public JpaComposite createJavaManyToManyMappingComposite(
			PropertyValueModel<JavaManyToManyMapping> mappingModel, 
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite, 
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkManyToManyMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	public JpaComposite createJavaEclipseLinkBasicMapMappingComposite(
			PropertyValueModel<EclipseLinkBasicMapMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkBasicMapMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	public JpaComposite createJavaEclipseLinkBasicCollectionMappingComposite(
			PropertyValueModel<EclipseLinkBasicCollectionMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkBasicCollectionMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	public JpaComposite createJavaEclipseLinkVariableOneToOneMappingComposite(
			PropertyValueModel<EclipseLinkVariableOneToOneMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkVariableOneToOneMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	public JpaComposite createJavaEclipseLinkTransformationMappingComposite(
			PropertyValueModel<EclipseLinkTransformationMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkTransformationMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}

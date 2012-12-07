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
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.EmbeddedIdMapping2_0Composite;
import org.eclipse.jpt.jpa.ui.jpa2.details.java.JavaUiFactory2_0;
import org.eclipse.swt.widgets.Composite;

public class EclipseLink2_0JavaUiFactory
	extends EclipseLink1_2JavaUiFactory
	implements JavaUiFactory2_0
{
	// ********** type mappings **********
	
	@Override
	public JpaComposite createJavaEntityComposite(
			PropertyValueModel<JavaEntity> entityModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEclipseLinkEntity2_0Composite(entityModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@Override
	public JpaComposite createJavaMappedSuperclassComposite(
			PropertyValueModel<JavaMappedSuperclass> mappedSuperclassModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEclipseLinkMappedSuperclass2_0Composite(mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
	}
	
	
	// ********** attribute mappings **********
	
	@Override
	public JpaComposite createJavaIdMappingComposite(
			PropertyValueModel<JavaIdMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEclipseLinkIdMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@Override
	public JpaComposite createJavaEmbeddedIdMappingComposite(
			PropertyValueModel<JavaEmbeddedIdMapping> mappingModel, 
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite, 
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EmbeddedIdMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	public JpaComposite createJavaElementCollectionMapping2_0Composite(
			PropertyValueModel<JavaElementCollectionMapping2_0> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEclipseLinkElementCollectionMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@Override
	public JpaComposite createJavaOneToManyMappingComposite(
			PropertyValueModel<JavaOneToManyMapping> mappingModel, 
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite, 
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEclipseLinkOneToManyMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@Override
	public JpaComposite createJavaOneToOneMappingComposite(
		PropertyValueModel<JavaOneToOneMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEclipseLinkOneToOneMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@Override
	public JpaComposite createJavaManyToOneMappingComposite(
		PropertyValueModel<JavaManyToOneMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEclipseLinkManyToOneMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@Override
	public JpaComposite createJavaManyToManyMappingComposite(
			PropertyValueModel<JavaManyToManyMapping> mappingModel, 
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite, 
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEclipseLinkManyToManyMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}

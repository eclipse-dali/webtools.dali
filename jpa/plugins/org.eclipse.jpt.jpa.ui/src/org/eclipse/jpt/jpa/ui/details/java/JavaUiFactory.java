/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.details.java;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.swt.widgets.Composite;

/**
 * Use a Java UI factory to create any Java JPA composites.
 * <p>
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface JavaUiFactory {

	// ********** type mappings **********

	JpaComposite createJavaMappedSuperclassComposite(
		PropertyValueModel<JavaMappedSuperclass> mappedSuperclassModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createJavaEntityComposite(
		PropertyValueModel<JavaEntity> entityModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createJavaEmbeddableComposite(
		PropertyValueModel<JavaEmbeddable> embeddableModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);


	// ********** attribute mappings **********

	JpaComposite createJavaIdMappingComposite(
		PropertyValueModel<JavaIdMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createJavaEmbeddedIdMappingComposite(
		PropertyValueModel<JavaEmbeddedIdMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createJavaBasicMappingComposite(
		PropertyValueModel<JavaBasicMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createJavaVersionMappingComposite(
		PropertyValueModel<JavaVersionMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createJavaManyToOneMappingComposite(
		PropertyValueModel<JavaManyToOneMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createJavaOneToManyMappingComposite(
		PropertyValueModel<JavaOneToManyMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createJavaOneToOneMappingComposite(
		PropertyValueModel<JavaOneToOneMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createJavaManyToManyMappingComposite(
		PropertyValueModel<JavaManyToManyMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createJavaEmbeddedMappingComposite(
		PropertyValueModel<JavaEmbeddedMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createJavaTransientMappingComposite(
		PropertyValueModel<JavaTransientMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);
}

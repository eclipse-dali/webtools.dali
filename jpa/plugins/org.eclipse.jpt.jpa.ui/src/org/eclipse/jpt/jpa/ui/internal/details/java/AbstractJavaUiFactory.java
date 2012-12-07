/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.java;

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
import org.eclipse.jpt.jpa.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.jpa.ui.internal.details.BasicMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.EmbeddedIdMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.IdMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.ManyToManyMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.ManyToOneMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.OneToManyMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.OneToOneMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.TransientMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.VersionMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.java.JavaEmbeddedMapping2_0Composite;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractJavaUiFactory
	implements JavaUiFactory
{
	// ********** type mappings **********

	public JpaComposite createJavaMappedSuperclassComposite(
			PropertyValueModel<JavaMappedSuperclass> mappedSuperclassModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaMappedSuperclassComposite(mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createJavaEntityComposite(
			PropertyValueModel<JavaEntity> entityModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEntityComposite(entityModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createJavaEmbeddableComposite(
			PropertyValueModel<JavaEmbeddable> embeddableModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEmbeddableComposite(embeddableModel, parentComposite, widgetFactory, resourceManager);
	}


	// ********** attribute mappings **********

	public JpaComposite createJavaIdMappingComposite(
			PropertyValueModel<JavaIdMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new IdMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createJavaEmbeddedIdMappingComposite(
			PropertyValueModel<JavaEmbeddedIdMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EmbeddedIdMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createJavaBasicMappingComposite(
			PropertyValueModel<JavaBasicMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new BasicMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createJavaVersionMappingComposite(
			PropertyValueModel<JavaVersionMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new VersionMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createJavaManyToOneMappingComposite(
			PropertyValueModel<JavaManyToOneMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new ManyToOneMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createJavaOneToManyMappingComposite(
			PropertyValueModel<JavaOneToManyMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OneToManyMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createJavaOneToOneMappingComposite(
			PropertyValueModel<JavaOneToOneMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OneToOneMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createJavaManyToManyMappingComposite(
			PropertyValueModel<JavaManyToManyMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new ManyToManyMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createJavaEmbeddedMappingComposite(
			PropertyValueModel<JavaEmbeddedMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEmbeddedMapping2_0Composite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createJavaTransientMappingComposite(
			PropertyValueModel<JavaTransientMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new TransientMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}

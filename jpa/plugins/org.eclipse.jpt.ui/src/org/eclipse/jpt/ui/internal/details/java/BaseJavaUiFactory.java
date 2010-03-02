/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.java;

import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.ui.internal.details.BasicMappingComposite;
import org.eclipse.jpt.ui.internal.details.EmbeddedIdMappingComposite;
import org.eclipse.jpt.ui.internal.details.IdMappingComposite;
import org.eclipse.jpt.ui.internal.details.ManyToManyMappingComposite;
import org.eclipse.jpt.ui.internal.details.ManyToOneMappingComposite;
import org.eclipse.jpt.ui.internal.details.OneToManyMappingComposite;
import org.eclipse.jpt.ui.internal.details.OneToOneMappingComposite;
import org.eclipse.jpt.ui.internal.details.TransientMappingComposite;
import org.eclipse.jpt.ui.internal.details.VersionMappingComposite;
import org.eclipse.jpt.ui.internal.details.orm.OrmEmbeddableComposite;
import org.eclipse.jpt.ui.internal.details.orm.OrmEntityComposite;
import org.eclipse.jpt.ui.internal.details.orm.OrmMappedSuperclassComposite;
import org.eclipse.jpt.ui.internal.jpa2.details.java.JavaEmbeddedMapping2_0Composite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * The default implementation of the UI factory required to show the information
 * related to a JPA mapping (type or attribute).
 */
public abstract class BaseJavaUiFactory implements JavaUiFactory
{	
	
	// **************** java type mapping composites ***************************
	
	public JpaComposite createJavaMappedSuperclassComposite(
			PropertyValueModel<JavaMappedSuperclass> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new JavaMappedSuperclassComposite(subjectHolder, parent, widgetFactory);
	}
	
	public JpaComposite createJavaEntityComposite(
			PropertyValueModel<JavaEntity> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new JavaEntityComposite(subjectHolder, parent, widgetFactory);
	}
	
	public JpaComposite createJavaEmbeddableComposite(
			PropertyValueModel<JavaEmbeddable> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new JavaEmbeddableComposite(subjectHolder, parent, widgetFactory);
	}
	
	
	// **************** orm type mapping composites ****************************
	
	public JpaComposite createOrmMappedSuperclassComposite(
			PropertyValueModel<OrmMappedSuperclass> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmMappedSuperclassComposite(subjectHolder, parent, widgetFactory);
	}
	
	public JpaComposite createOrmEntityComposite(
			PropertyValueModel<OrmEntity> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEntityComposite(subjectHolder, parent, widgetFactory);
	}
	
	public JpaComposite createOrmEmbeddableComposite(
			PropertyValueModel<OrmEmbeddable> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEmbeddableComposite(subjectHolder, parent, widgetFactory);
	}
	
	
	// **************** java attribute mapping composites **********************
	
	public JpaComposite createJavaIdMappingComposite(
			PropertyValueModel<JavaIdMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new IdMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	public JpaComposite createJavaEmbeddedIdMappingComposite(
			PropertyValueModel<JavaEmbeddedIdMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new EmbeddedIdMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	public JpaComposite createJavaBasicMappingComposite(
			PropertyValueModel<JavaBasicMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new BasicMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	public JpaComposite createJavaVersionMappingComposite(
			PropertyValueModel<JavaVersionMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new VersionMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	public JpaComposite createJavaManyToOneMappingComposite(
			PropertyValueModel<JavaManyToOneMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new ManyToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	public JpaComposite createJavaOneToManyMappingComposite(
			PropertyValueModel<JavaOneToManyMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OneToManyMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	public JpaComposite createJavaOneToOneMappingComposite(
			PropertyValueModel<JavaOneToOneMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OneToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	public JpaComposite createJavaManyToManyMappingComposite(
			PropertyValueModel<JavaManyToManyMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new ManyToManyMappingComposite(subjectHolder, parent, widgetFactory);
	}

	public JpaComposite createJavaEmbeddedMappingComposite(
			PropertyValueModel<JavaEmbeddedMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new JavaEmbeddedMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	public JpaComposite createJavaTransientMappingComposite(
			PropertyValueModel<JavaTransientMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new TransientMappingComposite(subjectHolder, parent, widgetFactory);
	}
}

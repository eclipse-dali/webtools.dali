/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicMapMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkTransformationMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkBasicCollectionMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkBasicMapMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkBasicMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkIdMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkManyToManyMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkManyToOneMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkOneToManyMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkOneToOneMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkTransformationMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkVariableOneToOneMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkVersionMappingComposite;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.details.java.BaseJavaUiFactory;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkJavaUiFactory extends BaseJavaUiFactory
{
	public EclipseLinkJavaUiFactory() {
		super();
	}
	
	// **************** java type mapping composites ***************************
	
	@Override
	public JpaComposite createJavaMappedSuperclassComposite(
			PropertyValueModel<JavaMappedSuperclass> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		return new JavaEclipseLinkMappedSuperclassComposite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaEntityComposite(
			PropertyValueModel<JavaEntity> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		return new JavaEclipseLinkEntityComposite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaEmbeddableComposite(
			PropertyValueModel<JavaEmbeddable> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		return new JavaEclipseLinkEmbeddableComposite(subjectHolder, parent, widgetFactory);
	}
	
	
	// **************** java attribute mapping composites **********************
	
	@Override
	public JpaComposite createJavaIdMappingComposite(
			PropertyValueModel<JavaIdMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new EclipseLinkIdMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaBasicMappingComposite(
			PropertyValueModel<JavaBasicMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new EclipseLinkBasicMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaVersionMappingComposite(
			PropertyValueModel<JavaVersionMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new EclipseLinkVersionMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaManyToOneMappingComposite(
			PropertyValueModel<JavaManyToOneMapping> subjectHolder, 
			Composite parent, 
			WidgetFactory widgetFactory) {
		return new EclipseLinkManyToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaOneToManyMappingComposite(
			PropertyValueModel<JavaOneToManyMapping> subjectHolder, 
			Composite parent, 
			WidgetFactory widgetFactory) {
		return new EclipseLinkOneToManyMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaOneToOneMappingComposite(
			PropertyValueModel<JavaOneToOneMapping> subjectHolder, 
			Composite parent, 
			WidgetFactory widgetFactory) {
		return new EclipseLinkOneToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaManyToManyMappingComposite(
			PropertyValueModel<JavaManyToManyMapping> subjectHolder, 
			Composite parent, 
			WidgetFactory widgetFactory) {
		return new EclipseLinkManyToManyMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	public JpaComposite createJavaEclipseLinkBasicMapMappingComposite(
			PropertyValueModel<JavaEclipseLinkBasicMapMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new EclipseLinkBasicMapMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	public JpaComposite createJavaEclipseLinkBasicCollectionMappingComposite(
			PropertyValueModel<JavaEclipseLinkBasicCollectionMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new EclipseLinkBasicCollectionMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	public JpaComposite createJavaEclipseLinkVariableOneToOneMappingComposite(
			PropertyValueModel<JavaEclipseLinkVariableOneToOneMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new EclipseLinkVariableOneToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}
	
	public JpaComposite createJavaEclipseLinkTransformationMappingComposite(
			PropertyValueModel<JavaEclipseLinkTransformationMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new EclipseLinkTransformationMappingComposite(subjectHolder, parent, widgetFactory);
	}


}
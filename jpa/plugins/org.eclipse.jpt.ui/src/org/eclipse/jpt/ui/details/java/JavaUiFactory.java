/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.details.java;

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
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Use {@link JavaUiFactory} to create any java JPA composites
 * <p>
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 *
 *
 * @version 3.0
 * @since 3.0
 */
public interface JavaUiFactory
{	
	
	// **************** java type mapping composites ***************************
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit a <code>JavaMappedSuperclass</code>.
	 *
	 * @param subjectHolder The holder of the mapped superclass
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createJavaMappedSuperclassComposite(
		PropertyValueModel<JavaMappedSuperclass> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit an <code>JavaEntity</code>.
	 *
	 * @param subjectHolder The holder of the java entity
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createJavaEntityComposite(
		PropertyValueModel<JavaEntity> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit a <code>JavaEmbeddable</code>.
	 *
	 * @param subjectHolder The holder of the embeddable
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createJavaEmbeddableComposite(
		PropertyValueModel<JavaEmbeddable> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	
	// **************** orm type mapping composites ****************************
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit an <code>OrmMappedSuperclass</code>.
	 *
	 * @param subjectHolder The holder of the mapped superclass
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createOrmMappedSuperclassComposite(
		PropertyValueModel<OrmMappedSuperclass> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit an <code>OrmEntity</code>.
	 *
	 * @param subjectHolder The holder of the orm entity
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createOrmEntityComposite(
		PropertyValueModel<OrmEntity> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);

	/**
	 * Creates a new <code>JpaComposite</code> used to edit an <code>OrmEmbeddable</code>.
	 *
	 * @param subjectHolder The holder of the embeddable
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createOrmEmbeddableComposite(
		PropertyValueModel<OrmEmbeddable> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	
	// **************** java attribute mapping composites **********************
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit a <code>JavaIdMapping</code>.
	 *
	 * @param subjectHolder The holder of the ID mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createJavaIdMappingComposite(
		PropertyValueModel<JavaIdMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit a <code>JavaEmbeddedIdMapping</code>.
	 *
	 * @param subjectHolder The holder of the embedded ID mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createJavaEmbeddedIdMappingComposite(
		PropertyValueModel<JavaEmbeddedIdMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit a <code>JavaBasicMapping</code>.
	 *
	 * @param subjectHolder The holder of the basic mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createJavaBasicMappingComposite(
		PropertyValueModel<JavaBasicMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit a <code>JavaVersionMapping</code>.
	 *
	 * @param subjectHolder The holder of the version mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createJavaVersionMappingComposite(
		PropertyValueModel<JavaVersionMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit a <code>JavaManyToOneMapping</code>.
	 *
	 * @param subjectHolder The holder of the many to one mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createJavaManyToOneMappingComposite(
		PropertyValueModel<JavaManyToOneMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit a <code>JavaOneToManyMapping</code>.
	 *
	 * @param subjectHolder The holder of the one to many mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createJavaOneToManyMappingComposite(
		PropertyValueModel<? extends JavaOneToManyMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit a <code>JavaOneToOneMapping</code>.
	 *
	 * @param subjectHolder The holder of the one to one mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createJavaOneToOneMappingComposite(
		PropertyValueModel<JavaOneToOneMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit a <code>JavaManyToManyMapping</code>.
	 *
	 * @param subjectHolder The holder of the many to many mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createJavaManyToManyMappingComposite(
		PropertyValueModel<JavaManyToManyMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit a <code>JavaEmbeddedMapping</code>.
	 *
	 * @param subjectHolder The holder of the embedded mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createJavaEmbeddedMappingComposite(
		PropertyValueModel<JavaEmbeddedMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit a <code>JavaTransientMapping</code>.
	 *
	 * @param subjectHolder The holder of the transient mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createJavaTransientMappingComposite(
		PropertyValueModel<JavaTransientMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
}
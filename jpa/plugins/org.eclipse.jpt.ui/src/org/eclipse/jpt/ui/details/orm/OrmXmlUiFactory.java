/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.swt.widgets.Composite;

/**
 * Use {@link OrmXmlUiFactory} to create any ORM JPA composites
 * <p>
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 *
 * @see org.eclipse.jpt.ui.internal.BaseJpaUiFactory
 *
 * @version 2.0
 * @since 1.0
 */
public interface OrmXmlUiFactory
{
	
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
	

	// **************** orm attribute mapping composites ***********************
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit an <code>OrmIdMapping</code>.
	 *
	 * @param subjectHolder The holder of the ID mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createOrmIdMappingComposite(
		PropertyValueModel<OrmIdMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit an <code>OrmEmbeddedIdMapping</code>.
	 *
	 * @param subjectHolder The holder of the embedded ID mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createOrmEmbeddedIdMappingComposite(
		PropertyValueModel<OrmEmbeddedIdMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit an <code>OrmBasicMapping</code>.
	 *
	 * @param subjectHolder The holder of the basic mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createOrmBasicMappingComposite(
		PropertyValueModel<OrmBasicMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit an <code>OrmVersionMapping</code>.
	 *
	 * @param subjectHolder The holder of the version mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createOrmVersionMappingComposite(
		PropertyValueModel<OrmVersionMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit an <code>OrmManyToOneMapping</code>.
	 *
	 * @param subjectHolder The holder of the many to one mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createOrmManyToOneMappingComposite(
		PropertyValueModel<OrmManyToOneMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit an <code>OrmOneToManyMapping</code>.
	 *
	 * @param subjectHolder The holder of the one to many mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createOrmOneToManyMappingComposite(
		PropertyValueModel<OrmOneToManyMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit an <code>OrmOneToOneMapping</code>.
	 *
	 * @param subjectHolder The holder of the one to one mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createOrmOneToOneMappingComposite(
		PropertyValueModel<OrmOneToOneMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit an <code>OrmManyToManyMapping</code>.
	 *
	 * @param subjectHolder The holder of the many to many mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createOrmManyToManyMappingComposite(
		PropertyValueModel<OrmManyToManyMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
		
	/**
	 * Creates a new <code>JpaComposite</code> used to edit an <code>OrmEmbeddedMapping</code>.
	 *
	 * @param subjectHolder The holder of the embedded mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createOrmEmbeddedMappingComposite(
		PropertyValueModel<OrmEmbeddedMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
	
	/**
	 * Creates a new <code>JpaComposite</code> used to edit an <code>OrmTransientMapping</code>.
	 *
	 * @param subjectHolder The holder of the transient mapping
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create the widgets
	 * @return A new <code>JpaComposite</code>
	 */
	JpaComposite createOrmTransientMappingComposite(
		PropertyValueModel<OrmTransientMapping> subjectHolder,
		Composite parent,
		WidgetFactory widgetFactory);
}
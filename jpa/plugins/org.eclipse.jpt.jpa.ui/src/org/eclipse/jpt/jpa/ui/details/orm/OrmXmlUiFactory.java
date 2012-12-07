/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.swt.widgets.Composite;

/**
 * Use an <code>orm.xml</code> UI factory to create any <code>orm.xml</code>
 * JPA composites.
 * <p>
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.0
 * @since 1.0
 */
public interface OrmXmlUiFactory {

	// ********** type mappings **********

	JpaComposite createOrmMappedSuperclassComposite(
		PropertyValueModel<OrmMappedSuperclass> mappedSuperclassModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createOrmEntityComposite(
		PropertyValueModel<OrmEntity> entityModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createOrmEmbeddableComposite(
		PropertyValueModel<OrmEmbeddable> embeddableModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);


	// ********** attribute mappings **********

	JpaComposite createOrmIdMappingComposite(
		PropertyValueModel<OrmIdMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createOrmEmbeddedIdMappingComposite(
		PropertyValueModel<OrmEmbeddedIdMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createOrmBasicMappingComposite(
		PropertyValueModel<OrmBasicMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createOrmVersionMappingComposite(
		PropertyValueModel<OrmVersionMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createOrmManyToOneMappingComposite(
		PropertyValueModel<OrmManyToOneMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createOrmOneToManyMappingComposite(
		PropertyValueModel<OrmOneToManyMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createOrmOneToOneMappingComposite(
		PropertyValueModel<OrmOneToOneMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createOrmManyToManyMappingComposite(
		PropertyValueModel<OrmManyToManyMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createOrmEmbeddedMappingComposite(
		PropertyValueModel<OrmEmbeddedMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createOrmTransientMappingComposite(
		PropertyValueModel<OrmTransientMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);
}

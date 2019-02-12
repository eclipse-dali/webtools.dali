/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.details;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.swt.widgets.Composite;

/**
 * Use {@link JpaUiFactory} to create any JPA composites for use in the JPA Details view
 * <p>
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 *
 *
 * @version 3.3
 * @since 3.3
 */
public interface JpaUiFactory
{

	// ********** type mappings **********

	JpaComposite createMappedSuperclassComposite(
		PropertyValueModel<? extends MappedSuperclass> mappedSuperclassModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createEntityComposite(
		PropertyValueModel<? extends Entity> entityModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createEmbeddableComposite(
		PropertyValueModel<? extends Embeddable> embeddableModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);


	// ********** attribute mappings **********

	JpaComposite createIdMappingComposite(
		PropertyValueModel<? extends IdMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createEmbeddedIdMappingComposite(
		PropertyValueModel<? extends EmbeddedIdMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createBasicMappingComposite(
		PropertyValueModel<? extends BasicMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createVersionMappingComposite(
		PropertyValueModel<? extends VersionMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createManyToOneMappingComposite(
		PropertyValueModel<? extends ManyToOneMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createOneToManyMappingComposite(
		PropertyValueModel<? extends OneToManyMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createOneToOneMappingComposite(
		PropertyValueModel<? extends OneToOneMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createManyToManyMappingComposite(
		PropertyValueModel<? extends ManyToManyMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createEmbeddedMappingComposite(
		PropertyValueModel<? extends EmbeddedMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);

	JpaComposite createTransientMappingComposite(
		PropertyValueModel<? extends TransientMapping> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);
}
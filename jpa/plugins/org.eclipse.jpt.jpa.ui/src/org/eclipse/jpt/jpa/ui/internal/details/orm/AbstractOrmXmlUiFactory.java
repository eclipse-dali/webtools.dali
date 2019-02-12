/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

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
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JpaUiFactory;
import org.eclipse.jpt.jpa.ui.internal.details.TransientMappingComposite;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractOrmXmlUiFactory
	implements JpaUiFactory
{
	// ********** type mappings **********

	@SuppressWarnings("unchecked")
	public JpaComposite createMappedSuperclassComposite(
			PropertyValueModel<? extends MappedSuperclass> mappedSuperclassModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmMappedSuperclassComposite((PropertyValueModel<? extends OrmMappedSuperclass>) mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
	}

	@SuppressWarnings("unchecked")
	public JpaComposite createEntityComposite(
			PropertyValueModel<? extends Entity> entityModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEntityComposite((PropertyValueModel<? extends OrmEntity>) entityModel, parentComposite, widgetFactory, resourceManager);
	}

	@SuppressWarnings("unchecked")
	public JpaComposite createEmbeddableComposite(
			PropertyValueModel<? extends Embeddable> embeddableModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEmbeddableComposite((PropertyValueModel<? extends OrmEmbeddable>) embeddableModel, parentComposite, widgetFactory, resourceManager);
	}


	// ********** attribute mappings **********

	public JpaComposite createIdMappingComposite(
			PropertyValueModel<? extends IdMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmIdMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createEmbeddedIdMappingComposite(
			PropertyValueModel<? extends EmbeddedIdMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEmbeddedIdMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createBasicMappingComposite(
			PropertyValueModel<? extends BasicMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmBasicMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createVersionMappingComposite(
			PropertyValueModel<? extends VersionMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmVersionMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createManyToOneMappingComposite(
			PropertyValueModel<? extends ManyToOneMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmManyToOneMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createOneToManyMappingComposite(
			PropertyValueModel<? extends OneToManyMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmOneToManyMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createOneToOneMappingComposite(
			PropertyValueModel<? extends OneToOneMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmOneToOneMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createManyToManyMappingComposite(
			PropertyValueModel<? extends ManyToManyMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmManyToManyMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createEmbeddedMappingComposite(
			PropertyValueModel<? extends EmbeddedMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEmbeddedMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	public JpaComposite createTransientMappingComposite(
			PropertyValueModel<? extends TransientMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new TransientMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}

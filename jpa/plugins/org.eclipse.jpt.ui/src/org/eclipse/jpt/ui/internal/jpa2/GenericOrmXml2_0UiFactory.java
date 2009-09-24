/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2;

import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.details.TransientMappingComposite;
import org.eclipse.jpt.ui.internal.details.orm.BaseOrmXmlUiFactory;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmBasicMapping2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmEmbeddedIdMapping2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmEmbeddedMapping2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmEntity2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmIdMapping2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmManyToManyMapping2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmManyToOneMapping2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmOneToManyMapping2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmOneToOneMapping2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmVersionMapping2_0Composite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class GenericOrmXml2_0UiFactory extends BaseOrmXmlUiFactory
{
	// **************** orm type mapping composites ****************************
		
	@Override
	public JpaComposite createOrmEntityComposite(
			PropertyValueModel<OrmEntity> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEntity2_0Composite(subjectHolder, parent, widgetFactory);
	}

	
	// **************** orm attribute mapping composites ***********************
	
	@Override
	public JpaComposite createOrmIdMappingComposite(
			PropertyValueModel<OrmIdMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmIdMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createOrmEmbeddedIdMappingComposite(
			PropertyValueModel<OrmEmbeddedIdMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEmbeddedIdMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createOrmBasicMappingComposite(
			PropertyValueModel<OrmBasicMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmBasicMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createOrmVersionMappingComposite(
			PropertyValueModel<OrmVersionMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmVersionMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JpaComposite createOrmManyToOneMappingComposite(
			PropertyValueModel<OrmManyToOneMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmManyToOneMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createOrmOneToManyMappingComposite(
			PropertyValueModel<OrmOneToManyMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmOneToManyMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JpaComposite createOrmOneToOneMappingComposite(
			PropertyValueModel<OrmOneToOneMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmOneToOneMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createOrmManyToManyMappingComposite(
			PropertyValueModel<OrmManyToManyMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmManyToManyMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createOrmEmbeddedMappingComposite(
			PropertyValueModel<OrmEmbeddedMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEmbeddedMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createOrmTransientMappingComposite(
			PropertyValueModel<OrmTransientMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new TransientMappingComposite(subjectHolder, parent, widgetFactory);
	}
	

}

/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.BaseEclipseLinkOrmXmlUiFactory;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.details.orm.OrmEclipseLinkBasicMapping1_1Composite;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.details.orm.OrmEclipseLinkVersionMapping1_1Composite;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmElementCollectionMapping2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmEmbeddedMapping2_0Composite;
import org.eclipse.jpt.ui.jpa2.details.orm.OrmXmlUiFactory2_0;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkOrmXml2_0UiFactory 
	extends BaseEclipseLinkOrmXmlUiFactory
	implements OrmXmlUiFactory2_0
{
	@Override
	public JpaComposite createOrmEntityComposite(
			PropertyValueModel<OrmEntity> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkEntity2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JpaComposite createOrmMappedSuperclassComposite(
			PropertyValueModel<OrmMappedSuperclass> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkMappedSuperclass2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	// **************** orm attribute mapping composites ***********************
	
	@Override
	public JpaComposite createOrmIdMappingComposite(
			PropertyValueModel<OrmIdMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkIdMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createOrmEmbeddedMappingComposite(
			PropertyValueModel<OrmEmbeddedMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEmbeddedMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createOrmEmbeddedIdMappingComposite(
			PropertyValueModel<OrmEmbeddedIdMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkEmbeddedIdMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createOrmBasicMappingComposite(
			PropertyValueModel<OrmBasicMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkBasicMapping1_1Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createOrmVersionMappingComposite(
			PropertyValueModel<OrmVersionMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkVersionMapping1_1Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createOrmManyToOneMappingComposite(
			PropertyValueModel<OrmManyToOneMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkManyToOneMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JpaComposite createOrmOneToManyMappingComposite(
			PropertyValueModel<OrmOneToManyMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkOneToManyMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createOrmOneToOneMappingComposite(
			PropertyValueModel<OrmOneToOneMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkOneToOneMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createOrmManyToManyMappingComposite(
			PropertyValueModel<OrmManyToManyMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkManyToManyMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	public JpaComposite createOrmElementCollectionMapping2_0Composite(
			PropertyValueModel<OrmElementCollectionMapping2_0> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmElementCollectionMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
}

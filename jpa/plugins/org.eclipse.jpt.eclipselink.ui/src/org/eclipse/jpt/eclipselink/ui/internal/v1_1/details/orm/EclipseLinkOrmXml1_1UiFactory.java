/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v1_1.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.BaseEclipseLinkOrmXmlUiFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkOrmXml1_1UiFactory extends BaseEclipseLinkOrmXmlUiFactory
{
	// **************** orm attribute mapping composites ***********************
	
	@Override
	public JpaComposite createOrmIdMappingComposite(
			PropertyValueModel<OrmIdMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkIdMapping1_1Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createOrmEmbeddedIdMappingComposite(
			PropertyValueModel<OrmEmbeddedIdMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkEmbeddedIdMapping1_1Composite(subjectHolder, parent, widgetFactory);
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
		return new OrmEclipseLinkManyToOneMapping1_1Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JpaComposite createOrmOneToManyMappingComposite(
			PropertyValueModel<OrmOneToManyMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkOneToManyMapping1_1Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JpaComposite createOrmOneToOneMappingComposite(
			PropertyValueModel<OrmOneToOneMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkOneToOneMapping1_1Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createOrmManyToManyMappingComposite(
			PropertyValueModel<OrmManyToManyMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkManyToManyMapping1_1Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createOrmEmbeddedMappingComposite(
			PropertyValueModel<OrmEmbeddedMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkEmbeddedMapping1_1Composite(subjectHolder, parent, widgetFactory);
	}
}

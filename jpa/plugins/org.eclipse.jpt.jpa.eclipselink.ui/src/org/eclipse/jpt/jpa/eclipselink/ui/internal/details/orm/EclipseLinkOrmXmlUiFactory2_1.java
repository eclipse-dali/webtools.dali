/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmMappedSuperclass;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkOrmXmlUiFactory2_1
	extends EclipseLinkOrmXmlUiFactory2_0
{
	// ********** type mappings **********

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createMappedSuperclassComposite(
			PropertyValueModel<? extends MappedSuperclass> mappedSuperclassModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkOrmMappedSuperclassComposite2_1((PropertyValueModel<EclipseLinkOrmMappedSuperclass>) mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
	}


	// ********** attribute mappings **********

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createElementCollectionMapping2_0Composite(
			PropertyValueModel<? extends ElementCollectionMapping2_0> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new EclipseLinkOrmElementCollectionMappingComposite2_1((PropertyValueModel<? extends EclipseLinkElementCollectionMapping2_0>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}

/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.orm;

import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.BaseEclipseLinkOrmXmlUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkOrmXml2_0UiFactory extends BaseEclipseLinkOrmXmlUiFactory
{
	@Override
	public JpaComposite createOrmEntityComposite(
			PropertyValueModel<OrmEntity> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkEntity2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	// **************** orm attribute mapping composites ***********************
	
	@Override
	public JpaComposite createOrmIdMappingComposite(
			PropertyValueModel<OrmIdMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkIdMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}

}

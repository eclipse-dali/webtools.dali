/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_1.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.orm.EclipseLinkOrmXml2_0UiFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkOrmXml2_1UiFactory 
	extends EclipseLinkOrmXml2_0UiFactory
{

	
	@Override
	public JpaComposite createOrmElementCollectionMapping2_0Composite(
			PropertyValueModel<OrmElementCollectionMapping2_0> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new OrmEclipseLinkElementCollectionMapping2_1Composite(subjectHolder, parent, widgetFactory);
	}
}

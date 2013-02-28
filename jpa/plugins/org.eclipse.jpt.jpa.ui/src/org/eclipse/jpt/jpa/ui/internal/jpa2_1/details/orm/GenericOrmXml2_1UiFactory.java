/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2_1.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmEntity2_0;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.GenericOrmXml2_0UiFactory;
import org.eclipse.jpt.jpa.ui.jpa2_1.details.JpaUiFactory2_1;
import org.eclipse.swt.widgets.Composite;

public class GenericOrmXml2_1UiFactory
	extends GenericOrmXml2_0UiFactory
	implements JpaUiFactory2_1
{

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createEntityComposite(
			PropertyValueModel<? extends Entity> entityModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new OrmEntity2_1Composite((PropertyValueModel<? extends OrmEntity2_0>) entityModel, parentComposite, widgetFactory, resourceManager);
	}
}

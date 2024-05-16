/*******************************************************************************
 * Copyright (c) 2024 Lakshminarayana Nekkanti. All rights reserved.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa3_1.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmEntity2_0;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2_1.details.orm.GenericOrmXmlUiFactory3_0;
import org.eclipse.jpt.jpa.ui.jpa3_1.details.JpaUiFactory3_1;
import org.eclipse.swt.widgets.Composite;

public class GenericOrmXmlUiFactory3_1 extends GenericOrmXmlUiFactory3_0 implements JpaUiFactory3_1 {

	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createEntityComposite(PropertyValueModel<? extends Entity> entityModel,
			Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return new OrmEntityComposite3_1((PropertyValueModel<? extends OrmEntity2_0>) entityModel, parentComposite,
				widgetFactory, resourceManager);
	}
}

/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.jpa2.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.swt.widgets.Composite;

/**
 * Use an <code>orm.xml</code> UI factory to create any <code>orm.xml</code>
 * JPA composites.
 * <p>
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.0
 * @since 3.0
 */
public interface OrmXmlUiFactory2_0
	extends OrmXmlUiFactory
{
	JpaComposite createOrmElementCollectionMapping2_0Composite(
		PropertyValueModel<OrmElementCollectionMapping2_0> mappingModel,
		PropertyValueModel<Boolean> enabledModel,
		Composite parentComposite,
		WidgetFactory widgetFactory,
		ResourceManager resourceManager);
}
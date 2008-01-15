/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public interface IJpaDetailsProvider
{
	/**
	 * Creates a new details page for.
	 */
	IJpaDetailsPage<? extends IJpaContextNode> buildDetailsPage(
		PropertyValueModel<IJpaContextNode> subjectHolder,
		Composite parentComposite,
		Object contentNodeId,
		TabbedPropertySheetWidgetFactory widgetFactory);
}
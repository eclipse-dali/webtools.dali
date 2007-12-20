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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public interface IJpaDetailsProvider
{
	/**
	 * Build a properties page given the parent Composite and the
	 * content node id.
	 * It is legal to set the layout for the given Composite.
	 */
	IJpaDetailsPage<? extends IJpaContextNode> buildDetailsPage(
		Composite parentComposite,
		Object contentNodeId,
		TabbedPropertySheetWidgetFactory widgetFactory);

	void dispose();

	String fileContentType();
}
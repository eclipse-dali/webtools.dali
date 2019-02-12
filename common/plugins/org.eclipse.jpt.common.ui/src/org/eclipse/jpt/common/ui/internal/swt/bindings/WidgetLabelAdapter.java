/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.bindings;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Widget;

/**
 * A interface used to bind all the various <em>label</em> implementations to
 * models.
 * @see SWTBindingTools#bind(org.eclipse.jpt.common.utility.model.value.PropertyValueModel, org.eclipse.jpt.common.utility.model.value.PropertyValueModel, WidgetLabelAdapter)
 */
public interface WidgetLabelAdapter {
	/**
	 * Set the widget's image.
	 * Pre-condition: The adapter's widget is not disposed.
	 */
	void setImage(Image image);

	/**
	 * Set the widget's text.
	 * Pre-condition: The adapter's widget is not disposed.
	 */
	void setText(String text);

	/**
	 * Return the adapted widget.
	 */
	Widget getWidget();
}

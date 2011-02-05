/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.swing;

import javax.swing.Icon;

import org.eclipse.jpt.common.utility.model.Model;

/**
 * Used by general-purpose UI models and renderers to cast
 * application model objects to something displayable.
 */
public interface Displayable
	extends Model
{

	/**
	 * Return a string that can be used to identify the model
	 * in a textual UI setting (typically the object's name).
	 * When the display string changes, the model should fire
	 * the appropriate change notification:
	 *     this.firePropertyChanged(DISPLAY_STRING_PROPERTY, oldDisplayString, this.displayString());
	 */
	String displayString();
		String DISPLAY_STRING_PROPERTY = "displayString"; //$NON-NLS-1$

	/**
	 * Return an icon that can be used to identify the model
	 * in a UI component that supports icons (the icon can be null).
	 * When the icon changes, the model should fire
	 * the appropriate change notification:
	 *     this.firePropertyChanged(ICON_PROPERTY, oldIcon, this.icon());
	 */
	Icon icon();
		String ICON_PROPERTY = "icon"; //$NON-NLS-1$

}

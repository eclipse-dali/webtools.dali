/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model;

import javax.swing.Icon;

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.transformer.Transformer;

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
	 * the appropriate change notification:<pre>
	 *     this.firePropertyChanged(DISPLAY_STRING_PROPERTY, oldDisplayString, this.displayString);
	 * </pre>
	 */
	String displayString();
		String DISPLAY_STRING_PROPERTY = "displayString"; //$NON-NLS-1$

		Transformer<Displayable, String> DISPLAY_STRING_TRANSFORMER = new DisplayStringTransformer();
		class DisplayStringTransformer
			extends TransformerAdapter<Displayable, String>
		{
			@Override
			public String transform(Displayable input) {
				return input.displayString();
			}
		}

	/**
	 * Return an icon that can be used to identify the model
	 * in a UI component that supports icons (the icon can be null).
	 * When the icon changes, the model should fire
	 * the appropriate change notification:<pre>
	 *     this.firePropertyChanged(ICON_PROPERTY, oldIcon, this.icon);
	 * </pre>
	 */
	Icon icon();
		String ICON_PROPERTY = "icon"; //$NON-NLS-1$

		Transformer<Displayable, Icon> ICON_TRANSFORMER = new IconTransformer();
		class IconTransformer
			extends TransformerAdapter<Displayable, Icon>
		{
			@Override
			public Icon transform(Displayable input) {
				return input.icon();
			}
		}
}

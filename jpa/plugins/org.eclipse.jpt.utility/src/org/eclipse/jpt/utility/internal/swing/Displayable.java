/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.swing;

import java.text.Collator;
import java.util.Comparator;
import javax.swing.Icon;
import org.eclipse.jpt.utility.model.Model;

/**
 * Used by general-purpose UI models and renderers to cast
 * application model objects to something displayable.
 */
public interface Displayable
	extends Model, Comparable<Displayable>
{

	/**
	 * Return a string that can be used to identify the model
	 * in a textual UI setting (typically the object's name).
	 * When the display string changes, the model should fire
	 * the appropriate change notification:
	 *     this.firePropertyChanged(DISPLAY_STRING_PROPERTY, oldDisplayString, this.displayString());
	 */
	String displayString();
		String DISPLAY_STRING_PROPERTY = "displayString";

	/**
	 * Return an icon that can be used to identify the model
	 * in a UI component that supports icons (the icon can be null).
	 * When the icon changes, the model should fire
	 * the appropriate change notification:
	 *     this.firePropertyChanged(ICON_PROPERTY, oldIcon, this.icon());
	 */
	Icon icon();
		String ICON_PROPERTY = "icon";


	// ********** helper implementations **********

	Collator DEFAULT_COLLATOR = Collator.getInstance();

	/**
	 * Since all displayable objects must be comparable, provide a
	 * typical comparator that can be used to sort a collection of
	 * displayable objects.
	 * Sort based on display string:
	 *     - identical objects are equal (which means they cannot
	 *         co-exist in a SortedSet)
	 *     - use the default collator (which typically interleaves
	 *         lower- and upper-case letters)
	 *     - allow duplicate display strings (from different objects)
	 *     - try to return consistent results for same object pairs
	 */
	Comparator<Displayable> DEFAULT_COMPARATOR =
		new Comparator<Displayable>() {
			public int compare(Displayable d1, Displayable d2) {
				// disallow duplicates based on object identity
				if (d1 == d2) {
					return 0;
				}

				// first compare display strings using the default collator
				int result = DEFAULT_COLLATOR.compare(d1.displayString(), d2.displayString());
				if (result != 0) {
					return result;
				}

				// then compare using object-id
				result = System.identityHashCode(d1) - System.identityHashCode(d2);
				if (result != 0) {
					return result;
				}

				// It's unlikely that we get to this point; but, just in case, we will return -1.
				// Unfortunately, this introduces some mild unpredictability to the sort order
				// (unless the objects are always passed into this method in the same order).
				return -1;		// if all else fails, indicate that o1 < o2
			}
			@Override
			public String toString() {
				return "Displayable.DEFAULT_COMPARATOR";
			}
		};

}

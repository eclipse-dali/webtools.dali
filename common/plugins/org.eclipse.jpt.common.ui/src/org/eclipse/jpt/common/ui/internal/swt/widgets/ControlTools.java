/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.widgets;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.SharedScrolledComposite;

/**
 * {@link Control} utility methods.
 */
public final class ControlTools {

	// ********** center **********

	/**
	 * Center the specified control within its display.
	 * (This is helpful for centering {@link org.eclipse.swt.widgets.Shell}s in
	 * a display.)
	 * <p>
	 * <strong>NB:</strong> This will not look too good on a dual monitor system.
	 */
	public static void center(Control control) {
		control.setBounds(calculateCenteredBounds(control.getBounds(), control.getDisplay().getBounds()));
	}

	/**
	 * Center the specified control within the specified parent control.
	 */
	public static void center(Control control, Control parentControl) {
		control.setBounds(calculateCenteredBounds(control.getBounds(), parentControl.getBounds()));
	}

	/**
	 * Calculate the bounds (within the second specified rectangle's coordinate
	 * system) that would center the first specified rectangle
	 * with respect to the second specified rectangle.
	 */
	public static Rectangle calculateCenteredBounds(Rectangle rectangle1, Rectangle rectangle2) {
		return calculateCenteredBounds(rectangle1.width, rectangle1.height, rectangle2.x, rectangle2.y, rectangle2.width, rectangle2.height);
	}

	/**
	 * Calculate the bounds (within the second specified rectangle's coordinate
	 * system) that would center the first specified rectangle
	 * with respect to the second specified rectangle.
	 */
	public static Rectangle calculateCenteredBounds(Point size1, Point size2) {
		return calculateCenteredBounds(size1.x, size1.y, size2.x, size2.y);
	}

	/**
	 * Calculate the point (within the second specified rectangle's coordinate
	 * system) that would center the first specified rectangle
	 * with respect to the second specified rectangle.
	 */
	public static Rectangle calculateCenteredBounds(int width1, int height1, int width2, int height2) {
		return calculateCenteredBounds(width1, height1, 0, 0, width2, height2);
	}


	/**
	 * Calculate the point (within the second specified rectangle's coordinate
	 * system) that would center the first specified rectangle
	 * with respect to the second specified rectangle.
	 */
	public static Rectangle calculateCenteredBounds(int width1, int height1, int x2, int y2, int width2, int height2) {
		return new Rectangle(x2 + ((width2 - width1) >> 1), y2 + ((height2 - height1) >> 1), width1, height1);
	}

	/**
	 * Calculate the point (within the second specified rectangle's coordinate
	 * system) that would center the first specified rectangle
	 * with respect to the second specified rectangle.
	 */
	public static Point calculateCenteredPosition(Rectangle rectangle1, Rectangle rectangle2) {
		return calculateCenteredPosition(rectangle1.width, rectangle1.height, rectangle2.x, rectangle2.y, rectangle2.width, rectangle2.height);
	}

	/**
	 * Calculate the point (within the second specified rectangle's coordinate
	 * system) that would center the first specified rectangle
	 * with respect to the second specified rectangle.
	 */
	public static Point calculateCenteredPosition(Point size1, Point size2) {
		return calculateCenteredPosition(size1.x, size1.y, size2.x, size2.y);
	}

	/**
	 * Calculate the point (within the second specified rectangle's coordinate
	 * system) that would center the first specified rectangle
	 * with respect to the second specified rectangle.
	 */
	public static Point calculateCenteredPosition(int width1, int height1, int width2, int height2) {
		return calculateCenteredPosition(width1, height1, 0, 0, width2, height2);
	}

	/**
	 * Calculate the point (within the second specified rectangle's coordinate
	 * system) that would center the first specified rectangle
	 * with respect to the second specified rectangle.
	 */
	public static Point calculateCenteredPosition(int width1, int height1, int x2, int y2, int width2, int height2) {
		return new Point(x2 + ((width2 - width1) >> 1), y2 + ((height2 - height1) >> 1));
	}


	// ********** reflow **********

	/**
	 * Reflow the specified control and all its ancestors up its containment
	 * hierarchy.
	 * @see #reflow(Composite)
	 */
	public static void reflow(Control control) {
		reflow((control instanceof Composite) ? (Composite) control : control.getParent());
	}

	/**
	 * Reflow the specified composite and all its ancestors up its containment
	 * hierarchy until we reach a {@link SharedScrolledComposite} or the window
	 * (i.e. the top of the containment hierarchy).
	 * Copied from {@link org.eclipse.ui.forms.widgets.Section#reflow()}.
	 */
	public static void reflow(Composite composite) {
		for (Composite c = composite; c != null; ) {
			c.setRedraw(false);
			c = c.getParent();
			if ((c instanceof SharedScrolledComposite) || (c instanceof Shell)) {
				break;
			}
		}

		for (Composite c = composite; c != null; ) {
			c.layout(true);
			c = c.getParent();
			if (c instanceof SharedScrolledComposite) {
				((SharedScrolledComposite) c).reflow(true);
				break;
			}
		}

		for (Composite c = composite; c != null; ) {
			c.setRedraw(true);
			c = c.getParent();
			if ((c instanceof SharedScrolledComposite) || (c instanceof Shell)) {
				break;
			}
		}
	}


	// ********** disabled constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private ControlTools() {
		super();
		throw new UnsupportedOperationException();
	}
}

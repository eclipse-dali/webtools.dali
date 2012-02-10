/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * This <code>WidgetFactory</code> is responsible to create the widgets using
 * <code>TabbedPropertySheetWidgetFactory</code> in order use the form style
 * (flat-style) look and feel.
 *
 * @see TabbedPropertySheetWidgetFactory
 *
 * @version 2.0
 * @since 2.0
 */
public class PropertySheetWidgetFactory
	extends FormWidgetFactory
{
	/**
	 * Creates a new <code>PropertySheetWidgetFactory</code>.
	 */
	public PropertySheetWidgetFactory() {
		this(new TabbedPropertySheetWidgetFactory());
	}

	/**
	 * Creates a new <code>PropertySheetWidgetFactory</code>.
	 */
	public PropertySheetWidgetFactory(TabbedPropertySheetWidgetFactory widgetFactory) {
		super(widgetFactory);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Composite createComposite(Composite parent) {
		return getWidgetFactory().createComposite(parent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Group createGroup(Composite parent, String title) {
		return getWidgetFactory().createGroup(parent, title);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return (TabbedPropertySheetWidgetFactory) super.getWidgetFactory();
	}
}
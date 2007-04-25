/*******************************************************************************
 *  Copyright (c) 2006 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public abstract class BaseJpaComposite extends BaseJpaController
	implements IJpaComposite
{
	private Composite composite;
	
	public BaseJpaComposite(Composite parent, int style, CommandStack theCommandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, style, theCommandStack, widgetFactory);
	}
	
	public BaseJpaComposite(Composite parent, CommandStack theCommandStack,TabbedPropertySheetWidgetFactory widgetFactory) {
		this(parent, SWT.NULL, theCommandStack, widgetFactory);
	}
	
	@Override
	protected void buildWidget(Composite parent, int style) {
		super.buildWidget(parent);
		this.composite = createComposite(parent, style);
		initializeLayout(this.composite);
	}
	
	protected Composite createComposite(Composite parent, int style) {
		return this.widgetFactory.createComposite(parent, style);
	}
	
	protected abstract void initializeLayout(Composite composite);

	public Control getControl() {
		return this.composite;
	}
	
}

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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public abstract class BaseJpaDetailsPage extends BaseJpaComposite 
	implements IJpaDetailsPage 
{
	private Composite control;

	public BaseJpaDetailsPage(
			Composite parent, CommandStack theCommandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, theCommandStack, widgetFactory);
	}

	public BaseJpaDetailsPage(
			Composite parent, int style, CommandStack theCommandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, style, theCommandStack, widgetFactory);
	}
	
	@Override
	//using this to get a Scroll bar on the JpaDetailsView
	protected Composite createComposite(Composite parent, int style) {
		ScrolledForm scrolledForm = this.widgetFactory.createScrolledForm(parent);
		//Nesting another composite because combos on the ScrolledForm didn't have a border
		scrolledForm.getBody().setLayout(new FillLayout());
		Composite composite = super.createComposite(scrolledForm.getBody(), style);
		this.control = scrolledForm;
		return composite;
	}

	public final void populate(IJpaContentNode contentNode) {
		super.populate(contentNode);
	}
	
	@Override
	protected final void doPopulate(EObject obj) {
		doPopulate((IJpaContentNode) obj);
	}
	
	protected abstract void doPopulate(IJpaContentNode contentNode);
	
	
	@Override
	public Control getControl() {
		return this.control;
	}

}

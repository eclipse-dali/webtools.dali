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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public abstract class BaseJpaController 
{
	protected CommandStack commandStack;
	
	protected TabbedPropertySheetWidgetFactory widgetFactory;

	//put in the populating flag to stop the circular population of the entity name combo
	//populateEntityNameCombo is calling select() which causes entityNameComboModified() to be called
	//this sets the name in the model which starts the circle over again. We should probably
	//short-circuit this differently, like in the emf model, keep the property change from being fired if 
	//a change did not actually occur - KFM
	private boolean populating;
	
	
	public BaseJpaController(Composite parent, CommandStack theCommandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super();
		this.widgetFactory = widgetFactory;
		buildWidget(parent);
		this.commandStack = theCommandStack;
	}
	
	public BaseJpaController(Composite parent, int style, CommandStack theCommandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super();
		this.widgetFactory = widgetFactory;
		buildWidget(parent, style);
		this.commandStack = theCommandStack;
	}
	
	
	/**
	 * Override this method if using the constructor without the style bit
	 */
	protected void buildWidget(Composite parent) {
		// no op
	}
	
	/**
	 * Override this method if using the constructor with the style bit
	 */
	protected void buildWidget(Composite parent, int style) {
		// no op
	}
	
	/**
	 * This method is called from outside when setting the object of interest
	 */
	public final void populate(EObject obj) {
		if (getControl().isDisposed()) {
			return;
		}
		this.populating = true;
		disengageListeners();
		doPopulate(obj);
		engageListeners();
		this.populating = false;
	}
	
	/**
	 * This method is called (perhaps internally) when this needs to repopulate 
	 * but the object of interest has not changed
	 */
	public final void populate() {
		if (getControl().isDisposed()) {
			return;
		}
		this.populating = true;
		doPopulate();
		this.populating = false;
	}
	
	protected abstract void doPopulate(EObject obj);
	
	protected abstract void doPopulate();
	
	protected abstract void engageListeners();
	
	protected abstract void disengageListeners();
	
	protected boolean isPopulating() {
		return this.populating;
	}
	
	public TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return this.widgetFactory;
	}
	
	public void dispose() {
		disengageListeners();
	}
	
	public abstract Control getControl();
}
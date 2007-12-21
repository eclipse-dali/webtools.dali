/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jpt.ui.internal.util.ControlAligner;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

@SuppressWarnings("nls")
public abstract class BaseJpaController<T>
{
	/**
	 * The aligner responsible to align the left controls.
	 */
	private ControlAligner leftControlAligner;

	//put in the populating flag to stop the circular population of the entity name combo
	//populateEntityNameCombo is calling select() which causes entityNameComboModified() to be called
	//this sets the name in the model which starts the circle over again. We should probably
	//short-circuit this differently, like in the emf model, keep the property change from being fired if
	//a change did not actually occur - KFM
	private boolean populating;

	/**
	 * The aligner responsible to align the left controls.
	 */
	private ControlAligner rightControlAligner;

	/**
	 * The subject of this controller.
	 */
	private PropertyValueModel<T> subjectHolder;

	/**
	 * The factory used to create various common widgets.
	 */
	protected TabbedPropertySheetWidgetFactory widgetFactory;

	/**
	 * Creates a new <code>BaseJpaController</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 */
	protected BaseJpaController(BaseJpaController<? extends T> parentController,
	                            Composite parent) {

		this(parentController.getSubjectHolder(),
		     parent,
		     parentController.getWidgetFactory());

		parentController.leftControlAligner .add(leftControlAligner);
		parentController.rightControlAligner.add(rightControlAligner);
	}

	/**
	 * Creates a new <code>BaseJpaController</code>.
	 *
	 * @param subjectHolder The holder of the <code>T</code>
	 * @param parent The parent container
	 * @param style
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected BaseJpaController(PropertyValueModel<? extends T> subjectHolder,
	                            Composite parent,
	                            int style,
	                            TabbedPropertySheetWidgetFactory widgetFactory) {

		super();
		initialize(subjectHolder, widgetFactory);
		buildWidget(parent, style);
	}

	/**
	 * Creates a new <code>BaseJpaController</code>.
	 *
	 * @param subjectHolder The holder of the <code>T</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 */
	protected BaseJpaController(PropertyValueModel<? extends T> subjectHolder,
	                            Composite parent,
	                            TabbedPropertySheetWidgetFactory widgetFactory) {

		this(subjectHolder, parent, SWT.NULL, widgetFactory);
	}

	protected final void addAlignLeft(BaseJpaController<?> container) {
		leftControlAligner.add(container.leftControlAligner);
	}

	protected final void addAlignLeft(Control control) {
		leftControlAligner.add(control);
	}

	protected final void addAlignRight(BaseJpaController<?> container) {
		rightControlAligner.add(container.rightControlAligner);
	}

	protected final void addAlignRight(Control control) {
		rightControlAligner.add(control);
	}

	protected final void addPaneForAlignment(BaseJpaController<?> container) {
		addAlignLeft(container);
		addAlignRight(container);
	}

	/**
	 * Creates a new container that will have the given center composite labeled
	 * with the given label text.
	 *
	 * @param labelText The text to label the main composite
	 * @param container The parent container
	 * @param centerComposite The main component
	 * @return The container of the label and the given center composite
	 */
	protected final Composite buildLabeledComposite(String labelText,
	                                                Composite container,
	                                                Composite centerComposite) {


		// Container for the label and main composite
		container = this.widgetFactory.createComposite(container);

		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth  = 0;
		layout.marginBottom = 0;
		layout.marginRight  = 1;
		container.setLayout(layout);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		container.setLayoutData(gridData);

		// Label
		Label label = this.widgetFactory.createLabel(container, labelText);
		leftControlAligner.add(label);

		// Center composite
		gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;

		centerComposite.setParent(container);
		centerComposite.setLayoutData(gridData);

		return container;
	}

	/**
	 * Override this method if using the constructor with the style bit
	 */
	protected void buildWidget(Composite parent, int style) {
		// no op
	}

	/**
	 * Uninstalls any listeners from the subject in order to stop being notified
	 * for changes made outside of this controllers.
	 */
	protected void disengageListeners() {
	}

	/**
	 * Notifies this controller is should dispose itself.
	 */
	public void dispose() {
		disengageListeners();
	}

	/**
	 * Requests this controller to populate its widgets with the subject's values.
	 */
	protected abstract void doPopulate();

	/**
	 * Installs the listeners on the subject in order to be notified from changes
	 * made outside of this controllers.
	 */
	protected void engageListeners() {
	}

	public abstract Control getControl();

	/**
	 * Returns the subject holder used by this controller.
	 *
	 * @return The holder of the subject
	 */
	protected final PropertyValueModel<T> getSubjectHolder() {
		return subjectHolder;
	}

	public TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return this.widgetFactory;
	}

	protected final IWorkbenchHelpSystem helpSystem() {
		return PlatformUI.getWorkbench().getHelpSystem();
	}

	/**
	 * Initializes this <code>BaseJpaController</code>.
	 */
	protected void initialize() {
	}

	/**
	 * Initializes this <code>BaseJpaController</code>.
	 *
	 * @param subjectHolder The holder of the <code>T</code>
	 * @param widgetFactory The factory used to create various widgets
	 */
	@SuppressWarnings("unchecked")
	private void initialize(PropertyValueModel<? extends T> subjectHolder,
	                        TabbedPropertySheetWidgetFactory widgetFactory)
	{
		Assert.isNotNull(subjectHolder, "The subject holder cannot be null");

		this.subjectHolder       = (PropertyValueModel<T>) subjectHolder;
		this.widgetFactory       = widgetFactory;
		this.leftControlAligner  = new ControlAligner();
		this.rightControlAligner = new ControlAligner();

		this.initialize();
	}

	protected final boolean isPopulating() {
		return this.populating;
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

		try {
			doPopulate();
		}
		finally {
			this.populating = false;
		}
	}

	protected final void removeAlignLeft(BaseJpaController<?> container) {
		leftControlAligner.remove(container.rightControlAligner);
	}

	protected final void removeAlignRight(BaseJpaController<?> container) {
		rightControlAligner.remove(container.rightControlAligner);
	}

	protected final void removeAlignRight(Control control) {
		rightControlAligner.remove(control);
	}

	protected final void removePaneForAlignment(BaseJpaController<?> container) {
		removeAlignLeft(container);
		removeAlignRight(container);
	}

	/**
	 * Returns the subject of this controller.
	 *
	 * @return The subject if this controller was not disposed; <code>null</code>
	 * if it was
	 */
	protected T subject() {
		return subjectHolder.value();
	}
}
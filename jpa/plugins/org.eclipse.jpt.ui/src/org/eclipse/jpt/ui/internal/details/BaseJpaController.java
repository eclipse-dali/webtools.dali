/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jpt.ui.internal.swt.BooleanButtonModelAdapter;
import org.eclipse.jpt.ui.internal.util.ControlAligner;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

@SuppressWarnings("nls")
public abstract class BaseJpaController<T>
{
	/**
	 * The aligner responsible to align the left controls.
	 */
	private ControlAligner leftControlAligner;

	/**
	 * Flag used to stop the circular population of widgets.
	 */
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
	 * The collection of registered sub-panes will be automatically notified
	 * when listeners need to be engaged or disengaged or when to populate its
	 * widgets.
	 */
	private Collection<BaseJpaController<?>> subPanes;

	/**
	 * The factory used to create various common widgets.
	 */
	private TabbedPropertySheetWidgetFactory widgetFactory;

	/**
	 * Creates a new <code>BaseJpaController</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 */
	protected BaseJpaController(BaseJpaController<? extends T> parentController,
	                            Composite parent) {

		this(parentController, parent, true);
	}

	/**
	 * Creates a new <code>BaseJpaController</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent controller;
	 * <code>false</code> to not align them
	 */
	protected BaseJpaController(BaseJpaController<? extends T> parentController,
	                            Composite parent,
	                            boolean automaticallyAlignWidgets) {

		this(parentController.getSubjectHolder(),
		     parent,
		     parentController.getWidgetFactory());

		if (automaticallyAlignWidgets) {
			parentController.leftControlAligner .add(leftControlAligner);
			parentController.rightControlAligner.add(rightControlAligner);
		}
	}

	/**
	 * Creates a new <code>BaseJpaController</code>.
	 *
	 * @param subjectHolder The holder of the <code>T</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected BaseJpaController(PropertyValueModel<? extends T> subjectHolder,
	                            Composite parent,
	                            TabbedPropertySheetWidgetFactory widgetFactory) {

		super();

		this.initialize(subjectHolder, widgetFactory);
		this.buildWidgets(parent);
	}

	/**
	 * Adds the given controller's widgets (those that were registered with its
	 * left <code>ControlAligner</code>) to this controller's left
	 * <code>ControlAligner</code> so that their width can be adjusted to have
	 * the width of the widest widget.
	 *
	 * @param controller The controller containing the widgets to add
	 */
	protected final void addAlignLeft(BaseJpaController<?> container) {
		leftControlAligner.add(container.leftControlAligner);
	}

	/**
	 * Adds the given control to the collection of widgets that have their width
	 * adjust with the width of the widest widget. The left alignment is usually
	 * used for labels.
	 *
	 * @param controller The controller to add
	 */
	protected final void addAlignLeft(Control control) {
		leftControlAligner.add(control);
	}

	/**
	 * Adds the given controller's widgets (those that were registered with its
	 * right <code>ControlAligner</code>) to this controller's right
	 * <code>ControlAligner</code> so that their width can be adjusted to have
	 * the width of the widest widget.
	 *
	 * @param controller The controller containing the widgets to add
	 */
	protected final void addAlignRight(BaseJpaController<?> container) {
		rightControlAligner.add(container.rightControlAligner);
	}

	/**
	 * Adds the given control to the collection of widgets that have their width
	 * adjust with the width of the widest widget. The left alignment is usually
	 * used for buttons.
	 *
	 * @param controller The controller to add
	 */
	protected final void addAlignRight(Control control) {
		rightControlAligner.add(control);
	}

	/**
	 * Adds the given controller's controls (those that were registered for
	 * alignment) from this controller.
	 *
	 * @param controller The controller containing the widgets to add for
	 * alignment
	 */
	protected final void addPaneForAlignment(BaseJpaController<?> container) {
		addAlignLeft(container);
		addAlignRight(container);
	}

	/**
	 * Creates a new check box using the given information.
	 *
	 * @param parent The parent container
	 * @param buttonText The button's text
	 * @param booleanHolder The holder of the selection state
	 */
	protected final Button buildCheckBox(Composite parent,
	                                     String buttonText,
	                                     WritablePropertyValueModel<Boolean> booleanHolder) {

		Button button = getWidgetFactory().createButton(
			parent,
			buttonText,
			SWT.CHECK
		);

		button.setLayoutData(new GridData());
		BooleanButtonModelAdapter.adapt(booleanHolder, button);
		return button;
	}

	/**
	 * Creates a new flat-style combo.
	 *
	 * @param container The parent container
	 * @return A new combo
	 */
	protected final CCombo buildCombo(Composite container) {
		return this.widgetFactory.createCCombo(container, SWT.FLAT);
	}

	/**
	 * Creates a new flat-style <code>ComboViewer</code>.
	 *
	 * @param container The parent container
	 * @param labelProvider The provider responsible to convert the combo's items
	 * into human readable strings
	 * @return A new <code>ComboViewer</code>
	 */
	protected final ComboViewer buildComboViewer(Composite container,
	                                             IBaseLabelProvider labelProvider) {

		CCombo combo = buildCombo(container);
		ComboViewer viewer = new ComboViewer(combo);
		viewer.setLabelProvider(labelProvider);
		return viewer;
	}

	/**
	 * Creates a new container that will have the given center control labeled
	 * with the given label.
	 *
	 * @param container The parent container
	 * @param label The label used to describe the center control
	 * @param centerControl The main widget
	 * @return The container of the label and the given center control
	 */
	protected final Composite buildLabeledComposite(Composite container,
	                                                Label label,
	                                                Control centerControl) {

		return this.buildLabeledComposite(
			container,
			label,
			centerControl,
			null
		);
	}

	/**
	 * Creates a new container that will have the given center control labeled
	 * with the given label.
	 *
	 * @param container The parent container
	 * @param label The label used to describe the center control
	 * @param centerControl The main widget
	 * @param helpId The topic help ID to be registered for the given center
	 * compositer
	 * @return The container of the label and the given center control
	 */
	protected final Composite buildLabeledComposite(Composite container,
	                                                Label label,
	                                                Control centerControl,
	                                                String helpId) {

		// Container for the label and main composite
		container = this.buildPane(container);

		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 5;
		layout.marginLeft   = 0;
		layout.marginBottom = 1; // Weird, it seems the right and bottom borders
		layout.marginRight  = 1; // are not painted if it's zero
		container.setLayout(layout);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		container.setLayoutData(gridData);

		// Label
		label.setParent(container);
		leftControlAligner.add(label);

		// Center composite
		gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		centerControl.setLayoutData(gridData);

		// Re-parent the center control to the new sub pane
		centerControl.setParent(container);

		// Register the help id for the center composite
		if (helpId != null) {
			helpSystem().setHelp(centerControl, helpId);
		}

		return container;
	}

	/**
	 * Creates a new container that will have the given center composite labeled
	 * with the given label text.
	 *
	 * @param container The parent container
	 * @param labelText The text to label the main composite
	 * @param centerControl The main widget
	 * @return The container of the label and the given center control
	 */
	protected final Composite buildLabeledComposite(Composite container,
	                                                String labelText,
	                                                Control centerControl) {


		return this.buildLabeledComposite(
			container,
			labelText,
			centerControl,
			null
		);
	}

	/**
	 * Creates a new container that will have the given center composite labeled
	 * with the given label text.
	 *
	 * @param container The parent container
	 * @param labelText The text to label the main composite
	 * @param centerControl The main widget
	 * @param helpId The topic help ID to be registered for the given center
	 * compositer
	 * @return The container of the label and the given center control
	 */
	protected final Composite buildLabeledComposite(Composite container,
	                                                String labelText,
	                                                Control centerControl,
	                                                String helpId) {

		Label label = this.widgetFactory.createLabel(container, labelText);

		return this.buildLabeledComposite(
			container,
			label,
			centerControl,
			helpId
		);
	}

	/**
	 * Creates a new container that will have a <code>Text</code> widget labeled
	 * with the given label text.
	 *
	 * @param container The parent container
	 * @param labelText The text to label the text field
	 * @return The container of the label and the given center control
	 */
	protected final Composite buildLabeledText(Composite container,
	                                           String labelText) {

		return this.buildLabeledComposite(
			container,
			labelText,
			this.buildText(container)
		);
	}

	/**
	 * Creates a new list and notify the given selection holder when the
	 * selection changes. If the selection count is different than one than the
	 * holder will receive <code>null</code>.
	 *
	 * @param container The parent container
	 * @param selectionHolder The holder of the unique selected item
	 * @return A new list widget
	 */
	protected final List buildList(Composite container,
	                               WritablePropertyValueModel<String> selectionHolder) {

		List list = this.widgetFactory.createList(
			container,
			SWT.FLAT | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL
		);

		list.addSelectionListener(buildSelectionListener(selectionHolder));

		GridData gridData = new GridData();
		gridData.horizontalAlignment     = GridData.FILL;
		gridData.verticalAlignment       = GridData.FILL;
		gridData.grabExcessVerticalSpace = true;
		list.setLayoutData(gridData);

		return list;
	}

	/**
	 * Creates a new container without specifying any layout manager.
	 *
	 * @param container The parent of the new container
	 * @return A new container
	 */
	protected final Composite buildPane(Composite parent) {
		return this.widgetFactory.createComposite(parent);
	}

	/**
	 * Creates a new container using the given layout manager.
	 *
	 * @param parent The parent of the new container
	 * @param layout The layout manager of the new container
	 * @return A new container
	 */
	protected final Composite buildPane(Composite parent, Layout layout) {
		Composite container = this.widgetFactory.createComposite(parent);
		container.setLayout(layout);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.verticalAlignment         = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;
		container.setLayoutData(gridData);

		return container;
	}

	/**
	 * Creates a new <code>Section</code> with flat style. A sub-pane is
	 * automatically added as its client which can be typed cast directly as a
	 * <code>Composite</code>.
	 *
	 * @param container The container of the new widget
	 * @param sectionText The text of the new section
	 * @return The newly created <code>Section</code>
	 */
	protected final Section buildSection(Composite container,
	                                     String sectionText) {

		return buildSection(
			container,
			sectionText,
			ExpandableComposite.TITLE_BAR
		);
	}

	/**
	 * Creates a new <code>Section</code> with flat style. A sub-pane is
	 * automatically added as its client which can be typed cast directly as a
	 * <code>Composite</code>.
	 *
	 * @param container The container of the new widget
	 * @param sectionText The text of the new section
	 * @param type The type of section to create
	 * @return The newly created <code>Section</code>
	 */
	private Section buildSection(Composite container,
	                             String sectionText,
	                             int type) {

		Section section = this.widgetFactory.createSection(
			container,
			SWT.FLAT |
			ExpandableComposite.TWISTIE |
			type
		);

		section.setText(sectionText);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		section.setLayoutData(gridData);

		Composite subPane = buildSubPane(section, 5, 15);
		section.setClient(subPane);

		return section;
	}

	private SelectionListener buildSelectionListener(final WritablePropertyValueModel<String> selectionHolder) {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				List list = (List) e.widget;
				String[] selectedItems = list.getSelection();
				if ((selectedItems == null) || (selectedItems.length != 1)) {
					selectionHolder.setValue(null);
				}
				else {
					selectionHolder.setValue(selectedItems[0]);
				}
			}
		};
	}

	/**
	 * Creates a new <code>Composite</code> used as a sub-pane.
	 *
	 * @param container The parent container
	 * @return A new <code>Composite</code> used as a sub-pane
	 */
	protected final Composite buildSubPane(Composite container) {
		return this.buildSubPane(container, 0);
	}

	/**
	 * Creates a new <code>Composite</code> used as a sub-pane.
	 *
	 * @param container The parent container
	 * @param topMargin The extra spacing to add at the top of the pane
	 * @return A new <code>Composite</code> used as a sub-pane
	 */
	protected final Composite buildSubPane(Composite container, int topMargin) {
		return this.buildSubPane(container, topMargin, 0);
	}

	/**
	 * Creates a new <code>Composite</code> used as a sub-pane.
	 *
	 * @param container The parent container
	 * @param topMargin The extra spacing to add at the top of the pane
	 * @param leftMargin The extra spacing to add to the left of the pane
	 * @return A new <code>Composite</code> used as a sub-pane
	 */
	protected final Composite buildSubPane(Composite container,
	                                       int topMargin,
	                                       int leftMargin) {

		return this.buildSubPane(container, topMargin, leftMargin, 0, 0);
	}

	/**
	 * Creates a new <code>Composite</code> used as a sub-pane, the new widget
	 * will have its layout and layout data already initialized, the layout will
	 * be a <code>GridLayout</code> with 1 column.
	 *
	 * @param container The parent container
	 * @param topMargin The extra spacing to add at the top of the pane
	 * @param leftMargin The extra spacing to add to the left of the pane
	 * @param bottomMargin The extra spacing to add at the bottom of the pane
	 * @param rightMargin The extra spacing to add to the right of the pane
	 * @return A new <code>Composite</code> used as a sub-pane
	 */
	protected final Composite buildSubPane(Composite container,
	                                       int topMargin,
	                                       int leftMargin,
	                                       int bottomMargin,
	                                       int rightMargin) {

		return this.buildSubPane(
			container,
			1,
			topMargin,
			leftMargin,
			bottomMargin,
			rightMargin);
	}

	/**
	 * Creates a new <code>Composite</code> used as a sub-pane, the new widget
	 * will have its layout and layout data already initialized, the layout will
	 * be a <code>GridLayout</code> with 1 column.
	 *
	 * @param container The parent container
	 * @param topMargin The extra spacing to add at the top of the pane
	 * @param leftMargin The extra spacing to add to the left of the pane
	 * @param bottomMargin The extra spacing to add at the bottom of the pane
	 * @param rightMargin The extra spacing to add to the right of the pane
	 * @return A new <code>Composite</code> used as a sub-pane
	 */
	protected final Composite buildSubPane(Composite container,
	                                       int columnCount,
	                                       int topMargin,
	                                       int leftMargin,
	                                       int bottomMargin,
	                                       int rightMargin) {

		Composite group = this.buildPane(container);

		GridLayout layout = new GridLayout(columnCount, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = topMargin;
		layout.marginLeft   = leftMargin;
		layout.marginBottom = bottomMargin;
		layout.marginRight  = rightMargin;
		group.setLayout(layout);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		group.setLayoutData(gridData);

		return group;
	}

	/**
	 * Creates a new <code>Section</code> with flat style. A sub-pane is
	 * automatically added as its client which can be typed cast directly as a
	 * <code>Composite</code>.
	 *
	 * @param container The container of the new widget
	 * @param sectionText The text of the new section
	 * @return The newly created <code>Section</code>
	 */
	protected final Section buildSubSection(Composite container,
	                                        String sectionText) {

		return this.buildSection(container, sectionText, SWT.NULL);
	}

	/**
	 * Creates a new <code>Text</code> widget, the widget is created with the
	 * flat-style look.
	 *
	 * @param container The parent container
	 * @return A new <code>Text</code> widget
	 */
	protected final Text buildText(Composite container) {
		return this.widgetFactory.createText(container, null);
	}

	/**
	 * Creates a new container with a titled border.
	 *
	 * @param title The text of the titled border
	 * @param container The parent container
	 * @return A new <code>Composite</code> with a titled border
	 */
	protected final Group buildTitledPane(String title, Composite container) {
		Group group = this.widgetFactory.createGroup(container, title);

		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 2;
		layout.marginLeft   = 5;
		layout.marginBottom = 9;
		layout.marginRight  = 5;
		group.setLayout(layout);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalIndent            = 10;
		group.setLayoutData(gridData);

		return group;
	}

	/**
	 * Creates the widgets for this controller.
	 *
	 * @param parent The parent container
	 */
	protected abstract void buildWidgets(Composite parent);

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
		if (!this.getControl().isDisposed()) {
			this.disengageListeners();

			for (BaseJpaController<?> subPane : this.subPanes) {
				subPane.dispose();
			}
		}
	}

	/**
	 * Requests this controller to populate its widgets with the subject's values.
	 */
	protected void doPopulate() {
	}

	/**
	 * Installs the listeners on the subject in order to be notified from changes
	 * made outside of this controllers.
	 */
	protected void engageListeners() {
		for (BaseJpaController<?> subPane : this.subPanes) {
			subPane.engageListeners();
		}
	}

	/**
	 * Returns this controller's widget.
	 *
	 * @return The main widget of this controller
	 */
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

	/**
	 * Returns the margin taken by a group box, which is the number of pixel the
	 * group box border and its margin takes before displaying its widgets plus
	 * 5 pixels since the widgets inside of the group box and the border should
	 * have that extra 5 pixels.
	 *
	 * @return The width taken by the group box border with its margin
	 */
	protected final int groupBoxMargin() {
		Group group = this.widgetFactory.createGroup(Display.getCurrent().getActiveShell(), "");
		Rectangle clientArea = group.getClientArea();
		group.dispose();
		return clientArea.x + 5;
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
	 * @param subjectHolder The holder of this controller's subject
	 * @param widgetFactory The factory used to create various widgets
	 */
	@SuppressWarnings("unchecked")
	private void initialize(PropertyValueModel<? extends T> subjectHolder,
	                        TabbedPropertySheetWidgetFactory widgetFactory)
	{
		Assert.isNotNull(subjectHolder, "The subject holder cannot be null");

		this.subjectHolder       = (PropertyValueModel<T>) subjectHolder;
		this.widgetFactory       = widgetFactory;
		this.subPanes            = new ArrayList<BaseJpaController<?>>();
		this.leftControlAligner  = new ControlAligner();
		this.rightControlAligner = new ControlAligner();

		this.initialize();
	}

	protected final boolean isPopulating() {
		return this.populating;
	}

	/**
	 * Notifies this pane to populate itself using the subject's information.
	 */
	public final void populate() {
		if (!getControl().isDisposed()) {
			this.repopulate();
			this.engageListeners();
		}
	}

	/**
	 * Registers another <code>BaseJpaController</code> with this one so it can
	 * be automatically notified about certain events such as engaging or
	 * disengaging the listeners, etc.
	 *
	 * @param subPane The sub-pane to register
	 */
	protected final void registerSubPane(BaseJpaController<?> subPane) {
		this.subPanes.add(subPane);
	}

	/**
	 * Removes the given controller's widgets (those that were registered with
	 * its left <code>ControlAligner</code>) from this controller's left
	 * <code>ControlAligner</code> so that their width will no longer be adjusted
	 * with the width of the widest widget.
	 *
	 * @param controller The controller containing the widgets to remove
	 */
	protected final void removeAlignLeft(BaseJpaController<?> controller) {
		leftControlAligner.remove(controller.leftControlAligner);
	}

	/**
	 * Removes the given control from the collection of widgets that are aligned
	 * to have the same width when they are shown to the left side of the 3
	 * widget colums.
	 *
	 * @param controller The controller to remove, its width will no longer be
	 * ajusted to be the width of the longest widget
	 */
	protected final void removeAlignLeft(Control control) {
		leftControlAligner.remove(control);
	}

	/**
	 * Removes the given controller's widgets (those that were registered with
	 * its right <code>ControlAligner</code>) from this controller's right
	 * <code>ControlAligner</code> so that their width will no longer be adjusted
	 * with the width of the widest widget.
	 *
	 * @param controller The controller containing the widgets to remove
	 */
	protected final void removeAlignRight(BaseJpaController<?> controller) {
		rightControlAligner.remove(controller.rightControlAligner);
	}

	/**
	 * Removes the given control from the collection of widgets that are aligned
	 * to have the same width when they are shown to the right side of the 3
	 * widget colums.
	 *
	 * @param controller The controller to remove, its width will no longer be
	 * ajusted to be the width of the longest widget
	 */
	protected final void removeAlignRight(Control control) {
		rightControlAligner.remove(control);
	}

	/**
	 * Removes the given controller's controls (those that were registered for
	 * alignment) from this controller.
	 *
	 * @param controller The controller containing the widgets that no longer
	 * requires their width adjusted with the width of the longest widget
	 */
	protected final void removePaneForAlignment(BaseJpaController<?> controller) {
		removeAlignLeft(controller);
		removeAlignRight(controller);
	}

	/**
	 * This method is called (perhaps internally) when this needs to repopulate
	 * but the object of interest has not changed.
	 */
	protected final void repopulate() {

		// Populate this pane
		try {
			setPopulating(true);
			doPopulate();
		}
		finally {
			setPopulating(false);
		}

		// Ask the sub-panes to populate themselves
		for (BaseJpaController<?> subPane : this.subPanes) {
			subPane.populate();
		}
	}

	protected final void setPopulating(boolean populating)
	{
		this.populating = populating;
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

	/**
	 * Unregisters the given <code>BaseJpaController</code> from this one so it
	 * can no longer be automatically notified about certain events such as
	 * engaging or disengaging the listeners, etc.
	 *
	 * @param subPane The sub-pane to unregister
	 */
	protected final void unregisterSubPane(BaseJpaController<?> subPane) {
		this.subPanes.remove(subPane);
	}
}
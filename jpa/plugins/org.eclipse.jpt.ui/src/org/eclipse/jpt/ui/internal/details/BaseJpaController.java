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
import org.eclipse.jpt.ui.internal.Tracing;
import org.eclipse.jpt.ui.internal.swt.BooleanButtonModelAdapter;
import org.eclipse.jpt.ui.internal.util.ControlAligner;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.node.Node;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
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
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * The abstract class used to create a pane. (TODO)
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public abstract class BaseJpaController<T extends Node>
{
	/**
	 *
	 */
	private PropertyChangeListener aspectChangeListener;

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
	 *
	 */
	private PropertyChangeListener subjectChangeListener;

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
	 * @category Constructor
	 */
	@SuppressWarnings("unused")
	private BaseJpaController() {
		super();
	}

	/**
	 * Creates a new <code>BaseJpaController</code>.
	 *
	 * @param parentController The parent controller of this one
	 * @param parent The parent container
	 *
	 * @category Constructor
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
	 *
	 * @category Constructor
	 */
	protected BaseJpaController(BaseJpaController<? extends T> parentController,
	                            Composite parent,
	                            boolean automaticallyAlignWidgets) {

		this(parentController,
		     parentController.getSubjectHolder(),
		     parent,
		     automaticallyAlignWidgets);
	}

	/**
	 * Creates a new <code>BaseJpaController</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 *
	 * @category Constructor
	 */
	protected BaseJpaController(BaseJpaController<?> parentController,
	                            PropertyValueModel<? extends T> subjectHolder,
	                            Composite parent) {

		this(parentController, subjectHolder, parent, true);
	}

	/**
	 * Creates a new <code>BaseJpaController</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent controller;
	 * <code>false</code> to not align them
	 *
	 * @category Constructor
	 */
	protected BaseJpaController(BaseJpaController<?> parentController,
	                            PropertyValueModel<? extends T> subjectHolder,
	                            Composite parent,
	                            boolean automaticallyAlignWidgets) {

		this(subjectHolder,
		     parent,
		     parentController.getWidgetFactory());

		initialize(parentController, automaticallyAlignWidgets);
	}

	/**
	 * Creates a new <code>BaseJpaController</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 *
	 * @category Constructor
	 */
	protected BaseJpaController(PropertyValueModel<? extends T> subjectHolder,
	                            Composite parent,
	                            TabbedPropertySheetWidgetFactory widgetFactory) {

		super();

		this.initialize(subjectHolder, widgetFactory);

		try {
			this.populating = true;
			this.buildWidgets(parent);
		}
		finally {
			this.populating = false;
		}
	}

	/**
	 * Adds the given controller's widgets (those that were registered with its
	 * left <code>ControlAligner</code>) to this controller's left
	 * <code>ControlAligner</code> so that their width can be adjusted to have
	 * the width of the widest widget.
	 *
	 * @param controller The controller containing the widgets to add
	 *
	 * @category Layout
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
	 *
	 * @category Layout
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
	 *
	 * @category Layout
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
	 *
	 * @category Layout
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
	 *
	 * @category Layout
	 */
	protected final void addPaneForAlignment(BaseJpaController<?> container) {
		addAlignLeft(container);
		addAlignRight(container);
	}

	private PropertyChangeListener buildAspectChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {

				if (getControl().isDisposed()) {
					return;
				}

				final String propertyName = e.propertyName();

				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						BaseJpaController.this.propertyChanged(propertyName);
					}
				});
			}
		};
	}

	/**
	 * Creates a new button using the given information.
	 *
	 * @param parent The parent container
	 * @param buttonText The button's text
	 * @param buttonAction The action to be invoked when the button is pressed
	 * @return The newly created <code>Button</code>
	 *
	 * @category Layout
	 */
	protected final Button buildButton(Composite container,
	                                   String text,
	                                   final Runnable buttonAction) {

		return this.buildButton(container, text, null, buttonAction);
	}

	/**
	 * Creates a new button using the given information.
	 *
	 * @param parent The parent container
	 * @param buttonText The button's text
	 * @param helpId The topic help ID to be registered for the new check box
	 * @param buttonAction The action to be invoked when the button is pressed
	 * @return The newly created <code>Button</code>
	 *
	 * @category Layout
	 */
	protected final Button buildButton(Composite container,
	                                   String text,
	                                   String helpId,
	                                   final Runnable buttonAction) {

		Button button = this.widgetFactory.createButton(container, text, SWT.NULL);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SWTUtil.asyncExec(buttonAction);
			}
		});

		if (helpId != null) {
			helpSystem().setHelp(button, helpId);
		}

		return button;
	}

	/**
	 * Creates a new check box using the given information.
	 *
	 * @param parent The parent container
	 * @param buttonText The button's text
	 * @param booleanHolder The holder of the selection state
	 * @return The newly created <code>Button</code>
	 *
	 * @category Layout
	 */
	protected final Button buildCheckBox(Composite parent,
	                                     String buttonText,
	                                     WritablePropertyValueModel<Boolean> booleanHolder) {

		return this.buildCheckBox(parent, buttonText, booleanHolder, null);
	}

	/**
	 * Creates a new check box using the given information.
	 *
	 * @param parent The parent container
	 * @param buttonText The button's text
	 * @param booleanHolder The holder of the selection state
	 * @param helpId The topic help ID to be registered for the new check box
	 * @return The newly created <code>Button</code>
	 *
	 * @category Layout
	 */
	protected final Button buildCheckBox(Composite parent,
	                                     String buttonText,
	                                     WritablePropertyValueModel<Boolean> booleanHolder,
	                                     String helpId) {

		return this.buildToggleButton(
			parent,
			buttonText,
			booleanHolder,
			helpId,
			SWT.CHECK
		);
	}

	/**
	 * Creates a new flat-style combo.
	 *
	 * @param container The parent container
	 * @return The newly created <code>Combo</code>
	 *
	 * @category Layout
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
	 * @return The newly created <code>ComboViewer</code>
	 *
	 * @category Layout
	 */
	protected final ComboViewer buildComboViewer(Composite container,
	                                             IBaseLabelProvider labelProvider) {

		CCombo combo = this.buildCombo(container);
		ComboViewer viewer = new ComboViewer(combo);
		viewer.setLabelProvider(labelProvider);
		return viewer;
	}

	/**
	 * Creates a new <code>Hyperlink</code> that will invoked the given
	 * <code>Runnable</code> when selected. The given action is always invoked
	 * from the UI thread.
	 *
	 * @param parent The parent container
	 * @param text The hyperlink's text
	 * @param hyperLinkAction The action to be invoked when the link was selected
	 * return The newly created <code>Hyperlink</code>
	 */
	protected final Hyperlink buildHyperLink(Composite parent,
	                                         String text,
	                                         final Runnable hyperLinkAction) {

		Hyperlink link = this.widgetFactory.createHyperlink(parent, text, SWT.FLAT);
		link.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				SWTUtil.asyncExec(hyperLinkAction);
			}
		});
		return link;
	}

	/**
	 * Creates a new container that will have the given center control labeled
	 * with the given label.
	 *
	 * @param container The parent container
	 * @param leftControl The widget shown to the left of the main widget
	 * @param centerControl The main widget
	 * @param rightControl The control shown to the right of the main widget
	 * @param helpId The topic help ID to be registered for the given center
	 * compositer
	 * @return The container of the label and the given center control
	 *
	 * @category Layout
	 */
	protected final Composite buildLabeledComposite(Composite container,
	                                                Control leftControl,
	                                                Control centerControl,
	                                                Control rightControl,
	                                                String helpId) {

		// Container for the label and main composite
		container = this.buildPane(container);

		GridLayout layout = new GridLayout(3, false);
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

		// Left control
		gridData = new GridData();
		gridData.horizontalAlignment       = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = false;
		leftControl.setLayoutData(gridData);

		// Re-parent the left control to the new sub pane
		leftControl.setParent(container);
		leftControlAligner.add(leftControl);

		// Center control
		gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		centerControl.setLayoutData(gridData);

		// Re-parent the center control to the new sub pane
		centerControl.setParent(container);

		// Register the help id for the center control
		if (helpId != null) {
			helpSystem().setHelp(centerControl, helpId);
		}

		// Right control
		if (rightControl == null) {
			// TODO: Find a way to create a spacer that doesn't always
			// have a size of (64, 64) (I tried with Composite and Canvas) ~PF
		}
		else {
			gridData = new GridData();
			gridData.horizontalAlignment       = GridData.FILL_HORIZONTAL;
			gridData.grabExcessHorizontalSpace = false;

			rightControl.setLayoutData(gridData);
			rightControl.setParent(container);

			// Re-parent the right control to the new sub pane
			rightControlAligner.add(rightControl);

			// Register the help id for the right control
			if (helpId != null) {
				helpSystem().setHelp(rightControl, helpId);
			}
		}

		return container;
	}

	/**
	 * Creates a new container that will have the given center control labeled
	 * with the given label.
	 *
	 * @param container The parent container
	 * @param label The label used to describe the center control
	 * @param centerControl The main widget
	 * @return The container of the label and the given center control
	 *
	 * @category Layout
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
	 * control
	 * @return The container of the label and the given center control
	 *
	 * @category Layout
	 */
	protected final Composite buildLabeledComposite(Composite container,
	                                                Label label,
	                                                Control centerControl,
	                                                String helpId) {

		return this.buildLabeledComposite(
			container,
			label,
			centerControl,
			null,
			helpId
		);
	}

	/**
	 * Creates a new container that will have the given center composite labeled
	 * with the given label text.
	 *
	 * @param container The parent container
	 * @param labelText The text to label the main composite
	 * @param centerControl The main widget
	 * @return The container of the label and the given center control
	 *
	 * @category Layout
	 */
	protected final Composite buildLabeledComposite(Composite container,
	                                                String labelText,
	                                                Control centerControl) {


		return this.buildLabeledComposite(
			container,
			labelText,
			centerControl,
			null,
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
	 * @param rightControl The control shown to the right of the main widget
	 * @return The container of the label and the given center control
	 *
	 * @category Layout
	 */
	protected final Composite buildLabeledComposite(Composite container,
	                                                String labelText,
	                                                Control centerControl,
	                                                Control rightControl) {


		return this.buildLabeledComposite(
			container,
			labelText,
			centerControl,
			rightControl
		);
	}

	/**
	 * Creates a new container that will have the given center composite labeled
	 * with the given label text.
	 *
	 * @param container The parent container
	 * @param labelText The text to label the main composite
	 * @param centerControl The main widget
	 * @param rightControl The control shown to the right of the main widget
	 * @param helpId The topic help ID to be registered for the given center
	 * compositer
	 * @return The container of the label and the given center control
	 *
	 * @category Layout
	 */
	protected final Composite buildLabeledComposite(Composite container,
	                                                String labelText,
	                                                Control centerControl,
	                                                Control rightCentrol,
	                                                String helpId) {

		Label label = this.widgetFactory.createLabel(container, labelText);

		return this.buildLabeledComposite(
			container,
			label,
			centerControl,
			rightCentrol,
			helpId
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
	 *
	 * @category Layout
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
	 *
	 * @category Layout
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
	 * @return The newly created <code>List</code>
	 *
	 * @category Layout
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
	 * @return The newly created <code>Composite</code>
	 *
	 * @category Layout
	 */
	protected final Composite buildPane(Composite parent) {
		return this.widgetFactory.createComposite(parent);
	}

	/**
	 * Creates a new container using the given layout manager.
	 *
	 * @param parent The parent of the new container
	 * @param layout The layout manager of the new container
	 * @return The newly created container
	 *
	 * @category Layout
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
	 * Creates a new push button using the given information.
	 *
	 * @param parent The parent container
	 * @param buttonText The button's text
	 * @param buttonAction The action to be invoked when the button is pressed
	 * @return The newly created <code>Button</code>
	 *
	 * @category Layout
	 */
	protected final Button buildPushButton(Composite parent,
	                                       String buttonText,
	                                       final Runnable buttonAction) {

		return this.buildPushButton(parent, buttonText, null, buttonAction);
	}

	/**
	 * Creates a new push button using the given information.
	 *
	 * @param parent The parent container
	 * @param buttonText The button's text
	 * @param buttonAction The action to be invoked when the button is pressed
	 * @param helpId The topic help ID to be registered for the new radio button
	 * @return The newly created <code>Button</code>
	 *
	 * @category Layout
	 */
	protected final Button buildPushButton(Composite parent,
	                                       String buttonText,
	                                       String helpId,
	                                       final Runnable buttonAction) {

		Button button = this.widgetFactory.createButton(
			parent,
			buttonText,
			SWT.PUSH
		);

		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SWTUtil.asyncExec(buttonAction);
			}
		});

		button.setLayoutData(new GridData());

		if (helpId != null) {
			helpSystem().setHelp(button, helpId);
		}

		return button;
	}

	/**
	 * Creates a new radio button using the given information.
	 *
	 * @param parent The parent container
	 * @param buttonText The button's text
	 * @param booleanHolder The holder of the selection state
	 * @return The newly created <code>Button</code>
	 *
	 * @category Layout
	 */
	protected final Button buildRadioButton(Composite parent,
	                                        String buttonText,
	                                        WritablePropertyValueModel<Boolean> booleanHolder) {

		return this.buildRadioButton(parent, buttonText, booleanHolder, null);
	}

	/**
	 * Creates a new check box using the given information.
	 *
	 * @param parent The parent container
	 * @param buttonText The button's text
	 * @param booleanHolder The holder of the selection state
	 * @param helpId The topic help ID to be registered for the new radio button
	 * @return The newly created <code>Button</code>
	 *
	 * @category Layout
	 */
	protected final Button buildRadioButton(Composite parent,
	                                        String buttonText,
	                                        WritablePropertyValueModel<Boolean> booleanHolder,
	                                        String helpId) {

		return this.buildToggleButton(
			parent,
			buttonText,
			booleanHolder,
			helpId,
			SWT.RADIO
		);
	}

	/**
	 * Creates a new <code>Section</code> with flat style. A sub-pane is
	 * automatically added as its client which can be typed cast directly as a
	 * <code>Composite</code>.
	 *
	 * @param container The container of the new widget
	 * @param sectionText The text of the new section
	 * @return The newly created <code>Section</code>
	 *
	 * @category Layout
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
	 *
	 * @category Layout
	 * TODO Should we simply return its client (Composite) since once the Section
	 * is created, we no longer need it - PF
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

	private PropertyChangeListener buildSubjectChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				BaseJpaController.this.subjectChanged(e);
			}
		};
	}

	/**
	 * Creates a new <code>Composite</code> used as a sub-pane.
	 *
	 * @param container The parent container
	 * @return The newly created <code>Composite</code> used as a sub-pane
	 *
	 * @category Layout
	 */
	protected final Composite buildSubPane(Composite container) {
		return this.buildSubPane(container, 0);
	}

	/**
	 * Creates a new <code>Composite</code> used as a sub-pane.
	 *
	 * @param container The parent container
	 * @param topMargin The extra spacing to add at the top of the pane
	 * @return The newly created <code>Composite</code> used as a sub-pane
	 *
	 * @category Layout
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
	 * @return The newly created <code>Composite</code> used as a sub-pane
	 *
	 * @category Layout
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
	 * @return The newly created <code>Composite</code> used as a sub-pane
	 *
	 * @category Layout
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
	 * @return The newly created <code>Composite</code> used as a sub-pane
	 *
	 * @category Layout
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
	 *
	 * @category Layout
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
	 * @return The newly created <code>Text</code> widget
	 *
	 * @category Layout
	 */
	protected final Text buildText(Composite container) {
		return this.widgetFactory.createText(container, null);
	}

	/**
	 * Creates a new <code>Text</code> widget, the widget is created with the
	 * flat-style look.
	 *
	 * @param container The parent container
	 * @param helpId The topic help ID to be registered for the new text
	 * @return The newly created <code>Text</code> widget
	 *
	 * @category Layout
	 */
	protected final Text buildText(Composite container, String helpId) {
		Text text = this.widgetFactory.createText(container, null);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		text.setLayoutData(gridData);

		if (helpId != null) {
			helpSystem().setHelp(text, helpId);
		}

		return text;
	}

	/**
	 * Creates a new container with a titled border.
	 *
	 * @param title The text of the titled border
	 * @param container The parent container
	 * @return The newly created <code>Composite</code> with a titled border
	 *
	 * @category Layout
	 */
	protected final Group buildTitledPane(Composite container, String title) {
		return this.buildTitledPane(container, title, null);
	}

	/**
	 * Creates a new container with a titled border.
	 *
	 * @param title The text of the titled border
	 * @param container The parent container
	 * @param helpId The topic help ID to be registered for the new group
	 * @return The newly created <code>Composite</code> with a titled border
	 *
	 * @category Layout
	 */
	protected final Group buildTitledPane(Composite container,
	                                      String title,
	                                      String helpId) {

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
		gridData.verticalIndent            = 0;
		group.setLayoutData(gridData);

		if (helpId != null) {
			helpSystem().setHelp(group, helpId);
		}

		return group;
	}

	/**
	 * Creates a new toggle button (radio button or check box) using the given
	 * information.
	 *
	 * @param parent The parent container
	 * @param buttonText The button's text
	 * @param booleanHolder The holder of the selection state
	 * @param helpId The topic help ID to be registered for the new button
	 * @return The newly created <code>Button</code>
	 *
	 * @category Layout
	 */
	private Button buildToggleButton(Composite parent,
	                                 String buttonText,
	                                 WritablePropertyValueModel<Boolean> booleanHolder,
	                                 String helpId,
	                                 int toggleButtonType) {

		Button button = this.widgetFactory.createButton(
			parent,
			buttonText,
			toggleButtonType
		);

		button.setLayoutData(new GridData());
		BooleanButtonModelAdapter.adapt(booleanHolder, button);

		if (helpId != null) {
			helpSystem().setHelp(button, helpId);
		}

		return button;
	}

	/**
	 * Creates the widgets for this controller.
	 *
	 * @param parent The parent container
	 *
	 * @category Layout
	 */
	protected abstract void buildWidgets(Composite parent);

	/**
	 * Uninstalls any listeners from the subject in order to stop being notified
	 * for changes made outside of this controllers.
	 *
	 * @category Populate
	 */
	protected void disengageListeners() {

		this.log("   ->disengageListeners()");

		this.subjectHolder.removePropertyChangeListener(
			PropertyValueModel.VALUE,
			this.subjectChangeListener
		);

		this.disengageListeners(this.subject());

		for (BaseJpaController<?> subPane : this.subPanes) {
			subPane.disengageListeners();
		}
	}

	/**
	 * Removes any property change listeners from the given subject.
	 *
	 * @param subject The old subject
	 *
	 * @category Populate
	 */
	protected void disengageListeners(T subject) {
		if (subject != null) {
			this.log("   ->disengageListeners() from " + subject.displayString());

			for (String propertyName : this.propertyNames()) {
				subject.removePropertyChangeListener(propertyName, this.aspectChangeListener);
			}
		}
	}

	/**
	 * Notifies this controller is should dispose itself.
	 *
	 * @category Populate
	 */
	public void dispose() {
		if (!this.getControl().isDisposed()) {
			this.log("dispose()");
			this.disengageListeners();

			for (BaseJpaController<?> subPane : this.subPanes) {
				subPane.dispose();
			}
		}
	}

	/**
	 * Requests this controller to populate its widgets with the subject's values.
	 *
	 * @category Populate
	 */
	protected void doPopulate() {
		this.log("   ->doPopulate()");
	}

	/**
	 * Changes the enablement state of the widgets of this pane.
	 *
	 * @param enabled <code>true</code> to enable the widgets or <code>false</code>
	 * to disable them
	 *
	 * @category Layout
	 */
	public void enableWidgets(boolean enabled) {
		for (BaseJpaController<?> subPane : this.subPanes) {
			subPane.enableWidgets(enabled);
		}
	}

	/**
	 * Installs the listeners on the subject in order to be notified from changes
	 * made outside of this controllers.
	 *
	 * @category Populate
	 */
	protected void engageListeners() {

		this.log("   ->engageListeners()");

		this.subjectHolder.addPropertyChangeListener(
			PropertyValueModel.VALUE,
			this.subjectChangeListener
		);

		this.engageListeners(this.subject());

		for (BaseJpaController<?> subPane : this.subPanes) {
			subPane.engageListeners();
		}
	}

	/**
	 * TODO
	 *
	 * @param subject
	 *
	 * @category Populate
	 */
	protected void engageListeners(T subject) {
		if (subject != null) {

			this.log("   ->engageListeners() on " + subject.displayString());

			for (String propertyName : this.propertyNames()) {
				subject.addPropertyChangeListener(propertyName, this.aspectChangeListener);
			}
		}
	}

	/**
	 * Returns this controller's widget.
	 *
	 * @return The main widget of this controller
	 *
	 * @category Layout
	 */
	public abstract Control getControl();

	/**
	 * Returns the subject holder used by this controller.
	 *
	 * @return The holder of the subject
	 *
	 * @category Populate
	 */
	protected final PropertyValueModel<T> getSubjectHolder() {
		return subjectHolder;
	}

	/**
	 * Returns
	 *
	 * @return
	 *
	 * @category Layout
	 */
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
	 *
	 * @category Layout
	 */
	protected final int groupBoxMargin() {
		Group group = this.widgetFactory.createGroup(Display.getCurrent().getActiveShell(), "");
		Rectangle clientArea = group.getClientArea();
		group.dispose();
		return clientArea.x + 5;
	}

	/**
	 * Returns the helps system.
	 *
	 * @return The platform's help system
	 *
	 * @category Helper
	 */
	protected final IWorkbenchHelpSystem helpSystem() {
		return PlatformUI.getWorkbench().getHelpSystem();
	}

	/**
	 * Initializes this <code>BaseJpaController</code>.
	 *
	 * @category Initialization
	 */
	protected void initialize() {
	}

	/**
	 * Registers this controller with the parent controller.
	 *
	 * @param parentController The parent controller
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent controller;
	 * <code>false</code> to not align them
	 *
	 * @category Initialization
	 */
	private void initialize(BaseJpaController<?> parentController,
	                        boolean automaticallyAlignWidgets) {

		// Register this pane with the parent pane, it will call the methods
		// automatically (engageListeners(), disengageListeners(), populate(),
		// dispose(), etc)
		parentController.registerSubPane(this);

		// Align the left and right controls with the controls from the parent
		// controller
		if (automaticallyAlignWidgets) {
			parentController.leftControlAligner .add(leftControlAligner);
			parentController.rightControlAligner.add(rightControlAligner);
		}
	}

	/**
	 * Initializes this <code>BaseJpaController</code>.
	 *
	 * @param subjectHolder The holder of this controller's subject
	 * @param widgetFactory The factory used to create various widgets
	 *
	 * @category Initialization
	 */
	@SuppressWarnings("unchecked")
	private void initialize(PropertyValueModel<? extends T> subjectHolder,
	                        TabbedPropertySheetWidgetFactory widgetFactory)
	{
		Assert.isNotNull(subjectHolder, "The subject holder cannot be null");

		this.subjectHolder         = (PropertyValueModel<T>) subjectHolder;
		this.widgetFactory         = widgetFactory;
		this.subPanes              = new ArrayList<BaseJpaController<?>>();
		this.leftControlAligner    = new ControlAligner();
		this.rightControlAligner   = new ControlAligner();
		this.subjectChangeListener = this.buildSubjectChangeListener();
		this.aspectChangeListener  = this.buildAspectChangeListener();

		this.initialize();
	}

	/**
	 * Determines whether
	 *
	 * @return
	 *
	 * @category Populate
	 */
	protected final boolean isPopulating() {
		return this.populating;
	}

	/**
	 * Logs the given message if the <code>Tracing.DEBUG_LAYOUT</code> is enabled.
	 *
	 * @param message The logging message
	 */
	private void log(String message) {

		if (Tracing.booleanDebugOption(Tracing.DEBUG_LAYOUT)) {

			Class<?> thisClass = getClass();
			String className = ClassTools.shortNameFor(thisClass);

			if (thisClass.isAnonymousClass()) {
				className = className.substring(0, className.indexOf('$'));
				className += "->" + ClassTools.shortNameFor(thisClass.getSuperclass());
			}

			Tracing.log(className + ": " + message);
		}
	}

	/**
	 * Notifies this pane to populate itself using the subject's information.
	 *
	 * @category Populate
	 */
	public final void populate() {
		if (!getControl().isDisposed()) {
			this.log("populate()");
			this.repopulate();
			this.engageListeners();
		}
	}

	/**
	 * Notifies the subject's property associated with the given property name
	 * has changed.
	 *
	 * @param propertyName The property name associated with the property change
	 *
	 * @category Populate
	 */
	protected void propertyChanged(String propertyName) {
	}

	/**
	 * Returns the list of names to listen for properties changing from the
	 * subject.
	 *
	 * @return A non-<code>null</code> list of property names
	 *
	 * @category Populate
	 */
	protected String[] propertyNames() {
		return new String[0];
	}

	/**
	 * Registers another <code>BaseJpaController</code> with this one so it can
	 * be automatically notified about certain events such as engaging or
	 * disengaging the listeners, etc.
	 *
	 * @param subPane The sub-pane to register
	 *
	 * @category Controller
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
	 *
	 * @category Layout
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
	 *
	 * @category Layout
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
	 *
	 * @category Layout
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
	 *
	 * @category Layout
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
	 *
	 * @category Layout
	 */
	protected final void removePaneForAlignment(BaseJpaController<?> controller) {
		removeAlignLeft(controller);
		removeAlignRight(controller);
	}

	/**
	 * This method is called (perhaps internally) when this needs to repopulate
	 * but the object of interest has not changed.
	 *
	 * @category Populate
	 */
	protected final void repopulate() {

		this.log("   ->repopulate()");

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

	/**
	 * Sets (TODO)
	 *
	 * @param populating
	 *
	 * @category Populate
	 */
	protected final void setPopulating(boolean populating) {
		this.populating = populating;
	}

	/**
	 * Returns the subject of this controller.
	 *
	 * @return The subject if this controller was not disposed; <code>null</code>
	 * if it was
	 *
	 * @category Populate
	 */
	protected T subject() {
		return subjectHolder.value();
	}

	/**
	 * TODO
	 *
	 * @param e
	 *
	 * @category Populate
	 */
	@SuppressWarnings("unchecked")
	private void subjectChanged(PropertyChangeEvent e) {
		this.log("subjectChanged()");
		this.disengageListeners((T) e.oldValue());
		this.repopulate();
		this.engageListeners((T) e.newValue());
	}

	/**
	 * Unregisters the given <code>BaseJpaController</code> from this one so it
	 * can no longer be automatically notified about certain events such as
	 * engaging or disengaging the listeners, etc.
	 *
	 * @param subPane The sub-pane to unregister
	 *
	 * @category Controller
	 */
	protected final void unregisterSubPane(BaseJpaController<?> subPane) {
		this.subPanes.remove(subPane);
	}
}
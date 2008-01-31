/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jpt.ui.internal.Tracing;
import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.ui.internal.swt.BooleanButtonModelAdapter;
import org.eclipse.jpt.ui.internal.swt.TriStateBooleanButtonModelAdapter;
import org.eclipse.jpt.ui.internal.util.ControlAligner;
import org.eclipse.jpt.ui.internal.util.LabeledControlUpdater;
import org.eclipse.jpt.ui.internal.util.LabeledTableItem;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.help.IWorkbenchHelpSystem;

/**
 * The abstract definition of a pane which holds onto a <code>PropertyValueModel</code>
 * that contains the subject of this pane.
 * <p>
 * It also contains convenience methods for building buttons, labels, check
 * boxes, and radio buttons, etc.
 * <p>
 * It is possible to easily listen to any property changes coming from the
 * subject, {@link #addPropertyNames(Collection)} is specify which properties
 * are of interest and {@link #propertyChanged(String)} is used to notify the
 * pane when the property has changed.
 *
 * @see AbstractFormPane
 * @see AbstractDialogPane
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class AbstractPane<T extends Model>
{
	/**
	 * The listener registered with the subject in order to be notified when a
	 * property has changed, the property names are determined by
	 * {@link #propertyNames()}.
	 */
	private PropertyChangeListener aspectChangeListener;

	/**
	 * The container of this composite.
	 */
	private Composite container;

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
	 * This listener is registered with the subject holder in order to
	 * automatically repopulate this pane with the new subject.
	 */
	private PropertyChangeListener subjectChangeListener;

	/**
	 * The subject of this pane.
	 */
	private PropertyValueModel<T> subjectHolder;

	/**
	 * The collection of registered sub-panes will be automatically notified
	 * when listeners need to be engaged or disengaged or when to populate its
	 * widgets.
	 */
	private Collection<AbstractPane<?>> subPanes;

	/**
	 * The factory used to create various common widgets.
	 */
	private IWidgetFactory widgetFactory;

	/**
	 * Creates a new <code>AbstractSubjectPane</code>.
	 *
	 * @category Constructor
	 */
	@SuppressWarnings("unused")
	private AbstractPane() {
		super();
	}

	/**
	 * Creates a new <code>AbstractSubjectPane</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 *
	 * @category Constructor
	 */
	protected AbstractPane(AbstractPane<? extends T> parentPane,
	                       Composite parent) {

		this(parentPane, parent, true);
	}

	/**
	 * Creates a new <code>AbstractSubjectPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent pane;
	 * <code>false</code> to not align them
	 *
	 * @category Constructor
	 */
	protected AbstractPane(AbstractPane<? extends T> parentPane,
	                       Composite parent,
	                       boolean automaticallyAlignWidgets) {

		this(parentPane,
		     parentPane.getSubjectHolder(),
		     parent,
		     automaticallyAlignWidgets);
	}

	/**
	 * Creates a new <code>AbstractSubjectPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 *
	 * @category Constructor
	 */
	protected AbstractPane(AbstractPane<?> parentPane,
	                       PropertyValueModel<? extends T> subjectHolder,
	                       Composite parent) {

		this(parentPane, subjectHolder, parent, true);
	}

	/**
	 * Creates a new <code>AbstractSubjectPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent pane;
	 * <code>false</code> to not align them
	 *
	 * @category Constructor
	 */
	protected AbstractPane(AbstractPane<?> parentPane,
	                       PropertyValueModel<? extends T> subjectHolder,
	                       Composite parent,
	                       boolean automaticallyAlignWidgets) {

		this(subjectHolder,
		     parent,
		     parentPane.getWidgetFactory());

		initialize(parentPane, automaticallyAlignWidgets);
	}

	/**
	 * Creates a new <code>AbstractSubjectPane</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 *
	 * @category Constructor
	 */
	protected AbstractPane(PropertyValueModel<? extends T> subjectHolder,
	                       Composite parent,
	                       IWidgetFactory widgetFactory) {

		super();

		this.initialize(subjectHolder, widgetFactory);

		try {
			this.populating = true;

			this.container = this.buildContainer(parent);
			this.initializeLayout(this.container);
		}
		finally {
			this.populating = false;
		}
	}

	/**
	 * Adds the given pane's widgets (those that were registered with its
	 * left <code>ControlAligner</code>) to this pane's left
	 * <code>ControlAligner</code> so that their width can be adjusted to have
	 * the width of the widest widget.
	 *
	 * @param pane The pane containing the widgets to add
	 *
	 * @category Layout
	 */
	protected final void addAlignLeft(AbstractPane<?> container) {
		leftControlAligner.add(container.leftControlAligner);
	}

	/**
	 * Adds the given control to the collection of widgets that have their width
	 * adjust with the width of the widest widget. The left alignment is usually
	 * used for labels.
	 *
	 * @param pane The pane to add
	 *
	 * @category Layout
	 */
	protected final void addAlignLeft(Control control) {
		leftControlAligner.add(control);
	}

	/**
	 * Adds the given pane's widgets (those that were registered with its
	 * right <code>ControlAligner</code>) to this pane's right
	 * <code>ControlAligner</code> so that their width can be adjusted to have
	 * the width of the widest widget.
	 *
	 * @param pane The pane containing the widgets to add
	 *
	 * @category Layout
	 */
	protected final void addAlignRight(AbstractPane<?> container) {
		rightControlAligner.add(container.rightControlAligner);
	}

	/**
	 * Adds the given control to the collection of widgets that have their width
	 * adjust with the width of the widest widget. The left alignment is usually
	 * used for buttons.
	 *
	 * @param pane The pane to add
	 *
	 * @category Layout
	 */
	protected final void addAlignRight(Control control) {
		rightControlAligner.add(control);
	}

	/**
	 * Adds the given pane's controls (those that were registered for
	 * alignment) from this pane.
	 *
	 * @param pane The pane containing the widgets to add for
	 * alignment
	 *
	 * @category Layout
	 */
	protected final void addPaneForAlignment(AbstractPane<?> container) {
		addAlignLeft(container);
		addAlignRight(container);
	}

	/**
	 * Adds any property names to the given collection in order to be notified
	 * when the actual property changes in the subject.
	 *
	 * @param propertyNames The collection of property names to register with the
	 * subject
	 */
	protected void addPropertyNames(Collection<String> propertyNames) {
	}

	private PropertyChangeListener buildAspectChangeListener() {
		return new SWTPropertyChangeListenerWrapper(buildAspectChangeListener_());
	}
	
	private PropertyChangeListener buildAspectChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				if (container.isDisposed()) {
					return;
				}
				AbstractPane.this.propertyChanged(e.propertyName());
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

		container = this.fixBorderNotPainted(container);
		CCombo combo = this.widgetFactory.createCombo(container);

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		combo.setLayoutData(gridData);

		return combo;
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

	protected Composite buildContainer(Composite parent) {
		Composite container = this.buildPane(parent);

		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 0;
		layout.marginLeft   = 0;
		layout.marginBottom = 0;
		layout.marginRight  = 0;
		container.setLayout(layout);

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		container.setLayoutData(gridData);

		return container;
	}

	private PropertyChangeListener buildExpandedStateChangeListener(final Section section) {
		return new SWTPropertyChangeListenerWrapper(buildExpandedStateChangeListener_(section));
	}
	
	private PropertyChangeListener buildExpandedStateChangeListener_(final Section section) {
		return new PropertyChangeListener() {
			public void propertyChanged(final PropertyChangeEvent e) {
				Boolean value = (Boolean) e.newValue();
				if (value == null) {
					value = Boolean.TRUE;
				}
				section.setExpanded(value);
			}
		};
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

		Hyperlink link = this.widgetFactory.createHyperlink(parent, text);
		link.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				SWTUtil.asyncExec(hyperLinkAction);
			}
		});
		return link;
	}

	/**
	 * Creates a new lable using the given information.
	 *
	 * @param parent The parent container
	 * @param labelText The label's text
	 *
	 * @category Layout
	 */
	protected final Label buildLabel(Composite container,
	                                 String labelText) {

		return this.widgetFactory.createLabel(container, labelText);
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
		container = this.buildSubPane(container, 3, 5, 0, 0, 0);

		// Left control
		GridData gridData = new GridData();
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

		Label label = this.buildLabel(container, labelText);

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

		Label label = this.buildLabel(shell(), labelText);

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
	 * @return The newly created <code>List</code>
	 *
	 * @category Layout
	 */
	protected final List buildList(Composite container) {

		return this.buildList(container, (String) null);
	}

	/**
	 * Creates a new list and notify the given selection holder when the
	 * selection changes. If the selection count is different than one than the
	 * holder will receive <code>null</code>.
	 *
	 * @param container The parent container
	 * @param helpId The topic help ID to be registered for the new radio button
	 * @return The newly created <code>List</code>
	 *
	 * @category Layout
	 */
	protected final List buildList(Composite container, String helpId) {

		return this.buildList(
			container,
			new SimplePropertyValueModel<String>(),
			helpId
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

		return this.buildList(container, selectionHolder, null);
	}

	/**
	 * Creates a new list and notify the given selection holder when the
	 * selection changes. If the selection count is different than one than the
	 * holder will receive <code>null</code>.
	 *
	 * @param container The parent container
	 * @param selectionHolder The holder of the unique selected item
	 * @param helpId The topic help ID to be registered for the new radio button
	 * @return The newly created <code>List</code>
	 *
	 * @category Layout
	 */
	protected final List buildList(Composite container,
	                               WritablePropertyValueModel<String> selectionHolder,
	                               String helpId) {

		List list = this.widgetFactory.createList(
			container,
			SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI
		);

		list.addSelectionListener(buildSelectionListener(selectionHolder));

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.verticalAlignment         = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;
		list.setLayoutData(gridData);

		if (helpId != null) {
			helpSystem().setHelp(list, helpId);
		}

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
	 * automatically added as its client and is the returned <code>Composite</code>.
	 *
	 * @param container The container of the new widget
	 * @param sectionText The text of the new section
	 * @return The <code>Section</code>'s sub-pane
	 *
	 * @category Layout
	 */
	protected final Composite buildSection(Composite container,
	                                       String sectionText) {

		return this.buildSection(
			container,
			sectionText,
			ExpandableComposite.TITLE_BAR
		);
	}

	/**
	 * Creates a new <code>Section</code> with flat style.  A sub-pane is
	 * automatically added as its client and is the returned <code>Composite</code>.
	 *
	 * @param container The container of the new widget
	 * @param sectionText The text of the new section
	 * @param type The type of section to create
	 * @return The <code>Section</code>'s sub-pane
	 *
	 * @category Layout
	 */
	private Composite buildSection(Composite container,
	                               String sectionText,
	                               int type) {

		return this.buildSection(
			container,
			sectionText,
			type,
			new SimplePropertyValueModel<Boolean>(true)
		);
	}

	/**
	 * Creates a new <code>Section</code> with flat style.  A sub-pane is
	 * automatically added as its client and is the returned <code>Composite</code>.
	 *
	 * @param container The container of the new widget
	 * @param sectionText The text of the new section
	 * @param type The type of section to create
	 * @param expandedStateHolder The holder of the boolean that will dictate
	 * when to expand or collapse the section
	 * @return The <code>Section</code>'s sub-pane
	 *
	 * @category Layout
	 */
	private Composite buildSection(Composite container,
	                               String sectionText,
	                               int type,
	                               PropertyValueModel<Boolean> expandedStateHolder) {

		Section section = this.widgetFactory.createSection(
			container,
			ExpandableComposite.TWISTIE | type
		);

		section.setText(sectionText);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		section.setLayoutData(gridData);

		Composite subPane = this.buildSubPane(section, 5, 0);
		section.setClient(subPane);

		expandedStateHolder.addPropertyChangeListener(
			PropertyValueModel.VALUE,
			buildExpandedStateChangeListener(section)
		);

		section.setExpanded(
			expandedStateHolder.value() != null ? expandedStateHolder.value() : true
		);

		return subPane;
	}

	/**
	 * Creates a new <code>Section</code> with flat style. A sub-pane is
	 * automatically added as its client and is the returned <code>Composite</code>.
	 *
	 * @param container The container of the new widget
	 * @param sectionText The text of the new section
	 * @param expandedStateHolder The holder of the boolean that will dictate
	 * when to expand or collapse the section
	 * @return The <code>Section</code>'s sub-pane
	 *
	 * @category Layout
	 */
	protected final Composite buildSection(Composite container,
	                                       String sectionText,
	                                       PropertyValueModel<Boolean> expandedStateHolder) {

		return this.buildSection(
			container,
			sectionText,
			ExpandableComposite.TITLE_BAR,
			expandedStateHolder
		);
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
		return new SWTPropertyChangeListenerWrapper(this.buildSubjectChangeListener_());
	}
	
	private PropertyChangeListener buildSubjectChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				AbstractPane.this.subjectChanged(e);
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

		GridLayout layout = new GridLayout(columnCount, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = topMargin;
		layout.marginLeft   = leftMargin;
		layout.marginBottom = bottomMargin;
		layout.marginRight  = rightMargin;

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;

		container = this.buildPane(container, layout);
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
	 * @return The <code>Section</code>'s sub-pane
	 *
	 * @category Layout
	 */
	protected final Composite buildSubSection(Composite container,
	                                          String sectionText) {

		return this.buildSubSection(
			container,
			sectionText,
			new SimplePropertyValueModel<Boolean>(true)
		);
	}

	/**
	 * Creates a new <code>Section</code> with flat style. A sub-pane is
	 * automatically added as its client which can be typed cast directly as a
	 * <code>Composite</code>.
	 *
	 * @param container The container of the new widget
	 * @param sectionText The text of the new section
	 * @param expandedStateHolder The holder of the boolean that will dictate
	 * when to expand or collapse the section
	 * @return The <code>Section</code>'s sub-pane
	 *
	 * @category Layout
	 */
	protected final Composite buildSubSection(Composite container,
	                                          String sectionText,
	                                          PropertyValueModel<Boolean> expandedStateHolder) {

		return this.buildSection(
			container,
			sectionText,
			SWT.NULL,
			expandedStateHolder
		);
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
		return this.widgetFactory.createText(container);
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
		Text text = this.widgetFactory.createText(container);

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
	 * Creates a new check box that can have 3 selection states (selected,
	 * unselected and partially selected.
	 *
	 * @param parent The parent container
	 * @param text The button's text
	 * @param booleanHolder The holder of the boolean value where <code>null</code>
	 * means partially selected
	 * @return The newly created <code>TriStateCheckBox</code>
	 */
	protected final TriStateCheckBox buildTriStateCheckBox(Composite parent,
	                                                       String text,
	                                                       WritablePropertyValueModel<Boolean> booleanHolder) {

		return this.buildTriStateCheckBox(container, text, booleanHolder, null);
	}

	/**
	 * Creates a new check box that can have 3 selection states (selected,
	 * unselected and partially selected.
	 *
	 * @param parent The parent container
	 * @param text The button's text
	 * @param booleanHolder The holder of the boolean value where <code>null</code>
	 * means partially selected
	 * @param helpId The topic help ID to be registered for the new check box
	 * @return The newly created <code>TriStateCheckBox</code>
	 */
	protected final TriStateCheckBox buildTriStateCheckBox(Composite parent,
	                                                       String text,
	                                                       WritablePropertyValueModel<Boolean> booleanHolder,
	                                                       String helpId) {

		TriStateCheckBox checkBox = new TriStateCheckBox(parent);
		checkBox.setText(text);

		TriStateBooleanButtonModelAdapter.adapt(
			booleanHolder,
			checkBox
		);

		if (helpId != null) {
			helpSystem().setHelp(checkBox.getControl(), helpId);
		}

		return checkBox;
	}

	/**
	 * Creates a new check box that can have 3 selection states (selected,
	 * unselected and partially selected.
	 *
	 * @param parent The parent container
	 * @param text The button's text
	 * @param booleanHolder The holder of the boolean value where <code>null</code>
	 * means partially selected
	 * @param stringHolder The holder of the string to put in parenthesis after
	 * the check box's text when it is partially selected
	 * @return The newly created <code>TriStateCheckBox</code>
	 */
	protected final TriStateCheckBox buildTriStateCheckBoxWithDefault(Composite parent,
	                                                                  String text,
	                                                                  WritablePropertyValueModel<Boolean> booleanHolder,
	                                                                  PropertyValueModel<String> stringHolder) {

		return this.buildTriStateCheckBoxWithDefault(
			container,
			text,
			booleanHolder,
			stringHolder,
			null
		);
	}

	/**
	 * Creates a new check box that can have 3 selection states (selected,
	 * unselected and partially selected.
	 *
	 * @param parent The parent container
	 * @param text The button's text
	 * @param booleanHolder The holder of the boolean value where <code>null</code>
	 * means partially selected
	 * @param stringHolder The holder of the string to put in parenthesis after
	 * the check box's text when it is partially selected
	 * @param helpId The topic help ID to be registered for the new check box
	 * @return The newly created <code>TriStateCheckBox</code>
	 */
	protected final TriStateCheckBox buildTriStateCheckBoxWithDefault(Composite parent,
	                                                                  String text,
	                                                                  WritablePropertyValueModel<Boolean> booleanHolder,
	                                                                  PropertyValueModel<String> stringHolder,
	                                                                  String helpId) {

		TriStateCheckBox checkBox = this.buildTriStateCheckBox(
			parent,
			text,
			booleanHolder,
			helpId
		);

		new LabeledControlUpdater(
			new LabeledTableItem(checkBox.getCheckBox()),
			stringHolder
		);

		return checkBox;
	}

	/**
	 * Uninstalls any listeners from the subject in order to stop being notified
	 * for changes made outside of this panes.
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

		for (AbstractPane<?> subPane : this.subPanes) {
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
			this.log("   ->disengageListeners() from " + subject);

			for (String propertyName : this.propertyNames()) {
				subject.removePropertyChangeListener(propertyName, this.aspectChangeListener);
			}
		}
	}

	/**
	 * Notifies this pane is should dispose itself.
	 *
	 * @category Populate
	 */
	public final void dispose() {
		if (!container.isDisposed()) {
			this.log("dispose()");
			this.performDispose();
			this.disengageListeners();
		}
	}

	/**
	 * Requests this pane to dispose itself.
	 *
	 * @category Populate
	 */
	protected void doDispose() {
		this.log("   ->doDispose()");
	}

	/**
	 * Requests this pane to populate its widgets with the subject's values.
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
		for (AbstractPane<?> subPane : this.subPanes) {
			subPane.enableWidgets(enabled);
		}
	}

	/**
	 * Installs the listeners on the subject in order to be notified from changes
	 * made outside of this panes.
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

		for (AbstractPane<?> subPane : this.subPanes) {
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

			this.log("   ->engageListeners() on " + subject);

			for (String propertyName : this.propertyNames()) {
				subject.addPropertyChangeListener(propertyName, this.aspectChangeListener);
			}
		}
	}

	/**
	 * Wraps the given <code>Composite</code> into a new <code>Composite</code>
	 * in order to have the widgets' border painted. This must be a bug in the
	 * <code>GridLayout</code> used in a form.
	 *
	 * @param container The parent of the sub-pane with 1 pixel border
	 * @return A new <code>Composite</code> that has the necessary space to paint
	 * the border
	 */
	protected final Composite fixBorderNotPainted(Composite container) {
		return buildSubPane(container, 1, 1, 1, 1, 1);
	}

	/**
	 * Returns the main <code>Control</code> of this pane.
	 *
	 * @return The main container
	 *
	 * @category Layout
	 */
	public Composite getControl() {
		return this.container;
	}

	/**
	 * Returns the subject holder used by this pane.
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
	protected final IWidgetFactory getWidgetFactory() {
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
	 * Initializes this <code>AbstractSubjectPane</code>.
	 *
	 * @category Initialization
	 */
	protected void initialize() {
	}

	/**
	 * Registers this pane with the parent pane.
	 *
	 * @param parentPane The parent pane
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent pane;
	 * <code>false</code> to not align them
	 *
	 * @category Initialization
	 */
	private void initialize(AbstractPane<?> parentPane,
	                        boolean automaticallyAlignWidgets) {

		// Register this pane with the parent pane, it will call the methods
		// automatically (engageListeners(), disengageListeners(), populate(),
		// dispose(), etc)
		parentPane.registerSubPane(this);

		// Align the left and right controls with the controls from the parent
		// pane
		if (automaticallyAlignWidgets) {
			parentPane.leftControlAligner .add(leftControlAligner);
			parentPane.rightControlAligner.add(rightControlAligner);
		}
	}

	/**
	 * Initializes this <code>AbstractSubjectPane</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param widgetFactory The factory used to create various widgets
	 *
	 * @category Initialization
	 */
	@SuppressWarnings("unchecked")
	private void initialize(PropertyValueModel<? extends T> subjectHolder,
	                        IWidgetFactory widgetFactory)
	{
		Assert.isNotNull(subjectHolder, "The subject holder cannot be null");

		this.subjectHolder         = (PropertyValueModel<T>) subjectHolder;
		this.widgetFactory         = widgetFactory;
		this.subPanes              = new ArrayList<AbstractPane<?>>();
		this.leftControlAligner    = new ControlAligner();
		this.rightControlAligner   = new ControlAligner();
		this.subjectChangeListener = this.buildSubjectChangeListener();
		this.aspectChangeListener  = this.buildAspectChangeListener();

		this.initialize();
	}

	/**
	 * Initializes the layout of this pane.
	 *
	 * @param container The parent container
	 *
	 * @category Layout
	 */
	protected abstract void initializeLayout(Composite container);

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

		if (Tracing.booleanDebugOption(Tracing.UI_LAYOUT)) {

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
	 * Notifies this pane is should dispose itself.
	 *
	 * @category Populate
	 */
	protected void performDispose() {
		this.log("   ->performDispose()");

		// Dispose this pane
		doDispose();

		// Ask the sub-panes to perform the dispose themselves
		for (AbstractPane<?> subPane : this.subPanes) {
			subPane.performDispose();
		}
	}

	/**
	 * Notifies this pane to populate itself using the subject's information.
	 *
	 * @category Populate
	 */
	public final void populate() {
		if (!container.isDisposed()) {
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
	protected Collection<String> propertyNames() {
		ArrayList<String> propertyNames = new ArrayList<String>();
		addPropertyNames(propertyNames);
		return propertyNames;
	}

	/**
	 * Registers another <code>AbstractSubjectPane</code> with this one so it can
	 * be automatically notified about certain events such as engaging or
	 * disengaging the listeners, etc.
	 *
	 * @param subPane The sub-pane to register
	 *
	 * @category Controller
	 */
	protected final void registerSubPane(AbstractPane<?> subPane) {
		this.subPanes.add(subPane);
	}

	/**
	 * Removes the given pane's widgets (those that were registered with
	 * its left <code>ControlAligner</code>) from this pane's left
	 * <code>ControlAligner</code> so that their width will no longer be adjusted
	 * with the width of the widest widget.
	 *
	 * @param pane The pane containing the widgets to remove
	 *
	 * @category Layout
	 */
	protected final void removeAlignLeft(AbstractPane<?> pane) {
		leftControlAligner.remove(pane.leftControlAligner);
	}

	/**
	 * Removes the given control from the collection of widgets that are aligned
	 * to have the same width when they are shown to the left side of the 3
	 * widget colums.
	 *
	 * @param pane The pane to remove, its width will no longer be
	 * ajusted to be the width of the longest widget
	 *
	 * @category Layout
	 */
	protected final void removeAlignLeft(Control control) {
		leftControlAligner.remove(control);
	}

	/**
	 * Removes the given pane's widgets (those that were registered with
	 * its right <code>ControlAligner</code>) from this pane's right
	 * <code>ControlAligner</code> so that their width will no longer be adjusted
	 * with the width of the widest widget.
	 *
	 * @param pane The pane containing the widgets to remove
	 *
	 * @category Layout
	 */
	protected final void removeAlignRight(AbstractPane<?> pane) {
		rightControlAligner.remove(pane.rightControlAligner);
	}

	/**
	 * Removes the given control from the collection of widgets that are aligned
	 * to have the same width when they are shown to the right side of the 3
	 * widget colums.
	 *
	 * @param pane The pane to remove, its width will no longer be
	 * ajusted to be the width of the longest widget
	 *
	 * @category Layout
	 */
	protected final void removeAlignRight(Control control) {
		rightControlAligner.remove(control);
	}

	/**
	 * Removes the given pane's controls (those that were registered for
	 * alignment) from this pane.
	 *
	 * @param pane The pane containing the widgets that no longer
	 * requires their width adjusted with the width of the longest widget
	 *
	 * @category Layout
	 */
	protected final void removePaneForAlignment(AbstractPane<?> pane) {
		removeAlignLeft(pane);
		removeAlignRight(pane);
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

		// Ask the sub-panes to repopulate themselves
		for (AbstractPane<?> subPane : this.subPanes) {
			subPane.repopulate();
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
	 * Returns the nearest <code>Shell</code> displaying the main widget of this
	 * pane.
	 *
	 * @return The nearest window displaying this pane
	 */
	protected final Shell shell() {
		return container.getShell();
	}

	/**
	 * Returns the subject of this pane.
	 *
	 * @return The subject if this pane was not disposed; <code>null</code>
	 * if it was
	 *
	 * @category Populate
	 */
	protected T subject() {
		return subjectHolder.value();
	}

	/**
	 * The subject holder's value changed, disconnects any listeners from the
	 * old subject and connects those listeners onto the new subject.
	 *
	 * @param e The property change containing the old and new subjects
	 *
	 * @category Populate
	 */
	@SuppressWarnings("unchecked")
	private void subjectChanged(PropertyChangeEvent e) {
		if (!container.isDisposed()) {
			this.log("subjectChanged()");
			this.disengageListeners((T) e.oldValue());
			this.repopulate();
			this.engageListeners((T) e.newValue());
		}
	}

	/**
	 * Unregisters the given <code>AbstractSubjectPane</code> from this one so it
	 * can no longer be automatically notified about certain events such as
	 * engaging or disengaging the listeners, etc.
	 *
	 * @param subPane The sub-pane to unregister
	 *
	 * @category Controller
	 */
	protected final void unregisterSubPane(AbstractPane<?> subPane) {
		this.subPanes.remove(subPane);
	}

	public static interface IWidgetFactory {
		Button createButton(Composite parent, String text, int style);
		CCombo createCombo(Composite parent);
		Composite createComposite(Composite parent);
		Group createGroup(Composite parent, String title);
		Hyperlink createHyperlink(Composite parent, String text);
		Label createLabel(Composite container, String labelText);
		List createList(Composite container, int style);
		Section createSection(Composite parent, int style);
		Text createText(Composite parent);
	}
}

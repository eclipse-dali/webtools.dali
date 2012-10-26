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

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.ui.internal.plugin.JptCommonUiPlugin;
import org.eclipse.jpt.common.ui.internal.swt.ComboModelAdapter;
import org.eclipse.jpt.common.ui.internal.swt.DateTimeModelAdapter;
import org.eclipse.jpt.common.ui.internal.swt.SpinnerModelAdapter;
import org.eclipse.jpt.common.ui.internal.swt.TriStateCheckBoxModelAdapter;
import org.eclipse.jpt.common.ui.internal.util.LabeledButton;
import org.eclipse.jpt.common.ui.internal.util.LabeledControlUpdater;
import org.eclipse.jpt.common.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeBooleanPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.NonNullBooleanTransformer;
import org.eclipse.jpt.common.utility.internal.transformer.StringObjectTransformer;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.part.PageBook;

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
 * @see FormPane
 * @see DialogPane
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class Pane<T extends Model>
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
	private final Composite container;

	/**
	 * Flag used to stop the circular population of widgets.
	 */
	private boolean populating;


	/**
	 * This listener is registered with the subject holder in order to
	 * automatically repopulate this pane with the new subject.
	 */
	private PropertyChangeListener subjectChangeListener;

	/**
	 * The subject of this pane.
	 */
	private PropertyValueModel<T> subjectModel;

	/**
	 * The collection of registered sub-panes will be automatically notified
	 * when listeners need to be engaged or disengaged or when to populate its
	 * widgets.
	 */
	private Collection<Pane<?>> subPanes;

	/**
	 * The factory used to create various common widgets.
	 */
	private WidgetFactory widgetFactory;

	/**
	 * The "and" combination enabledModel passed in via constructor and the parent Pane's {@link #enabledModel}
	 */
	private PropertyValueModel<Boolean> enabledModel;

	/**
	 * A listener that allows us to stop listening to stuff when the control
	 * is disposed. (Critical for preventing memory leaks.)
	 */
	private final DisposeListener controlDisposeListener;

	/**
	 * Creates a new <code>Pane</code>.
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
	protected Pane(
			Pane<? extends T> parentPane,
	        Composite parent) {

		this(
			parentPane,
			parentPane.getSubjectHolder(),
			parent);
	}

	/**
	 * Creates a new <code>Pane</code>.
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
	protected Pane(
			Pane<? extends T> parentPane,
	        Composite parent,
	        PropertyValueModel<Boolean> enabledModel) {

		this(
			parentPane,
			parentPane.getSubjectHolder(),
			enabledModel,
			parent);
	}

	/**
	 * Creates a new <code>Pane</code>.
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
	protected Pane(
			Pane<?> parentPane,
			PropertyValueModel<? extends T> subjectHolder,
			Composite parent) {

		super();
		this.initialize(subjectHolder, parentPane.getEnabledModel(), parentPane.getWidgetFactory());
		this.initialize(parentPane);
		if (this.addsComposite()) {
			this.container = this.addComposite(parent);
			this.initializeLayout(this.container);
		}
		else {
			this.container = null;
			this.initializeLayout(parent);
		}
		this.controlDisposeListener = this.buildControlDisposeListener();
		this.getControl().addDisposeListener(this.controlDisposeListener);
		this.engageSubjectHolder();
		this.engageListeners(getSubject());
		this.populate();
	}

	/**
	 * Creates a new <code>Pane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent pane;
	 * <code>false</code> to not align them
	 * @param parentManagePane <code>true</code> to have the parent pane manage
	 * the enabled state of this pane
	 *
	 * @category Constructor
	 */
	protected Pane(
			Pane<?> parentPane,
			PropertyValueModel<? extends T> subjectHolder,
			PropertyValueModel<Boolean> enabledModel,
			Composite parent) {

		
		super();
		this.initialize(subjectHolder, CompositeBooleanPropertyValueModel.and(this.wrapEnabledModel(enabledModel), parentPane.getEnabledModel()), parentPane.getWidgetFactory());
		this.initialize(parentPane);
		if (this.addsComposite()) {
			this.container = this.addComposite(parent);
			this.initializeLayout(this.container);
		}
		else {
			this.container = null;
			this.initializeLayout(parent);
		}
		this.controlDisposeListener = this.buildControlDisposeListener();
		this.getControl().addDisposeListener(this.controlDisposeListener);
		this.engageSubjectHolder();
		this.engageListeners(getSubject());
		this.populate();
	}

	/**
	 * Creates a new <code>Pane</code>.
	 *
	 * @param subjectModel The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 *
	 * @category Constructor
	 */
	protected Pane(
		PropertyValueModel<? extends T> subjectModel,
		Composite parent,
		WidgetFactory widgetFactory) {

		super();
		this.initialize(subjectModel, this.buildNonNullEnabledModel(subjectModel), widgetFactory);
		if (this.addsComposite()) {
			this.container = this.addComposite(parent);
			this.initializeLayout(this.container);
		}
		else {
			this.container = null;
			this.initializeLayout(parent);
		}
		this.controlDisposeListener = this.buildControlDisposeListener();
		this.getControl().addDisposeListener(this.controlDisposeListener);
		this.engageSubjectHolder();
		this.engageListeners(getSubject());
		this.populate();
	}
	
	protected Pane(
		PropertyValueModel<? extends T> subjectHolder,
		PropertyValueModel<Boolean> enabledModel,
		Composite parent,
		WidgetFactory widgetFactory) {

		super();
		this.initialize(subjectHolder, this.wrapEnabledModel(enabledModel), widgetFactory);
		if (this.addsComposite()) {
			this.container = this.addComposite(parent);
			this.initializeLayout(this.container);
		}
		else {
			this.container = null;
			this.initializeLayout(parent);
		}
		this.controlDisposeListener = this.buildControlDisposeListener();
		this.getControl().addDisposeListener(this.controlDisposeListener);
		this.engageSubjectHolder();
		this.engageListeners(getSubject());
		this.populate();
	}

	protected PropertyValueModel<Boolean> buildNonNullEnabledModel(PropertyValueModel<? extends T> subjectModel) {
		return new TransformationPropertyValueModel<T, Boolean>(subjectModel) {
			@Override
			protected Boolean transform(T value) {
				return Boolean.valueOf(value != null);
			}
		};
	}
	

	// ********** initialization **********

	@SuppressWarnings("unchecked")
	private void initialize(
			PropertyValueModel<? extends T> subjectModel,
			PropertyValueModel<Boolean> enabledModel,
	        WidgetFactory widgetFactory) {

		Assert.isNotNull(subjectModel, "The subject model cannot be null");

		this.subjectModel         = (PropertyValueModel<T>) subjectModel;
		this.widgetFactory         = widgetFactory;
		this.enabledModel 		   = enabledModel;
		this.subPanes              = new ArrayList<Pane<?>>();
		this.subjectChangeListener = this.buildSubjectChangeListener();
		this.aspectChangeListener  = this.buildAspectChangeListener();

		this.initialize();
	}

	protected void initialize() {
		// do nothing by default
	}

	/**
	 * Registers this pane with the parent pane.
	 *
	 * @param parentPane The parent pane
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent pane;
	 * <code>false</code> to not align them
	 * @param parentManagePane <code>true</code> to have the parent pane manage
	 * the enabled state of this pane
	 *
	 * @category Initialization
	 */
	private void initialize(Pane<?> parentPane) {
		// Register this pane with the parent pane, it will call the methods
		// automatically (engageListeners(), disengageListeners(), populate(),
		// dispose(), etc)
		parentPane.registerSubPane(this);
	}

	/**
	 * Initializes the layout of this pane.
	 *
	 * @param container The parent container
	 *
	 * @category Layout
	 */
	protected abstract void initializeLayout(Composite container);

	private DisposeListener buildControlDisposeListener() {
		return new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				Pane.this.controlDisposed();
			}
		    @Override
			public String toString() {
				return "control dispose listener";
			}
		};
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
				//subject() could have changed or is null because of the possibility of
				//"jumping" on the UI thread here and a selection change occuring
				if (e.getSource() == getSubject()) {
					updatePane(e.getPropertyName());
				}
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
	protected final Button addButton(Composite container,
	                                   String text,
	                                   final Runnable buttonAction) {

		return this.addButton(container, text, null, buttonAction);
	}

	protected final Button addButton(Composite container,
        String text,
        final Runnable buttonAction,
        PropertyValueModel<Boolean> enabledModel) {

		return this.addButton(container, text, null, buttonAction, enabledModel);
	}

	/**
	 * Creates a new unmanaged <code>Button</code> widget.  Unmanaged means
	 * that this Pane will not handle the enabling/disabling of this widget.
	 * The owning object will handle it with its own PaneEnabler or ControlEnabler.
	 *
	 * @param parent The parent container
	 * @param buttonText The button's text
	 * @param buttonAction The action to be invoked when the button is pressed
	 * @return The newly created <code>Button</code>
	 *
	 * @category Layout
	 */
	protected final Button addUnmanagedButton(Composite container,
	                                   String text,
	                                   final Runnable buttonAction) {

		return this.addUnmanagedButton(container, text, null, buttonAction);
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
	protected final Button addButton(Composite container,
	                                   String text,
	                                   String helpId,
	                                   final Runnable buttonAction) {

		Button button = addUnmanagedButton(container, text, helpId, buttonAction);
		this.controlEnabledState(button);

		return button;
	}

	protected final Button addButton(Composite container,
								        String text,
								        String helpId,
								        final Runnable buttonAction,
								        PropertyValueModel<Boolean> enabledModel) {

		Button button = addUnmanagedButton(container, text, helpId, buttonAction);
		this.controlEnabledState(enabledModel, button);

		return button;
	}

	/**
	 * Creates a new unmanaged <code>Button</code> widget.  Unmanaged means
	 * that this Pane will not handle the enabling/disabling of this widget.
	 * The owning object will handle it with its own PaneEnabler or ControlEnabler.
	 *
	 * @param parent The parent container
	 * @param buttonText The button's text
	 * @param helpId The topic help ID to be registered for the new check box
	 * @param buttonAction The action to be invoked when the button is pressed
	 * @return The newly created <code>Button</code>
	 *
	 * @category Layout
	 */
	private Button addUnmanagedButton(Composite container,
	                                   String text,
	                                   String helpId,
	                                   final Runnable buttonAction) {

		Button button = this.widgetFactory.createButton(container, text);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				buttonAction.run();
			}
		});

		if (helpId != null) {
			getHelpSystem().setHelp(button, helpId);
		}

		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.horizontalAlignment       = GridData.FILL;
		button.setLayoutData(gridData);

		return button;
	}

	/**
	 * This layout will leave space for decorations on widgets.
	 * Whether decorated or not, all of the widgets need the same indent
 	 * so that they align properly.
	 */
	protected GridData getFieldGridData() {
		int margin = FieldDecorationRegistry.getDefault()
				.getMaximumDecorationWidth();
		GridData data = new GridData();
		data.horizontalAlignment = SWT.FILL;
		data.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH + margin;
		data.horizontalIndent = margin;
		data.grabExcessHorizontalSpace = true;
		return data;
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
	protected final Button addCheckBox(
			Composite parent,
	        String buttonText,
	        ModifiablePropertyValueModel<Boolean> booleanHolder,
	        String helpId) {

		return this.addToggleButton(
			parent,
			buttonText,
			booleanHolder,
			helpId,
			SWT.CHECK);
	}

	protected final Button addCheckBox(
			Composite parent,
			String buttonText,
			ModifiablePropertyValueModel<Boolean> booleanHolder,
			String helpId,
			PropertyValueModel<Boolean> enabledModel) {

		Button button = this.addUnmanagedToggleButton(parent, buttonText, booleanHolder, helpId, SWT.CHECK);
		this.controlEnabledState(enabledModel, button);
		return button;
	}

	/**
	 * Creates a new non-editable <code>Combo</code>.
	 *
	 * @param container The parent container
	 * @return The newly created <code>Combo</code>
	 *
	 * @category Layout
	 */
	protected final Combo addCombo(Composite container) {
		Combo combo = this.addUnmanagedCombo(container);
		this.controlEnabledState(combo);
		return combo;
	}

	/**
	 * Creates a new non-editable <code>Combo</code>.
	 *
	 * @param container The parent container
	 * @return The newly created <code>Combo</code>
	 *
	 * @category Layout
	 */
	protected final Combo addCombo(Composite container, String helpId) {
		Combo combo = this.addUnmanagedCombo(container);

		if (helpId != null) {
			getHelpSystem().setHelp(combo, helpId);
		}

		this.controlEnabledState(combo);
		return combo;
	}

	/**
	 * Creates a new non-editable <code>Combo</code>.
	 *
	 * @param container The parent container
	 * @return The newly created <code>Combo</code>
	 *
	 * @category Layout
	 */
	private Combo addUnmanagedCombo(Composite container) {
		Combo combo = this.widgetFactory.createCombo(container);
		combo.setLayoutData(getFieldGridData());
		return combo;
	}

	/**
	 * Creates a new non-editable <code>Combo</code>.
	 *
	 * @param container The parent container
	 * @param listHolder The <code>ListValueHolder</code>
	 * @param selectedItemHolder The holder of the selected item
	 * @param stringConverter The converter responsible to transform each item
	 * into a string representation
	 * @return The newly created <code>Combo</code>
	 *
	 * @category Layout
	 */
	protected final <V> Combo addCombo(Composite container,
	                                     ListValueModel<V> listHolder,
	                                     ModifiablePropertyValueModel<V> selectedItemHolder,
	                                     Transformer<V, String> stringConverter,
	                                     String helpId) {

		Combo combo = this.addCombo(container, helpId);

		ComboModelAdapter.adapt(
			listHolder,
			selectedItemHolder,
			combo,
			stringConverter
		);

		return combo;
	}

	/**
	 * Creates a new non-editable <code>Combo</code>.
	 *
	 * @param container The parent container
	 * @param listHolder The <code>ListValueHolder</code>
	 * @param selectedItemHolder The holder of the selected item
	 * @param stringConverter The converter responsible to transform each item
	 * into a string representation
	 * @return The newly created <code>Combo</code>
	 *
	 * @category Layout
	 */
	private <V> Combo addUnmanagedCombo(Composite container,
	                                     ListValueModel<V> listHolder,
	                                     ModifiablePropertyValueModel<V> selectedItemHolder,
	                                     Transformer<V, String> stringConverter) {

		Combo combo = this.addUnmanagedCombo(container);

		ComboModelAdapter.adapt(
			listHolder,
			selectedItemHolder,
			combo,
			stringConverter
		);

		return combo;
	}

	protected final <V> Combo addCombo(
			Composite container,
			ListValueModel<V> listHolder,
			ModifiablePropertyValueModel<V> selectedItemHolder,
			Transformer<V, String> stringConverter,
			PropertyValueModel<Boolean> enabledModel) {

		Combo combo = this.addUnmanagedCombo(container, listHolder, selectedItemHolder, stringConverter);
		this.controlEnabledState(enabledModel, combo);
		return combo;
	}

	/**
	 * Creates a new <code>ComboViewer</code> using a <code>Combo</code>.
	 *
	 * @param container The parent container
	 * @param labelProvider The provider responsible to convert the combo's items
	 * into human readable strings
	 * @return The newly created <code>ComboViewer</code>
	 *
	 * @category Layout
	 */
	protected final ComboViewer addComboViewer(Composite container,
	                                             IBaseLabelProvider labelProvider) {

		Combo combo = this.addCombo(container);
		ComboViewer viewer = new ComboViewer(combo);
		viewer.setLabelProvider(labelProvider);
		return viewer;
	}
	protected final ComboViewer addComboViewer(Composite container,
												IBaseLabelProvider labelProvider,
												String helpId) {
	
		Combo combo = this.addCombo(container, helpId);
		ComboViewer viewer = new ComboViewer(combo);
		viewer.setLabelProvider(labelProvider);
		return viewer;
	}

	/**
	 * Creates the main container of this pane. The layout and layout data are
	 * automatically set.
	 *
	 * @param parent The parent container
	 * @return The newly created <code>Composite</code> that will holds all the
	 * widgets created by this pane through {@link #initializeLayout(Composite)}
	 *
	 * @category Layout
	 */
	protected Composite addComposite(Composite parent) {
		return this.addSubPane(parent);
	}

	/**
	 * Return whether this Pane should add a Composite. Using this
	 * to reduce the number of SWT Controls (USER handles in windows) created.
	 * Typically you would return false if the Pane is for only 1 widget. In this case
	 * you need to override {@link #getControl()} to return the appropriate Control
	 * @return
	 */
	protected boolean addsComposite() {
		return true;
	}

	protected final <V> Combo addEditableCombo(
			Composite container,
			ListValueModel<V> listHolder,
			ModifiablePropertyValueModel<V> selectedItemHolder,
			Transformer<V, String> stringConverter,
			PropertyValueModel<Boolean> enabledModel) {

		return this.addEditableCombo(container, listHolder, selectedItemHolder, stringConverter, enabledModel, null);
	}

	protected final <V> Combo addEditableCombo(
			Composite container,
			ListValueModel<V> listHolder,
			ModifiablePropertyValueModel<V> selectedItemHolder,
			Transformer<V, String> stringConverter,
			PropertyValueModel<Boolean> enabledModel,
			String helpId) {

		Combo combo = this.addUnmanagedEditableCombo(container, listHolder, selectedItemHolder, stringConverter, helpId);
		this.controlEnabledState(enabledModel, combo);
		return combo;
	}

	protected final Combo addEditableCombo(Composite container) {
		return this.addEditableCombo(container, null);
	}

	protected final Combo addEditableCombo(Composite container, String helpId) {
		Combo combo = this.widgetFactory.createEditableCombo(container);

		if (helpId != null) {
			getHelpSystem().setHelp(combo, helpId);
		}

		combo.setLayoutData(getFieldGridData());
		this.controlEnabledState(combo);
		return combo;
	}

	protected final <V> Combo addEditableCombo(Composite container,
       											ListValueModel<V> listHolder,
       											ModifiablePropertyValueModel<V> selectedItemHolder) {
		
		return this.addEditableCombo(
			container, 
			listHolder, 
			selectedItemHolder, 
			StringObjectTransformer.<V>instance(),
			(String) null);
	}
	
	/**
	 * Creates a new editable <code>Combo</code>.
	 *
	 * @param container The parent container
	 * @param listHolder The <code>ListValueHolder</code>
	 * @param selectedItemHolder The holder of the selected item
	 * @param stringConverter The converter responsible to transform each item
	 * into a string representation
	 * @return The newly created <code>Combo</code>
	 *
	 * @category Layout
	 */
	protected final <V> Combo addEditableCombo(Composite container,
	                                             ListValueModel<V> listHolder,
	                                             ModifiablePropertyValueModel<V> selectedItemHolder,
	                                             Transformer<V, String> stringConverter,
	                                             String helpId) {

		Combo combo = this.addEditableCombo(container, helpId);

		ComboModelAdapter.adapt(
			listHolder,
			selectedItemHolder,
			combo,
			stringConverter
		);

		return combo;
	}

	/**
	 * Creates a new editable <code>ComboViewer</code> using a <code>Combo</code>.
	 *
	 * @param container The parent container
	 * @param labelProvider The provider responsible to convert the combo's items
	 * into human readable strings
	 * @return The newly created <code>ComboViewer</code>
	 *
	 * @category Layout
	 */
	protected final ComboViewer addEditableComboViewer(Composite container,
	                                                     IBaseLabelProvider labelProvider) {

		Combo combo = this.addEditableCombo(container);
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
	 *
	 * @category Layout
	 */
	protected final Hyperlink addHyperlink(Composite parent,
	                                         String text,
	                                         final Runnable hyperLinkAction) {

		Hyperlink link = this.widgetFactory.createHyperlink(parent, text);
		this.controlEnabledState(link);

		link.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {

				Hyperlink hyperLink = (Hyperlink) e.widget;

				if (hyperLink.isEnabled()) {
					hyperLinkAction.run();
				}
			}
		});

		return link;
	}

	protected final Hyperlink addHyperlink(Composite parent,
											String text) {

		Hyperlink link = this.widgetFactory.createHyperlink(parent, text);
		this.controlEnabledState(link);
		
		return link;
	}

	/**
	 * Creates a new label using the given information.
	 *
	 * @param parent The parent container
	 * @param labelText The label's text
	 *
	 * @category Layout
	 */
	protected final Label addLabel(Composite container,
	                                 String labelText) {

		Label label = this.addUnmanagedLabel(container, labelText);
		this.controlEnabledState(label);
		return label;
	}

	protected final Label addLabel(
			Composite container,
			String labelText,
			PropertyValueModel<Boolean> enabledModel
	) {
		Label label = this.addUnmanagedLabel(container, labelText);
		this.controlEnabledState(enabledModel, label);
		return label;
	}

	/**
	 * Creates a new unmanaged <code>Label</code> widget.  Unmanaged means
	 * that this Pane will not handle the enabling/disabling of this widget.
	 * The owning object will handle it with its own PaneEnabler or ControlEnabler.
	 *
	 * @param parent The parent container
	 * @param labelText The label's text
	 *
	 * @category Layout
	 */
	private Label addUnmanagedLabel(Composite container,
	                                 String labelText) {

		return this.widgetFactory.createLabel(container, labelText);
	}

	/**
	 * Creates a new managed spinner. Managed means that this Pane will
	 * handle enabling/disabling of this widget if a PaneEnabler is used.
	 *
	 * @param parent The parent container
	 * @param numberHolder The holder of the integer value
	 * @param defaultValue The value shown when the holder has <code>null</code>
	 * @param minimumValue The minimum value that the spinner will allow
	 * @param maximumValue The maximum value that the spinner will allow
	 * @param helpId The topic help ID to be registered for the new button
	 * @return The newly created <code>Spinner</code>
	 *
	 * @category Layout
	 */
	protected final Spinner addSpinner(Composite parent,
	                                     ModifiablePropertyValueModel<Integer> numberHolder,
	                                     int defaultValue,
	                                     int minimumValue,
	                                     int maximumValue,
	                                     String helpId) {

		Spinner spinner = addUnmanagedSpinner(parent, numberHolder, defaultValue, minimumValue, maximumValue, helpId);
		this.controlEnabledState(spinner);
		return spinner;
	}

	/**
	 * Creates a new unmanaged spinner.  Unmanaged means that this Pane will
	 * not handle the enabling/disabling of this widget.  The owning object will handle
	 * it with its own PaneEnabler or ControlEnabler.
	 *
	 * @param parent The parent container
	 * @param numberHolder The holder of the integer value
	 * @param defaultValue The value shown when the holder has <code>null</code>
	 * @param minimumValue The minimum value that the spinner will allow
	 * @param maximumValue The maximum value that the spinner will allow
	 * @param helpId The topic help ID to be registered for the new button
	 * @return The newly created <code>Spinner</code>
	 *
	 * @category Layout
	 */
	private Spinner addUnmanagedSpinner(Composite parent,
	                                     ModifiablePropertyValueModel<Integer> numberHolder,
	                                     int defaultValue,
	                                     int minimumValue,
	                                     int maximumValue,
	                                     String helpId) {

		Spinner spinner = this.widgetFactory.createSpinner(parent);
		spinner.setMinimum(minimumValue);
		spinner.setMaximum(maximumValue);
		GridData gridData = getFieldGridData();
		gridData.grabExcessHorizontalSpace = false;
		spinner.setLayoutData(gridData);

		SpinnerModelAdapter.adapt(numberHolder, spinner, defaultValue);

		if (helpId != null) {
			getHelpSystem().setHelp(spinner, helpId);
		}

		return spinner;
	}

	/**
	 * Creates a new managed DateTime of type SWT.TIME.  Managed means that this Pane will
	 * handle enabling/disabling of this widget if a PaneEnabler is used.
	 *
	 * @param parent The parent container
	 * @param hoursHolder The holder of the hours integer value
	 * @param minutesHolder The holder of the minutes integer value
	 * @param secondsHolder The holder of the seconds integer value
	 * @param helpId The topic help ID to be registered for the new dateTime
	 * @return The newly created <code>DateTime</code>
	 *
	 * @category Layout
	 */
	protected final DateTime addDateTime(Composite parent,
											ModifiablePropertyValueModel<Integer> hoursHolder,
											ModifiablePropertyValueModel<Integer> minutesHolder,
											ModifiablePropertyValueModel<Integer> secondsHolder,
											String helpId) {

		DateTime dateTime = this.addUnmanagedDateTime(parent, hoursHolder, minutesHolder, secondsHolder, helpId);
		this.controlEnabledState(dateTime);

		return dateTime;
	}

	protected final DateTime addDateTime(
			Composite parent,
			ModifiablePropertyValueModel<Integer> hoursHolder,
			ModifiablePropertyValueModel<Integer> minutesHolder,
			ModifiablePropertyValueModel<Integer> secondsHolder,
			String helpId,
			PropertyValueModel<Boolean> enabledModel
	) {
		DateTime dateTime = this.addUnmanagedDateTime(parent, hoursHolder, minutesHolder, secondsHolder, helpId);
		this.controlEnabledState(enabledModel, dateTime);
		return dateTime;
	}

	/**
	 * Creates a new unmanaged DateTime of type SWT.TIME.  Unmanaged means that this Pane will
	 * not handle the enabling/disabling of this widget.  The owning object will handle
	 * it with its own PaneEnabler or ControlEnabler.
	 *
	 * @param parent The parent container
	 * @param hoursHolder The holder of the hours integer value
	 * @param minutesHolder The holder of the minutes integer value
	 * @param secondsHolder The holder of the seconds integer value
	 * @param helpId The topic help ID to be registered for the new dateTime
	 * @return The newly created <code>DateTime</code>
	 *
	 * @category Layout
	 */
	private DateTime addUnmanagedDateTime(Composite parent,
											ModifiablePropertyValueModel<Integer> hoursHolder,
											ModifiablePropertyValueModel<Integer> minutesHolder,
											ModifiablePropertyValueModel<Integer> secondsHolder,
											String helpId) {

		DateTime dateTime = this.widgetFactory.createDateTime(parent, SWT.TIME);

		DateTimeModelAdapter.adapt(hoursHolder, minutesHolder, secondsHolder, dateTime);

		if (helpId != null) {
			getHelpSystem().setHelp(dateTime, helpId);
		}

		return dateTime;
	}
	/**
	 * Creates a new editable <code>Combo</code>.
	 *
	 * @param container The parent container
	 * @param listHolder The <code>ListValueHolder</code>
	 * @param selectedItemHolder The holder of the selected item
	 * @param stringConverter The converter responsible to transform each item
	 * into a string representation
	 * @return The newly created <code>CCombo</code>
	 *
	 * @category Layout
	 */
	private <V> Combo addUnmanagedEditableCombo(Composite container,
	                                               ListValueModel<V> listHolder,
	                                               ModifiablePropertyValueModel<V> selectedItemHolder,
	                                               Transformer<V, String> stringConverter,
	                                               String helpId) {

		Combo combo = addUnmanagedEditableCombo(container, helpId);

		ComboModelAdapter.adapt(
			listHolder,
			selectedItemHolder,
			combo,
			stringConverter
		);

		return combo;
	}


	/**
	 * Creates a new editable <code>Combo</code>.
	 *
	 * @param container The parent container
	 * @return The newly created <code>CCombo</code>
	 *
	 * @category Layout
	 */
	private Combo addUnmanagedEditableCombo(Composite container,
												String helpId) {

		Combo combo = this.widgetFactory.createEditableCombo(container);
		combo.setLayoutData(getFieldGridData());
		

		if (helpId != null) {
			getHelpSystem().setHelp(container, helpId);
		}

		return combo;
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
	protected final List addList(Composite container, String helpId) {

		return this.addList(
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
	 * @param helpId The topic help ID to be registered for the new radio button
	 * @return The newly created <code>List</code>
	 *
	 * @category Layout
	 */
	protected final List addList(Composite container,
	                               ModifiablePropertyValueModel<String> selectionHolder,
	                               String helpId) {

		List list = this.addUnmanagedList(container, selectionHolder, helpId);
		this.controlEnabledState(list);

		return list;
	}

	/**
	 * Creates a new unmanaged list and notify the given selection holder when the
	 * selection changes. If the selection count is different than one than the
	 * holder will receive <code>null</code>.
	 * Unmanaged means that this Pane will not handle the enabling/disabling of this widget.
	 * The owning object will handle it with its own PaneEnabler or ControlEnabler.
	 *
	 * @param container The parent container
	 * @param selectionHolder The holder of the unique selected item
	 * @param helpId The topic help ID to be registered for the new radio button
	 * @return The newly created <code>List</code>
	 *
	 * @category Layout
	 */
	private List addUnmanagedList(Composite container,
	                               ModifiablePropertyValueModel<String> selectionHolder,
	                               String helpId) {

		List list = this.widgetFactory.createList(
			container,
			SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI
		);

		list.addSelectionListener(buildSelectionListener(selectionHolder));
		list.setLayoutData(new GridData(GridData.FILL_BOTH));

		if (helpId != null) {
			getHelpSystem().setHelp(list, helpId);
		}

		return list;
	}

	/**
	 * Creates a new <code>Text</code> widget that has multiple lines.
	 *
	 * @param container The parent container
	 * @return The newly created <code>Text</code> widget
	 *
	 */
	protected final Text addMultiLineText(Composite container) {

		Text text = this.widgetFactory.createMultiLineText(container);
		text.setLayoutData(getFieldGridData());
		this.controlEnabledState(text);

		return text;
	}

	/**
	 * Creates a new <code>Text</code> widget that has multiple lines.
	 *
	 * @param container The parent container
	 * @param lineCount The number of lines the text area should display
	 * @param helpId The topic help ID to be registered for the new text
	 * @return The newly created <code>Text</code> widget
	 *
	 * @category Layout
	 */
	protected final Text addMultiLineText(Composite container,
	                                      int lineCount,
	                                      String helpId) {

		Text text = this.addMultiLineText(container);
		adjustMultiLineTextLayout(lineCount, text, text.getLineHeight());

		if (helpId != null) {
			getHelpSystem().setHelp(text, helpId);
		}

		return text;
	}

	/**
	 * Creates a new <code>Text</code> widget that has multiple lines.
	 *
	 * @param container The parent container
	 * @param textHolder The holder of the text field's input
	 * @param lineCount The number of lines the text area should display
	 * @return The newly created <code>Text</code> widget
	 *
	 * @category Layout
	 */
	protected final Text addMultiLineText(Composite container,
	                                        ModifiablePropertyValueModel<String> textHolder,
	                                        int lineCount) {

		return this.addMultiLineText(container, textHolder, lineCount, null);
	}

	/**
	 * Creates a new <code>Text</code> widget that has multiple lines.
	 *
	 * @param container The parent container
	 * @param textHolder The holder of the text field's input
	 * @param helpId The topic help ID to be registered for the new text
	 * @return The newly created <code>Text</code> widget
	 *
	 * @category Layout
	 */
	protected final Text addMultiLineText(Composite container,
	                                        ModifiablePropertyValueModel<String> textHolder,
	                                        int lineCount,
	                                        String helpId) {

		Text text = this.addMultiLineText(container, lineCount, helpId);
		SWTTools.bind(textHolder, text);
		return text;
	}

	/**
	 * Adjusts the layout of the given container so that the text control has the correct amount of
	 * lines by default.
	 */
	protected final void adjustMultiLineTextLayout(int lineCount,
	                                               Control text,
	                                               int lineHeight) {

		// Specify the number of lines the text area should display
		GridData gridData = (GridData) text.getLayoutData();
		if (gridData == null) {
			gridData = this.getFieldGridData();
			text.setLayoutData(gridData);
		}
		gridData.heightHint = lineHeight * lineCount;
	}
	/**
	 * Creates a new <code>PageBook</code> and set the proper layout and layout
	 * data.
	 *
	 * @param container The parent container
	 * @return The newly created <code>PageBook</code>
	 *
	 * @category Layout
	 */
	protected final PageBook addPageBook(Composite container) {

		PageBook pageBook = new PageBook(container, SWT.NULL);
		pageBook.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return pageBook;
	}

	/**
	 * Creates a new container without specifying any layout manager.
	 *
	 * @param container The parent of the new container
	 * @return The newly created <code>Composite</code>
	 *
	 * @category Layout
	 */
	protected final Composite addPane(Composite parent) {
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
	protected final Composite addPane(Composite container, Layout layout) {

		container = this.addPane(container);
		container.setLayout(layout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		return container;
	}

	/**
	 * Creates a new <code>Text</code> widget.
	 *
	 * @param container The parent container
	 * @param textHolder The holder of the text field's input
	 * @return The newly created <code>Text</code> widget
	 *
	 * @category Layout
	 */
	protected final Text addPasswordText(Composite container,
	                                       ModifiablePropertyValueModel<String> textHolder) {

		Text text = this.addPasswordText(container);
		SWTTools.bind(textHolder, text);

		return text;
	}

	/**
	 * Creates a new <code>Text</code> widget.
	 *
	 * @param container The parent container
	 * @return The newly created <code>Text</code> widget
	 *
	 * @category Layout
	 */
	protected final Text addPasswordText(Composite container) {

		Text text = this.widgetFactory.createPasswordText(container);
		text.setLayoutData(getFieldGridData());

		this.controlEnabledState(text);
		return text;
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
	protected final Button addPushButton(Composite parent,
	                                       String buttonText,
	                                       final Runnable buttonAction) {

		return this.addPushButton(parent, buttonText, null, buttonAction);
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
	protected final Button addPushButton(Composite parent,
	                                       String buttonText,
	                                       String helpId,
	                                       final Runnable buttonAction) {

		Button button = this.widgetFactory.createPushButton(parent, buttonText);
		controlEnabledState(button);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				buttonAction.run();
			}
		});

		button.setLayoutData(new GridData());

		if (helpId != null) {
			getHelpSystem().setHelp(button, helpId);
		}

		return button;
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
	protected final Button addRadioButton(Composite parent,
	                                        String buttonText,
	                                        ModifiablePropertyValueModel<Boolean> booleanHolder,
	                                        String helpId) {

		return this.addToggleButton(
			parent,
			buttonText,
			booleanHolder,
			helpId,
			SWT.RADIO
		);
	}

	/**
	 * Creates a new <code>Section</code>. A sub-pane is automatically added as
	 * its client and is the returned <code>Composite</code>.
	 *
	 * @param container The container of the new widget
	 * @param sectionText The text of the new section
	 * @param description The section's description
	 * @return The <code>Section</code>'s sub-pane
	 *
	 * @category Layout
	 */
	protected final Composite addSection(Composite container,
	                                       String sectionText,
	                                       String description) {

		return this.addSection(
			container,
			sectionText,
			description,
			ExpandableComposite.TITLE_BAR
		);
	}

	/**
	 * Creates a new <code>Section</code>. A sub-pane is automatically added as
	 * its client and is the returned <code>Composite</code>.
	 *
	 * @param container The container of the new widget
	 * @param sectionText The text of the new section
	 * @param description The section's description or <code>null</code> if none
	 * was provider
	 * @param type The type of section to create
	 * when to expand or collapse the section
	 * @return The <code>Section</code>'s sub-pane
	 *
	 * @category Layout
	 */
	private Composite addSection(Composite container,
	                               String sectionText,
	                               String description,
	                               int type) {

		Section section = this.widgetFactory.createSection(container, type | ((description != null) ? Section.DESCRIPTION : SWT.NULL));
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(sectionText);

		if (description != null) {
			section.setDescription(description);
		}

		Composite subPane = this.addSubPane(section);
		section.setClient(subPane);

		return subPane;
	}

	private SelectionListener buildSelectionListener(final ModifiablePropertyValueModel<String> selectionHolder) {
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
			@SuppressWarnings("unchecked")
			public void propertyChanged(PropertyChangeEvent e) {
				Pane.this.subjectChanged((T) e.getOldValue(), (T) e.getNewValue());
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
	protected final Composite addSubPane(Composite container) {
		return this.addSubPane(container, 0);
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
	protected final Composite addSubPane(Composite container, int topMargin) {
		return this.addSubPane(container, topMargin, 0);
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
	protected final Composite addSubPane(Composite container,
	                                       int topMargin,
	                                       int leftMargin) {

		return this.addSubPane(container, topMargin, leftMargin, 0, 0);
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
	protected final Composite addSubPane(Composite container,
	                                       int topMargin,
	                                       int leftMargin,
	                                       int bottomMargin,
	                                       int rightMargin) {

		return this.addSubPane(
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
	protected final Composite addSubPane(Composite container,
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

		container = this.addPane(container, layout);

		return container;
	}

	/**
	 * Creates a new table.
	 *
	 * @param container The parent container
	 * @param style The style to apply to the table
	 * @param helpId The topic help ID to be registered for the new table or
	 * <code>null</code> if no help ID is required
	 * @return The newly created <code>Table</code>
	 *
	 * @category Layout
	 */
	protected final Table addTable(Composite container,
	                                 int style,
	                                 String helpId) {

		Table table = addUnmanagedTable(container, style, helpId);
		this.controlEnabledState(table);

		return table;
	}
	/**
	 * Creates a new unmanaged table.  Unmanaged means that this Pane will
	 * not handle the enabling/disabling of this widget.  The owning object will handle
	 * it with its own PaneEnabler or ControlEnabler.
	 *
	 * @param container The parent container
	 * @param style The style to apply to the table
	 * @param helpId The topic help ID to be registered for the new table or
	 * <code>null</code> if no help ID is required
	 * @return The newly created <code>Table</code>
	 *
	 * @category Layout
	 */
	protected final Table addUnmanagedTable(Composite container,
	                                 int style,
	                                 String helpId) {

		Table table = this.widgetFactory.createTable(container, style);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		GridData gridData   = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = table.getItemHeight() * 4;
		table.setLayoutData(gridData);

		if (helpId != null) {
			getHelpSystem().setHelp(table, helpId);
		}

		return table;
	}

	/**
	 * Creates a new table.
	 *
	 * @param container The parent container
	 * @param helpId The topic help ID to be registered for the new table or
	 * <code>null</code> if no help ID is required
	 * @return The newly created <code>Table</code>
	 *
	 * @category Layout
	 */
	protected final Table addTable(Composite container, String helpId) {

		return this.addTable(
			container,
			SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI,
			helpId
		);
	}

	/**
	 * Creates a new unmanaged table.  Unmanaged means that this Pane will
	 * not handle the enabling/disabling of this widget.  The owning object will handle
	 * it with its own PaneEnabler or ControlEnabler.
	 *
	 * @param container The parent container
	 * @param helpId The topic help ID to be registered for the new table or
	 * <code>null</code> if no help ID is required
	 * @return The newly created <code>Table</code>
	 *
	 * @category Layout
	 */
	protected final Table addUnmanagedTable(Composite container, String helpId) {

		return this.addUnmanagedTable(
			container,
			SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI,
			helpId
		);
	}

	/**
	 * Creates a new managed <code>Text</code> widget.
	 *
	 * @param container The parent container
	 * @return The newly created <code>Text</code> widget
	 *
	 * @category Layout
	 */
	protected final Text addText(Composite container) {
		Text text = this.addUnmanagedText(container);
		this.controlEnabledState(text);
		return text;
	}

	/**
	 * Creates a new unmanaged <code>Text</code> widget.  Unmanaged means
	 * that this Pane will not handle the enabling/disabling of this widget.
	 * The owning object will handle it with its own PaneEnabler or ControlEnabler.
	 *
	 * @param container The parent container
	 * @return The newly created <code>Text</code> widget
	 *
	 * @category Layout
	 */
	private Text addUnmanagedText(Composite container) {
		Text text = this.widgetFactory.createText(container);
		text.setLayoutData(getFieldGridData());
		return text;
	}

	/**
	 * Creates a new <code>Text</code> widget.
	 *
	 * @param container The parent container
	 * @param helpId The topic help ID to be registered for the new text
	 * @return The newly created <code>Text</code> widget
	 *
	 * @category Layout
	 */
	protected final Text addText(Composite container, String helpId) {

		Text text = this.addText(container);

		if (helpId != null) {
			getHelpSystem().setHelp(text, helpId);
		}

		return text;
	}

	/**
	 * Creates a new unmanaged <code>Text</code> widget.  Unmanaged means
	 * that this Pane will not handle the enabling/disabling of this widget.
	 * The owning object will handle it with its own PaneEnabler or ControlEnabler.
	 *
	 * @param container The parent container
	 * @param helpId The topic help ID to be registered for the new text
	 * @return The newly created <code>Text</code> widget
	 *
	 * @category Layout
	 */
	private Text addUnmanagedText(Composite container, String helpId) {

		Text text = this.addUnmanagedText(container);

		if (helpId != null) {
			getHelpSystem().setHelp(text, helpId);
		}

		return text;
	}

	/**
	 * Creates a new <code>Text</code> widget.
	 *
	 * @param container The parent container
	 * @param textHolder The holder of the text field's input
	 * @return The newly created <code>Text</code> widget
	 *
	 * @category Layout
	 */
	protected final Text addText(Composite container,
	                               ModifiablePropertyValueModel<String> textHolder) {

		return this.addText(container, textHolder, null);
	}

	/**
	 * Creates a new <code>Text</code> widget.
	 *
	 * @param container The parent container
	 * @param textHolder The holder of the text field's input
	 * @param helpId The topic help ID to be registered for the new text
	 * @return The newly created <code>Text</code> widget
	 *
	 * @category Layout
	 */
	protected final Text addText(Composite container,
	                               ModifiablePropertyValueModel<String> textHolder,
	                               String helpId) {

		Text text = this.addText(container, helpId);
		SWTTools.bind(textHolder, text);

		return text;
	}

	protected final Text addText(
			Composite container,
			ModifiablePropertyValueModel<String> textHolder,
			String helpId,
			PropertyValueModel<Boolean> enabledModel
	) {
		Text text = this.addUnmanagedText(container, textHolder, helpId);
		this.controlEnabledState(enabledModel, text);
		return text;
	}

	/**
	 * Creates a new unmanaged <code>Text</code> widget.  Unmanaged means
	 * that this Pane will not handle the enabling/disabling of this widget.
	 * The owning object will handle it with its own PaneEnabler or ControlEnabler.
	 *
	 * @param container The parent container
	 * @param textHolder The holder of the text field's input
	 * @param helpId The topic help ID to be registered for the new text
	 * @return The newly created <code>Text</code> widget
	 *
	 * @category Layout
	 */
	private Text addUnmanagedText(Composite container,
	                               ModifiablePropertyValueModel<String> textHolder,
	                               String helpId) {

		Text text = this.addUnmanagedText(container, helpId);
		SWTTools.bind(textHolder, text);

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
	protected final Group addTitledGroup(Composite container, String title) {
		return this.addTitledGroup(container, title, null);
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
	protected final Group addTitledGroup(Composite container,
	                                      String title,
	                                      String helpId) {

		return addTitledGroup(container, title, 1, helpId);
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
	protected final Group addTitledGroup(Composite container,
	                                      String title,
	                                      int columnCount,
	                                      String helpId) {

		Group group = this.widgetFactory.createGroup(container, title);
		//manageWidget(group); TODO unsure if I want to manage groups,
		//also should probably rename this addUnmanagedTitledPane
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		GridLayout layout = new GridLayout(columnCount, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 5;
		layout.marginLeft   = 5;
		layout.marginBottom = 5;
		layout.marginRight  = 5;
		group.setLayout(layout);

		if (helpId != null) {
			getHelpSystem().setHelp(group, helpId);
		}

		return group;
	}

	/**
	 * Creates a new unmanaged new toggle button (radio button or check box).
	 * Unmanaged means  that this Pane will not handle the enabling/disabling
	 * of this widget. The owning object will handle it with its own PaneEnabler
	 * or ControlEnabler.
	 *
	 * @param parent The parent container
	 * @param buttonText The button's text
	 * @param booleanHolder The holder of the selection state
	 * @param helpId The topic help ID to be registered for the new button
	 * @return The newly created <code>Button</code>
	 *
	 * @category Layout
	 */
	private Button addUnmanagedToggleButton(
			Composite parent,
	        String buttonText,
	        ModifiablePropertyValueModel<Boolean> booleanHolder,
	        String helpId,
	        int toggleButtonType) {

		Button button;

		if (toggleButtonType == SWT.PUSH) {
			button = this.widgetFactory.createPushButton(parent, buttonText);
		}
		else if (toggleButtonType == SWT.RADIO) {
			button = this.widgetFactory.createRadioButton(parent, buttonText);
		}
		else if (toggleButtonType == SWT.CHECK) {
			button = this.widgetFactory.createCheckBox(parent, buttonText);
		}
		else {
			button = this.widgetFactory.createButton(parent, buttonText);
		}

		button.setLayoutData(new GridData());
		SWTTools.bind(booleanHolder, button);

		if (helpId != null) {
			getHelpSystem().setHelp(button, helpId);
		}

		return button;
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
	private Button addToggleButton(
			Composite parent,
	        String buttonText,
	        ModifiablePropertyValueModel<Boolean> booleanHolder,
	        String helpId,
	        int toggleButtonType) {

		Button button = addUnmanagedToggleButton(
				parent,
				buttonText,
				booleanHolder,
				helpId,
				toggleButtonType);
		this.controlEnabledState(button);
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
	 * @param helpId The topic help ID to be registered for the new check box
	 * @return The newly created <code>TriStateCheckBox</code>
	 *
	 * @category Layout
	 */
	protected final TriStateCheckBox addTriStateCheckBox(Composite parent,
	                                                       String text,
	                                                       ModifiablePropertyValueModel<Boolean> booleanHolder,
	                                                       String helpId) {

		TriStateCheckBox checkBox = this.addUnmanagedTriStateCheckBox(parent, text, booleanHolder, helpId);

		this.controlEnabledState(checkBox.getCheckBox());

		return checkBox;
	}

	protected final TriStateCheckBox addUnmanagedTriStateCheckBox(Composite parent,
	        String text,
	        ModifiablePropertyValueModel<Boolean> booleanHolder,
	        String helpId) {
	
		TriStateCheckBox checkBox = new TriStateCheckBox(
			parent,
			text,
			this.getWidgetFactory()
		);
		
		TriStateCheckBoxModelAdapter.adapt(
			booleanHolder,
			checkBox
		);
		
		if (helpId != null) {
			getHelpSystem().setHelp(checkBox.getCheckBox(), helpId);
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
	 * @param helpId The topic help ID to be registered for the new check box
	 * @return The newly created <code>TriStateCheckBox</code>
	 *
	 * @category Layout
	 */
	protected final TriStateCheckBox addTriStateCheckBoxWithDefault(Composite parent,
	                                                                  String text,
	                                                                  ModifiablePropertyValueModel<Boolean> booleanHolder,
	                                                                  PropertyValueModel<String> stringHolder,
	                                                                  String helpId) {

		TriStateCheckBox checkBox = this.addTriStateCheckBox(
			parent,
			text,
			booleanHolder,
			helpId
		);

		new LabeledControlUpdater(
			new LabeledButton(checkBox.getCheckBox()),
			stringHolder
		);

		return checkBox;
	}

	protected final TriStateCheckBox addTriStateCheckBoxWithDefault(Composite parent,
			        String text,
			        ModifiablePropertyValueModel<Boolean> booleanHolder,
			        PropertyValueModel<String> stringHolder,
			        PropertyValueModel<Boolean> enabledModel,
			        String helpId) {
			
		TriStateCheckBox checkBox = this.addUnmanagedTriStateCheckBox(parent, text, booleanHolder, helpId);
		
		this.controlEnabledState(enabledModel, checkBox.getCheckBox());
		
		new LabeledControlUpdater(
			new LabeledButton(checkBox.getCheckBox()),
			stringHolder
		);
		
		return checkBox;
	}

	/**
	 * Requests this pane to populate its widgets with the subject's values.
	 *
	 * @category Populate
	 */
	protected void doPopulate() {
		JptCommonUiPlugin.instance().trace(TRACE_OPTION, "doPopulate");
	}

	private void controlEnabledState(Control... controls) {
		SWTTools.controlEnabledState(getEnabledModel(), controls);
	}

	private void controlEnabledState(PropertyValueModel<Boolean> booleanModel, Control... controls) {
		this.controlEnabledState_(this.wrapEnabledModel(booleanModel), controls);
	}

	/**
	 * Assume the "enabled" models can return null (which is typical with aspect
	 * adapters etc.).
	 */
	private PropertyValueModel<Boolean> wrapEnabledModel(PropertyValueModel<Boolean> booleanModel) {
		return new TransformationPropertyValueModel<Boolean, Boolean>(booleanModel, NonNullBooleanTransformer.FALSE);
	}

	private void controlEnabledState_(PropertyValueModel<Boolean> booleanModel, Control... controls) {
		SWTTools.controlEnabledState(this.andEnabledModel(booleanModel), controls);
	}

	protected PropertyValueModel<Boolean> getEnabledModel() {
		return this.enabledModel;
	}

	@SuppressWarnings("unchecked")
	private PropertyValueModel<Boolean> andEnabledModel(PropertyValueModel<Boolean> booleanModel) {
		return CompositeBooleanPropertyValueModel.and(getEnabledModel(), booleanModel);
	}

	private void engageSubjectHolder() {
		this.subjectModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.subjectChangeListener);
	}

	/**
	 * engage the specified subject
	 */
	protected void engageListeners(T subject) {
		if (subject != null) {
			this.engageListeners_(subject);
		}
	}

	/**
	 * specified subject is not null
	 */
	protected void engageListeners_(T subject) {
		JptCommonUiPlugin.instance().trace(TRACE_OPTION, "engageListeners_({0})", subject);

		for (String propertyName : this.getPropertyNames()) {
			subject.addPropertyChangeListener(propertyName, this.aspectChangeListener);
		}
	}

	/**
	 * disengage the specified subject
	 */
	protected void disengageListeners(T subject) {
		if (subject != null) {
			this.disengageListeners_(subject);
		}
	}

	/**
	 * specified subject is not null
	 */
	protected void disengageListeners_(T subject) {
		JptCommonUiPlugin.instance().trace(TRACE_OPTION, "disengageListeners_({0})", subject);

		for (String propertyName : this.getPropertyNames()) {
			subject.removePropertyChangeListener(propertyName, this.aspectChangeListener);
		}
	}

	private void disengageSubjectHolder() {
		this.subjectModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.subjectChangeListener);
	}

	/**
	 * Returns the main <code>Composite</code> of this pane.
	 *
	 * @return The main container
	 *
	 * @category Layout
	 */
	public Control getControl() {
		if (!addsComposite()) {
			throw new IllegalStateException("Must override getControl() if addsComposite() returns false");
		}
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
		return this.subjectModel;
	}

	/**
	 * Returns the factory responsible for creating the widgets.
	 *
	 * @return The factory used by this pane to create the widgets
	 *
	 * @category Layout
	 */
	protected final WidgetFactory getWidgetFactory() {
		return this.widgetFactory;
	}

	/**
	 * Returns the helps system.
	 *
	 * @return The platform's help system
	 *
	 * @category Helper
	 */
	protected final IWorkbenchHelpSystem getHelpSystem() {
		return PlatformUI.getWorkbench().getHelpSystem();
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
	 * Notifies this pane to populate itself using the subject's information.
	 *
	 * @category Populate
	 */
	private void populate() {
		if (!this.getControl().isDisposed()) {
			JptCommonUiPlugin.instance().trace(TRACE_OPTION, "populate");
			this.repopulate();
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
	protected Collection<String> getPropertyNames() {
		ArrayList<String> propertyNames = new ArrayList<String>();
		addPropertyNames(propertyNames);
		return propertyNames;
	}

	/**
	 * This method is called (perhaps internally) when this needs to repopulate
	 * but the object of interest has not changed.
	 *
	 * @category Populate
	 */
	protected final void repopulate() {
		JptCommonUiPlugin.instance().trace(TRACE_OPTION, "repopulate");

		// Populate this pane
		try {
			setPopulating(true);
			doPopulate();
		}
		finally {
			setPopulating(false);
		}

		// Ask the sub-panes to repopulate themselves
		for (Pane<?> subPane : this.subPanes) {
			subPane.repopulate();
		}
	}

	/**
	 * Sets the internal flag that is used to determine whether the pane is being
	 * populated or not. During population, it is required to not update the
	 * widgets when the model is updated nor to update the model when the widgets
	 * are being synchronized with the model's values.
	 *
	 * @param populating
	 *
	 * @category Populate
	 */
	protected final void setPopulating(boolean populating) {
		this.populating = populating;
	}

	/**
	 * Either show or hides this pane.
	 *
	 * @param visible The new visibility state
	 */
	public void setVisible(boolean visible) {
		if (this.container != null && !this.container.isDisposed()) {
			this.container.setVisible(visible);
		}
	}

	/**
	 * Returns the nearest <code>Shell</code> displaying the main widget of this
	 * pane.
	 *
	 * @return The nearest window displaying this pane
	 */
	public final Shell getShell() {
		return this.getControl().getShell();
	}

	/**
	 * Returns the subject of this pane.
	 *
	 * @return The subject if this pane was not disposed; <code>null</code>
	 * if it was
	 *
	 * @category Populate
	 */
	public T getSubject() {
		return this.subjectModel.getValue();
	}

	/**
	 * The subject has changed, disconnects any listeners from the old subject
	 * and connects those listeners onto the new subject.
	 *
	 * @param oldsubject The old subject or <code>null</code> if none was set
	 * @param newSubject The new subject or <code>null</code> if none needs to be
	 * set
	 *
	 * @category Populate
	 */
	protected final void subjectChanged(T oldSubject, T newSubject) {
		if (!this.getControl().isDisposed()) {

			JptCommonUiPlugin.instance().trace(TRACE_OPTION, "subjectChanged({0}, {1})", oldSubject, newSubject);
			this.disengageListeners(oldSubject);

			this.repopulate();

			this.engageListeners(newSubject);
		}
	}


	/**
	 * Registers another <code>Pane</code> with this one so it can
	 * be automatically notified about certain events such as engaging or
	 * disengaging the listeners, etc.
	 *
	 * @param subPane The sub-pane to register
	 *
	 * @category Controller
	 */
	protected final void registerSubPane(Pane<?> subPane) {
		this.subPanes.add(subPane);
	}

	/**
	 * Unregisters the given <code>Pane</code> from this one so it
	 * can no longer be automatically notified about certain events such as
	 * engaging or disengaging the listeners, etc.
	 *
	 * @param subPane The sub-pane to unregister
	 *
	 * @category Controller
	 */
	protected final void unregisterSubPane(Pane<?> subPane) {
		this.subPanes.remove(subPane);
	}

	private void updatePane(String propertyName) {
		if (!isPopulating() && !this.getControl().isDisposed()) {
			this.populating = true;

			try {
				propertyChanged(propertyName);
			}
			finally {
				this.populating = false;
			}
		}
	}

	protected void controlDisposed() {
		// the control is not yet "disposed" when we receive this event
		// so we can still remove our listeners
		JptCommonUiPlugin.instance().trace(TRACE_OPTION, "dispose");

		// Dispose this pane
		this.disengageListeners(getSubject());
		this.disengageSubjectHolder();

		this.getControl().removeDisposeListener(this.controlDisposeListener);
	}

	private static final String TRACE_OPTION = Pane.class.getSimpleName();
}

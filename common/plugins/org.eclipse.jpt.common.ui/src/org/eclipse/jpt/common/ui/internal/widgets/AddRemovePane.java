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

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.listeners.SWTListChangeListenerWrapper;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * The abstract definition of a pane that has buttons for adding, removing and
 * possibly editing the items.
 *
 * @see AddRemoveListPane
 *
 * @version 1.0
 * @since 2.0
 */
public abstract class AddRemovePane<T extends Model, E extends Object> extends Pane<T>
{
	private Adapter<E> adapter;
	private Button addButton;
	private IBaseLabelProvider labelProvider;
	private ListValueModel<?> listHolder;
	private Button optionalButton;
	private Button removeButton;
	private ModifiableCollectionValueModel<E> selectedItemsModel;

	/**
	 * Creates a new <code>AddRemovePane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param adapter This <code>Adapter</code> is used to dictate the behavior
	 * of this <code>AddRemovePane</code> and by delegating to it some of the
	 * behavior
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemsModel The holder of the selected items
	 * @param labelProvider The renderer used to format the list holder's items
	 */
	protected AddRemovePane(Pane<? extends T> parentPane,
	                        Composite parent,
	                        Adapter<E> adapter,
	                        ListValueModel<?> listHolder,
	                        ModifiableCollectionValueModel<E> selectedItemsModel,
	                        IBaseLabelProvider labelProvider) {

		this(parentPane,
		     parent,
		     adapter,
		     listHolder,
		     selectedItemsModel,
		     labelProvider,
		     null);
	}

	/**
	 * Creates a new <code>AddRemovePane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param adapter This <code>Adapter</code> is used to dictacte the behavior
	 * of this <code>AddRemovePane</code> and by delegating to it some of the
	 * behavior
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemsModel The holder of the selected items
	 * @param labelProvider The renderer used to format the list holder's items
	 * @param helpId The topic help ID to be registered with this pane
	 * @param parentManagePane <code>true</code> to have the parent pane manage
	 * the enabled state of this pane
	 */
	protected AddRemovePane(Pane<? extends T> parentPane,
	                        Composite parent,
	                        Adapter<E> adapter,
	                        ListValueModel<?> listHolder,
	                        ModifiableCollectionValueModel<E> selectedItemsModel,
	                        IBaseLabelProvider labelProvider,
	                        String helpId) {

		super(parentPane, parent);

		initialize(
			adapter,
			listHolder,
			selectedItemsModel,
			labelProvider
		);

		initializeLayout(
			adapter,
			listHolder,
			selectedItemsModel,
			labelProvider,
			helpId
		);
	}

	protected AddRemovePane(Pane<? extends T> parentPane,
        Composite parent,
        Adapter<E> adapter,
        ListValueModel<?> listHolder,
        ModifiableCollectionValueModel<E> selectedItemsModel,
        IBaseLabelProvider labelProvider,
        PropertyValueModel<Boolean> enabledModel,
        String helpId) {

		super(parentPane, parent, enabledModel);

		initialize(
			adapter,
			listHolder,
			selectedItemsModel,
			labelProvider
		);
		
		initializeLayout(
			adapter,
			listHolder,
			selectedItemsModel,
			labelProvider,
			helpId
		);
	}

	/**
	 * Creates a new <code>AddRemovePane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject
	 * @param adapter This <code>Adapter</code> is used to dictate the behavior
	 * of this <code>AddRemovePane</code> and by delegating to it some of the
	 * behavior
	 * @param parent The parent container
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemsModel The holder of the selected item
	 * @param labelProvider The renderer used to format the list holder's items
	 */
	protected AddRemovePane(Pane<?> parentPane,
	                        PropertyValueModel<? extends T> subjectHolder,
	                        Composite parent,
	                        Adapter<E> adapter,
	                        ListValueModel<?> listHolder,
	                        ModifiableCollectionValueModel<E> selectedItemsModel,
	                        IBaseLabelProvider labelProvider) {

		this(parentPane,
		     subjectHolder,
		     parent,
		     adapter,
		     listHolder,
		     selectedItemsModel,
		     labelProvider,
		     null);
	}

	/**
	 * Creates a new <code>AddRemovePane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject
	 * @param adapter This <code>Adapter</code> is used to dictate the behavior
	 * of this <code>AddRemovePane</code> and by delegating to it some of the
	 * behavior
	 * @param parent The parent container
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemsModel The holder of the selected item
	 * @param labelProvider The renderer used to format the list holder's items
	 * @param helpId The topic help ID to be registered with this pane
	 */
	protected AddRemovePane(Pane<?> parentPane,
	                        PropertyValueModel<? extends T> subjectHolder,
	                        Composite parent,
	                        Adapter<E> adapter,
	                        ListValueModel<?> listHolder,
	                        ModifiableCollectionValueModel<E> selectedItemsModel,
	                        IBaseLabelProvider labelProvider,
	                        String helpId) {

		super(parentPane, subjectHolder, parent);

		initialize(
			adapter,
			listHolder,
			selectedItemsModel,
			labelProvider
		);

		initializeLayout(
			adapter,
			listHolder,
			selectedItemsModel,
			labelProvider,
			helpId
		);
	}

	/**
	 * Gives the possibility to add buttons after the Add button and before the
	 * optional button.
	 *
	 * @param container The parent container
	 * @param helpId The topic help ID to be registered with the buttons
	 *
	 * @category Layout
	 */
	protected void addCustomButtonAfterAddButton(Composite container,
	                                             String helpId) {
	}

	/**
	 * Gives the possibility to add buttons after the optional button and before
	 * the Remove button.
	 *
	 * @param container The parent container
	 * @param helpId The topic help ID to be registered with the buttons
	 *
	 * @category Layout
	 */
	protected void addCustomButtonAfterOptionalButton(Composite container,
	                                                  String helpId) {
	}

	/**
	 * @category Add
	 */
	protected void addItem() {
		E item = this.adapter.addNewItem();
		if (item != null) {
			this.selectedItemsModel.setValues(new SingleElementIterable<E>(item));
		}
	}

	/**
	 * @category Initialize
	 */
	protected Adapter<E> buildAdapter() {
		return null;
	}

	/**
	 * @category Add
	 */
	protected Button addAddButton(Composite parent) {
		return addButton(
			parent,
			adapter.addButtonText(),
			buildAddItemAction()
		);
	}

	/**
	 * @category Add
	 */
	private Runnable buildAddItemAction() {
		return new Runnable() {
			public void run() {
				AddRemovePane.this.addItem();
			}
		};
	}

	private ListChangeListener buildListChangeListener() {
		return new SWTListChangeListenerWrapper(buildListChangeListener_());
	}

	private ListChangeListener buildListChangeListener_() {
		return new ListChangeListener() {

			public void itemsAdded(ListAddEvent e) {
				AddRemovePane.this.itemsAdded(e);
			}

			public void itemsMoved(ListMoveEvent e) {
				AddRemovePane.this.itemsMoved(e);
			}

			public void itemsRemoved(ListRemoveEvent e) {
				AddRemovePane.this.itemsRemoved(e);
			}

			public void itemsReplaced(ListReplaceEvent e) {
				AddRemovePane.this.itemsReplaced(e);
			}

			public void listChanged(ListChangeEvent e) {
				AddRemovePane.this.listChanged(e);
			}

			public void listCleared(ListClearEvent e) {
				AddRemovePane.this.listCleared(e);
			}
		};
	}

	protected void itemsAdded(ListAddEvent e) {
		
	}
	
	protected void itemsMoved(ListMoveEvent e) {
		
	}
	
	protected void itemsRemoved(ListRemoveEvent e) {
		//clear the selection if any of the removed items were part of the selection
		for (Object removedItem : e.getItems()) {
			if (CollectionTools.contains(this.selectedItemsModel, removedItem)) {
				this.selectedItemsModel.setValues(EmptyIterable.<E>instance());
				break;
			}
		}
	}
	
	protected void itemsReplaced(ListReplaceEvent e) {
		//Sometimes itemsReplaced happens when the subject changes, so clear the selectedItemsModel ??
		//this fixes bug 379274 - fixes orm where we use a CompositeListValueModel
		this.selectedItemsModel.setValues(EmptyListIterable.<E>instance());
	}
	
	protected void listChanged(ListChangeEvent e) {
		//Sometimes listChanged happens when the subject changes, so clear the selectedItemsModel ??
		//this fixes bug 379274 - fixes Java where we use a ListAspectAdapter
		this.selectedItemsModel.setValues(EmptyListIterable.<E>instance());
		
	}
	
	protected void listCleared(ListClearEvent e) {
		this.selectedItemsModel.setValues(EmptyListIterable.<E>instance());
	}
	
	
	/**
	 * @category Option
	 */
	private Runnable buildOptionalAction() {
		return new Runnable() {
			public void run() {
				AddRemovePane.this.editItem();
			}
		};
	}

	/**
	 * @category Option
	 */
	protected Button addOptionalButton(Composite container) {
		return addButton(
			container,
			adapter.optionalButtonText(),
			buildOptionalAction(),
			adapter.buildOptionalButtonEnabledModel(this.selectedItemsModel)
		);
	}

	/**
	 * @category Add
	 */
	protected Button addRemoveButton(Composite parent) {
		return addButton(
			parent,
			adapter.removeButtonText(),
			buildRemoveItemsAction(),
			adapter.buildRemoveButtonEnabledModel(this.selectedItemsModel)
		);
	}

	/**
	 * @category Remove
	 */
	private Runnable buildRemoveItemsAction() {
		return new Runnable() {
			public void run() {
				AddRemovePane.this.removeItems();
			}
		};
	}

	/**
	 * @category Option
	 */
	protected void editItem() {
		this.adapter.optionOnSelection(this.selectedItemsModel);
	}

	protected IBaseLabelProvider getLabelProvider() {
		return this.labelProvider;
	}

	protected final ListValueModel<?> getListHolder() {
		return this.listHolder;
	}

	/**
	 * Returns
	 *
	 * @return
	 */
	public abstract Composite getMainControl();

	protected final ModifiableCollectionValueModel<E> getSelectedItemsModel() {
		return this.selectedItemsModel;
	}

	/**
	 * Initializes this add/remove pane.
	 *
	 * @param adapter This <code>Adapter</code> is used to dictacte the behavior
	 * of this <code>AddRemovePane</code> and by delegating to it some of the
	 * behavior
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemsModel The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the list holder's items
	 *
	 * @category Initialization
	 */
	protected void initialize(Adapter<E> adapter,
	                          ListValueModel<?> listHolder,
	                          ModifiableCollectionValueModel<E> selectedItemsModel,
	                          IBaseLabelProvider labelProvider)
	{
		this.listHolder         = listHolder;
		this.labelProvider      = labelProvider;
		this.adapter            = (adapter == null) ? buildAdapter() : adapter;
		this.selectedItemsModel = selectedItemsModel;

		this.listHolder.addListChangeListener(
			ListValueModel.LIST_VALUES,
			buildListChangeListener()
		);
	}

	/**
	 * Initializes the pane containing the buttons (Add, optional (if required)
	 * and Remove).
	 *
	 * @param container The parent container
	 * @param helpId The topic help ID to be registered with the buttons
	 *
	 * @category Layout
	 */
	protected void initializeButtonPane(Composite container, String helpId) {
		container = addSubPane(container);

		GridData gridData = new GridData();
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalAlignment       = SWT.TOP;
		container.setLayoutData(gridData);

		// Add button
		this.addButton = addAddButton(container);

		// Custom button
		addCustomButtonAfterAddButton(container, helpId);

		// Optional button
		if (this.adapter.hasOptionalButton()) {
			this.optionalButton = addOptionalButton(container);
		}

		// Custom button
		addCustomButtonAfterOptionalButton(container, helpId);

		// Remove button
		this.removeButton = addRemoveButton(container);

		// Update the help topic ID
		if (helpId != null) {
			getHelpSystem().setHelp(this.addButton, helpId);
			getHelpSystem().setHelp(this.removeButton, helpId);

			if (this.optionalButton != null) {
				getHelpSystem().setHelp(this.optionalButton, helpId);
			}
		}
	}

	/**
	 * Initializes this add/remove pane by creating the widgets. The subclass is
	 * required to build the main widget.
	 *
	 * @param adapter This <code>Adapter</code> is used to dictate the behavior
	 * of this <code>AddRemovePane</code> and by delegating to it some of the
	 * behavior
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemsModel The holder of the selected item
	 * @param labelProvider The renderer used to format the list holder's items
	 * @param helpId The topic help ID to be registered with this pane
	 *
	 * @category Layout
	 */
	protected void initializeLayout(Adapter<E> adapter,
    	                             ListValueModel<?> listHolder,
   	                             ModifiableCollectionValueModel<E> selectedItemsModel,
   	                             IBaseLabelProvider labelProvider,
   	                             String helpId) {

		initializeMainComposite(
			(Composite) getControl(),
			adapter,
			listHolder,
			selectedItemsModel,
			labelProvider,
			helpId);

		initializeButtonPane((Composite) getControl(), helpId);
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return addSubPane(parent, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {
		//see other initializeLayout
	}

	/**
	 * Initializes the main widget of this add/remove pane.
	 *
	 * @param container The parent container
	 * @param adapter This <code>Adapter</code> is used to dictacte the behavior
	 * of this <code>AddRemovePane</code> and by delegating to it some of the
	 * behavior
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemsModel The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the list holder's items
	 * @param helpId The topic help ID to be registered with this pane or
	 * <code>null</code> if it was not specified
	 *
	 * @category Layout
	 */
	protected abstract void initializeMainComposite(Composite container,
	                                                Adapter<E> adapter,
	                   	                           ListValueModel<?> listHolder,
	                  	                           ModifiableCollectionValueModel<E> selectedItemsModel,
	                  	                           IBaseLabelProvider labelProvider,
	                  	                           String helpId);

	/**
	 * @category Remove
	 */
	protected void removeItems() {
		// Notify the adapter to remove the selected items
		this.adapter.removeSelectedItems(this.selectedItemsModel);
		//clear the selection
		this.selectedItemsModel.setValues(EmptyListIterable.<E>instance());
	}

	/**
	 * Selects the given value, which can be <code>null</code>.
	 *
	 * @param value The new selected value
	 */
	public void setSelectedItem(E value) {
		this.selectedItemsModel.setValues(new SingleElementIterable<E>(value));
	}


	/**
	 * This adapter is used to perform the actual action when adding a new item
	 * or removing the selected items. It is possible to add an optional button.
	 */
	public interface Adapter<E extends Object> {

		/**
		 * The add button's text.
		 *
		 * @return The text shown on the add button
		 */
		String addButtonText();

		/**
		 * Invoked when the user selects the Add button.
		 * Return the newly added item so it can be selected in the list
		 */
		E addNewItem();

		/**
		 * Build a PropertyValueModel to handle enablement of the remove button.
		 */
		PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<E> selectedItemsModel);

		/**
		 * Build a PropertyValueModel to handle enablement of the optional button.
		 */
		PropertyValueModel<Boolean> buildOptionalButtonEnabledModel(CollectionValueModel<E> selectedItemsModel);

		/**
		 * Determines whether an optional button should be added between the add
		 * and remove buttons.
		 *
		 * @return <code>true</code> to show an optional button and to use the
		 * behavior related to the optional button; <code>false</code> to not use
		 * it
		 */
		boolean hasOptionalButton();

		/**
		 * Resource string key for the optional button.
		 */
		String optionalButtonText();

		/**
		 * Invoked when the user selects the optional button
		 */
		void optionOnSelection(CollectionValueModel<E> selectedItemsModel);

		/**
		 * The remove button's text.
		 *
		 * @return The text shown on the remove button
		 */
		String removeButtonText();

		/**
		 * Invoked when the user selects the Remove button.
		 */
		void removeSelectedItems(CollectionValueModel<E> selectedItemsModel);
	}

	/**
	 * An abstract implementation of <code>Adapter</code>.
	 */
	public abstract static class AbstractAdapter<E extends Object> implements AddRemovePane.Adapter<E> {

		/**
		 * The text of the add button.
		 */
		private String addButtonText;

		/**
		 * Determines whether the optional button should be shown or not.
		 */
		private boolean hasOptionalButton;

		/**
		 * The text of the optional button, if used.
		 */
		private String optionalButtonText;

		/**
		 * The text of the remove button.
		 */
		private String removeButtonText;

		/**
		 * Creates a new <code>AbstractAdapter</code> with default text for the
		 * add and remove buttons.
		 */
		public AbstractAdapter() {
			this(JptCommonUiMessages.AddRemovePane_AddButtonText,
				JptCommonUiMessages.AddRemovePane_RemoveButtonText);
		}

		/**
		 * Creates a new <code>AbstractAdapter</code> with default text for the
		 * add and remove buttons.
		 *
		 * @param hasOptionalButton <code>true</code> to show an optional button
		 * and to use the behavior related to the optional button;
		 * <code>false</code> to not use it
		 */
		public AbstractAdapter(boolean hasOptionalButton) {
			this();
			this.setHasOptionalButton(hasOptionalButton);
		}

		/**
		 * Creates a new <code>AbstractAdapter</code> with default text for the
		 * add and remove buttons.
		 *
		 * @param optionalButtonText The text of the optional button, which means
		 * the optional button will be shown
		 */
		public AbstractAdapter(String optionalButtonText) {
			this(true);
			this.setOptionalButtonText(optionalButtonText);
		}

		/**
		 * Creates a new <code>AbstractAdapter</code>.
		 *
		 * @param addButtonText The add button's text
		 * @param removeButtonText The remove button's text
		 */
		public AbstractAdapter(String addButtonText,
		                       String removeButtonText) {

			super();
			this.addButtonText    = addButtonText;
			this.removeButtonText = removeButtonText;
		}

		/**
		 * Creates a new <code>AbstractAdapter</code>.
		 *
		 * @param addButtonText The add button's text
		 * @param removeButtonText The remove button's text
		 * @param optionalButtonText The text of the optional button, which means
		 * the optional button will be shown
		 */
		public AbstractAdapter(String addButtonText,
		                       String removeButtonText,
		                       String optionalButtonText) {

			this(optionalButtonText);
			this.setAddButtonText(addButtonText);
			this.setRemoveButtonText(removeButtonText);
		}

		/*
		 * (non-Javadoc)
		 */
		public String addButtonText() {
			return this.addButtonText;
		}

		public PropertyValueModel<Boolean> buildOptionalButtonEnabledModel(CollectionValueModel<E> selectedItemsModel) {
			return this.buildSingleSelectedItemEnabledModel(selectedItemsModel);
		}

		protected PropertyValueModel<Boolean> buildSingleSelectedItemEnabledModel(CollectionValueModel<E> selectedItemsModel) {
			return new CollectionPropertyValueModelAdapter<Boolean, Object>(selectedItemsModel) {
				@Override
				protected Boolean buildValue() {
					return Boolean.valueOf(this.collectionModel.size() == 1);
				}
			};
		}

		public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<E> selectedItemsModel) {
			return this.buildMultipleSelectedItemsEnabledModel(selectedItemsModel);
		}

		protected PropertyValueModel<Boolean> buildMultipleSelectedItemsEnabledModel(CollectionValueModel<E> selectedItemsModel) {
			return new CollectionPropertyValueModelAdapter<Boolean, E>(selectedItemsModel) {
				@Override
				protected Boolean buildValue() {
					return Boolean.valueOf(this.collectionModel.size() >= 1);
				}
			};
		}

		/*
		 * (non-Javadoc)
		 */
		public boolean hasOptionalButton() {
			return this.hasOptionalButton;
		}

		/*
		 * (non-Javadoc)
		 */
		public String optionalButtonText() {
			return this.optionalButtonText;
		}

		/*
		 * (non-Javadoc)
		 */
		public void optionOnSelection(CollectionValueModel<E> selectedItemsModel) {
			//override as necessary
		}

		/*
		 * (non-Javadoc)
		 */
		public String removeButtonText() {
			return this.removeButtonText;
		}

		/**
		 * Changes the text of the add button. This method has to be called before
		 * the <code>AddRemoveListPane</code> is initialized.
		 *
		 * @param addButtonText The add button's text
		 */
		public void setAddButtonText(String addButtonText) {
			this.addButtonText = addButtonText;
		}

		/**
		 * Changes the state of the optional button, meaning if it should be shown
		 * between the add and remove buttons or not.
		 *
		 * @param hasOptionalButton <code>true</code> to show an optional button
		 * and to use the behavior related to the optional button;
		 * <code>false</code> to not use it
		 */
		public void setHasOptionalButton(boolean hasOptionalButton) {
			this.hasOptionalButton = hasOptionalButton;
		}

		/**
		 * Changes the text of the optional button. This method has to be called
		 * before the <code>AddRemoveListPane</code> is initialized. This does not
		 * make the optional button visible.
		 *
		 * @param optionalButtonText The optional button's text
		 */
		public void setOptionalButtonText(String optionalButtonText) {
			this.optionalButtonText = optionalButtonText;
		}

		/**
		 * Changes the text of the remove button. This method has to be called
		 * before the <code>AddRemoveListPane</code> is initialized.
		 *
		 * @param removeButtonText The remove button's text
		 */
		public void setRemoveButtonText(String removeButtonText) {
			this.removeButtonText = removeButtonText;
		}
	}
}
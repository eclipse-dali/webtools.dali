/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.common.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.AbstractAdapter;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.common.utility.internal.model.SingleAspectChangeSupport;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaNode;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.eclipselink.core.context.ReadOnlyTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;

/**
 * Here is the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | AddRemoveListPane                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>

 *
 * @version 3.1
 * @since 3.1
 */
public class TenantDiscriminatorColumnsComposite<T extends JpaNode> extends Pane<T>
{
	/**
	 * The editor used to perform the common behaviors defined in the list pane.
	 */
	TenantDiscriminatorColumnsEditor<T> tenantDiscriminatorColumnsEditor;

	private AddRemoveListPane<T> listPane;
	private Pane<ReadOnlyTenantDiscriminatorColumn2_3> tenantDiscriminatorColumnPane;
	private ModifiablePropertyValueModel<ReadOnlyTenantDiscriminatorColumn2_3> tenantDiscriminatorColumnHolder;

	public TenantDiscriminatorColumnsComposite(Pane<? extends T> parentPane,
	                            Composite parent,
	                            TenantDiscriminatorColumnsEditor<T> tenantDiscriminatorColumnsEditor) {

		super(parentPane, parent);
		this.tenantDiscriminatorColumnsEditor = tenantDiscriminatorColumnsEditor;
		initializeLayout2();
	}

	public TenantDiscriminatorColumnsComposite(Pane<?> parentPane,
	                            PropertyValueModel<? extends T> subjectHolder,
	                            Composite parent,
	                            TenantDiscriminatorColumnsEditor<T> tenantDiscriminatorColumnsEditor,
	                            boolean automaticallyAlignWidgets) {

		super(parentPane, subjectHolder, parent, automaticallyAlignWidgets);
		this.tenantDiscriminatorColumnsEditor = tenantDiscriminatorColumnsEditor;
		initializeLayout2();
	}

	public TenantDiscriminatorColumnsComposite(PropertyValueModel<? extends T> subjectHolder,
	                            Composite parent,
	                            WidgetFactory widgetFactory,
	                            TenantDiscriminatorColumnsEditor<T> tenantDiscriminatorColumnsEditor) {

		super(subjectHolder, parent, widgetFactory);
		this.tenantDiscriminatorColumnsEditor = tenantDiscriminatorColumnsEditor;
		initializeLayout2();
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.tenantDiscriminatorColumnHolder = buildTenantDiscriminatorColumnHolder();
	}

	private ModifiablePropertyValueModel<ReadOnlyTenantDiscriminatorColumn2_3> buildTenantDiscriminatorColumnHolder() {
		return new SimplePropertyValueModel<ReadOnlyTenantDiscriminatorColumn2_3>();
	}

	@Override
	protected void initializeLayout(Composite container) {
		//see intiailizeLayout2()
	}

	private void initializeLayout2() {
		this.listPane = new AddRemoveListPane<T>(
			this,
			getControl(),
			buildTenantDiscriminatorColumnsAdapter(),
			buildTenantDiscriminatorColumnsListModel(),
			this.tenantDiscriminatorColumnHolder,
			buildTenantDiscriminatorColumnsListLabelProvider(),
			EclipseLinkHelpContextIds.MULTITENANCY_TENANT_DISCRIMINATOR_COLUMNS,
			false
		);

		// Property pane
		PageBook pageBook = new PageBook(getControl(), SWT.NULL);
		pageBook.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		//Tenant Discriminator Column property pane
		this.tenantDiscriminatorColumnPane = this.buildTenantDiscriminatorColumnComposite(pageBook);


		installPaneSwitcher(pageBook);
	}

	protected Pane<ReadOnlyTenantDiscriminatorColumn2_3> buildTenantDiscriminatorColumnComposite(PageBook pageBook) {
		return new TenantDiscriminatorColumnComposite(
			this,
			this.tenantDiscriminatorColumnHolder,
			pageBook
		);
	}

	private void installPaneSwitcher(PageBook pageBook) {
		new ControlSwitcher(this.tenantDiscriminatorColumnHolder, this.tenantDiscriminatorColumnPane.getControl(), pageBook);
	}

	String buildTenantDiscriminatorColumnLabel(ReadOnlyTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
		if (tenantDiscriminatorColumn.isVirtual() || tenantDiscriminatorColumn.getSpecifiedName() == null) {
			return NLS.bind(
				EclipseLinkUiDetailsMessages.TenantDiscriminatorColumnComposite_defaultTenantDiscriminatorColumnNameLabel,
				tenantDiscriminatorColumn.getName()
			);
		}
		return tenantDiscriminatorColumn.getName();
	}

	private Adapter buildTenantDiscriminatorColumnsAdapter() {
		return new AbstractAdapter() {

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				TenantDiscriminatorColumnsComposite.this.tenantDiscriminatorColumnsEditor.addTenantDiscriminatorColumn(getSubject());
			}

			@Override
			public boolean hasOptionalButton() {
				return false;
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				TenantDiscriminatorColumnsComposite.this.tenantDiscriminatorColumnsEditor.removeTenantDiscriminatorColumns(getSubject(), listSelectionModel.selectedIndices());
			}
		};
	}

	private ListValueModel<ReadOnlyTenantDiscriminatorColumn2_3> buildTenantDiscriminatorColumnsListModel() {
		return new LocalItemPropertyListValueModelAdapter<ReadOnlyTenantDiscriminatorColumn2_3>(buildTenantDiscriminatorColumnsListHolder(),
			ReadOnlyNamedColumn.SPECIFIED_NAME_PROPERTY,
			ReadOnlyNamedColumn.DEFAULT_NAME_PROPERTY);
	}

	private ListValueModel<ReadOnlyTenantDiscriminatorColumn2_3> buildTenantDiscriminatorColumnsListHolder() {
		List<ListValueModel<ReadOnlyTenantDiscriminatorColumn2_3>> list = new ArrayList<ListValueModel<ReadOnlyTenantDiscriminatorColumn2_3>>();
		list.add(buildDefaultTenantDiscriminatorColumnListHolder());
		list.add(buildSpecifiedTenantDiscriminatorColumnsListHolder());
		return new CompositeListValueModel<ListValueModel<ReadOnlyTenantDiscriminatorColumn2_3>, ReadOnlyTenantDiscriminatorColumn2_3>(list);
	}

	private ListValueModel<ReadOnlyTenantDiscriminatorColumn2_3> buildSpecifiedTenantDiscriminatorColumnsListHolder() {
		return new ListAspectAdapter<T, ReadOnlyTenantDiscriminatorColumn2_3>(getSubjectHolder(), this.tenantDiscriminatorColumnsEditor.getSpecifiedTenantDiscriminatorsListPropertyName()) {
			@Override
			protected ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getListIterable() {
				return TenantDiscriminatorColumnsComposite.this.tenantDiscriminatorColumnsEditor.getSpecifiedTenantDiscriminatorColumns(this.subject);
			}

			@Override
			protected int size_() {
				return TenantDiscriminatorColumnsComposite.this.tenantDiscriminatorColumnsEditor.getSpecifiedTenantDiscriminatorColumnsSize(this.subject);
			}
		};
	}

	private ListValueModel<ReadOnlyTenantDiscriminatorColumn2_3> buildDefaultTenantDiscriminatorColumnListHolder() {
		return new ListAspectAdapter<T, ReadOnlyTenantDiscriminatorColumn2_3>(getSubjectHolder(), this.tenantDiscriminatorColumnsEditor.getDefaultTenantDiscriminatorsListPropertyName()) {
			@Override
			protected ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getListIterable() {
				return TenantDiscriminatorColumnsComposite.this.tenantDiscriminatorColumnsEditor.getDefaultTenantDiscriminatorColumns(this.subject);
			}

			@Override
			protected int size_() {
				return TenantDiscriminatorColumnsComposite.this.tenantDiscriminatorColumnsEditor.getDefaultTenantDiscriminatorColumnsSize(this.subject);
			}
		};
	}

	private ILabelProvider buildTenantDiscriminatorColumnsListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return buildTenantDiscriminatorColumnLabel((ReadOnlyTenantDiscriminatorColumn2_3) element);
			}
		};
	}

	public void installListPaneEnabler(PropertyValueModel<Boolean> paneEnablerHolder) {
		new PaneEnabler(paneEnablerHolder, this.listPane);
	}

	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);
		this.listPane.enableWidgets(enabled);
	}

	public void setSelectedTenantDiscriminatorColumn(ReadOnlyTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
		this.listPane.setSelectedItem(tenantDiscriminatorColumn);
	}

	/**
	 * The editor is used to complete the behavior of this pane.
	 */
	public static interface TenantDiscriminatorColumnsEditor<T> {

		/**
		 * Add a tenant discriminator column to the given subject
		 */
		void addTenantDiscriminatorColumn(T subject);

		/**
		 * Return whether the subject has specified tenant discriminator columns
		 */
		boolean hasSpecifiedTenantDiscriminatorColumns(T subject);

		/**
		 * Return the specified tenant discriminator from the given subject
		 */
		ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getSpecifiedTenantDiscriminatorColumns(T subject);

		/**
		 * Return the number of specified join columns on the given subject
		 */
		int getSpecifiedTenantDiscriminatorColumnsSize(T subject);

		/**
		 * Return the default tenant discriminator columns from the given subject or null.
		 */
		ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getDefaultTenantDiscriminatorColumns(T subject);

		/**
		 * Return the number of default tenant discriminator on the given subject
		 */
		int getDefaultTenantDiscriminatorColumnsSize(T subject);

		/**
		 * Return the property name of the specified tenant discriminator columns list
		 */
		String getSpecifiedTenantDiscriminatorsListPropertyName();

		/**
		 * Return the property name of the default tenant discriminator columns list
		 */
		String getDefaultTenantDiscriminatorsListPropertyName();

		/**
		 * Remove the tenant discriminator columns at the specified indices from the subject
		 */
		void removeTenantDiscriminatorColumns(T subject, int[] selectedIndices);
	}

	private class LocalItemPropertyListValueModelAdapter<E> extends ItemPropertyListValueModelAdapter<E> {

		public LocalItemPropertyListValueModelAdapter(ListValueModel<E> listHolder, String... propertyNames) {
			super(listHolder, propertyNames);
		}

		public LocalItemPropertyListValueModelAdapter(CollectionValueModel<E> collectionHolder, String[] propertyNames) {
			super(collectionHolder, propertyNames);
		}

		/**
		 * bug 310720
		 * Override to just fire an itemReplaced event instead of a listChanged event.
		 * An aspect of the item as changed, so no reason to say that the entire list has changed.
		 * Added a LocalChangeSupport so that I can fire a ListReplacedEvent for an old list
		 * and new list containing the same item.
		 */
		@Override
		protected void itemAspectChanged(EventObject event) {
			Object item = event.getSource();
			this.getChangeSupport().fireItemsReplaced(
				new ListReplaceEvent(this, LIST_VALUES, CollectionTools.indexOf(this.listHolder, item), item, item));
		}

		@Override
		protected ChangeSupport buildChangeSupport() {
			return new LocalChangeSupport(this, ListChangeListener.class, ListValueModel.LIST_VALUES);
		}

		private class LocalChangeSupport extends SingleAspectChangeSupport {
			public LocalChangeSupport(Model source, Class<? extends EventListener> validListenerClass, String validAspectName) {
				super(source, validListenerClass, validAspectName);
			}
			@Override
			public boolean fireItemsReplaced(ListReplaceEvent event) {
				this.check(LIST_CHANGE_LISTENER_CLASS, event.getListName());
				if (event.getItemsSize() != 0) {
					this.fireItemsReplaced_(event);
					return true;
				}
				return false;
			}
		}
	}
}
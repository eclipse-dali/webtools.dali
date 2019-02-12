/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.persistence;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.swt.ColumnAdapter;
import org.eclipse.jpt.common.ui.internal.swt.widgets.ControlTools;
import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveTablePane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.persistence.JptJpaUiPersistenceMessages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class PersistenceUnitPropertiesEditorPage
	extends Pane<PersistenceUnit> 
{
	private ModifiableCollectionValueModel<PersistenceUnit.Property> selectedPropertiesModel;
	private TablePane tablePane;


	public PersistenceUnitPropertiesEditorPage(
			PropertyValueModel<PersistenceUnit> persistenceUnitModel,
            Composite parentComposite,
            WidgetFactory widgetFactory,
            ResourceManager resourceManager) {
		super(persistenceUnitModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedPropertiesModel = new SimpleCollectionValueModel<PersistenceUnit.Property>();
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.addLabel(
			container,
			JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_PROPERTIES_COMPOSITE_PROPERTIES_DESCRIPTION
		);

		this.tablePane = new TablePane(container);
	}

	private ListValueModel<PersistenceUnit.Property> buildPropertiesListModel() {
		return new ListAspectAdapter<PersistenceUnit, PersistenceUnit.Property>(getSubjectHolder(), PersistenceUnit.PROPERTIES_LIST) {
			@Override
			protected ListIterable<PersistenceUnit.Property> getListIterable() {
				return subject.getProperties();
			}

			@Override
			protected int size_() {
				return subject.getPropertiesSize();
			}
		};
	}

	private ITableLabelProvider buildPropertyLabelProvider() {
		return new TableLabelProvider();
	}

	private AddRemoveTablePane.Adapter<PersistenceUnit.Property> buildTableAdapter() {
		return new AddRemoveTablePane.AbstractAdapter<PersistenceUnit.Property>() {
			public PersistenceUnit.Property addNewItem() {

				PersistenceUnit.Property property = getSubject().addProperty();

				tablePane.getTableViewer().editElement(
					property,
					PropertyColumnAdapter.NAME_COLUMN
				);
				return property;
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<PersistenceUnit.Property> selectedItemsModel) {
				//enable the remove button only when 1 item is selected, same as the optional button
				return buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<PersistenceUnit.Property> selectedItemsModel) {
				//assume only 1 item since remove button is disabled otherwise
				PersistenceUnit.Property property = selectedItemsModel.iterator().next();
				getSubject().removeProperty(property);
			}
		};
	}

	private static class PropertyColumnAdapter implements ColumnAdapter<PersistenceUnit.Property> {

		public static final int COLUMN_COUNT = 3;
		public static final int NAME_COLUMN = 1;
		public static final int SELECTION_COLUMN = 0;
		public static final int VALUE_COLUMN = 2;

		private ModifiablePropertyValueModel<String> buildNameModel(PersistenceUnit.Property subject) {
			return new PropertyAspectAdapter<PersistenceUnit.Property, String>(PersistenceUnit.Property.NAME_PROPERTY, subject) {
				@Override
				protected String buildValue_() {
					return subject.getName();
				}

				@Override
				protected void setValue_(String value) {
					subject.setName(value);
				}
			};
		}

		private ModifiablePropertyValueModel<String> buildValueModel(PersistenceUnit.Property subject) {
			return new PropertyAspectAdapter<PersistenceUnit.Property, String>(PersistenceUnit.Property.VALUE_PROPERTY, subject) {
				@Override
				protected String buildValue_() {
					return subject.getValue();
				}

				@Override
				protected void setValue_(String value) {
					subject.setValue(value);
				}
			};
		}

		public ModifiablePropertyValueModel<?>[] cellModels(PersistenceUnit.Property subject) {
			ModifiablePropertyValueModel<?>[] models = new ModifiablePropertyValueModel<?>[COLUMN_COUNT];
			models[SELECTION_COLUMN] = new SimplePropertyValueModel<Object>();
			models[NAME_COLUMN]      = buildNameModel(subject);
			models[VALUE_COLUMN]     = buildValueModel(subject);
			return models;
		}

		public int columnCount() {
			return COLUMN_COUNT;
		}

		public String columnName(int columnIndex) {

			switch (columnIndex) {
				case PropertyColumnAdapter.NAME_COLUMN: {
					return JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_PROPERTIES_COMPOSITE_NAME_COLUMN;
				}

				case PropertyColumnAdapter.VALUE_COLUMN: {
					return JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_PROPERTIES_COMPOSITE_VALUE_COLUMN;
				}

				default: {
					return null;
				}
			}
		}
	}

	private class TableLabelProvider extends LabelProvider
	                                 implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {

			PersistenceUnit.Property property = (PersistenceUnit.Property) element;
			String value = null;

			switch (columnIndex) {
				case PropertyColumnAdapter.NAME_COLUMN: {
					value = property.getName();
					break;
				}

				case PropertyColumnAdapter.VALUE_COLUMN: {
					value = property.getValue();
					break;
				}
			}

			if (value == null) {
				value = "";
			}

			return value;
		}
	}

	private class TablePane extends AddRemoveTablePane<PersistenceUnit, PersistenceUnit.Property> {

		private final String SELECTION_COLUMN = "selection";

		private TableViewer tableViewer;

		private TablePane(Composite parent) {
			super(PersistenceUnitPropertiesEditorPage.this,
					parent,
					buildTableAdapter(),
					buildPropertiesListModel(),
					selectedPropertiesModel,
					buildPropertyLabelProvider());
		}

		private CellEditor[] buildCellEditors(Table table) {
			return new CellEditor[] {
				null,
				new TextCellEditor(table),
				new TextCellEditor(table)
			};
		}

		private ICellModifier buildCellModifier() {
			return new ICellModifier() {

				public boolean canModify(Object element, String property) {
					return !SELECTION_COLUMN.equals(property);
				}

				public Object getValue(Object element, String property) {
					PersistenceUnit.Property propertyModel = (PersistenceUnit.Property) element;
					String value = null;

					if (property == PersistenceUnit.Property.NAME_PROPERTY) {
						value = propertyModel.getName();
					}
					else if (property == PersistenceUnit.Property.VALUE_PROPERTY) {
						value = propertyModel.getValue();
					}

					if (value == null) {
						value = "";
					}

					return value;
				}

				public void modify(Object element, String property, Object value) {
					PersistenceUnit.Property propertyModel;

					if (element instanceof TableItem) {
						TableItem tableItem = (TableItem) element;
						propertyModel = (PersistenceUnit.Property) tableItem.getData();
					}
					else {
						propertyModel = (PersistenceUnit.Property) element;
					}

					if (property == PersistenceUnit.Property.NAME_PROPERTY) {
						propertyModel.setName(value.toString());
					}
					else if (property == PersistenceUnit.Property.VALUE_PROPERTY) {
						propertyModel.setValue(value.toString());
					}
				}
			};
		}

		@Override
		protected ColumnAdapter<PersistenceUnit.Property> buildColumnAdapter() {
			return new PropertyColumnAdapter();
		}

		private String[] buildColumnProperties() {
			return new String[] {
				SELECTION_COLUMN,
				PersistenceUnit.Property.NAME_PROPERTY,
				PersistenceUnit.Property.VALUE_PROPERTY
			};
		}

		TableViewer getTableViewer() {
			return tableViewer;
		}

		@Override
		protected void initializeMainComposite(Composite container,
		                                       Adapter<PersistenceUnit.Property> adapter,
		                                       ListValueModel<?> listHolder,
		                                       ModifiableCollectionValueModel<PersistenceUnit.Property> selectedItemsHolder,
		                                       IBaseLabelProvider labelProvider,
		                                       String helpId) {

			super.initializeMainComposite(
				container,
				adapter,
				listHolder,
				selectedItemsHolder,
				labelProvider,
				helpId
			);

			Table table = getMainControl();
			table.setLayoutData(new GridData(GridData.FILL_BOTH));

			// Make the selection column non-resizable since it's only used to
			// ease the selection of rows
			TableColumn selectionColumn = table.getColumn(PropertyColumnAdapter.SELECTION_COLUMN);
			selectionColumn.setResizable(false);
			selectionColumn.setWidth(20);

			// Install the editors
			tableViewer = new TableViewer(table);
			tableViewer.setCellEditors(buildCellEditors(table));
			tableViewer.setCellModifier(buildCellModifier());
			tableViewer.setColumnProperties(buildColumnProperties());
		}

		@Override
		protected void itemsAdded(ListAddEvent e) {
			super.itemsAdded(e);
			revalidateLayout();
		}

		@Override
		protected void itemsRemoved(ListRemoveEvent e) {
			super.itemsRemoved(e);
			revalidateLayout();
		}

		@Override
		protected void listChanged(ListChangeEvent e) {
			super.listChanged(e);
			revalidateLayout();
		}

		/**
		 * Revalidates the table layout after the list of items has changed. The
		 * layout has to be done in a new UI thread because our listener might be
		 * notified before the table has been updated (table column added or removed).
		 */
		private void revalidateLayout() {
			DisplayTools.asyncExec(new Runnable() { public void run() {
				Table table = getMainControl();
				if (!table.isDisposed()) {
					// We have to do a total relayout of the tab otherwise the
					// table might become cut off at the bottom
					ControlTools.reflow(table);
				}
			}});
		}
	}
}
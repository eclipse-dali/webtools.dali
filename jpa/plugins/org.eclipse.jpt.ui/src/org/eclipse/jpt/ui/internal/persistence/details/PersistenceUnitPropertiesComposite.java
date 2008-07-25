/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.persistence.details;

import java.util.ListIterator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaPageComposite;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.persistence.JptUiPersistenceMessages;
import org.eclipse.jpt.ui.internal.swt.ColumnAdapter;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveTablePane;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | - Properties ------------------------------------------------------------ |
 * |                                                                           |
 * |   Description                                                             |
 * |                                                                           |
 * |   ----------------------------------------------------------------------- |
 * |   |                                                                     | |
 * |   | AddRemoveTablePane                                                  | |
 * |   |                                                                     | |
 * |   ----------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see PersistenceUnit
 * @see BaseJpaUiFactory - The invoker
 * @see AddRemoveTablePane
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public class PersistenceUnitPropertiesComposite extends AbstractPane<PersistenceUnit>
                                                implements JpaPageComposite
{
	private WritablePropertyValueModel<Property> propertyHolder;
	private TablePane tablePane;

	/**
	 * Creates a new <code>PersistenceUnitPropertiesComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public PersistenceUnitPropertiesComposite(PropertyValueModel<PersistenceUnit> subjectHolder,
	                                          Composite container,
	                                          WidgetFactory widgetFactory) {

		super(subjectHolder, container, widgetFactory);
	}

	private ListValueModel<Property> buildPropertiesListHolder() {
		return new ListAspectAdapter<PersistenceUnit, Property>(getSubjectHolder(), PersistenceUnit.PROPERTIES_LIST) {
			@Override
			protected ListIterator<Property> listIterator_() {
				return subject.properties();
			}

			@Override
			protected int size_() {
				return subject.propertiesSize();
			}
		};
	}

	private ITableLabelProvider buildPropertyLabelProvider() {
		return new TableLabelProvider();
	}

	private AddRemoveTablePane.Adapter buildTableAdapter() {
		return new AddRemoveTablePane.AbstractAdapter() {
			public void addNewItem(ObjectListSelectionModel listSelectionModel) {

				Property property = subject().addProperty();
				propertyHolder.setValue(property);

				int index = subject().propertiesSize() - 1;
				TableItem tableItem = tablePane.getMainControl().getItem(index);
				tablePane.getMainControl().showItem(tableItem);

				tablePane.getTableViewer().editElement(
					property,
					PropertyColumnAdapter.NAME_COLUMN
				);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				for (Object item : listSelectionModel.selectedValues()) {
					subject().removeProperty((Property) item);
				}
			}
		};
	}

	/**
	 * {@inheritDoc}
	 */
	public String getHelpID() {
		return JpaHelpContextIds.PERSISTENCE_UNIT_PROPERTIES;
	}

	/**
	 * {@inheritDoc}
	 */
	public Image getPageImage() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getPageText() {
		return JptUiPersistenceMessages.PersistenceUnitPropertiesComposite_properties;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initialize() {
		super.initialize();
		propertyHolder = new SimplePropertyValueModel<Property>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initializeLayout(Composite container) {

		buildLabel(
			container,
			JptUiPersistenceMessages.PersistenceUnitPropertiesComposite_properties_description
		);

		tablePane = new TablePane(container);
	}

	private static class PropertyColumnAdapter implements ColumnAdapter<Property> {

		public static final int COLUMN_COUNT = 3;
		public static final int NAME_COLUMN = 1;
		public static final int SELECTION_COLUMN = 0;
		public static final int VALUE_COLUMN = 2;

		private WritablePropertyValueModel<String> buildNameHolder(Property subject) {
			return new PropertyAspectAdapter<Property, String>(Property.NAME_PROPERTY, subject) {
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

		private WritablePropertyValueModel<String> buildValueHolder(Property subject) {
			return new PropertyAspectAdapter<Property, String>(Property.VALUE_PROPERTY, subject) {
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

		public WritablePropertyValueModel<?>[] cellModels(Property subject) {
			WritablePropertyValueModel<?>[] holders = new WritablePropertyValueModel<?>[COLUMN_COUNT];
			holders[SELECTION_COLUMN] = new SimplePropertyValueModel<Object>();
			holders[NAME_COLUMN]      = buildNameHolder(subject);
			holders[VALUE_COLUMN]     = buildValueHolder(subject);
			return holders;
		}

		public int columnCount() {
			return COLUMN_COUNT;
		}

		public String columnName(int columnIndex) {

			switch (columnIndex) {
				case PropertyColumnAdapter.NAME_COLUMN: {
					return JptUiPersistenceMessages.PersistenceUnitPropertiesComposite_nameColumn;
				}

				case PropertyColumnAdapter.VALUE_COLUMN: {
					return JptUiPersistenceMessages.PersistenceUnitPropertiesComposite_valueColumn;
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

			Property property = (Property) element;
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

	private class TablePane extends AddRemoveTablePane<PersistenceUnit> {

		private final String SELECTION_COLUMN = "selection";

		private TableViewer tableViewer;

		private TablePane(Composite parent) {
			super(PersistenceUnitPropertiesComposite.this,
			      parent,
					buildTableAdapter(),
					buildPropertiesListHolder(),
					propertyHolder,
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
					Property propertyModel = (Property) element;
					String value = null;

					if (property == Property.NAME_PROPERTY) {
						value = propertyModel.getName();
					}
					else if (property == Property.VALUE_PROPERTY) {
						value = propertyModel.getValue();
					}

					if (value == null) {
						value = "";
					}

					return value;
				}

				public void modify(Object element, String property, Object value) {
					Property propertyModel;

					if (element instanceof TableItem) {
						TableItem tableItem = (TableItem) element;
						propertyModel = (Property) tableItem.getData();
					}
					else {
						propertyModel = (Property) element;
					}

					if (property == Property.NAME_PROPERTY) {
						propertyModel.setName(value.toString());
					}
					else if (property == Property.VALUE_PROPERTY) {
						propertyModel.setValue(value.toString());
					}
				}
			};
		}

		@Override
		protected ColumnAdapter<?> buildColumnAdapter() {
			return new PropertyColumnAdapter();
		}

		private String[] buildColumnProperties() {
			return new String[] {
				SELECTION_COLUMN,
				Property.NAME_PROPERTY,
				Property.VALUE_PROPERTY
			};
		}

		@Override
		protected Composite buildContainer(Composite parent) {
			Composite container = super.buildContainer(parent);
			container.setLayoutData(new GridData(GridData.FILL_BOTH));
			return container;
		}

		TableViewer getTableViewer() {
			return tableViewer;
		}

		@Override
		protected void initializeMainComposite(Composite container,
		                                       Adapter adapter,
		                                       ListValueModel<?> listHolder,
		                                       WritablePropertyValueModel<?> selectedItemHolder,
		                                       IBaseLabelProvider labelProvider,
		                                       String helpId) {

			super.initializeMainComposite(
				container,
				adapter,
				listHolder,
				selectedItemHolder,
				labelProvider,
				helpId
			);

			Table table = getMainControl();

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

			getContainer().setLayoutData(new GridData(GridData.FILL_BOTH));
			table.setLayoutData(new GridData(GridData.FILL_BOTH));
		}
	}
}
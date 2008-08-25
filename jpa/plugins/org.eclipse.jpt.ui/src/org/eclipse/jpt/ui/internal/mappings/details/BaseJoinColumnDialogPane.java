/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.ArrayList;
import java.util.ListIterator;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.DialogPane;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                         ------------------------------------------------- |
 * | Name:                   | I                                           |v| |
 * |                         ------------------------------------------------- |
 * |                         ------------------------------------------------- |
 * | Referenced Column Name: | I                                           |v| |
 * |                         ------------------------------------------------- |
 * |                         ------------------------------------------------- |
 * | Table:                  | I                                           |v| |
 * |                         ------------------------------------------------- |
 * |                         ------------------------------------------------- |
 * | Column Definition:      | I                                             | |
 * |                         ------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see BaseJoinColumnStateObject
 * @see InverseJoinColumnInJoinTableDialog - A container of this pane
 * @see JoinColumnInJoinTableDialog - A container of this pane
 * @see PrimaryKeyJoinColumnDialog - A container of this pane
 * @see PrimaryKeyJoinColumnInSecondaryTableDialog - A container of this pane
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public class BaseJoinColumnDialogPane<T extends BaseJoinColumnStateObject> extends DialogPane<T>
{
	/**
	 * A key used to represent the default value, this is required to convert
	 * the selected item from a combo to <code>null</code>. This key is most
	 * likely never typed the user and it will help to convert the value to
	 * <code>null</code> when it's time to set the new selected value into the
	 * model.
	 */
	protected static String DEFAULT_KEY = "?!#!?#?#?default?#?!#?!#?";

	/**
	 * Creates a new <code>BaseJoinColumnDialogPane</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public BaseJoinColumnDialogPane(PropertyValueModel<? extends T> subjectHolder,
	                                Composite parent) {

		super(subjectHolder, parent);
	}

	private WritablePropertyValueModel<String> buildColumnDefinitionHolder() {
		return new PropertyAspectAdapter<BaseJoinColumnStateObject, String>(getSubjectHolder(), BaseJoinColumnStateObject.COLUMN_DEFINITION_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getColumnDefinition();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setColumnDefinition(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Composite addContainer(Composite parent) {
		return addSubPane(parent, 0, 7, 0, 5);
	}

	private PropertyValueModel<String> buildDefaultNameHolder() {
		return new TransformationPropertyValueModel<BaseJoinColumnStateObject, String>(getSubjectHolder()) {
			@Override
			protected String transform_(BaseJoinColumnStateObject value) {
				String name = value.getDefaultName();

				if (name == null) {
					name = DEFAULT_KEY;
				}
				else {
					name = DEFAULT_KEY + name;
				}

				return name;
			}
		};
	}

	private ListValueModel<String> buildDefaultNameListHolder() {
		return new PropertyListValueModelAdapter<String>(
			buildDefaultNameHolder()
		);
	}

	private PropertyValueModel<String> buildDefaultReferencedColumnNameHolder() {
		return new TransformationPropertyValueModel<BaseJoinColumnStateObject, String>(getSubjectHolder()) {
			@Override
			protected String transform_(BaseJoinColumnStateObject value) {
				String name = value.getDefaultReferencedColumnName();

				if (name == null) {
					name = DEFAULT_KEY;
				}
				else {
					name = DEFAULT_KEY + name;
				}

				return name;
			}
		};
	}

	private ListValueModel<String> buildDefaultReferencedColumnNameListHolder() {
		return new PropertyListValueModelAdapter<String>(
			buildDefaultReferencedColumnNameHolder()
		);
	}

	private PropertyValueModel<String> buildDefaultTableHolder() {
		return new TransformationPropertyValueModel<BaseJoinColumnStateObject, String>(getSubjectHolder()) {
			@Override
			protected String transform_(BaseJoinColumnStateObject value) {
				String name = value.getDefaultTable();

				if (name == null) {
					name = DEFAULT_KEY;
				}
				else {
					name = DEFAULT_KEY + name;
				}

				return name;
			}
		};
	}

	private ListValueModel<String> buildDefaultTableListHolder() {
		return new PropertyListValueModelAdapter<String>(
			buildDefaultTableHolder()
		);
	}

	private StringConverter<String> buildDisplayableStringConverter(final DefaultValueHandler handler) {
		return new StringConverter<String>() {
			public String convertToString(String value) {

				if (getSubject() == null) {
					return null;
				}

				if (value == null) {
					value = handler.getDefaultValue();

					if (value != null) {
						value = DEFAULT_KEY + value;
					}
					else {
						value = DEFAULT_KEY;
					}
				}

				if (value.startsWith(DEFAULT_KEY)) {
					String defaultName = value.substring(DEFAULT_KEY.length());

					if (defaultName.length() > 0) {
						value = NLS.bind(
							JptUiMappingsMessages.DefaultWithValue,
							defaultName
						);
					}
					else {
						value = JptUiMappingsMessages.DefaultWithoutValue;
					}
				}

				return value;
			}
		};
	}

	private WritablePropertyValueModel<String> buildNameHolder() {
		return new PropertyAspectAdapter<BaseJoinColumnStateObject, String>(getSubjectHolder(), BaseJoinColumnStateObject.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getName();
			}

			@Override
			protected void setValue_(String value) {

				// Convert the default value or an empty string to null
				if ((value != null) &&
				   ((value.length() == 0) || value.startsWith(DEFAULT_KEY))) {

					value = null;
				}

				subject.setName(value);
			}
		};
	}

	private ListValueModel<String> buildNameListHolder() {
		return new ListAspectAdapter<BaseJoinColumnStateObject, String>(getSubjectHolder(), BaseJoinColumnStateObject.NAMES_LIST) {
			@Override
			protected ListIterator<String> listIterator_() {
				return subject.names();
			}
			@Override
			protected int size_() {
				return subject.columnsSize();
			}
		};
	}

	private ListValueModel<String> buildNamesListHolder() {
		ArrayList<ListValueModel<String>> holders = new ArrayList<ListValueModel<String>>(2);
		holders.add(buildDefaultNameListHolder());
		holders.add(buildNameListHolder());
		return new CompositeListValueModel<ListValueModel<String>, String>(holders);
	}

	private StringConverter<String> buildNameStringConverter() {
		return buildDisplayableStringConverter(new DefaultValueHandler() {
			public String getDefaultValue() {
				return getSubject().getDefaultName();
			}
		});
	}

	private WritablePropertyValueModel<String> buildReferencedColumnNameHolder() {
		return new PropertyAspectAdapter<BaseJoinColumnStateObject, String>(getSubjectHolder(), BaseJoinColumnStateObject.REFERENCED_COLUMN_NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getReferencedColumnName();
			}

			@Override
			protected void setValue_(String value) {

				// Convert the default value or an empty string to null
				if ((value != null) &&
				   ((value.length() == 0) || value.startsWith(DEFAULT_KEY))) {

					value = null;
				}

				subject.setReferencedColumnName(value);
			}
		};
	}

	private ListValueModel<String> buildReferencedColumnNameListHolder() {
		return new ListAspectAdapter<BaseJoinColumnStateObject, String>(getSubjectHolder(), BaseJoinColumnStateObject.REFERENCE_COLUMN_NAMES_LIST) {
			@Override
			protected ListIterator<String> listIterator_() {
				return subject.referenceColumnNames();
			}
			@Override
			protected int size_() {
				return subject.referenceColumnsSize();
			}
		};
	}

	private ListValueModel<String> buildReferencedColumnNamesListHolder() {
		ArrayList<ListValueModel<String>> holders = new ArrayList<ListValueModel<String>>(2);
		holders.add(buildDefaultReferencedColumnNameListHolder());
		holders.add(buildReferencedColumnNameListHolder());
		return new CompositeListValueModel<ListValueModel<String>, String>(holders);
	}

	private StringConverter<String> buildReferencedColumnNameStringConverter() {
		return buildDisplayableStringConverter(new DefaultValueHandler() {
			public String getDefaultValue() {
				return getSubject().getDefaultReferencedColumnName();
			}
		});
	}

	private WritablePropertyValueModel<String> buildTableHolder() {
		return new PropertyAspectAdapter<BaseJoinColumnStateObject, String>(getSubjectHolder(), BaseJoinColumnStateObject.TABLE_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getTable();
			}

			@Override
			protected void setValue_(String value) {

				// Convert the default value or an empty string to null
				if ((value != null) &&
				   ((value.length() == 0) || value.startsWith(DEFAULT_KEY))) {

					value = null;
				}

				subject.setTable(value);
			}
		};
	}

	private ListValueModel<String> buildTableListHolder() {
		return new ListAspectAdapter<BaseJoinColumnStateObject, String>(getSubjectHolder(), "") {
			@Override
			protected ListIterator<String> listIterator_() {
				return subject.tables();
			}
		};
	}

	private ListValueModel<String> buildTablesListHolder() {
		ArrayList<ListValueModel<String>> holders = new ArrayList<ListValueModel<String>>(2);
		holders.add(buildDefaultTableListHolder());
		holders.add(buildTableListHolder());
		return new CompositeListValueModel<ListValueModel<String>, String>(holders);
	}

	private StringConverter<String> buildTableStringConverter() {
		return buildDisplayableStringConverter(new DefaultValueHandler() {
			public String getDefaultValue() {
				return getSubject().getDefaultTable();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Name widgets
		Combo nameCombo = addLabeledEditableCombo(
			container,
			JptUiMappingsMessages.JoinColumnDialog_name,
			buildNamesListHolder(),
			buildNameHolder(),
			buildNameStringConverter(),
			JpaHelpContextIds.MAPPING_JOIN_COLUMN_NAME
		);

		SWTUtil.attachDefaultValueHandler(nameCombo);

		// Referenced Column Name widgets
		Combo referencedColumnNameCombo = addLabeledEditableCombo(
			container,
			JptUiMappingsMessages.JoinColumnDialog_referencedColumnName,
			buildReferencedColumnNamesListHolder(),
			buildReferencedColumnNameHolder(),
			buildReferencedColumnNameStringConverter(),
			JpaHelpContextIds.MAPPING_JOIN_REFERENCED_COLUMN
		);

		SWTUtil.attachDefaultValueHandler(referencedColumnNameCombo);

		// Table widgets
		if (isTableEditable()) {

			Combo tableCombo = addLabeledEditableCombo(
				container,
				JptUiMappingsMessages.JoinColumnDialogPane_table,
				buildTablesListHolder(),
				buildTableHolder(),
				buildTableStringConverter(),
				JpaHelpContextIds.MAPPING_JOIN_REFERENCED_COLUMN
			);

			SWTUtil.attachDefaultValueHandler(tableCombo);
		}
		else {
			Combo tableCombo = addLabeledCombo(
				container,
				JptUiMappingsMessages.JoinColumnDialogPane_table,
				buildTablesListHolder(),
				buildTableHolder(),
				buildTableStringConverter(),
				JpaHelpContextIds.MAPPING_JOIN_REFERENCED_COLUMN
			);

			tableCombo.setEnabled(false);
		}

		// Column Definition widgets
		addLabeledText(
			container,
			JptUiMappingsMessages.JoinColumnDialogPane_columnDefinition,
			buildColumnDefinitionHolder()
		);
	}

	/**
	 * Determines whether the table combo should be editable or not. The default
	 * is to keep the combo read-only.
	 *
	 * @return <code>true</code> to support the editing of the selected table;
	 * <code>false</code> otherwise
	 */
	protected boolean isTableEditable() {
		return false;
	}

	protected static interface DefaultValueHandler {
		String getDefaultValue();
	}
}
/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.ArrayList;
import java.util.ListIterator;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.swt.widgets.ComboTools;
import org.eclipse.jpt.common.ui.internal.widgets.DialogPane;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class BaseJoinColumnDialogPane<T extends BaseJoinColumnStateObject>
	extends DialogPane<T>
{
	/**
	 * A key used to represent the default value, this is required to convert
	 * the selected item from a combo to <code>null</code>. This key is most
	 * likely never typed the user and it will help to convert the value to
	 * <code>null</code> when it's time to set the new selected value into the
	 * model.
	 */
	protected static String DEFAULT_KEY = "?!#!?#?#?default?#?!#?!#?"; //$NON-NLS-1$

	public BaseJoinColumnDialogPane(
			PropertyValueModel<? extends T> subjectModel,
			Composite parentComposite,
			ResourceManager resourceManager) {
		super(subjectModel, parentComposite, resourceManager);
	}

	private ModifiablePropertyValueModel<String> buildColumnDefinitionModel() {
		return new PropertyAspectAdapterXXXX<BaseJoinColumnStateObject, String>(getSubjectHolder(), BaseJoinColumnStateObject.COLUMN_DEFINITION_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getColumnDefinition();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setColumnDefinition(value);
			}
		};
	}

	private PropertyValueModel<String> buildDefaultNameModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), DEFAULT_NAME_TRANSFORMER);
	}

	private static final Transformer<BaseJoinColumnStateObject, String> DEFAULT_NAME_TRANSFORMER = new DefaultNameTransformer();
	static class DefaultNameTransformer
		extends TransformerAdapter<BaseJoinColumnStateObject, String>
	{
		@Override
		public String transform(BaseJoinColumnStateObject column) {
			String name = column.getDefaultName();
			return (name == null) ? DEFAULT_KEY : DEFAULT_KEY + name;
		}
	}

	private ListValueModel<String> buildDefaultNameListModel() {
		return new PropertyListValueModelAdapter<>(
			buildDefaultNameModel()
		);
	}

	private PropertyValueModel<String> buildDefaultReferencedColumnNameModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), DEFAULT_REFERENCED_COLUMN_NAME_TRANSFORMER);
	}

	private static final Transformer<BaseJoinColumnStateObject, String> DEFAULT_REFERENCED_COLUMN_NAME_TRANSFORMER = new DefaultReferencedColumnNameTransformer();
	static class DefaultReferencedColumnNameTransformer
		extends TransformerAdapter<BaseJoinColumnStateObject, String>
	{
		@Override
		public String transform(BaseJoinColumnStateObject column) {
			String name = column.getDefaultReferencedColumnName();
			return (name == null) ? DEFAULT_KEY : DEFAULT_KEY + name;
		}
	}

	private ListValueModel<String> buildDefaultReferencedColumnNameListModel() {
		return new PropertyListValueModelAdapter<>(
			buildDefaultReferencedColumnNameModel()
		);
	}

	private PropertyValueModel<String> buildDefaultTableModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), DEFAULT_TABLE_TRANSFORMER);
	}

	private static final Transformer<BaseJoinColumnStateObject, String> DEFAULT_TABLE_TRANSFORMER = new DefaultTableTransformer();
	static class DefaultTableTransformer
		extends TransformerAdapter<BaseJoinColumnStateObject, String>
	{
		@Override
		public String transform(BaseJoinColumnStateObject column) {
			String name = column.getDefaultTable();
			return (name == null) ? DEFAULT_KEY : DEFAULT_KEY + name;
		}
	}

	private ListValueModel<String> buildDefaultTableListModel() {
		return new PropertyListValueModelAdapter<>(
			buildDefaultTableModel()
		);
	}

	private Transformer<String, String> buildDisplayableStringConverter(DefaultValueHandler handler) {
		return new DisplayableStringTransformer(handler);
	}

	class DisplayableStringTransformer
		extends TransformerAdapter<String, String>
	{
		private final DefaultValueHandler handler;
		DisplayableStringTransformer(DefaultValueHandler handler) {
			super();
			this.handler = handler;
		}
		@Override
		public String transform(String value) {

			if (getSubject() == null) {
				return null;
			}

			if (value == null) {
				value = this.handler.getDefaultValue();

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
						JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM,
						defaultName
					);
				}
				else {
					value = JptCommonUiMessages.DEFAULT_EMPTY;
				}
			}

			return value;
		}
	}

	private ModifiablePropertyValueModel<String> buildNameModel() {
		return new PropertyAspectAdapterXXXX<BaseJoinColumnStateObject, String>(getSubjectHolder(), BaseJoinColumnStateObject.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getName();
			}

			@Override
			protected void setValue_(String value) {

				// Convert the default value or an empty string to null
				if ((value != null) &&
				   ((value.length() == 0) || value.startsWith(DEFAULT_KEY))) {

					value = null;
				}

				this.subject.setName(value);
			}
		};
	}

	private ListValueModel<String> buildNameListModel() {
		return new ListAspectAdapter<BaseJoinColumnStateObject, String>(getSubjectHolder(), BaseJoinColumnStateObject.NAMES_LIST) {
			@Override
			protected ListIterator<String> listIterator_() {
				return this.subject.names();
			}
			@Override
			protected int size_() {
				return this.subject.columnsSize();
			}
		};
	}

	private ListValueModel<String> buildNamesListModel() {
		ArrayList<ListValueModel<String>> models = new ArrayList<>(2);
		models.add(buildDefaultNameListModel());
		models.add(buildNameListModel());
		return CompositeListValueModel.forModels(models);
	}

	private Transformer<String, String> buildNameStringConverter() {
		return buildDisplayableStringConverter(new DefaultValueHandler() {
			public String getDefaultValue() {
				return getSubject().getDefaultName();
			}
		});
	}

	private ModifiablePropertyValueModel<String> buildReferencedColumnNameModel() {
		return new PropertyAspectAdapterXXXX<BaseJoinColumnStateObject, String>(getSubjectHolder(), BaseJoinColumnStateObject.REFERENCED_COLUMN_NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getReferencedColumnName();
			}

			@Override
			protected void setValue_(String value) {

				// Convert the default value or an empty string to null
				if ((value != null) &&
				   ((value.length() == 0) || value.startsWith(DEFAULT_KEY))) {

					value = null;
				}

				this.subject.setReferencedColumnName(value);
			}
		};
	}

	private ListValueModel<String> buildReferencedColumnNameListModel() {
		return new ListAspectAdapter<BaseJoinColumnStateObject, String>(getSubjectHolder(), BaseJoinColumnStateObject.REFERENCE_COLUMN_NAMES_LIST) {
			@Override
			protected ListIterator<String> listIterator_() {
				return this.subject.referenceColumnNames();
			}
			@Override
			protected int size_() {
				return this.subject.referenceColumnsSize();
			}
		};
	}

	private ListValueModel<String> buildReferencedColumnNamesListModel() {
		ArrayList<ListValueModel<String>> models = new ArrayList<>(2);
		models.add(buildDefaultReferencedColumnNameListModel());
		models.add(buildReferencedColumnNameListModel());
		return CompositeListValueModel.forModels(models);
	}

	private Transformer<String, String> buildReferencedColumnNameStringConverter() {
		return buildDisplayableStringConverter(new DefaultValueHandler() {
			public String getDefaultValue() {
				return getSubject().getDefaultReferencedColumnName();
			}
		});
	}

	private ModifiablePropertyValueModel<String> buildTableModel() {
		return new PropertyAspectAdapterXXXX<BaseJoinColumnStateObject, String>(getSubjectHolder(), BaseJoinColumnStateObject.TABLE_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getTable();
			}

			@Override
			protected void setValue_(String value) {

				// Convert the default value or an empty string to null
				if ((value != null) &&
				   ((value.length() == 0) || value.startsWith(DEFAULT_KEY))) {

					value = null;
				}

				this.subject.setTable(value);
			}
		};
	}

	private ListValueModel<String> buildTableListModel() {
		return new ListAspectAdapter<BaseJoinColumnStateObject, String>(getSubjectHolder(), StringTools.EMPTY_STRING) {
			@Override
			protected ListIterator<String> listIterator_() {
				return this.subject.tables();
			}
		};
	}

	private ListValueModel<String> buildTablesListModel() {
		ArrayList<ListValueModel<String>> models = new ArrayList<>(2);
		models.add(buildDefaultTableListModel());
		models.add(buildTableListModel());
		return CompositeListValueModel.forModels(models);
	}

	private Transformer<String, String> buildTableStringConverter() {
		return buildDisplayableStringConverter(new DefaultValueHandler() {
			public String getDefaultValue() {
				return getSubject().getDefaultTable();
			}
		});
	}


	@Override
	protected Composite addComposite(Composite container) {
		return this.addSubPane(container, 2, 0, 7, 0, 5);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Name widgets
		this.addLabel(container, JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_NAME);
		Combo nameCombo = this.addEditableCombo(
			container,
			buildNamesListModel(),
			buildNameModel(),
			buildNameStringConverter(),
			JpaHelpContextIds.MAPPING_JOIN_COLUMN_NAME
		);

		ComboTools.handleDefaultValue(nameCombo);

		// Referenced Column Name widgets
		this.addLabel(container, JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_REFERENCED_COLUMN_NAME);
		Combo referencedColumnNameCombo = this.addEditableCombo(
			container,
			buildReferencedColumnNamesListModel(),
			buildReferencedColumnNameModel(),
			buildReferencedColumnNameStringConverter(),
			JpaHelpContextIds.MAPPING_JOIN_REFERENCED_COLUMN
		);

		ComboTools.handleDefaultValue(referencedColumnNameCombo);

		// Table widgets
		if (isTableEditable()) {
			this.addLabel(container, JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_TABLE);

			Combo tableCombo = this.addEditableCombo(
				container,
				buildTablesListModel(),
				buildTableModel(),
				buildTableStringConverter(),
				JpaHelpContextIds.MAPPING_JOIN_REFERENCED_COLUMN
			);

			ComboTools.handleDefaultValue(tableCombo);
		}
		else {
			this.addLabel(container, JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_TABLE);
			Combo tableCombo = addCombo(
				container,
				buildTablesListModel(),
				buildTableModel(),
				buildTableStringConverter(),
				JpaHelpContextIds.MAPPING_JOIN_REFERENCED_COLUMN
			);

			tableCombo.setEnabled(false);
		}

		// Column Definition widgets
		this.addLabel(container, JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_COLUMN_DEFINITION);
		this.addText(
			container,
			buildColumnDefinitionModel()
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
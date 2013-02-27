/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedBaseColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedNamedColumn;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedOrderColumn2_0;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.db.ColumnCombo;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | ColumnCombo                                                           | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * | > Details                                                                 |
 * |                                                                           |
 * |   x Insertable                                                            |
 * |                                                                           |
 * |   x Updatable                                                             |
 * |                                                                           |
 * |   x Nullable                                                              |
 * |                                                                           |
 * |                      ---------------------------------------------------- |
 * |   Column Definition: | I                                                | |
 * |                      ---------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 * *
 * @version 3.0
 * @since 3.0
 */
public class OrderColumnComposite extends Pane<SpecifiedOrderColumn2_0> {

	/**
	 * Creates a new <code>ColumnComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject <code>IColumn</code>
	 * @param parent The parent container
	 */
	public OrderColumnComposite(Pane<?> parentPane,
	                       PropertyValueModel<? extends SpecifiedOrderColumn2_0> subjectHolder,
		                   PropertyValueModel<Boolean> enabledModel,
	                       Composite parent) {

		super(parentPane, subjectHolder, enabledModel, parent);
	}


	private ColumnCombo<SpecifiedOrderColumn2_0> addColumnCombo(Composite container) {

		return new ColumnCombo<SpecifiedOrderColumn2_0>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SpecifiedNamedColumn.DEFAULT_NAME_PROPERTY);
				propertyNames.add(SpecifiedNamedColumn.SPECIFIED_NAME_PROPERTY);
				propertyNames.add(SpecifiedNamedColumn.DB_TABLE_PROPERTY);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				if (propertyName.equals(SpecifiedNamedColumn.DB_TABLE_PROPERTY)) {
					this.doPopulate();
				} else {
					super.propertyChanged(propertyName);
				}
			}

			@Override
			protected String getDefaultValue() {
				return getSubject().getDefaultName();
			}

			@Override
			protected void setValue(String value) {
				getSubject().setSpecifiedName(value);
			}

			@Override
			protected Table getDbTable_() {
				return getSubject().getDbTable();
			}

			@Override
			protected String getValue() {
				return getSubject().getSpecifiedName();
			}

			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.MAPPING_ORDER_COLUMN_ORDERING_COLUMN;
			}

			@Override
			public String toString() {
				return "OrderColumnComposite.columnCombo"; //$NON-NLS-1$
			}
		};
	}

	private ModifiablePropertyValueModel<String> buildColumnDefinitionHolder() {
		return new PropertyAspectAdapter<SpecifiedOrderColumn2_0, String>(getSubjectHolder(), SpecifiedNamedColumn.COLUMN_DEFINITION_PROPERTY) {
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
	
	private ModifiablePropertyValueModel<Boolean> buildInsertableHolder() {
		return new PropertyAspectAdapter<SpecifiedOrderColumn2_0, Boolean>(getSubjectHolder(), SpecifiedBaseColumn.SPECIFIED_INSERTABLE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedInsertable();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedInsertable(value);
			}
		};
	}

	private PropertyValueModel<String> buildInsertableStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultInsertableHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaUiDetailsMessages.ColumnComposite_insertableWithDefault, defaultStringValue);
				}
				return JptJpaUiDetailsMessages.ColumnComposite_insertable;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultInsertableHolder() {
		return new PropertyAspectAdapter<SpecifiedOrderColumn2_0, Boolean>(
			getSubjectHolder(),
			SpecifiedBaseColumn.SPECIFIED_INSERTABLE_PROPERTY,
			SpecifiedBaseColumn.DEFAULT_INSERTABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedInsertable() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultInsertable());
			}
		};
	}

	private ModifiablePropertyValueModel<Boolean> buildNullableHolder() {
		return new PropertyAspectAdapter<SpecifiedOrderColumn2_0, Boolean>(
			getSubjectHolder(),
			SpecifiedBaseColumn.SPECIFIED_NULLABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedNullable();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedNullable(value);
			}
		};
	}

	private PropertyValueModel<String> buildNullableStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultNullableHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaUiDetailsMessages.ColumnComposite_nullableWithDefault, defaultStringValue);
				}
				return JptJpaUiDetailsMessages.ColumnComposite_nullable;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultNullableHolder() {
		return new PropertyAspectAdapter<SpecifiedOrderColumn2_0, Boolean>(
			getSubjectHolder(),
			SpecifiedBaseColumn.SPECIFIED_NULLABLE_PROPERTY,
			SpecifiedBaseColumn.DEFAULT_NULLABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedNullable() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultNullable());
			}
		};
	}


	private ModifiablePropertyValueModel<Boolean> buildUpdatableHolder() {
		return new PropertyAspectAdapter<SpecifiedOrderColumn2_0, Boolean>(
			getSubjectHolder(),
			SpecifiedBaseColumn.DEFAULT_UPDATABLE_PROPERTY,
			SpecifiedBaseColumn.SPECIFIED_UPDATABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedUpdatable();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedUpdatable(value);
			}
		};
	}

	private PropertyValueModel<String> buildUpdatableStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultUpdatableHolder()) {

			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaUiDetailsMessages.ColumnComposite_updatableWithDefault, defaultStringValue);
				}
				return JptJpaUiDetailsMessages.ColumnComposite_updatable;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultUpdatableHolder() {
		return new PropertyAspectAdapter<SpecifiedOrderColumn2_0, Boolean>(
			getSubjectHolder(),
			SpecifiedBaseColumn.SPECIFIED_UPDATABLE_PROPERTY,
			SpecifiedBaseColumn.DEFAULT_UPDATABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedUpdatable() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultUpdatable());
			}
		};
	}

	@Override
	protected Composite addComposite(Composite container) {
		return this.addSubPane(container, 2, 0, 0, 0, 0); //2 columns
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Column widgets
		this.addLabel(container, JptJpaUiDetailsMessages.ColumnComposite_name);
		this.addColumnCombo(container);

		// Details sub-pane
		Section section = this.getWidgetFactory().createSection(container, 
				ExpandableComposite.TWISTIE | 
				ExpandableComposite.CLIENT_INDENT);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		section.setLayoutData(gridData);
		section.setText(JptJpaUiDetailsMessages.ColumnComposite_details);

		Composite detailsClient = this.addSubPane(section, 2, 0, 0, 0, 0);
		section.setClient(detailsClient);
		
		// Insertable tri-state check box
		TriStateCheckBox insertableCheckBox = addTriStateCheckBoxWithDefault(
			detailsClient,
			JptJpaUiDetailsMessages.ColumnComposite_insertable,
			buildInsertableHolder(),
			buildInsertableStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_INSERTABLE
		);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		insertableCheckBox.getCheckBox().setLayoutData(gridData);

		// Updatable tri-state check box
		TriStateCheckBox updatableCheckBox = addTriStateCheckBoxWithDefault(
			detailsClient,
			JptJpaUiDetailsMessages.ColumnComposite_updatable,
			buildUpdatableHolder(),
			buildUpdatableStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_UPDATABLE
		);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		updatableCheckBox.getCheckBox().setLayoutData(gridData);

		// Nullable tri-state check box
		TriStateCheckBox nullableCheckBox = addTriStateCheckBoxWithDefault(
			detailsClient,
			JptJpaUiDetailsMessages.ColumnComposite_nullable,
			buildNullableHolder(),
			buildNullableStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_NULLABLE
		);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		nullableCheckBox.getCheckBox().setLayoutData(gridData);

		// Column Definition widgets
		this.addLabel(detailsClient, JptJpaUiDetailsMessages.ColumnComposite_columnDefinition);
		this.addText(detailsClient, buildColumnDefinitionHolder());
	}

}
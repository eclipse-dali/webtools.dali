/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedOrderColumn2_0;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.BooleanStringTransformer;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.db.ColumnCombo;
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
public class OrderColumnComposite2_0 extends Pane<SpecifiedOrderColumn2_0> {

	/**
	 * Creates a new <code>ColumnComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject <code>IColumn</code>
	 * @param parent The parent container
	 */
	public OrderColumnComposite2_0(Pane<?> parentPane,
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
				propertyNames.add(NamedColumn.DEFAULT_NAME_PROPERTY);
				propertyNames.add(NamedColumn.SPECIFIED_NAME_PROPERTY);
				propertyNames.add(NamedColumn.DB_TABLE_PROPERTY);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				if (propertyName.equals(NamedColumn.DB_TABLE_PROPERTY)) {
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

	private ModifiablePropertyValueModel<String> buildColumnDefinitionModel() {
		return new PropertyAspectAdapter<SpecifiedOrderColumn2_0, String>(getSubjectHolder(), NamedColumn.COLUMN_DEFINITION_PROPERTY) {
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
	
	private ModifiablePropertyValueModel<Boolean> buildInsertableModel() {
		return new PropertyAspectAdapter<SpecifiedOrderColumn2_0, Boolean>(getSubjectHolder(), BaseColumn.SPECIFIED_INSERTABLE_PROPERTY) {
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

	private PropertyValueModel<String> buildInsertableStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultInsertableModel(), INSERTABLE_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> INSERTABLE_TRANSFORMER = new BooleanStringTransformer(
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_INSERTABLE_WITH_DEFAULT,
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_INSERTABLE
		);

	private PropertyValueModel<Boolean> buildDefaultInsertableModel() {
		return new PropertyAspectAdapter<SpecifiedOrderColumn2_0, Boolean>(
			getSubjectHolder(),
			BaseColumn.SPECIFIED_INSERTABLE_PROPERTY,
			BaseColumn.DEFAULT_INSERTABLE_PROPERTY)
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

	private ModifiablePropertyValueModel<Boolean> buildNullableModel() {
		return new PropertyAspectAdapter<SpecifiedOrderColumn2_0, Boolean>(
			getSubjectHolder(),
			BaseColumn.SPECIFIED_NULLABLE_PROPERTY)
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

	private PropertyValueModel<String> buildNullableStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultNullableModel(), NULLABLE_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> NULLABLE_TRANSFORMER = new BooleanStringTransformer(
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_NULLABLE_WITH_DEFAULT,
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_NULLABLE
		);

	private PropertyValueModel<Boolean> buildDefaultNullableModel() {
		return new PropertyAspectAdapter<SpecifiedOrderColumn2_0, Boolean>(
			getSubjectHolder(),
			BaseColumn.SPECIFIED_NULLABLE_PROPERTY,
			BaseColumn.DEFAULT_NULLABLE_PROPERTY)
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


	private ModifiablePropertyValueModel<Boolean> buildUpdatableModel() {
		return new PropertyAspectAdapter<SpecifiedOrderColumn2_0, Boolean>(
			getSubjectHolder(),
			BaseColumn.DEFAULT_UPDATABLE_PROPERTY,
			BaseColumn.SPECIFIED_UPDATABLE_PROPERTY)
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

	private PropertyValueModel<String> buildUpdatableStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultUpdatableModel(), UPDATABLE_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> UPDATABLE_TRANSFORMER = new BooleanStringTransformer(
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_UPDATABLE_WITH_DEFAULT,
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_UPDATABLE
		);

	private PropertyValueModel<Boolean> buildDefaultUpdatableModel() {
		return new PropertyAspectAdapter<SpecifiedOrderColumn2_0, Boolean>(
			getSubjectHolder(),
			BaseColumn.SPECIFIED_UPDATABLE_PROPERTY,
			BaseColumn.DEFAULT_UPDATABLE_PROPERTY)
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
		this.addLabel(container, JptJpaUiDetailsMessages.COLUMN_COMPOSITE_NAME);
		this.addColumnCombo(container);

		// Details sub-pane
		Section section = this.getWidgetFactory().createSection(container, 
				ExpandableComposite.TWISTIE | 
				ExpandableComposite.CLIENT_INDENT);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		section.setLayoutData(gridData);
		section.setText(JptJpaUiDetailsMessages.COLUMN_COMPOSITE_DETAILS);

		Composite detailsClient = this.addSubPane(section, 2, 0, 0, 0, 0);
		section.setClient(detailsClient);
		
		// Insertable tri-state check box
		TriStateCheckBox insertableCheckBox = addTriStateCheckBoxWithDefault(
			detailsClient,
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_INSERTABLE,
			buildInsertableModel(),
			buildInsertableStringModel(),
			JpaHelpContextIds.MAPPING_COLUMN_INSERTABLE
		);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		insertableCheckBox.getCheckBox().setLayoutData(gridData);

		// Updatable tri-state check box
		TriStateCheckBox updatableCheckBox = addTriStateCheckBoxWithDefault(
			detailsClient,
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_UPDATABLE,
			buildUpdatableModel(),
			buildUpdatableStringModel(),
			JpaHelpContextIds.MAPPING_COLUMN_UPDATABLE
		);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		updatableCheckBox.getCheckBox().setLayoutData(gridData);

		// Nullable tri-state check box
		TriStateCheckBox nullableCheckBox = addTriStateCheckBoxWithDefault(
			detailsClient,
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_NULLABLE,
			buildNullableModel(),
			buildNullableStringModel(),
			JpaHelpContextIds.MAPPING_COLUMN_NULLABLE
		);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		nullableCheckBox.getCheckBox().setLayoutData(gridData);

		// Column Definition widgets
		this.addLabel(detailsClient, JptJpaUiDetailsMessages.COLUMN_COMPOSITE_COLUMN_DEFINITION);
		this.addText(detailsClient, buildColumnDefinitionModel());
	}

}

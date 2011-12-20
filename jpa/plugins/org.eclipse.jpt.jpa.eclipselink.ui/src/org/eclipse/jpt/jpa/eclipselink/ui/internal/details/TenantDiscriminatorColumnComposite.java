/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.common.ui.internal.widgets.ComboPane;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTableColumn;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.eclipselink.core.context.ReadOnlyTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.TenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.DiscriminatorColumnComposite;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.db.ColumnCombo;
import org.eclipse.jpt.jpa.ui.internal.details.db.DatabaseObjectCombo;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

public class TenantDiscriminatorColumnComposite extends Pane<ReadOnlyTenantDiscriminatorColumn2_3> {

	public TenantDiscriminatorColumnComposite(Pane<?> parentPane,
	                                   PropertyValueModel<ReadOnlyTenantDiscriminatorColumn2_3> subjectHolder,
	                                   Composite parent) {

		super(parentPane, subjectHolder, parent);
	}


	@Override
	protected void initializeLayout(Composite container) {
		
		// Name widgets
		addLabeledComposite(
				container,
				EclipseLinkUiDetailsMessages.TenantDiscriminatorColumnComposite_nameLabel,
				addNameCombo(container),
				EclipseLinkHelpContextIds.TENANT_DISCRIMINATOR_COLUMN_NAME);

		// Table widgets
		addLabeledComposite(
				container,
				EclipseLinkUiDetailsMessages.TenantDiscriminatorColumnComposite_tableLabel,
				addTableCombo(container),
				EclipseLinkHelpContextIds.TENANT_DISCRIMINATOR_COLUMN_TABLE);

		// Context property widgets
		addLabeledComposite(
				container,
				EclipseLinkUiDetailsMessages.TenantDiscriminatorColumnComposite_contextPropertyLabel,
				addContextPropertyCombo(container),
				EclipseLinkHelpContextIds.TENANT_DISCRIMINATOR_COLUMN_CONTEXT_PROPERTY);

		// Discriminator Type widgets
		addLabeledComposite(
				container,
				EclipseLinkUiDetailsMessages.TenantDiscriminatorColumnComposite_discriminatorTypeLabel,
				addDiscriminatorTypeCombo(container),
				EclipseLinkHelpContextIds.TENANT_DISCRIMINATOR_COLUMN_DISCRIMINATOR_TYPE
		);
		// Length widgets
		addLengthCombo(container);

		// Column Definition widgets
		addLabeledText(
			container,
			EclipseLinkUiDetailsMessages.TenantDiscriminatorColumnComposite_columnDefinitionLabel,
			buildColumnDefinitionHolder(getSubjectHolder())
		);

		// Primary key tri-state check box
		TriStateCheckBox pkCheckBox = addTriStateCheckBoxWithDefault(
			addSubPane(container, 4),
			EclipseLinkUiDetailsMessages.TenantDiscriminatorColumnComposite_primaryKey,
			buildPrimaryKeyHolder(),
			buildPrimaryKeyStringHolder(),
			EclipseLinkHelpContextIds.TENANT_DISCRIMINATOR_COLUMN_PRIMARY_KEY);

		SWTTools.controlVisibleState(new EclipseLink2_4ProjectFlagModel<ReadOnlyTenantDiscriminatorColumn2_3>(this.getSubjectHolder()), pkCheckBox.getCheckBox());
	}

	private ColumnCombo<ReadOnlyTenantDiscriminatorColumn2_3> addNameCombo(Composite container) {

		return new ColumnCombo<ReadOnlyTenantDiscriminatorColumn2_3>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(ReadOnlyNamedColumn.DEFAULT_NAME_PROPERTY);
				propertyNames.add(ReadOnlyNamedColumn.SPECIFIED_NAME_PROPERTY);
				propertyNames.add(ReadOnlyTableColumn.DEFAULT_TABLE_PROPERTY);
				propertyNames.add(ReadOnlyTableColumn.SPECIFIED_TABLE_PROPERTY);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				if (propertyName == ReadOnlyTableColumn.DEFAULT_TABLE_PROPERTY ||
				    propertyName == ReadOnlyTableColumn.SPECIFIED_TABLE_PROPERTY) {
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
				((TenantDiscriminatorColumn2_3) this.getSubject()).setSpecifiedName(value);
			}

			@Override
			protected Table getDbTable_() {
				ReadOnlyTenantDiscriminatorColumn2_3 column = this.getSubject();
				return (column == null) ? null : column.getDbTable();
			}

			@Override
			protected String getValue() {
				return getSubject().getSpecifiedName();
			}

			@Override
			protected String buildNullDefaultValueEntry() {
				return NLS.bind(
						JptCommonUiMessages.DefaultWithOneParam,
						JptCommonUiMessages.NoneSelected);
			}

			@Override
			public String toString() {
				return "TenantDiscriminatorColumnComposite.nameCombo"; //$NON-NLS-1$
			}
		};
	}

	private Pane<ReadOnlyTenantDiscriminatorColumn2_3> addTableCombo(Composite container) {

		return new DatabaseObjectCombo<ReadOnlyTenantDiscriminatorColumn2_3>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(ReadOnlyTableColumn.DEFAULT_TABLE_PROPERTY);
				propertyNames.add(ReadOnlyTableColumn.SPECIFIED_TABLE_PROPERTY);
			}

			@Override
			protected String getDefaultValue() {
				return this.getSubject().getDefaultTable();
			}

			@Override
			protected void setValue(String value) {
				((TenantDiscriminatorColumn2_3) this.getSubject()).setSpecifiedTable(value);
			}

			@Override
			protected String getValue() {
				return this.getSubject().getSpecifiedTable();
			}

			// TODO we need to listen for this list to change...
			@Override
			protected Iterable<String> getValues_() {
				ReadOnlyTenantDiscriminatorColumn2_3 column = this.getSubject();
				return (column != null) ? column.getCandidateTableNames() : EmptyIterable.<String> instance();
			}

			@Override
			protected String buildNullDefaultValueEntry() {
				return NLS.bind(
						JptCommonUiMessages.DefaultWithOneParam,
						JptCommonUiMessages.NoneSelected);
			}

			@Override
			public String toString() {
				return "TenantDiscriminatorColumnComposite.tableCombo"; //$NON-NLS-1$
			}
		};
	}

	private Pane<ReadOnlyTenantDiscriminatorColumn2_3> addContextPropertyCombo(Composite container) {

		return new ComboPane<ReadOnlyTenantDiscriminatorColumn2_3>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(ReadOnlyTenantDiscriminatorColumn2_3.DEFAULT_CONTEXT_PROPERTY);
				propertyNames.add(ReadOnlyTenantDiscriminatorColumn2_3.SPECIFIED_CONTEXT_PROPERTY_PROPERTY);
			}

			@Override
			protected String getDefaultValue() {
				return this.getSubject().getDefaultContextProperty();
			}

			@Override
			protected void setValue(String value) {
				((TenantDiscriminatorColumn2_3) this.getSubject()).setSpecifiedContextProperty(value);
			}

			@Override
			protected String getValue() {
				return this.getSubject().getSpecifiedContextProperty();
			}

			@Override
			protected Iterable<String> getValues() {
				return EmptyIterable.<String> instance();
			}

			@Override
			protected String buildNullDefaultValueEntry() {
				return NLS.bind(
						JptCommonUiMessages.DefaultWithOneParam,
						JptCommonUiMessages.NoneSelected);
			}

			@Override
			public String toString() {
				return "TenantDiscriminatorColumnComposite.contextPropertyCombo"; //$NON-NLS-1$
			}
		};
	}

	private EnumFormComboViewer<ReadOnlyTenantDiscriminatorColumn2_3, DiscriminatorType> addDiscriminatorTypeCombo(Composite container) {

		return new EnumFormComboViewer<ReadOnlyTenantDiscriminatorColumn2_3, DiscriminatorType>(
			this,
			getSubjectHolder(),
			container)
		{
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(ReadOnlyNamedDiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE_PROPERTY);
				propertyNames.add(ReadOnlyNamedDiscriminatorColumn.SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY);
			}

			@Override
			protected DiscriminatorType[] getChoices() {
				return DiscriminatorType.values();
			}

			@Override
			protected DiscriminatorType getDefaultValue() {
				return getSubject().getDefaultDiscriminatorType();
			}

			@Override
			protected String displayString(DiscriminatorType value) {
				return buildDisplayString(
					JptUiDetailsMessages.class,
					DiscriminatorColumnComposite.class,
					value
				);
			}

			@Override
			protected String nullDisplayString() {
				return JptCommonUiMessages.NoneSelected;
			}

			@Override
			protected DiscriminatorType getValue() {
				return getSubject().getSpecifiedDiscriminatorType();
			}

			@Override
			protected void setValue(DiscriminatorType value) {
				((TenantDiscriminatorColumn2_3) this.getSubject()).setSpecifiedDiscriminatorType(value);
			}
		};
	}

	private void addLengthCombo(Composite container) {
		new IntegerCombo<ReadOnlyTenantDiscriminatorColumn2_3>(this, container) {

			@Override
			protected String getLabelText() {
				return JptUiDetailsMessages.ColumnComposite_length;
			}

			@Override
			protected String getHelpId() {
				return EclipseLinkHelpContextIds.TENANT_DISCRIMINATOR_COLUMN_LENGTH;
			}

			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<ReadOnlyTenantDiscriminatorColumn2_3, Integer>(getSubjectHolder(), ReadOnlyNamedDiscriminatorColumn.DEFAULT_LENGTH_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return Integer.valueOf(this.subject.getDefaultLength());
					}
				};
			}

			@Override
			protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<ReadOnlyTenantDiscriminatorColumn2_3, Integer>(getSubjectHolder(), ReadOnlyNamedDiscriminatorColumn.SPECIFIED_LENGTH_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getSpecifiedLength();
					}

					@Override
					protected void setValue_(Integer value) {
						((TenantDiscriminatorColumn2_3) this.subject).setSpecifiedLength(value);
					}
				};
			}
		};
	}

	private WritablePropertyValueModel<String> buildColumnDefinitionHolder(PropertyValueModel<ReadOnlyTenantDiscriminatorColumn2_3> discriminatorColumnHolder) {
		return new PropertyAspectAdapter<ReadOnlyTenantDiscriminatorColumn2_3, String>(discriminatorColumnHolder, ReadOnlyNamedColumn.COLUMN_DEFINITION_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getColumnDefinition();
			}
			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				((TenantDiscriminatorColumn2_3) this.subject).setColumnDefinition(value);
			}
		};
	}

	WritablePropertyValueModel<Boolean> buildPrimaryKeyHolder() {
		return new PropertyAspectAdapter<ReadOnlyTenantDiscriminatorColumn2_3, Boolean>(getSubjectHolder(), ReadOnlyTenantDiscriminatorColumn2_3.SPECIFIED_PRIMARY_KEY_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedPrimaryKey();
			}

			@Override
			protected void setValue_(Boolean value) {
				((TenantDiscriminatorColumn2_3) this.subject).setSpecifiedPrimaryKey(value);
			}
		};
	}

	PropertyValueModel<String> buildPrimaryKeyStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultPrimaryKeyHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiDetailsMessages.TenantDiscriminatorColumnComposite_primaryKeyWithDefault, defaultStringValue);
				}
				return EclipseLinkUiDetailsMessages.TenantDiscriminatorColumnComposite_primaryKey;
			}
		};
	}

	PropertyValueModel<Boolean> buildDefaultPrimaryKeyHolder() {
		return new PropertyAspectAdapter<ReadOnlyTenantDiscriminatorColumn2_3, Boolean>(
				getSubjectHolder(),
				ReadOnlyTenantDiscriminatorColumn2_3.SPECIFIED_PRIMARY_KEY_PROPERTY,
				ReadOnlyTenantDiscriminatorColumn2_3.DEFAULT_PRIMARY_KEY_PROPERTY) {

			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedPrimaryKey() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isPrimaryKey());
			}
		};
	}
}
/*******************************************************************************
 * Copyright (c) 2011, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import java.util.Arrays;
import java.util.Collection;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.swt.bindings.SWTBindingTools;
import org.eclipse.jpt.common.ui.internal.widgets.ComboPane;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JpaModel;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.NamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.TableColumn;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkSpecifiedTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_4;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.TriStateCheckBoxLabelModelAdapter;
import org.eclipse.jpt.jpa.ui.internal.TriStateCheckBoxLabelModelStringTransformer;
import org.eclipse.jpt.jpa.ui.internal.details.db.ColumnCombo;
import org.eclipse.jpt.jpa.ui.internal.details.db.DatabaseObjectCombo;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkTenantDiscriminatorColumnComposite
	extends Pane<EclipseLinkTenantDiscriminatorColumn2_3>
{

	public EclipseLinkTenantDiscriminatorColumnComposite(Pane<?> parentPane,
	                                   PropertyValueModel<EclipseLinkTenantDiscriminatorColumn2_3> subjectHolder,
	                                   Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected Composite addComposite(Composite container) {
		return this.addSubPane(container, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Name widgets
		this.addLabel(container, JptJpaEclipseLinkUiDetailsMessages.TENANT_DISCRIMINATOR_COLUMN_COMPOSITE_NAME_LABEL);
		this.addNameCombo(container);

		// Table widgets
		this.addLabel(container, JptJpaEclipseLinkUiDetailsMessages.TENANT_DISCRIMINATOR_COLUMN_COMPOSITE_TABLE_LABEL);
		this.addTableCombo(container);

		// Context property widgets
		this.addLabel(container, JptJpaEclipseLinkUiDetailsMessages.TENANT_DISCRIMINATOR_COLUMN_COMPOSITE_CONTEXT_PROPERTY_LABEL);
		this.addContextPropertyCombo(container);

		// Discriminator Type widgets
		this.addLabel(container, JptJpaEclipseLinkUiDetailsMessages.TENANT_DISCRIMINATOR_COLUMN_COMPOSITE_DISCRIMINATOR_TYPE_LABEL);
		this.addDiscriminatorTypeCombo(container);

		// Length widgets
		this.addLabel(container, JptJpaUiDetailsMessages.COLUMN_COMPOSITE_LENGTH);
		this.addLengthCombo(container);

		// Column Definition widgets
		this.addLabel(container, JptJpaEclipseLinkUiDetailsMessages.TENANT_DISCRIMINATOR_COLUMN_COMPOSITE_COLUMN_DEFINITION_LABEL);
		this.addText(container, this.buildColumnDefinitionModel(getSubjectHolder()));

		// Primary key tri-state check box
		TriStateCheckBox pkCheckBox = addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiDetailsMessages.TENANT_DISCRIMINATOR_COLUMN_COMPOSITE_PRIMARY_KEY,
			buildSpecifiedPrimaryKeyModel(),
			buildPrimaryKeyStringModel(),
			EclipseLinkHelpContextIds.TENANT_DISCRIMINATOR_COLUMN_PRIMARY_KEY);

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		pkCheckBox.getCheckBox().setLayoutData(gridData);
		SWTBindingTools.bindVisibleState(this.buildPKCheckBoxIsVisibleModel(), pkCheckBox.getCheckBox());
	}

	private PropertyValueModel<Boolean> buildPKCheckBoxIsVisibleModel() {
		return PropertyValueModelTools.valueAffirms(this.getSubjectHolder(), IS_COMPATIBLE_WITH_ECLIPSELINK_2_4, false);
	}

	private static final Predicate<JpaModel> IS_COMPATIBLE_WITH_ECLIPSELINK_2_4 =
			new EclipseLinkVersionIsCompatibleWith<>(EclipseLinkJpaPlatformFactory2_4.VERSION);

	private ColumnCombo<EclipseLinkTenantDiscriminatorColumn2_3> addNameCombo(Composite container) {

		return new ColumnCombo<EclipseLinkTenantDiscriminatorColumn2_3>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(NamedColumn.DEFAULT_NAME_PROPERTY);
				propertyNames.add(NamedColumn.SPECIFIED_NAME_PROPERTY);
				propertyNames.addAll(COLUMN_PICK_LIST_PROPERTIES);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				if (COLUMN_PICK_LIST_PROPERTIES.contains(propertyName)) {
					this.repopulateComboBox();
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
				((EclipseLinkSpecifiedTenantDiscriminatorColumn2_3) this.getSubject()).setSpecifiedName(value);
			}

			@Override
			protected Table getDbTable_() {
				EclipseLinkTenantDiscriminatorColumn2_3 column = this.getSubject();
				return (column == null) ? null : column.getDbTable();
			}

			@Override
			protected String getValue() {
				return getSubject().getSpecifiedName();
			}

			@Override
			protected String buildNullDefaultValueEntry() {
				return NLS.bind(
						JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM,
						JptCommonUiMessages.NONE_SELECTED);
			}

			@Override
			protected String getHelpId() {
				return EclipseLinkHelpContextIds.TENANT_DISCRIMINATOR_COLUMN_NAME;
			}

			@Override
			public String toString() {
				return "TenantDiscriminatorColumnComposite.nameCombo"; //$NON-NLS-1$
			}
		};
	}

	/* CU private */ static final Collection<String> COLUMN_PICK_LIST_PROPERTIES = Arrays.asList(new String[] {
		TableColumn.DEFAULT_TABLE_NAME_PROPERTY,
		TableColumn.SPECIFIED_TABLE_NAME_PROPERTY
	});

	private Pane<EclipseLinkTenantDiscriminatorColumn2_3> addTableCombo(Composite container) {

		return new DatabaseObjectCombo<EclipseLinkTenantDiscriminatorColumn2_3>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(TableColumn.DEFAULT_TABLE_NAME_PROPERTY);
				propertyNames.add(TableColumn.SPECIFIED_TABLE_NAME_PROPERTY);
			}

			@Override
			protected String getDefaultValue() {
				return this.getSubject().getDefaultTableName();
			}

			@Override
			protected void setValue(String value) {
				((EclipseLinkSpecifiedTenantDiscriminatorColumn2_3) this.getSubject()).setSpecifiedTableName(value);
			}

			@Override
			protected String getValue() {
				return this.getSubject().getSpecifiedTableName();
			}

			// TODO we need to listen for this list to change...
			@Override
			protected Iterable<String> getValues_() {
				EclipseLinkTenantDiscriminatorColumn2_3 column = this.getSubject();
				return (column != null) ? column.getCandidateTableNames() : EmptyIterable.<String> instance();
			}

			@Override
			protected String buildNullDefaultValueEntry() {
				return NLS.bind(
						JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM,
						JptCommonUiMessages.NONE_SELECTED);
			}

			@Override
			protected String getHelpId() {
				return EclipseLinkHelpContextIds.TENANT_DISCRIMINATOR_COLUMN_TABLE;
			}

			@Override
			public String toString() {
				return "TenantDiscriminatorColumnComposite.tableCombo"; //$NON-NLS-1$
			}
		};
	}

	private Pane<EclipseLinkTenantDiscriminatorColumn2_3> addContextPropertyCombo(Composite container) {

		return new ComboPane<EclipseLinkTenantDiscriminatorColumn2_3>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkTenantDiscriminatorColumn2_3.DEFAULT_CONTEXT_PROPERTY);
				propertyNames.add(EclipseLinkTenantDiscriminatorColumn2_3.SPECIFIED_CONTEXT_PROPERTY_PROPERTY);
			}

			@Override
			protected String getDefaultValue() {
				return this.getSubject().getDefaultContextProperty();
			}

			@Override
			protected void setValue(String value) {
				((EclipseLinkSpecifiedTenantDiscriminatorColumn2_3) this.getSubject()).setSpecifiedContextProperty(value);
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
						JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM,
						JptCommonUiMessages.NONE_SELECTED);
			}

			@Override
			public String toString() {
				return "TenantDiscriminatorColumnComposite.contextPropertyCombo"; //$NON-NLS-1$
			}

			@Override
			protected String getHelpId() {
				return EclipseLinkHelpContextIds.TENANT_DISCRIMINATOR_COLUMN_CONTEXT_PROPERTY;
			}
		};
	}

	private EnumFormComboViewer<EclipseLinkTenantDiscriminatorColumn2_3, DiscriminatorType> addDiscriminatorTypeCombo(Composite container) {

		return new EnumFormComboViewer<EclipseLinkTenantDiscriminatorColumn2_3, DiscriminatorType>(
			this,
			getSubjectHolder(),
			container)
		{
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(NamedDiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE_PROPERTY);
				propertyNames.add(NamedDiscriminatorColumn.SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY);
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
				switch (value) {
					case CHAR :
						return JptJpaUiDetailsMessages.DISCRIMINATOR_COLUMN_COMPOSITE_CHAR;
					case INTEGER :
						return JptJpaUiDetailsMessages.DISCRIMINATOR_COLUMN_COMPOSITE_INTEGER;
					case STRING :
						return JptJpaUiDetailsMessages.DISCRIMINATOR_COLUMN_COMPOSITE_STRING;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected String nullDisplayString() {
				return JptCommonUiMessages.NONE_SELECTED;
			}

			@Override
			protected DiscriminatorType getValue() {
				return getSubject().getSpecifiedDiscriminatorType();
			}

			@Override
			protected void setValue(DiscriminatorType value) {
				((EclipseLinkSpecifiedTenantDiscriminatorColumn2_3) this.getSubject()).setSpecifiedDiscriminatorType(value);
			}

			@Override
			protected String getHelpId() {
				return EclipseLinkHelpContextIds.TENANT_DISCRIMINATOR_COLUMN_DISCRIMINATOR_TYPE;
			}
		};
	}

	@SuppressWarnings("unused")
	private void addLengthCombo(Composite container) {
		new LengthCombo(this, container);
	}

	static class LengthCombo
		extends IntegerCombo<EclipseLinkTenantDiscriminatorColumn2_3>
	{
		LengthCombo(Pane<? extends EclipseLinkTenantDiscriminatorColumn2_3> parentPane, Composite parent) {
			super(parentPane, parent);
		}

		@Override
		protected String getHelpId() {
			return EclipseLinkHelpContextIds.TENANT_DISCRIMINATOR_COLUMN_LENGTH;
		}

		@Override
		protected PropertyValueModel<Integer> buildDefaultModel() {
			return PropertyValueModelTools.subjectModelAspectAdapter(
					this.getSubjectHolder(),
					NamedDiscriminatorColumn.DEFAULT_LENGTH_PROPERTY,
					m -> Integer.valueOf(m.getDefaultLength())
				);
		}

		@Override
		protected ModifiablePropertyValueModel<Integer> buildSelectedItemModel() {
			return new PropertyAspectAdapterXXXX<EclipseLinkTenantDiscriminatorColumn2_3, Integer>(getSubjectHolder(), NamedDiscriminatorColumn.SPECIFIED_LENGTH_PROPERTY) {
				@Override
				protected Integer buildValue_() {
					return this.subject.getSpecifiedLength();
				}

				@Override
				protected void setValue_(Integer value) {
					((EclipseLinkSpecifiedTenantDiscriminatorColumn2_3) this.subject).setSpecifiedLength(value);
				}
			};
		}
	}

	private ModifiablePropertyValueModel<String> buildColumnDefinitionModel(PropertyValueModel<? extends EclipseLinkTenantDiscriminatorColumn2_3> discriminatorColumnModel) {
		return new PropertyAspectAdapterXXXX<EclipseLinkTenantDiscriminatorColumn2_3, String>(discriminatorColumnModel, NamedColumn.COLUMN_DEFINITION_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getColumnDefinition();
			}
			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				((EclipseLinkSpecifiedTenantDiscriminatorColumn2_3) this.subject).setColumnDefinition(value);
			}
		};
	}

	ModifiablePropertyValueModel<Boolean> buildSpecifiedPrimaryKeyModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkTenantDiscriminatorColumn2_3, Boolean>(getSubjectHolder(), EclipseLinkTenantDiscriminatorColumn2_3.SPECIFIED_PRIMARY_KEY_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedPrimaryKey();
			}

			@Override
			protected void setValue_(Boolean value) {
				((EclipseLinkSpecifiedTenantDiscriminatorColumn2_3) this.subject).setSpecifiedPrimaryKey(value);
			}
		};
	}

	PropertyValueModel<String> buildPrimaryKeyStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultPrimaryKeyModel(), PRIMARY_KEY_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> PRIMARY_KEY_TRANSFORMER = new TriStateCheckBoxLabelModelStringTransformer(
				JptJpaEclipseLinkUiDetailsMessages.TENANT_DISCRIMINATOR_COLUMN_COMPOSITE_PRIMARY_KEY_WITH_DEFAULT,
				JptJpaEclipseLinkUiDetailsMessages.TENANT_DISCRIMINATOR_COLUMN_COMPOSITE_PRIMARY_KEY
			);

	PropertyValueModel<Boolean> buildDefaultPrimaryKeyModel() {
		return TriStateCheckBoxLabelModelAdapter.adaptSubjectModelAspects_(
				this.getSubjectHolder(),
				EclipseLinkTenantDiscriminatorColumn2_3.SPECIFIED_PRIMARY_KEY_PROPERTY,
				tdc -> tdc.getSpecifiedPrimaryKey(),
				EclipseLinkTenantDiscriminatorColumn2_3.DEFAULT_PRIMARY_KEY_PROPERTY,
				tdc -> tdc.getDefaultPrimaryKey()
			);
	}
}

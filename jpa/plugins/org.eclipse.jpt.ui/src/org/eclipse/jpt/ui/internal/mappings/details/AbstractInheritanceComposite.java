/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Collection;
import org.eclipse.jpt.core.context.DiscriminatorColumn;
import org.eclipse.jpt.core.context.DiscriminatorType;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.ColumnCombo;
import org.eclipse.jpt.ui.internal.util.LabeledControlUpdater;
import org.eclipse.jpt.ui.internal.util.LabeledLabel;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                      ---------------------------------------------------- |
 * | Strategy:            | EnumComboViewer                                |v| |
 * |                      ---------------------------------------------------- |
 * |                      ---------------------------------------------------- |
 * | Value:               | I                                              |v| |
 * |                      ---------------------------------------------------- |
 * |                                                                           |
 * | > Discriminator Column                                                    |
 * |                                                                           |
 * |                      ---------------------------------------------------- |
 * | Name:                | ColumnCombo                                    |v| |
 * |                      ---------------------------------------------------- |
 * |                      ---------------------------------------------------- |
 * | Type:                | EnumComboViewer                                |v| |
 * |                      ---------------------------------------------------- |
 * |                      ---------------------------------------------------- |
 * | Column Definition:   | I                                                | |
 * |                      ---------------------------------------------------- |
 * |                      -------------                                        |
 * | Length:              | I       |I|                                        |
 * |                      -------------                                        |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | PrimaryKeyJoinColumnsComposite                                        | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see Entity
 * @see AbstractEntityComposite - The parent container
 * @see ColumnCombo
 * @see EnumComboViewer
 * @see PrimaryKeyJoinColumnsComposite
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class AbstractInheritanceComposite<T extends Entity> extends AbstractPane<T> {

	/**
	 * A key used to represent the default value, this is required to convert
	 * the selected item from a combo to <code>null</code>. This key is most
	 * likely never typed the user and it will help to convert the value to
	 * <code>null</code> when it's time to set the new selected value into the
	 * model.
	 */
	protected static String DEFAULT_KEY = "?!#!?#?#?default?#?!#?!#?";

	/**
	 * Creates a new <code>InheritanceComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public AbstractInheritanceComposite(AbstractPane<? extends T> parentPane,
	                            Composite parent) {

		super(parentPane, parent, false);
	}

	private WritablePropertyValueModel<String> buildColumnDefinitionHolder(PropertyValueModel<DiscriminatorColumn> discriminatorColumnHolder) {

		return new PropertyAspectAdapter<DiscriminatorColumn, String>(discriminatorColumnHolder, DiscriminatorColumn.COLUMN_DEFINITION_PROPERTY) {
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

	private ListValueModel<String> buildDefaultDiscriminatorListValueHolder() {
		return new PropertyListValueModelAdapter<String>(
			buildDefaultDiscriminatorValueHolder()
		);
	}

	private WritablePropertyValueModel<String> buildDefaultDiscriminatorValueHolder() {
		return new PropertyAspectAdapter<Entity, String>(getSubjectHolder(), Entity.DEFAULT_DISCRIMINATOR_VALUE_PROPERTY) {
			@Override
			protected String buildValue_() {
				String name = subject.getDefaultDiscriminatorValue();

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

	private WritablePropertyValueModel<Integer> buildDefaultLengthHolder() {
		return new PropertyAspectAdapter<DiscriminatorColumn, Integer>(buildDiscriminatorColumnHolder(), DiscriminatorColumn.DEFAULT_LENGTH_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return subject.getDefaultLength();
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Integer.MIN_VALUE, newValue);
				}
			}
		};
	}

	private Control buildDefaultLengthLabel(Composite container) {

		Label label = buildLabel(
			container,
			JptUiMappingsMessages.DefaultWithoutValue
		);

		new LabeledControlUpdater(
			new LabeledLabel(label),
			buildDefaultLengthLabelHolder()
		);

		return label;
	}

	private PropertyValueModel<String> buildDefaultLengthLabelHolder() {

		return new TransformationPropertyValueModel<Integer, String>(buildDefaultLengthHolder()) {

			@Override
			protected String transform(Integer value) {

				Integer defaultValue = (subject() != null) ? subject().getDiscriminatorColumn().getDefaultLength() :
				                                             DiscriminatorColumn.DEFAULT_LENGTH;

				return NLS.bind(
					JptUiMappingsMessages.DefaultWithValue,
					defaultValue
				);
			}
		};
	}

	private ColumnCombo<DiscriminatorColumn> buildDiscriminatorColumnCombo(
		Composite container,
		PropertyValueModel<DiscriminatorColumn> discriminatorColumnHolder) {

		return new ColumnCombo<DiscriminatorColumn>(
			this,
			discriminatorColumnHolder,
			container)
		{

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(NamedColumn.SPECIFIED_NAME_PROPERTY);
				propertyNames.add(NamedColumn.DEFAULT_NAME_PROPERTY);
			}

			@Override
			protected String defaultValue() {
				return subject().getDefaultName();
			}

			@Override
			protected void setValue(String value) {
				subject().setSpecifiedName(value);
			}

			@Override
			protected Table table() {
				return subject().getDbTable();
			}

			@Override
			protected String value() {
				return subject().getSpecifiedName();
			}
		};
	}

	private PropertyValueModel<DiscriminatorColumn> buildDiscriminatorColumnHolder() {
		return new TransformationPropertyValueModel<Entity, DiscriminatorColumn>(getSubjectHolder()) {
			@Override
			protected DiscriminatorColumn transform_(Entity value) {
				return value.getDiscriminatorColumn();
			}
		};
	}

	private EnumFormComboViewer<DiscriminatorColumn, DiscriminatorType> buildDiscriminatorTypeCombo(
		Composite container,
		PropertyValueModel<DiscriminatorColumn> discriminatorColumnHolder) {

		return new EnumFormComboViewer<DiscriminatorColumn, DiscriminatorType>(
			this,
			discriminatorColumnHolder,
			container)
		{
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(DiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE_PROPERTY);
				propertyNames.add(DiscriminatorColumn.SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY);
			}

			@Override
			protected DiscriminatorType[] choices() {
				return DiscriminatorType.values();
			}

			@Override
			protected DiscriminatorType defaultValue() {
				return subject().getDefaultDiscriminatorType();
			}

			@Override
			protected String displayString(DiscriminatorType value) {
				return buildDisplayString(
					JptUiMappingsMessages.class,
					AbstractInheritanceComposite.class,
					value
				);
			}

			@Override
			protected DiscriminatorType getValue() {
				return subject().getSpecifiedDiscriminatorType();
			}

			@Override
			protected void setValue(DiscriminatorType value) {
				subject().setSpecifiedDiscriminatorType(value);
			}
		};
	}

	private StringConverter<String> buildDiscriminatorValueConverter() {
		return new StringConverter<String>() {
			public String convertToString(String value) {

				if (subject() == null) {
					return null;
				}

				if (value == null) {
					value = subject().getDefaultDiscriminatorValue();

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

	private WritablePropertyValueModel<String> buildDiscriminatorValueHolder() {
		return new PropertyAspectAdapter<Entity, String>(getSubjectHolder(), Entity.SPECIFIED_DISCRIMINATOR_VALUE_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getSpecifiedDiscriminatorValue();
			}

			@Override
			protected void setValue_(String value) {

				// Convert the default value or an empty string to null
				if ((value != null) &&
				   ((value.length() == 0) || value.startsWith(DEFAULT_KEY))) {

					value = null;
				}

				subject.setSpecifiedDiscriminatorValue(value);
			}
		};
	}

	private ListValueModel<String> buildDiscriminatorValueListHolder() {
		return buildDefaultDiscriminatorListValueHolder();
	}

	private WritablePropertyValueModel<Integer> buildLengthHolder(PropertyValueModel<DiscriminatorColumn> discriminatorColumnHolder) {

		return new PropertyAspectAdapter<DiscriminatorColumn, Integer>(discriminatorColumnHolder, DiscriminatorColumn.SPECIFIED_LENGTH_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return subject.getSpecifiedLength();
			}

			@Override
			protected void setValue_(Integer value) {
				if (value == -1) {
					value = null;
				}
				subject.setSpecifiedLength(value);
			}
		};
	}

	private EnumFormComboViewer<Entity, InheritanceType> buildStrategyCombo(Composite container) {

		return new EnumFormComboViewer<Entity, InheritanceType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Entity.DEFAULT_INHERITANCE_STRATEGY_PROPERTY);
				propertyNames.add(Entity.SPECIFIED_INHERITANCE_STRATEGY_PROPERTY);
			}

			@Override
			protected InheritanceType[] choices() {
				return InheritanceType.values();
			}

			@Override
			protected InheritanceType defaultValue() {
				return subject().getDefaultInheritanceStrategy();
			}

			@Override
			protected String displayString(InheritanceType value) {
				return buildDisplayString(
					JptUiMappingsMessages.class,
					AbstractInheritanceComposite.class,
					value
				);
			}

			@Override
			protected InheritanceType getValue() {
				return subject().getSpecifiedInheritanceStrategy();
			}

			@Override
			protected void setValue(InheritanceType value) {
				subject().setSpecifiedInheritanceStrategy(value);
			}
		};
	}

	private void initializeDiscriminatorColumnPane(Composite container) {

		PropertyValueModel<DiscriminatorColumn> discriminatorColumnHolder =
			buildDiscriminatorColumnHolder();

		// Name widgets
		buildLabeledComposite(
			container,
			JptUiMappingsMessages.DiscriminatorColumnComposite_name,
			buildDiscriminatorColumnCombo(container, discriminatorColumnHolder),
			JpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_COLUMN
		);

		// Discriminator Type widgets
		buildLabeledComposite(
			container,
			JptUiMappingsMessages.DiscriminatorColumnComposite_discriminatorType,
			buildDiscriminatorTypeCombo(container, discriminatorColumnHolder),
			JpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_TYPE
		);

		container = buildCollapsableSubSection(
			buildSubPane(container, 10),
			JptUiMappingsMessages.InheritanceComposite_detailsGroupBox,
			new SimplePropertyValueModel<Boolean>(Boolean.FALSE)
		);

		// Length widgets
		Spinner lengthSpinner = buildLabeledSpinner(
			container,
			JptUiMappingsMessages.ColumnComposite_length,
			buildLengthHolder(discriminatorColumnHolder),
			-1,
			-1,
			Integer.MAX_VALUE,
			buildDefaultLengthLabel(container),
			JpaHelpContextIds.MAPPING_COLUMN_LENGTH
		);

		updateGridData(container, lengthSpinner);

		// Column Definition widgets
		buildLabeledText(
			container,
			JptUiMappingsMessages.ColumnComposite_columnDefinition,
			buildColumnDefinitionHolder(discriminatorColumnHolder)
		);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = groupBoxMargin();

		Composite subPane = buildSubPane(
			container, 0, groupBoxMargin, 0, groupBoxMargin
		);

		// Strategy widgets
		buildLabeledComposite(
			subPane,
			JptUiMappingsMessages.InheritanceComposite_strategy,
			buildStrategyCombo(subPane),
			JpaHelpContextIds.ENTITY_INHERITANCE_STRATEGY
		);

		// Discrinator Value widgets
		CCombo discriminatorValueCombo = buildLabeledEditableCCombo(
			subPane,
			JptUiMappingsMessages.InheritanceComposite_discriminatorValue,
			buildDiscriminatorValueListHolder(),
			buildDiscriminatorValueHolder(),
			buildDiscriminatorValueConverter(),
			JpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_VALUE
		);

		// Discriminator Column sub-pane
		Composite discriminatorColumnContainer = buildTitledPane(
			buildSubPane(container, 10),
			JptUiMappingsMessages.InheritanceComposite_discriminatorColumnGroupBox
		);

		initializeDiscriminatorColumnPane(discriminatorColumnContainer);

		// Primary Key Join Columns widgets
		buildPrimaryKeyJoinColumnsComposite(buildSubPane(container, 5));
	}

	protected abstract void buildPrimaryKeyJoinColumnsComposite(Composite container);

	/**
	 * Changes the layout of the given container by changing which widget will
	 * grab the excess of horizontal space. By default, the center control grabs
	 * the excess space, we change it to be the right control.
	 *
	 * @param container The container containing the controls needing their
	 * <code>GridData</code> to be modified from the default values
	 * @param spinner The spinner that got created
	 */
	private void updateGridData(Composite container, Spinner spinner) {

		// It is possible the spinner's parent is not the container of the
		// label, spinner and right control (a pane is sometimes required for
		// painting the spinner's border)
		Composite paneContainer = spinner.getParent();

		while (container != paneContainer.getParent()) {
			paneContainer = paneContainer.getParent();
		}

		Control[] controls = paneContainer.getChildren();

		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.horizontalAlignment       = GridData.BEGINNING;
		controls[1].setLayoutData(gridData);

		controls[2].setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		removeAlignRight(controls[2]);
	}
}
/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
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
public abstract class AbstractInheritanceComposite<T extends Entity> extends FormPane<T> {

	/**
	 * A key used to represent the default value, this is required to convert
	 * the selected item from a combo to <code>null</code>. This key is most
	 * likely never typed the user and it will help to convert the value to
	 * <code>null</code> when it's time to set the new selected value into the
	 * model.
	 */
	protected static String DEFAULT_KEY = "?!#!?#?#?default?#?!#?!#?";
	
	protected static String NONE_KEY = "?!#!?#?#?none?#?!#?!#?";

	/**
	 * Creates a new <code>InheritanceComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public AbstractInheritanceComposite(FormPane<? extends T> parentPane,
	                            Composite parent) {

		super(parentPane, parent, false);
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
				String name = this.subject.getDefaultDiscriminatorValue();
				if (name == null && ! getSubject().isDiscriminatorValueAllowed()) {
					return NONE_KEY;
				}

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

	private ColumnCombo<DiscriminatorColumn> addDiscriminatorColumnCombo(
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
			protected String buildNullDefaultValueEntry() {
				return JptUiMappingsMessages.NoneSelected;
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

	private EnumFormComboViewer<DiscriminatorColumn, DiscriminatorType> addDiscriminatorTypeCombo(
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
					JptUiMappingsMessages.class,
					AbstractInheritanceComposite.class,
					value
				);
			}
			
			@Override
			protected String nullDisplayString() {
				return JptUiMappingsMessages.NoneSelected;
			}
			
			@Override
			protected DiscriminatorType getValue() {
				return getSubject().getSpecifiedDiscriminatorType();
			}

			@Override
			protected void setValue(DiscriminatorType value) {
				getSubject().setSpecifiedDiscriminatorType(value);
			}
		};
	}

	private StringConverter<String> buildDiscriminatorValueConverter() {
		return new StringConverter<String>() {
			public String convertToString(String value) {

				if (getSubject() == null) {
					return null;
				}

				if (value == null) {
					value = getSubject().getDefaultDiscriminatorValue();
					if (value != null || getSubject().isDiscriminatorValueAllowed()) {
						value = (value != null) ? 
								DEFAULT_KEY + value
							: 
								DEFAULT_KEY;
					}
					else {
						value = NONE_KEY;						
					}
				}
				if (value.startsWith(DEFAULT_KEY)) {
					String defaultName = value.substring(DEFAULT_KEY.length());

					if (defaultName.length() > 0) {
						value = NLS.bind(
							JptUiMappingsMessages.DefaultWithOneParam,
							defaultName
						);
					}
					else {
						value = JptUiMappingsMessages.ProviderDefault;
					}
				}
				if (value.startsWith(NONE_KEY)) {
					value = JptUiMappingsMessages.NoneSelected;
				}
				return value;
			}
		};
	}

	private WritablePropertyValueModel<String> buildDiscriminatorValueHolder() {
		return new PropertyAspectAdapter<Entity, String>(getSubjectHolder(), Entity.SPECIFIED_DISCRIMINATOR_VALUE_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getSpecifiedDiscriminatorValue();
			}

			@Override
			protected void setValue_(String value) {

				// Convert the default value or an empty string to null
				if ((value != null) &&
				   ((value.length() == 0) || value.startsWith(DEFAULT_KEY) || value.startsWith(NONE_KEY))) {

					value = null;
				}

				this.subject.setSpecifiedDiscriminatorValue(value);
			}
		};
	}

	private ListValueModel<String> buildDiscriminatorValueListHolder() {
		return buildDefaultDiscriminatorListValueHolder();
	}

	private EnumFormComboViewer<Entity, InheritanceType> addStrategyCombo(Composite container) {

		return new EnumFormComboViewer<Entity, InheritanceType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Entity.DEFAULT_INHERITANCE_STRATEGY_PROPERTY);
				propertyNames.add(Entity.SPECIFIED_INHERITANCE_STRATEGY_PROPERTY);
			}

			@Override
			protected InheritanceType[] getChoices() {
				return InheritanceType.values();
			}

			@Override
			protected InheritanceType getDefaultValue() {
				return getSubject().getDefaultInheritanceStrategy();
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
				return getSubject().getSpecifiedInheritanceStrategy();
			}

			@Override
			protected void setValue(InheritanceType value) {
				getSubject().setSpecifiedInheritanceStrategy(value);
			}
		};
	}

	private void initializeDiscriminatorColumnPane(Composite container) {

		PropertyValueModel<DiscriminatorColumn> discriminatorColumnHolder =
			buildDiscriminatorColumnHolder();

		// Name widgets
		addLabeledComposite(
			container,
			JptUiMappingsMessages.DiscriminatorColumnComposite_name,
			addDiscriminatorColumnCombo(container, discriminatorColumnHolder),
			JpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_COLUMN
		);

		// Discriminator Type widgets
		addLabeledComposite(
			container,
			JptUiMappingsMessages.DiscriminatorColumnComposite_discriminatorType,
			addDiscriminatorTypeCombo(container, discriminatorColumnHolder),
			JpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_TYPE
		);

		container = addCollapsableSubSection(
			container,
			JptUiMappingsMessages.InheritanceComposite_detailsGroupBox,
			new SimplePropertyValueModel<Boolean>(Boolean.FALSE)
		);
		
		new DetailsComposite(this, discriminatorColumnHolder, addSubPane(container, 0, 16));	
	}

	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = getGroupBoxMargin();

		Composite subPane = addSubPane(
			container, 0, groupBoxMargin, 0, groupBoxMargin
		);

		// Strategy widgets
		addLabeledComposite(
			subPane,
			JptUiMappingsMessages.InheritanceComposite_strategy,
			addStrategyCombo(subPane),
			JpaHelpContextIds.ENTITY_INHERITANCE_STRATEGY
		);

		// Discrinator Value widgets
		addLabeledEditableCCombo(
			subPane,
			JptUiMappingsMessages.InheritanceComposite_discriminatorValue,
			buildDiscriminatorValueListHolder(),
			buildDiscriminatorValueHolder(),
			buildDiscriminatorValueConverter(),
			JpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_VALUE
		);

		// Discriminator Column sub-pane
		Composite discriminatorColumnContainer = addTitledGroup(
			addSubPane(container, 10),
			JptUiMappingsMessages.InheritanceComposite_discriminatorColumnGroupBox
		);

		initializeDiscriminatorColumnPane(discriminatorColumnContainer);

		// Primary Key Join Columns widgets
		addPrimaryKeyJoinColumnsComposite(addSubPane(container, 5));
	}

	protected abstract void addPrimaryKeyJoinColumnsComposite(Composite container);

	
	protected class DetailsComposite extends FormPane<DiscriminatorColumn> {
		public DetailsComposite(FormPane<?> parentPane,
            PropertyValueModel<? extends DiscriminatorColumn> subjectHolder,
            Composite parent) {

			super(parentPane, subjectHolder, parent, false);
		}

		@Override
		protected void initializeLayout(Composite container) {
			// Length widgets
			Spinner lengthSpinner = addLabeledSpinner(
				container,
				JptUiMappingsMessages.ColumnComposite_length,
				buildLengthHolder(),
				-1,
				-1,
				Integer.MAX_VALUE,
				addDefaultLengthLabel(container),
				JpaHelpContextIds.MAPPING_COLUMN_LENGTH
			);

			updateGridData(container, lengthSpinner);

			// Column Definition widgets
			addLabeledText(
				container,
				JptUiMappingsMessages.ColumnComposite_columnDefinition,
				buildColumnDefinitionHolder(getSubjectHolder())
			);
		}

		private WritablePropertyValueModel<Integer> buildLengthHolder() {

			return new PropertyAspectAdapter<DiscriminatorColumn, Integer>(getSubjectHolder(), DiscriminatorColumn.SPECIFIED_LENGTH_PROPERTY) {
				@Override
				protected Integer buildValue_() {
					return this.subject.getSpecifiedLength();
				}

				@Override
				protected void setValue_(Integer value) {
					if (value.intValue() == -1) {
						value = null;
					}
					this.subject.setSpecifiedLength(value);
				}
			};
		}

		private Control addDefaultLengthLabel(Composite container) {

			Label label = addLabel(
				container,
				JptUiMappingsMessages.DefaultEmpty
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

					int defaultValue = (getSubject() != null) ? getSubject().getDefaultLength() :
					                                             DiscriminatorColumn.DEFAULT_LENGTH;

					return NLS.bind(
						JptUiMappingsMessages.DefaultWithOneParam,
						Integer.valueOf(defaultValue)
					);
				}
			};
		}

		private WritablePropertyValueModel<Integer> buildDefaultLengthHolder() {
			return new PropertyAspectAdapter<DiscriminatorColumn, Integer>(getSubjectHolder(), DiscriminatorColumn.DEFAULT_LENGTH_PROPERTY) {
				@Override
				protected Integer buildValue_() {
					return Integer.valueOf(this.subject.getDefaultLength());
				}

				@Override
				protected void subjectChanged() {
					Object oldValue = this.getValue();
					super.subjectChanged();
					Object newValue = this.getValue();

					// Make sure the default value is appended to the text
					if (oldValue == newValue && newValue == null) {
						this.fireAspectChange(Integer.valueOf(Integer.MIN_VALUE), newValue);
					}
				}
			};
		}

		private WritablePropertyValueModel<String> buildColumnDefinitionHolder(PropertyValueModel<DiscriminatorColumn> discriminatorColumnHolder) {

			return new PropertyAspectAdapter<DiscriminatorColumn, String>(discriminatorColumnHolder, NamedColumn.COLUMN_DEFINITION_PROPERTY) {
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
}
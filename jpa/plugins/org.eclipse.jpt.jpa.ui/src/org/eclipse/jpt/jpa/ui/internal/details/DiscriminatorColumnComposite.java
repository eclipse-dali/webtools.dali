/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.DiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.ModifiableNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.db.ColumnCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | > Discriminator Column                                                    |
 * |                                                                           |
 * |                      ---------------------------------------------------- |
 * | Name:                | ColumnCombo                                    |v| |
 * |                      ---------------------------------------------------- |
 * |                      ---------------------------------------------------- |
 * | Type:                | EnumComboViewer                                |v| |
 * |                      ---------------------------------------------------- |
 * | > Details																   |
 * |                                                                           |
 * |                      ---------------------------------------------------- |
 * | Column Definition:   | I                                                | |
 * |                      ---------------------------------------------------- |
 * |                      -------------                                        |
 * | Length:              | I       |I|                                        |
 * |                      -------------                                        |
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
public class DiscriminatorColumnComposite<T extends Entity> extends Pane<T> {

	/**
	 * Creates a new <code>InheritanceComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public DiscriminatorColumnComposite(Pane<? extends T> parentPane,
	                            Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return addTitledGroup(
			parent,
			JptJpaUiDetailsMessages.InheritanceComposite_discriminatorColumnGroupBox,
			2,
			null
		);
	}

	@Override
	protected void initializeLayout(Composite container) {
		PropertyValueModel<DiscriminatorColumn> discriminatorColumnHolder =
			buildDiscriminatorColumnHolder();
		PropertyValueModel<Boolean> enabledModel = buildDiscriminatorColumnEnabledHolder();

		// Name widgets
		this.addLabel(container, JptJpaUiDetailsMessages.DiscriminatorColumnComposite_name, enabledModel);
		this.addDiscriminatorColumnCombo(container, discriminatorColumnHolder, enabledModel);

		// Discriminator Type widgets
		this.addLabel(container,JptJpaUiDetailsMessages.DiscriminatorColumnComposite_discriminatorType, enabledModel);
		this.addDiscriminatorTypeCombo(container, discriminatorColumnHolder, enabledModel);


		Section detailsSection = this.getWidgetFactory().createSection(container, 
				ExpandableComposite.TWISTIE |
				ExpandableComposite.CLIENT_INDENT);
		detailsSection.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		detailsSection.setText(JptJpaUiDetailsMessages.InheritanceComposite_detailsGroupBox);
		detailsSection.setClient(this.initializeDetailsClient(detailsSection, discriminatorColumnHolder, enabledModel));
	}

	protected Control initializeDetailsClient(Section detailsSection, PropertyValueModel<DiscriminatorColumn> discriminatorColumnHolder, PropertyValueModel<Boolean> enabledModel) {
		Composite detailsClient = this.addSubPane(detailsSection, 2, 0, 0, 0, 0);
		detailsSection.setClient(detailsClient);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		detailsSection.setLayoutData(gridData);

		// Length widgets
		this.addLabel(detailsClient, JptJpaUiDetailsMessages.ColumnComposite_length, enabledModel);
		this.addLengthCombo(detailsClient, discriminatorColumnHolder, enabledModel);

		// Column Definition widgets
		this.addLabel(detailsClient, JptJpaUiDetailsMessages.ColumnComposite_columnDefinition, enabledModel);
		this.addText(detailsClient, this.buildColumnDefinitionHolder(discriminatorColumnHolder), null, enabledModel);

		return detailsClient;
	}

	private ColumnCombo<DiscriminatorColumn> addDiscriminatorColumnCombo(
		Composite container,
		PropertyValueModel<DiscriminatorColumn> discriminatorColumnHolder, 
		PropertyValueModel<Boolean> enabledModel) {

		return new ColumnCombo<DiscriminatorColumn>(
			this,
			discriminatorColumnHolder,
			enabledModel,
			container)
		{

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(ReadOnlyNamedColumn.SPECIFIED_NAME_PROPERTY);
				propertyNames.add(ReadOnlyNamedColumn.DEFAULT_NAME_PROPERTY);
				propertyNames.add(ReadOnlyNamedColumn.DB_TABLE_PROPERTY);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				if (propertyName.equals(ReadOnlyNamedColumn.DB_TABLE_PROPERTY)) {
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
			protected String buildNullDefaultValueEntry() {
				return JptCommonUiMessages.NONE_SELECTED;
			}

			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_COLUMN;
			}

			@Override
			public String toString() {
				return "DiscriminatorColumnComposite.columnCombo"; //$NON-NLS-1$
			}
		};
	}

	private PropertyValueModel<DiscriminatorColumn> buildDiscriminatorColumnHolder() {
		return new PropertyAspectAdapter<Entity, DiscriminatorColumn>(getSubjectHolder()) {
			@Override
			protected DiscriminatorColumn buildValue_() {
				return this.subject.getDiscriminatorColumn();
			}
		};
	}

	private EnumFormComboViewer<DiscriminatorColumn, DiscriminatorType> addDiscriminatorTypeCombo(
		Composite container,
		PropertyValueModel<DiscriminatorColumn> discriminatorColumnHolder,
		PropertyValueModel<Boolean> enabledModel) {

		return new EnumFormComboViewer<DiscriminatorColumn, DiscriminatorType>(
			this,
			discriminatorColumnHolder,
			enabledModel,
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
				switch (value) {
					case CHAR :
						return JptJpaUiDetailsMessages.DiscriminatorColumnComposite_char;
					case INTEGER :
						return JptJpaUiDetailsMessages.DiscriminatorColumnComposite_integer;
					case STRING :
						return JptJpaUiDetailsMessages.DiscriminatorColumnComposite_string;
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
				getSubject().setSpecifiedDiscriminatorType(value);
			}

			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_TYPE;
			}
		};
	}
	
	protected ModifiablePropertyValueModel<Boolean> buildDiscriminatorColumnEnabledHolder() {
		return new PropertyAspectAdapter<Entity, Boolean>(getSubjectHolder(), Entity.SPECIFIED_DISCRIMINATOR_COLUMN_IS_ALLOWED_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf( this.subject != null && this.subject.specifiedDiscriminatorColumnIsAllowed());
			}
		};
	}

	private void addLengthCombo(Composite container, PropertyValueModel<DiscriminatorColumn> subjectHolder, PropertyValueModel<Boolean> enabledModel) {
		new IntegerCombo<DiscriminatorColumn>(this, subjectHolder, enabledModel, container) {			
			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.MAPPING_COLUMN_LENGTH;
			}

			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<DiscriminatorColumn, Integer>(getSubjectHolder(), DiscriminatorColumn.DEFAULT_LENGTH_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return Integer.valueOf(this.subject.getDefaultLength());
					}
				};
			}
			
			@Override
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<DiscriminatorColumn, Integer>(getSubjectHolder(), DiscriminatorColumn.SPECIFIED_LENGTH_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getSpecifiedLength();
					}

					@Override
					protected void setValue_(Integer value) {
						this.subject.setSpecifiedLength(value);
					}
				};
			}
		};
	}

	private ModifiablePropertyValueModel<String> buildColumnDefinitionHolder(PropertyValueModel<DiscriminatorColumn> discriminatorColumnHolder) {

		return new PropertyAspectAdapter<DiscriminatorColumn, String>(discriminatorColumnHolder, ModifiableNamedColumn.COLUMN_DEFINITION_PROPERTY) {
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
}
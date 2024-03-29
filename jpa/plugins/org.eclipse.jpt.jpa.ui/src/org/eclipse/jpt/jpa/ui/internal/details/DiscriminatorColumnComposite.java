/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.core.context.SpecifiedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.SpecifiedNamedColumn;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
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
			JptJpaUiDetailsMessages.INHERITANCE_COMPOSITE_DISCRIMINATOR_COLUMN_GROUP_BOX,
			2,
			null
		);
	}

	@Override
	protected void initializeLayout(Composite container) {
		PropertyValueModel<SpecifiedDiscriminatorColumn> discriminatorColumnHolder =
			buildDiscriminatorColumnHolder();
		PropertyValueModel<Boolean> enabledModel = buildDiscriminatorColumnEnabledHolder();

		// Name widgets
		this.addLabel(container, JptJpaUiDetailsMessages.DISCRIMINATOR_COLUMN_COMPOSITE_NAME, enabledModel);
		this.addDiscriminatorColumnCombo(container, discriminatorColumnHolder, enabledModel);

		// Discriminator Type widgets
		this.addLabel(container,JptJpaUiDetailsMessages.DISCRIMINATOR_COLUMN_COMPOSITE_DISCRIMINATOR_TYPE, enabledModel);
		this.addDiscriminatorTypeCombo(container, discriminatorColumnHolder, enabledModel);


		Section detailsSection = this.getWidgetFactory().createSection(container, 
				ExpandableComposite.TWISTIE |
				ExpandableComposite.CLIENT_INDENT);
		detailsSection.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		detailsSection.setText(JptJpaUiDetailsMessages.INHERITANCE_COMPOSITE_DETAILS_GROUP_BOX);
		detailsSection.setClient(this.initializeDetailsClient(detailsSection, discriminatorColumnHolder, enabledModel));
	}

	protected Control initializeDetailsClient(Section detailsSection, PropertyValueModel<SpecifiedDiscriminatorColumn> discriminatorColumnHolder, PropertyValueModel<Boolean> enabledModel) {
		Composite detailsClient = this.addSubPane(detailsSection, 2, 0, 0, 0, 0);
		detailsSection.setClient(detailsClient);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		detailsSection.setLayoutData(gridData);

		// Length widgets
		this.addLabel(detailsClient, JptJpaUiDetailsMessages.COLUMN_COMPOSITE_LENGTH, enabledModel);
		this.addLengthCombo(detailsClient, discriminatorColumnHolder, enabledModel);

		// Column Definition widgets
		this.addLabel(detailsClient, JptJpaUiDetailsMessages.COLUMN_COMPOSITE_COLUMN_DEFINITION, enabledModel);
		this.addText(detailsClient, this.buildColumnDefinitionHolder(discriminatorColumnHolder), null, enabledModel);

		return detailsClient;
	}

	private ColumnCombo<SpecifiedDiscriminatorColumn> addDiscriminatorColumnCombo(
		Composite container,
		PropertyValueModel<SpecifiedDiscriminatorColumn> discriminatorColumnHolder, 
		PropertyValueModel<Boolean> enabledModel) {

		return new ColumnCombo<SpecifiedDiscriminatorColumn>(
			this,
			discriminatorColumnHolder,
			enabledModel,
			container)
		{

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(NamedColumn.SPECIFIED_NAME_PROPERTY);
				propertyNames.add(NamedColumn.DEFAULT_NAME_PROPERTY);
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

	private PropertyValueModel<SpecifiedDiscriminatorColumn> buildDiscriminatorColumnHolder() {
		return new PropertyAspectAdapter<Entity, SpecifiedDiscriminatorColumn>(getSubjectHolder()) {
			@Override
			protected SpecifiedDiscriminatorColumn buildValue_() {
				return this.subject.getDiscriminatorColumn();
			}
		};
	}

	private EnumFormComboViewer<SpecifiedDiscriminatorColumn, DiscriminatorType> addDiscriminatorTypeCombo(
		Composite container,
		PropertyValueModel<SpecifiedDiscriminatorColumn> discriminatorColumnHolder,
		PropertyValueModel<Boolean> enabledModel) {

		return new EnumFormComboViewer<SpecifiedDiscriminatorColumn, DiscriminatorType>(
			this,
			discriminatorColumnHolder,
			enabledModel,
			container)
		{
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SpecifiedDiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE_PROPERTY);
				propertyNames.add(SpecifiedDiscriminatorColumn.SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY);
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

	private void addLengthCombo(Composite container, PropertyValueModel<SpecifiedDiscriminatorColumn> subjectHolder, PropertyValueModel<Boolean> enabledModel) {
		new IntegerCombo<SpecifiedDiscriminatorColumn>(this, subjectHolder, enabledModel, container) {			
			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.MAPPING_COLUMN_LENGTH;
			}

			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<SpecifiedDiscriminatorColumn, Integer>(getSubjectHolder(), SpecifiedDiscriminatorColumn.DEFAULT_LENGTH_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return Integer.valueOf(this.subject.getDefaultLength());
					}
				};
			}
			
			@Override
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<SpecifiedDiscriminatorColumn, Integer>(getSubjectHolder(), SpecifiedDiscriminatorColumn.SPECIFIED_LENGTH_PROPERTY) {
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

	private ModifiablePropertyValueModel<String> buildColumnDefinitionHolder(PropertyValueModel<SpecifiedDiscriminatorColumn> discriminatorColumnHolder) {

		return new PropertyAspectAdapter<SpecifiedDiscriminatorColumn, String>(discriminatorColumnHolder, SpecifiedNamedColumn.COLUMN_DEFINITION_PROPERTY) {
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
/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.DiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.db.ColumnCombo;
import org.eclipse.swt.widgets.Composite;

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

		super(parentPane, parent, false);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Discriminator Column sub-pane
		Composite discriminatorColumnContainer = addTitledGroup(
			addSubPane(container, 10),
			JptUiDetailsMessages.InheritanceComposite_discriminatorColumnGroupBox
		);

		PropertyValueModel<DiscriminatorColumn> discriminatorColumnHolder =
			buildDiscriminatorColumnHolder();

		// Name widgets
		addLabeledComposite(
			discriminatorColumnContainer,
			JptUiDetailsMessages.DiscriminatorColumnComposite_name,
			addDiscriminatorColumnCombo(container, discriminatorColumnHolder),
			JpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_COLUMN
		);

		// Discriminator Type widgets
		addLabeledComposite(
			discriminatorColumnContainer,
			JptUiDetailsMessages.DiscriminatorColumnComposite_discriminatorType,
			addDiscriminatorTypeCombo(container, discriminatorColumnHolder),
			JpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_TYPE
		);

		container = addCollapsibleSubSection(
			discriminatorColumnContainer,
			JptUiDetailsMessages.InheritanceComposite_detailsGroupBox,
			new SimplePropertyValueModel<Boolean>(Boolean.FALSE)
		);
		
		new DetailsComposite(this, discriminatorColumnHolder, addSubPane(container, 0, 16));
		
		new PaneEnabler(buildDiscriminatorColumnEnabledHolder(), this);
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
				propertyNames.add(ReadOnlyNamedColumn.SPECIFIED_NAME_PROPERTY);
				propertyNames.add(ReadOnlyNamedColumn.DEFAULT_NAME_PROPERTY);
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
				return JptCommonUiMessages.NoneSelected;
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
				getSubject().setSpecifiedDiscriminatorType(value);
			}
		};
	}
	
	protected WritablePropertyValueModel<Boolean> buildDiscriminatorColumnEnabledHolder() {
		return new PropertyAspectAdapter<Entity, Boolean>(getSubjectHolder(), Entity.SPECIFIED_DISCRIMINATOR_COLUMN_IS_ALLOWED_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.specifiedDiscriminatorColumnIsAllowed());
			}
		};
	}
	
	protected class DetailsComposite extends Pane<DiscriminatorColumn> {
		public DetailsComposite(Pane<?> parentPane,
            PropertyValueModel<? extends DiscriminatorColumn> subjectHolder,
            Composite parent) {

			super(parentPane, subjectHolder, parent, false);
		}

		@Override
		protected void initializeLayout(Composite container) {
			// Length widgets
			addLengthCombo(container);

			// Column Definition widgets
			addLabeledText(
				container,
				JptUiDetailsMessages.ColumnComposite_columnDefinition,
				buildColumnDefinitionHolder(getSubjectHolder())
			);
		}
		
		private void addLengthCombo(Composite container) {
			new IntegerCombo<DiscriminatorColumn>(this, container) {
				
				@Override
				protected String getLabelText() {
					return JptUiDetailsMessages.ColumnComposite_length;
				}
			
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
				protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
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
	}
}
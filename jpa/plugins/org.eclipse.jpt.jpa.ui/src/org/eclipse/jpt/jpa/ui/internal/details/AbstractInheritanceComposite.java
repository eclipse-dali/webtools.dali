/*******************************************************************************
 * Copyright (c) 2006, 2016 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.db.ColumnCombo;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

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
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class AbstractInheritanceComposite<T extends Entity>
	extends Pane<T>
{
	public AbstractInheritanceComposite(Pane<? extends T> parentPane,
	                            Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected Composite addComposite(Composite container) {
		return this.addSubPane(container, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Strategy widgets
		this.addLabel(container, JptJpaUiDetailsMessages.INHERITANCE_COMPOSITE_STRATEGY);
		this.addStrategyCombo(container);

		// Discriminator Value widgets
		PropertyValueModel<Boolean> dvEnabled = this.buildDiscriminatorValueEnabledHolder();
		this.addLabel(
			container, 
			JptJpaUiDetailsMessages.INHERITANCE_COMPOSITE_DISCRIMINATOR_VALUE,
			dvEnabled
		);
		this.addEditableCombo(
			container,
			buildDiscriminatorValueListHolder(),
			buildDiscriminatorValueHolder(),
			TransformerTools.<String>objectToStringTransformer(),
			dvEnabled,
			JpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_VALUE
		);
	
		// Discriminator column widgets
		DiscriminatorColumnComposite<Entity> discriminatorColumnComposite = new DiscriminatorColumnComposite<>(this, container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		gridData.verticalIndent = 10;
		discriminatorColumnComposite.getControl().setLayoutData(gridData);

		// Primary Key Join Columns widgets
		Control pkJoinColumnsComposite = addPrimaryKeyJoinColumnsComposite(container);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		pkJoinColumnsComposite.setLayoutData(gridData);
	}
	
	protected PropertyValueModel<Boolean> buildDiscriminatorValueEnabledHolder() {
		return PropertyValueModelTools.subjectModelAspectAdapter(
				this.getSubjectHolder(),
				Entity.SPECIFIED_DISCRIMINATOR_VALUE_IS_ALLOWED_PROPERTY,
				m -> Boolean.valueOf(m.specifiedDiscriminatorValueIsAllowed())
			);
	}

	private ListValueModel<String> buildDefaultDiscriminatorListValueHolder() {
		return new PropertyListValueModelAdapter<>(
			buildDefaultDiscriminatorValueHolder()
		);
	}

	private PropertyValueModel<String> buildDefaultDiscriminatorValueHolder() {
		return PropertyValueModelTools.subjectModelAspectAdapter(
				this.getSubjectHolder(),
				Entity.DISCRIMINATOR_VALUE_PROPERTY,
				m -> defaultValue(m)
			);
	}

	String defaultValue(Entity subject) {
		String defaultValue = subject.getDefaultDiscriminatorValue();

		if (defaultValue == null && subject.discriminatorValueIsUndefined()) {
			return JptCommonUiMessages.NONE_SELECTED;
		}
		if (defaultValue != null && defaultValue.length() > 0) {
			return NLS.bind(
				JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM,
				defaultValue
			);
		}
		return JptJpaUiDetailsMessages.PROVIDER_DEFAULT;
	}


	private ModifiablePropertyValueModel<String> buildDiscriminatorValueHolder() {
		return new PropertyAspectAdapterXXXX<Entity, String>(getSubjectHolder(), Entity.SPECIFIED_DISCRIMINATOR_VALUE_PROPERTY) {
			@Override
			protected String buildValue_() {
				String v = this.subject.getSpecifiedDiscriminatorValue();
				if (v == null) {
					return defaultValue(this.subject);
				}
				return v;
			}

			@Override
			protected void setValue_(String value) {
				if (defaultValue(this.subject).equals(value)) {
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
				switch (value) {
					case JOINED :
						return JptJpaUiDetailsMessages.ABSTRACT_INHERITANCE_COMPOSITE_JOINED;
					case SINGLE_TABLE :
						return JptJpaUiDetailsMessages.ABSTRACT_INHERITANCE_COMPOSITE_SINGLE_TABLE;
					case TABLE_PER_CLASS :
						return JptJpaUiDetailsMessages.ABSTRACT_INHERITANCE_COMPOSITE_TABLE_PER_CLASS;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected InheritanceType getValue() {
				return getSubject().getSpecifiedInheritanceStrategy();
			}

			@Override
			protected void setValue(InheritanceType value) {
				getSubject().setSpecifiedInheritanceStrategy(value);
			}

			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.ENTITY_INHERITANCE_STRATEGY;
			}
		};
	}

	protected abstract Control addPrimaryKeyJoinColumnsComposite(Composite container);

}
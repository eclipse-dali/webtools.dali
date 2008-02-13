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
import org.eclipse.jpt.core.internal.context.base.DiscriminatorType;
import org.eclipse.jpt.core.internal.context.base.IDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.InheritanceType;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.ColumnCombo;
import org.eclipse.jpt.ui.internal.util.ControlEnabler;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.IWidgetFactory;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                      ---------------------------------------------------- |
 * | Strategy:            | EnumComboViewer                                |v| |
 * |                      ---------------------------------------------------- |
 * |                      ---------------------------------------------------- |
 * | Column:              | ColumnCombo                                    |v| |
 * |                      ---------------------------------------------------- |
 * |                      ---------------------------------------------------- |
 * | Discriminator Type:  | EnumComboViewer                                |v| |
 * |                      ---------------------------------------------------- |
 * |                      ---------------------------------------------------- |
 * | Discriminator Value: | I                                              |v| |
 * |                      ---------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | PrimaryKeyJoinColumnsComposite                                        | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IEntity
 * @see EntityComposite - The parent container
 * @see ColumnCombo
 * @see EnumComboViewer
 * @see PrimaryKeyJoinColumnsComposite
 *
 * @version 2.0
 * @since 2.0
 */
public class InheritanceComposite extends AbstractFormPane<IEntity> {

	private CCombo discriminatorValueCombo;

	/**
	 * Creates a new <code>InheritanceComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public InheritanceComposite(AbstractFormPane<? extends IEntity> parentPane,
	                            Composite parent) {

		super(parentPane, parent, false);
	}

	/**
	 * Creates a new <code>InheritanceComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public InheritanceComposite(PropertyValueModel<? extends IEntity> subjectHolder,
	                            Composite parent,
	                            IWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(IEntity.DEFAULT_DISCRIMINATOR_VALUE_PROPERTY);
		propertyNames.add(IEntity.SPECIFIED_DISCRIMINATOR_VALUE_PROPERTY);
	}

	private ColumnCombo<IDiscriminatorColumn> buildColumnCombo(Composite container) {

		return new ColumnCombo<IDiscriminatorColumn>(
			this,
			buildDiscriminatorColumnHolder(),
			container)
		{
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
				return subject().dbTable();
			}

			@Override
			protected String value() {
				return subject().getSpecifiedName();
			}
		};
	}

	private PropertyValueModel<IDiscriminatorColumn> buildDiscriminatorColumnHolder() {
		return new TransformationPropertyValueModel<IEntity, IDiscriminatorColumn>(getSubjectHolder()) {
			@Override
			protected IDiscriminatorColumn transform_(IEntity value) {
				return value.getDiscriminatorColumn();
			}
		};
	}

	private EnumFormComboViewer<IDiscriminatorColumn, DiscriminatorType> buildDiscriminatorTypeCombo(Composite container) {

		return new EnumFormComboViewer<IDiscriminatorColumn, DiscriminatorType>(
			this,
			buildDiscriminatorColumnHolder(),
			container)
		{
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(IDiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE_PROPERTY);
				propertyNames.add(IDiscriminatorColumn.SPECIFIED_DISCRIMINATOR_TYPE_PROPERTY);
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
					InheritanceComposite.this,
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

	private PropertyValueModel<Boolean> buildDiscriminatorValueBooleanHolder() {
		return new PropertyAspectAdapter<IEntity, Boolean>(getSubjectHolder(), IEntity.DISCRIMINATOR_VALUE_ALLOWED_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.isDiscriminatorValueAllowed();
			}
		};
	}

	private ModifyListener buildDiscriminatorValueComboSelectionListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (!isPopulating()) {
					CCombo combo = (CCombo) e.widget;
					discriminatorValueChanged(combo.getText());
				}
			}
		};
	}

	private EnumFormComboViewer<IEntity, InheritanceType> buildStrategyCombo(Composite container) {

		return new EnumFormComboViewer<IEntity, InheritanceType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(IEntity.DEFAULT_INHERITANCE_STRATEGY_PROPERTY);
				propertyNames.add(IEntity.SPECIFIED_INHERITANCE_STRATEGY_PROPERTY);
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
					InheritanceComposite.this,
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

	private void discriminatorValueChanged(String value) {

		IEntity subject = subject();
		String oldValue = (subject != null) ? subject.getSpecifiedDiscriminatorValue() : null;

		// Check for null value
		if (StringTools.stringIsEmpty(value)) {
			value = null;

			if (StringTools.stringIsEmpty(oldValue)) {
				return;
			}
		}

		// The default value
		if (value != null &&
		    discriminatorValueCombo.getItemCount() > 0 &&
		    value.equals(discriminatorValueCombo.getItem(0)))
		{
			value = null;
		}

		// Nothing to change
		if ((oldValue == value) && value == null) {
			return;
		}

		// Set the new value
		if ((value != null) && (oldValue == null) ||
		   ((oldValue != null) && !oldValue.equals(value))) {

			setPopulating(true);

			try {
				subject.setSpecifiedDiscriminatorValue(value);
			}
			finally {
				setPopulating(false);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		populateDiscriminatorValueCombo();
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
			IJpaHelpContextIds.ENTITY_INHERITANCE_STRATEGY
		);

		// Column widgets
		buildLabeledComposite(
			subPane,
			JptUiMappingsMessages.DiscriminatorColumnComposite_column,
			buildColumnCombo(subPane),
			IJpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_COLUMN
		);

		// Discriminator Type widgets
		buildLabeledComposite(
			subPane,
			JptUiMappingsMessages.DiscriminatorColumnComposite_discriminatorType,
			buildDiscriminatorTypeCombo(subPane),
			IJpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_TYPE
		);

		// Discrinator Value widgets
		discriminatorValueCombo = buildLabeledEditableCCombo(
			subPane,
			JptUiMappingsMessages.InheritanceComposite_discriminatorValue,
			buildDiscriminatorValueComboSelectionListener(),
			IJpaHelpContextIds.ENTITY_INHERITANCE_DISCRIMINATOR_VALUE
		);

		installDiscriminatorValueComboEnabler(discriminatorValueCombo);

		// Primary Key Join Columns widgets
		new PrimaryKeyJoinColumnsComposite(
			this,
			buildSubPane(container, 5)
		);
	}

	private void installDiscriminatorValueComboEnabler(CCombo discriminatorValueCombo) {
		new ControlEnabler(
			buildDiscriminatorValueBooleanHolder(),
			discriminatorValueCombo
		);
	}

	private void populateDiscriminatorValueCombo() {

		IEntity subject = subject();
		discriminatorValueCombo.removeAll();

		if (subject == null) {
			return;
		}

		// Add the default discriminator column value if one exists
		String defaultDiscriminatorValue = subject.getDefaultDiscriminatorValue();

		if (defaultDiscriminatorValue != null) {
			discriminatorValueCombo.add(NLS.bind(
				JptUiMappingsMessages.ColumnComposite_defaultWithOneParam,
				defaultDiscriminatorValue)
			);
		}
		else {
			discriminatorValueCombo.add(NLS.bind(
				JptUiMappingsMessages.DiscriminatorColumnComposite_defaultEmpty,
				defaultDiscriminatorValue)
			);
		}

		// Select the discriminator column value
		String specifiedDiscriminatorValue = subject.getSpecifiedDiscriminatorValue();

		if (specifiedDiscriminatorValue != null) {
			discriminatorValueCombo.setText(specifiedDiscriminatorValue);
		}
		else {
			discriminatorValueCombo.select(0);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == IEntity.DEFAULT_DISCRIMINATOR_VALUE_PROPERTY ||
		    propertyName == IEntity.SPECIFIED_DISCRIMINATOR_VALUE_PROPERTY)
		{
			populateDiscriminatorValueCombo();
		}
	}
}
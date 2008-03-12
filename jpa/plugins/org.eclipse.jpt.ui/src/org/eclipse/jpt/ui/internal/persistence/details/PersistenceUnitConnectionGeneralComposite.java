/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.persistence.details;

import java.util.Collection;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.ui.internal.persistence.JptUiPersistenceMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | Description                                                               |
 * |                                                                           |
 * |                    ------------------------------------------------------ |
 * | Transaction Type:  |                                                  |v| |
 * |                    ------------------------------------------------------ |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see PersistenceUnit
 * @see PersistenceUnitGeneralComposite - The parent container
 *
 * @version 2.0
 * @since 2.0
 */
public class PersistenceUnitConnectionGeneralComposite extends AbstractPane<PersistenceUnit>
{
	/**
	 * Creates a new <code>PersistenceUnitConnectionGeneralComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public PersistenceUnitConnectionGeneralComposite(AbstractPane<PersistenceUnit> subjectHolder,
	                                                 Composite container) {

		super(subjectHolder, container, false);
	}

	private EnumFormComboViewer<PersistenceUnit, PersistenceUnitTransactionType> buildTransactionTypeCombo(Composite container) {

		return new EnumFormComboViewer<PersistenceUnit, PersistenceUnitTransactionType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(PersistenceUnit.SPECIFIED_TRANSACTION_TYPE_PROPERTY);
				propertyNames.add(PersistenceUnit.DEFAULT_TRANSACTION_TYPE_PROPERTY);
			}

			@Override
			protected PersistenceUnitTransactionType[] choices() {
				return PersistenceUnitTransactionType.values();
			}

			@Override
			protected PersistenceUnitTransactionType defaultValue() {
				return subject().getDefaultTransactionType();
			}

			@Override
			protected String displayString(PersistenceUnitTransactionType value) {
				return buildDisplayString(
					JptUiPersistenceMessages.class,
					PersistenceUnitConnectionGeneralComposite.this,
					value
				);
			}

			@Override
			protected PersistenceUnitTransactionType getValue() {
				return subject().getSpecifiedTransactionType();
			}

			@Override
			protected void setValue(PersistenceUnitTransactionType value) {
				subject().setSpecifiedTransactionType(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Description
		buildMultiLineLabel(
			container,
			JptUiPersistenceMessages.PersistenceUnitConnectionGeneralComposite_description
		);

		// Transaction Type widgets
		buildLabeledComposite(
			container,
			JptUiPersistenceMessages.PersistenceUnitConnectionGeneralComposite_transactionType,
			buildTransactionTypeCombo(container).getControl()
		);
	}
}

/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.connection;

import java.util.Collection;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.eclipselink.core.internal.context.connection.Connection;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * TransactionTypeComposite
 */
public class TransactionTypeComposite extends AbstractFormPane<Connection>
{
	/**
	 * Creates a new <code>TransactionTypeComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public TransactionTypeComposite(
					AbstractFormPane<? extends Connection> parentComposite,
					Composite parent) {

		super( parentComposite, parent);
	}

	private EnumFormComboViewer<Connection, PersistenceUnitTransactionType> buildTransactionTypeCombo(Composite container) {
		return new EnumFormComboViewer<Connection, PersistenceUnitTransactionType>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Connection.TRANSACTION_TYPE_PROPERTY);
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
				return buildDisplayString(EclipseLinkUiMessages.class, TransactionTypeComposite.this, value);
			}

			@Override
			protected PersistenceUnitTransactionType getValue() {
				return subject().getTransactionType();
			}

			@Override
			protected void setValue(PersistenceUnitTransactionType value) {
				subject().setTransactionType(value);

				if (value == PersistenceUnitTransactionType.RESOURCE_LOCAL) {
					clearJTAProperties();
				}
				else {
					clearResourceLocalProperties();
				}
			}
		};
	}

	private void clearJTAProperties() {
		subject().setJtaDataSource(null);
	}

	private void clearResourceLocalProperties() {
		Connection connection = subject();
		connection.setNonJtaDataSource(null);
		connection.setDriver(null);
		connection.setUrl(null);
		connection.setUser(null);
		connection.setPassword(null);
		connection.setBindParameters(null);
		connection.setWriteConnectionsMax(null);
		connection.setWriteConnectionsMin(null);
		connection.setReadConnectionsMax(null);
		connection.setReadConnectionsMin(null);
		connection.setReadConnectionsShared(null);
	}

	@Override
	protected void initializeLayout( Composite container) {

		this.buildLabeledComposite(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_transactionTypeLabel,
			this.buildTransactionTypeCombo( container),
			null		// TODO IJpaHelpContextIds.
		);
	}
}

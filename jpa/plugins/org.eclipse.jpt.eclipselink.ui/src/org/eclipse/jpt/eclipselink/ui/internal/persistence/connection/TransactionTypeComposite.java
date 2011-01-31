/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.connection;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * TransactionTypeComposite
 */
public class TransactionTypeComposite<T extends Connection>
	extends Pane<T>
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
					Pane<T> parentComposite,
					Composite parent) {

		super( parentComposite, parent);
	}

	@Override
	protected void initializeLayout( Composite container) {

		this.addLabeledComposite(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_transactionTypeLabel,
			this.addTransactionTypeCombo( container),
			JpaHelpContextIds.PERSISTENCE_XML_CONNECTION
		);
	}

	private EnumFormComboViewer<PersistenceUnit, PersistenceUnitTransactionType> addTransactionTypeCombo(Composite container) {
		return new EnumFormComboViewer<PersistenceUnit, PersistenceUnitTransactionType>(this, this.buildPersistenceUnitHolder(), container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(PersistenceUnit.SPECIFIED_TRANSACTION_TYPE_PROPERTY);
				propertyNames.add(PersistenceUnit.DEFAULT_TRANSACTION_TYPE_PROPERTY);
			}

			@Override
			protected PersistenceUnitTransactionType[] getChoices() {
				return PersistenceUnitTransactionType.values();
			}

			@Override
			protected PersistenceUnitTransactionType getDefaultValue() {
				return this.getSubject().getDefaultTransactionType();
			}

			@Override
			protected String displayString(PersistenceUnitTransactionType value) {
				return this.buildDisplayString(EclipseLinkUiMessages.class, TransactionTypeComposite.this, value);
			}

			@Override
			protected PersistenceUnitTransactionType getValue() {
				return this.getSubject().getSpecifiedTransactionType();
			}

			@Override
			protected void setValue(PersistenceUnitTransactionType value) {
				this.getSubject().setSpecifiedTransactionType(value);

				if (value == PersistenceUnitTransactionType.RESOURCE_LOCAL) {
					clearJTAProperties();
				}
				else {
					clearResourceLocalProperties();
				}
			}
		};
	}

	private PropertyValueModel<PersistenceUnit> buildPersistenceUnitHolder() {
		return new PropertyAspectAdapter<Connection, PersistenceUnit>(getSubjectHolder()) {
			@Override
			protected PersistenceUnit buildValue_() {
				return this.subject.getPersistenceUnit();
			}
		};
		
	}

	private void clearJTAProperties() {
		getSubject().getPersistenceUnit().setJtaDataSource(null);
	}

	private void clearResourceLocalProperties() {
		Connection connection = this.getSubject();
		connection.getPersistenceUnit().setNonJtaDataSource(null);
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
		connection.setExclusiveConnectionMode(null);
		connection.setLazyConnection(null);
	}
}

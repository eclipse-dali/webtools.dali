/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.connection;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 *  JdbcPropertiesComposite
 */
public class JdbcPropertiesComposite<T extends Connection> 
	extends Pane<T>
{
	public JdbcPropertiesComposite(Pane<T> parentComposite, Composite parent) {

		super(parentComposite, parent);
	}

	private PropertyValueModel<Boolean> buildPaneEnablerHolder() {
		return new TransformationPropertyValueModel<PersistenceUnitTransactionType, Boolean>(buildTransactionTypeHolder()) {
			@Override
			protected Boolean transform(PersistenceUnitTransactionType value) {
				return value == PersistenceUnitTransactionType.RESOURCE_LOCAL;
			}
		};
	}

	private PropertyValueModel<PersistenceUnitTransactionType> buildTransactionTypeHolder() {
		return new PropertyAspectAdapter<PersistenceUnit, PersistenceUnitTransactionType>(
			buildPersistenceUnitHolder(), 
			PersistenceUnit.SPECIFIED_TRANSACTION_TYPE_PROPERTY, 
			PersistenceUnit.DEFAULT_TRANSACTION_TYPE_PROPERTY) {
			@Override
			protected PersistenceUnitTransactionType buildValue_() {
				return this.subject.getTransactionType();
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

	@Override
	protected void initializeLayout(Composite container) {

		container = addTitledGroup(
			addSubPane(container, 10),
			EclipseLinkUiMessages.JdbcPropertiesComposite_EclipseLinkConnectionPool_GroupBox
		);

		new JdbcConnectionPropertiesComposite<T>(this, container);

		container = addPane(container, new GridLayout(2, true));

		new JdbcReadConnectionPropertiesComposite<T>(this, container);
		new JdbcWriteConnectionPropertiesComposite<T>(this, container);

		this.installPaneEnabler();
	}

	private void installPaneEnabler() {
		new PaneEnabler(buildPaneEnablerHolder(), this);
	}
}
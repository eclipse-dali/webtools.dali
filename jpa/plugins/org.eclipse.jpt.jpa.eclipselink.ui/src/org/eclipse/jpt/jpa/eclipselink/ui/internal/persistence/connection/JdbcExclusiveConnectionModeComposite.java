/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.connection;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Connection;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.ExclusiveConnectionMode;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.swt.widgets.Composite;

/**
 * ExclusiveConnectionModeComposite
 */
public class JdbcExclusiveConnectionModeComposite<T extends Connection>
		extends Pane<T>
{
	/**
	 * Creates a new <code>ExclusiveConnectionModeComposite</code>.
	 * 
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public JdbcExclusiveConnectionModeComposite(
					Pane<T> parentComposite, 
					Composite parent) {

		super(parentComposite, parent);
	}
	
	@Override
	protected void initializeLayout( Composite container) {

		this.addLabeledComposite(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_exclusiveConnectionModeLabel,
			this.addExclusiveConnectionModeCombo(container),
			JpaHelpContextIds.PERSISTENCE_XML_CONNECTION
		);
	}

	private EnumFormComboViewer<Connection, ExclusiveConnectionMode> addExclusiveConnectionModeCombo(Composite container) {
		return new EnumFormComboViewer<Connection, ExclusiveConnectionMode>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Connection.EXCLUSIVE_CONNECTION_MODE_PROPERTY);
			}

			@Override
			protected ExclusiveConnectionMode[] getChoices() {
				return ExclusiveConnectionMode.values();
			}

			@Override
			protected ExclusiveConnectionMode getDefaultValue() {
				return this.getSubject().getDefaultExclusiveConnectionMode();
			}

			@Override
			protected String displayString(ExclusiveConnectionMode value) {
				switch (value) {
					case always :
						return EclipseLinkUiMessages.JdbcExclusiveConnectionModeComposite_always;
					case isolated :
						return EclipseLinkUiMessages.JdbcExclusiveConnectionModeComposite_isolated;
					case transactional :
						return EclipseLinkUiMessages.JdbcExclusiveConnectionModeComposite_transactional;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected ExclusiveConnectionMode getValue() {
				return this.getSubject().getExclusiveConnectionMode();
			}

			@Override
			protected void setValue(ExclusiveConnectionMode value) {
				this.getSubject().setExclusiveConnectionMode(value);
			}
		};
	}
}

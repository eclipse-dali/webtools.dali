/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.logging;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Logging;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.LoggingLevel;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.swt.widgets.Composite;

/**
 * LoggingLevelComposite
 */
public class LoggingLevelComposite extends Pane<Logging>
{
	/**
	 * Creates a new <code>LoggingLevelComposite</code>.
	 * 
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public LoggingLevelComposite(
				Pane<? extends Logging> parentComposite, 
				Composite parent) {
		
		super(parentComposite, parent);
	}

	private EnumFormComboViewer<Logging, LoggingLevel> addLoggingLevelCombo(Composite container) {
		return new EnumFormComboViewer<Logging, LoggingLevel>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Logging.LEVEL_PROPERTY);
			}

			@Override
			protected LoggingLevel[] getChoices() {
				return LoggingLevel.values();
			}
			
			@Override
			protected boolean sortChoices() {
				return false;
			}
			
			@Override
			protected LoggingLevel getDefaultValue() {
				return this.getSubject().getDefaultLevel();
			}

			@Override
			protected String displayString(LoggingLevel value) {
				switch (value) {
					case all :
						return EclipseLinkUiMessages.LoggingLevelComposite_all;
					case config :
						return EclipseLinkUiMessages.LoggingLevelComposite_config;
					case fine :
						return EclipseLinkUiMessages.LoggingLevelComposite_fine;
					case finer :
						return EclipseLinkUiMessages.LoggingLevelComposite_finer;
					case finest :
						return EclipseLinkUiMessages.LoggingLevelComposite_finest;
					case info :
						return EclipseLinkUiMessages.LoggingLevelComposite_info;
					case off :
						return EclipseLinkUiMessages.LoggingLevelComposite_off;
					case severe :
						return EclipseLinkUiMessages.LoggingLevelComposite_severe;
					case warning :
						return EclipseLinkUiMessages.LoggingLevelComposite_warning;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected LoggingLevel getValue() {
				return this.getSubject().getLevel();
			}

			@Override
			protected void setValue(LoggingLevel value) {
				this.getSubject().setLevel(value);
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.addLabeledComposite(
				container,
				EclipseLinkUiMessages.PersistenceXmlLoggingTab_loggingLevelLabel,
				this.addLoggingLevelCombo(container),
				EclipseLinkHelpContextIds.PERSISTENCE_LOGGING_LEVEL
		);
	}
}

/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.logging;

import java.util.Collection;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.logging.Logging;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.logging.LoggingLevel;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.swt.widgets.Composite;

/**
 * LoggingLevelComposite
 */
public class LoggingLevelComposite extends FormPane<Logging>
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
				FormPane<? extends Logging> parentComposite, 
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
				return this.buildDisplayString(EclipseLinkUiMessages.class, LoggingLevelComposite.this, value);
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

/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.logging;

import java.util.Collection;

import org.eclipse.jpt.eclipselink.core.internal.context.logging.Logging;
import org.eclipse.jpt.eclipselink.core.internal.context.logging.LoggingLevel;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * LoggingLevelComposite
 */
public class LoggingLevelComposite extends AbstractFormPane<Logging>
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
				AbstractFormPane<? extends Logging> parentComposite, 
				Composite parent) {
		
		super(parentComposite, parent);
	}

	private EnumFormComboViewer<Logging, LoggingLevel> buildLoggingLevelCombo(Composite container) {
		return new EnumFormComboViewer<Logging, LoggingLevel>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Logging.LEVEL_PROPERTY);
			}

			@Override
			protected LoggingLevel[] choices() {
				return LoggingLevel.values();
			}

			@Override
			protected LoggingLevel defaultValue() {
				return this.subject().getDefaultLevel();
			}

			@Override
			protected String displayString(LoggingLevel value) {
				return this.buildDisplayString(EclipseLinkUiMessages.class, LoggingLevelComposite.this, value);
			}

			@Override
			protected LoggingLevel getValue() {
				return this.subject().getLevel();
			}

			@Override
			protected void setValue(LoggingLevel value) {
				this.subject().setLevel(value);
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.buildLabeledComposite(
				container,
				EclipseLinkUiMessages.PersistenceXmlLoggingTab_loggingLevelLabel,
				this.buildLoggingLevelCombo(container),
				null // TODO IJpaHelpContextIds.
		);
	}
}

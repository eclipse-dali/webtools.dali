/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.options;

import java.util.Collection;

import org.eclipse.jpt.eclipselink.core.internal.context.options.Options;
import org.eclipse.jpt.eclipselink.core.internal.context.options.TargetDatabase;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * TargetDatabaseComposite
 */
public class TargetDatabaseComposite extends AbstractFormPane<Options>
{
	/**
	 * Creates a new <code>TargetDatabaseComposite</code>.
	 * 
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public TargetDatabaseComposite(
				AbstractFormPane<? extends Options> parentComposite, 
				Composite parent) {
		
		super(parentComposite, parent);
	}

	private EnumFormComboViewer<Options, TargetDatabase> buildTargetDatabaseCombo(Composite container) {
		return new EnumFormComboViewer<Options, TargetDatabase>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Options.TARGET_DATABASE_PROPERTY);
			}

			@Override
			protected TargetDatabase[] choices() {
				return TargetDatabase.values();
			}

			@Override
			protected TargetDatabase defaultValue() {
				return this.subject().getDefaultTargetDatabase();
			}

			@Override
			protected String displayString(TargetDatabase value) {
				return this.buildDisplayString(EclipseLinkUiMessages.class, TargetDatabaseComposite.this, value);
			}

			@Override
			protected TargetDatabase getValue() {
				return this.subject().getTargetDatabase();
			}

			@Override
			protected void setValue(TargetDatabase value) {
				this.subject().setTargetDatabase(value);
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.buildLabeledComposite(
				container,
				EclipseLinkUiMessages.PersistenceXmlOptionsTab_targetDatabaseLabel,
				this.buildTargetDatabaseCombo(container),
				null // TODO IJpaHelpContextIds.
		);
	}
}

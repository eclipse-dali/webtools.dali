/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.customization;

import java.util.Collection;

import org.eclipse.jpt.eclipselink.core.internal.context.caching.CacheType;
import org.eclipse.jpt.eclipselink.core.internal.context.customization.Customization;
import org.eclipse.jpt.eclipselink.core.internal.context.customization.Weaving;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * WeavingComposite
 */
public class WeavingComposite extends AbstractFormPane<Customization>
{
	/**
	 * Creates a new <code>WeavingComposite</code>.
	 * 
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public WeavingComposite(
					AbstractFormPane<? extends Customization> parentComposite, 
					Composite parent) {

		super( parentComposite, parent);
	}

	private EnumFormComboViewer<Customization, Weaving> buildWeavingCombo(Composite container) {
		return new EnumFormComboViewer<Customization, Weaving>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Customization.WEAVING_PROPERTY);
			}

			@Override
			protected Weaving[] choices() {
				return Weaving.values();
			}

			@Override
			protected Weaving defaultValue() {
				return subject().getDefaultWeaving();
			}

			@Override
			protected String displayString(Weaving value) {
				return buildDisplayString(EclipseLinkUiMessages.class, WeavingComposite.this, value);
			}

			@Override
			protected Weaving getValue() {
				return subject().getWeaving();
			}

			@Override
			protected void setValue(Weaving value) {
				subject().setWeaving(value);
			}
		};
	}
	
	@Override
	protected void initializeLayout( Composite container) {

		this.buildLabeledComposite(
			container,
			EclipseLinkUiMessages.PersistenceXmlCustomizationTab_weavingLabel,
			this.buildWeavingCombo( container),
			null		// TODO IJpaHelpContextIds.
		);
	}

}

/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.customization;

import java.util.Collection;

import org.eclipse.jpt.eclipselink.core.context.persistence.customization.Customization;
import org.eclipse.jpt.eclipselink.core.context.persistence.customization.Weaving;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.swt.widgets.Composite;

/**
 * WeavingComposite
 */
public class WeavingComposite extends FormPane<Customization>
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
					FormPane<? extends Customization> parentComposite, 
					Composite parent) {

		super( parentComposite, parent);
	}

	private EnumFormComboViewer<Customization, Weaving> addWeavingCombo(Composite container) {
		return new EnumFormComboViewer<Customization, Weaving>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Customization.WEAVING_PROPERTY);
			}

			@Override
			protected Weaving[] getChoices() {
				return Weaving.values();
			}

			@Override
			protected Weaving getDefaultValue() {
				return getSubject().getDefaultWeaving();
			}

			@Override
			protected String displayString(Weaving value) {
				return buildDisplayString(EclipseLinkUiMessages.class, WeavingComposite.this, value);
			}

			@Override
			protected Weaving getValue() {
				return getSubject().getWeaving();
			}

			@Override
			protected void setValue(Weaving value) {
				getSubject().setWeaving(value);
			}
		};
	}
	
	@Override
	protected void initializeLayout( Composite container) {

		this.addLabeledComposite(
			container,
			EclipseLinkUiMessages.PersistenceXmlCustomizationTab_weavingLabel,
			this.addWeavingCombo( container),
			EclipseLinkHelpContextIds.PERSISTENCE_CUSTOMIZATION
		);
	}

}

/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.general;

import org.eclipse.jpt.eclipselink.core.internal.context.general.GeneralProperties;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  GeneralComposite
 */
public class GeneralComposite extends FormPane<GeneralProperties>
{
	/**
	 * Creates a new <code>GeneralComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public GeneralComposite(
					FormPane<? extends GeneralProperties> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	// ********** initialization **********

	@Override
	protected void initializeLayout(Composite container) {

		// Name widgets
		addLabeledText(
			container,
			EclipseLinkUiMessages.PersistenceXmlGeneralTab_nameLabel,
			buildPersistenceUnitNameHolder()
		);

		// Persistence Provider widgets
		addLabeledText(
			container,
			EclipseLinkUiMessages.PersistenceXmlGeneralTab_persistenceProviderLabel,
			buildPersistenceProviderHolder()
		);

		// Description widgets
		addLabeledText(
			container,
			EclipseLinkUiMessages.PersistenceXmlGeneralTab_descriptionLabel,
			buildPersistenceUnitDescriptionHolder()
		);
	}
	
	// ********** internal methods **********
	
	private WritablePropertyValueModel<String> buildPersistenceUnitNameHolder() {
		return new PropertyAspectAdapter<GeneralProperties, String>(getSubjectHolder(), GeneralProperties.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getName();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setName(value);
			}
		};
	}

	private WritablePropertyValueModel<String> buildPersistenceProviderHolder() {
		return new PropertyAspectAdapter<GeneralProperties, String>(getSubjectHolder(), GeneralProperties.PROVIDER_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getProvider();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setProvider(value);
			}
		};
	}

	private WritablePropertyValueModel<String> buildPersistenceUnitDescriptionHolder() {
		return new PropertyAspectAdapter<GeneralProperties, String>(getSubjectHolder(), GeneralProperties.DESCRIPTION_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getDescription();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setDescription(value);
			}
		};
	}

}

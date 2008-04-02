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

import org.eclipse.jpt.eclipselink.core.internal.context.customization.Customization;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * ThrowExceptionsComposite
 */
public class ThrowExceptionsComposite extends AbstractFormPane<Customization>
{
	/**
	 * Creates a new <code>ThrowExceptionsComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public ThrowExceptionsComposite(
					AbstractFormPane<? extends Customization> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	private WritablePropertyValueModel<Boolean> buildThrowExceptionsHolder() {
		return new PropertyAspectAdapter<Customization, Boolean>(getSubjectHolder(), Customization.THROW_EXCEPTIONS_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.getThrowExceptions();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setThrowExceptions(value);
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Boolean.TRUE, newValue);
				}
			}
		};
	}

	private PropertyValueModel<String> buildThrowExceptionsStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildThrowExceptionsHolder()) {
			@Override
			protected String transform(Boolean value) {
				if ((subject() != null) && (value == null)) {
					Boolean defaultValue = subject().getDefaultThrowExceptions();
					if (defaultValue != null) {
						String defaultStringValue = defaultValue ? EclipseLinkUiMessages.Boolean_True : EclipseLinkUiMessages.Boolean_False;
						return NLS.bind(EclipseLinkUiMessages.PersistenceXmlCustomizationTab_throwExceptionsLabelDefault, defaultStringValue);
					}
				}
				return EclipseLinkUiMessages.PersistenceXmlCustomizationTab_throwExceptionsLabel;
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.buildTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlCustomizationTab_throwExceptionsLabel,
			this.buildThrowExceptionsHolder(),
			this.buildThrowExceptionsStringHolder(),
			null
//			EclipseLinkHelpContextIds.CUSTOMIZATION_THROW_EXCEPTIONS
		);
	}
	
}

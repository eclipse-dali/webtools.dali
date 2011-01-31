/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.customization;

import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.eclipselink.core.context.persistence.customization.Customization;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * ThrowExceptionsComposite
 */
public class ThrowExceptionsComposite extends Pane<Customization>
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
					Pane<? extends Customization> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlCustomizationTab_throwExceptionsLabel,
			this.buildThrowExceptionsHolder(),
			this.buildThrowExceptionsStringHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_CUSTOMIZATION
		);
	}
	
	private WritablePropertyValueModel<Boolean> buildThrowExceptionsHolder() {
		return new PropertyAspectAdapter<Customization, Boolean>(getSubjectHolder(), Customization.THROW_EXCEPTIONS_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getThrowExceptions();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setThrowExceptions(value);
			}
		};
	}

	private PropertyValueModel<String> buildThrowExceptionsStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultThrowExceptionsHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlCustomizationTab_throwExceptionsLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlCustomizationTab_throwExceptionsLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultThrowExceptionsHolder() {
		return new PropertyAspectAdapter<Customization, Boolean>(
			getSubjectHolder(),
			Customization.THROW_EXCEPTIONS_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getThrowExceptions() != null) {
					return null;
				}
				return this.subject.getDefaultThrowExceptions();
			}
		};
	}
}

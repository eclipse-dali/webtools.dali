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

import org.eclipse.jpt.eclipselink.core.internal.context.logging.Logging;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * ExceptionsComposite
 */
public class ExceptionsComposite extends AbstractFormPane<Logging>
{
	/**
	 * Creates a new <code>ExceptionsComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public ExceptionsComposite(
					AbstractFormPane<? extends Logging> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	private WritablePropertyValueModel<Boolean> buildExceptionsHolder() {
		return new PropertyAspectAdapter<Logging, Boolean>(getSubjectHolder(), Logging.ECLIPSELINK_EXCEPTIONS) {
			@Override
			protected Boolean buildValue_() {
				return subject.getExceptions();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setExceptions(value);
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

	private PropertyValueModel<String> buildExceptionsStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildExceptionsHolder()) {
			@Override
			protected String transform(Boolean value) {
				if ((subject() != null) && (value == null)) {
					Boolean defaultValue = subject().getDefaultExceptions();
					if (defaultValue != null) {
						String defaultStringValue = defaultValue ? EclipseLinkUiMessages.Boolean_True : EclipseLinkUiMessages.Boolean_False;
						return NLS.bind(EclipseLinkUiMessages.PersistenceXmlLoggingTab_exceptionsLabelDefault, defaultStringValue);
					}
				}
				return EclipseLinkUiMessages.PersistenceXmlLoggingTab_exceptionsLabel;
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.buildTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlLoggingTab_exceptionsLabel,
			this.buildExceptionsHolder(),
			this.buildExceptionsStringHolder(),
			null
//			EclipseLinkHelpContextIds.LOGGING_
		);
	}
}

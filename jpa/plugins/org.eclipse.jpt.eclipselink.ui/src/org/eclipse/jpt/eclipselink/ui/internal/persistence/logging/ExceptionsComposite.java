/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.logging;

import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.eclipselink.core.context.persistence.logging.Logging;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * ExceptionsComposite
 */
public class ExceptionsComposite extends Pane<Logging>
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
					Pane<? extends Logging> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlLoggingTab_exceptionsLabel,
			this.buildExceptionsHolder(),
			this.buildExceptionsStringHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_LOGGING_EXCEPTIONS
		);
	}
	
	private WritablePropertyValueModel<Boolean> buildExceptionsHolder() {
		return new PropertyAspectAdapter<Logging, Boolean>(getSubjectHolder(), Logging.EXCEPTIONS_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getExceptions();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setExceptions(value);
			}
		};
	}

	private PropertyValueModel<String> buildExceptionsStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultExceptionsHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlLoggingTab_exceptionsLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlLoggingTab_exceptionsLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultExceptionsHolder() {
		return new PropertyAspectAdapter<Logging, Boolean>(
			getSubjectHolder(),
			Logging.EXCEPTIONS_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getExceptions() != null) {
					return null;
				}
				return this.subject.getDefaultExceptions();
			}
		};
	}
}

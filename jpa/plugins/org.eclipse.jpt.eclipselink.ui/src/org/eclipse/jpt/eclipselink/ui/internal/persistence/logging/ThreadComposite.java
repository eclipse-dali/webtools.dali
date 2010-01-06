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

import org.eclipse.jpt.eclipselink.core.context.persistence.logging.Logging;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * ThreadComposite
 */
public class ThreadComposite extends Pane<Logging>
{
	/**
	 * Creates a new <code>ThreadComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public ThreadComposite(
					Pane<? extends Logging> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlLoggingTab_threadLabel,
			this.buildThreadHolder(),
			this.buildThreadStringHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_LOGGING_THREAD
		);
	}
	
	private WritablePropertyValueModel<Boolean> buildThreadHolder() {
		return new PropertyAspectAdapter<Logging, Boolean>(getSubjectHolder(), Logging.THREAD_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getThread();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setThread(value);
			}
		};
	}

	private PropertyValueModel<String> buildThreadStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultThreadHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? EclipseLinkUiMessages.Boolean_True : EclipseLinkUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlLoggingTab_threadLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlLoggingTab_threadLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultThreadHolder() {
		return new PropertyAspectAdapter<Logging, Boolean>(
			getSubjectHolder(),
			Logging.THREAD_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getThread() != null) {
					return null;
				}
				return this.subject.getDefaultThread();
			}
		};
	}
}

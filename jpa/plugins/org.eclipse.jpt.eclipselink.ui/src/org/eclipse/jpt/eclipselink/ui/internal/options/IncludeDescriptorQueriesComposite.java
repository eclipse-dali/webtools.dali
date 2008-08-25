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

import org.eclipse.jpt.eclipselink.core.internal.context.options.Options;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * IncludeDescriptorQueriesComposite
 */
public class IncludeDescriptorQueriesComposite extends FormPane<Options>
{
	/**
	 * Creates a new <code>IncludeDescriptorQueriesComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public IncludeDescriptorQueriesComposite(
					FormPane<? extends Options> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	private WritablePropertyValueModel<Boolean> buildIncludeDescriptorQueriesHolder() {
		return new PropertyAspectAdapter<Options, Boolean>(getSubjectHolder(), Options.SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.getIncludeDescriptorQueries();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setIncludeDescriptorQueries(value);
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

	private PropertyValueModel<String> buildIncludeDescriptorQueriesStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildIncludeDescriptorQueriesHolder()) {
			@Override
			protected String transform(Boolean value) {
				if ((getSubject() != null) && (value == null)) {
					Boolean defaultValue = getSubject().getDefaultIncludeDescriptorQueries();
					if (defaultValue != null) {
						String defaultStringValue = defaultValue ? EclipseLinkUiMessages.Boolean_True : EclipseLinkUiMessages.Boolean_False;
						return NLS.bind(EclipseLinkUiMessages.PersistenceXmlOptionsTab_includeDescriptorQueriesLabelDefault, defaultStringValue);
					}
				}
				return EclipseLinkUiMessages.PersistenceXmlOptionsTab_includeDescriptorQueriesLabel;
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlOptionsTab_includeDescriptorQueriesLabel,
			this.buildIncludeDescriptorQueriesHolder(),
			this.buildIncludeDescriptorQueriesStringHolder(),
			null
//			EclipseLinkHelpContextIds.
		);
	}
}

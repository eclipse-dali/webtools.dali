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
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 *  ExcludeEclipselinkOrmComposite
 */
public class ExcludeEclipselinkOrmComposite extends FormPane<GeneralProperties>
{
	/**
	 * Creates a new <code>ExcludeEclipselinkOrmComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public ExcludeEclipselinkOrmComposite(
					FormPane<? extends GeneralProperties> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	// ********** initialization **********

	@Override
	protected void initializeLayout(Composite container) {

		addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlGeneralTab_excludeEclipselinkOrm,
			this.buildExcludeEclipselinkOrmHolder(),
			this.buildExcludeEclipselinkOrmStringHolder(),
			null
		);
	}
	
	// ********** internal methods **********

	private WritablePropertyValueModel<Boolean> buildExcludeEclipselinkOrmHolder() {
		return new PropertyAspectAdapter<GeneralProperties, Boolean>(
			getSubjectHolder(),
			GeneralProperties.EXCLUDE_ECLIPSELINK_ORM_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return subject.getExcludeEclipselinkOrm();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setExcludeEclipselinkOrm(value);
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

	private PropertyValueModel<String> buildExcludeEclipselinkOrmStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildExcludeEclipselinkOrmHolder()) {

			@Override
			protected String transform(Boolean value) {

				if ((getSubject() != null) && (value == null)) {

					Boolean defaultValue = getSubject().getDefaultExcludeEclipselinkOrm();

					if (defaultValue != null) {

						String defaultStringValue = defaultValue ? EclipseLinkUiMessages.Boolean_True :
																			EclipseLinkUiMessages.Boolean_False;

						return NLS.bind(
							EclipseLinkUiMessages.PersistenceXmlGeneralTab_excludeEclipselinkOrmWithDefault,
							defaultStringValue
						);
					}
				}
				return EclipseLinkUiMessages.PersistenceXmlGeneralTab_excludeEclipselinkOrm;
			}
		};
	}
}

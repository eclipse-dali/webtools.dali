/*******************************************************************************
* Copyright (c) 2008, 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.customization;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Customization;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.swt.widgets.Composite;

/**
 *  ExceptionHandlerComposite
 */
public class ExceptionHandlerComposite extends Pane<Customization>
{
	/**
	 * Creates a new <code>ExceptionHandlerComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public ExceptionHandlerComposite(Pane<? extends Customization> parentPane,
                           Composite parent) {

		super(parentPane, parent);
	}

	private ClassChooserPane<Customization> initializeClassChooser(Composite container) {

		return new ClassChooserPane<Customization>(this, container) {

			@Override
			protected ModifiablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<Customization, String>(
							this.getSubjectHolder(), Customization.EXCEPTION_HANDLER_PROPERTY) {
					@Override
					protected String buildValue_() {
						return this.subject.getExceptionHandler();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}
						this.subject.setExceptionHandler(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return this.getSubject().getExceptionHandler();
			}

			@Override
			protected String getLabelText() {
				return EclipseLinkUiMessages.PersistenceXmlCustomizationTab_exceptionHandlerLabel;
			}
			
			@Override
			protected IJavaProject getJavaProject() {
				return getSubject().getJpaProject().getJavaProject();
			}
			
			@Override
			protected void setClassName(String className) {
				this.getSubject().setExceptionHandler(className);
			}
			
			@Override
			protected String getSuperInterfaceName() {
				return Customization.ECLIPSELINK_EXCEPTION_HANDLER_CLASS_NAME;
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.initializeClassChooser(container);
	}
}

/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.persistence.connection;

import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.connection.JpaConnection2_0;
import org.eclipse.jpt.ui.internal.jpa2.Jpt2_0UiMessages;
import org.eclipse.jpt.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  JdbcDriverComposite
 */
public class JdbcDriverComposite extends Pane<JpaConnection2_0>
{
	/**
	 * Creates a new <code>JdbcDriverComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public JdbcDriverComposite(Pane<? extends JpaConnection2_0> parentPane,
                           Composite parent) {

		super(parentPane, parent);
	}

	private ClassChooserPane<JpaConnection2_0> initializeClassChooser(Composite container) {

		return new ClassChooserPane<JpaConnection2_0>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<JpaConnection2_0, String>(
							this.getSubjectHolder(), JpaConnection2_0.DRIVER_PROPERTY) {
					@Override
					protected String buildValue_() {
						return this.subject.getDriver();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}
						this.subject.setDriver(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return this.getSubject().getDriver();
			}

			@Override
			protected String getLabelText() {
				return Jpt2_0UiMessages.JdbcConnectionPropertiesComposite_driverLabel;
			}
			
			@Override
			protected JpaProject getJpaProject() {
				return this.getSubject().getJpaProject();
			}
			@Override
			protected void setClassName(String className) {
				this.getSubject().setDriver(className);				
			}
			
			@Override
			protected boolean allowTypeCreation() {
				//Does not make sense to allow the user to create a new Driver class
				return false;
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.initializeClassChooser(container);
	}
}

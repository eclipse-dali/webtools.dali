/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.connection;

import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  JdbcDriverComposite
 */
public class JdbcDriverComposite extends Pane<Connection>
{
	/**
	 * Creates a new <code>EventListenerComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public JdbcDriverComposite(Pane<? extends Connection> parentPane,
                           Composite parent) {

		super(parentPane, parent);
	}

	private ClassChooserPane<Connection> initializeClassChooser(Composite container) {

		return new ClassChooserPane<Connection>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<Connection, String>(
							this.getSubjectHolder(), Connection.DRIVER_PROPERTY) {
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
				return EclipseLinkUiMessages.PersistenceXmlConnectionTab_driverLabel;
			}
			
			@Override
			protected JpaProject getJpaProject() {
				return getSubject().getJpaProject();
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

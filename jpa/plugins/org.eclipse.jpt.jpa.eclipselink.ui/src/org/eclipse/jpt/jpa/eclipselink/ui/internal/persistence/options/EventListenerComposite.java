/*******************************************************************************
* Copyright (c) 2008, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.options;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.options.Options;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.swt.widgets.Composite;

/**
 *  EventListenerComposite
 */
public class EventListenerComposite extends Pane<Options>
{
	/**
	 * Creates a new <code>EventListenerComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public EventListenerComposite(Pane<? extends Options> parentPane,
                           Composite parent) {

		super(parentPane, parent);
	}

	private ClassChooserPane<Options> initializeClassChooser(Composite container) {

		return new ClassChooserPane<Options>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<Options, String>(
							this.getSubjectHolder(), Options.SESSION_EVENT_LISTENER_PROPERTY) {
					@Override
					protected String buildValue_() {
						return this.subject.getEventListener();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}
						this.subject.setEventListener(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return this.getSubject().getEventListener();
			}

			@Override
			protected String getLabelText() {
				return EclipseLinkUiMessages.PersistenceXmlOptionsTab_eventListenerLabel;
			}
			
			@Override
			protected IJavaProject getJavaProject() {
				return getSubject().getJpaProject().getJavaProject();
			}

			@Override
			protected void setClassName(String className) {
				this.getSubject().setEventListener(className);
			}
			
			@Override
			protected String getSuperInterfaceName() {
				return Options.ECLIPSELINK_EVENT_LISTENER_CLASS_NAME;
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.initializeClassChooser(container);
	}
}

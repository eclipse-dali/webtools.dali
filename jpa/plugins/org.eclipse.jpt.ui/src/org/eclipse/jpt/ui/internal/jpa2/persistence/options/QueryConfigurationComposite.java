/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.persistence.options;

import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.core.jpa2.context.persistence.options.JpaOptions2_0;
import org.eclipse.jpt.ui.internal.jpa2.persistence.JptUiPersistence2_0Messages;
import org.eclipse.swt.widgets.Composite;

/**
 *  QueryConfigurationComposite
 */
public class QueryConfigurationComposite extends Pane<JpaOptions2_0>
{
	/**
	 * Creates a new <code>QueryConfigurationComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public QueryConfigurationComposite(
					Pane<? extends JpaOptions2_0> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		this.addQueryTimeoutCombo(parent);
	}
	
	private void addQueryTimeoutCombo(Composite parent) {
		new IntegerCombo<JpaOptions2_0>(this, parent) {
			
			@Override
			protected String getLabelText() {
				return JptUiPersistence2_0Messages.QueryConfigurationComposite_queryTimeoutLabel;
			}
		
			@Override
			protected String getHelpId() {
				return null;		// TODO
			}
			
			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<JpaOptions2_0, Integer>(this.getSubjectHolder()) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getDefaultQueryTimeout();
					}
				};
			}
			
			@Override
			protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<JpaOptions2_0, Integer>(this.getSubjectHolder(), JpaOptions2_0.QUERY_TIMEOUT_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getQueryTimeout();
					}

					@Override
					protected void setValue_(Integer value) {
						this.subject.setQueryTimeout(value);
					}
				};
			}
		};
	}
}
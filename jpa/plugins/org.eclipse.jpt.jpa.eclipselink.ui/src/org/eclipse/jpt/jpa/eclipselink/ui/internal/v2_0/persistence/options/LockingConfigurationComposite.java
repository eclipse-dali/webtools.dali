/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence.options;

import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Options2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.JptUiPersistence2_0Messages;
import org.eclipse.swt.widgets.Composite;

/**
 *  LockingConfigurationComposite
 */
public class LockingConfigurationComposite extends Pane<Options2_0>
{
	/**
	 * Creates a new <code>LockingConfigurationComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public LockingConfigurationComposite(
					Pane<Options2_0> parentComposite, 
					Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		addLockTimeoutCombo(container);
	}
	
	private void addLockTimeoutCombo(Composite container) {
		new IntegerCombo<Options2_0>(this, container) {
			
			@Override
			protected String getLabelText() {
				return JptUiPersistence2_0Messages.LockingConfigurationComposite_lockTimeoutLabel;
			}
		
			@Override
			protected String getHelpId() {
				return null;		// TODO
			}
			
			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<Options2_0, Integer>(this.getSubjectHolder()) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getDefaultLockTimeout();
					}
				};
			}
			
			@Override
			protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<Options2_0, Integer>(this.getSubjectHolder(), Options2_0.LOCK_TIMEOUT_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getLockTimeout();
					}

					@Override
					protected void setValue_(Integer value) {
						this.subject.setLockTimeout(value);
					}
				};
			}
		};
	}
}
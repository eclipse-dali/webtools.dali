/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details;

import java.util.Collection;

import org.eclipse.jpt.core.jpa2.context.LockModeType_2_0;
import org.eclipse.jpt.core.jpa2.context.NamedQuery2_0;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.swt.widgets.Composite;

/**
 *  LockModeComposite
 */
public class LockModeComposite extends Pane<NamedQuery2_0>
{
	/**
	 * Creates a new <code>LockModeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public LockModeComposite(Pane<? extends NamedQuery2_0> parentPane,
	                          Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.addLabeledComposite(
			container,
			JptUiDetailsMessages2_0.LockModeComposite_lockModeLabel,
			this.addLockModeTypeCombo(container),
			null		// TODO
		);
	}

	private EnumFormComboViewer<NamedQuery2_0, LockModeType_2_0> addLockModeTypeCombo(Composite container) {

		return new EnumFormComboViewer<NamedQuery2_0, LockModeType_2_0>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(NamedQuery2_0.DEFAULT_LOCK_MODE_PROPERTY);
				propertyNames.add(NamedQuery2_0.SPECIFIED_LOCK_MODE_PROPERTY);
			}

			@Override
			protected LockModeType_2_0[] getChoices() {
				return LockModeType_2_0.values();
			}

			@Override
			protected LockModeType_2_0 getDefaultValue() {
				return this.getSubject().getDefaultLockMode();
			}

			@Override
			protected String displayString(LockModeType_2_0 value) {
				return this.buildDisplayString(
					JptUiDetailsMessages2_0.class,
					LockModeComposite.this,
					value
				);
			}

			@Override
			protected LockModeType_2_0 getValue() {
				return this.getSubject().getSpecifiedLockMode();
			}

			@Override
			protected void setValue(LockModeType_2_0 value) {
				this.getSubject().setSpecifiedLockMode(value);
			}
		};
	}
}

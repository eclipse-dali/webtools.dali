/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.persistence.options;

import java.util.Collection;

import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.options.SharedCacheMode;
import org.eclipse.jpt.ui.internal.jpa2.Jpt2_0UiMessages;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  SharedCacheModeComposite
 */
public class SharedCacheModeComposite extends FormPane<PersistenceUnit2_0>
{
	/**
	 * Creates a new <code>SharedCacheModeComposite</code>.
	 * 
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public SharedCacheModeComposite(
					FormPane<?> parentPane,
			        PropertyValueModel<? extends PersistenceUnit2_0> subjectHolder,
			        Composite parent) {

	super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		
		this.addLabeledComposite(
			parent,
			Jpt2_0UiMessages.SharedCacheModeComposite_sharedCacheModeLabel,
			this.addSharedCacheModeCombo(parent),
			null			// TODO
		);
	}
	
	private EnumFormComboViewer<PersistenceUnit2_0, SharedCacheMode> addSharedCacheModeCombo(Composite parent) {
		
		return new EnumFormComboViewer<PersistenceUnit2_0, SharedCacheMode>(this, this.getSubjectHolder(), parent) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(PersistenceUnit2_0.SPECIFIED_SHARED_CACHE_MODE_PROPERTY);
			}
			
			@Override
			protected SharedCacheMode[] getChoices() {
				return SharedCacheMode.values();
			}
			
			@Override
			protected boolean sortChoices() {
				return false;
			}
			
			@Override
			protected SharedCacheMode getDefaultValue() {
				return this.getSubject().getDefaultSharedCacheMode();
			}

			@Override
			protected String displayString(SharedCacheMode value) {
				return this.buildDisplayString(Jpt2_0UiMessages.class, SharedCacheModeComposite.this, value);
			}

			@Override
			protected SharedCacheMode getValue() {
				return this.getSubject().getSpecifiedSharedCacheMode();
			}

			@Override
			protected void setValue(SharedCacheMode value) {
				this.getSubject().setSpecifiedSharedCacheMode(value);
			}
		};
	}
}

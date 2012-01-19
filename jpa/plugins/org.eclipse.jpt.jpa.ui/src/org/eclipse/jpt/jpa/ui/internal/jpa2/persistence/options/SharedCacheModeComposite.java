/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.options;

import java.util.Collection;

import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.SharedCacheMode;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.JptUiPersistence2_0Messages;
import org.eclipse.swt.widgets.Composite;

/**
 *  SharedCacheModeComposite
 */
public class SharedCacheModeComposite extends Pane<PersistenceUnit2_0>
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
					Pane<?> parentPane,
			        PropertyValueModel<? extends PersistenceUnit2_0> subjectHolder,
			        Composite parent) {

	super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		
		this.addLabeledComposite(
			parent,
			JptUiPersistence2_0Messages.SharedCacheModeComposite_sharedCacheModeLabel,
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
				switch (value) {
					case ALL :
						return JptUiPersistence2_0Messages.SharedCacheModeComposite_all;
					case DISABLE_SELECTIVE :
						return JptUiPersistence2_0Messages.SharedCacheModeComposite_disable_selective;
					case ENABLE_SELECTIVE :
						return JptUiPersistence2_0Messages.SharedCacheModeComposite_enable_selective;
					case NONE :
						return JptUiPersistence2_0Messages.SharedCacheModeComposite_none;
					case UNSPECIFIED :
						return JptUiPersistence2_0Messages.SharedCacheModeComposite_unspecified;
					default :
						throw new IllegalStateException();
				}
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

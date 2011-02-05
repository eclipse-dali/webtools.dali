/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.caching;

import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.swt.widgets.Composite;

/**
 * CacheSizeComposite
 */
public class DefaultCacheSizeComposite<T extends Caching> extends Pane<T>
{
	/**
	 * Creates a new <code>CacheTypeComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public DefaultCacheSizeComposite(Pane<T> parentComposite,
	                                 Composite parent) {

		super(parentComposite, parent);
	}



	@Override
	protected void initializeLayout(Composite container) {
		addDefaultCacheSizeCombo(container);
	}
	
	private void addDefaultCacheSizeCombo(Composite container) {
		new IntegerCombo<Caching>(this, container) {
			
			@Override
			protected String getLabelText() {
				return EclipseLinkUiMessages.DefaultCacheSizeComposite_defaultCacheSize;
			}
		
			@Override
			protected String getHelpId() {
				return EclipseLinkHelpContextIds.PERSISTENCE_CACHING_DEFAULT_SIZE;
			}

			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<Caching, Integer>(getSubjectHolder()) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getDefaultCacheSizeDefault();
					}
				};
			}
			
			@Override
			protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<Caching, Integer>(getSubjectHolder(), Caching.CACHE_SIZE_DEFAULT_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getCacheSizeDefault();
					}

					@Override
					protected void setValue_(Integer value) {
						this.subject.setCacheSizeDefault(value);
					}
				};			
			}
		};
	}

}
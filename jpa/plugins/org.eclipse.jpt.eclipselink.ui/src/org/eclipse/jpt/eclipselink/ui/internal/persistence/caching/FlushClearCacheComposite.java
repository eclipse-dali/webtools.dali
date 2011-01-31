/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.caching;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.eclipselink.core.context.persistence.caching.FlushClearCache;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.swt.widgets.Composite;

/**
 * FlushClearCacheComposite
 */
public class FlushClearCacheComposite extends Pane<Caching>
{
	/**
	 * Creates a new <code>FlushClearCacheComposite</code>.
	 * 
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public FlushClearCacheComposite(
				Pane<? extends Caching> parentComposite, 
				Composite parent) {
		
		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		this.addLabeledComposite(
				parent,
				EclipseLinkUiMessages.PersistenceXmlCachingTab_FlushClearCacheLabel,
				this.addFlushClearCacheCombo(parent),
				EclipseLinkHelpContextIds.PERSISTENCE_CACHING
		);
	}

	private EnumFormComboViewer<Caching, FlushClearCache> addFlushClearCacheCombo(Composite container) {
		return new EnumFormComboViewer<Caching, FlushClearCache>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Caching.FLUSH_CLEAR_CACHE_PROPERTY);
			}

			@Override
			protected FlushClearCache[] getChoices() {
				return FlushClearCache.values();
			}
			
			@Override
			protected boolean sortChoices() {
				return false;
			}
			
			@Override
			protected FlushClearCache getDefaultValue() {
				return this.getSubject().getDefaultFlushClearCache();
			}

			@Override
			protected String displayString(FlushClearCache value) {
				return this.buildDisplayString(EclipseLinkUiMessages.class, FlushClearCacheComposite.class, value);
			}

			@Override
			protected FlushClearCache getValue() {
				return this.getSubject().getFlushClearCache();
			}

			@Override
			protected void setValue(FlushClearCache value) {
				this.getSubject().setFlushClearCache(value);
			}
		};
	}
}

/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.caching;

import org.eclipse.jpt.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.swt.widgets.Composite;

/**
 *  CacheDefaultsComposite
 */
public class CacheDefaultsComposite<T extends Caching> extends Pane<T>
{
	public CacheDefaultsComposite(Pane<T> subjectHolder,
	                                       Composite container) {

		super(subjectHolder, container);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		
		this.initializeCacheDefaultsPane(parent);
	}
	
	protected void initializeCacheDefaultsPane(Composite parent) {

		Composite defaultPane = this.addTitledGroup(
			parent,
			EclipseLinkUiMessages.CacheDefaultsComposite_groupTitle
		);

		this.initializeCacheDefaultsComposites(defaultPane);
	}
	
	protected void initializeCacheDefaultsComposites(Composite parent) {

		// Default Cache Type
		new DefaultCacheTypeComposite(this, parent);

		// Default Cache Size
		new DefaultCacheSizeComposite<T>(this, parent);

		// Default Shared Cache
		new DefaultSharedCacheComposite(this, parent);
	}
}

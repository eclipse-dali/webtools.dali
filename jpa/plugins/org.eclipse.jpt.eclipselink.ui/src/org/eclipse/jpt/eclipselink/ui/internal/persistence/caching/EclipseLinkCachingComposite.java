/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.caching;

import org.eclipse.jpt.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.swt.widgets.Composite;

/**
 *  EclipseLinkCachingComposite
 */
public class EclipseLinkCachingComposite<T extends Caching> extends Pane<T>
{
	public EclipseLinkCachingComposite(Pane<T> subjectHolder,
	                                       Composite container) {

		super(subjectHolder, container);
	}

	@Override
	protected void initializeLayout(Composite parent) {

		Composite container = this.addSection(
			parent,
			EclipseLinkUiMessages.PersistenceXmlCachingTab_sectionTitle,
			EclipseLinkUiMessages.PersistenceXmlCachingTab_sectionDescription
		);

		// Defaults
		new CacheDefaultsComposite<T>(this, container);
		// EntitiesList
		new EntityListComposite<T>(this, container);
		// Flush Clear Cache
		new FlushClearCacheComposite(this, container);
	}
	
}
/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.caching;

import org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching.Caching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.swt.widgets.Composite;

/**
 *  EclipseLinkCachingComposite
 */
public class EclipseLinkCachingComposite extends FormPane<Caching>
{
	public EclipseLinkCachingComposite(FormPane<Caching> subjectHolder,
	                                       Composite container) {

		super(subjectHolder, container);
	}

	@Override
	protected void initializeLayout(Composite container) {

		container = this.addSection(
			container,
			EclipseLinkUiMessages.PersistenceXmlCachingTab_sectionTitle,
			EclipseLinkUiMessages.PersistenceXmlCachingTab_sectionDescription
		);

		// Default pane
		int groupBoxMargin = this.getGroupBoxMargin();

		Composite defaultPane = this.addSubPane(
			container,
			0, groupBoxMargin, 0, groupBoxMargin
		);

		// Default Cache Type
		new DefaultCacheTypeComposite(this, defaultPane);

		// Default Cache Size
		new DefaultCacheSizeComposite(this, defaultPane);

		// Default Shared Cache
		new DefaultSharedCacheComposite(this, defaultPane);

		// EntitiesList
		new EntityListComposite(this, container);
		
		// Flush Clear Cache
		new FlushClearCacheComposite(this, container);
	}
}
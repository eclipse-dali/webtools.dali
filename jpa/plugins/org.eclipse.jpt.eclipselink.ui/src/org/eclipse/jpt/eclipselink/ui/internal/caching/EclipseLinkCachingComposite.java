/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.caching;

import org.eclipse.jpt.eclipselink.core.internal.context.caching.Caching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 *  EclipseLinkCachingComposite
 */
public class EclipseLinkCachingComposite extends AbstractFormPane<Caching>
{
	public EclipseLinkCachingComposite(AbstractFormPane<Caching> subjectHolder,
	                                       Composite container) {

		super(subjectHolder, container);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		Section section = getWidgetFactory().createSection(parent, SWT.FLAT | ExpandableComposite.TITLE_BAR| Section.DESCRIPTION);
		section.setText(EclipseLinkUiMessages.PersistenceXmlCachingTab_sectionTitle);
		section.setDescription(EclipseLinkUiMessages.PersistenceXmlCachingTab_sectionDescription);
		section.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite composite = buildSubPane(section);
		section.setClient(composite);

		// Default pane
		int groupBoxMargin = groupBoxMargin();

		Composite defaultPane = buildSubPane(
			composite,
			0, groupBoxMargin, 0, groupBoxMargin
		);

		// Default Cache Type
		new DefaultCacheTypeComposite(this, defaultPane);

		// Default Cache Size
		new DefaultCacheSizeComposite(this, defaultPane);

		// Default Shared Cache
		new DefaultSharedCacheComposite(this, defaultPane);

		// EntitiesList
		new EntityListComposite(this, composite);
	}
}
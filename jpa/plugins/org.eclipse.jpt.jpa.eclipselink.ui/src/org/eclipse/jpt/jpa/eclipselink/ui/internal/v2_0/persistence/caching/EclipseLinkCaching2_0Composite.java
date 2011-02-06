/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence.caching;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.caching.EclipseLinkCachingComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.options.SharedCacheModeComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 *  EclipseLinkCaching2_0Composite
 */
public class EclipseLinkCaching2_0Composite extends EclipseLinkCachingComposite<Caching>
{
	public EclipseLinkCaching2_0Composite(
			Pane<Caching> subjectHolder, 
			Composite container) {
		super(subjectHolder, container);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		Section section = getWidgetFactory().createSection(parent, SWT.FLAT | ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		section.setText(EclipseLinkUiMessages.PersistenceXmlCachingTab_sectionTitle);
		section.setDescription(EclipseLinkUiMessages.PersistenceXmlCachingTab_sectionDescription);
		Composite composite = getWidgetFactory().createComposite(section);
		composite.setLayout(new GridLayout(1, false));
		section.setClient(composite);
		this.updateGridData(composite);
		this.updateGridData(composite.getParent());

		// SharedCacheMode
		new SharedCacheModeComposite(this, this.buildPersistenceUnit2_0Holder(), composite);
		// Defaults
		new CacheDefaults2_0Composite(this, composite);
		// Flush Clear Cache
		new FlushClearCache2_0Composite(this, composite);
	}

	private PropertyValueModel<PersistenceUnit2_0> buildPersistenceUnit2_0Holder() {
		return new PropertyAspectAdapter<Caching, PersistenceUnit2_0>(this.getSubjectHolder()) {
			@Override
			protected PersistenceUnit2_0 buildValue_() {
				return (PersistenceUnit2_0) this.subject.getPersistenceUnit();
			}
		};
	}

	private void updateGridData(Composite container) {
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		container.setLayoutData(gridData);
	}	
}
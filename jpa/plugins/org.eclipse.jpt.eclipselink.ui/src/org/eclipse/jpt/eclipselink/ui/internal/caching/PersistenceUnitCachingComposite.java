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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 *  PersistenceUnitCachingComposite
 */
public class PersistenceUnitCachingComposite extends AbstractFormPane<Caching>
{
	private DefaultCacheTypeComposite defaultCacheTypeComposite; 
	private DefaultSharedCacheComposite defaultShareCacheComposite; 
//	private SelectableSpinner defaultCacheSizeSpinner;
	
	private CacheTypeComposite cacheTypeComposite; 
	private ShareCacheComposite shareCacheComposite; 
//	private SelectableSpinner cacheSizeSpinner;

	private EntityListComposite entitiesComposite;
	
	public PersistenceUnitCachingComposite(
								AbstractFormPane<Caching> subjectHolder,
	                            Composite container) {

		super(subjectHolder, container, false);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		Section section = getWidgetFactory().createSection(parent, SWT.FLAT | ExpandableComposite.TITLE_BAR| Section.DESCRIPTION);
		section.setText(EclipseLinkUiMessages.PersistenceXmlCachingTab_sectionTitle);
		section.setDescription(EclipseLinkUiMessages.PersistenceXmlCachingTab_sectionDescription);
		
		Composite composite = getWidgetFactory().createComposite(section);
		composite.setLayout(new GridLayout(1, false));
		section.setClient(composite);

		this.updateGridData(composite);
		this.updateGridData(composite.getParent());
				
		// Default Cache Type:
		this.defaultCacheTypeComposite = new DefaultCacheTypeComposite(this, composite);

		// Default Shared Cache:
		this.defaultShareCacheComposite = new DefaultSharedCacheComposite(this, composite);
		
		// Default Cache Size:
		Label defaultCacheSizeLabel = getWidgetFactory().createLabel(composite, "Default Cache Size:");

//		this.defaultCacheSizeSpinner = new SelectableSpinner(defaultCachingComposite, this.commandStack, this.getWidgetFactory());
		
		// listComposite
		Composite listComposite =  getWidgetFactory().createComposite(composite);
		GridData gridData =  new GridData();
		gridData =  new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		listComposite.setLayoutData(gridData);
		listComposite.setLayout(new GridLayout(2, false));

		// EntitiesList
		this.entitiesComposite = new EntityListComposite(this, this.getSubjectHolder(), listComposite);
		
		Composite cachingComposite = getWidgetFactory().createComposite(composite);
		gridData =  new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		cachingComposite.setLayoutData(gridData);
		cachingComposite.setLayout(new GridLayout(1, false));

		// Cache Type:
		this.cacheTypeComposite = new CacheTypeComposite(this, cachingComposite, this.entitiesComposite);

		// Share Cache:
		this.shareCacheComposite = new ShareCacheComposite(this, cachingComposite, this.entitiesComposite);
		
		// Cache Size:
		Label cacheSizeLabel = getWidgetFactory().createLabel(cachingComposite, "Cache Size:");
		gridData =  new GridData();
		cacheSizeLabel.setLayoutData(gridData);

//		this.cacheSizeSpinner = new SelectableSpinner(cachingComposite, this.commandStack, this.getWidgetFactory());
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

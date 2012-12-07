/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.CacheableHolder2_0;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.EntityNameCombo;
import org.eclipse.jpt.jpa.ui.internal.details.IdClassChooser;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.TableComposite;
import org.eclipse.jpt.jpa.ui.internal.details.orm.AbstractOrmEntityComposite;
import org.eclipse.jpt.jpa.ui.internal.details.orm.JptUiDetailsOrmMessages;
import org.eclipse.jpt.jpa.ui.internal.details.orm.MetadataCompleteTriStateCheckBox;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmJavaClassChooser;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.Cacheable2_0TriStateCheckBox;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.Entity2_0OverridesComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.Generation2_0Composite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.Queries2_0Composite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Hyperlink;

public class OrmEntity2_0Composite
	extends AbstractOrmEntityComposite
{
	public OrmEntity2_0Composite(
			PropertyValueModel<? extends OrmEntity> entityModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(entityModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@Override
	protected Control initializeEntitySection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		// Java class widgets
		Hyperlink javaClassHyperlink = this.addHyperlink(container, JptUiDetailsOrmMessages.OrmJavaClassChooser_javaClass);
		new OrmJavaClassChooser(this, getSubjectHolder(), container, javaClassHyperlink);

		// Table widgets
		TableComposite tableComposite = new TableComposite(this, container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		tableComposite.getControl().setLayoutData(gridData);

		// Entity name widgets
		this.addLabel(container, JptUiDetailsMessages.EntityNameComposite_name);
		new EntityNameCombo(this, container);

		// Access type widgets
		this.addLabel(container, JptUiMessages.AccessTypeComposite_access);
		new AccessTypeComboViewer(this, this.buildAccessHolder(), container);

		// Id class widgets
		Hyperlink hyperlink = this.addHyperlink(container,JptUiDetailsMessages.IdClassComposite_label);
		new IdClassChooser(this, this.buildIdClassReferenceHolder(), container, hyperlink);

		// Cacheable widgets
		Cacheable2_0TriStateCheckBox cacheableCheckBox = new Cacheable2_0TriStateCheckBox(this, buildCacheableHolder(), container);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		cacheableCheckBox.getControl().setLayoutData(gridData);

		// Metadata complete widgets
		MetadataCompleteTriStateCheckBox metadataCompleteCheckBox = new MetadataCompleteTriStateCheckBox(this, getSubjectHolder(), container);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		metadataCompleteCheckBox.getControl().setLayoutData(gridData);

		return container;
	}
	
	protected PropertyValueModel<Cacheable2_0> buildCacheableHolder() {
		return new PropertyAspectAdapter<OrmEntity, Cacheable2_0>(getSubjectHolder()) {
			@Override
			protected Cacheable2_0 buildValue_() {
				return ((CacheableHolder2_0) this.subject).getCacheable();
			}
		};
	}

	@Override
	protected Control initializeAttributeOverridesSection(Composite container) {
		return new Entity2_0OverridesComposite(this, container).getControl();
	}

	@Override
	protected Control initializeGeneratorsSection(Composite container) {
		return new Generation2_0Composite(this, this.buildGeneratorContainerHolder(), container).getControl();
	}

	@Override
	protected Control initializeQueriesSection(Composite container) {
		return new Queries2_0Composite(this, this.buildQueryContainerHolder(), container).getControl();
	}
}

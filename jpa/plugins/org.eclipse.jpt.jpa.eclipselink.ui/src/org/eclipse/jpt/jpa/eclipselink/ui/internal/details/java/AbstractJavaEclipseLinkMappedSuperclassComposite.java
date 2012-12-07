/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.GeneratorContainer;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkConvertersComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkNonEmbeddableTypeMappingAdvancedComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractJavaMappedSuperclassComposite;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.Generation2_0Composite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public abstract class AbstractJavaEclipseLinkMappedSuperclassComposite
	extends AbstractJavaMappedSuperclassComposite
{
	protected AbstractJavaEclipseLinkMappedSuperclassComposite(
			PropertyValueModel<? extends JavaMappedSuperclass> mappedSuperclassModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		this.initializeMappedSuperclassCollapsibleSection(container);		
		this.initializeCachingCollapsibleSection(container);
		this.initializeQueriesCollapsibleSection(container);
		this.initializeConvertersCollapsibleSection(container);
		this.initializeAdvancedCollapsibleSection(container);
	}

	protected void initializeCachingCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_caching);

		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(initializeCachingSection(section));
				}
			}
		});
	}
	
	protected Control initializeCachingSection(Composite container) {
		return new JavaEclipseLinkCachingComposite(this, buildCachingHolder(), container).getControl();
	}
	
	protected PropertyAspectAdapter<JavaMappedSuperclass, JavaEclipseLinkCaching> buildCachingHolder() {
		return new PropertyAspectAdapter<JavaMappedSuperclass, JavaEclipseLinkCaching>(getSubjectHolder()) {
			@Override
			protected JavaEclipseLinkCaching buildValue_() {
				return ((JavaEclipseLinkMappedSuperclass) this.subject).getCaching();
			}
		};
	}

	protected void initializeConvertersCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_converters);

		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(initializeConvertersSection(section));
				}
			}
		});
	}

	protected Control initializeConvertersSection(Composite container) {
		return new EclipseLinkConvertersComposite(this, this.buildConverterHolderValueModel(), container).getControl();
	}

	private PropertyValueModel<JavaEclipseLinkConverterContainer> buildConverterHolderValueModel() {
		return new PropertyAspectAdapter<JavaMappedSuperclass, JavaEclipseLinkConverterContainer>(getSubjectHolder()) {
			@Override
			protected JavaEclipseLinkConverterContainer buildValue_() {
				return ((JavaEclipseLinkMappedSuperclass) this.subject).getConverterContainer();
			}	
		};
	}

	protected void initializeAdvancedCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_advanced);

		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(initializeAdvancedSection(section));
				}
			}
		});
	}

	protected Control initializeAdvancedSection(Composite container) {
		return new EclipseLinkNonEmbeddableTypeMappingAdvancedComposite(this, container).getControl();
	}

	protected void initializeGeneratorsCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptUiDetailsMessages.IdMappingComposite_primaryKeyGenerationSection);

		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(initializeGeneratorsSection(section));
				}
			}
		});
	}

	protected Control initializeGeneratorsSection(Composite container) {
		return new Generation2_0Composite(this, this.buildGeneratorContainerHolder(), container).getControl();
	}

	protected PropertyValueModel<GeneratorContainer> buildGeneratorContainerHolder() {
		return new PropertyAspectAdapter<MappedSuperclass, GeneratorContainer>(getSubjectHolder()) {
			@Override
			protected GeneratorContainer buildValue_() {
				return ((EclipseLinkMappedSuperclass) this.subject).getGeneratorContainer();
			}
		};
	}
}

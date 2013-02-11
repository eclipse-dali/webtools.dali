/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.GeneratorContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkConvertersComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkNonEmbeddableTypeMappingAdvancedComposite;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappedSuperclassComposite;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.IdClassChooser;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.orm.JptUiDetailsOrmMessages;
import org.eclipse.jpt.jpa.ui.internal.details.orm.MetadataCompleteTriStateCheckBox;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmJavaClassChooser;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.Generation2_0Composite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;
public abstract class AbstractOrmEclipseLinkMappedSuperclassComposite<T extends OrmEclipseLinkMappedSuperclass>
	extends AbstractMappedSuperclassComposite<T> 
{
	protected AbstractOrmEclipseLinkMappedSuperclassComposite(
			PropertyValueModel<? extends T> mappedSuperclassModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		this.initializeMappedSuperclassCollapsibleSection(container);
		this.initializeCachingCollapsibleSection(container);
		this.initializeConvertersCollapsibleSection(container);
		this.initializeAdvancedCollapsibleSection(container);
	}
	
	@Override
	protected Control initializeMappedSuperclassSection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		// Java class widgets
		Hyperlink javaClassHyperlink = addHyperlink(container, JptUiDetailsOrmMessages.OrmJavaClassChooser_javaClass);
		new OrmJavaClassChooser(this, getSubjectHolder(), container, javaClassHyperlink);

		// Access type widgets
		this.addLabel(container, JptJpaUiMessages.AccessTypeComposite_access);
		new AccessTypeComboViewer(this, this.buildAccessReferenceModel(), container);

		// Id class widgets
		Hyperlink hyperlink = this.addHyperlink(container,JptUiDetailsMessages.IdClassComposite_label);
		new IdClassChooser(this, this.buildIdClassReferenceModel(), container, hyperlink);

		// Metadata complete widgets
		MetadataCompleteTriStateCheckBox metadataCompleteComposite = new MetadataCompleteTriStateCheckBox(this, getSubjectHolder(), container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		metadataCompleteComposite.getControl().setLayoutData(gridData);

		return container;
	}

	protected void initializeCachingCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_TYPE_MAPPING_COMPOSITE_CACHING);

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
		return new OrmEclipseLinkCachingComposite(this, buildCachingModel(), container).getControl();
	}
	
	protected PropertyAspectAdapter<T, EclipseLinkCaching> buildCachingModel() {
		return new PropertyAspectAdapter<T, EclipseLinkCaching>(getSubjectHolder()) {
			@Override
			protected EclipseLinkCaching buildValue_() {
				return this.subject.getCaching();
			}
		};
	}

	protected void initializeConvertersCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_TYPE_MAPPING_COMPOSITE_CONVERTERS);

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
		return new EclipseLinkConvertersComposite(this, this.buildConverterContainerModel(), container).getControl();
	}

	private PropertyValueModel<OrmEclipseLinkConverterContainer> buildConverterContainerModel() {
		return new PropertyAspectAdapter<T, OrmEclipseLinkConverterContainer>(getSubjectHolder()) {
			@Override
			protected OrmEclipseLinkConverterContainer buildValue_() {
				return this.subject.getConverterContainer();
			}
		};
	}

	protected void initializeAdvancedCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_TYPE_MAPPING_COMPOSITE_ADVANCED);

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
		return new Generation2_0Composite(this, this.buildGeneratorContainerModel(), container).getControl();
	}

	protected PropertyValueModel<GeneratorContainer> buildGeneratorContainerModel() {
		return new PropertyAspectAdapter<T, GeneratorContainer>(getSubjectHolder()) {
			@Override
			protected GeneratorContainer buildValue_() {
				return this.subject.getGeneratorContainer();
			}
		};
	}
}

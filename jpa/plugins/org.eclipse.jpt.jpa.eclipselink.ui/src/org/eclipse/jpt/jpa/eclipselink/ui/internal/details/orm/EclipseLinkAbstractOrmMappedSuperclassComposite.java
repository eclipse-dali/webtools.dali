/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.GeneratorContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkConvertersComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkNonEmbeddableTypeMappingAdvancedComposite;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.details.orm.JptJpaUiDetailsOrmMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappedSuperclassComposite;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.IdClassChooser;
import org.eclipse.jpt.jpa.ui.internal.details.orm.MetadataCompleteTriStateCheckBox;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmJavaClassChooser;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.GenerationComposite2_0;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;
public abstract class EclipseLinkAbstractOrmMappedSuperclassComposite<T extends EclipseLinkOrmMappedSuperclass>
	extends AbstractMappedSuperclassComposite<T> 
{
	protected EclipseLinkAbstractOrmMappedSuperclassComposite(
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
		Hyperlink javaClassHyperlink = addHyperlink(container, JptJpaUiDetailsOrmMessages.ORM_JAVA_CLASS_CHOOSER_JAVA_CLASS);
		new OrmJavaClassChooser(this, this.buildPersistentTypeReferenceModel(), container, javaClassHyperlink);

		// Access type widgets
		this.addLabel(container, JptJpaUiMessages.ACCESS_TYPE_COMPOSITE_ACCESS);
		new AccessTypeComboViewer(this, this.buildAccessReferenceModel(), container);

		// Id class widgets
		Hyperlink hyperlink = this.addHyperlink(container,JptJpaUiDetailsMessages.ID_CLASS_COMPOSITE_LABEL);
		new IdClassChooser(this, this.buildIdClassReferenceModel(), container, hyperlink);

		// Metadata complete widgets
		MetadataCompleteTriStateCheckBox metadataCompleteComposite = new MetadataCompleteTriStateCheckBox(this, getSubjectHolder(), container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		metadataCompleteComposite.getControl().setLayoutData(gridData);

		return container;
	}

	protected PropertyValueModel<OrmPersistentType> buildPersistentTypeReferenceModel() {
		return new PropertyAspectAdapterXXXX<T, OrmPersistentType>(getSubjectHolder()) {
			@Override
			protected OrmPersistentType buildValue_() {
				return this.subject.getPersistentType();
			}
		};
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
		return new EclipseLinkOrmCachingComposite(this, buildCachingModel(), container).getControl();
	}
	
	protected PropertyAspectAdapterXXXX<T, EclipseLinkCaching> buildCachingModel() {
		return new PropertyAspectAdapterXXXX<T, EclipseLinkCaching>(getSubjectHolder()) {
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

	private PropertyValueModel<EclipseLinkOrmConverterContainer> buildConverterContainerModel() {
		return new PropertyAspectAdapterXXXX<T, EclipseLinkOrmConverterContainer>(getSubjectHolder()) {
			@Override
			protected EclipseLinkOrmConverterContainer buildValue_() {
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
		section.setText(JptJpaUiDetailsMessages.ID_MAPPING_COMPOSITE_PRIMARY_KEY_GENERATION_SECTION);

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
		return new GenerationComposite2_0(this, this.buildGeneratorContainerModel(), container).getControl();
	}

	protected PropertyValueModel<GeneratorContainer> buildGeneratorContainerModel() {
		return new PropertyAspectAdapterXXXX<T, GeneratorContainer>(getSubjectHolder()) {
			@Override
			protected GeneratorContainer buildValue_() {
				return this.subject.getGeneratorContainer();
			}
		};
	}
}

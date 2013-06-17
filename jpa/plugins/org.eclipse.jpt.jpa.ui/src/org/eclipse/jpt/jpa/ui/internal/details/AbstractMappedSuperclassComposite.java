/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedAccessReference;
import org.eclipse.jpt.jpa.core.context.IdClassReference;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;

public abstract class AbstractMappedSuperclassComposite<T extends MappedSuperclass>
	extends Pane<T>
    implements JpaComposite
{
	protected AbstractMappedSuperclassComposite(
			PropertyValueModel<? extends T> mappedSuperclassModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		this.initializeMappedSuperclassCollapsibleSection(container);
	}
	
	protected void initializeMappedSuperclassCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container,
				ExpandableComposite.TITLE_BAR |
				ExpandableComposite.TWISTIE |
				ExpandableComposite.EXPANDED);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.MAPPED_SUPERCLASS_SECTION_TITLE);
		section.setClient(this.initializeMappedSuperclassSection(section));
	}
	
	protected Control initializeMappedSuperclassSection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		Hyperlink hyperlink = this.addHyperlink(container,JptJpaUiDetailsMessages.ID_CLASS_COMPOSITE_LABEL);
		new IdClassChooser(this, this.buildIdClassReferenceModel(), container, hyperlink);

		return container;
	}

	protected PropertyValueModel<IdClassReference> buildIdClassReferenceModel() {
		return new PropertyAspectAdapter<T, IdClassReference>(getSubjectHolder()) {
			@Override
			protected IdClassReference buildValue_() {
				return this.subject.getIdClassReference();
			}
		};
	}

	protected PropertyValueModel<SpecifiedAccessReference> buildAccessReferenceModel() {
		return new PropertyAspectAdapter<T, SpecifiedAccessReference>(this.getSubjectHolder()) {
			@Override
			protected SpecifiedAccessReference buildValue_() {
				return this.subject.getPersistentType();
			}
		};
	}
}

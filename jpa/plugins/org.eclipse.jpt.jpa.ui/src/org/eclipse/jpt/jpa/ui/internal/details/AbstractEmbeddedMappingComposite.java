/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public abstract class AbstractEmbeddedMappingComposite<T extends EmbeddedMapping> 
	extends Pane<T>
	implements JpaComposite
{
	protected AbstractEmbeddedMappingComposite(
			PropertyValueModel<? extends T> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeEmbeddedCollapsibleSection(container);
	}
	
	protected void initializeEmbeddedCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container,
				ExpandableComposite.TITLE_BAR |
				ExpandableComposite.TWISTIE |
				ExpandableComposite.EXPANDED);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.EMBEDDED_SECTION_TITLE);
		section.setClient(this.initializeEmbeddedSection(section));
	}
	
	protected Control initializeEmbeddedSection(Composite container) {
		//a Section having a Group as its client causes exceptions. EmbeddedMappingOverridesComposite
		//uses a Group as its 'control' so I am adding an extra composite here.
		container = this.addSubPane(container);
		new EmbeddedMappingOverridesComposite(this, container);
		return container;
	}

	protected PropertyValueModel<SpecifiedAccessReference> buildAccessReferenceModel() {
		return new PropertyAspectAdapter<T, SpecifiedAccessReference>(getSubjectHolder()) {
			@Override
			protected SpecifiedAccessReference buildValue_() {
				return this.subject.getPersistentAttribute();
			}
		};
	}
}
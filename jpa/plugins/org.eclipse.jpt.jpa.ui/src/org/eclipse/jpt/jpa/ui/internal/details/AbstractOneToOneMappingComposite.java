/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedAccessReference;
import org.eclipse.jpt.jpa.core.context.Cascade;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneRelationship;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public abstract class AbstractOneToOneMappingComposite<T extends OneToOneMapping, R extends OneToOneRelationship, C extends Cascade> 
	extends Pane<T>
	implements JpaComposite
{
	protected AbstractOneToOneMappingComposite(
			PropertyValueModel<? extends T> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
	        WidgetFactory widgetFactory,
	        ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeOneToOneCollapsibleSection(container);
		initializeJoiningStrategyCollapsibleSection(container);
	}
	
	protected void initializeOneToOneCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container,
				ExpandableComposite.TITLE_BAR |
				ExpandableComposite.TWISTIE |
				ExpandableComposite.EXPANDED);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.OneToOneSection_title);
		section.setClient(this.initializeOneToOneSection(section));
	}

	protected abstract Control initializeOneToOneSection(Composite container);

	protected void initializeJoiningStrategyCollapsibleSection(Composite container) {
		new OneToOneJoiningStrategyPane(this, buildRelationshipModel(), container);
	}

	protected PropertyValueModel<R> buildRelationshipModel() {
		return new TransformationPropertyValueModel<T, R>(getSubjectHolder()) {
			@SuppressWarnings("unchecked")
			@Override
			protected R transform_(T value) {
				return (R) value.getRelationship();
			}
		};
	}

	protected PropertyValueModel<C> buildCascadeModel() {
		return new TransformationPropertyValueModel<T, C>(getSubjectHolder()) {
			@SuppressWarnings("unchecked")
			@Override
			protected C transform_(T value) {
				return (C) value.getCascade();
			}
		};
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

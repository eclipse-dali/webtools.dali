/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedAccessReference;
import org.eclipse.jpt.jpa.core.context.Cascade;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToManyRelationship;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public abstract class AbstractOneToManyMappingComposite<T extends OneToManyMapping, R extends OneToManyRelationship, C extends Cascade> 
	extends Pane<T>
	implements JpaComposite
{
	protected AbstractOneToManyMappingComposite(
			PropertyValueModel<? extends T> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeOneToManyCollapsibleSection(container);
		initializeJoiningStrategyCollapsibleSection(container);
		initializeOrderingCollapsibleSection(container);
	}
	
	protected void initializeOneToManyCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container,
				ExpandableComposite.TITLE_BAR |
				ExpandableComposite.TWISTIE |
				ExpandableComposite.EXPANDED);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.ONE_TO_MANY_SECTION_TITLE);
		section.setClient(this.initializeOneToManySection(section));
	}

	protected abstract Control initializeOneToManySection(Composite container);

	protected void initializeJoiningStrategyCollapsibleSection(Composite container) {
		new OneToManyJoiningStrategyPane(this, buildRelationshipModel(), container);
	}
	
	protected void initializeOrderingCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.ORDERING_COMPOSITE_ORDERING_GROUP);

		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(initializeOrderingSection(section));
				}
			}
		});
	}

	protected Control initializeOrderingSection(Composite container) {
		return new OrderingComposite(this, container).getControl();
	}

	protected PropertyValueModel<R> buildRelationshipModel() {
		return new TransformationPropertyValueModel<T, R>(getSubjectHolder()) {
			@SuppressWarnings("unchecked")
			@Override
			protected R transform_(T mapping) {
				return (R) mapping.getRelationship();
			}
		};
	}	
	
	protected PropertyValueModel<C> buildCascadeModel() {
		return new TransformationPropertyValueModel<T, C>(getSubjectHolder()) {
			@SuppressWarnings("unchecked")
			@Override
			protected C transform_(T mapping) {
				return (C) mapping.getCascade();
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

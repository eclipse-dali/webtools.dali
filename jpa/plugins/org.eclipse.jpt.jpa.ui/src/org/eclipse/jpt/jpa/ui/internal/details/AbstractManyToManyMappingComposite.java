/*******************************************************************************
 * Copyright (c) 2006, 2016 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Cascade;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToManyRelationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedAccessReference;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public abstract class AbstractManyToManyMappingComposite<M extends ManyToManyMapping, R extends ManyToManyRelationship, C extends Cascade> 
	extends Pane<M>
    implements JpaComposite
{
	protected AbstractManyToManyMappingComposite(
			PropertyValueModel<? extends M> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeManyToManyCollapsibleSection(container);
		initializeJoiningStrategyCollapsibleSection(container);
		initializeOrderingCollapsibleSection(container);
	}
	
	protected void initializeManyToManyCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container,
				ExpandableComposite.TITLE_BAR |
				ExpandableComposite.TWISTIE |
				ExpandableComposite.EXPANDED);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.MANY_TO_MANY_SECTION_TITLE);
		section.setClient(this.initializeManyToManySection(section));
	}

	protected abstract Control initializeManyToManySection(Composite container);

	@SuppressWarnings("unused")
	protected void initializeJoiningStrategyCollapsibleSection(Composite container) {
		new ManyToManyJoiningStrategyPane(this, buildRelationshipModel(), container);
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
		return PropertyValueModelTools.transform(this.getSubjectHolder(), TransformerTools.downcast(ManyToManyMapping.RELATIONSHIP_TRANSFORMER));
	}
	
	protected PropertyValueModel<C> buildCascadeModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), TransformerTools.downcast(RelationshipMapping.CASCADE_TRANSFORMER));
	}
	
	protected PropertyValueModel<SpecifiedAccessReference> buildAccessReferenceModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), AttributeMapping.PERSISTENT_ATTRIBUTE_TRANSFORMER);
	}
}

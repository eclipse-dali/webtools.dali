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
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneRelationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedAccessReference;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public abstract class AbstractManyToOneMappingComposite<M extends ManyToOneMapping, R extends ManyToOneRelationship, C extends Cascade> 
	extends Pane<M>
	implements JpaComposite
{
	protected AbstractManyToOneMappingComposite(
			PropertyValueModel<? extends M> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
	        WidgetFactory widgetFactory,
	        ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeManyToOneCollapsibleSection(container);
		initializeJoiningStrategyCollapsibleSection(container);
	}
	
	protected void initializeManyToOneCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container,
				ExpandableComposite.TITLE_BAR |
				ExpandableComposite.TWISTIE |
				ExpandableComposite.EXPANDED);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.MANY_TO_ONE_SECTION_TITLE);
		section.setClient(this.initializeManyToOneSection(section));
	}

	protected abstract Control initializeManyToOneSection(Composite container);

	@SuppressWarnings("unused")
	protected void initializeJoiningStrategyCollapsibleSection(Composite container) {
		new ManyToOneJoiningStrategyPane(this, buildRelationshipModel(), container);
	}

	protected PropertyValueModel<C> buildCascadeModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), TransformerTools.downcast(RelationshipMapping.CASCADE_TRANSFORMER));
	}

	protected PropertyValueModel<R> buildRelationshipModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), TransformerTools.downcast(ManyToOneMapping.RELATIONSHIP_TRANSFORMER));
	}

	protected PropertyValueModel<SpecifiedAccessReference> buildAccessReferenceModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), AttributeMapping.PERSISTENT_ATTRIBUTE_TRANSFORMER);
	}
}

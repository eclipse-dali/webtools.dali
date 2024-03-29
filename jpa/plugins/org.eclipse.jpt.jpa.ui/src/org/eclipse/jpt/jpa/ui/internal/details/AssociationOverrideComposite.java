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

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.JoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | JoinColumnsComposite                                                      |
 * |                                                                           |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see SpecifiedAssociationOverride
 * @see EntityOverridesComposite - The parent container
 * @see JoinColumnJoiningStrategyPane
 *
 * @version 2.2
 * @since 1.0
 */
public class AssociationOverrideComposite
	extends Pane<AssociationOverride>
{
	/**
	 * Creates a new <code>AssociationOverrideComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>AssociationOverride</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public AssociationOverrideComposite(Pane<?> parentPane, 
			PropertyValueModel<? extends AssociationOverride> subjectHolder,
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}

	public AssociationOverrideComposite(Pane<?> parentPane, 
		PropertyValueModel<? extends AssociationOverride> subjectHolder,
		PropertyValueModel<Boolean> enabledModel,
		Composite parent) {
	
	super(parentPane, subjectHolder, enabledModel, parent);
}
	
	@Override
	protected Composite addComposite(Composite parent) {
		return addTitledGroup(
			parent,
			JptJpaUiDetailsMessages.JOINING_TITLE);
	}

	@Override
	protected void initializeLayout(Composite container) {
		addJoinColumnJoiningStrategyPane(container);
	}
	
	protected void addJoinColumnJoiningStrategyPane(Composite container) {
		JoinColumnJoiningStrategyPane.
			buildJoinColumnJoiningStrategyPaneWithoutIncludeOverrideCheckBox(
				this, 
				buildRelationshipModel(), 
				container);		
	}
	
	private PropertyValueModel<JoinColumnRelationship> buildRelationshipModel() {
		return new TransformationPropertyValueModel<AssociationOverride, JoinColumnRelationship>(getSubjectHolder()) {
			@Override
			protected JoinColumnRelationship transform_(AssociationOverride value) {
				// with virtual overrides: m:m mappings do not support join columns, so we need to check
				Relationship relationship = value.getRelationship();
				return (relationship instanceof JoinColumnRelationship) ?
					(JoinColumnRelationship) relationship : null;
			}
		};
	}
}
/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.core.context.ReadOnlyJoinColumnRelationship;
import org.eclipse.jpt.core.context.ReadOnlyRelationship;
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
 * @see AssociationOverride
 * @see EntityOverridesComposite - The parent container
 * @see JoinColumnJoiningStrategyPane
 *
 * @version 2.2
 * @since 1.0
 */
public class AssociationOverrideComposite
	extends Pane<ReadOnlyAssociationOverride>
{
	/**
	 * Creates a new <code>AssociationOverrideComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>AssociationOverride</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public AssociationOverrideComposite(Pane<?> parentPane, 
			PropertyValueModel<? extends ReadOnlyAssociationOverride> subjectHolder,
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		Composite composite = addTitledGroup(
				container,
				JptUiDetailsMessages.Joining_title);
		
		addJoinColumnJoiningStrategyPane(composite);
		
		addSubPane(composite, 5);
	}
	
	protected void addJoinColumnJoiningStrategyPane(Composite container) {
		JoinColumnJoiningStrategyPane.
			buildJoinColumnJoiningStrategyPaneWithoutIncludeOverrideCheckBox(
				this, 
				buildRelationshipModel(), 
				container);		
	}
	
	private PropertyValueModel<ReadOnlyJoinColumnRelationship> buildRelationshipModel() {
		return new TransformationPropertyValueModel<ReadOnlyAssociationOverride, ReadOnlyJoinColumnRelationship>(getSubjectHolder()) {
			@Override
			protected ReadOnlyJoinColumnRelationship transform_(ReadOnlyAssociationOverride value) {
				// with virtual overrides: m:m mappings do not support join columns, so we need to check
				ReadOnlyRelationship relationship = value.getRelationship();
				return (relationship instanceof ReadOnlyJoinColumnRelationship) ?
					(ReadOnlyJoinColumnRelationship) relationship : null;
			}
		};
	}
}
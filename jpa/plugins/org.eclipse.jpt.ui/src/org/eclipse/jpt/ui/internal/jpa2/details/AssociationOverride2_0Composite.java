/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.core.context.ReadOnlyJoinTableRelationship;
import org.eclipse.jpt.ui.internal.details.AssociationOverrideComposite;
import org.eclipse.jpt.ui.internal.details.EntityOverridesComposite;
import org.eclipse.jpt.ui.internal.details.JoinColumnsComposite;
import org.eclipse.jpt.ui.internal.details.JoinTableJoiningStrategyPane;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | JoinColumnJoiningStrategyPane                                             |
 * | JoinTableJoiningStrategyPane                                              |
 * |                                                                           |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see AssociationOverride
 * @see EntityOverridesComposite - The parent container
 * @see JoinColumnsComposite
 *
 * @version 2.3
 * @since 1.0
 */
public class AssociationOverride2_0Composite
	extends AssociationOverrideComposite
{
	/**
	 * Creates a new <code>AssociationOverrideComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>AssociationOverride</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public AssociationOverride2_0Composite(Pane<?> parentPane, 
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
		
		addJoinTableJoiningStrategyPane(composite);
		
		addSubPane(composite, 5);
	}
	
	protected void addJoinTableJoiningStrategyPane(Composite container) {
		new JoinTableJoiningStrategyPane(this, buildRelationshipModel(), container);		
	}
	
	private PropertyValueModel<ReadOnlyJoinTableRelationship> buildRelationshipModel() {
		return new TransformationPropertyValueModel<ReadOnlyAssociationOverride, ReadOnlyJoinTableRelationship>(getSubjectHolder()) {
			@Override
			protected ReadOnlyJoinTableRelationship transform_(ReadOnlyAssociationOverride value) {
				// all specified and virtual (mappings) overrides support join tables
				return (ReadOnlyJoinTableRelationship) value.getRelationship();
			}
		};
	}
}
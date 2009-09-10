/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AssociationOverrideRelationshipReference;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

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
public class AssociationOverrideComposite extends FormPane<AssociationOverride>
{

	/**
	 * Creates a new <code>AssociationOverrideComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>AssociationOverride</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public AssociationOverrideComposite(FormPane<?> parentPane, 
								PropertyValueModel<? extends AssociationOverride> subjectHolder,
								Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// joining strategy group pane
		Group groupPane = addTitledGroup(
			container,
			JptUiDetailsMessages.Joining_title
		);
		
		addJoinColumnJoiningStrategyPane(groupPane);
	}

	protected void addJoinColumnJoiningStrategyPane(Composite container) {
		JoinColumnJoiningStrategyPane.
			buildJoinColumnJoiningStrategyPaneWithoutIncludeOverrideCheckBox(
				this, 
				buildRelationshipReferenceHolder(), 
				container);		
	}
	
	private PropertyValueModel<AssociationOverrideRelationshipReference> buildRelationshipReferenceHolder() {
		return new TransformationPropertyValueModel<AssociationOverride, AssociationOverrideRelationshipReference>(getSubjectHolder()) {
			@Override
			protected AssociationOverrideRelationshipReference transform_(AssociationOverride value) {
				return value.getRelationshipReference();
			}
		};
	}

}
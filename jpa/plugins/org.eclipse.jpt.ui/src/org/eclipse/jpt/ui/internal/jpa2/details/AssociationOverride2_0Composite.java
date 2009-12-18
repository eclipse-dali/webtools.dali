/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details;

import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.jpa2.context.AssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.ui.internal.details.AssociationOverrideComposite;
import org.eclipse.jpt.ui.internal.details.EntityOverridesComposite;
import org.eclipse.jpt.ui.internal.details.JoinColumnsComposite;
import org.eclipse.jpt.ui.internal.details.JoinTableJoiningStrategyPane;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
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
 * @see JoinColumnsComposite
 *
 * @version 2.2
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
	public AssociationOverride2_0Composite(FormPane<?> parentPane, 
			PropertyValueModel<? extends AssociationOverride> subjectHolder,
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
		new JoinTableJoiningStrategyPane(this, buildRelationshipReferenceHolder(), container);		
	}
	
	private PropertyValueModel<AssociationOverrideRelationshipReference2_0> buildRelationshipReferenceHolder() {
		return new TransformationPropertyValueModel<AssociationOverride, AssociationOverrideRelationshipReference2_0>(getSubjectHolder()) {
			@Override
			protected AssociationOverrideRelationshipReference2_0 transform_(AssociationOverride value) {
				return (AssociationOverrideRelationshipReference2_0) value.getRelationshipReference();
			}
		};
	}
	
	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);
//		this.joinColumnsComposite.enableWidgets(enabled);
	}
}
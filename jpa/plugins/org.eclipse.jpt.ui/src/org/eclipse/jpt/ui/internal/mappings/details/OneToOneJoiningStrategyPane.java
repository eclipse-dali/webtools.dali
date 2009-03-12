/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.OneToOneRelationshipReference;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Here is the layout of this pane:  
 * <pre>
 * -----------------------------------------------------------------------------
 * | - Joining Strategy ------------------------------------------------------ |
 * | |                                                                       | |
 * | | o MappedByJoiningStrategyPane _______________________________________ | |
 * | | |                                                                   | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | | o JoinColumnStrategyPane ____________________________________________ | |
 * | | |                                                                   | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | | o PrimaryKeyJoinColumnStrategyPane __________________________________ | |
 * | | |                                                                   | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link OneToOneMapping}
 * @see {@link OneToOneRelationshipReference}
 * @see {@link OneToOneMappingComposite}
 * @see {@link MappedByStrategyPane}
 * @see {@link JoinColumnStrategyPane}
 * @see {@link PrimaryKeyJoinColumnStrategyPane}
 *
 * @version 2.1
 * @since 2.1
 */
public class OneToOneJoiningStrategyPane 
	extends FormPane<OneToOneRelationshipReference>
{
	public OneToOneJoiningStrategyPane(
			FormPane<?> parentPane, 
			PropertyValueModel<OneToOneRelationshipReference> subjectHolder, 
			Composite parent) {
		super(parentPane, subjectHolder, parent);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		// joining strategy group pane
		Group groupPane = addTitledGroup(
			container,
			JptUiMappingsMessages.Joining_title
		);
		
		new MappedByJoiningStrategyPane(this, groupPane);
		
		new PrimaryKeyJoinColumnJoiningStrategyPane(this, groupPane);
		
		new JoinColumnJoiningStrategyPane(this, groupPane);
	}
}

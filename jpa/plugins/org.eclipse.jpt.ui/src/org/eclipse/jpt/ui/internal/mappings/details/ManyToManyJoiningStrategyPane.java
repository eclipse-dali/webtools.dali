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

import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToManyRelationshipReference;
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
 * | | o JoinTableStrategyPane _____________________________________________ | |
 * | | |                                                                   | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link ManyToManyMapping}
 * @see {@link ManyToManyRelationshipReference}
 * @see {@link ManyToManyMappingComposite}
 * @see {@link MappedByStrategyPane}
 * @see {@link JoinTableStrategyPane}
 *
 * @version 2.1
 * @since 2.1
 */
public class ManyToManyJoiningStrategyPane 
	extends FormPane<ManyToManyRelationshipReference>
{
	public ManyToManyJoiningStrategyPane(
			FormPane<?> parentPane, 
			PropertyValueModel<ManyToManyRelationshipReference> subjectHolder, 
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
		
		new JoinTableJoiningStrategyPane(this, groupPane);
	}
}

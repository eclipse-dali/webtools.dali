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
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyRelationshipReference;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.JoinColumnJoiningStrategyPane;
import org.eclipse.jpt.ui.internal.mappings.details.JoinTableJoiningStrategyPane;
import org.eclipse.jpt.ui.internal.mappings.details.MappedByJoiningStrategyPane;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class EclipseLinkOneToManyJoiningStrategyPane 
	extends FormPane<EclipseLinkOneToManyRelationshipReference>
{
	public EclipseLinkOneToManyJoiningStrategyPane(
			FormPane<?> parentPane, 
			PropertyValueModel<EclipseLinkOneToManyRelationshipReference> subjectHolder, 
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
		
		new JoinColumnJoiningStrategyPane(this, groupPane);
		
		new JoinTableJoiningStrategyPane(this, groupPane);
	}
}

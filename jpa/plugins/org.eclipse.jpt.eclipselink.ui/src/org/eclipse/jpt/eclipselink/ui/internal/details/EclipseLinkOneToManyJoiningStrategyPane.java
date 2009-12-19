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
package org.eclipse.jpt.eclipselink.ui.internal.details;

import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyRelationshipReference;
import org.eclipse.jpt.ui.internal.details.JoinColumnJoiningStrategyPane;
import org.eclipse.jpt.ui.internal.details.JoinTableJoiningStrategyPane;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.ui.internal.details.MappedByJoiningStrategyPane;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

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
		Composite composite = addCollapsibleSection(
				container,
				JptUiDetailsMessages.Joining_title,
				new SimplePropertyValueModel<Boolean>(Boolean.TRUE));
		
		new MappedByJoiningStrategyPane(this, composite);
		
		JoinColumnJoiningStrategyPane.
			buildJoinColumnJoiningStrategyPaneWithIncludeOverrideCheckBox(this, composite);
		
		new JoinTableJoiningStrategyPane(this, composite);
		
		addSubPane(composite, 5);
	}
}

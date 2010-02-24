/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.context.OneToManyRelationshipReference;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

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
 * @see {@link OneToManyMapping}
 * @see {@link OneToManyRelationshipReference}
 * @see {@link OneToManyMappingComposite}
 * @see {@link MappedByStrategyPane}
 * @see {@link JoinTableStrategyPane}
 *
 * @version 2.1
 * @since 2.1
 */
public class OneToManyJoiningStrategyPane 
	extends Pane<OneToManyRelationshipReference>
{
	public OneToManyJoiningStrategyPane(
			Pane<?> parentPane, 
			PropertyValueModel<? extends OneToManyRelationshipReference> subjectHolder, 
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
		
		new JoinTableJoiningStrategyPane(this, composite);
		
		addSubPane(composite, 5);
	}
}

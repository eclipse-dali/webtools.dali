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

import org.eclipse.jpt.core.context.JoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.JoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here is the layout of this pane:  
 * <pre>
 * -----------------------------------------------------------------------------
 * | o Join columns __________________________________________________________ |
 * | | --------------------------------------------------------------------- | |
 * | | |                                                                   | | |
 * | | |  JoinColumnComposite                                              | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link JoinColumnEnabledRelationshipReference}
 * @see {@link JoinColumnJoiningStrategy}
 * @see {@link OneToOneJoiningStrategyPane}
 * @see {@link ManyToOneJoiningStrategyPane}
 *
 * @version 2.1
 * @since 2.1
 */
public class JoinColumnJoiningStrategyPane
	extends AbstractJoiningStrategyPane
		<JoinColumnEnabledRelationshipReference, JoinColumnJoiningStrategy>
{
	public JoinColumnJoiningStrategyPane(
			FormPane<? extends JoinColumnEnabledRelationshipReference> parentPane, 
			Composite parent) {
		super(parentPane, parent);
	}
	
	
	@Override
	protected WritablePropertyValueModel<Boolean> buildUsesStrategyHolder() {
		return new PropertyAspectAdapter<JoinColumnEnabledRelationshipReference, Boolean>(
				this.getSubjectHolder(), RelationshipReference.PREDOMINANT_JOINING_STRATEGY_PROPERTY) {
			@Override
			protected Boolean buildValue() {
				return (this.subject == null) ? Boolean.FALSE :
					Boolean.valueOf(this.subject.usesJoinColumnJoiningStrategy());
			}
			
			@Override
			protected void setValue_(Boolean value) {
				if (value == Boolean.TRUE) {
					this.subject.setJoinColumnJoiningStrategy();
				}
				else {
					this.subject.unsetJoinColumnJoiningStrategy();
				}
			}
		};
	}
	
	@Override
	protected PropertyValueModel<JoinColumnJoiningStrategy> buildJoiningStrategyHolder() {
		return new PropertyAspectAdapter
				<JoinColumnEnabledRelationshipReference, JoinColumnJoiningStrategy>(
					getSubjectHolder()) {
			@Override
			protected JoinColumnJoiningStrategy buildValue_() {
				return this.subject.getJoinColumnJoiningStrategy();
			}
		};
	}
	
	@Override
	protected String getStrategyLabelKey() {
		return JptUiMappingsMessages.Joining_joinColumnJoiningLabel;
	}
	
	@Override
	protected Composite buildStrategyDetailsComposite(Composite parent) {
		return new JoinColumnComposite(this, this.joiningStrategyHolder, parent).getControl();
	}
}

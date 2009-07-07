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

import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.JoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.JoinTableJoiningStrategy;
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
 * | o Join table ____________________________________________________________ |
 * | | --------------------------------------------------------------------- | |
 * | | |                                                                   | | |
 * | | |  JoinTableComposite                                               | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link JoinTableEnabledRelationshipReference}
 * @see {@link JoinTableJoiningStrategy}
 * @see {@link ManyToOneJoiningStrategyPane}
 * @see {@link ManyToManyJoiningStrategyPane}
 *
 * @version 2.1
 * @since 2.1
 */
public class JoinTableJoiningStrategyPane
	extends AbstractJoiningStrategyPane
		<JoinTableEnabledRelationshipReference, JoinTableJoiningStrategy>
{
	public JoinTableJoiningStrategyPane(
			FormPane<? extends JoinTableEnabledRelationshipReference> parentPane, 
			Composite parent) {
		super(parentPane, parent);
	}
	
	
	@Override
	protected WritablePropertyValueModel<Boolean> buildUsesStrategyHolder() {
		return new PropertyAspectAdapter<JoinTableEnabledRelationshipReference, Boolean>(
				this.getSubjectHolder(), RelationshipReference.PREDOMINANT_JOINING_STRATEGY_PROPERTY) {
			@Override
			protected Boolean buildValue() {
				return (this.subject == null) ? Boolean.FALSE :
					Boolean.valueOf(this.subject.usesJoinTableJoiningStrategy());
			}
			
			@Override
			protected void setValue_(Boolean value) {
				if (value == Boolean.TRUE) {
					this.subject.setJoinTableJoiningStrategy();
				}
				else {
					this.subject.unsetJoinTableJoiningStrategy();
				}
			}
		};
	}
	
	@Override
	protected PropertyValueModel<JoinTableJoiningStrategy> buildJoiningStrategyHolder() {
		return new PropertyAspectAdapter
				<JoinTableEnabledRelationshipReference, JoinTableJoiningStrategy>(
					getSubjectHolder()) {
			@Override
			protected JoinTableJoiningStrategy buildValue_() {
				return this.subject.getJoinTableJoiningStrategy();
			}
		};
	}
	
	@Override
	protected String getStrategyLabelKey() {
		return JptUiMappingsMessages.Joining_joinTableJoiningLabel;
	}
	
	@Override
	protected Composite buildStrategyDetailsComposite(Composite parent) {
		return new JoinTableComposite(this, buildJoinTableHolder(), parent).getControl();
	}
	
	protected PropertyValueModel<JoinTable> buildJoinTableHolder() {
		return new PropertyAspectAdapter<JoinTableJoiningStrategy, JoinTable>(
				this.joiningStrategyHolder, JoinTableJoiningStrategy.JOIN_TABLE_PROPERTY) {
			@Override
			protected JoinTable buildValue_() {
				return this.subject.getJoinTable();
			}
		};
	}
}

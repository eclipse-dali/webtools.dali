/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.context.MappedByJoiningStrategy;
import org.eclipse.jpt.core.context.OwnableRelationshipReference;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here is the layout of this pane:  
 * <pre>
 * -----------------------------------------------------------------------------
 * | o Mapped by _____________________________________________________________ |
 * | |             ---------------------------------------------  ---------- | |
 * | |  Attribute: |                                           |  |Browse..| | |
 * | |             ---------------------------------------------  ---------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link OwnableRelationshipReference}
 * @see {@link MappedByJoiningStrategy}
 * @see {@link OneToOneJoiningStrategyPane}
 * @see {@link OneToManyJoiningStrategyPane}
 * @see {@link ManyToManyJoiningStrategyPane}
 *
 * @version 2.3
 * @since 2.1
 */
public class MappedByJoiningStrategyPane 
	extends AbstractJoiningStrategyPane<OwnableRelationshipReference, MappedByJoiningStrategy>
{
	/**
	 * Creates a new <code>MappedByJoiningStrategyPane</code>.
	 *
	 * @param parentPane The parent form pane
	 * @param parent The parent container
	 */
	public MappedByJoiningStrategyPane(
			Pane<? extends OwnableRelationshipReference> parentPane, 
			Composite parent) {
		super(parentPane, parent);
	}

	@Override
	protected Composite buildStrategyDetailsComposite(Composite parent) {
		return new MappedByPane(this, this.buildMappedByJoiningStrategyHolder(), parent).getControl();
	}	

	@Override
	protected WritablePropertyValueModel<Boolean> buildUsesStrategyHolder() {
		return buildUsesMappedByJoiningStrategyHolder(getSubjectHolder());
	}

	protected PropertyValueModel<MappedByJoiningStrategy> buildMappedByJoiningStrategyHolder() {
		return new PropertyAspectAdapter<OwnableRelationshipReference, MappedByJoiningStrategy>(
				getSubjectHolder()) {
			@Override
			protected MappedByJoiningStrategy buildValue_() {
				return this.subject.getMappedByJoiningStrategy();
			}
		};
	}

	public static WritablePropertyValueModel<Boolean> buildUsesMappedByJoiningStrategyHolder(PropertyValueModel<? extends OwnableRelationshipReference> subjectHolder) {
		return new PropertyAspectAdapter<OwnableRelationshipReference, Boolean>(
				subjectHolder, RelationshipReference.PREDOMINANT_JOINING_STRATEGY_PROPERTY) {
			@Override
			protected Boolean buildValue() {
				return (this.subject == null) ? Boolean.FALSE :
					Boolean.valueOf(this.subject.usesMappedByJoiningStrategy());
			}
			
			@Override
			protected void setValue_(Boolean value) {
				if (value == Boolean.TRUE) {
					this.subject.setMappedByJoiningStrategy();
				}
				else {
					this.subject.unsetMappedByJoiningStrategy();
				}
			}
		};
	}
}

/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.MappedByRelationship;
import org.eclipse.jpt.jpa.core.context.SpecifiedMappedByRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

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
 * @see {@link MappedByRelationship}
 * @see {@link SpecifiedMappedByRelationshipStrategy}
 * @see {@link OneToOneJoiningStrategyPane}
 * @see {@link OneToManyJoiningStrategyPane}
 * @see {@link ManyToManyJoiningStrategyPane}
 *
 * @version 2.3
 * @since 2.1
 */
public class MappedByJoiningStrategyPane 
	extends AbstractJoiningStrategyPane<MappedByRelationship, SpecifiedMappedByRelationshipStrategy>
{
	/**
	 * Creates a new <code>MappedByJoiningStrategyPane</code>.
	 *
	 * @param parentPane The parent form pane
	 * @param parent The parent container
	 */
	public MappedByJoiningStrategyPane(
			Pane<? extends MappedByRelationship> parentPane, 
			Composite parent) {
		super(parentPane, parent);
	}

	@Override
	protected Control buildStrategyDetailsComposite(Composite parent) {
		MappedByPane mappedByPane = new MappedByPane(this, this.buildMappedByJoiningStrategyHolder(), buildMappedByRelationshipPaneEnablerHolder(), parent);
		return mappedByPane.getControl();
	}	

	@Override
	protected ModifiablePropertyValueModel<Boolean> buildUsesStrategyHolder() {
		return buildUsesMappedByJoiningStrategyHolder(getSubjectHolder());
	}

	protected PropertyValueModel<SpecifiedMappedByRelationshipStrategy> buildMappedByJoiningStrategyHolder() {
		return new PropertyAspectAdapter<MappedByRelationship, SpecifiedMappedByRelationshipStrategy>(
				getSubjectHolder()) {
			@Override
			protected SpecifiedMappedByRelationshipStrategy buildValue_() {
				return this.subject.getMappedByStrategy();
			}
		};
	}

	public static ModifiablePropertyValueModel<Boolean> buildUsesMappedByJoiningStrategyHolder(PropertyValueModel<? extends MappedByRelationship> subjectHolder) {
		return new PropertyAspectAdapter<MappedByRelationship, Boolean>(
				subjectHolder, Relationship.STRATEGY_PROPERTY) {
			@Override
			protected Boolean buildValue() {
				return (this.subject == null) ? Boolean.FALSE :
					Boolean.valueOf(this.subject.strategyIsMappedBy());
			}
			
			@Override
			protected void setValue_(Boolean value) {
				if (value == Boolean.TRUE) {
					this.subject.setStrategyToMappedBy();
				}
				//value == FALSE - selection of another radio button causes this strategy to get unset
			}
		};
	}


	private TransformationPropertyValueModel<MappedByRelationship, Boolean> buildMappedByRelationshipPaneEnablerHolder() {
		return new TransformationPropertyValueModel<MappedByRelationship, Boolean>(getSubjectHolder()) {
			@Override
			protected Boolean transform_(MappedByRelationship v) {
				return Boolean.valueOf(!v.isVirtual());
			}
		};
	}
}

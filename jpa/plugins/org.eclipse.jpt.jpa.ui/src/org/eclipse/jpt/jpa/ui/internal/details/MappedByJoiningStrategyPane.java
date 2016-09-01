/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.MappedByRelationship;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.SpecifiedMappedByRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.SpecifiedOrVirtual;
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
 * @see MappedByRelationship
 * @see SpecifiedMappedByRelationshipStrategy
 * @see OneToOneJoiningStrategyPane
 * @see OneToManyJoiningStrategyPane
 * @see ManyToManyJoiningStrategyPane
 *
 * @version 2.3
 * @since 2.1
 */
public class MappedByJoiningStrategyPane 
	extends AbstractJoiningStrategyPane<MappedByRelationship>
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
		MappedByPane mappedByPane = new MappedByPane(this, this.buildMappedByJoiningStrategyModel(), buildMappedByRelationshipPaneEnablerModel(), parent);
		return mappedByPane.getControl();
	}	

	@Override
	protected ModifiablePropertyValueModel<Boolean> buildUsesStrategyModel() {
		return buildUsesMappedByJoiningStrategyModel(getSubjectHolder());
	}

	protected PropertyValueModel<SpecifiedMappedByRelationshipStrategy> buildMappedByJoiningStrategyModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), m -> m.getMappedByStrategy());
	}

	public static ModifiablePropertyValueModel<Boolean> buildUsesMappedByJoiningStrategyModel(PropertyValueModel<? extends MappedByRelationship> subjectHolder) {
		return PropertyValueModelTools.modifiableSubjectModelAspectAdapter_(
				subjectHolder,
				Relationship.STRATEGY_PROPERTY,
				m -> Boolean.valueOf((m != null) && m.strategyIsMappedBy()),
				(m, value) -> {
					if ((m != null) && (value != null) && value.booleanValue()) {
						m.setStrategyToMappedBy();
					}
				}
			);
	}


	private PropertyValueModel<Boolean> buildMappedByRelationshipPaneEnablerModel() {
		return PropertyValueModelTools.valueAffirms(this.getSubjectHolder(), SpecifiedOrVirtual.SPECIFIED_PREDICATE);
	}
}

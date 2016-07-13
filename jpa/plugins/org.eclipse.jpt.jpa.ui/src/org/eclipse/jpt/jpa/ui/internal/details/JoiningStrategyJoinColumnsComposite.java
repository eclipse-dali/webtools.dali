/*******************************************************************************
 * Copyright (c) 2005, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.ui.internal.details.JoinColumnsComposite.JoinColumnsEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * Here is the layout of this pane:
 * <pre>                                            
 * ---------------------------------------------------------------------
 * |                                                                   |
 * | JoinColumnsComposite                                              |
 * |                                                                   |
 * ---------------------------------------------------------------------
 * -------------------------------------------------------------------------</pre>
 *
 * @see SpecifiedJoinColumnRelationship
 * @see SpecifiedJoinColumnRelationshipStrategy
 * @see JoinColumnJoiningStrategyPane
 * @see JoinColumnInJoiningStrategyDialog
 *
 * @version 3.0
 * @since 2.0
 */
public class JoiningStrategyJoinColumnsComposite 
	extends Pane<JoinColumnRelationshipStrategy>
{
	
	private JoinColumnsComposite<JoinColumnRelationshipStrategy> joinColumnsComposite;
	
	public JoiningStrategyJoinColumnsComposite(Pane<? extends JoinColumnRelationshipStrategy> parentPane, Composite parentComposite) {
		super(parentPane, parentComposite);
	}

	public JoiningStrategyJoinColumnsComposite(
		Pane<?> parentPane,
		PropertyValueModel<JoinColumnRelationshipStrategy> subjectModel,
		Composite parent
	) {
		super(parentPane, subjectModel, parent);
	}

	@Override
	protected Composite addComposite(Composite container) {
		this.joinColumnsComposite = new JoinColumnsComposite<>(
				this,
				container,
				this.buildJoinColumnsProvider(),
				this.buildJoinColumnsPaneEnabledModel()
			);
		return this.joinColumnsComposite.getControl();
	}

	@Override
	protected void initializeLayout(Composite container) {
		// NOP
	}
	
	private JoinColumnsEditor<JoinColumnRelationshipStrategy> buildJoinColumnsProvider() {
		return new JoinColumnsEditor<JoinColumnRelationshipStrategy>() {

			public SpecifiedJoinColumn addJoinColumn(JoinColumnRelationshipStrategy subject) {
				return JoiningStrategyJoinColumnsComposite.this.addJoinColumn(subject);
			}

			public boolean hasSpecifiedJoinColumns(JoinColumnRelationshipStrategy subject) {
				return subject.hasSpecifiedJoinColumns();
			}

			public void editJoinColumn(JoinColumnRelationshipStrategy subject, JoinColumn joinColumn) {
				JoiningStrategyJoinColumnsComposite.this.editJoinColumn(subject, joinColumn);
			}

			public JoinColumn getDefaultJoinColumn(JoinColumnRelationshipStrategy subject) {
				return subject.getDefaultJoinColumn();
			}

			public String getDefaultPropertyName() {
				return JoinColumnRelationshipStrategy.DEFAULT_JOIN_COLUMN_PROPERTY;
			}

			public String getSpecifiedJoinColumnsListPropertyName() {
				return JoinColumnRelationshipStrategy.SPECIFIED_JOIN_COLUMNS_LIST;
			}

			public void removeJoinColumn(JoinColumnRelationshipStrategy subject, SpecifiedJoinColumn joinColumn) {
				((SpecifiedJoinColumnRelationshipStrategy) subject).removeSpecifiedJoinColumn(joinColumn);
			}

			public ListIterable<JoinColumn> getSpecifiedJoinColumns(JoinColumnRelationshipStrategy subject) {
				return new SuperListIterableWrapper<>(subject.getSpecifiedJoinColumns());
			}

			public int getSpecifiedJoinColumnsSize(JoinColumnRelationshipStrategy subject) {
				return subject.getSpecifiedJoinColumnsSize();
			}
		};
	}
	
	SpecifiedJoinColumn addJoinColumn(JoinColumnRelationshipStrategy joiningStrategy) {
		JoinColumnInJoiningStrategyDialog dialog = new JoinColumnInJoiningStrategyDialog(this.getShell(), this.getResourceManager(), joiningStrategy);

		dialog.setBlockOnOpen(true);
		dialog.open();
		if (dialog.wasConfirmed()) {
			return addJoinColumn(dialog.getSubject());
		}
		return null;
	}
	
	SpecifiedJoinColumn addJoinColumn(JoinColumnInJoiningStrategyStateObject stateObject) {
		SpecifiedJoinColumnRelationshipStrategy subject = (SpecifiedJoinColumnRelationshipStrategy) getSubject();
		SpecifiedJoinColumn joinColumn = subject.addSpecifiedJoinColumn();
		stateObject.updateJoinColumn(joinColumn);
		return joinColumn;
	}

	public void setSelectedJoinColumn(SpecifiedJoinColumn joinColumn) {
		this.joinColumnsComposite.setSelectedJoinColumn(joinColumn);
	}
	
	void editJoinColumn(JoinColumnRelationshipStrategy joiningStrategy, JoinColumn joinColumn) {
		JoinColumnInJoiningStrategyDialog dialog = new JoinColumnInJoiningStrategyDialog(this.getShell(), this.getResourceManager(), joiningStrategy, joinColumn);

		dialog.setBlockOnOpen(true);
		dialog.open();
		if (dialog.wasConfirmed()) {
			updateJoinColumn(dialog.getSubject());
		}
	}
	
	void updateJoinColumn(JoinColumnInJoiningStrategyStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}

	protected PropertyValueModel<Boolean> buildJoinColumnsPaneEnabledModel() {
		return PropertyValueModelTools.and(this.buildStrategyIsSpecifiedModel(), this.buildSpecifiedJoinColumnsIsNotEmptyModel());
	}

	protected PropertyValueModel<Boolean> buildStrategyIsSpecifiedModel() {
		return PropertyValueModelTools.valueAffirms(this.getSubjectHolder(), this.buildStrategyIsSpecifiedPredicate(), false);
	}

	protected Predicate<JoinColumnRelationshipStrategy> buildStrategyIsSpecifiedPredicate() {
		return PredicateTools.not(this.buildStrategyIsVirtualPredicate());
	}

	protected Predicate<JoinColumnRelationshipStrategy> buildStrategyIsVirtualPredicate() {
		return STRATEGY_IS_VIRTUAL_PREDICATE;
	}

	public static final PredicateAdapter<JoinColumnRelationshipStrategy> STRATEGY_IS_VIRTUAL_PREDICATE = new StrategyIsVirtualPredicate();

	public static class StrategyIsVirtualPredicate
		extends PredicateAdapter<JoinColumnRelationshipStrategy>
	{
		@Override
		public boolean evaluate(JoinColumnRelationshipStrategy strategy) {
			return strategy.getRelationship().isVirtual();
		}
	}

	protected PropertyValueModel<Boolean> buildSpecifiedJoinColumnsIsNotEmptyModel() {
		return ListValueModelTools.isNotEmptyPropertyValueModel(this.buildSpecifiedJoinColumnsModel());
	}

	protected ListValueModel<JoinColumn> buildSpecifiedJoinColumnsModel() {
		return new ListAspectAdapter<JoinColumnRelationshipStrategy, JoinColumn>(this.getSubjectHolder(), JoinColumnRelationshipStrategy.SPECIFIED_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterable<JoinColumn> getListIterable() {
				return IterableTools.upcast(this.subject.getSpecifiedJoinColumns());
			}
			@Override
			protected int size_() {
				return this.subject.getSpecifiedJoinColumnsSize();
			}
		};
	}
}

/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.ReadOnlyModifiablePropertyValueModelWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ValueListAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.JoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationshipStrategy;
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
 * @see JoinColumnRelationship
 * @see JoinColumnRelationshipStrategy
 * @see JoinColumnJoiningStrategyPane
 * @see JoinColumnInJoiningStrategyDialog
 *
 * @version 3.0
 * @since 2.0
 */
public class JoiningStrategyJoinColumnsComposite 
	extends Pane<ReadOnlyJoinColumnRelationshipStrategy>
{
	
	private JoinColumnsComposite<ReadOnlyJoinColumnRelationshipStrategy> joinColumnsComposite;
	
	public JoiningStrategyJoinColumnsComposite(
			Pane<?> parentPane,
			PropertyValueModel<ReadOnlyJoinColumnRelationshipStrategy> subjectHolder,
			Composite parent) {
		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected Composite addComposite(Composite container) {
		this.joinColumnsComposite = new JoinColumnsComposite<ReadOnlyJoinColumnRelationshipStrategy>(this, container, buildJoinColumnsProvider(), new JoinColumnPaneEnablerHolder());
		return this.joinColumnsComposite.getControl();
	}

	@Override
	protected void initializeLayout(Composite container) {		
	}
	
	private JoinColumnsEditor<ReadOnlyJoinColumnRelationshipStrategy> buildJoinColumnsProvider() {
		return new JoinColumnsEditor<ReadOnlyJoinColumnRelationshipStrategy>() {

			public JoinColumn addJoinColumn(ReadOnlyJoinColumnRelationshipStrategy subject) {
				return JoiningStrategyJoinColumnsComposite.this.addJoinColumn(subject);
			}

			public boolean hasSpecifiedJoinColumns(ReadOnlyJoinColumnRelationshipStrategy subject) {
				return subject.hasSpecifiedJoinColumns();
			}

			public void editJoinColumn(ReadOnlyJoinColumnRelationshipStrategy subject, ReadOnlyJoinColumn joinColumn) {
				JoiningStrategyJoinColumnsComposite.this.editJoinColumn(subject, joinColumn);
			}

			public ReadOnlyJoinColumn getDefaultJoinColumn(ReadOnlyJoinColumnRelationshipStrategy subject) {
				return subject.getDefaultJoinColumn();
			}

			public String getDefaultPropertyName() {
				return ReadOnlyJoinColumnRelationshipStrategy.DEFAULT_JOIN_COLUMN_PROPERTY;
			}

			public String getSpecifiedJoinColumnsListPropertyName() {
				return ReadOnlyJoinColumnRelationshipStrategy.SPECIFIED_JOIN_COLUMNS_LIST;
			}

			public void removeJoinColumn(ReadOnlyJoinColumnRelationshipStrategy subject, JoinColumn joinColumn) {
				((JoinColumnRelationshipStrategy) subject).removeSpecifiedJoinColumn(joinColumn);
			}

			public ListIterable<ReadOnlyJoinColumn> getSpecifiedJoinColumns(ReadOnlyJoinColumnRelationshipStrategy subject) {
				return new SuperListIterableWrapper<ReadOnlyJoinColumn>(subject.getSpecifiedJoinColumns());
			}

			public int getSpecifiedJoinColumnsSize(ReadOnlyJoinColumnRelationshipStrategy subject) {
				return subject.getSpecifiedJoinColumnsSize();
			}
		};
	}
	
	JoinColumn addJoinColumn(ReadOnlyJoinColumnRelationshipStrategy joiningStrategy) {
		JoinColumnInJoiningStrategyDialog dialog =
			new JoinColumnInJoiningStrategyDialog(getShell(), joiningStrategy, null);

		dialog.setBlockOnOpen(true);
		dialog.open();
		if (dialog.wasConfirmed()) {
			return addJoinColumn(dialog.getSubject());
		}
		return null;
	}
	
	JoinColumn addJoinColumn(JoinColumnInJoiningStrategyStateObject stateObject) {
		JoinColumnRelationshipStrategy subject = (JoinColumnRelationshipStrategy) getSubject();
		JoinColumn joinColumn = subject.addSpecifiedJoinColumn();
		stateObject.updateJoinColumn(joinColumn);
		return joinColumn;
	}

	public void setSelectedJoinColumn(JoinColumn joinColumn) {
		this.joinColumnsComposite.setSelectedJoinColumn(joinColumn);
	}
	
	void editJoinColumn(ReadOnlyJoinColumnRelationshipStrategy joiningStrategy, ReadOnlyJoinColumn joinColumn) {
		JoinColumnInJoiningStrategyDialog dialog =
			new JoinColumnInJoiningStrategyDialog(getShell(), joiningStrategy, joinColumn);

		dialog.setBlockOnOpen(true);
		dialog.open();
		if (dialog.wasConfirmed()) {
			updateJoinColumn(dialog.getSubject());
		}
	}
	
	void updateJoinColumn(JoinColumnInJoiningStrategyStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}
	
	/* CU private */ class JoinColumnPaneEnablerHolder 
		extends TransformationPropertyValueModel<ReadOnlyJoinColumnRelationshipStrategy, Boolean>
	{
		private StateChangeListener stateChangeListener;
		
		JoinColumnPaneEnablerHolder() {
			super(
				new ValueListAdapter<ReadOnlyJoinColumnRelationshipStrategy>(
					new ReadOnlyModifiablePropertyValueModelWrapper<ReadOnlyJoinColumnRelationshipStrategy>(getSubjectHolder()), 
					ReadOnlyJoinColumnRelationshipStrategy.SPECIFIED_JOIN_COLUMNS_LIST
				)
			);
			this.stateChangeListener = this.buildStateChangeListener();
		}
		
		
		private StateChangeListener buildStateChangeListener() {
			return new StateChangeListener() {
				public void stateChanged(StateChangeEvent event) {
					JoinColumnPaneEnablerHolder.this.valueStateChanged();
				}
			};
		}
		
		void valueStateChanged() {
			Object old = this.value;
			this.value = this.transform(this.valueModel.getValue());
			this.firePropertyChanged(VALUE, old, this.value);
		}
		
		@Override
		protected Boolean transform(ReadOnlyJoinColumnRelationshipStrategy v) {
			return (v == null) ? Boolean.FALSE : super.transform(v);
		}
		
		@Override
		protected Boolean transform_(ReadOnlyJoinColumnRelationshipStrategy v) {
			boolean virtual = v.getRelationship().isVirtual();
			return Boolean.valueOf(! virtual && v.getSpecifiedJoinColumnsSize() > 0);
		}
		
		@Override
		protected void engageModel() {
			super.engageModel();
			this.valueModel.addStateChangeListener(this.stateChangeListener);
		}
		
		@Override
		protected void disengageModel() {
			this.valueModel.removeStateChangeListener(this.stateChangeListener);
			super.disengageModel();
		}
	}
}

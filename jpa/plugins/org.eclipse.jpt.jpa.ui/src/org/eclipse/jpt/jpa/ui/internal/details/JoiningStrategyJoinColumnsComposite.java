/*******************************************************************************
 * Copyright (c) 2005, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.ListIterator;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.common.utility.internal.iterators.SuperListIteratorWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CachingTransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ReadOnlyWritablePropertyValueModelWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.ValueListAdapter;
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
	protected void initializeLayout(Composite container) {		
		this.joinColumnsComposite = new JoinColumnsComposite<ReadOnlyJoinColumnRelationshipStrategy>(this, container, buildJoinColumnsProvider());
		this.joinColumnsComposite.installJoinColumnsPaneEnabler(new JoinColumnPaneEnablerHolder());
	}
	
	private JoinColumnsEditor<ReadOnlyJoinColumnRelationshipStrategy> buildJoinColumnsProvider() {
		return new JoinColumnsEditor<ReadOnlyJoinColumnRelationshipStrategy>() {

			public void addJoinColumn(ReadOnlyJoinColumnRelationshipStrategy subject) {
				JoiningStrategyJoinColumnsComposite.this.addJoinColumn(subject);
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

			public void removeJoinColumns(ReadOnlyJoinColumnRelationshipStrategy subject, int[] selectedIndices) {
				for (int index = selectedIndices.length; --index >= 0; ) {
					((JoinColumnRelationshipStrategy) subject).removeSpecifiedJoinColumn(selectedIndices[index]);
				}				
			}

			public ListIterator<ReadOnlyJoinColumn> specifiedJoinColumns(ReadOnlyJoinColumnRelationshipStrategy subject) {
				return new SuperListIteratorWrapper<ReadOnlyJoinColumn>(subject.getSpecifiedJoinColumns());
			}

			public int specifiedJoinColumnsSize(ReadOnlyJoinColumnRelationshipStrategy subject) {
				return subject.getSpecifiedJoinColumnsSize();
			}
		};
	}
	
	void addJoinColumn(ReadOnlyJoinColumnRelationshipStrategy joiningStrategy) {
		JoinColumnInJoiningStrategyDialog dialog =
			new JoinColumnInJoiningStrategyDialog(getShell(), joiningStrategy, null);
		dialog.openDialog(buildAddJoinColumnPostExecution());
	}
	
	private PostExecution<JoinColumnInJoiningStrategyDialog> buildAddJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInJoiningStrategyDialog>() {
			public void execute(JoinColumnInJoiningStrategyDialog dialog) {
				if (dialog.wasConfirmed()) {
					addJoinColumn(dialog.getSubject());
				}
			}
		};
	}
	
	void addJoinColumn(JoinColumnInJoiningStrategyStateObject stateObject) {
		JoinColumnRelationshipStrategy subject = (JoinColumnRelationshipStrategy) getSubject();
		JoinColumn joinColumn = subject.addSpecifiedJoinColumn();
		stateObject.updateJoinColumn(joinColumn);
		this.setSelectedJoinColumn(joinColumn);
	}

	public void setSelectedJoinColumn(JoinColumn joinColumn) {
		this.joinColumnsComposite.setSelectedJoinColumn(joinColumn);
	}
	
	void editJoinColumn(ReadOnlyJoinColumnRelationshipStrategy joiningStrategy, ReadOnlyJoinColumn joinColumn) {
		JoinColumnInJoiningStrategyDialog dialog =
			new JoinColumnInJoiningStrategyDialog(getShell(), joiningStrategy, joinColumn);
		dialog.openDialog(buildEditJoinColumnPostExecution());
	}
	
	private PostExecution<JoinColumnInJoiningStrategyDialog> buildEditJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInJoiningStrategyDialog>() {
			public void execute(JoinColumnInJoiningStrategyDialog dialog) {
				if (dialog.wasConfirmed()) {
					updateJoinColumn(dialog.getSubject());
				}
			}
		};
	}
	
	void updateJoinColumn(JoinColumnInJoiningStrategyStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}
	
	protected CachingTransformationPropertyValueModel<ReadOnlyJoinColumnRelationshipStrategy, Boolean> buildJoinColumnsPaneEnabledHolder() {
		return new CachingTransformationPropertyValueModel<ReadOnlyJoinColumnRelationshipStrategy, Boolean>(
			new ValueListAdapter<ReadOnlyJoinColumnRelationshipStrategy>(
				new ReadOnlyWritablePropertyValueModelWrapper<ReadOnlyJoinColumnRelationshipStrategy>(getSubjectHolder()), 
				ReadOnlyJoinColumnRelationshipStrategy.SPECIFIED_JOIN_COLUMNS_LIST)) {
			
			@Override
			protected Boolean transform(ReadOnlyJoinColumnRelationshipStrategy value) {
				if (value == null) {
					return Boolean.FALSE;
				}
				return super.transform(value);
			}
			
			@Override
			protected Boolean transform_(ReadOnlyJoinColumnRelationshipStrategy value) {
				boolean virtual = value.getRelationship().getMapping().getPersistentAttribute().isVirtual();
				return Boolean.valueOf(! virtual && value.getSpecifiedJoinColumnsSize() > 0);
			}
		};

	}
	
	
	private class JoinColumnPaneEnablerHolder 
		extends CachingTransformationPropertyValueModel<ReadOnlyJoinColumnRelationshipStrategy, Boolean>
	{
		private StateChangeListener stateChangeListener;
		
		
		public JoinColumnPaneEnablerHolder() {
			super(
				new ValueListAdapter<ReadOnlyJoinColumnRelationshipStrategy>(
					new ReadOnlyWritablePropertyValueModelWrapper<ReadOnlyJoinColumnRelationshipStrategy>(getSubjectHolder()), 
					ReadOnlyJoinColumnRelationshipStrategy.SPECIFIED_JOIN_COLUMNS_LIST));
			this.stateChangeListener = buildStateChangeListener();
		}
		
		
		private StateChangeListener buildStateChangeListener() {
			return new StateChangeListener() {
				public void stateChanged(StateChangeEvent event) {
					valueStateChanged();
				}
			};
		}
		
		void valueStateChanged() {
			Object oldValue = this.cachedValue;
			Object newValue = transformNew(this.valueHolder.getValue());
			firePropertyChanged(VALUE, oldValue, newValue);
		}
		
		@Override
		protected Boolean transform(ReadOnlyJoinColumnRelationshipStrategy value) {
			if (value == null) {
				return Boolean.FALSE;
			}
			return super.transform(value);
		}
		
		@Override
		protected Boolean transform_(ReadOnlyJoinColumnRelationshipStrategy value) {
			boolean virtual = value.getRelationship().isVirtual();
			return Boolean.valueOf(! virtual && value.getSpecifiedJoinColumnsSize() > 0);
		}
		
		@Override
		protected void engageModel() {
			super.engageModel();
			this.valueHolder.addStateChangeListener(this.stateChangeListener);
		}
		
		@Override
		protected void disengageModel() {
			this.valueHolder.removeStateChangeListener(this.stateChangeListener);
			super.disengageModel();
		}
	}
}

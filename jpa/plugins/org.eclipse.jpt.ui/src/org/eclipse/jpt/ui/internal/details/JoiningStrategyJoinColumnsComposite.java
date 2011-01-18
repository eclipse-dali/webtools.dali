/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.JoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.core.context.ReadOnlyJoinColumnJoiningStrategy;
import org.eclipse.jpt.ui.internal.details.JoinColumnsComposite.JoinColumnsEditor;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.utility.internal.iterators.SuperListIteratorWrapper;
import org.eclipse.jpt.utility.internal.model.value.CachingTransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.ReadOnlyWritablePropertyValueModelWrapper;
import org.eclipse.jpt.utility.internal.model.value.ValueListAdapter;
import org.eclipse.jpt.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
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
 * @see JoinColumnEnabledRelationshipReference
 * @see JoinColumnJoiningStrategy
 * @see JoinColumnJoiningStrategyPane
 * @see JoinColumnInJoiningStrategyDialog
 *
 * @version 3.0
 * @since 2.0
 */
public class JoiningStrategyJoinColumnsComposite 
	extends Pane<ReadOnlyJoinColumnJoiningStrategy>
{
	
	private JoinColumnsComposite<ReadOnlyJoinColumnJoiningStrategy> joinColumnsComposite;
	
	public JoiningStrategyJoinColumnsComposite(
			Pane<?> parentPane,
			PropertyValueModel<ReadOnlyJoinColumnJoiningStrategy> subjectHolder,
			Composite parent) {
		super(parentPane, subjectHolder, parent);
	}


	@Override
	protected void initializeLayout(Composite container) {		
		this.joinColumnsComposite = new JoinColumnsComposite<ReadOnlyJoinColumnJoiningStrategy>(this, container, buildJoinColumnsProvider());
		this.joinColumnsComposite.installJoinColumnsPaneEnabler(new JoinColumnPaneEnablerHolder());
	}
	
	private JoinColumnsEditor<ReadOnlyJoinColumnJoiningStrategy> buildJoinColumnsProvider() {
		return new JoinColumnsEditor<ReadOnlyJoinColumnJoiningStrategy>() {

			public void addJoinColumn(ReadOnlyJoinColumnJoiningStrategy subject) {
				JoiningStrategyJoinColumnsComposite.this.addJoinColumn(subject);
			}

			public boolean hasSpecifiedJoinColumns(ReadOnlyJoinColumnJoiningStrategy subject) {
				return subject.hasSpecifiedJoinColumns();
			}

			public void editJoinColumn(ReadOnlyJoinColumnJoiningStrategy subject, ReadOnlyJoinColumn joinColumn) {
				JoiningStrategyJoinColumnsComposite.this.editJoinColumn(subject, joinColumn);
			}

			public ReadOnlyJoinColumn getDefaultJoinColumn(ReadOnlyJoinColumnJoiningStrategy subject) {
				return subject.getDefaultJoinColumn();
			}

			public String getDefaultPropertyName() {
				return ReadOnlyJoinColumnJoiningStrategy.DEFAULT_JOIN_COLUMN_PROPERTY;
			}

			public String getSpecifiedJoinColumnsListPropertyName() {
				return ReadOnlyJoinColumnJoiningStrategy.SPECIFIED_JOIN_COLUMNS_LIST;
			}

			public void removeJoinColumns(ReadOnlyJoinColumnJoiningStrategy subject, int[] selectedIndices) {
				for (int index = selectedIndices.length; --index >= 0; ) {
					((JoinColumnJoiningStrategy) subject).removeSpecifiedJoinColumn(selectedIndices[index]);
				}				
			}

			public ListIterator<ReadOnlyJoinColumn> specifiedJoinColumns(ReadOnlyJoinColumnJoiningStrategy subject) {
				return new SuperListIteratorWrapper<ReadOnlyJoinColumn>(subject.specifiedJoinColumns());
			}

			public int specifiedJoinColumnsSize(ReadOnlyJoinColumnJoiningStrategy subject) {
				return subject.specifiedJoinColumnsSize();
			}
		};
	}
	
	void addJoinColumn(ReadOnlyJoinColumnJoiningStrategy joiningStrategy) {
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
		JoinColumnJoiningStrategy subject = (JoinColumnJoiningStrategy) getSubject();
		JoinColumn joinColumn = subject.addSpecifiedJoinColumn();
		stateObject.updateJoinColumn(joinColumn);
		this.setSelectedJoinColumn(joinColumn);
	}

	public void setSelectedJoinColumn(JoinColumn joinColumn) {
		this.joinColumnsComposite.setSelectedJoinColumn(joinColumn);
	}
	
	void editJoinColumn(ReadOnlyJoinColumnJoiningStrategy joiningStrategy, ReadOnlyJoinColumn joinColumn) {
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
	
	protected CachingTransformationPropertyValueModel<ReadOnlyJoinColumnJoiningStrategy, Boolean> buildJoinColumnsPaneEnabledHolder() {
		return new CachingTransformationPropertyValueModel<ReadOnlyJoinColumnJoiningStrategy, Boolean>(
			new ValueListAdapter<ReadOnlyJoinColumnJoiningStrategy>(
				new ReadOnlyWritablePropertyValueModelWrapper<ReadOnlyJoinColumnJoiningStrategy>(getSubjectHolder()), 
				ReadOnlyJoinColumnJoiningStrategy.SPECIFIED_JOIN_COLUMNS_LIST)) {
			
			@Override
			protected Boolean transform(ReadOnlyJoinColumnJoiningStrategy value) {
				if (value == null) {
					return Boolean.FALSE;
				}
				return super.transform(value);
			}
			
			@Override
			protected Boolean transform_(ReadOnlyJoinColumnJoiningStrategy value) {
				boolean virtual = value.getRelationshipReference().getMapping().getPersistentAttribute().isVirtual();
				return Boolean.valueOf(! virtual && value.specifiedJoinColumnsSize() > 0);
			}
		};

	}
	
	
	private class JoinColumnPaneEnablerHolder 
		extends CachingTransformationPropertyValueModel<ReadOnlyJoinColumnJoiningStrategy, Boolean>
	{
		private StateChangeListener stateChangeListener;
		
		
		public JoinColumnPaneEnablerHolder() {
			super(
				new ValueListAdapter<ReadOnlyJoinColumnJoiningStrategy>(
					new ReadOnlyWritablePropertyValueModelWrapper<ReadOnlyJoinColumnJoiningStrategy>(getSubjectHolder()), 
					ReadOnlyJoinColumnJoiningStrategy.SPECIFIED_JOIN_COLUMNS_LIST));
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
		protected Boolean transform(ReadOnlyJoinColumnJoiningStrategy value) {
			if (value == null) {
				return Boolean.FALSE;
			}
			return super.transform(value);
		}
		
		@Override
		protected Boolean transform_(ReadOnlyJoinColumnJoiningStrategy value) {
			boolean virtual = value.getRelationshipReference().isVirtual();
			return Boolean.valueOf(! virtual && value.specifiedJoinColumnsSize() > 0);
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

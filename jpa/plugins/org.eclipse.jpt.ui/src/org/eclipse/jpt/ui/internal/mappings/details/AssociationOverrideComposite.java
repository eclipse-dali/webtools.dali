/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinColumnJoiningStrategy;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.JoinColumnsComposite.JoinColumnsEditor;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | JoinColumnsComposite                                                      |
 * |                                                                           |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see AssociationOverride
 * @see OverridesComposite - The parent container
 * @see JoinColumnsComposite
 *
 * @version 2.2
 * @since 1.0
 */
public class AssociationOverrideComposite extends FormPane<AssociationOverride>
{
	private Composite joinColumnsPane;
	private JoinColumnsComposite<JoinColumnJoiningStrategy> joinColumnsComposite;

	private PropertyValueModel<JoinColumnJoiningStrategy> selectedJoinColumnJoiningStrategyHolder;

	/**
	 * Creates a new <code>AssociationOverrideComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>AssociationOverride</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public AssociationOverrideComposite(FormPane<?> parentPane, 
								PropertyValueModel<? extends AssociationOverride> subjectHolder,
								Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedJoinColumnJoiningStrategyHolder = buildJoinColumnJoiningStrategyHolder();
	}

	private PropertyValueModel<JoinColumnJoiningStrategy> buildJoinColumnJoiningStrategyHolder() {
		return new TransformationPropertyValueModel<AssociationOverride, JoinColumnJoiningStrategy>(getSubjectHolder()) {
			@Override
			protected JoinColumnJoiningStrategy transform_(AssociationOverride value) {
				return value.getRelationshipReference().getJoinColumnJoiningStrategy();
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.joinColumnsPane = addSubPane(container);

		Group joinColumnsGroupPane = addTitledGroup(
			this.joinColumnsPane,
			JptUiMappingsMessages.AssociationOverridesComposite_joinColumn
		);

		// Join Columns list pane (for AssociationOverride)
		this.joinColumnsComposite =
			new JoinColumnsComposite<JoinColumnJoiningStrategy>(
				this,
				this.selectedJoinColumnJoiningStrategyHolder,
				joinColumnsGroupPane,
				buildJoinColumnsEditor(),
				false
			);
	}

	private JoinColumnsProvider buildJoinColumnsEditor() {
		return new JoinColumnsProvider();
	}

	private void addJoinColumn(JoinColumnJoiningStrategy subject) {

		JoinColumnInJoiningStrategyDialog dialog =
			new JoinColumnInJoiningStrategyDialog(getShell(), subject, null);

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

	private void addJoinColumn(JoinColumnInJoiningStrategyStateObject stateObject) {

		JoinColumnJoiningStrategy joiningStrategy = stateObject.getOwner();
		int index = joiningStrategy.specifiedJoinColumnsSize();

		JoinColumn joinColumn = joiningStrategy.addSpecifiedJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);
		this.joinColumnsComposite.setSelectedJoinColumn(joinColumn);
	}


	private void editJoinColumn(JoinColumn joinColumn) {

		JoinColumnInJoiningStrategyDialog dialog =
			new JoinColumnInJoiningStrategyDialog(
				getShell(),
				this.selectedJoinColumnJoiningStrategyHolder.getValue(),
				joinColumn
			);

		dialog.openDialog(buildEditJoinColumnPostExecution());
	}
	
	private PostExecution<JoinColumnInJoiningStrategyDialog> buildEditJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInJoiningStrategyDialog>() {
			public void execute(JoinColumnInJoiningStrategyDialog dialog) {
				if (dialog.wasConfirmed()) {
					editJoinColumn(dialog.getSubject());
				}
			}
		};
	}

	private void editJoinColumn(JoinColumnInJoiningStrategyStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}
	
	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);
		this.joinColumnsComposite.enableWidgets(enabled);
	}

	private class JoinColumnsProvider implements JoinColumnsEditor<JoinColumnJoiningStrategy> {

		public void addJoinColumn(JoinColumnJoiningStrategy subject) {
			AssociationOverrideComposite.this.addJoinColumn(subject);
		}

		public JoinColumn getDefaultJoinColumn(JoinColumnJoiningStrategy subject) {
			//association overrides have no default join column
			return null;
		}

		public String getDefaultPropertyName() {
			return JoinColumnJoiningStrategy.DEFAULT_JOIN_COLUMN_PROPERTY;
		}

		public void editJoinColumn(JoinColumnJoiningStrategy subject, JoinColumn joinColumn) {
			AssociationOverrideComposite.this.editJoinColumn(joinColumn);
		}

		public boolean hasSpecifiedJoinColumns(JoinColumnJoiningStrategy subject) {
			return subject.hasSpecifiedJoinColumns();
		}

		public void removeJoinColumns(JoinColumnJoiningStrategy subject, int[] selectedIndices) {
			for (int index = selectedIndices.length; --index >= 0; ) {
				subject.removeSpecifiedJoinColumn(selectedIndices[index]);
			}
		}

		public ListIterator<JoinColumn> specifiedJoinColumns(JoinColumnJoiningStrategy subject) {
			return subject.specifiedJoinColumns();
		}

		public int specifiedJoinColumnsSize(JoinColumnJoiningStrategy subject) {
			return subject.specifiedJoinColumnsSize();
		}

		public String getSpecifiedJoinColumnsListPropertyName() {
			return JoinColumnJoiningStrategy.SPECIFIED_JOIN_COLUMNS_LIST;
		}
	}
}
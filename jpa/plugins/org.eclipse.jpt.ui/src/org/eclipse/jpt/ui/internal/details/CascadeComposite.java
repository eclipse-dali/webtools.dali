/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | - Cascade --------------------------------------------------------------- |
 * | |                                                                       | |
 * | | x All       x Persist   x Merge     x Remove    x Refresh             | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see Cascade
 * @see RelationshipMapping
 * @see ManyToManyMappingComposite - A container of this pane
 * @see ManyToOneMappingComposite - A container of this pane
 * @see OneToManyMappingComposite - A container of this pane
 * @see OneToOneMappingComposite - A container of this pane
 *
 * @version 2.0
 * @since 1.0
 */
public class CascadeComposite extends Pane<Cascade>
{
	/**
	 * Creates a new <code>CascadeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject <code>ICascade</code>
	 * @param parent The parent container
	 */
	public CascadeComposite(Pane<? extends RelationshipMapping> parentPane,
	                        PropertyValueModel<? extends Cascade> subjectHolder,
		                     Composite parent) {

		super(parentPane, subjectHolder, parent, false);
	}

	/**
	 * Creates a new <code>ColumnComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>ICascade</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public CascadeComposite(PropertyValueModel<? extends Cascade> subjectHolder,
		                     Composite parent,
		                     WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private WritablePropertyValueModel<Boolean> buildCascadeTypeAllHolder() {
		return new PropertyAspectAdapter<Cascade, Boolean>(getSubjectHolder(), Cascade.ALL_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.isAll();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setAll(value);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildCascadeTypeMergeHolder() {
		return new PropertyAspectAdapter<Cascade, Boolean>(getSubjectHolder(), Cascade.MERGE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.isMerge();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setMerge(value);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildCascadeTypePersistHolder() {
		return new PropertyAspectAdapter<Cascade, Boolean>(getSubjectHolder(), Cascade.PERSIST_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.isPersist();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setPersist(value);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildCascadeTypeRefreshHolder() {
		return new PropertyAspectAdapter<Cascade, Boolean>(getSubjectHolder(), Cascade.REFRESH_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.isRefresh();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setRefresh(value);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildCascadeTypeRemoveHolder() {
		return new PropertyAspectAdapter<Cascade, Boolean>(getSubjectHolder(), Cascade.REMOVE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.isRemove();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setRemove(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Cascade group
		Group cascadeGroup = addTitledGroup(
			container,
			JptUiDetailsMessages.CascadeComposite_cascadeTitle
		);

		// Container of the check boxes
		container = addSubPane(cascadeGroup, 5, 8, 0, 0, 0);

		// All check box
		addCheckBox(
			container,
			JptUiDetailsMessages.CascadeComposite_all,
			buildCascadeTypeAllHolder(),
			null
		);

		// Persist check box
		addCheckBox(
			container,
			JptUiDetailsMessages.CascadeComposite_persist,
			buildCascadeTypePersistHolder(),
			null
		);

		// Merge check box
		addCheckBox(
			container,
			JptUiDetailsMessages.CascadeComposite_merge,
			buildCascadeTypeMergeHolder(),
			null
		);

		// Remove check box
		addCheckBox(
			container,
			JptUiDetailsMessages.CascadeComposite_remove,
			buildCascadeTypeRemoveHolder(),
			null
		);

		// Refresh check box
		addCheckBox(
			container,
			JptUiDetailsMessages.CascadeComposite_refresh,
			buildCascadeTypeRefreshHolder(),
			null
		);
	}
}
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

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.context.RelationshipMapping;
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
public class CascadeComposite<T extends Cascade> extends Pane<T>
{
	/**
	 * Creates a new <code>CascadeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject <code>ICascade</code>
	 * @param parent The parent container
	 */
	public CascadeComposite(
			Pane<? extends RelationshipMapping> parentPane,
	        PropertyValueModel<T> subjectHolder,
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
	public CascadeComposite(
			PropertyValueModel<T> subjectHolder,
			Composite parent,
		    WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		// Cascade group
		Group cascadeGroup = addCascadeGroup(container);
		
		// Container of the check boxes
		container = addSubPane(cascadeGroup, 5, 8, 0, 0, 0);
		
		addAllCheckBox(container);
		addPersistCheckBox(container);
		addMergeCheckBox(container);
		addRemoveCheckBox(container);
		addRefreshCheckBox(container);
	}
	
	protected void addAllCheckBox(Composite container) {
		// All check box
		addCheckBox(
				container,
				JptUiDetailsMessages.CascadeComposite_all,
				buildCascadeTypeAllHolder(),
				null);
	}
	
	protected void addPersistCheckBox(Composite container) {
		// Persist check box
		addCheckBox(
				container,
				JptUiDetailsMessages.CascadeComposite_persist,
				buildCascadeTypePersistHolder(),
				null);
	}
	
	protected void addMergeCheckBox(Composite container) {
		// Merge check box
		addCheckBox(
				container,
				JptUiDetailsMessages.CascadeComposite_merge,
				buildCascadeTypeMergeHolder(),
				null);
	}
	
	protected void addRemoveCheckBox(Composite container) {
		// Remove check box
		addCheckBox(
				container,
				JptUiDetailsMessages.CascadeComposite_remove,
				buildCascadeTypeRemoveHolder(),
				null);
	}
	
	protected void addRefreshCheckBox(Composite container) {
		// Refresh check box
		addCheckBox(
				container,
				JptUiDetailsMessages.CascadeComposite_refresh,
				buildCascadeTypeRefreshHolder(),
				null);
	}
	
	protected Group addCascadeGroup(Composite container) {
		return addTitledGroup(
				container,
				JptUiDetailsMessages.CascadeComposite_cascadeTitle);
	}
	
	protected WritablePropertyValueModel<Boolean> buildCascadeTypeAllHolder() {
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
	
	protected WritablePropertyValueModel<Boolean> buildCascadeTypeMergeHolder() {
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
	
	protected WritablePropertyValueModel<Boolean> buildCascadeTypePersistHolder() {
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
	
	protected WritablePropertyValueModel<Boolean> buildCascadeTypeRefreshHolder() {
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
	
	protected WritablePropertyValueModel<Boolean> buildCascadeTypeRemoveHolder() {
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
}
/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Cascade;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.swt.widgets.Composite;

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
 * @see ManyToManyMappingComposite
 * @see ManyToOneMappingComposite
 * @see OneToManyMappingComposite
 * @see OneToOneMappingComposite
 */
public class CascadeComposite
	extends Pane<Cascade>
{
	public CascadeComposite(
		Pane<? extends RelationshipMapping> parentPane,
		PropertyValueModel<? extends Cascade> cascadeModel,
		Composite parent
	) {
		super(parentPane, cascadeModel, parent);
	}

	@Override
	protected Composite addComposite(Composite container) {
		return addTitledGroup(
			container,
			JptUiDetailsMessages.CascadeComposite_cascadeTitle,
			5,
			null);
	}
	
	@Override
	protected void initializeLayout(Composite container) {		
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
		
	protected ModifiablePropertyValueModel<Boolean> buildCascadeTypeAllHolder() {
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
	
	protected ModifiablePropertyValueModel<Boolean> buildCascadeTypeMergeHolder() {
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
	
	protected ModifiablePropertyValueModel<Boolean> buildCascadeTypePersistHolder() {
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
	
	protected ModifiablePropertyValueModel<Boolean> buildCascadeTypeRefreshHolder() {
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
	
	protected ModifiablePropertyValueModel<Boolean> buildCascadeTypeRemoveHolder() {
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
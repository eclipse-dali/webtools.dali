/*******************************************************************************
* Copyright (c) 2011, 2016 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.closure.BooleanClosure;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListValueModelTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkSpecifiedTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkTenantDiscriminatorColumnsComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkTenantDiscriminatorColumnsComposite.TenantDiscriminatorColumnsEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class EclipseLinkEntityMappingsTenantDiscriminatorColumnsComposite extends Pane<EntityMappings> {

	public EclipseLinkEntityMappingsTenantDiscriminatorColumnsComposite(Pane<? extends EntityMappings> parentPane,
	                           Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Tenant discriminator columns group pane
		Group tenantDiscriminatorColumnGroupPane = addTitledGroup(
			container,
			JptJpaEclipseLinkUiDetailsMessages.TENANT_DISCRIMINATOR_COLUMNS_GROUP_LABEL
		);

		// Override Default Tenant Discriminator Columns check box
		addCheckBox(
			tenantDiscriminatorColumnGroupPane,
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_MULTITENANCY_OVERRIDE_DEFAULT_TENANT_DISCRIMINATOR_COLUMNS,
			buildOverrideDefaultTenantDiscriminatorColumnModel(),
			null
		);

		this.buildTenantDiscriminatorColumnsComposite(tenantDiscriminatorColumnGroupPane);
	}

	protected EclipseLinkTenantDiscriminatorColumnsComposite<EntityMappings>  buildTenantDiscriminatorColumnsComposite(Composite container) {
		return new EclipseLinkTenantDiscriminatorColumnsComposite<>(
				this,
				this.getSubjectHolder(),
				this.buildSpecifiedTenantDiscriminatorColumnsPaneEnabledModel(),
				container,
				this.buildTenantDiscriminatorColumnsEditor()
			);
	}


	protected TenantDiscriminatorColumnsEditor<EntityMappings> buildTenantDiscriminatorColumnsEditor() {
		return new TenantDiscriminatorColumnsProvider();
	}

	class TenantDiscriminatorColumnsProvider implements TenantDiscriminatorColumnsEditor<EntityMappings> {

		public EclipseLinkTenantDiscriminatorColumn2_3 addTenantDiscriminatorColumn(EntityMappings subject) {
			EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 column = ((EclipseLinkEntityMappings) subject).addSpecifiedTenantDiscriminatorColumn();
			column.setSpecifiedName(EclipseLinkTenantDiscriminatorColumn2_3.DEFAULT_NAME);
			return column;
		}

		public ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getDefaultTenantDiscriminatorColumns(EntityMappings subject) {
			return IterableTools.upCast(((EclipseLinkEntityMappings) subject).getDefaultTenantDiscriminatorColumns());
		}

		public int getDefaultTenantDiscriminatorColumnsSize(EntityMappings subject) {
			return ((EclipseLinkEntityMappings) subject).getDefaultTenantDiscriminatorColumnsSize();
		}

		public String getDefaultTenantDiscriminatorsListPropertyName() {
			return EclipseLinkEntityMappings.DEFAULT_TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}

		public ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getSpecifiedTenantDiscriminatorColumns(EntityMappings subject) {
			return IterableTools.upCast(((EclipseLinkEntityMappings) subject).getSpecifiedTenantDiscriminatorColumns());
		}

		public int getSpecifiedTenantDiscriminatorColumnsSize(EntityMappings subject) {
			return ((EclipseLinkEntityMappings) subject).getSpecifiedTenantDiscriminatorColumnsSize();
		}

		public String getSpecifiedTenantDiscriminatorsListPropertyName() {
			return EclipseLinkEntityMappings.SPECIFIED_TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}

		public boolean hasSpecifiedTenantDiscriminatorColumns(EntityMappings subject) {
			return ((EclipseLinkEntityMappings) subject).hasSpecifiedTenantDiscriminatorColumns();
		}

		public void removeTenantDiscriminatorColumn(EntityMappings subject, EclipseLinkTenantDiscriminatorColumn2_3 column) {
			((EclipseLinkEntityMappings) subject).removeSpecifiedTenantDiscriminatorColumn((EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3) column);
		}
	}

	private ModifiablePropertyValueModel<Boolean> buildOverrideDefaultTenantDiscriminatorColumnModel() {
		return ListValueModelTools.isNotEmptyModifiablePropertyValueModel(this.buildSpecifiedTenantDiscriminatorColumnsListModel(), new OverrideDefaultTenantDiscriminatorColumnModelSetValueClosure());
	}

	ListValueModel<EclipseLinkTenantDiscriminatorColumn2_3> buildSpecifiedTenantDiscriminatorColumnsListModel() {
		return new ListAspectAdapter<EntityMappings, EclipseLinkTenantDiscriminatorColumn2_3>(
				getSubjectHolder(), EclipseLinkEntityMappings.SPECIFIED_TENANT_DISCRIMINATOR_COLUMNS_LIST) {
			@Override
			protected ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getListIterable() {
				return IterableTools.upCast(((EclipseLinkEntityMappings) this.subject).getSpecifiedTenantDiscriminatorColumns());
			}

			@Override
			protected int size_() {
				return ((EclipseLinkEntityMappings) this.subject).getSpecifiedTenantDiscriminatorColumnsSize();
			}
		};
	}

	class OverrideDefaultTenantDiscriminatorColumnModelSetValueClosure
		implements BooleanClosure.Adapter
	{
		public void execute(boolean value) {
			updateTenantDiscriminatorColumns(value);
		}
	}

	void updateTenantDiscriminatorColumns(boolean selected) {
		if (isPopulating()) {
			return;
		}

		setPopulating(true);

		try {
			EclipseLinkEntityMappings subject = (EclipseLinkEntityMappings) getSubject();

			if (selected) {
				EclipseLinkSpecifiedTenantDiscriminatorColumn2_3 newTenantDiscriminatorColumn = subject.addSpecifiedTenantDiscriminatorColumn();
				newTenantDiscriminatorColumn.setSpecifiedName(EclipseLinkTenantDiscriminatorColumn2_3.DEFAULT_NAME);
			}
			// Remove all the specified tenant discriminator columns
			else {
				for (int index = subject.getSpecifiedTenantDiscriminatorColumnsSize(); --index >= 0; ) {
					subject.removeSpecifiedTenantDiscriminatorColumn(index);
				}
			}
		}
		finally {
			setPopulating(false);
		}
	}

	protected PropertyValueModel<Boolean> buildSpecifiedTenantDiscriminatorColumnsPaneEnabledModel() {
		return this.buildSpecifiedTenantDiscriminatorColumnsIsNotEmptyModel();
	}

	protected PropertyValueModel<Boolean> buildSpecifiedTenantDiscriminatorColumnsIsNotEmptyModel() {
		return ListValueModelTools.isNotEmptyPropertyValueModel(this.buildSpecifiedTenantDiscriminatorColumnsModel());
	}

	@SuppressWarnings("unchecked")
	protected ListValueModel<EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3> buildSpecifiedTenantDiscriminatorColumnsModel() {
		return new ListAspectAdapter<EclipseLinkEntityMappings, EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3>((PropertyValueModel<EclipseLinkEntityMappings>) this.getSubjectHolder(), EclipseLinkEntityMappings.SPECIFIED_TENANT_DISCRIMINATOR_COLUMNS_LIST) {
			@Override
			protected ListIterable<EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3> getListIterable() {
				return IterableTools.upCast(this.subject.getSpecifiedTenantDiscriminatorColumns());
			}
			@Override
			protected int size_() {
				return this.subject.getSpecifiedTenantDiscriminatorColumnsSize();
			}
		};
	}
}

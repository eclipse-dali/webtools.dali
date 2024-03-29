/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.persistence;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.persistence.JptJpaUiPersistenceMessages;
import org.eclipse.swt.widgets.Composite;

public class PersistenceUnitGeneralComposite extends Pane<PersistenceUnit>
{
	/**
	 * Creates a new <code>PersistenceUnitMappedClassesComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public PersistenceUnitGeneralComposite(Pane<? extends PersistenceUnit> parentPane,
	                                             Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return this.addSubPane(parent, 2, 0, 0, 0, 0);//2 columns
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Name widgets
		this.addLabel(container, JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_GENERAL_COMPOSITE_NAME);
		this.addText(container, this.buildPersistenceUnitNameHolder(), this.getHelpID());

		// Persistence Provider widgets
		this.addLabel(container, JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_GENERAL_COMPOSITE_PERSISTENCE_PROVIDER);
		this.addText(container, this.buildPersistenceProviderHolder(), this.getHelpID());

		// Description widgets
		this.addLabel(container, JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_GENERAL_COMPOSITE_DESCRIPTION);
		this.addText(container, this.buildPersistenceUnitDescriptionHolder(), this.getHelpID());
	}

	protected String getHelpID() {
		return JpaHelpContextIds.PERSISTENCE_XML_GENERAL;
	}

	private ModifiablePropertyValueModel<String> buildPersistenceProviderHolder() {
		return new PropertyAspectAdapter<PersistenceUnit, String>(getSubjectHolder(), PersistenceUnit.PROVIDER_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getProvider();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setProvider(value);
			}
		};
	}

	private ModifiablePropertyValueModel<String> buildPersistenceUnitNameHolder() {
		return new PropertyAspectAdapter<PersistenceUnit, String>(getSubjectHolder(), PersistenceUnit.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getName();
			}

			@Override
			protected void setValue_(String value) {
				subject.setName(value);
			}
		};
	}

	private ModifiablePropertyValueModel<String> buildPersistenceUnitDescriptionHolder() {
		return new PropertyAspectAdapter<PersistenceUnit, String>(getSubjectHolder(), PersistenceUnit.DESCRIPTION_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getDescription();
			}
			
			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setDescription(value);
			}
		};
	}
}
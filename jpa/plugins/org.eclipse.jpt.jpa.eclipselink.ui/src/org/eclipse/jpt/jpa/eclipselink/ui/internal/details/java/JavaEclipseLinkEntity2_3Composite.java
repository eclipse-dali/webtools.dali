/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkMultitenancy2_3;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkMultitenancyComposite;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for an EclipseLink 2.3 Java entity.
 *
 * @version 3.1
 * @since 3.1
 */
public class JavaEclipseLinkEntity2_3Composite
	extends AbstractJavaEclipseLinkEntity2_xComposite
{
	public JavaEclipseLinkEntity2_3Composite(
			PropertyValueModel<? extends JavaEntity> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.initializeEntityCollapsibleSection(container);
		this.initializeCachingCollapsibleSectionPane(container);
		this.initializeQueriesCollapsibleSection(container);
		this.initializeInheritanceCollapsibleSection(container);
		this.initializeAttributeOverridesCollapsibleSection(container);
		this.initializeMultitenancyCollapsibleSectionPane(container);
		this.initializeGeneratorsCollapsibleSection(container);
		this.initializeConvertersCollapsibleSection(container);
		this.initializeSecondaryTablesCollapsibleSection(container);
		this.initializeAdvancedCollapsibleSection(container);
	}

	protected void initializeMultitenancyCollapsibleSectionPane(Composite container) {
		container = addCollapsibleSection(
				container,
				EclipseLinkUiDetailsMessages.EclipseLinkTypeMappingComposite_multitenancy);
		this.initializeMultitenancySection(container, buildMultitenancyHolder());
	}

	protected void initializeMultitenancySection(Composite container, PropertyValueModel<JavaEclipseLinkMultitenancy2_3> multitenancyHolder) {
		new EclipseLinkMultitenancyComposite(this, multitenancyHolder, container);
	}

	private PropertyAspectAdapter<JavaEntity, JavaEclipseLinkMultitenancy2_3> buildMultitenancyHolder() {
		return new PropertyAspectAdapter<JavaEntity, JavaEclipseLinkMultitenancy2_3>(getSubjectHolder()) {
			@Override
			protected JavaEclipseLinkMultitenancy2_3 buildValue_() {
				return ((JavaEclipseLinkEntity) this.subject).getMultitenancy();
			}
		};
	}
}

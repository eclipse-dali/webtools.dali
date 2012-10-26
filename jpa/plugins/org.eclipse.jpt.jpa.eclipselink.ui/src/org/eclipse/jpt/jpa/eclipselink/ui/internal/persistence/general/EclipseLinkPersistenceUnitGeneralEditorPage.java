/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.general;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.internal.persistence.PersistenceUnitGeneralEditorPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class EclipseLinkPersistenceUnitGeneralEditorPage
	extends PersistenceUnitGeneralEditorPage
{

	public EclipseLinkPersistenceUnitGeneralEditorPage(
			PropertyValueModel<PersistenceUnit> subjectHolder,
            Composite parent,
            WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected Control buildMappingFilesComposite(Composite parent) {
		return new EclipseLinkPersistenceUnitMappingFilesComposite(this, parent).getControl();
	}

	@Override
	protected Control buildJarFilesComposite(Composite parent) {
		return new EclipseLinkPersistenceUnitJarFilesComposite(this, parent).getControl();
	}
}
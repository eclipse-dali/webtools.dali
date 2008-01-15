/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.details;

import java.util.ListIterator;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.ui.internal.IJpaPlatformUi;
import org.eclipse.jpt.ui.internal.details.PersistentTypeDetailsPage;
import org.eclipse.jpt.ui.internal.platform.JpaPlatformUiRegistry;
import org.eclipse.jpt.ui.internal.platform.base.BaseJpaPlatformUi;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class JavaPersistentTypeDetailsPage extends
		PersistentTypeDetailsPage<IJavaPersistentType>
{
	public JavaPersistentTypeDetailsPage(PropertyValueModel<? extends IJavaPersistentType> subjectHolder,
	                                     Composite parent,
	                                     TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	protected IJpaPlatformUi jpaPlatformUi() {
		String platformId = subject().jpaProject().jpaPlatform().getId();
		return JpaPlatformUiRegistry.instance().jpaPlatform(platformId);
	}

	@Override
	protected ListIterator<ITypeMappingUiProvider<? extends ITypeMapping>> typeMappingUiProviders() {
		// TODO
		return ((BaseJpaPlatformUi) jpaPlatformUi()).javaTypeMappingUiProviders();
	}

	@Override
	protected void initializeLayout(Composite container) {
//		GridLayout gridLayout = new GridLayout();
//		gridLayout.numColumns = 2;
//		composite.setLayout(gridLayout);

		// Entity Type widgets
		buildLabeledComposite(
			container,
			buildTypeMappingLabel(container),
			buildTypeMappingCombo(container).getControl()
		);
//		buildTypeMappingLabel(container);
//
//		ComboViewer typeMappingCombo = buildTypeMappingCombo(container);
//		GridData gridData = new GridData();
//		gridData.horizontalAlignment = GridData.FILL;
//		gridData.grabExcessHorizontalSpace = true;
//		typeMappingCombo.getCCombo().setLayoutData(gridData);

		PageBook typeMappingPageBook = buildTypeMappingPageBook(container);

		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 1;
		typeMappingPageBook.setLayoutData(gridData);
	}
}
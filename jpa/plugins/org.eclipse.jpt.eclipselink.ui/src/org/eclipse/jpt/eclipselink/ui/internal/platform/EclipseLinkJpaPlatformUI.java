/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.platform;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.eclipselink.ui.EclipseLinkJpaUiFactory;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkJpaUiFactoryImpl;
import org.eclipse.jpt.eclipselink.ui.internal.ddlgen.EclipseLinkDDLGeneratorUi;
import org.eclipse.jpt.eclipselink.ui.internal.persistencexml.details.PersistenceDetailsProvider;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.platform.base.BaseJpaPlatformUi;
import org.eclipse.jpt.ui.navigator.JpaNavigatorProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * EclipseLinkPlatformUI
 */
public class EclipseLinkJpaPlatformUI extends BaseJpaPlatformUi
{
	public EclipseLinkJpaPlatformUI() {
		super();
	}

	// ********* navigator content *********
	public JpaNavigatorProvider buildNavigatorProvider() {
		return new EclipseLinkNavigatorProvider();
	}

	@Override
	protected EclipseLinkJpaUiFactory createJpaUiFactory() {
		return new EclipseLinkJpaUiFactoryImpl();
	}

	public void generateDDL(JpaProject project, IStructuredSelection selection) {

		String projectLocation = project.getProject().getLocation().toString();
		
		EclipseLinkDDLGeneratorUi.generate(project, projectLocation, selection);
	}

	protected void displayNotSupportedMessage(String title, String message) {
		String formattedMessage = MessageFormat.format(message, message);
		Shell currentShell = Display.getCurrent().getActiveShell();
		MessageDialog.openInformation(currentShell, title, formattedMessage);
	}

	@Override
	protected void addDetailsProvidersTo(Collection<JpaDetailsProvider> providers) {
		super.addDetailsProvidersTo(providers);
		providers.add(new PersistenceDetailsProvider());
	}

	@Override
	protected void addJavaAttributeMappingUiProvidersTo(
							List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		super.addJavaAttributeMappingUiProvidersTo(providers);
	}

	@Override
	protected void addJavaTypeMappingUiProvidersTo(
							List<TypeMappingUiProvider<? extends TypeMapping>> providers) {
		super.addJavaTypeMappingUiProvidersTo(providers);
	}
}

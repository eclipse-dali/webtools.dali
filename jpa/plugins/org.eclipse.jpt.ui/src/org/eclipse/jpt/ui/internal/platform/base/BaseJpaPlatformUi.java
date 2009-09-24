/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.platform.base;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.FileUiDefinition;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.JpaDetailsPage;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.details.MappingUiDefinition;
import org.eclipse.jpt.ui.navigator.JpaNavigatorProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class BaseJpaPlatformUi
	implements JpaPlatformUi
{
	private final JpaUiFactory jpaUiFactory;
	
	private final JpaNavigatorProvider navigatorProvider;
	
	private final JpaPlatformUiProvider platformUiProvider;

	private JpaStructureProvider persistenceStructureProvider;

	protected BaseJpaPlatformUi(
		JpaUiFactory jpaUiFactory,
		JpaNavigatorProvider navigatorProvider,
		JpaStructureProvider persistenceStructureProvider,
		JpaPlatformUiProvider platformUiProvider) {
		super();
		this.jpaUiFactory = jpaUiFactory;
		this.navigatorProvider = navigatorProvider;
		this.persistenceStructureProvider = persistenceStructureProvider;
		this.platformUiProvider = platformUiProvider;
	}


	// ********** factory **********

	public JpaUiFactory getJpaUiFactory() {
		return this.jpaUiFactory;
	}

	public JpaNavigatorProvider getNavigatorProvider() {
		return this.navigatorProvider;
	}
	
	
	// ********** details providers **********

	public JpaDetailsPage<? extends JpaStructureNode> buildJpaDetailsPage(Composite parent, JpaStructureNode structureNode, WidgetFactory widgetFactory) {
		JpaDetailsProvider jpaDetailsProvider = getDetailsProvider(structureNode);
		return jpaDetailsProvider == null ? null : jpaDetailsProvider.buildDetailsPage(parent, widgetFactory);
	}
	
	protected JpaDetailsProvider getDetailsProvider(JpaStructureNode structureNode) {
		return getDetailsProvider(structureNode.getContentType(), structureNode.getId());
	}
	
	protected JpaDetailsProvider getDetailsProvider(IContentType contentType, String id) {
		for (JpaDetailsProvider provider : CollectionTools.iterable(this.detailsProviders(id))) {
			if (provider.getContentType().isKindOf(contentType)) {
				return provider;
			}
		}
		if (contentType.getBaseType() != null) {
			return getDetailsProvider(contentType.getBaseType(), id);
		}
		return null;//return null, some structure nodes do not have a details page
	}
	
	protected Iterator<JpaDetailsProvider> detailsProviders(final String id) {
		return new FilteringIterator<JpaDetailsProvider, JpaDetailsProvider>(detailsProviders()) {
			@Override
			protected boolean accept(JpaDetailsProvider o) {
				return o.getId() == id;
			}
		};
	}

	protected ListIterator<JpaDetailsProvider> detailsProviders() {
		return this.platformUiProvider.detailsProviders();
	}


	// ********** mapping ui definitions **********

	public JpaComposite buildTypeMappingComposite(IContentType contentType, String key, Composite parent, PropertyValueModel<TypeMapping> mappingHolder, WidgetFactory widgetFactory) {
		FileUiDefinition fileUiDefinition = getFileUiDefinition(contentType);
		return fileUiDefinition.buildTypeMappingComposite(key, mappingHolder, parent, widgetFactory);
	}
	
	public JpaComposite buildAttributeMappingComposite(IContentType contentType, String key, Composite parent, PropertyValueModel<AttributeMapping> mappingHolder, WidgetFactory widgetFactory) {
		FileUiDefinition fileUiDefinition = getFileUiDefinition(contentType);
		return fileUiDefinition.buildAttributeMappingComposite(key, mappingHolder, parent, widgetFactory);
	}
	
	public DefaultMappingUiDefinition<? extends AttributeMapping> getDefaultAttributeMappingUiDefinition(IContentType contentType, String key) {
		return getFileUiDefinition(contentType).getDefaultAttributeMappingUiDefinition(key);
	}
	
	public Iterator<? extends MappingUiDefinition<? extends AttributeMapping>> attributeMappingUiDefinitions(IContentType contentType) {
		return getFileUiDefinition(contentType).attributeMappingUiDefinitions();
	}
	
	public DefaultMappingUiDefinition<? extends TypeMapping> getDefaultTypeMappingUiDefinition(IContentType contentType) {
		return getFileUiDefinition(contentType).getDefaultTypeMappingUiDefinition();
	}
	
	public Iterator<? extends MappingUiDefinition<? extends TypeMapping>> typeMappingUiDefinitions(IContentType contentType) {
		return getFileUiDefinition(contentType).typeMappingUiDefinitions();
	}


	
	// ********** structure providers **********

	public JpaStructureProvider getStructureProvider(JpaFile jpaFile) {
		return this.getStructureProvider(jpaFile.getContentType());
	}
	
	protected JpaStructureProvider getStructureProvider(IContentType contentType) {
	if (this.persistenceStructureProvider.getContentType().isKindOf(contentType)) {
			return this.persistenceStructureProvider;
		}
		return getFileUiDefinition(contentType).getStructureProvider();
	}
	
	
	// ********** mapping file ui definitions **********

	protected ListIterator<FileUiDefinition> fileUiDefinitions() {
		return this.platformUiProvider.fileUiDefinitions();
	}

	public FileUiDefinition getFileUiDefinition(IContentType contentType) {
		for (FileUiDefinition definition : CollectionTools.iterable(this.fileUiDefinitions())) {
			if (definition.getContentType().equals(contentType)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("No file ui definition for the contentType: " + contentType); //$NON-NLS-1$
	}
	

	// ********** entity generation **********

	public void generateEntities(JpaProject project, IStructuredSelection selection) {
		//EntitiesGenerator.generate(project, selection);
		EntitiesGenerator2.generate(project, selection);
	}


	// ********** convenience methods **********

	protected void displayMessage(String title, String message) {
	    Shell currentShell = Display.getCurrent().getActiveShell();
	    MessageDialog.openInformation(currentShell, title, message);
	}

}

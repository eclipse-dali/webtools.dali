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
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultTypeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaDetailsPage;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.navigator.JpaNavigatorProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationListIterator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class BaseJpaPlatformUi
	implements JpaPlatformUi
{
	private final JpaUiFactory jpaUiFactory;
	
	private final JpaNavigatorProvider navigatorProvider;
	
	private final JpaPlatformUiProvider[] platformUiProviders;

	private JpaStructureProvider persistenceStructureProvider;
	
	private JpaStructureProvider javaStructureProvider;

	protected BaseJpaPlatformUi(
		JpaUiFactory jpaUiFactory,
		JpaNavigatorProvider navigatorProvider,
		JpaStructureProvider persistenceStructureProvider, 
		JpaStructureProvider javaStructureProvider,
		JpaPlatformUiProvider... platformUiProviders) {
		super();
		this.jpaUiFactory = jpaUiFactory;
		this.navigatorProvider = navigatorProvider;
		this.persistenceStructureProvider = persistenceStructureProvider;
		this.javaStructureProvider = javaStructureProvider;
		this.platformUiProviders = platformUiProviders;
	}


	// ********** factory **********

	public JpaUiFactory getJpaUiFactory() {
		return this.jpaUiFactory;
	}

	public JpaNavigatorProvider getNavigatorProvider() {
		return this.navigatorProvider;
	}
	
	// ********** platform ui providers **********
	
	protected synchronized ListIterator<JpaPlatformUiProvider> platformUiProviders() {
		return new ArrayListIterator<JpaPlatformUiProvider>(this.platformUiProviders);
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
		return new CompositeListIterator<JpaDetailsProvider> ( 
			new TransformationListIterator<JpaPlatformUiProvider, ListIterator<JpaDetailsProvider>>(this.platformUiProviders()) {
				@Override
				protected ListIterator<JpaDetailsProvider> transform(JpaPlatformUiProvider platformProvider) {
					return platformProvider.detailsProviders();
				}
			}
		);
	}


	// ********** Java type mapping UI providers **********

	public Iterator<TypeMappingUiProvider<? extends TypeMapping>> typeMappingUiProviders(final IContentType contentType) {
		return new FilteringIterator<TypeMappingUiProvider<? extends TypeMapping>, TypeMappingUiProvider<? extends TypeMapping>>(typeMappingUiProviders()) {
			@Override
			protected boolean accept(TypeMappingUiProvider<? extends TypeMapping> provider) {
				return contentType.equals(provider.getContentType());
			}
		};
	}

	protected ListIterator<TypeMappingUiProvider<? extends TypeMapping>> typeMappingUiProviders() {
		return new CompositeListIterator<TypeMappingUiProvider<? extends TypeMapping>> ( 
			new TransformationListIterator<JpaPlatformUiProvider, ListIterator<TypeMappingUiProvider<? extends TypeMapping>>>(this.platformUiProviders()) {
				@Override
				protected ListIterator<TypeMappingUiProvider<? extends TypeMapping>> transform(JpaPlatformUiProvider platformProvider) {
					return platformProvider.typeMappingUiProviders();
				}
			}
		);
	}
	
	public DefaultTypeMappingUiProvider<? extends TypeMapping> getDefaultTypeMappingUiProvider(IContentType contentType) {
		for (DefaultTypeMappingUiProvider<? extends TypeMapping> provider : CollectionTools.iterable(defaultTypeMappingUiProviders())) {
			if (provider.getContentType().equals(contentType)) {
				return provider;
			}
		}
		return null;

	}
	
	public TypeMappingUiProvider<? extends TypeMapping> getTypeMappingUiProvider(String key, IContentType contentType) {
		for (TypeMappingUiProvider<? extends TypeMapping> provider : CollectionTools.iterable(typeMappingUiProviders(contentType))) {
			if (key == provider.getKey()) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Unsupported type mapping UI provider key: " + key); //$NON-NLS-1$
	}
	
	
	
	protected ListIterator<DefaultTypeMappingUiProvider<? extends TypeMapping>> defaultTypeMappingUiProviders() {
		return new CompositeListIterator<DefaultTypeMappingUiProvider<? extends TypeMapping>> ( 
			new TransformationListIterator<JpaPlatformUiProvider, ListIterator<DefaultTypeMappingUiProvider<? extends TypeMapping>>>(this.platformUiProviders()) {
				@Override
				protected ListIterator<DefaultTypeMappingUiProvider<? extends TypeMapping>> transform(JpaPlatformUiProvider platformProvider) {
					return platformProvider.defaultTypeMappingUiProviders();
				}
			}
		);
	}

	// ********** Java attribute mapping UI providers **********

	public Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> attributeMappingUiProviders(final IContentType contentType) {
		return new FilteringIterator<AttributeMappingUiProvider<? extends AttributeMapping>, AttributeMappingUiProvider<? extends AttributeMapping>>(attributeMappingUiProviders()) {
			@Override
			protected boolean accept(AttributeMappingUiProvider<? extends AttributeMapping> provider) {
				return contentType.equals(provider.getContentType());
			}
		};
	}

	protected ListIterator<AttributeMappingUiProvider<? extends AttributeMapping>> attributeMappingUiProviders() {
		return new CompositeListIterator<AttributeMappingUiProvider<? extends AttributeMapping>> ( 
			new TransformationListIterator<JpaPlatformUiProvider, ListIterator<AttributeMappingUiProvider<? extends AttributeMapping>>>(this.platformUiProviders()) {
				@Override
				protected ListIterator<AttributeMappingUiProvider<? extends AttributeMapping>> transform(JpaPlatformUiProvider platformProvider) {
					return platformProvider.attributeMappingUiProviders();
				}
			}
		);
	}

	// ********** default Java attribute mapping UI providers **********

	public Iterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> defaultAttributeMappingUiProviders(final IContentType contentType) {
		return new FilteringIterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>, DefaultAttributeMappingUiProvider<? extends AttributeMapping>>(defaultAttributeMappingUiProviders()) {
			@Override
			protected boolean accept(DefaultAttributeMappingUiProvider<? extends AttributeMapping> provider) {
				return provider.getContentType().equals(contentType);
			}
		};
	}
	
	public DefaultAttributeMappingUiProvider<? extends AttributeMapping> getDefaultAttributeMappingUiProvider(String key, IContentType contentType) {
		for (DefaultAttributeMappingUiProvider<?> provider : CollectionTools.iterable(defaultAttributeMappingUiProviders(contentType))) {
			if (key == provider.getDefaultKey()) {
				return provider;
			}
		}
		return null;
	}
	
	public AttributeMappingUiProvider<? extends AttributeMapping> getAttributeMappingUiProvider(String key, IContentType contentType) {
		for (AttributeMappingUiProvider<?> provider : CollectionTools.iterable(attributeMappingUiProviders(contentType))) {
			if (key == provider.getKey()) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Unsupported attribute mapping UI provider key: "); //$NON-NLS-1$
	}

	protected ListIterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> defaultAttributeMappingUiProviders() {
		return new CompositeListIterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> ( 
			new TransformationListIterator<JpaPlatformUiProvider, ListIterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>>>(this.platformUiProviders()) {
				@Override
				protected ListIterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> transform(JpaPlatformUiProvider platformProvider) {
					return platformProvider.defaultAttributeMappingUiProviders();
				}
			}
		);
	}


	// ********** structure providers **********

	public JpaStructureProvider getStructureProvider(JpaFile jpaFile) {
		return this.getStructureProvider(jpaFile.getContentType());
	}
	
	protected JpaStructureProvider getStructureProvider(IContentType contentType) {
		for (JpaStructureProvider provider : CollectionTools.iterable(this.structureProviders())) {
			if (provider.getContentType().isKindOf(contentType)) {
				return provider;
			}
		}
		if (contentType.getBaseType() != null) {
			return getStructureProvider(contentType.getBaseType());
		}
		throw new IllegalArgumentException("No structure provider for the contentType: " + contentType); //$NON-NLS-1$
	}

	protected ListIterator<JpaStructureProvider> structureProviders() {
		return 
			new CompositeListIterator<JpaStructureProvider> (this.persistenceStructureProvider,
				new CompositeListIterator<JpaStructureProvider> (this.javaStructureProvider,
					new CompositeListIterator<JpaStructureProvider> ( 
						new TransformationListIterator<JpaPlatformUiProvider, ListIterator<JpaStructureProvider>>(this.platformUiProviders()) {
							@Override
							protected ListIterator<JpaStructureProvider> transform(JpaPlatformUiProvider platformUiProvider) {
								return platformUiProvider.mappingFileStructureProviders();
							}
						}
					)
				)
			);
	}

	
	// ********** entity generation **********

	public void generateEntities(JpaProject project, IStructuredSelection selection) {
		EntitiesGenerator.generate(project, selection);
	}


	// ********** convenience methods **********

	protected void displayMessage(String title, String message) {
	    Shell currentShell = Display.getCurrent().getActiveShell();
	    MessageDialog.openInformation(currentShell, title, message);
	}

}

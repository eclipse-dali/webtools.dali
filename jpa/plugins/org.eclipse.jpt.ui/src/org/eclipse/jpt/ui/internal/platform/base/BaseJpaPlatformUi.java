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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaDetailsPage;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.DefaultBasicMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.DefaultEmbeddedMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaBasicMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaEmbeddableUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaEmbeddedIdMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaEmbeddedMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaEntityUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaIdMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaManyToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaManyToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaMappedSuperclassUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaOneToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaOneToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaTransientMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaVersionMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.NullAttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.NullTypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmBasicMappingUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmEmbeddableUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmEmbeddedIdMappingUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmEmbeddedMappingUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmEntityUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmIdMappingUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmManyToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmManyToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmMappedSuperclassUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmOneToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmOneToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmTransientMappingUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmVersionMappingUiProvider;
import org.eclipse.jpt.ui.navigator.JpaNavigatorProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
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

	private TypeMappingUiProvider<? extends TypeMapping>[] javaTypeMappingUiProviders;
	private AttributeMappingUiProvider<? extends AttributeMapping>[] javaAttributeMappingUiProviders;
	private DefaultAttributeMappingUiProvider<? extends AttributeMapping>[] defaultJavaAttributeMappingUiProviders;

	private TypeMappingUiProvider<? extends TypeMapping>[] ormTypeMappingUiProviders;
	private AttributeMappingUiProvider<? extends AttributeMapping>[] ormAttributeMappingUiProviders;
	private DefaultAttributeMappingUiProvider<? extends AttributeMapping>[] defaultOrmAttributeMappingUiProviders;

	/**
	 * zero-argument constructor
	 */
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

	public ListIterator<TypeMappingUiProvider<? extends TypeMapping>> javaTypeMappingUiProviders() {
		if (this.javaTypeMappingUiProviders == null) {
			this.javaTypeMappingUiProviders = this.buildJavaTypeMappingUiProviders();
		}
		return new ArrayListIterator<TypeMappingUiProvider<? extends TypeMapping>>(this.javaTypeMappingUiProviders);
	}

	protected TypeMappingUiProvider<? extends TypeMapping>[] buildJavaTypeMappingUiProviders() {
		ArrayList<TypeMappingUiProvider<? extends TypeMapping>> providers = new ArrayList<TypeMappingUiProvider<? extends TypeMapping>>();
		this.addJavaTypeMappingUiProvidersTo(providers);
		@SuppressWarnings("unchecked")
		TypeMappingUiProvider<? extends TypeMapping>[] providerArray = providers.toArray(new TypeMappingUiProvider[providers.size()]);
		return providerArray;
	}

	/**
	 * Override this to specify more or different type mapping ui providers
	 * The default includes the JPA spec-defined entity, mapped superclass, embeddable,
	 * and null (when the others don't apply)
	 */
	protected void addJavaTypeMappingUiProvidersTo(List<TypeMappingUiProvider<? extends TypeMapping>> providers) {
		providers.add(NullTypeMappingUiProvider.instance());
		providers.add(JavaEntityUiProvider.instance());
		providers.add(JavaMappedSuperclassUiProvider.instance());
		providers.add(JavaEmbeddableUiProvider.instance());
	}


	// ********** Java attribute mapping UI providers **********

	public ListIterator<AttributeMappingUiProvider<? extends AttributeMapping>> javaAttributeMappingUiProviders() {
		if (this.javaAttributeMappingUiProviders == null) {
			this.javaAttributeMappingUiProviders = this.buildJavaAttributeMappingUiProviders();
		}
		return new ArrayListIterator<AttributeMappingUiProvider<? extends AttributeMapping>>(this.javaAttributeMappingUiProviders);
	}

	protected AttributeMappingUiProvider<? extends AttributeMapping>[] buildJavaAttributeMappingUiProviders() {
		ArrayList<AttributeMappingUiProvider<? extends AttributeMapping>> providers = new ArrayList<AttributeMappingUiProvider<? extends AttributeMapping>>();
		this.addJavaAttributeMappingUiProvidersTo(providers);
		@SuppressWarnings("unchecked")
		AttributeMappingUiProvider<? extends AttributeMapping>[] providerArray = providers.toArray(new AttributeMappingUiProvider[providers.size()]);
		return providerArray;
	}

	/**
	 * Override this to specify more or different java attribute mapping ui providers.
	 * The default includes the JPA spec-defined basic, embedded, embeddedId, id,
	 * manyToMany, manyToOne, oneToMany, oneToOne, transient, and version
	 */
	protected void addJavaAttributeMappingUiProvidersTo(List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(JavaIdMappingUiProvider.instance());
		providers.add(JavaEmbeddedIdMappingUiProvider.instance());
		providers.add(JavaBasicMappingUiProvider.instance());
		providers.add(JavaVersionMappingUiProvider.instance());
		providers.add(JavaManyToOneMappingUiProvider.instance());
		providers.add(JavaOneToManyMappingUiProvider.instance());
		providers.add(JavaOneToOneMappingUiProvider.instance());
		providers.add(JavaManyToManyMappingUiProvider.instance());
		providers.add(JavaEmbeddedMappingUiProvider.instance());
		providers.add(JavaTransientMappingUiProvider.instance());
		providers.add(NullAttributeMappingUiProvider.instance());
	}


	// ********** default Java attribute mapping UI providers **********

	public ListIterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> defaultJavaAttributeMappingUiProviders() {
		if (this.defaultJavaAttributeMappingUiProviders == null) {
			this.defaultJavaAttributeMappingUiProviders = this.buildDefaultJavaAttributeMappingUiProviders();
		}
		return new ArrayListIterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>>(this.defaultJavaAttributeMappingUiProviders);
	}

	protected DefaultAttributeMappingUiProvider<? extends AttributeMapping>[] buildDefaultJavaAttributeMappingUiProviders() {
		ArrayList<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers = new ArrayList<DefaultAttributeMappingUiProvider<? extends AttributeMapping>>();
		this.addDefaultJavaAttributeMappingUiProvidersTo(providers);
		@SuppressWarnings("unchecked")
		DefaultAttributeMappingUiProvider<? extends AttributeMapping>[] providerArray = providers.toArray(new DefaultAttributeMappingUiProvider[providers.size()]);
		return providerArray;
	}

	/**
	 * Override this to specify more or different default java attribute mapping ui providers.
	 * The default includes the JPA spec-defined basic, embedded
	 */
	protected void addDefaultJavaAttributeMappingUiProvidersTo(List<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(DefaultBasicMappingUiProvider.instance());
		providers.add(DefaultEmbeddedMappingUiProvider.instance());
	}


	// ********** ORM type mapping UI providers **********

	public ListIterator<TypeMappingUiProvider<? extends TypeMapping>> ormTypeMappingUiProviders() {
		if (this.ormTypeMappingUiProviders == null) {
			this.ormTypeMappingUiProviders = this.buildOrmTypeMappingUiProviders();
		}
		return new ArrayListIterator<TypeMappingUiProvider<? extends TypeMapping>>(this.ormTypeMappingUiProviders);
	}

	protected TypeMappingUiProvider<? extends TypeMapping>[] buildOrmTypeMappingUiProviders() {
		ArrayList<TypeMappingUiProvider<? extends TypeMapping>> providers = new ArrayList<TypeMappingUiProvider<? extends TypeMapping>>();
		this.addOrmTypeMappingUiProvidersTo(providers);
		@SuppressWarnings("unchecked")
		TypeMappingUiProvider<? extends TypeMapping>[] providerArray = providers.toArray(new TypeMappingUiProvider[providers.size()]);
		return providerArray;
	}

	/**
	 * Override this to specify more or different ORM type mapping ui providers.
	 * The default includes the JPA spec-defined entity, mapped superclass,
	 * embeddable, and null (when the others don't apply).
	 */
	protected void addOrmTypeMappingUiProvidersTo(List<TypeMappingUiProvider<? extends TypeMapping>> providers) {
		providers.add(OrmEntityUiProvider.instance());
		providers.add(OrmMappedSuperclassUiProvider.instance());
		providers.add(OrmEmbeddableUiProvider.instance());
	}


	// ********** ORM attribute mapping UI providers **********

	public Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> ormAttributeMappingUiProviders() {
		if (this.ormAttributeMappingUiProviders == null) {
			this.ormAttributeMappingUiProviders = this.buildOrmAttributeMappingUiProviders();
		}
		return new ArrayListIterator<AttributeMappingUiProvider<? extends AttributeMapping>>(this.ormAttributeMappingUiProviders);
	}

	protected AttributeMappingUiProvider<? extends AttributeMapping>[] buildOrmAttributeMappingUiProviders() {
		ArrayList<AttributeMappingUiProvider<? extends AttributeMapping>> providers = new ArrayList<AttributeMappingUiProvider<? extends AttributeMapping>>();
		this.addOrmAttributeMappingUiProvidersTo(providers);
		@SuppressWarnings("unchecked")
		AttributeMappingUiProvider<? extends AttributeMapping>[] providerArray = providers.toArray(new AttributeMappingUiProvider[providers.size()]);
		return providerArray;
	}

	/**
	 * Override this to specify more or different ORM attribute mapping ui
	 * providers. The default includes the JPA spec-defined basic, embedded,
	 * embeddedId, id, manyToMany, manyToOne, oneToMany, oneToOne, transient,
	 * and version.
	 */
	protected void addOrmAttributeMappingUiProvidersTo(List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(OrmBasicMappingUiProvider.instance());
		providers.add(OrmEmbeddedMappingUiProvider.instance());
		providers.add(OrmEmbeddedIdMappingUiProvider.instance());
		providers.add(OrmIdMappingUiProvider.instance());
		providers.add(OrmManyToManyMappingUiProvider.instance());
		providers.add(OrmManyToOneMappingUiProvider.instance());
		providers.add(OrmOneToManyMappingUiProvider.instance());
		providers.add(OrmOneToOneMappingUiProvider.instance());
		providers.add(OrmTransientMappingUiProvider.instance());
		providers.add(OrmVersionMappingUiProvider.instance());
	}


	// ********** default ORM attribute mapping UI providers **********

	public Iterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> defaultOrmAttributeMappingUiProviders() {
		if (this.defaultOrmAttributeMappingUiProviders == null) {
			this.defaultOrmAttributeMappingUiProviders = this.buildDefaultOrmAttributeMappingUiProviders();
		}
		return new ArrayListIterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>>(this.defaultOrmAttributeMappingUiProviders);
	}

	protected DefaultAttributeMappingUiProvider<? extends AttributeMapping>[] buildDefaultOrmAttributeMappingUiProviders() {
		ArrayList<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers = new ArrayList<DefaultAttributeMappingUiProvider<? extends AttributeMapping>>();
		this.addDefaultOrmAttributeMappingUiProvidersTo(providers);
		@SuppressWarnings("unchecked")
		DefaultAttributeMappingUiProvider<? extends AttributeMapping>[] providerArray = providers.toArray(new DefaultAttributeMappingUiProvider[providers.size()]);
		return providerArray;
	}

	/**
	 * Override this to specify more or different default ORM attribute mapping
	 * ui providers. The default has no specific mappings.
	 */
	protected void addDefaultOrmAttributeMappingUiProvidersTo(@SuppressWarnings("unused") List<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		// nothing by default
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

	public Iterator<TypeMappingUiProvider<? extends TypeMapping>> typeMappingUiProviders(PersistentType type) {
		if (type instanceof JavaPersistentType) {
			return javaTypeMappingUiProviders();
		}
		if (type instanceof OrmPersistentType) {
			return ormTypeMappingUiProviders();
		}
		return EmptyIterator.instance();
	}
	
	public Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> attributeMappingUiProviders(PersistentAttribute attribute) {
		if (attribute instanceof JavaPersistentAttribute) {
			return javaAttributeMappingUiProviders();
		}
		if (attribute instanceof OrmPersistentAttribute) {
			return ormAttributeMappingUiProviders();
		}
		return EmptyIterator.instance();
	}

}

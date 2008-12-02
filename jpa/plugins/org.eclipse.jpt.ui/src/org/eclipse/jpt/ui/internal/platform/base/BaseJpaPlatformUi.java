/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.platform.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.JavaJpaFile;
import org.eclipse.jpt.core.internal.XmlJpaFile;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.DefaultBasicMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.DefaultEmbeddedMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaBasicMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaDetailsProvider;
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
import org.eclipse.jpt.ui.internal.orm.details.OrmDetailsProvider;
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
import org.eclipse.jpt.ui.internal.structure.JavaResourceModelStructureProvider;
import org.eclipse.jpt.ui.internal.structure.OrmResourceModelStructureProvider;
import org.eclipse.jpt.ui.internal.structure.PersistenceResourceModelStructureProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

public abstract class BaseJpaPlatformUi implements JpaPlatformUi
{
	// TODO: Transformed into a List for testing.  Must overhaul.
	private List<JpaDetailsProvider> detailsProviders;

	private List<TypeMappingUiProvider<? extends TypeMapping>> javaTypeMappingUiProviders;
	private List<AttributeMappingUiProvider<? extends AttributeMapping>> javaAttributeMappingUiProviders;
	private List<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> defaultJavaAttributeMappingUiProviders;

	private List<TypeMappingUiProvider<? extends TypeMapping>> ormTypeMappingUiProviders;
	private List<AttributeMappingUiProvider<? extends AttributeMapping>> ormAttributeMappingUiProviders;
	private List<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> defaultOrmAttributeMappingUiProviders;

	private JpaUiFactory jpaUiFactory;


	protected BaseJpaPlatformUi() {
		super();
		this.jpaUiFactory = createJpaUiFactory();
	}


	// **************** structure view content *********************************

	public JpaStructureProvider buildStructureProvider(JpaFile jpaFile) {
		String resourceType = jpaFile.getResourceType();

		if (resourceType == JpaFile.JAVA_RESOURCE_TYPE) {
			return new JavaResourceModelStructureProvider((JavaJpaFile) jpaFile);
		}
		if (resourceType == JpaFile.ORM_RESOURCE_TYPE) {
			return new OrmResourceModelStructureProvider((XmlJpaFile) jpaFile);
		}
		if (resourceType == JpaFile.PERSISTENCE_RESOURCE_TYPE) {
			return new PersistenceResourceModelStructureProvider((XmlJpaFile) jpaFile);
		}

		return null;
	}


	// ********** behavior **********

	protected abstract JpaUiFactory createJpaUiFactory();

	public JpaUiFactory getJpaUiFactory() {
		return this.jpaUiFactory;
	}
	
	protected List<JpaDetailsProvider> getDetailsProviders() {
		if (this.detailsProviders == null) {
			this.detailsProviders = new ArrayList<JpaDetailsProvider>();
			this.addDetailsProvidersTo(this.detailsProviders);
		}
		return Collections.unmodifiableList(this.detailsProviders);
	}
	
	public Iterator<JpaDetailsProvider> detailsProviders() {
		return getDetailsProviders().iterator();
	}

	/**
	 * Override this to specify more or different details providers.
	 * The default includes the JPA spec-defined java and orm.xml
	 */
	protected void addDetailsProvidersTo(Collection<JpaDetailsProvider> providers) {
		providers.add(new JavaDetailsProvider());
		providers.add(new OrmDetailsProvider());
	}

	public JpaDetailsProvider getDetailsProvider(JpaStructureNode structureNode) {
		// TODO: To implement, this is written only for testing
		if (structureNode instanceof JavaJpaContextNode) {
			return getDetailsProviders().get(0);
		}
		return getDetailsProviders().get(1);
//		for (Iterator<IJpaDetailsProvider> i = this.detailsProviders(); i.hasNext(); ) {
//			IJpaDetailsProvider provider = i.next();
//			if (provider.fileContentType().equals(fileContentType)) {
//				return provider;
//			}
//		}
//		return null;
	}
	
	public Iterator<TypeMappingUiProvider<? extends TypeMapping>> typeMappingUiProviders(PersistentType type) {
		if (type instanceof JavaPersistentType) {
			return javaTypeMappingUiProviders();
		}
		else if (type instanceof OrmPersistentType) {
			return ormTypeMappingUiProviders();
		}
		else {
			return EmptyIterator.instance();
		}
	}
	
	public Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> attributeMappingUiProviders(PersistentAttribute attribute) {
		if (attribute instanceof JavaPersistentAttribute) {
			return javaAttributeMappingUiProviders();
		}
		else if (attribute instanceof OrmPersistentAttribute) {
			return ormAttributeMappingUiProviders();
		}
		else {
			return EmptyIterator.instance();
		}
	}

	public ListIterator<TypeMappingUiProvider<? extends TypeMapping>> javaTypeMappingUiProviders() {
		if (this.javaTypeMappingUiProviders == null) {
			this.javaTypeMappingUiProviders = new ArrayList<TypeMappingUiProvider<? extends TypeMapping>>();
			this.addJavaTypeMappingUiProvidersTo(this.javaTypeMappingUiProviders);
		}
		return new CloneListIterator<TypeMappingUiProvider<? extends TypeMapping>>(
			this.javaTypeMappingUiProviders
		);
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

	public ListIterator<AttributeMappingUiProvider<? extends AttributeMapping>> javaAttributeMappingUiProviders() {
		if (this.javaAttributeMappingUiProviders == null) {
			this.javaAttributeMappingUiProviders = new ArrayList<AttributeMappingUiProvider<? extends AttributeMapping>>();
			this.addJavaAttributeMappingUiProvidersTo(this.javaAttributeMappingUiProviders);
		}

		return new CloneListIterator<AttributeMappingUiProvider<? extends AttributeMapping>>(
			this.javaAttributeMappingUiProviders
		);
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
		providers.add(JavaEmbeddedMappingUiProvider.instance());
		providers.add(JavaOneToOneMappingUiProvider.instance());
		providers.add(JavaManyToManyMappingUiProvider.instance());
		providers.add(JavaTransientMappingUiProvider.instance());
		providers.add(NullAttributeMappingUiProvider.instance());
	}

	public ListIterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> defaultJavaAttributeMappingUiProviders() {
		if (this.defaultJavaAttributeMappingUiProviders == null) {
			this.defaultJavaAttributeMappingUiProviders = new ArrayList<DefaultAttributeMappingUiProvider<? extends AttributeMapping>>();
			this.addDefaultJavaAttributeMappingUiProvidersTo(this.defaultJavaAttributeMappingUiProviders);
		}

		return new CloneListIterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>>(
			this.defaultJavaAttributeMappingUiProviders
		);
	}

	/**
	 * Override this to specify more or different default java attribute mapping ui providers.
	 * The default includes the JPA spec-defined basic, embedded
	 */
	protected void addDefaultJavaAttributeMappingUiProvidersTo(List<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(DefaultBasicMappingUiProvider.instance());
		providers.add(DefaultEmbeddedMappingUiProvider.instance());
	}

	public void generateEntities(JpaProject project, IStructuredSelection selection) {
		EntitiesGenerator.generate(project, selection);
	}

	// *************** ORM mapping UI providers ***************
	
	public Iterator<TypeMappingUiProvider<? extends TypeMapping>> ormTypeMappingUiProviders() {
		if (this.ormTypeMappingUiProviders == null) {
			this.ormTypeMappingUiProviders = new ArrayList<TypeMappingUiProvider<? extends TypeMapping>>();
			this.addOrmTypeMappingUiProvidersTo(this.ormTypeMappingUiProviders);
		}

		return new CloneListIterator<TypeMappingUiProvider<? extends TypeMapping>>(
			this.ormTypeMappingUiProviders
		);
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
	
	public Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> ormAttributeMappingUiProviders() {
		if (this.ormAttributeMappingUiProviders == null) {
			this.ormAttributeMappingUiProviders = new ArrayList<AttributeMappingUiProvider<? extends AttributeMapping>>();
			this.addOrmAttributeMappingUiProvidersTo(this.ormAttributeMappingUiProviders);
		}

		return new CloneListIterator<AttributeMappingUiProvider<? extends AttributeMapping>>(
			this.ormAttributeMappingUiProviders
		);
	}

	/**
	 * Override this to specify more or different ORM attribute mapping ui
	 * providers. The default includes the JPA spec-defined basic, embedded,
	 * embeddedId, id, manyToMany, manyToOne, oneToMany, oneToOne, transient,
	 * and version.
	 */
	protected void addOrmAttributeMappingUiProvidersTo(List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(OrmIdMappingUiProvider.instance());
		providers.add(OrmEmbeddedIdMappingUiProvider.instance());
		providers.add(OrmBasicMappingUiProvider.instance());
		providers.add(OrmVersionMappingUiProvider.instance());
		providers.add(OrmManyToOneMappingUiProvider.instance());
		providers.add(OrmOneToManyMappingUiProvider.instance());
		providers.add(OrmOneToOneMappingUiProvider.instance());
		providers.add(OrmManyToManyMappingUiProvider.instance());
		providers.add(OrmEmbeddedMappingUiProvider.instance());
		providers.add(OrmTransientMappingUiProvider.instance());
	}
	
	public Iterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> defaultOrmAttributeMappingUiProviders() {
		if (this.defaultOrmAttributeMappingUiProviders == null) {
			this.defaultOrmAttributeMappingUiProviders = new ArrayList<DefaultAttributeMappingUiProvider<? extends AttributeMapping>>();
			this.addDefaultOrmAttributeMappingUiProvidersTo(this.defaultOrmAttributeMappingUiProviders);
		}

		return new CloneListIterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>>(
			this.defaultOrmAttributeMappingUiProviders
		);
	}

	/**
	 * Override this to specify more or different default ORM attribute mapping
	 * ui providers. The default has no specific mappings.
	 */
	protected void addDefaultOrmAttributeMappingUiProvidersTo(List<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers) {
	}
}
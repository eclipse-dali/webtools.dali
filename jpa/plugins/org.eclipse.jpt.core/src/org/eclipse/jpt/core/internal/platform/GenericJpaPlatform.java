/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.EntityGeneratorDatabaseAnnotationNameBuilder;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaResourceModel;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.MappingFileProvider;
import org.eclipse.jpt.core.context.java.DefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.ExtendedOrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.ExtendedOrmTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.JavaResourceModelProvider;
import org.eclipse.jpt.core.internal.OrmResourceModelProvider;
import org.eclipse.jpt.core.internal.PersistenceResourceModelProvider;
import org.eclipse.jpt.core.internal.context.GenericMappingFileProvider;
import org.eclipse.jpt.core.internal.context.java.JavaBasicMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddableProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddedIdMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddedMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEntityProvider;
import org.eclipse.jpt.core.internal.context.java.JavaIdMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaManyToManyMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaManyToOneMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaMappedSuperclassProvider;
import org.eclipse.jpt.core.internal.context.java.JavaNullAttributeMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaNullTypeMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaOneToManyMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaOneToOneMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaTransientMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaVersionMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmBasicMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmEmbeddableProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmEmbeddedIdMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmEmbeddedMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmEntityProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmIdMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmManyToManyMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmManyToOneMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmMappedSuperclassProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmNullAttributeMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmOneToManyMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmOneToOneMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmTransientMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmVersionMappingProvider;
import org.eclipse.jpt.core.internal.utility.PlatformTools;
import org.eclipse.jpt.core.internal.utility.jdt.DefaultAnnotationEditFormatter;
import org.eclipse.jpt.core.resource.orm.OrmXmlResource;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.db.ConnectionProfileFactory;
import org.eclipse.jpt.db.DatabaseFinder;
import org.eclipse.jpt.db.JptDbPlugin;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class GenericJpaPlatform
	implements JpaPlatform
{
	public static final String ID = "generic"; //$NON-NLS-1$

	private String id;

	protected final JpaFactory jpaFactory;

	private JpaAnnotationProvider annotationProvider;

	private JpaResourceModelProvider[] resourceModelProviders;

	private JavaTypeMappingProvider[] javaTypeMappingProviders;

	private JavaAttributeMappingProvider[] javaAttributeMappingProviders;

	private MappingFileProvider[] mappingFileProviders;

	private DefaultJavaAttributeMappingProvider[] defaultJavaAttributeMappingProviders;

	private ExtendedOrmTypeMappingProvider[] extendedOrmTypeMappingProviders;

	private OrmTypeMappingProvider[] ormTypeMappingProviders;

	private ExtendedOrmAttributeMappingProvider[] extendedOrmAttributeMappingProviders;

	private OrmAttributeMappingProvider[] ormAttributeMappingProviders;


	/**
	 * zero-argument constructor
	 */
	public GenericJpaPlatform() {
		super();
		this.jpaFactory = this.buildJpaFactory();
	}

	public String getId() {
		return this.id;
	}

	/**
	 * For INTERNAL use only!!
	 */
	public void setId(String id) {
		this.id = id;
	}


	// ********** factory **********

	public synchronized JpaFactory getJpaFactory() {
		return this.jpaFactory;
	}

	protected JpaFactory buildJpaFactory() {
		return new GenericJpaFactory();
	}


	// ********** JPA file/resource models **********

	public JpaFile buildJpaFile(JpaProject jpaProject, IFile file) {
		IContentType contentType = PlatformTools.getContentType(file);
		return (contentType == null) ? null : this.buildJpaFile(jpaProject, file, contentType);
	}

	protected JpaFile buildJpaFile(JpaProject jpaProject, IFile file, IContentType contentType) {
		JpaResourceModel resourceModel = this.buildResourceModel(jpaProject, file, contentType);
		return (resourceModel == null) ? null : this.jpaFactory.buildJpaFile(jpaProject, file, contentType, resourceModel);
	}

	protected JpaResourceModel buildResourceModel(JpaProject jpaProject, IFile file, IContentType contentType) {
		JpaResourceModelProvider provider = this.getResourceModelProvider(contentType);
		return (provider == null) ? null : provider.buildResourceModel(jpaProject, file);
	}

	/**
	 * Return null if we don't have a provider for the specified content type
	 * (since we don't have control over the possible content types).
	 * NB: We use "is kind of" to match content types; so the search may be
	 * order-sensitive.
	 * @see IContentType#isKindOf(IContentType)
	 */
	protected JpaResourceModelProvider getResourceModelProvider(IContentType contentType) {
		for (JpaResourceModelProvider provider : this.getResourceModelProviders()) {
			if (provider.getContentType().isKindOf(contentType)) {
				return provider;
			}
		}
		return null;
	}

	protected synchronized JpaResourceModelProvider[] getResourceModelProviders() {
		if (this.resourceModelProviders == null) {
			this.resourceModelProviders = this.buildResourceModelProviders();
		}
		return this.resourceModelProviders;
	}

	protected JpaResourceModelProvider[] buildResourceModelProviders() {
		ArrayList<JpaResourceModelProvider> providers = new ArrayList<JpaResourceModelProvider>();
		this.addResourceModelProvidersTo(providers);
		return providers.toArray(new JpaResourceModelProvider[providers.size()]);
	}

	/**
	 * Override this to specify more or different JPA resource model providers.
	 * The default includes support for Java, persistence.xml, and orm.xml
	 * files
	 */
	protected void addResourceModelProvidersTo(List<JpaResourceModelProvider> providers) {
		providers.add(JavaResourceModelProvider.instance());
		providers.add(PersistenceResourceModelProvider.instance());
		providers.add(OrmResourceModelProvider.instance());
	}


	// ********** Java annotations **********

	public synchronized JpaAnnotationProvider getAnnotationProvider() {
		if (this.annotationProvider == null) {
			this.annotationProvider = this.buildAnnotationProvider();
		}
		return this.annotationProvider;
	}

	protected JpaAnnotationProvider buildAnnotationProvider() {
		return new JpaAnnotationProviderImpl(GenericJpaAnnotationDefinitionProvider.instance());
	}


	public AnnotationEditFormatter getAnnotationEditFormatter() {
		return DefaultAnnotationEditFormatter.instance();
	}


	// ********** Java type mappings **********

	public JavaTypeMapping buildJavaTypeMappingFromMappingKey(String key, JavaPersistentType type) {
		return this.getJavaTypeMappingProviderForMappingKey(key).buildMapping(type, this.jpaFactory);
	}

	protected JavaTypeMappingProvider getJavaTypeMappingProviderForMappingKey(String key) {
		for (JavaTypeMappingProvider provider : this.getJavaTypeMappingProviders()) {
			if (provider.getKey() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + key); //$NON-NLS-1$
	}

	public JavaTypeMapping buildJavaTypeMappingFromAnnotation(String annotationName, JavaPersistentType type) {
		return this.getJavaTypeMappingProviderForAnnotation(annotationName).buildMapping(type, this.jpaFactory);
	}

	protected JavaTypeMappingProvider getJavaTypeMappingProviderForAnnotation(String annotationName) {
		for (JavaTypeMappingProvider provider : this.getJavaTypeMappingProviders()) {
			if (provider.getAnnotationName() == annotationName) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal annotation name: " + annotationName); //$NON-NLS-1$
	}

	protected synchronized JavaTypeMappingProvider[] getJavaTypeMappingProviders() {
		if (this.javaTypeMappingProviders == null) {
			this.javaTypeMappingProviders = this.buildJavaTypeMappingProviders();
		}
		return this.javaTypeMappingProviders;
	}

	protected JavaTypeMappingProvider[] buildJavaTypeMappingProviders() {
		ArrayList<JavaTypeMappingProvider> providers = new ArrayList<JavaTypeMappingProvider>();
		this.addJavaTypeMappingProvidersTo(providers);
		return providers.toArray(new JavaTypeMappingProvider[providers.size()]);
	}

	/**
	 * Override this to specify more or different type mapping providers.
	 * The default includes the JPA spec-defined type mappings of 
	 * Entity, MappedSuperclass, and Embeddable.
	 */
	protected void addJavaTypeMappingProvidersTo(List<JavaTypeMappingProvider> providers) {
		providers.add(JavaEntityProvider.instance());
		providers.add(JavaMappedSuperclassProvider.instance());
		providers.add(JavaEmbeddableProvider.instance());
		providers.add(JavaNullTypeMappingProvider.instance());
	}


	// ********** Java attribute mappings **********

	public JavaAttributeMapping buildJavaAttributeMappingFromMappingKey(String key, JavaPersistentAttribute attribute) {
		return this.getJavaAttributeMappingProviderForMappingKey(key).buildMapping(attribute, this.jpaFactory);
	}

	protected JavaAttributeMappingProvider getJavaAttributeMappingProviderForMappingKey(String key) {
		for (JavaAttributeMappingProvider provider : this.getJavaAttributeMappingProviders()) {
			if (provider.getKey() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal attribute mapping key: " + key); //$NON-NLS-1$
	}

	public JavaAttributeMapping buildJavaAttributeMappingFromAnnotation(String annotationName, JavaPersistentAttribute attribute) {
		return this.getJavaAttributeMappingProviderForAnnotation(annotationName).buildMapping(attribute, this.jpaFactory);
	}

	protected JavaAttributeMappingProvider getJavaAttributeMappingProviderForAnnotation(String annotationName) {
		for (JavaAttributeMappingProvider provider : this.getJavaAttributeMappingProviders()) {
			if (provider.getAnnotationName() == annotationName) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal annotation name: " + annotationName); //$NON-NLS-1$
	}

	protected synchronized JavaAttributeMappingProvider[] getJavaAttributeMappingProviders() {
		if (this.javaAttributeMappingProviders == null) {
			this.javaAttributeMappingProviders = this.buildJavaAttributeMappingProviders();
		}
		return this.javaAttributeMappingProviders;
	}

	protected JavaAttributeMappingProvider[] buildJavaAttributeMappingProviders() {
		ArrayList<JavaAttributeMappingProvider> providers = new ArrayList<JavaAttributeMappingProvider>();
		this.addJavaAttributeMappingProvidersTo(providers);
		return providers.toArray(new JavaAttributeMappingProvider[providers.size()]);
	}

	/**
	 * Override this to specify more or different attribute mapping providers.
	 * The default includes the JPA spec-defined attribute mappings of 
	 *     Basic
	 *     Embeddable
	 *     EmbeddedId
	 *     Id
	 *     ManyToMany
	 *     ManyToOne
	 *     OneToMany
	 *     OneToOne
	 *     Transient
	 *     Version
	 */
	protected void addJavaAttributeMappingProvidersTo(List<JavaAttributeMappingProvider> providers) {
		providers.add(JavaBasicMappingProvider.instance());
		providers.add(JavaEmbeddedMappingProvider.instance());
		providers.add(JavaEmbeddedIdMappingProvider.instance());
		providers.add(JavaIdMappingProvider.instance());
		providers.add(JavaManyToManyMappingProvider.instance());
		providers.add(JavaManyToOneMappingProvider.instance());
		providers.add(JavaOneToManyMappingProvider.instance());
		providers.add(JavaOneToOneMappingProvider.instance());
		providers.add(JavaTransientMappingProvider.instance());
		providers.add(JavaVersionMappingProvider.instance());
	}


	// ********** default Java attribute mappings **********

	public JavaAttributeMapping buildDefaultJavaAttributeMapping(JavaPersistentAttribute attribute) {
		return this.getDefaultJavaAttributeMappingProvider(attribute).buildMapping(attribute, this.jpaFactory);
	}

	public String getDefaultJavaAttributeMappingKey(JavaPersistentAttribute attribute) {
		return this.getDefaultJavaAttributeMappingProvider(attribute).getKey();
	}

	protected JavaAttributeMappingProvider getDefaultJavaAttributeMappingProvider(JavaPersistentAttribute attribute) {
		for (DefaultJavaAttributeMappingProvider provider : this.getDefaultJavaAttributeMappingProviders()) {
			if (provider.defaultApplies(attribute)) {
				return provider;
			}
		}
		return this.getNullAttributeMappingProvider();
	}

	protected synchronized DefaultJavaAttributeMappingProvider[] getDefaultJavaAttributeMappingProviders() {
		if (this.defaultJavaAttributeMappingProviders == null) {
			this.defaultJavaAttributeMappingProviders = this.buildDefaultJavaAttributeMappingProviders();
		}
		return this.defaultJavaAttributeMappingProviders;
	}

	protected DefaultJavaAttributeMappingProvider[] buildDefaultJavaAttributeMappingProviders() {
		ArrayList<DefaultJavaAttributeMappingProvider> providers = new ArrayList<DefaultJavaAttributeMappingProvider>();
		this.addDefaultJavaAttributeMappingProvidersTo(providers);
		return providers.toArray(new DefaultJavaAttributeMappingProvider[providers.size()]);
	}

	/**
	 * Override this to specify more or different default attribute mapping providers.
	 * The default includes the JPA spec-defined attribute mappings of 
	 * Embedded and Basic.
	 */
	protected void addDefaultJavaAttributeMappingProvidersTo(List<DefaultJavaAttributeMappingProvider> providers) {
		providers.add(JavaEmbeddedMappingProvider.instance());  // bug 190344 need to test default embedded before basic
		providers.add(JavaBasicMappingProvider.instance());
	}

	/**
	 * the "null" attribute mapping is used when the attribute is neither
	 * modified with a mapping annotation nor mapped by a "default" mapping
	 */
	protected JavaAttributeMappingProvider getNullAttributeMappingProvider() {
		return JavaNullAttributeMappingProvider.instance();
	}


	// ********** Mapping File **********

	public MappingFile buildMappingFile(MappingFileRef parent, OrmXmlResource resource) {
		return this.getMappingFileProviderForResourceType(resource.getType()).buildMappingFile(parent, resource, this.jpaFactory);
	}

	protected MappingFileProvider getMappingFileProviderForResourceType(String resourceType) {
		for (MappingFileProvider provider : this.getMappingFileProviders()) {
			if (provider.getResourceType() == resourceType) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal mapping file resource type: " + resourceType); //$NON-NLS-1$
	}

	protected synchronized MappingFileProvider[] getMappingFileProviders() {
		if (this.mappingFileProviders == null) {
			this.mappingFileProviders = this.buildMappingFileProviders();
		}
		return this.mappingFileProviders;
	}

	protected MappingFileProvider[] buildMappingFileProviders() {
		ArrayList<MappingFileProvider> providers = new ArrayList<MappingFileProvider>();
		this.addMappingFileProvidersTo(providers);
		return providers.toArray(new MappingFileProvider[providers.size()]);
	}

	/**
	 * Override this to specify more or different mapping file providers.
	 */
	protected void addMappingFileProvidersTo(List<MappingFileProvider> providers) {
		providers.add(GenericMappingFileProvider.instance());
	}


	// ********** extended ORM type mappings **********

	public OrmTypeMapping buildOrmTypeMappingFromMappingKey(String key, OrmPersistentType type) {
		return this.getOrmTypeMappingProviderForMappingKey(type.getOrmType(), key).buildMapping(type, this.jpaFactory);
	}

	protected OrmTypeMappingProvider getOrmTypeMappingProviderForMappingKey(String ormType, String key) {
		for (ExtendedOrmTypeMappingProvider provider : this.getExtendedOrmTypeMappingProviders()) {
			if ((provider.getOrmType() == ormType) && (provider.getKey() == key)) {
				return provider;
			}
		}
		// if we don't have an ORM-specific provider, look for a "default" provider
		return this.getOrmTypeMappingProviderForMappingKey(key);
	}

	protected synchronized ExtendedOrmTypeMappingProvider[] getExtendedOrmTypeMappingProviders() {
		if (this.extendedOrmTypeMappingProviders == null) {
			this.extendedOrmTypeMappingProviders = this.buildExtendedOrmTypeMappingProviders();
		}
		return this.extendedOrmTypeMappingProviders;
	}

	protected ExtendedOrmTypeMappingProvider[] buildExtendedOrmTypeMappingProviders() {
		ArrayList<ExtendedOrmTypeMappingProvider> providers = new ArrayList<ExtendedOrmTypeMappingProvider>();
		this.addExtendedOrmTypeMappingProvidersTo(providers);
		return providers.toArray(new ExtendedOrmTypeMappingProvider[providers.size()]);
	}

	/**
	 * Override this to specify more or different extended type mapping providers.
	 */
	protected void addExtendedOrmTypeMappingProvidersTo(@SuppressWarnings("unused") List<ExtendedOrmTypeMappingProvider> providers) {
		// none by default
	}


	// ********** "default" ORM type mappings **********

	protected OrmTypeMappingProvider getOrmTypeMappingProviderForMappingKey(String key) {
		for (OrmTypeMappingProvider provider : this.getOrmTypeMappingProviders()) {
			if (provider.getKey() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + key); //$NON-NLS-1$
	}

	protected synchronized OrmTypeMappingProvider[] getOrmTypeMappingProviders() {
		if (this.ormTypeMappingProviders == null) {
			this.ormTypeMappingProviders = this.buildOrmTypeMappingProviders();
		}
		return this.ormTypeMappingProviders;
	}

	protected OrmTypeMappingProvider[] buildOrmTypeMappingProviders() {
		ArrayList<OrmTypeMappingProvider> providers = new ArrayList<OrmTypeMappingProvider>();
		this.addOrmTypeMappingProvidersTo(providers);
		return providers.toArray(new OrmTypeMappingProvider[providers.size()]);
	}

	/**
	 * Override this to specify more or different type mapping providers.
	 * The default includes the JPA spec-defined type mappings of 
	 *     Embeddable
	 *     Entity
	 *     MappedSuperclass
	 */
	protected void addOrmTypeMappingProvidersTo(List<OrmTypeMappingProvider> providers) {
		providers.add(OrmEmbeddableProvider.instance());
		providers.add(OrmEntityProvider.instance());
		providers.add(OrmMappedSuperclassProvider.instance());
	}


	// ********** extended ORM attribute mappings **********

	public OrmAttributeMapping buildOrmAttributeMappingFromMappingKey(String key, OrmPersistentAttribute attribute) {
		return this.getOrmAttributeMappingProviderForMappingKey(attribute.getOrmType(), key).buildMapping(attribute, this.jpaFactory);
	}

	public XmlAttributeMapping buildVirtualOrmResourceMappingFromMappingKey(String key, OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping) {
		return this.getOrmAttributeMappingProviderForMappingKey(ormTypeMapping.getOrmType(), key).buildVirtualResourceMapping(ormTypeMapping, javaAttributeMapping, this.jpaFactory);
	}

	protected OrmAttributeMappingProvider getOrmAttributeMappingProviderForMappingKey(String ormType, String key) {
		for (ExtendedOrmAttributeMappingProvider provider : this.getExtendedOrmAttributeMappingProviders()) {
			if ((provider.getOrmType() == ormType) && (provider.getKey() == key)) {
				return provider;
			}
		}
		// if we don't have an ORM-specific provider, look for a "default" provider
		return this.getOrmAttributeMappingProviderForMappingKey(key);
	}

	protected synchronized ExtendedOrmAttributeMappingProvider[] getExtendedOrmAttributeMappingProviders() {
		if (this.extendedOrmAttributeMappingProviders == null) {
			this.extendedOrmAttributeMappingProviders = this.buildExtendedOrmAttributeMappingProviders();
		}
		return this.extendedOrmAttributeMappingProviders;
	}

	protected ExtendedOrmAttributeMappingProvider[] buildExtendedOrmAttributeMappingProviders() {
		ArrayList<ExtendedOrmAttributeMappingProvider> providers = new ArrayList<ExtendedOrmAttributeMappingProvider>();
		this.addExtendedOrmAttributeMappingProvidersTo(providers);
		return providers.toArray(new ExtendedOrmAttributeMappingProvider[providers.size()]);
	}

	/**
	 * Override this to specify more or different extended attribute mapping providers.
	 */
	protected void addExtendedOrmAttributeMappingProvidersTo(@SuppressWarnings("unused") List<ExtendedOrmAttributeMappingProvider> providers) {
		// none by default
	}


	// ********** "default" ORM attribute mappings **********

	protected OrmAttributeMappingProvider getOrmAttributeMappingProviderForMappingKey(String key) {
		for (OrmAttributeMappingProvider provider : this.getOrmAttributeMappingProviders()) {
			if (provider.getKey() == key) {
				return provider;
			}
		}
		return OrmNullAttributeMappingProvider.instance();
	}

	protected synchronized OrmAttributeMappingProvider[] getOrmAttributeMappingProviders() {
		if (this.ormAttributeMappingProviders == null) {
			this.ormAttributeMappingProviders = this.buildOrmAttributeMappingProviders();
		}
		return this.ormAttributeMappingProviders;
	}

	protected OrmAttributeMappingProvider[] buildOrmAttributeMappingProviders() {
		ArrayList<OrmAttributeMappingProvider> providers = new ArrayList<OrmAttributeMappingProvider>();
		this.addOrmAttributeMappingProvidersTo(providers);
		return providers.toArray(new OrmAttributeMappingProvider[providers.size()]);
	}

	/**
	 * Override this to specify more or different attribute mapping providers.
	 * The default includes the JPA spec-defined attribute mappings of 
	 *     Basic
	 *     Id
	 *     Transient
	 *     OneToOne
	 *     OneToMany
	 *     ManyToOne
	 *     ManyToMany
	 *     Embeddable
	 *     EmbeddedId
	 *     Version
	 */
	protected void addOrmAttributeMappingProvidersTo(List<OrmAttributeMappingProvider> providers) {
		providers.add(OrmEmbeddedMappingProvider.instance());  // bug 190344 need to test default embedded before basic
		providers.add(OrmBasicMappingProvider.instance());
		providers.add(OrmTransientMappingProvider.instance());
		providers.add(OrmIdMappingProvider.instance());
		providers.add(OrmManyToManyMappingProvider.instance());
		providers.add(OrmOneToManyMappingProvider.instance());
		providers.add(OrmManyToOneMappingProvider.instance());
		providers.add(OrmOneToOneMappingProvider.instance());
		providers.add(OrmVersionMappingProvider.instance());
		providers.add(OrmEmbeddedIdMappingProvider.instance());
	}


	// ********** database **********

	public ConnectionProfileFactory getConnectionProfileFactory() {
		return JptDbPlugin.instance().getConnectionProfileFactory();
	}

	public EntityGeneratorDatabaseAnnotationNameBuilder getEntityGeneratorDatabaseAnnotationNameBuilder() {
		return GenericEntityGeneratorDatabaseAnnotationNameBuilder.instance();
	}

	public DatabaseFinder getDatabaseFinder() {
		return DatabaseFinder.Default.instance();
	}

}

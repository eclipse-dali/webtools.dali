package org.eclipse.jpt.core.internal.resource.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public abstract class BaseJpaPlatform implements JpaPlatform
{
	private List<JavaTypeMappingAnnotationProvider> javaTypeMappingAnnotationProviders;
	
	private Collection<JavaTypeAnnotationProvider> javaTypeAnnotationProviders;
	
	private Collection<JavaTypeAnnotationProvider> entityAnnotationProviders;
	
	private Collection<JavaTypeAnnotationProvider> embeddableAnnotationProviders;
	
	private Collection<JavaTypeAnnotationProvider> mappedSuperclassAnnotationProviders;

	protected BaseJpaPlatform() {
		super();
	}
	
	public ListIterator<JavaTypeMappingAnnotationProvider> javaTypeMappingAnnotationProviders() {
		if (this.javaTypeMappingAnnotationProviders == null) {
			this.javaTypeMappingAnnotationProviders = new ArrayList<JavaTypeMappingAnnotationProvider>();
			this.addJavaTypeMappingAnnotationProvidersTo(this.javaTypeMappingAnnotationProviders);
		}
		return new CloneListIterator<JavaTypeMappingAnnotationProvider>(this.javaTypeMappingAnnotationProviders);
	}
	
	/**
	 * Override this to specify more or different attribute mapping providers.
	 * The default includes the JPA spec-defined attribute mappings of 
	 * Basic, Id, Transient OneToOne, OneToMany, ManyToOne, ManyToMany, Embeddable, EmbeddedId, Version.
	 */
	protected void addJavaTypeMappingAnnotationProvidersTo(Collection<JavaTypeMappingAnnotationProvider> providers) {
		providers.add(JavaEmbeddableProvider.instance());
		providers.add(JavaEntityProvider.instance());
		providers.add(JavaMappedSuperclassProvider.instance());
	}
	
	public JavaTypeMappingAnnotationProvider javaTypeMappingAnnotationProvider(String annotationName) {
		for (Iterator<JavaTypeMappingAnnotationProvider> i = this.javaTypeMappingAnnotationProviders(); i.hasNext(); ) {
			JavaTypeMappingAnnotationProvider provider = i.next();
			if (provider.getAnnotationName().equals(annotationName)) {
				return provider;
			}
		}
		return null;
	}

	
	public Iterator<JavaTypeAnnotationProvider> javaTypeAnnotationProviders() {
		if (this.javaTypeAnnotationProviders == null) {
			this.javaTypeAnnotationProviders = new ArrayList<JavaTypeAnnotationProvider>();
			this.addJavaTypeAnnotationProvidersTo(this.javaTypeAnnotationProviders);
		}
		return new CloneIterator<JavaTypeAnnotationProvider>(this.javaTypeAnnotationProviders);
	}
	
	/**
	 * Override this to specify more or different attribute mapping providers.
	 * The default includes the JPA spec-defined attribute mappings of 
	 * Basic, Id, Transient OneToOne, OneToMany, ManyToOne, ManyToMany, Embeddable, EmbeddedId, Version.
	 */
	protected void addJavaTypeAnnotationProvidersTo(Collection<JavaTypeAnnotationProvider> providers) {
		providers.add(JavaTableProvider.instance());
		providers.add(JavaSecondaryTableProvider.instance());
		providers.add(JavaSecondaryTablesProvider.instance());
	}
	
	public JavaTypeAnnotationProvider javaTypeAnnotationProvider(String annotationName) {
		for (Iterator<JavaTypeAnnotationProvider> i = this.javaTypeAnnotationProviders(); i.hasNext(); ) {
			JavaTypeAnnotationProvider provider = i.next();
			if (provider.getAnnotationName().equals(annotationName)) {
				return provider;
			}
		}
		return null;
	}

	public Iterator<JavaTypeAnnotationProvider> entityAnnotationProviders() {
		if (this.entityAnnotationProviders == null) {
			this.entityAnnotationProviders = new ArrayList<JavaTypeAnnotationProvider>();
			this.addEntityAnnotationProvidersTo(this.entityAnnotationProviders);
		}
		return new CloneIterator<JavaTypeAnnotationProvider>(this.entityAnnotationProviders);
	}
	
	protected void addEntityAnnotationProvidersTo(Collection<JavaTypeAnnotationProvider> providers) {
		providers.add(JavaTableProvider.instance());
	}
	
	public Iterator<JavaTypeAnnotationProvider> embeddableAnnotationProviders() {
		if (this.embeddableAnnotationProviders == null) {
			this.embeddableAnnotationProviders = new ArrayList<JavaTypeAnnotationProvider>();
			this.addEmbeddableAnnotationProvidersTo(this.embeddableAnnotationProviders);
		}
		return new CloneIterator<JavaTypeAnnotationProvider>(this.embeddableAnnotationProviders);
	}
	
	protected void addEmbeddableAnnotationProvidersTo(Collection<JavaTypeAnnotationProvider> providers) {
	}
	
	public Iterator<JavaTypeAnnotationProvider> mappedSuperclassAnnotationProviders() {
		if (this.mappedSuperclassAnnotationProviders == null) {
			this.mappedSuperclassAnnotationProviders = new ArrayList<JavaTypeAnnotationProvider>();
			this.addMappedSuperclassAnnotationProvidersTo(this.mappedSuperclassAnnotationProviders);
		}
		return new CloneIterator<JavaTypeAnnotationProvider>(this.mappedSuperclassAnnotationProviders);
	}
	
	protected void addMappedSuperclassAnnotationProvidersTo(Collection<JavaTypeAnnotationProvider> providers) {
	}

}

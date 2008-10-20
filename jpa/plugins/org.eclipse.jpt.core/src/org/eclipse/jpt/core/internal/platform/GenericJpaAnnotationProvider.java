/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import java.util.ListIterator;

import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.internal.resource.java.AssociationOverrideImpl.AssociationOverrideAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.AssociationOverridesImpl.AssociationOverridesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverrideImpl.AttributeOverrideAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverridesImpl.AttributeOverridesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.BasicImpl.BasicAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.ColumnImpl.ColumnAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorColumnImpl.DiscriminatorColumnAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorValueImpl.DiscriminatorValueAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EmbeddableImpl.EmbeddableAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EmbeddedIdImpl.EmbeddedIdAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EmbeddedImpl.EmbeddedAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EntityImpl.EntityAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EnumeratedImpl.EnumeratedAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.GeneratedValueImpl.GeneratedValueAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.IdClassImpl.IdClassAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.IdImpl.IdAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.InheritanceImpl.InheritanceAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.JoinColumnImpl.JoinColumnAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.JoinColumnsImpl.JoinColumnsAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.JoinTableImpl.JoinTableAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.LobImpl.LobAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.ManyToManyImpl.ManyToManyAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.ManyToOneImpl.ManyToOneAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.MapKeyImpl.MapKeyAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.MappedSuperclassImpl.MappedSuperclassAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.NamedNativeQueriesImpl.NamedNativeQueriesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.NamedNativeQueryImpl.NamedNativeQueryAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.NamedQueriesImpl.NamedQueriesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.NamedQueryImpl.NamedQueryAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.OneToManyImpl.OneToManyAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.OneToOneImpl.OneToOneAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.OrderByImpl.OrderByAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumnImpl.PrimaryKeyJoinColumnAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumnsImpl.PrimaryKeyJoinColumnsAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTableImpl.SecondaryTableAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTablesImpl.SecondaryTablesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.SequenceGeneratorImpl.SequenceGeneratorAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TableGeneratorImpl.TableGeneratorAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TableImpl.TableAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TemporalImpl.TemporalAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TransientImpl.TransientAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.VersionImpl.VersionAnnotationDefinition;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.internal.iterators.ArrayListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationListIterator;

/**
 * 
 */
public class GenericJpaAnnotationProvider
	implements JpaAnnotationProvider
{
	/**
	 * Ordered list of possible type mapping annotations. Ordered because this
	 * is used to determine the mapping in the case where 2 mapping annotations exist
	 */
	private AnnotationDefinition[] typeMappingAnnotationDefinitions;
	
	private AnnotationDefinition[] typeSupportingAnnotationDefinitions;
	
	/**
	 * Ordered list of possible attribute mapping annotations. Ordered because this
	 * is used to determine the mapping in the case where 2 mapping annotations exist
	 */
	private AnnotationDefinition[] attributeMappingAnnotationDefinitions;
	
	private AnnotationDefinition[] attributeSupportingAnnotationDefinitions;
	
	
	protected GenericJpaAnnotationProvider() {
		super();
	}
	

	// ********** type annotation definitions **********

	protected synchronized ListIterator<AnnotationDefinition> typeMappingAnnotationDefinitions() {
		if (this.typeMappingAnnotationDefinitions == null) {
			this.typeMappingAnnotationDefinitions = this.buildTypeMappingAnnotationDefinitions();
		}
		return new ArrayListIterator<AnnotationDefinition>(this.typeMappingAnnotationDefinitions);
	}
	
	protected AnnotationDefinition[] buildTypeMappingAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addTypeMappingAnnotationDefinitionsTo(definitions);
		return definitions.toArray(new AnnotationDefinition[definitions.size()]);
	}
	
	/**
	 * Override this to specify more or different type mapping definitions.
	 * The default includes the JPA spec-defined type mappings:
	 *     Embeddable
	 *     Entity
	 *     MappedSuperclass
	 */
	protected void addTypeMappingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(EmbeddableAnnotationDefinition.instance());
		definitions.add(EntityAnnotationDefinition.instance());
		definitions.add(MappedSuperclassAnnotationDefinition.instance());
	}
	
	protected synchronized ListIterator<AnnotationDefinition> typeSupportingAnnotationDefinitions() {
		if (this.typeSupportingAnnotationDefinitions == null) {
			this.typeSupportingAnnotationDefinitions = this.buildTypeSupportingAnnotationDefinitions();
		}
		return new ArrayListIterator<AnnotationDefinition>(this.typeSupportingAnnotationDefinitions);
	}
	
	protected AnnotationDefinition[] buildTypeSupportingAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addTypeSupportingAnnotationDefinitionsTo(definitions);
		return definitions.toArray(new AnnotationDefinition[definitions.size()]);
	}
	
	/**
	 * Override this to specify more or different type supporting annotation definitions.
	 * The default includes the JPA spec-defined annotations.
	 */
	protected void addTypeSupportingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(AssociationOverrideAnnotationDefinition.instance());
		definitions.add(AssociationOverridesAnnotationDefinition.instance());
		definitions.add(AttributeOverrideAnnotationDefinition.instance());
		definitions.add(AttributeOverrideAnnotationDefinition.instance());
		definitions.add(AttributeOverridesAnnotationDefinition.instance());
		definitions.add(DiscriminatorColumnAnnotationDefinition.instance());
		definitions.add(DiscriminatorValueAnnotationDefinition.instance());
		definitions.add(IdClassAnnotationDefinition.instance());
		definitions.add(InheritanceAnnotationDefinition.instance());
		definitions.add(NamedQueryAnnotationDefinition.instance());
		definitions.add(NamedQueriesAnnotationDefinition.instance());
		definitions.add(NamedNativeQueryAnnotationDefinition.instance());
		definitions.add(NamedNativeQueriesAnnotationDefinition.instance());
		definitions.add(PrimaryKeyJoinColumnAnnotationDefinition.instance());
		definitions.add(PrimaryKeyJoinColumnsAnnotationDefinition.instance());
		definitions.add(SecondaryTableAnnotationDefinition.instance());
		definitions.add(SecondaryTablesAnnotationDefinition.instance());
		definitions.add(SequenceGeneratorAnnotationDefinition.instance());
		definitions.add(TableAnnotationDefinition.instance());
		definitions.add(TableGeneratorAnnotationDefinition.instance());
	}
	

	// ********** attribute annotation definitions **********

	protected synchronized ListIterator<AnnotationDefinition> attributeMappingAnnotationDefinitions() {
		if (this.attributeMappingAnnotationDefinitions == null) {
			this.attributeMappingAnnotationDefinitions = this.buildAttributeMappingAnnotationDefinitions();
		}
		return new ArrayListIterator<AnnotationDefinition>(this.attributeMappingAnnotationDefinitions);
	}
	
	protected AnnotationDefinition[] buildAttributeMappingAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addAttributeMappingAnnotationDefinitionsTo(definitions);
		return definitions.toArray(new AnnotationDefinition[definitions.size()]);
	}
	
	/**
	 * Override this to specify more or different attribute mapping definitions.
	 * The default includes the JPA spec-defined attribute mappings:
	 *     Basic
	 *     Embedded
	 *     EmbeddedId
	 *     Id
	 *     ManyToMany
	 *     ManyToOne
	 *     OneToMany
	 *     OneToOne
	 *     Transient
	 *     Version
	 */
	protected void addAttributeMappingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(BasicAnnotationDefinition.instance());
		definitions.add(EmbeddedAnnotationDefinition.instance());
		definitions.add(EmbeddedIdAnnotationDefinition.instance());
		definitions.add(IdAnnotationDefinition.instance());
		definitions.add(ManyToManyAnnotationDefinition.instance());
		definitions.add(ManyToOneAnnotationDefinition.instance());
		definitions.add(OneToManyAnnotationDefinition.instance());
		definitions.add(OneToOneAnnotationDefinition.instance());
		definitions.add(TransientAnnotationDefinition.instance());
		definitions.add(VersionAnnotationDefinition.instance());
	}
	
	protected synchronized ListIterator<AnnotationDefinition> attributeSupportingAnnotationDefinitions() {
		if (this.attributeSupportingAnnotationDefinitions == null) {
			this.attributeSupportingAnnotationDefinitions = this.buildAttributeSupportingAnnotationDefinitions();
		}
		return new ArrayListIterator<AnnotationDefinition>(this.attributeSupportingAnnotationDefinitions);
	}
	
	protected AnnotationDefinition[] buildAttributeSupportingAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addAttributeSupportingAnnotationDefinitionsTo(definitions);
		return definitions.toArray(new AnnotationDefinition[definitions.size()]);
	}
	
	/**
	 * Override this to specify more or different attribute supporting annotation definitions.
	 * The default includes the JPA spec-defined annotations.
	 */
	protected void addAttributeSupportingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(AssociationOverrideAnnotationDefinition.instance());
		definitions.add(AssociationOverridesAnnotationDefinition.instance());
		definitions.add(AttributeOverrideAnnotationDefinition.instance());
		definitions.add(AttributeOverridesAnnotationDefinition.instance());
		definitions.add(ColumnAnnotationDefinition.instance());
		definitions.add(EnumeratedAnnotationDefinition.instance());
		definitions.add(GeneratedValueAnnotationDefinition.instance());
		definitions.add(JoinColumnAnnotationDefinition.instance());
		definitions.add(JoinColumnsAnnotationDefinition.instance());
		definitions.add(JoinTableAnnotationDefinition.instance());
		definitions.add(LobAnnotationDefinition.instance());
		definitions.add(MapKeyAnnotationDefinition.instance());
		definitions.add(OrderByAnnotationDefinition.instance());
		definitions.add(PrimaryKeyJoinColumnAnnotationDefinition.instance());
		definitions.add(PrimaryKeyJoinColumnsAnnotationDefinition.instance());
		definitions.add(SequenceGeneratorAnnotationDefinition.instance());
		definitions.add(TableGeneratorAnnotationDefinition.instance());
		definitions.add(TemporalAnnotationDefinition.instance());
	}
	

	// ********** type annotations **********

	public ListIterator<String> typeMappingAnnotationNames() {
		return annotationNames(this.typeMappingAnnotationDefinitions());
	}
	
	public Annotation buildTypeMappingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName) {
		return this.getTypeMappingAnnotationDefinition(annotationName).buildAnnotation(parent, type);
	}
	
	public Annotation buildNullTypeMappingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName) {
		return this.getTypeMappingAnnotationDefinition(annotationName).buildNullAnnotation(parent, type);
	}

	protected AnnotationDefinition getTypeMappingAnnotationDefinition(String annotationName) {
		return getAnnotationDefinition(annotationName, this.typeMappingAnnotationDefinitions());
	}
	
	public ListIterator<String> typeSupportingAnnotationNames() {
		return annotationNames(this.typeSupportingAnnotationDefinitions());
	}
	
	public Annotation buildTypeSupportingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName) {
		return this.getTypeSupportingAnnotationDefinition(annotationName).buildAnnotation(parent, type);
	}

	public Annotation buildNullTypeSupportingAnnotation(JavaResourcePersistentType parent, Type type, String annotationName) {
		return this.getTypeSupportingAnnotationDefinition(annotationName).buildNullAnnotation(parent, type);
	}
	
	protected AnnotationDefinition getTypeSupportingAnnotationDefinition(String annotationName) {
		return getAnnotationDefinition(annotationName, this.typeSupportingAnnotationDefinitions());
	}


	// ********** attribute annotations **********

	public ListIterator<String> attributeMappingAnnotationNames() {
		return annotationNames(this.attributeMappingAnnotationDefinitions());
	}
	
	public Annotation buildAttributeMappingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName) {
		return this.attributeMappingAnnotationDefinition(annotationName).buildAnnotation(parent, attribute);
	}
	
	public Annotation buildNullAttributeMappingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName) {
		return this.attributeMappingAnnotationDefinition(annotationName).buildNullAnnotation(parent, attribute);
	}
	
	protected AnnotationDefinition attributeMappingAnnotationDefinition(String annotationName) {
		return getAnnotationDefinition(annotationName, this.attributeMappingAnnotationDefinitions());
	}
	
	public ListIterator<String> attributeSupportingAnnotationNames() {
		return annotationNames(this.attributeSupportingAnnotationDefinitions());
	}
	
	public Annotation buildAttributeSupportingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName) {
		return this.getAttributeSupportingAnnotationDefinition(annotationName).buildAnnotation(parent, attribute);
	}
	
	public Annotation buildNullAttributeSupportingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName) {
		return this.getAttributeSupportingAnnotationDefinition(annotationName).buildNullAnnotation(parent, attribute);
	}
	
	protected AnnotationDefinition getAttributeSupportingAnnotationDefinition(String annotationName) {
		return getAnnotationDefinition(annotationName, this.attributeSupportingAnnotationDefinitions());
	}
	

	// ********** static methods **********

	protected static ListIterator<String> annotationNames(ListIterator<AnnotationDefinition> annotationDefinitions) {
		return new TransformationListIterator<AnnotationDefinition, String>(annotationDefinitions) {
			@Override
			protected String transform(AnnotationDefinition annotationDefinition) {
				return annotationDefinition.getAnnotationName();
			}
		};
	}
	
	protected static AnnotationDefinition getAnnotationDefinition(String annotationName, ListIterator<AnnotationDefinition> annotationDefinitions) {
		while (annotationDefinitions.hasNext()) {
			AnnotationDefinition annotationDefinition = annotationDefinitions.next();
			if (annotationDefinition.getAnnotationName().equals(annotationName)) {
				return annotationDefinition;
			}
		}
		throw new IllegalArgumentException("unsupported annotation: " + annotationName); //$NON-NLS-1$
	}
	
}

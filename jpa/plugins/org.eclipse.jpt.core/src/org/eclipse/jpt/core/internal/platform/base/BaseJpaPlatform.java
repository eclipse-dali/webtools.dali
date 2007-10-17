/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.IContextModel;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.IResourceModel;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.resource.java.Annotation;
import org.eclipse.jpt.core.internal.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.MappingAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverrideImpl.AttributeOverrideAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverridesImpl.AttributeOverridesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.BasicImpl.BasicAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.ColumnImpl.ColumnAnnotationDefinition;
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
import org.eclipse.jpt.core.internal.resource.java.SecondaryTableImpl.SecondaryTableAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTablesImpl.SecondaryTablesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.SequenceGeneratorImpl.SequenceGeneratorAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TableGeneratorImpl.TableGeneratorAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TableImpl.TableAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TemporalImpl.TemporalAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TransientImpl.TransientAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.VersionImpl.VersionAnnotationDefinition;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class BaseJpaPlatform implements IJpaPlatform
{
	private String id;
	
	protected IJpaProject project;
	
	/**
	 * Ordered list of possible type mapping annotations.  Ordered because this
	 * is used to determine the mapping in the case where 2 mapping annotations exist
	 */
	private List<MappingAnnotationDefinition> typeMappingAnnotationDefinitions;
	
	private Collection<AnnotationDefinition> typeAnnotationDefinitions;
	
	/**
	 * Ordered list of possible attribute mapping annotations.  Ordered because this
	 * is used to determine the mapping in the case where 2 mapping annotations exist
	 */
	private List<MappingAnnotationDefinition> attributeMappingAnnotationDefinitions;
	
	private Collection<AnnotationDefinition> attributeAnnotationDefinitions;
	
	
	protected BaseJpaPlatform() {
		super();
	}
	
	
	public String getId() {
		return this.id;
	}
	
	/**
	 * *************
	 * * IMPORTANT *   For INTERNAL use only !!
	 * *************
	 * 
	 * @see IJpaPlatform#setId(String)
	 */
	public void setId(String theId) {
		this.id = theId;
	}
	
	public IJpaProject getProject() {
		return this.project;
	}
	
	public void setProject(IJpaProject jpaProject) {
		this.project = jpaProject;
	}
	
	
	// **************** Model construction / updating *************************
	
	public IJpaFile buildJpaFile(IJpaProject jpaProject, IFile file) {
		IResourceModel resourceModel = jpaFactory().buildResourceModel(file);
		
		if (resourceModel != null) {
			return jpaFactory().createJpaFile(jpaProject, file, resourceModel);
		}
		
		return null;
	}
	
	public void update(IJpaProject jpaProject, IContextModel contextModel, IProgressMonitor monitor) {
		contextModel.update(jpaProject, monitor);
	}
	
	
	// ************************************************************************

	protected ListIterator<MappingAnnotationDefinition> typeMappingAnnotationDefinitions() {
		if (this.typeMappingAnnotationDefinitions == null) {
			this.typeMappingAnnotationDefinitions = new ArrayList<MappingAnnotationDefinition>();
			this.addTypeMappingAnnotationDefinitionsTo(this.typeMappingAnnotationDefinitions);
		}
		return new CloneListIterator<MappingAnnotationDefinition>(this.typeMappingAnnotationDefinitions);
	}
	
	/**
	 * Override this to specify more or different type mapping definitions.
	 * The default includes the JPA spec-defined type mappings of 
	 * Embeddable, Entity, MappedSuperclass
	 */
	protected void addTypeMappingAnnotationDefinitionsTo(List<MappingAnnotationDefinition> definitions) {
		definitions.add(EmbeddableAnnotationDefinition.instance());
		definitions.add(EntityAnnotationDefinition.instance());
		definitions.add(MappedSuperclassAnnotationDefinition.instance());
	}
	
	protected Iterator<AnnotationDefinition> typeAnnotationDefinitions() {
		if (this.typeAnnotationDefinitions == null) {
			this.typeAnnotationDefinitions = new ArrayList<AnnotationDefinition>();
			this.addTypeAnnotationDefinitionsTo(this.typeAnnotationDefinitions);
		}
		return new CloneIterator<AnnotationDefinition>(this.typeAnnotationDefinitions);
	}
	
	/**
	 * Override this to specify more or different type annotation definitions.
	 * The default includes the JPA spec-defined annotations.
	 */
	protected void addTypeAnnotationDefinitionsTo(Collection<AnnotationDefinition> definitions) {
		definitions.add(AttributeOverrideAnnotationDefinition.instance());
		definitions.add(AttributeOverridesAnnotationDefinition.instance());
		definitions.add(IdClassAnnotationDefinition.instance());
		definitions.add(InheritanceAnnotationDefinition.instance());
		definitions.add(NamedQueryAnnotationDefinition.instance());
		definitions.add(NamedQueriesAnnotationDefinition.instance());
		definitions.add(NamedNativeQueryAnnotationDefinition.instance());
		definitions.add(NamedNativeQueriesAnnotationDefinition.instance());
		definitions.add(SecondaryTableAnnotationDefinition.instance());
		definitions.add(SecondaryTablesAnnotationDefinition.instance());
		definitions.add(SequenceGeneratorAnnotationDefinition.instance());
		definitions.add(TableAnnotationDefinition.instance());
		definitions.add(TableGeneratorAnnotationDefinition.instance());
	}
	
	protected ListIterator<MappingAnnotationDefinition> attributeMappingAnnotationDefinitions() {
		if (this.attributeMappingAnnotationDefinitions == null) {
			this.attributeMappingAnnotationDefinitions = new ArrayList<MappingAnnotationDefinition>();
			this.addAttributeMappingAnnotationDefinitionsTo(this.attributeMappingAnnotationDefinitions);
		}
		return new CloneListIterator<MappingAnnotationDefinition>(this.attributeMappingAnnotationDefinitions);
	}
	
	/**
	 * Override this to specify more or different attribute mapping definitions.
	 * The default includes the JPA spec-defined attribute mappings of 
	 * Basic, Id, Transient OneToOne, OneToMany, ManyToOne, ManyToMany, Embedded, EmbeddedId, Version.
	 */
	protected void addAttributeMappingAnnotationDefinitionsTo(List<MappingAnnotationDefinition> definitions) {
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
	
	protected Iterator<AnnotationDefinition> attributeAnnotationDefinitions() {
		if (this.attributeAnnotationDefinitions == null) {
			this.attributeAnnotationDefinitions = new ArrayList<AnnotationDefinition>();
			this.addAttributeAnnotationDefinitionsTo(this.attributeAnnotationDefinitions);
		}
		return new CloneIterator<AnnotationDefinition>(this.attributeAnnotationDefinitions);
	}
	
	/**
	 * Override this to specify more or different attribute annotation definitions.
	 * The default includes the JPA spec-defined annotations.
	 */
	protected void addAttributeAnnotationDefinitionsTo(Collection<AnnotationDefinition> definitions) {
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
		definitions.add(SequenceGeneratorAnnotationDefinition.instance());
		definitions.add(TableGeneratorAnnotationDefinition.instance());
		definitions.add(TemporalAnnotationDefinition.instance());
	}
	
	//********************* IJpaPlatform implementation *************************

	public Annotation buildTypeMappingAnnotation(JavaPersistentTypeResource parent, Type type, String mappingAnnotationName) {
		MappingAnnotationDefinition annotationDefinition = typeMappingAnnotationDefinition(mappingAnnotationName);
		return annotationDefinition.buildAnnotation(parent, type);
	}
	
	public Annotation buildTypeAnnotation(JavaPersistentTypeResource parent, Type type, String annotationName) {
		AnnotationDefinition annotationDefinition = typeAnnotationDefinition(annotationName);
		return annotationDefinition.buildAnnotation(parent, type);
	}

	public Iterator<String> correspondingTypeAnnotationNames(String mappingAnnotationName) {
		return typeMappingAnnotationDefinition(mappingAnnotationName).correspondingAnnotationNames();
	}
	
	public ListIterator<String> typeMappingAnnotationNames() {
		return new TransformationListIterator<MappingAnnotationDefinition, String>(typeMappingAnnotationDefinitions()) {
			@Override
			protected String transform(MappingAnnotationDefinition next) {
				return next.getAnnotationName();
			}
		};
	}
	
	public Iterator<String> typeAnnotationNames() {
		return new TransformationIterator<AnnotationDefinition, String>(typeAnnotationDefinitions()) {
			@Override
			protected String transform(AnnotationDefinition next) {
				return next.getAnnotationName();
			}
		};
	}
	
	public Annotation buildAttributeMappingAnnotation(JavaPersistentAttributeResource parent, Attribute attribute, String mappingAnnotationName) {
		MappingAnnotationDefinition annotationDefinition = attributeMappingAnnotationDefinition(mappingAnnotationName);
		return annotationDefinition.buildAnnotation(parent, attribute);
	}
	
	public Annotation buildAttributeAnnotation(JavaPersistentAttributeResource parent, Attribute attribute, String annotationName) {
		AnnotationDefinition annotationDefinition = attributeAnnotationDefinition(annotationName);
		return annotationDefinition.buildAnnotation(parent, attribute);
	}
	
	public Iterator<String> correspondingAttributeAnnotationNames(String mappingAnnotationName) {
		return attributeMappingAnnotationDefinition(mappingAnnotationName).correspondingAnnotationNames();
	}
	
	public ListIterator<String> attributeMappingAnnotationNames() {
		return new TransformationListIterator<MappingAnnotationDefinition, String>(attributeMappingAnnotationDefinitions()) {
			@Override
			protected String transform(MappingAnnotationDefinition next) {
				return next.getAnnotationName();
			}
		};
	}
	
	public Iterator<String> attributeAnnotationNames() {
		return new TransformationIterator<AnnotationDefinition, String>(attributeAnnotationDefinitions()) {
			@Override
			protected String transform(AnnotationDefinition next) {
				return next.getAnnotationName();
			}
		};
	}
	
	private MappingAnnotationDefinition typeMappingAnnotationDefinition(String mappingAnnotationName) {
		for (ListIterator<MappingAnnotationDefinition> i = typeMappingAnnotationDefinitions(); i.hasNext(); ) {
			MappingAnnotationDefinition definition = i.next();
			if (definition.getAnnotationName().equals(mappingAnnotationName)) {
				return definition;
			}
		}
		throw new IllegalArgumentException(mappingAnnotationName + " is an unsupported type mapping annotation");
	}
	
	private AnnotationDefinition typeAnnotationDefinition(String annotationName) {
		for (Iterator<AnnotationDefinition> i = typeAnnotationDefinitions(); i.hasNext(); ) {
			AnnotationDefinition definition = i.next();
			if (definition.getAnnotationName().equals(annotationName)) {
				return definition;
			}
		}
		throw new IllegalArgumentException(annotationName + " is an unsupported type annotation");
	}

	private MappingAnnotationDefinition attributeMappingAnnotationDefinition(String mappingAnnotationName) {
		for (ListIterator<MappingAnnotationDefinition> i = attributeMappingAnnotationDefinitions(); i.hasNext(); ) {
			MappingAnnotationDefinition definition = i.next();
			if (definition.getAnnotationName().equals(mappingAnnotationName)) {
				return definition;
			}
		}
		throw new IllegalArgumentException(mappingAnnotationName + " is an unsupported attribute mapping annotation");	
	}
	
	private AnnotationDefinition attributeAnnotationDefinition(String annotationName) {
		for (Iterator<AnnotationDefinition> i = attributeAnnotationDefinitions(); i.hasNext(); ) {
			AnnotationDefinition definition = i.next();
			if (definition.getAnnotationName().equals(annotationName)) {
				return definition;
			}
		}
		throw new IllegalArgumentException(annotationName + " is an unsupported attribute annotation");
	}
	
	
	// **************** Validation ********************************************
	
	public void addToMessages(List<IMessage> messages) {
		// TODO Auto-generated method stub
		
	}
}
